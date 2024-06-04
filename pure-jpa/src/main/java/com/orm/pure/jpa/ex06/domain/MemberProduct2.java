package com.orm.pure.jpa.ex06.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Mem - Product 연결 엔티티 (회원/상품과 식별관계) 회원FK, 상품 FK 가 해당 테이블의 PK 이기 때문
 */
@Entity
@Table(name = "MEM_PRD2")
public class MemberProduct2 {

	@EmbeddedId
	private MemberProductId2 id;

	private Integer orderAmount;

	public MemberProductId2 getId() {
		return id;
	}

	public void setId(MemberProductId2 id) {
		this.id = id;
	}

	public Integer getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}

}
