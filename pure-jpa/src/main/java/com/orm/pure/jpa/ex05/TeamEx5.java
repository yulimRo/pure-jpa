package com.orm.pure.jpa.ex05;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "TeamEx5")
public class TeamEx5 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team")
    private List<MemberEx5> members;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<MemberEx5> getMembers() {
        return members;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMembers(List<MemberEx5> members) {
        this.members = members;
    }

    /*
     * 양방향 연관관계로 연관 엔티티 관련 로직 추가
     * @param member
     */
    public void addMember(MemberEx5 member){
        this.members.add(member);
        if(member.getTeam() != null){
            member.setTeam(this);
        }
    }
}
