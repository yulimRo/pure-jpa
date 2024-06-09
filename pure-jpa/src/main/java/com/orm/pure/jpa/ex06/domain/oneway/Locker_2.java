package com.orm.pure.jpa.ex06.domain.oneway;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.orm.pure.jpa.ex06.domain.Member_6_3_1;

/**
 * Meber 엔티티와 1:1 매핑 양방향 (대상테이블)
 * 
 */
//@Entity
@Table(name = "LOCKER_2")
public class Locker_2 {

	@Id
	@Column(name = "locker_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	
	@OneToOne(mappedBy = "locker")
	private Member_6_3_1 member;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Member_6_3_1 getMember() {
		return member;
	}

	public void setMember(Member_6_3_1 member) {
		this.member = member;
	}

}
