package com.orm.pure.jpa.ex08.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
//@SequenceGenerator(
//		name= "PRD_SEQ_GE",
//		sequenceName = "PRD_SEQ")
public class Product8 {

	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRD_SEQ_GE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long id;
	private String name;

	private int stockAmount;

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

	public int getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(int stockAmount) {
		this.stockAmount = stockAmount;
	}
}
