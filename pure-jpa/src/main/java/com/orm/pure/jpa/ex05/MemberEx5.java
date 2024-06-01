package com.orm.pure.jpa.ex05;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name="MEMBER_EX5")
public class MemberEx5 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name="team_id")
    private TeamEx5 team;


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

    public TeamEx5 getTeam() {
        return team;
    }

    /*
        team 과 Member가 양방향 관계이므로, 두 참조에 값을 세팅 해줘야하기 때문에
        편의성 메서드로 커스텀
     */
    public void setTeam(TeamEx5 team) {
        if(this.team != null){
            team.getMembers().remove(this);
        }

        this.team = team;
        team.getMembers().add(this);
    }
}
