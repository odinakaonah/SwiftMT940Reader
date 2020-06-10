package com.unionbankng.swift;

import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SwiftApplication extends SpringBootServletInitializer {

    @Autowired
    private HttpClient httpClient;
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SwiftApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SwiftApplication.class, args);
    }

    @Bean
    public RestTemplate getResTemplate(){
        return new RestTemplate(clientHttpRequestFactory());
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {

        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectionRequestTimeout(50000);
        clientHttpRequestFactory.setReadTimeout(180000);
        clientHttpRequestFactory.setConnectTimeout(40000);
        clientHttpRequestFactory.setHttpClient(httpClient);
        return clientHttpRequestFactory;
    }


}
