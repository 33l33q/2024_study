package com.example.demo.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Service;
import com.example.demo.model.UserEntity;

import io.jsonwebtoken.Jwts;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenProvider {
	private static final String SECRET_KEY = "NMA8JPctFuna59f5";
	
	public String create(UserEntity userEntity) {
		//기한은 지금으로부터 1일로 설정
		Date expirDate = Date.from(
				Instant.now()
				.plus(1, ChronoUnit.DAYS));
		
		//JWT token 생성
		return Jwts.
	}
	
}
