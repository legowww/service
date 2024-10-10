package com.res.circuit.proxy;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "payment-api", url = "http://httpbin.org/anything")
public interface PaymentServerProxyClient {

    @GetMapping
    Response payment();
}
