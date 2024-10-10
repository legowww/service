package com.res.microservices.payment;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;


@RequiredArgsConstructor
@RestController
public class PaymentServerApi {

    private final WebServerApplicationContext webServerApplicationContext;

    @GetMapping("/payment")
    public ResponseEntity<String> payment(@RequestHeader(name = "Bearer") String header) throws UnknownHostException {
        String response = "";

        if (header != null) {
            response += "게이트웨이에서 추가된 Bearer 헤더: " + header + "\n";
        } else {
            response += "헤더 없음\n";
        }

        response += "Payment Server [IP: " + InetAddress.getLocalHost().getHostAddress() + " Port: " + webServerApplicationContext.getWebServer().getPort() + "] 응답";

        return ResponseEntity.ok(response);
    }
}
