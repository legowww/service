package com.res.circuit;

import com.res.circuit.proxy.PaymentServerProxyClient;
import feign.Response;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {

    private final PaymentServerProxyClient paymentServerProxyClient;

    @CircuitBreaker(name = "my-circuit-breaker", fallbackMethod = "fallback")
    public String book(String status) {
        // 1. REST API 로 결제 서비스 호출
        Response response = paymentServerProxyClient.payment();

        // 2. 파라미터의 status 값은 결제 서비스 응답 결과라고 가정

        switch (status) {
            // REST API 로 호출한 결제 서비스가 정상적으로 응답한 상황
            case "success": {
                return status;
            }

            // REST API 로 호출한 결제 서비스에 장애가 발생한 상황
            case "fail": {
                throw new RuntimeException("결제 서버와 통신할 수 없는 상태입니다.");
            }

            // 클라이언트가 잘못된 입력값을 전송한 상황
            default: {
                throw new IllegalArgumentException();
            }
        }
    }

    // 서킷브레이커가 OPEN 상태일 경우 fallback
    public String fallback(String status, CallNotPermittedException callNotPermittedException) {
        log.info("CircuitBreaker 의 상태가 OPEN 된 상태입니다.");
        throw new IllegalStateException("CircuitBreaker 의 상태가 OPEN 된 상태입니다.");
    }

    // 예외가 발생했을 경우 fallback
    public String fallback(String status, RuntimeException exception) {
        log.info("결제 서비스 호출 실패");
        throw new IllegalStateException("결제 서비스 호출 실패");
    }

    // IllegalArgumentException 는 서킷브레이커의 성공/실패에 기록되지 않음
    public String fallback(String status, IllegalArgumentException exception) {
        log.info("사용자 입력값이 잘못됐습니다.");
        throw new IllegalArgumentException("사용자 입력값이 잘못됐습니다.");
    }
}
