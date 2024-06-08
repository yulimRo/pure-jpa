package com.orm.pure.jpa.ex06.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.orm.pure.jpa.ex06.domain.oneway.Product6_6;

/**
 * Mem - Product 연결 엔티티 (회원/상품과 비식별관계)
 */
@Entity
@Table(name="MEM_PRD")
public class MemberProduct1_2 {
	
	@Id
	@Column(name="order_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name="member_id")
	private Member_6_6 member;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product6_6 product;
	
	private Integer orderAmount;

	public Member_6_6 getMember() {
		return member;
	}

	public void setMember(Member_6_6 member) {
		this.member = member;
	}

	public Product6_6 getProduct() {
		return product;
	}

	public void setProduct(Product6_6 product) {
		this.product = product;
	}

	public Integer getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	

}
