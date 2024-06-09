package com.orm.pure.jpa.ex06.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.orm.pure.jpa.ex06.domain.oneway.Team_6_0_1;

/**
 * Team 객체와의 1:N 양방향 매핑
 */
//@Entity
@Table(name = "MEMBER_6_0_1")
public class Member6_0_1 {

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String username;

	@Column(name = "age")
	private int age;
	
	@ManyToOne
	@JoinColumn(name = "team_id", insertable = false, updatable = false)
	private Team_6_0_1 team;
	
	
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

	public Team_6_0_1 getTeam() {
		return team;
	}

	public void setTeam(Team_6_0_1 team) {
		this.team = team;
	}
	

}

