package com.orm.pure.jpa.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * 기존 Member 클래스에서 필드 추가
 */
@Entity
@Table(name="MEMBER_EX3")
@NoArgsConstructor
public class MemberEx3 {

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

	@Enumerated(EnumType.STRING)
	private RoleType roleType;
	
	@Temporal(TemporalType.DATE)
	private Date lastModifiedDate;
	
	@Lob
	private String description;

	public RoleType getRoleType() {
		return roleType;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public String getDescription() {
		return description;
	}

	@Builder
	public MemberEx3(String userName, Integer age, RoleType roleType, Date lastModifiedDate,
			String description) {
		this.userName = userName;
		this.age = age;
		this.roleType = roleType;
		this.lastModifiedDate = lastModifiedDate;
		this.description = description;
	}
	
	
	
}
