package com.orm.pure.jpa.ex06.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.orm.pure.jpa.ex06.domain.oneway.Product;
import com.orm.pure.jpa.ex06.domain.oneway.Product6_2;

/**
 * Prodcut6_2 와 N:N 양방향
 */
//@Entity
@Table(name = "MEMBER_6_5")
public class Member_6_5 {

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String username;

	@Column(name = "age")
	private int age;

	@ManyToMany
	//자동으로 연결테이블 생성
	@JoinTable(name = "MEMBER_PRODUCT2", 
		joinColumns = @JoinColumn(name = "member_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
	private List<Product6_2> products = new ArrayList<>();

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

	public List<Product6_2> getProducts() {
		return products;
	}

	public void setProducts(List<Product6_2> products) {
		this.products = products;
	}

}
