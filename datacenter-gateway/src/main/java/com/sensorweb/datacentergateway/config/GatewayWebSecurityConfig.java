package com.sensorweb.datacentergateway.config;

import com.alibaba.fastjson.JSON;
import com.sensorweb.datacentergateway.util.Constant;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.cors.CorsUtils;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebFluxSecurity
public class GatewayWebSecurityConfig implements Constant {

    @Autowired
    private AuthorizationManager authorizationManager;

//    @Autowired
//    private final CustomServerAccessDeniedHandler customServerAccessDeniedHandler;

//    @Autowired
//    private final CustomServerAuthenticationEntryPoint customServerAuthenticationEntryPoint;

    //security鉴权排除的url列表
    private static final String[] excludeAuthPages = {
            "/auth/login",
            "/auth/logout"
    };

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
        //自定义处理Jwt请求头过期或签名错误的结果
//        http.oauth2ResourceServer().authenticationEntryPoint(customServerAuthenticationEntryPoint);
        http
                .authorizeExchange()
//                .pathMatchers(excludeAuthPages).permitAll()   //无需进行权限过滤的请求路径
//                .pathMatchers(HttpMethod.OPTIONS).permitAll()   //请求默认放行
                .anyExchange().access(authorizationManager)  //鉴权管理器配置
//                .anyExchange().permitAll()
//                .accessDeniedHandler(customServerAccessDeniedHandler)  //处理为未授权
//                .authenticationEntryPoint(customServerAuthenticationEntryPoint) //处理未认证
//                .and().httpBasic().disable()
                .and()
                .csrf().disable();

        return http.build();
    }

}
