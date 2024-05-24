package com.example.demo.dto;

import com.example.demo.model.TodoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {

	private String id;
	private String title;
	private boolean done;
	
	public TodoDTO(final TodoEntity entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.done = entity.isDone();
		
	}

	//HTTP응답 반환 시 비즈니스 로직을 캡슐화하거나 추가적인 정보를 함께 반환하려고 DTO를 사용
	//따라서 todoDTO를 요청바디로 넘겨받고 이를 todoEntity로 변환해 저장해야함
	// 또한 TodoService의 create가 리턴하는 TodoEntity를 TodoDTO로 변환해 리턴해야함
	public static TodoEntity toEntity(final TodoDTO dto) {
		return TodoEntity.builder()
				.id(dto.getId())
				.title(dto.getTitle())
				.done(dto.isDone())
				.build();
	}
}
