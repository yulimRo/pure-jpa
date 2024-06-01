package com.orm.pure.jpa.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.NoArgsConstructor;


@Entity
@Table(name="MEMBER")
@NoArgsConstructor
@Access(AccessType.FIELD)
public class Member {

	@Id
	@Column(name= "ID")
	private String id;
	
	@Column(name = "NAME")
	private String userName;
	
	private Integer age;

	public String getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public Integer getAge() {
		return age;
	}
	
	@Builder
	public Member(String id, String userName, Integer age) {
		this.id = id;
		this.userName = userName;
		this.age = age;
		
	}
	
	public void update(String username, Integer age) {
		this.userName = username;
		this.age = age;
	}
	
}
