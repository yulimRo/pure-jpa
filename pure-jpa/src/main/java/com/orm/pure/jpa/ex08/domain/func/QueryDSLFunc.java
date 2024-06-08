package com.orm.pure.jpa.ex08.domain.func;

import com.mysema.query.QueryModifiers;
import com.mysema.query.SearchResults;
import com.mysema.query.jpa.impl.JPAQuery;
import com.orm.pure.jpa.ex08.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class QueryDSLFunc {

    private static final Logger logger = LoggerFactory.getLogger(QueryDSLFunc.class);

    public static void exampleQueryDSL(EntityManagerFactory emf) {
        logger.debug("====================exampleQueryDSL=======================");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            JPAQuery query = new JPAQuery(em);
            QMember8 member8 = new QMember8("m"); //직접 지정
            QMember8 member8_2 = QMember8.member8; //기본 인스턴스 사용


            List<Member8> members = query.from(member8)
                    .where(member8.name.eq("홍길동1").and(member8.age.gt(5)))
                    .orderBy(member8.name.desc())
                    .list(member8);

            members.stream().forEach(x -> logger.debug("== QueryDSL 예제 1=" + x.getName()));

        } catch (Exception ex) {
            tx.rollback();
        } finally {
            em.close();
        }

    }


    public static void uniqueResultQueryDSL(EntityManagerFactory emf) {
        logger.debug("====================exampleQueryDSL=======================");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            JPAQuery query = new JPAQuery(em);
            QMember8 member8 = new QMember8("m"); //직접 지정
            QMember8 member8_2 = QMember8.member8; //기본 인스턴스 사용


            //조회되는 데이터가 없을때
            Member8 result = query.from(member8)
                    .where(member8.name.eq("홍길동1").and(member8.age.gt(5)))
                    .orderBy(member8.name.desc())
                    .uniqueResult(member8);

            if(result == null){
                logger.debug("널이다.");
            }else{
            logger.debug("== QueryDSL 예제 1=" + result.getName());
            }

            //조회되는 데이터가 있을 떄
            JPAQuery query2 = new JPAQuery(em);
            Member8 result2 = query2.from(member8_2)
                    .where(member8_2.name.eq("홍길동2").and(member8_2.age.gt(5)))
                    .orderBy(member8_2.name.desc())
                    .uniqueResult(member8_2);

            if(result2 == null){
                logger.debug("널이다.");
            }else{
                logger.debug("== QueryDSL 예제 1=" + result2.getName());
            }


        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            em.close();
        }

    }


    public static void singleQueryDSL(EntityManagerFactory emf) {
        logger.debug("====================exampleQueryDSL=======================");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            JPAQuery query = new JPAQuery(em);
            QMember8 member8 = new QMember8("m"); //직접 지정
            QMember8 member8_2 = QMember8.member8; //기본 인스턴스 사용


            //조회되는 데이터가 없을때
            Member8 result = query.from(member8)
                    .where(member8.name.eq("홍길동1").and(member8.age.gt(5)))
                    .orderBy(member8.name.desc())
                    .singleResult(member8);

            if(result == null){
                logger.debug("널이다.");
            }else{
                logger.debug("== singleResult() 예제1=" + result.getName());
            }

            //조회되는 데이터가 있을 떄
            JPAQuery query2 = new JPAQuery(em);
            Member8 result2 = query2.from(member8_2)
                    .where(member8_2.name.eq("홍길동2").and(member8_2.age.gt(5)))
                    .orderBy(member8_2.name.desc())
                    .singleResult(member8_2);

            if(result2 == null){
                logger.debug("널이다.");
            }else{
                logger.debug("== singleResult() 예제2=" + result2.getName() +",나이=" + result2.getAge());
            }


        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            em.close();
        }

    }


    public static void orderAndPagingExample(EntityManagerFactory emf) {
        logger.debug("====================orderAndPagingExample=======================");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            JPAQuery query = new JPAQuery(em);
            QMember8 member8 = new QMember8("m"); //직접 지정
            QMember8 member8_2 = QMember8.member8; //기본 인스턴스 사용
            QMember8 member8_3 = new QMember8("m3"); //기본 인스턴스 사용


            //offset, limit 사용
            List<Member8> result = query.from(member8)
                    .orderBy(member8.name.desc(),member8.age.asc())
                    .offset(2L)
                    .limit(2L)
                    .list(member8);

            result.stream().forEach(x -> logger.debug("정렬페이징예제 =" + x.getName()));

            //QueryModifiers
            QueryModifiers queryModifiers = new QueryModifiers(2L,0L); // limit, offset
            JPAQuery query2 = new JPAQuery(em);
            List<Member8> pagingResult = query2.from(member8_2)
                    .restrict(queryModifiers)
                    .list(member8_2);

            //조회된 데이터
            pagingResult.stream().forEach(x-> logger.debug("조회된 회원=" +x.getId() + "," + x.getName()));


            JPAQuery query3 = new JPAQuery(em);
            SearchResults<Member8> pagingResult2 = query3.from(member8_3)
                    .restrict(queryModifiers)
                    .listResults(member8_3);

            logger.debug("검색된 전체 데이터 수 : " + pagingResult2.getTotal());
            logger.debug("limit : " + pagingResult2.getLimit());
            logger.debug("offset : "+ pagingResult2.getOffset());
            //조회된 데이터
            List<Member8> results2 = pagingResult2.getResults();

            results2.stream().forEach(x-> logger.debug("조회된 회원=" +x.getId() + "," + x.getName()));

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            em.close();
        }

    }


    /**
     * TODO 에러 발생, 추후확인필요
     * @param emf
     */
    public static void groupHavingExample(EntityManagerFactory emf) {
        logger.debug("====================groupHavingExample=======================");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            JPAQuery query = new JPAQuery(em);
            QMember8 member8 = new QMember8("m"); //직접 지정

            List<Member8> result = query.from(member8)
                    .groupBy(member8.age)
                    .having(member8.age.gt(7))
                    .list(member8);

            result.stream().forEach(x-> logger.debug("조회된 회원=" +x.getId() + "," + x.getName()));


        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            em.close();
        }

    }

    public static void joinExample(EntityManagerFactory emf) {
        logger.debug("====================groupHavingExample=======================");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            QOrder8 order = QOrder8.order8;
            QMember8 member = QMember8.member8;
            QProduct8 product = QProduct8.product8;

            JPAQuery query = new JPAQuery(em);
            List<Order8> result= query.from(order)
                    .join(order.member, member)
                    .leftJoin(order.product, product)
                    .list(order);


        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            em.close();
        }

    }



}
