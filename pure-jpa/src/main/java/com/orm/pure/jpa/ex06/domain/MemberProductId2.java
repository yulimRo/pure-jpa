package com.orm.pure.jpa.ex06.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.orm.pure.jpa.ex06.domain.oneway.Product6_6;

import lombok.NoArgsConstructor;

/**
 * MemberProduct의 복합키 클래스
 */
@NoArgsConstructor
@Embeddable
public class MemberProductId2 implements Serializable {

	@ManyToOne
	@JoinColumn(name="member_id")
	private Member_6_8 member;
	

	@ManyToOne
	@JoinColumn(name="product_id")
	private Product6_6 product;

	@Override
	public int hashCode() {
		return Objects.hash(member, product);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberProductId2 other = (MemberProductId2) obj;
		return Objects.equals(member, other.member) && Objects.equals(product, other.product);
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

}
