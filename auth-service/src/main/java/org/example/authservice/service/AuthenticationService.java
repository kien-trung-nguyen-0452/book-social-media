package org.example.authservice.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

import org.example.authservice.dto.request.AuthenticateRequest;
import org.example.authservice.dto.request.IntrospectTokenRequest;
import org.example.authservice.dto.request.LogoutRequest;
import org.example.authservice.dto.request.RefreshRequest;
import org.example.authservice.dto.response.AuthenticateResponse;
import org.example.authservice.dto.response.IntrospectTokenResponse;
import org.example.authservice.entity.InvalidateToken;
import org.example.authservice.entity.User;
import org.example.authservice.exception.ErrorCode;
import org.example.authservice.exception.ServiceException;
import org.example.authservice.repository.InvalidateTokenRepository;
import org.example.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;
    InvalidateTokenRepository invalidateTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    public AuthenticateResponse authenticate(AuthenticateRequest authenticateRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var user = userRepository
                .findByUsername(authenticateRequest.getUsername())
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_EXISTED));
        boolean authenticated = passwordEncoder.matches(authenticateRequest.getPassword(), user.getPassword());

        if (!authenticated) {
            log.error(
                    "Invalid username or password: {}",
                    authenticateRequest.getPassword() + " user password: " + user.getPassword());
            throw new ServiceException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generatedToken(user);
        return AuthenticateResponse.builder().token(token).authenticated(true).build();
    }

    public IntrospectTokenResponse introspectToken(IntrospectTokenRequest introspectTokenRequest)
            throws JOSEException, ParseException {
        var token = introspectTokenRequest.getToken();
        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (ServiceException e) {
            isValid = false;
        }
        return IntrospectTokenResponse.builder().valid(isValid).build();
    }

    private String generatedToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", buildScope(user))
                .jwtID(UUID.randomUUID().toString())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getRoleName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> {
                        stringJoiner.add(permission.getName());
                    });
                }
            });

        } else {
            log.info("No user has been created with default scope");
        }
        return stringJoiner.toString();
    }

    public void logout(LogoutRequest logoutRequest) throws JOSEException, ParseException {

        try {
            var signToken = verifyToken(logoutRequest.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidateToken invalidatedToken =
                    InvalidateToken.builder().id(jit).expiryTime(expiryTime).build();

            invalidateTokenRepository.save(invalidatedToken);
        } catch (ServiceException exception) {
            log.info("Token already expired");
        }
    }

    public AuthenticateResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken(), true);

        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidateToken invalidatedToken =
                InvalidateToken.builder().id(jit).expiryTime(expiryTime).build();

        invalidateTokenRepository.save(invalidatedToken);

        var username = signedJWT.getJWTClaimsSet().getSubject();

        var user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ServiceException(ErrorCode.UNAUTHENTICATED));

        var token = generatedToken(user);

        return AuthenticateResponse.builder().token(token).authenticated(true).build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                        .getJWTClaimsSet()
                        .getIssueTime()
                        .toInstant()
                        .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                        .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new ServiceException(ErrorCode.UNAUTHENTICATED);

        if (invalidateTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new ServiceException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }
}
