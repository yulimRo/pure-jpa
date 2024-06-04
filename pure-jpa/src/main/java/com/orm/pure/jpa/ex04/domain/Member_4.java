package com.orm.pure.jpa.ex04.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 기본 회원 엔티티 (MEMBER 테이블과 매핑)
 */
@Entity
@Table(name = "MEMBER_4")
public class Member_4 {

	@Id
	@Column(name = "member_id")
	private Long id;

	@Column(name = "name", nullable = false, length = 20)
	private String username;

	@Column(name = "age")
	private int age;

	/*
	 * Enum 매핑
	 */
	@Enumerated
	private RoleType roleType;

	/*
	 *  clob, blob 타입 매핑
	 */
	@Lob
	private String description;

	/*
	 * Date, Time, Timestamp 타입 매핑
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

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

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
