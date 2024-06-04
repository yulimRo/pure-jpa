package com.orm.pure.jpa.ex01.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 기본 회원 엔티티 (MEMBER 테이블과 매핑)
 */
@Entity
@Table(name="MEMBER")
public class Member {

	@Id
	@Column(name = "member_id")
	private Long id;

	@Column(name = "name" , nullable = false, length = 20)
	private String username;
	
	@Column(name="age")
	private int age;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
