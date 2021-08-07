package com.sensorweb.datacentergateway.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 无效token/token过期 自定义响应
 */
@Slf4j
@Component
public class CustomServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {


    @Override
    public Mono<Void> commence(ServerWebExchange serverWebExchange, AuthenticationException e) {
        try {
            //解析异常，如果是401则处理
           ServerHttpResponse response = serverWebExchange.getResponse();
           if (response.getStatusCode()==HttpStatus.UNAUTHORIZED) {


           }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
