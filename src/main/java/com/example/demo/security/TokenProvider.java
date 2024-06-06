package com.example.demo.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.stereotype.Service;
import com.example.demo.model.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@SpringBootApplication(exclude =  { SecurityAutoConfiguration.class })
public class TokenProvider {
	private static final String SECRET_KEY = "NMA8JPctFuna59f5NMA8JPctFuna59f5NMA8JPctFuna59f5NMA8JPctFuna59f5";
	private static final String SECRET_KEY_ENCODER = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
	
	public String create(UserEntity userEntity) {
		//기한은 지금으로부터 1일로 설정
		Date expirDate = Date.from(
				Instant.now()
				.plus(1000, ChronoUnit.DAYS));
		
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        
		//JWT token 생성
		return Jwts.builder()
				//haeder에 들어갈 내용 및 서명을 하기 위한 secret_key
				//.signWith(Keys.hmacShaKeyFor(keyBytes))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY_ENCODER.getBytes())
				//payload에 들어갈 내용
				.setSubject(userEntity.getId()) //sub
				.setIssuedAt(new Date())//iat
				.setExpiration(expirDate)//exp
				.compact();
	}
	
	public String validateAndGetUserId(String token) {
		//parseClaimsJws 메서드가 Base64로 디코딩 및 파싱
		//헤더와 페이로드를 SetSignkey로 넘어온 시크릿을 이용해 서명한 후 token의 서명과 비교
		//위조되지 않았다면 페이로드 리턴, 위조라면 예외를 날림
		//그중에서 우리는 userId가 필요하므로 getBody를 부른다
		
		log.info("!!!!!!!!!!! validateAndGetUserId token " + token);
		Claims claims = Jwts.parserBuilder()
						.setSigningKey(SECRET_KEY_ENCODER.getBytes())
						.build()
						.parseClaimsJws(token)
						.getBody();
		return claims.getSubject();
	}
	
}
