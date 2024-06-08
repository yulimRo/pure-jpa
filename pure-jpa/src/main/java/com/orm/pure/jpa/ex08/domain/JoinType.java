package com.orm.pure.jpa.ex08.domain;
/*
 * criteria 조인을 위한 Enum
 * JPA 구현체나 데이터	베이스에 따라 지원하지 않을 수 있다. 
 */
public enum JoinType {
	INNER,
	LEFT,
	RIGHT
}
