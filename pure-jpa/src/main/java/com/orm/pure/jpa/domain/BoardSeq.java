package com.orm.pure.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Builder;

@Entity
@Table(name= "BOARD_SEQ")
@SequenceGenerator(name = "BOARD_SEQ_GENERATOR",
	sequenceName = "BOARD_SEQ_SEQ",
	initialValue = 1, allocationSize = 1)
public class BoardSeq {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GENERATOR")
	private Long id;
	
	@Column(name = "DATA")
	private String content;

	@Builder
	public BoardSeq(String content) {
		this.content = content;
	}
	
}
