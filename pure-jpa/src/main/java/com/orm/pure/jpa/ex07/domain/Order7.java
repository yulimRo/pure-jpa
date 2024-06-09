package com.orm.pure.jpa.ex07.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

//@Entity
public class Order7 {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id") // fk명
	private Member7 member;

	@ManyToOne
	@JoinColumn(name = "product_id") // fk명
	private Product7 product;

	private Integer orderAmount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Member7 getMember() {
		return member;
	}

	public void setMember(Member7 member) {
		this.member = member;
	}

	public Product7 getProduct() {
		return product;
	}

	public void setProduct(Product7 product) {
		this.product = product;
	}

	public Integer getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}

}
