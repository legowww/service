package com.gateway.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("user-service", r -> r.path("/caller/user/**").filters(f -> f.addRequestHeader())
//                        .uri("http://localhost:8081"))
//                .route("order-service", r -> r.path("/caller/order/**")
//                        .uri("http://localhost:8082"))
//                .route("payment-service", r -> r.path("/caller/pay/**")
//                        .uri("http://localhost:8083"))
//                .build();
//    }
}
