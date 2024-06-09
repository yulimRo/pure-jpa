package com.orm.pure.jpa.ex08;


import com.orm.pure.jpa.ex08.domain.func.DefaultFunc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

//import static com.orm.pure.jpa.ex08.domain.QTeam8.team8; //기본 인스턴스 사용방식 2
import static com.orm.pure.jpa.ex08.domain.func.CriteriaFunc.testCriteria;
import static com.orm.pure.jpa.ex08.domain.func.JPQLFunc.*;


public class JpaMain8 {

	private static final Logger logger = LoggerFactory.getLogger(JpaMain8.class);

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpatest");
		try {


			//DefaultFunc.saveData(emf);

			//basicSelectMember(emf);

			//selectStatisticsData(emf);

			//selectMemberDTO(emf);

			//selectJoinEntity(emf);

			//pathExpression(emf);

			//selectSubQuery(emf);

			//selectConditionExpression(emf);

			//selectNamedQuery(emf);

			testCriteria(emf);
			//QueryDSLFunc.exampleQueryDSL(emf);

			//단건조회
			//QueryDSLFunc.uniqueResultQueryDSL(emf);

			//단건조회
			//QueryDSLFunc.singleQueryDSL(emf);

			//정령 페이징
			//QueryDSLFunc.orderAndPagingExample(emf);

			//group-having
			//groupHavingExample(emf);

		} finally {
			emf.close();
		}
	}

	
}
