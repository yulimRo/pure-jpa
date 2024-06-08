package com.orm.pure.jpa.ex07.domain;

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
import javax.persistence.OneToMany;

@Entity
public class Member7 {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "member_id")
	private Long id;

	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="team_id")
	private Team7 team;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	private List<Order7> orders = new ArrayList<>();

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

	public Team7 getTeam() {
		return team;
	}

	public void setTeam(Team7 team) {
		this.team = team;
	}

	public List<Order7> getOrders() {
		return orders;
	}

	public void setOrders(List<Order7> orders) {
		this.orders = orders;
	}

}
