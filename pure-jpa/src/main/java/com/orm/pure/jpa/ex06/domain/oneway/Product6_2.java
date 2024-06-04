package com.orm.pure.jpa.ex06.domain.oneway;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.orm.pure.jpa.ex06.domain.Member_6_5;

/*
 * Member 엔티티와 N:N 양방향 매핑
 */
@Entity
@Table(name="PRODUCT_6_2")
public class Product6_2 {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_id")
	private Long id;

	private String name;

	@ManyToMany(mappedBy = "products") //역방향 추가( 연관관계의주인은 Member_6_5)
	private List<Member_6_5> members = new ArrayList<>();

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

	public List<Member_6_5> getMembers() {
		return members;
	}

	public void setMembers(List<Member_6_5> members) {
		this.members = members;
	}

}
