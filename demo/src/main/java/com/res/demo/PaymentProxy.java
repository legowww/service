package com.res.demo;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "payment-api", url = "http://httpbin.org/anything")
public interface PaymentProxy {

    @GetMapping
    Response payment();
}
