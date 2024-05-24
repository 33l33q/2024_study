package com.example.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.TodoEntity;

import lombok.extern.slf4j.Slf4j;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
//JpaRepository<T, ID>
// 첫번째 매개변수 T는 테이블에 매핑될 엔티티 클래스, ID는 엔티티의 기본 키타입
	
	//기본적인 쿼리가 아닌 경우 어떻게 사용할 것인가?
	List<TodoEntity> findByUserId(String userId);

	/*이 메서드를 작성하면 
	 * 스프링데이터 JPA가 메서드 이름을 파싱해서 
	 * SELECT * FROM TodoRepository WHERE userId = '{userId}'와 같은 쿼리를 작성해줌
	 * 메서드 이름은 '쿼리' 매개변수는 'where문에 들어갈 값'을 의미함
	 */
	
	//더 복잡한 쿼리는 @Query를 통해서 직접 지정할 수 있음
	// ?1은 매서드의 매개변수 순서 위치 
	//@Query("select * from Todo t where t.userId = ?1")
	//List<TodoEntity> findByUserId(String userId);
}
