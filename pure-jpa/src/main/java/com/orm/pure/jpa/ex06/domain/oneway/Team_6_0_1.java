package com.orm.pure.jpa.ex06.domain.oneway;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.orm.pure.jpa.ex06.domain.Member6_0;
import com.orm.pure.jpa.ex06.domain.Member6_0_1;

/**
 * Meber 엔티티와의 1:N 양방향 매핑
 * 
 */
//@Entity
@Table(name = "TEAM_6_0_1")
public class Team_6_0_1 {

	@Id
	@Column(name = "team_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	@OneToMany
	// MEMEBER 테이블의 tEAM_ID(FK), 해당 어노테이션 미명시할 경우 조인 테이블 전략을 기본으로 사용해서 매핑
	@JoinColumn(name = "team_id")
	private List<Member6_0_1> member = new ArrayList<Member6_0_1>();

	public Long getId() {
		return id;
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

	public List<Member6_0_1> getMember() {
		return member;
	}

	public void setMember(List<Member6_0_1> member) {
		this.member = member;
	}

}
