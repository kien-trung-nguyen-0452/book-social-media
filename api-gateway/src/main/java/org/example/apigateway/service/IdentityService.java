package org.example.apigateway.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.apigateway.dto.common.ApiResponse;
import org.example.apigateway.dto.request.IntrospectRequest;
import org.example.apigateway.dto.response.IntrospectResponse;
import org.example.apigateway.repository.IdentityClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {
    IdentityClient identityClient;

    public Mono<ApiResponse<IntrospectResponse>> introspect (@RequestBody String token){
        return identityClient.introspect(IntrospectRequest.builder()
                        .token(token)

                        .build());
    }



}
