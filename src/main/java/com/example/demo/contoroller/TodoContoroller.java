package com.example.demo.contoroller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.example.demo.dto.TodoDTO;
import com.example.demo.model.ResponseDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("todo")
public class TodoContoroller {
	//testTodo메서드 작성하기
	@Autowired
	private TodoService service;
	
	@GetMapping("/test")
	public ResponseEntity<?> testTodo(){
		String str = service.testService();
		List<String> list = new ArrayList();
		list.add(str);
		ResponseDTO response = ResponseDTO.<String>builder().data(list).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto){
		try {
			log.info("ResponseEntity try 진입");
			
			String temporaryUserId = "temporary-user";
			
			//1 TodoEntity로 변환
			TodoEntity entity = TodoDTO.toEntity(dto);
			
			//2 id를 null로 초기화 시킴, 생성당시에는 id가 존재하지 않아야 하기 때문
			entity.setId(null);
			
			//3 임시사용자 아이디를 설정해줌- 인증 인가 기능 설정 후에 기능 추가할 예정
			entity.setUserId(temporaryUserId);
			
			//4 서비스를 이용해 Todo 엔티티를 생성한다.
			List<TodoEntity> entities = service.create(entity);
			
			//5 자바 스트림을 이용해 리턴된 엔티티 리스트를 todoDTO 리스트로 변환함
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).toList();
			
			//6 변환된 TodoDTO리스트를 이용해 ResponseDTO를 초기화한다.
			ResponseDTO response =  ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			//7 ResponseDTO를 리턴한다.
			return ResponseEntity.ok().body(response);
		}catch (Exception e) {
			//8 혹시 예외가 있는 경우 dto 대신 error에 메시지를 넣어 리턴함
			String error = e.getMessage();
			ResponseDTO response = ResponseDTO.builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	
	@GetMapping
	public ResponseEntity<?> retrieveTodoList(){
		String temporaryUserId = "temporary-user"; //tmep user id
		
		// 1 서비스 메서드의 retrieve() 메서드를 사용해 Todo 리스트를 가져온다
		List<TodoEntity> entities = service.retrieve(temporaryUserId);
		
		// 2 자바 스트림을 이용해 리턴된 엔티티 리스트를 todoDTO 리스트로 변환한다.
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		// 3 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
		ResponseDTO response = ResponseDTO.builder().data(dtos).build();
		
		// 4 ResponseDTO를 리턴한다.
		return  ResponseEntity.ok().body(response);
		
	}
	
	@PutMapping
	public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto){
		
		log.info("!!!!!!!!!!!!!!!!!!!!!!! 업데이트로 진입 !!!!!!!!!!!!!!!");
		
		String temporaryUserId = "temporary-user"; //temp-user-id
		
		//1 dto를 entity로 변환한다.
		TodoEntity entity = TodoDTO.toEntity(dto);
		log.info("!!!!!!!!!!!!! entity >>> " + entity);
		//2 id를 temporaryUserId로 초기화한다.
		entity.setUserId(temporaryUserId);
		
		//3 서비스를 이용해 entity를 업데이트 함
		List<TodoEntity> entitise = service.update(entity);
		log.info("!!!!!!!!!!!!! entitise >>> " + entitise);
		//4 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
		List<TodoDTO> dtos = entitise.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		//5 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화함
		ResponseDTO response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto){
		try {
			
			String tempraryUserId = "temporary-user"; //temp-user-id
			
			//1 todoEntity로 변환
			TodoEntity entity = TodoDTO.toEntity(dto);
			
			//2 임시 사용자 아이디를 설정해둔다. 
			entity.setUserId(tempraryUserId);
			
			//3 서비스를 이용해 entity를 삭제
			List<TodoEntity> entities = service.delete(entity);
			
			//4 자바스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO리스트로 변환
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			//5 변환된 todoDTO 리스트를 이용해 ResponseDTO를 초기화
			ResponseDTO response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			//6 responseDTO를 리턴한다.
			return ResponseEntity.ok().body(response);
		}catch(Exception e) {
			
			//혹시 예외가 있는 경우 dto 대신 error 메시지를 넣어 리턴함
			String error = e.getMessage();
			ResponseDTO response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
}