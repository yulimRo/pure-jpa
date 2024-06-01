package com.orm.pure.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
//
//@Entity
//@Table(name= "BOARD")
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "DATA")
	private String content;

	@Builder
	public Board(String content) {
		this.content = content;
	}
	
}
