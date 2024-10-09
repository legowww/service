package com.res.demo;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.spring6.circuitbreaker.configure.CircuitBreakerConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@Configuration
public class MyCircuitBreakerConfig {

    /*
        주석 시 yml 설정값으로 설정
     */
    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        return CircuitBreakerRegistry.ofDefaults();
    }

    /**
     * "failureRate": "-1.0%" 는 아직 minimumNumberOfCalls 만큼 호출되지 않았음을 의미
     */
    @Bean
    public CircuitBreaker circuitBreaker(CircuitBreakerRegistry circuitBreakerRegistry) {
        // https://mein-figur.tistory.com/entry/resilience4j-circuit-breaker-setting
        CircuitBreakerConfig build = CircuitBreakerConfig.custom()
                .failureRateThreshold(35)
//                .slowCallRateThreshold(10)
//                .slowCallDurationThreshold(Duration.ofNanos(500))
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .minimumNumberOfCalls(3)
                .slidingWindowSize(5)
                .waitDurationInOpenState(Duration.ofSeconds(10)) // OPEN -> HALF-OPEN 전환 전 기다리는 시간
                .automaticTransitionFromOpenToHalfOpenEnabled(true) // 기본값은 false, 시간지나도 HALF 로 넘어가지 않ㄹ음
                .permittedNumberOfCallsInHalfOpenState(3)  // HALFOPEN -> CLOSE(3번 성공) or OPEN(3번 실패) 으로 판단하기 위해 호출 횟수,
                .build();

//        CircuitBreakerConfig build = CircuitBreakerConfig.custom()
//                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
//                .slidingWindowSize(5) // 최대 100개의 호출을 기록하여 failureRate를 계산
//                .minimumNumberOfCalls(1) // 최소 1번은 호출되어야 CircuitBreaker가 작동, 이 1번이 성공인지 실패인지는 상관없음
//                .failureRateThreshold(30) // failure rate는 30%로 설정
//                .build();

        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("my", build);

        CircuitBreaker.EventPublisher eventPublisher = circuitBreaker.getEventPublisher()
                .onSuccess(event -> log.info("success"))
                .onCallNotPermitted(event -> log.info("onCallNotPermitted"))
                .onError(event -> log.info("error"))
                .onStateTransition(event -> log.info("change"));

        return circuitBreaker;
    }
}
