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
@Table(name="MEMBER_ACC_PROP")
@NoArgsConstructor
@Access(AccessType.PROPERTY)
public class MemberAccProp {

	private String id;
	
	private String userName;
	
	private Integer age;

	@Id
	public String getId() {
		return id;
	}

	@Column(name = "NAME")
	public String getUserName() {
		return userName;
	}

	public Integer getAge() {
		return age;
	}
	
	@Builder
	public MemberAccProp(String id, String userName, Integer age) {
		this.id = id;
		this.userName = userName;
		this.age = age;
		
	}
	
	public void update(String username, Integer age) {
		this.userName = username;
		this.age = age;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	
	
}
