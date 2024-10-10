package com.res.microservices.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;


@Slf4j
@RequiredArgsConstructor
@RestController
public class UserServerApi {

    private final WebServerApplicationContext webServerApplicationContext;

    @GetMapping("/user")
    public ResponseEntity<String> user(@RequestHeader(name = "Bearer") String header) throws UnknownHostException {
        String response = "";

        log.info("header" + header);
        if (header != null) {
            response += "게이트웨이에서 추가된 Bearer 헤더: " + header + "\n";
        } else {
            response += "헤더 없음\n";
        }

        response += "UserServer [IP: " + InetAddress.getLocalHost().getHostAddress() + " Port: " + webServerApplicationContext.getWebServer().getPort() + "] 응답";

        return ResponseEntity.ok(response);
    }
}
