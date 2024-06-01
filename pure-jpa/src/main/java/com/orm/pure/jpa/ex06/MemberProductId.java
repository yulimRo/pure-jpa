package com.orm.pure.jpa.ex06;


import com.orm.pure.jpa.domain.Member;

import java.io.Serializable;
import java.util.Objects;

public class MemberProductId implements Serializable {

    private MemberMtom_2 member;

    private ProductMtoM_2 product;

    public MemberMtom_2 getMember() {
        return member;
    }

    public void setMember(MemberMtom_2 member) {
        this.member = member;
    }

    public ProductMtoM_2 getProduct() {
        return product;
    }

    public void setProduct(ProductMtoM_2 product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberProductId that = (MemberProductId) o;

        if (!Objects.equals(member, that.member)) return false;
        return Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        int result = member != null ? member.hashCode() : 0;
        result = 31 * result + (product != null ? product.hashCode() : 0);
        return result;
    }
}
