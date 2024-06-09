package com.orm.pure.jpa.ex06.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.orm.pure.jpa.ex06.domain.oneway.Locker;

/**
 *  Locker 와 1:1 단방향 매핑 객체  (주테이블 - 외래키 관리)
 */
//@Entity
@Table(name = "MEMBER_6_3")
public class Member_6_3 {

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", nullable = false, length = 20)
	private String username;

	@Column(name = "age")
	private int age;

	@OneToOne
	@JoinColumn(name = "locker_id")
	private Locker locker;

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

	public Locker getLocker() {
		return locker;
	}

	public void setLocker(Locker locker) {
		this.locker = locker;
	}

}
