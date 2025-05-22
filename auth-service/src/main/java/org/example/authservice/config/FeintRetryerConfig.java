package org.example.authservice.config;

import feign.RequestInterceptor;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeintRetryerConfig {

    private static final int MAX_ATTEMPTS = 3;
    private static final long MAX_INTERVAL = 2000L;
    private static final long INIT_INTERVAL = 1000L;

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(INIT_INTERVAL, MAX_INTERVAL, MAX_ATTEMPTS);
    }

    @Bean
    public RequestInterceptor interceptor(){
        return new AuthenticationRequestInterceptor();
    }
}
