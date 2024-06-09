package com.orm.pure.jpa.ex06.domain.twoway;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.orm.pure.jpa.ex06.domain.Member_6_2;

/**
 * Meber 엔티티의 다대일 양방향
 * 
 */
//@Entity
@Table(name = "TEAM_6_2")
public class Team_6_2 {

	@Id
	@Column(name = "team_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	@OneToMany(mappedBy = "team")
	private List<Member_6_2> member = new ArrayList<Member_6_2>();

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

	public List<Member_6_2> getMember() {
		return member;
	}

	public void setMember(List<Member_6_2> member) {
		this.member = member;
	}
	
	//편의성 메서드
	public void addMember(Member_6_2 member) {
		if(member.getTeam() != null) {
			member.setTeam(this);
		} else{
			this.getMember().add(member);
		}
	}

}
