package com.example.demo.contoroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.ResponseDTO;
import com.example.demo.model.UserEntity;
import com.example.demo.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
		try {
			log.info("!!!!!!!!!!!!!!데이터 확인하기 {}", userDTO.getEmail());
			//요청을 이용해 저장할 사용자 만들기 
			UserEntity user = UserEntity.builder().email(userDTO.getEmail())
								.username(userDTO.getUsername())
								.password(userDTO.getPassword())
								.build();
			log.info("!!!!!!!!!!!!!!데이터 확인하기 {}", user);
			//서비스를 이용해 리포지터리에 사용자 저장
			UserEntity registerUser = userService.create(user);
			log.info("2222222222222222222222222");
			UserDTO responseUserDTO = UserDTO.builder().email(registerUser.getEmail())
											.id(registerUser.getId())
											.username(registerUser.getUsername())
											.build();
			log.info("33333333333333333333333333333333333333");
			return ResponseEntity.ok().body(responseUserDTO);			
		}catch (Exception e) {
			//사용자 정보는 항상 하나이므로 리스트로 만들어야하는 ResponseDTO를 사용하지 않고 그냥 UserDTO를 리턴
			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){
		
		UserEntity user = userService.getByCredentials(userDTO.getEmail(), userDTO.getPassword());
		
		if(user != null) {
			final UserDTO responseUserDTO = UserDTO.builder()
											.email(userDTO.getEmail())
											.id(userDTO.getId())
											.build();
			return ResponseEntity.ok().body(responseUserDTO);
		}else {
			ResponseDTO responseDTO = ResponseDTO.builder().error("Login failed").build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
		
	}
}
