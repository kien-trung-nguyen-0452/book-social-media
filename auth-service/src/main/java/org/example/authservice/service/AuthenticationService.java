package org.example.authservice.service;

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
import org.example.authservice.dto.request.AuthenticateRequest;
import org.example.authservice.dto.request.IntrospectTokenRequest;
import org.example.authservice.dto.response.AuthenticateResponse;
import org.example.authservice.dto.response.IntrospectTokenResponse;
import org.example.authservice.entity.User;
import org.example.authservice.exception.ErrorCode;
import org.example.authservice.exception.ServiceException;
import org.example.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

     UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;


    public AuthenticateResponse authenticate(AuthenticateRequest authenticateRequest) {
        PasswordEncoder  passwordEncoder = new BCryptPasswordEncoder(10);
        var user = userRepository.findByUsername(authenticateRequest.getUsername()).orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_EXISTED));
        boolean authenticated = passwordEncoder.matches(authenticateRequest.getPassword(), user.getPassword());


        if(!authenticated) {
            log.error("Invalid username or password: {}", authenticateRequest.getPassword() + " user password: " + user.getPassword());
           throw new ServiceException(ErrorCode.UNAUTHENTICATED);

        }
        var token = generatedToken(user);
        return AuthenticateResponse.builder().token(token)
                .authenticated(true)
                .build();

    }

    public IntrospectTokenResponse introspectToken(IntrospectTokenRequest introspectTokenRequest) throws JOSEException, ParseException {
        var token = introspectTokenRequest.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);
        return IntrospectTokenResponse.builder().valid(verified && expiryTime.after(new Date())).build();

    }

    private String generatedToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issueTime(new Date())
                .expirationTime(
                        new Date (Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
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
        if(!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role ->{
                    stringJoiner.add("ROLE_" + role.getRoleName());
                    if(!CollectionUtils.isEmpty(role.getPermissions())) {
                        role.getPermissions().forEach(permission -> {
                            stringJoiner.add(permission.getName());
                        });
                    }
            }
            );

        }
        else {
            log.info("No user has been created with default scope");
        }
        return stringJoiner.toString();
    }
}
