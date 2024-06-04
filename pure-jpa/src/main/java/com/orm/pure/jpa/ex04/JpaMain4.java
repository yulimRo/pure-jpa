package com.orm.pure.jpa.ex04;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orm.pure.jpa.ex04.domain.Member_4_IDENTITY;
import com.orm.pure.jpa.ex04.domain.Member_4_SEQUENCE;
import com.orm.pure.jpa.ex04.domain.Member_4_TABLE;


public class JpaMain4 {
	
	private static final Logger logger = LoggerFactory.getLogger(JpaMain4.class);
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpatest");

		try {
			//시퀀스 기본키 매핑 전략
			//saveEntityBySequence(emf);
			
			//IDENTITY 기본키 매핑 전략
			//saveEntityByIDENTITY(emf);
			
			//TABLE 기본키 매핑 전략
			saveEntityByTABLE(emf);
			
		} finally {
			emf.close();
		}
	}

	public static void template(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			
			
		} catch(Exception ex) {
			tx.rollback();
		} finally {
			em.close();
		}
		
	}
	
	public static void saveEntityBySequence(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			
			tx.begin();
			
			Member_4_SEQUENCE member = new Member_4_SEQUENCE();
			member.setAge(10);
			member.setUsername("홍깅돌");
			member.setCreatedDate(new Date());
			
			em.persist(member);
			
			tx.commit();
			
		} catch(Exception ex) {
			tx.rollback();
		} finally {
			em.close();
		}
		
	}
	
	public static void saveEntityByIDENTITY(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			
			tx.begin();
			
			Member_4_IDENTITY member = new Member_4_IDENTITY();
			member.setAge(10);
			member.setUsername("홍깅돌");
			member.setCreatedDate(new Date());
			
			em.persist(member);
			
			tx.commit();
			
		} catch(Exception ex) {
			tx.rollback();
		} finally {
			em.close();
		}
		
	}
	
	public static void saveEntityByTABLE(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			
			tx.begin();
			
			Member_4_TABLE member = new Member_4_TABLE();
			member.setAge(10);
			member.setUsername("홍깅돌");
			member.setCreatedDate(new Date());
			
			em.persist(member);
			
			tx.commit();
			
		} catch(Exception ex) {
			tx.rollback();
		} finally {
			em.close();
		}
		
	}
}

