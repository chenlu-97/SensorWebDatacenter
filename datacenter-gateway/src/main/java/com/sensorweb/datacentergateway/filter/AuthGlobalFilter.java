//package com.sensorweb.datacentergateway.filter;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.nimbusds.jose.JWSObject;
//import com.sensorweb.datacentergateway.service.UserService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.security.authorization.AuthorizationDecision;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.math.BigInteger;
//import java.net.URI;
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Date;
//import java.util.List;
//
//@Slf4j
//@Component
//public class AuthGlobalFilter implements GlobalFilter, Ordered {
//    @Autowired
//    private UserService userService;
//
//    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        ServerHttpResponse response = exchange.getResponse();
//        URI uri = request.getURI();
//        String path = request.getURI().getPath();
//        //如果是请求授权玛，则自动重定向（使用网关直接访问地址，登陆成功后无法正常转到授权页面，网上说是因为初次请求与跳转请求地址不一致导致，目前不知道怎么解决，暂时进行如下处理）
//        if (uri.getPath().equals("/auth/oauth/authorize")) {
//            String url = "http://127.0.0.1:9401/oauth/authorize?client_id=hq&response_type=code&redirect_uri=https://www.baidu.com";
//            response.setStatusCode(HttpStatus.SEE_OTHER);
//            response.getHeaders().set(HttpHeaders.LOCATION, url);
//            return response.setComplete();
//        }
//        //处理Token
//        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
//        if (StringUtils.isBlank(token) || token.startsWith("Basic")) {
//            return chain.filter(exchange);
//        }
//        List<String> paths = new ArrayList<>();
//        try {
//            String realToken = token.replace("Bearer ", "");
//            JWSObject jwsObject = JWSObject.parse(realToken);
//            String jsonStr = jwsObject.getPayload().toString();
//            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
//            BigInteger epoch = jsonObject.getBigInteger("exp");
//            Date date = new Date(epoch.longValue() * 1000);
//            if (date.before(new Date(System.currentTimeMillis()))) {
//                System.out.println("过期");
//                response.setStatusCode(HttpStatus.UNAUTHORIZED);
//                return response.setComplete();
//            }
//            JSONArray jsonArray = jsonObject.getJSONArray("authorities");
//            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//            for (int i=0; i<jsonArray.size(); i++) {
//                authorities.add(new SimpleGrantedAuthority(jsonArray.getString(i)));
//            }
//            if (authorities.size()>0) {
//                for (GrantedAuthority authority:authorities) {
//                    String role = authority.getAuthority();
//                    List<String> temp = userService.getPathByRoleName(role);
//                    if (temp!=null && temp.size()>0) {
//                        paths.addAll(temp);
//                    }
//                }
//            }
//            boolean flag = checkPath(paths, path);
//            if (flag) {
//                log.info("AuthGlobalFilter.filter() user:{}", jsonStr);
//                request = request.mutate().header("user", jsonStr).build();
//                exchange = exchange.mutate().request(request).build();
//            } else {
//                response.setStatusCode(HttpStatus.UNAUTHORIZED);
//                return response.setComplete();
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return chain.filter(exchange);
//    }
//
//    private boolean checkPath(Collection<String> patterns, String path) {
//        for (String str:patterns) {
//            if (antPathMatcher.match(str, path)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
