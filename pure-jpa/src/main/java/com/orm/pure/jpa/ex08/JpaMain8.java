package com.orm.pure.jpa.ex08;


import com.mysema.query.jpa.impl.JPAQuery;
import com.orm.pure.jpa.ex07.domain.Album;
import com.orm.pure.jpa.ex08.domain.*;
import com.orm.pure.jpa.ex08.domain.func.DefaultFunc;
import com.orm.pure.jpa.ex08.domain.func.JPQLFunc;
import com.orm.pure.jpa.ex08.domain.func.QueryDSLFunc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.*;
import java.util.List;

import static com.orm.pure.jpa.ex07.JpaMain7.saveData;
//import static com.orm.pure.jpa.ex08.domain.QTeam8.team8; //기본 인스턴스 사용방식 2
import static com.orm.pure.jpa.ex08.domain.func.CriteriaFunc.testCriteria;
import static com.orm.pure.jpa.ex08.domain.func.QueryDSLFunc.groupHavingExample;

public class JpaMain8 {

	private static final Logger logger = LoggerFactory.getLogger(JpaMain8.class);

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpatest");
		try {


			//saveData(emf);
			//testJQPL(emf);
			//testCriteria(emf);
			//QueryDSLFunc.exampleQueryDSL(emf);

			//단건조회
			//QueryDSLFunc.uniqueResultQueryDSL(emf);

			//단건조회
			//QueryDSLFunc.singleQueryDSL(emf);

			//정령 페이징
			QueryDSLFunc.orderAndPagingExample(emf);

			//group-having
			groupHavingExample(emf);

		} finally {
			emf.close();
		}
	}

	
}
