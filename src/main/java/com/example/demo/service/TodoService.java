package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TodoService {
	
	@Autowired
	private TodoRepository repository;
	
	public String testService() {
		//TodoEntity 생성
		TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
		//TodoEntity 저장
		repository.save(entity);
		//TodoEntity 검색
		TodoEntity savedEntity = repository.findById(entity.getId()).get();
		
		return savedEntity.getTitle();
	}
	
	
	public List<TodoEntity> create(final TodoEntity entity){
	
		validate(entity);
		
		repository.save(entity);
		
		log.info("!!!!!!!!!!!!!!!!!!!!!!! Entity Id : {} is saved", entity.getId());
		
		return repository.findByUserId(entity.getUserId()); 
	}
	
	//재사용 가능성있어 리팩토링함
	private void validate(final TodoEntity entity) {

		if(entity == null) {
			log.warn( "Entity cannot be null");
			throw new RuntimeException("Entity cannot be null");
		}
		
		if(entity.getUserId() == null) {
			log.warn("Unknown user");
			throw new RuntimeException("Unknown user");
		}
	}
	
	public List<TodoEntity> retrieve(final String userId){
		return repository.findByUserId(userId);
	}
	
	public List<TodoEntity> update(final TodoEntity entity){
		
		//1 저장할 엔티티가 유효한지 확인하기
		validate(entity);
		
		//2 넘겨받은 엔티티id를 이용해 TodoEntity를 가져오기 ==> 존재하지 않는 엔티티는 가져올 수 없기 때문
		final Optional<TodoEntity> original = repository.findById(entity.getId());
		
		original.ifPresent(todo -> {
			
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			
			//4 db에 새 값을 저장한다.
			repository.save(todo);
		});
		
		// retrieve todo에서 만든 메서드를 이용해 사용자의 모든 todo 리스트를 리턴함
		return retrieve(entity.getUserId());
	}
	
	public List<TodoEntity> delete(final TodoEntity entity){
		// 1 저장할 엔티티가 유효한지 확인
		validate(entity);
		
		try {
		//2 엔티티를 삭제한다.
			repository.delete(entity);
		}catch(Exception e) {
		//3 예외 발생 시 id와 exception을 로깅한다.
			log.error("error deleting entity", entity.getId(), e);
		//4 컨트롤러로 예외처리를 보낸다. 데이터베이스 내부 로직을 캡슐화하려면 e를 리턴하지않고 새 예외 오브젝트를 리턴한다.
			throw new RuntimeException("error deleting entity " + entity.getId());
		}
		//5 새 todo 리스트를 가져와 리턴한다.
		return retrieve(entity.getUserId());
	}
}