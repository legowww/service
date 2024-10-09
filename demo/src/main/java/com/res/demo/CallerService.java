package com.res.demo;

import feign.Response;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
@Service
public class CallerService {

    private final PaymentProxy paymentProxy;

    @CircuitBreaker(name = "my", fallbackMethod = "fallback")
    public Response payment(String type) {

        // [demo] [nio-8080-exec-8] com.res.demo.MyCircuitBreakerConfig      : success
        if (type.equals("y")) {
            return paymentProxy.payment();
        }

        // [demo] [io-8080-exec-10] com.res.demo.MyCircuitBreakerConfig      : error
        if (type.equals("r")) {
            throw new RuntimeException();
        }

        if (type.equals("i")) {
            throw new IllegalStateException();
        }

        return paymentProxy.payment();
    }

    // OPEN 상태에서 호출할 경우에만 실행 (HALF-OPEN 도 실행안됨)
    public Response fallback(String type, CallNotPermittedException callNotPermittedException) {
        log.info("fallback occured {}", callNotPermittedException.toString());
        return null;
    }

    // 아무 조건 상관없이 파라미터 예외가 발생하면 실행됨 (but, OPEN 상태가 되면 CallNotPermittedException 이 실행됨)
    // - CLOSED 상태여도
    // - minimumNumberOfCalls 를 채우지 못해도
    public Response fallback(String type, RuntimeException exception) {
        log.info("fallback RuntimeException occured");
        return null;
    }

    public Response fallback(String type, IllegalStateException exception) {
        log.info("fallback IllegalStateException occured");
        return null;
    }
}
