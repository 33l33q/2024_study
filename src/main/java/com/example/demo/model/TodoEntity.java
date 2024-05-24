package com.example.demo.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder //오브젝트 생성을 위한 디자인 패턴 중 하나, builder 클래스 개발 않고도 빌드 패턴을 사용해 생성 가능
@NoArgsConstructor //매개변수 없는 생성자를 구현해줌
@AllArgsConstructor //모든 멤버변수를 매개변수로 받는 생성자를 구현해줌
@Data //클래스 멤버 변수의 GETTER SETTER를 구현해줌
@Entity //이름 부여하고 싶으면 @Entity("이름") 부여하면됨
@Table(name = "Todo") //테이블 이름 지정, 명시하지 않으면 entity이름을 테이블 이름으로 간주함

public class TodoEntity {
	@Id //기본키가 될 필드 위에 지정
	@GeneratedValue(generator = "system-uuid") //id필드를 자동으로 생성해주는 어노테이션
	@GenericGenerator(name="system-uuid", strategy="uuid") //id필드를 자동으로 생성
	private String id;//이 오브젝트의 아이디
	private String userId; //이 오브젝트를 생성한 사용자의 아이디
	private String title; //TODO의 타이틀(EX:운동하기)
	private boolean done; //완료여부 true

}
