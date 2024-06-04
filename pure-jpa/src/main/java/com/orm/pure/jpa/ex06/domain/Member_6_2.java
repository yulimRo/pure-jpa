package com.orm.pure.jpa.ex06.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.orm.pure.jpa.ex06.domain.twoway.Team_6_2;

/**
 * Team 객체와 N:1 양방향 매핑
 * 기본 회원 엔티티 (MEMBER 테이블과 매핑)
 */
@Entity
@Table(name = "MEMBER_6_2")
public class Member_6_2 {

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", nullable = false, length = 20)
	private String username;

	@Column(name = "age")
	private int age;
	
	@ManyToOne
	@JoinColumn(name="team_id") //해당 테이블의 외래키명
	private Team_6_2 team;

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

	public Team_6_2 getTeam() {
		return team;
	}

	//편의성 메서드
	public void setTeam(Team_6_2 team) {
		
		if(this.team != null ) {
			this.team.getMember().remove(this);
		}else {
			this.team = team;
		}
	}
	
	

}
