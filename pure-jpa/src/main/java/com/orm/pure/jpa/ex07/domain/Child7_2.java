package com.orm.pure.jpa.ex07.domain;

import javax.persistence.*;

//@Entity
public class Child7_2 {

    @Id
    @Column(name = "child_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent7_2 parent;

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

    public Parent7_2 getParent() {
        return parent;
    }

    public void setParent(Parent7_2 parent) {
        this.parent = parent;
    }
}
