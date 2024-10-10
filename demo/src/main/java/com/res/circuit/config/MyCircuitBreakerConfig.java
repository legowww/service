package com.res.circuit.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@Configuration
public class MyCircuitBreakerConfig {

    @Bean
    public CircuitBreaker myCircuitBreaker(CircuitBreakerRegistry circuitBreakerRegistry) {
        CircuitBreakerConfig config =
                CircuitBreakerConfig.custom()
                                    .minimumNumberOfCalls(1) // 집계에 필요한 최소 호출 수, 이 값 이전 까지는 아무리 실패해도 OPEN 상태가 되지 않음

                                    .slidingWindowSize(5) // 최대 5개의 최근 호출을 기반으로 실패율 계산

                                    .failureRateThreshold(30) // 실패율이 60%를 넘길 경우 CLOSE 에서 OPEN 전환

                                    .waitDurationInOpenState(Duration.ofSeconds(10)) // 10초 후 OPEN에서 HALF OPEN 상태로 전환

                                    .automaticTransitionFromOpenToHalfOpenEnabled(true)

                                    .ignoreExceptions(IllegalArgumentException.class) // 사용자 입력으로 발생하는 IllegalArgumentException 예외는 실패나 성공으로 기록하지 않음

                                    .build();

        return circuitBreakerRegistry.circuitBreaker("my-circuit-breaker", config);
    }
}
