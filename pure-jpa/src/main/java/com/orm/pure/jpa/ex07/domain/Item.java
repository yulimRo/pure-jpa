package com.orm.pure.jpa.ex07.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/*
 * 상속관계의 상위객체
 */

//@Entity
@Inheritance(strategy = InheritanceType.JOINED) //상속매핑전략
@DiscriminatorColumn(name="DTYPE") //구분타입칼럼명
@AttributeOverride(name="createDate", column = @Column(name="regDate")) //상속정보 변경
public abstract class Item extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "item_id")
	private Long id;
	private String name;

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


}
