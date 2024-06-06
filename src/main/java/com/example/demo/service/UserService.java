package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserEntity;
import com.example.demo.persistence.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public UserEntity create(UserEntity userEntity) {
		if(userEntity == null || userEntity.getEmail() == null) {
			throw new RuntimeException("Invalid argument");
		}
		final String email = userEntity.getEmail();
		if(userRepository.existsByEmail(email)) {
			log.warn("!!!!!!!! Email already eixts{}", email);
			throw new RuntimeException("Email already eixts");
		}
		return userRepository.save(userEntity);
	}

	public UserEntity getByCredentials(String email, String password, PasswordEncoder encoder) {

		final UserEntity originalUser = userRepository.findByEmail(email);
		
		if(originalUser != null && encoder.matches(password, originalUser.getPassword())) {
			return originalUser;
		}
		
		return null;
	}
	
	
}
