package com.orm.pure.jpa.ex08.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity(name="Member8")
//@NamedQuery(name="Member8.findByName", query="select m from Member8 m where name=:username")
//@SequenceGenerator(
//		name= "MEM_SEQ_GE",
//		sequenceName = "MEM_SEQ")
public class Member8 {

	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEM_SEQ_GE")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "member_id")
	private Long id;

	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="team_id")
	private Team8 team;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	private List<Order8> orders = new ArrayList<>();

	private int age;
	
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

	public Team8 getTeam() {
		return team;
	}

	public void setTeam(Team8 team) {
		this.team = team;
	}

	public List<Order8> getOrders() {
		return orders;
	}

	public void setOrders(List<Order8> orders) {
		this.orders = orders;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	

}
