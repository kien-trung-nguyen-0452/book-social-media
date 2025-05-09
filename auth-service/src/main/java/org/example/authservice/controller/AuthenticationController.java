package org.example.authservice.controller;

import java.text.ParseException;

import org.example.authservice.dto.common.ApiResponse;
import org.example.authservice.dto.request.AuthenticateRequest;
import org.example.authservice.dto.request.IntrospectTokenRequest;
import org.example.authservice.dto.request.LogoutRequest;
import org.example.authservice.dto.request.RefreshRequest;
import org.example.authservice.dto.response.AuthenticateResponse;
import org.example.authservice.dto.response.IntrospectTokenResponse;
import org.example.authservice.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/token")

    ApiResponse<AuthenticateResponse> authenticate(@RequestBody AuthenticateRequest authenticateRequest) {
        var result = authenticationService.authenticate(authenticateRequest);
        return ApiResponse.<AuthenticateResponse>builder().result(result).build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectTokenResponse> introspectToken(@RequestBody IntrospectTokenRequest introspectTokenRequest)
            throws JOSEException, ParseException {
        var result = authenticationService.introspectToken(introspectTokenRequest);
        return ApiResponse.<IntrospectTokenResponse>builder().result(result).build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticateResponse> authenticate(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticateResponse>builder().result(result).build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws JOSEException, ParseException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }
}
