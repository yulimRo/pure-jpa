package com.orm.pure.jpa.ex06.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.orm.pure.jpa.ex06.domain.oneway.Product6_6;

/**
 * Mem - Product 연결 엔티티 (회원/상품과 식별관계) 회원FK, 상품 FK 가 해당 테이블의 PK 이기 때문
 */
//@Entity
@Table(name = "MEM_PRD2")
public class MemberProduct2 {

	@EmbeddedId
	private MemberProductId2 orderId;

	@MapsId("memberId")
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member_6_8 member;

	@MapsId("productId")
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product6_6 product;

	private Integer orderAmount;

	public MemberProductId2 getOrderId() {
		return orderId;
	}

	public void setOrderId(MemberProductId2 orderId) {
		this.orderId = orderId;
	}

	public Member_6_8 getMember() {
		return member;
	}

	public void setMember(Member_6_8 member) {
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
