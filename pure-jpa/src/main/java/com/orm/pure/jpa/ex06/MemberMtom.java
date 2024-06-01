package com.orm.pure.jpa.ex06;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
    일대다 양방향
 */
@NoArgsConstructor
@Entity
@Table(name="MEMBER_MTOM")
public class MemberMtom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID")
    private Long id;
    private String name;


    @ManyToMany
    @JoinTable(name="MEMBER_PRODUCT",
            joinColumns = @JoinColumn(name = "MEMBER_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID")
    )
    private List<ProductMtoM> products = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductMtoM> getProducts() {
        return products;
    }

    public void setProducts(List<ProductMtoM> products) {
        this.products = products;
    }
}
