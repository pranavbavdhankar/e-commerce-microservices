package com.example.gateway.routes;

import com.example.gateway.filter.AuthFilter;
import com.example.gateway.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CustomRouteLocator {


    private final JwtService jwtService;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("AUTH-SERVICE", r -> r
                                .path("/api/auth/**")
                                .uri("lb://USER-SERVICE"))

                .route("PRODUCT-SERVICE", r -> r
                                .path("/api/product/**", "/api/category/**")
                                .uri("lb://PRODUCT-SERVICE"))



                .route("USER-SERVICE", r -> r
                        .path("/api/user/**")
                        .filters(f -> f .filter(new AuthFilter(jwtService)))
                        .uri("lb://USER-SERVICE"))

                .route("ADMIN-PRODUCT-SERVICE", r -> r
                        .path("/api/product/admin/**", "/api/category/admin/**")
                        .filters(f -> f .filter(new AuthFilter(jwtService)))
                        .uri("lb://PRODUCT-SERVICE"))
                .build();
    }

}
