package com.hello.userservice.config;

import lombok.RequiredArgsConstructor;

import com.hello.userservice.config.interceptor.RestTemplateInterceptor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.client.ClientHttpRequestInterceptor;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final OAuth2AuthorizedClientManager manager;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new RestTemplateInterceptor(manager));
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
