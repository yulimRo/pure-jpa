package com.orm.pure.jpa.ex06.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *  Product 엔티티 다대다 매핑(연결 엔티 사용)
 */
@Entity
@Table(name = "MEMBER_6_7")
public class Member_6_7 {

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String username;
	
	//연결엔티티
	@OneToMany(mappedBy = "member")
	private List<MemberProduct> memberProduct;

	@Column(name = "age")
	private int age;

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

	public List<MemberProduct> getMemberProduct() {
		return memberProduct;
	}

	public void setMemberProduct(List<MemberProduct> memberProduct) {
		this.memberProduct = memberProduct;
	}
	

}
