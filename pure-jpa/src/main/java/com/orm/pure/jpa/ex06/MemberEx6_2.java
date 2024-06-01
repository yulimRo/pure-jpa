package com.orm.pure.jpa.ex06;

import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
    일대다 양방향
 */
@NoArgsConstructor
@Entity
@Table(name="MEMBER_EX6_2")
public class MemberEx6_2 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToOne
    @JoinColumn(name = "LOCK_ID")
    private Lock lock;

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

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }
}
