package com.example.demo.config;

import org.springframework.web.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.security.JwtAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록
@Configuration
@Slf4j
public class WebSecurityConfig {
	//WebSecurityConfiguration 더이상 사용되지 않아서 직접 Bean 작성해줘야함

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		//http 시큐리티 빌더
	/*	http.cors() //webMvcConfig에서 이미 설정했으므로 기본 설정만
		.and()
		.csrf().disable() //csrf는 현재 사용하지 않으므로 disable 처리
		.sessionManagement() //session기반이 아님을 선언ㅇ함
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeHttpRequests() // /와 auth/** 경로는 인증안해도 됨
		.requestMatchers("/", "/auth/**") //antmtchers > requestMatchers로 대체 됨
		.permitAll() 
		.anyRequest()//이외의 경로는 인증해야함
		.authenticated(); */
		
		http
		.csrf(AbstractHttpConfigurer::disable)
		.authorizeHttpRequests((requests) ->  
			requests.requestMatchers("/", "/auth/**").permitAll()
			.anyRequest().authenticated()
		)
		
		//filter등록, 매 요청마다, cors실행 후에 jwtAuthenticationFilter 실행함
		.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
		
		return http.build();
		
	}
	
}
