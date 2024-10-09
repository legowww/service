package com.res.demo;

import feign.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/caller")
@RestController
public class CallerApi {

    private final WebServerApplicationContext webServerApplicationContext;
    private final CallerService callerService;


    @GetMapping
    public ResponseEntity<String> caller() throws UnknownHostException {
        log.info("caller method run");
        return ResponseEntity.ok("Server is running on IP: " + InetAddress.getLocalHost().getHostAddress() + " Port: " + webServerApplicationContext.getWebServer().getPort());
    }

    @GetMapping("/user")
    public ResponseEntity<String> caller1() throws UnknownHostException {
        return ResponseEntity.ok("[USER] Server is running on IP: " + InetAddress.getLocalHost().getHostAddress() + " Port: " + webServerApplicationContext.getWebServer().getPort());
    }

    @GetMapping("/order")
    public ResponseEntity<String> caller2() throws UnknownHostException {
        return ResponseEntity.ok("[ORDER] Server is running on IP: " + InetAddress.getLocalHost().getHostAddress() + " Port: " + webServerApplicationContext.getWebServer().getPort());
    }

    @GetMapping("/pay")
    public ResponseEntity<String> caller3() throws UnknownHostException {
        return ResponseEntity.ok("[PAY] Server is running on IP: " + InetAddress.getLocalHost().getHostAddress() + " Port: " + webServerApplicationContext.getWebServer().getPort());
    }

    @GetMapping("/payment")
    public ResponseEntity<String> caller(@RequestParam String type) throws IOException {
        Response payment = callerService.payment(type);

        StringBuilder responseBody = new StringBuilder();
        InputStream inputStream = payment.body().asInputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                responseBody.append(line);
            }
        }

        return ResponseEntity.ok(responseBody.toString());
    }
}
