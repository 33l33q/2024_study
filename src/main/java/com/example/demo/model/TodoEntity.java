package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder //오브젝트 생성을 위한 디자인 패턴 중 하나, builder 클래스 개발 않고도 빌드 패턴을 사용해 생성 가능
@NoArgsConstructor //매개변수 없는 생성자를 구현해줌
@AllArgsConstructor //모든 멤버변수를 매개변수로 받는 생성자를 구현해줌
@Data //클래스 멤버 변수의 GETTER SETTER를 구현해줌
public class TodoEntity {
	private boolean done; //완료여부 true
	private String id;//이 오브젝트의 아이디
	private String userId; //이 오브젝트를 생성한 사용자의 아이디
	private String title; //TODO의 타이틀(EX:운동하기)

}
