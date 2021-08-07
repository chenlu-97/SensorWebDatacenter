package com.sensorweb.datacenterauth2service.config;

import com.sensorweb.datacenterauth2service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Component
public class MyCustomTokenEnhancer implements TokenEnhancer {
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(oAuth2AccessToken);
        User user = (User) oAuth2Authentication.getPrincipal();
        defaultOAuth2AccessToken.getAdditionalInformation().put("user_info", user);
        defaultOAuth2AccessToken.setExpiration(new Date(System.currentTimeMillis() + 60*1000));
        return jwtAccessTokenConverter.enhance(defaultOAuth2AccessToken, oAuth2Authentication);
//        return defaultOAuth2AccessToken;
    }
}
