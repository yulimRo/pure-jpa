package com.orm.pure.jpa.ex06.domain.oneway;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.orm.pure.jpa.ex06.domain.Member_6_3_2;

/**
 * Meber 엔티티와 1:1 매핑 양방향 (대상테이블 -외래키)
 * 
 */
//@Entity
@Table(name = "LOCKER_3")
public class Locker_3 {

	@Id
	@Column(name = "locker_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	
	@OneToOne
	@JoinColumn(name="member_id")
	private Member_6_3_2 member;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Member_6_3_2 getMember() {
		return member;
	}

	public void setMember(Member_6_3_2 member) {
		this.member = member;
	}

}
