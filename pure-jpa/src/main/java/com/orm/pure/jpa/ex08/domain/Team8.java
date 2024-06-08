package com.orm.pure.jpa.ex08.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
//@SequenceGenerator(
//		name= "TEM_SEQ_GE",
//		sequenceName = "TEM_SEQ")
public class Team8 {

	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEM_SEQ_GE")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "team_id")
	private Long id;

	private String name;

	@OneToMany(mappedBy = "team")
	private List<Member8> members = new ArrayList<>();

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


	public List<Member8> getMembers() {
		return members;
	}

	public void setMembers(List<Member8> members) {
		this.members = members;
	}

}
