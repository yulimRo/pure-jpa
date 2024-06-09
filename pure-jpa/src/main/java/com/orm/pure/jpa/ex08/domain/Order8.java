package com.orm.pure.jpa.ex08.domain;

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
import javax.persistence.SequenceGenerator;

@Entity
//@SequenceGenerator(
//		name= "ORD_SEQ_GE",
//		sequenceName = "ORD_SEQ")
public class Order8 {

	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORD_SEQ_GE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id") // fk명
	private Member8 member;

	@ManyToOne
	@JoinColumn(name = "product_id") // fk명
	private Product8 product;

	private Integer orderAmount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Member8 getMember() {
		return member;
	}

	public void setMember(Member8 member) {
		this.member = member;
	}

	public Product8 getProduct() {
		return product;
	}

	public void setProduct(Product8 product) {
		this.product = product;
	}

	public Integer getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}

}
