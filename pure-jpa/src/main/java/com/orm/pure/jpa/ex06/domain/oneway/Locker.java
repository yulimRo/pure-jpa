package com.orm.pure.jpa.ex06.domain.oneway;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Meber 엔티티와 1:1 매핑 단방향 (대상테이블)
 * 
 */
@Entity
@Table(name = "LOCKER")
public class Locker {

	@Id
	@Column(name="locker_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
