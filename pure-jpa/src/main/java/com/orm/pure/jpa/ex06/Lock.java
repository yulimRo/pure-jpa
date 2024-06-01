package com.orm.pure.jpa.ex06;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "LOCKER")
public class Lock {

    @Id
    @Column(name="LOCK_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToOne(mappedBy = "lock")
    private MemberEx6_2 member;

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

    public MemberEx6_2 getMember() {
        return member;
    }

    public void setMember(MemberEx6_2 member) {
        this.member = member;
    }
}
