package com.orm.pure.jpa.ex04;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orm.pure.jpa.domain.Board;
import com.orm.pure.jpa.domain.BoardSeq;
import com.orm.pure.jpa.domain.BoardTableSeq;
import com.orm.pure.jpa.domain.MemberAccAll;

public class JpaMainEx4 {
	
	private static Logger logger = LoggerFactory.getLogger(JpaMainEx4.class);
	
	public static void main(String[] args) {
///======================================================================================
// 게시물 등록/수정/삭제 테스트 시 사용
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpatest");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();

			//1. 게시물 등록
			//saveBoard(emf, "test1");
			//saveBoardSeq(emf, "test1");
			//saveBoardTableSeq(emf, "test1");
			
			getMemberAccAll(emf, "1");
			
			tx.commit();
		} catch(Exception ex) {
			logger.debug(ex.getMessage(), ex);
			tx.rollback();
		} finally {
			em.close();
			emf.close();
		}
//==================================================================================================
		
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpatest");
//		try {
//			
//			//특정 엔티티 준영속화
//			clearMember(emf, "1");
//			
//		} catch(Exception ex) {
//			logger.debug(ex.getMessage(), ex);
//		} finally {
//			emf.close();
//		}
	}
	
	/**
	 * template
	 * @param emf
	 */
	private static void template(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			
			tx.commit();
		} catch(Exception ex) {
			tx.rollback();
		} finally {
			em.close();
		}
	}

	private static void saveBoard(EntityManagerFactory emf, String content) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			
			Board board = Board.builder().content(content).build();
			
			em.persist(board);
			
			tx.commit();
		} catch(Exception ex) {
			tx.rollback();
		} finally {
			em.close();
		}
	}
	private static void saveBoardSeq(EntityManagerFactory emf, String content) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			
			BoardSeq board = BoardSeq.builder().content(content).build();
			
			em.persist(board);
			
			tx.commit();
		} catch(Exception ex) {
			tx.rollback();
		} finally {
			em.close();
		}
	}
	private static void saveBoardTableSeq(EntityManagerFactory emf, String content) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			
			BoardTableSeq board = BoardTableSeq.builder().content(content).build();
			
			em.persist(board);
			
			tx.commit();
		} catch(Exception ex) {
			tx.rollback();
		} finally {
			em.close();
		}
	}
	
	private static MemberAccAll getMemberAccAll(EntityManagerFactory emf, String content) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		MemberAccAll memberAccAll = null;
		try {
			tx.begin();
			
			MemberAccAll all = new MemberAccAll();
			all.changeFirstName("FIRST");
			all.changeLastName("SUB");
			
			em.persist(all);
			
			em.flush();
			
			memberAccAll = em.find(MemberAccAll.class, all.getId());
			
			if(memberAccAll != null ) {
				logger.debug(memberAccAll.getFullName());
			}else {
				logger.debug("없다얘~");
			}
			tx.commit();
		} catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			tx.rollback();
		} finally {
			em.close();
		}
		
		return memberAccAll;
	}
}
