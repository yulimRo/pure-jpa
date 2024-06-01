package com.orm.pure.jpa.ex06;

import javax.persistence.*;

@Entity
@Table(name="ORDER")
public class Order {

    @Id
    @Column(name = "ORDER_ID")
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private MemberMtom_2 member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private ProductMtoM_2 product;

    private Integer orderCnt;

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

    public Integer getOrderCnt() {
        return orderCnt;
    }

    public void setOrderCnt(Integer orderCnt) {
        this.orderCnt = orderCnt;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}
