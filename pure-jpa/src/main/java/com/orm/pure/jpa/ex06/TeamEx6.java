package com.orm.pure.jpa.ex06;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/*
    일대다 양방향
 */
@NoArgsConstructor
@Entity
@Table(name = "TEAMEX6")
public class TeamEx6 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany
    @JoinColumn(name = "team_id")
    private List<MemberEx6> members;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<MemberEx6> getMembers() {
        return members;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMembers(List<MemberEx6> members) {
        this.members = members;
    }

    /*
     * 양방향 연관관계로 연관 엔티티 관련 로직 추가
     * @param member
     */
    public void addMember(MemberEx6 member){
        this.members.add(member);
        if(member.getTeam() != null){
            member.setTeam(this);
        }
    }
}
