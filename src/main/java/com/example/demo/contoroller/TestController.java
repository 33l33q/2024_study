package com.example.demo.contoroller;

import com.example.demo.dto.TestRequestBodyDTO;
import com.example.demo.model.ResponseDTO;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController //http와 관련된 코드 및 요청/응답 매핑 스프링이 처리해줌
@RequestMapping("test")
public class TestController {
	
	@PostMapping("/testRequestBody")
	public String testControllerRequsetBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
		
		return "안녕~~" + testRequestBodyDTO.getId() + " 메세지 : " + testRequestBodyDTO.getMessage();
	}
	
	@GetMapping()
	public String testController() {
		return "HELLO none";
	}
	
	@GetMapping("/{id}")
	public String testController(@PathVariable(required = false) int id) {
		return "HELLO iD" + id;
	}
	
	@GetMapping("/testRequsetParam")
	public String testControllerRequsetParam(@RequestParam(required = false) String id) {
		return "Hello World ! ID " + id;
		//int는 null을 받아올 수 없기 때문에 required = false을 사용해도 의미 없다. String만 가능함 혹은 INTEGER로 받아오는 후처리 필요
	}

	@GetMapping("/testResponseBody")
	public ResponseDTO testControllerResponseBody(){
		List<String> list =  new ArrayList<>();
		list.add("안녕~~ 나는 ResponseDTO야~~");
		ResponseDTO response = ResponseDTO.<String>builder().data(list).build();
		return response;
	}
	
	@GetMapping("/testResponseEntity")
	public ResponseEntity<?> testControllerResponseEntity(){
		List<String> list = new ArrayList<>();
		list.add("안녕~~ 난 ResponseEntity야 그리고 난 got 400" );
		ResponseDTO response = ResponseDTO.<String>builder().data(list).build();
		//http status를 400으로 설정
		return ResponseEntity.badRequest().body(response);
	}
}

