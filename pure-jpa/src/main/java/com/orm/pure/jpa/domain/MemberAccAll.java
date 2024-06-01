package com.orm.pure.jpa.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="MEMBER_ACC_ALL")
@NoArgsConstructor
public class MemberAccAll {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Transient
	private String firstName;
	
	@Transient
	private String lastName;
	
    @Setter
    private String fullName;
	
	@Access(AccessType.PROPERTY)
	public String getFullName() {
		return firstName + lastName;
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void changeFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void changeLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
