package com.example.demo.contoroller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController //http와 관련된 코드 및 요청/응답 매핑 스프링이 처리해줌
@RequestMapping("test")
public class TestController {
	
/*	@GetMapping()
	public String testController() {
		return "HELLO none";
	}
*/	
	@GetMapping("/{id}")
	public String testController(@PathVariable(required = true) int id) {
		return "HELLO iD" + id;
	}
}

