package com.example.demo.security;

import java.io.IOException;
import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
										throws ServletException, IOException {
		try {
			//요청에서 토큰 가져오기
			String token = parseBearerToken(request);
			log.info("!!!!!!!!!!!!!!filter is running...{}!!!!!!!!!!!!!!!!!!!!!!", token);
			// 토큰 검사하기 JWT이므로 인가 서버에 요청하지 않고도 검증 가능
			
			if(token != null && !token.equalsIgnoreCase("null")) {
				//userId가져오기, 위조된 경우 예외 처리 됨
				String userId = tokenProvider.validateAndGetUserId(token);
				log.info("!!!!!!!!!!!!!!! Authenticated user ID : " + userId);
				
				//인증 완료, 등록해야 인증된 사용자라고 인식함
				AbstractAuthenticationToken authentiocation = new UsernamePasswordAuthenticationToken
						(userId, //인증된 사용자 정보, 문자열이 아니어도 상관없음. 보통 UserDetail라는 오브젝트를 넣음
						null, 
						AuthorityUtils.NO_AUTHORITIES);
				authentiocation.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
				securityContext.setAuthentication(authentiocation);
				SecurityContextHolder.setContext(securityContext);
			}
			
		}catch (Exception e){
				logger.error("Could not set user authentication in security context", e);
		}
		
		filterChain.doFilter(request, response);
		
	}

	private String parseBearerToken(HttpServletRequest request) {
		//http 요청의 헤더를 파싱해 bearer 토큰을 리턴함
		String bearerToken = request.getHeader("Authorization");
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
