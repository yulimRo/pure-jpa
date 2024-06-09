package com.orm.pure.jpa.ex07.domain;

import javax.persistence.*;

//@Entity
@SecondaryTable(name="BOARD_DETAIL", pkJoinColumns = {
        @PrimaryKeyJoinColumn(name="BOARD_DETAIL_ID")}) //매핑할 다른 테이블의기본 키 컬럼 속성

public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="BOARD_ID")
    private Long id;

    private String name;

    @Column(table="BOARD_DETAIL") //BOARD_DETAIL 테이블의 컬럼에 매핑
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
