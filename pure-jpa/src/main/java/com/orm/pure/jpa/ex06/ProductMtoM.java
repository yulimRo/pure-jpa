package com.orm.pure.jpa.ex06;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="PRODUCT")
public class ProductMtoM {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRODUCT_ID")
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "products")
    private List<MemberMtom> members;

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

    public List<MemberMtom> getMembers() {
        return members;
    }

    public void setMembers(List<MemberMtom> members) {
        this.members = members;
    }
}
