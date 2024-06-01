package com.orm.pure.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Builder;

@Entity
@Table(name= "BOARD_TABLE_SEQ")
@TableGenerator(name = "BOARD_TABLE_SEQ_GENERATOR",
table = "MY_SEQUENCE",
pkColumnValue = "BOARD_TABLE_SEQ", allocationSize = 1)
public class BoardTableSeq {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "BOARD_TABLE_SEQ_GENERATOR")
	private Long id;
	
	@Column(name = "DATA")
	private String content;

	@Builder
	public BoardTableSeq(String content) {
		this.content = content;
	}
	
}
