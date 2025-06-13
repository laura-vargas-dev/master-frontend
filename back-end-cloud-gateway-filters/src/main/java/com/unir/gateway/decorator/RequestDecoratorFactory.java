package com.unir.gateway.decorator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unir.gateway.model.GatewayRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestDecoratorFactory {

    private final ObjectMapper objectMapper;

    public ServerHttpRequestDecorator getDecorator(GatewayRequest request) {
        return new UniversalRequestDecorator(
            request,
            org.springframework.http.HttpMethod.valueOf(request.getTargetMethod().name().toUpperCase()),
            objectMapper
        );
    }
}
