package com.orm.pure.jpa.ex06;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="PRODUCT_MTM_2")
public class ProductMtoM_2 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRODUCT_ID")
    private Long id;
    private String name;

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

}
