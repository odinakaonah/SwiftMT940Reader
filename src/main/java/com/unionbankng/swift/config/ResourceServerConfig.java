package com.unionbankng.swift.config;

import com.unionbankng.swift.SwiftException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.IOException;
import java.io.InputStreamReader;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable().formLogin().disable().authorizeRequests()
		.antMatchers("/","/index","/v2/api-docs/**","/swagger-resources/**"
				,"/swagger-ui.html","/favicon.ico","/webjars/**"
				,"/resources/**").permitAll()
		.anyRequest()
		.authenticated();
	}


	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenServices(tokenServices());
	}


	@Bean
	@Primary
	public DefaultTokenServices tokenServices() throws SwiftException {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		return defaultTokenServices;
	}
	@Bean
	public TokenStore tokenStore() throws SwiftException {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() throws SwiftException {
		org.springframework.core.io.Resource publicResource = new ClassPathResource("publickey.txt");
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

		String publicKey = null;
		try{
			publicKey = org.apache.commons.io.IOUtils.toString(new InputStreamReader(publicResource.getInputStream()));
		} catch (final IOException e){
			throw new SwiftException(503,e.getLocalizedMessage());
		}
		converter.setVerifierKey(publicKey);
		return converter;
	}

	@Bean
	public BCryptPasswordEncoder encoder(){
		return new BCryptPasswordEncoder();
	}
}