package com.orm.pure.jpa.ex08.dto;

/**
 * JPQL 반환타입 1. 생성자 필수
 */
public class MemberDto {

	private Long id;
	private String name;
	private int age;

	public Long getId() {
		return id;
	}

	public MemberDto(Long id, String name, int age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
