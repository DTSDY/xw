package com.sy.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class BlackListFilter implements GlobalFilter, Ordered {

    public static final Logger logger = LoggerFactory.getLogger(BlackListFilter.class);


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        logger.error(exchange.getAttributes().toString());
        logger.error("==================================");
        logger.error(request.getURI().toString());
        logger.error(exchange.getRequest().getQueryParams().toString());

        return chain.filter(exchange);
    }

    // 优先级 越小越高
    @Override
    public int getOrder() {
        return 0;
    }
}
