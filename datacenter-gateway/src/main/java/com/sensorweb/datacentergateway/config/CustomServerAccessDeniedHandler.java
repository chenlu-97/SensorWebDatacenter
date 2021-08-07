//package com.sensorweb.datacentergateway.config;
//
//import com.alibaba.fastjson.JSON;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.nio.charset.StandardCharsets;
//
///**
// * 无权访问自定义响应
// */
//@Component
//public class CustomServerAccessDeniedHandler implements ServerAccessDeniedHandler {
//    @Override
//    public Mono<Void> handle(ServerWebExchange serverWebExchange, AccessDeniedException e) {
//        ServerHttpResponse response = serverWebExchange.getResponse();
//        response.setStatusCode(HttpStatus.OK);
//        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//        response.getHeaders().set("Access-Control-Allow-Origin", "*");
//        response.getHeaders().set("Cache-Control", "no-cache");
//        String body = JSON.toJSONString("user-access-unauthorized");
//        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
//        return response.writeWith(Mono.just(buffer));
//    }
//}
