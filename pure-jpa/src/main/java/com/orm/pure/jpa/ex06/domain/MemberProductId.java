package com.orm.pure.jpa.ex06.domain;

import java.io.Serializable;
import java.util.Objects;

import com.orm.pure.jpa.ex06.domain.oneway.Product6_6;

import lombok.NoArgsConstructor;

/**
 * MemberProduct의 복합키 클래스
 */
@NoArgsConstructor
public class MemberProductId implements Serializable {

	private Member_6_6 member;
	private Product6_6 product;

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
		MemberProductId other = (MemberProductId) obj;
		return Objects.equals(member, other.member) && Objects.equals(product, other.product);
	}

}
