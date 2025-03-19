package com.example.medicamentos_service.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimitConfig {

    @Bean
    public RateLimiter rateLimiter() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(4) // 4 requisições
                .limitRefreshPeriod(Duration.ofSeconds(5)) // A cada 5 segundos
                .timeoutDuration(Duration.ofMillis(500)) // Espera 500ms antes de rejeitar
                .build();

        return RateLimiterRegistry.of(config).rateLimiter("medicamentoRateLimiter");
    }
}
