package org.example.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.Retryer;

@Configuration
public class FeintRetryerConfig {

    private static final int MAX_ATTEMPTS = 3;
    private static final long MAX_INTERVAL = 20000L;
    private static final long INIT_INTERVAL = 10000L;

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(INIT_INTERVAL, MAX_INTERVAL, MAX_ATTEMPTS);
    }

    @Bean
    public RequestInterceptor interceptor() {
        return new AuthenticationRequestInterceptor();
    }
}
