package com.orm.pure.jpa.ex03;

import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orm.pure.jpa.domain.Member;

public class JpaMainEx3 {
	
	private static Logger logger = LoggerFactory.getLogger(JpaMainEx3.class);
	
	public static void main(String[] args) {
///======================================================================================
// 회원 등록/수정/삭제 테스트 시 사용
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpatest");
//		EntityManager em = emf.createEntityManager();
//		EntityTransaction tx = em.getTransaction();
//		
//		try {
//			tx.begin();
//
//			//1. 회원등록
//			Member newMem = makeMember(em,"김수진");
//			saveMember(em, newMem);
//			
//			//2. 회원수정
//			//updateMember(em, "1","홍길똥",20);
//			
//			//3. 회원삭제
//			//removeMember(em, "1");
//			
//			
//			//Member findMem = findMember(em, "1");
//			//Member findMem = findMember(em, newMem.getId());
//			//logger.debug("#### 두번째 조회 ###");
//			//Member findMem2 = findMember(em, "1");
//			
//			tx.commit();
//		} catch(Exception ex) {
//			logger.debug(ex.getMessage(), ex);
//			tx.rollback();
//		} finally {
//			em.close();
//			emf.close();
//		}
//==================================================================================================
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpatest");
		try {
			
			//특정 엔티티 준영속화
			clearMember(emf, "1");
			
		} catch(Exception ex) {
			logger.debug(ex.getMessage(), ex);
		} finally {
			emf.close();
		}
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
	
	public static Member findMember(EntityManager em, String id) {
		Member member = em.find(Member.class, id);
		logger.debug("### findMember(" + id + ")=" + member.getUserName() + "," + member.getAge());
		return em.find(Member.class, id);
	}
	
	
	/**
	 * Create New Member Object For Save
	 * @param em
	 * @return
	 */
	private static Member makeMember(EntityManager em, String name) {
		Random random = new Random();
		Integer age = random.nextInt(100);
		List<String> ids = em.createQuery("select id from Member m order by m.id desc", String.class).getResultList();
		String newId = "1";
		
		if(ids.size() > 0) {
			newId = String.valueOf(Integer.parseInt(ids.get(0)) + 1);			
		}
		
		Member newMember = Member.builder()
				.id(newId)
				.userName(name)
				.age(age).build();
		
		return newMember;
	}
	
	/**
	 * Save Member Data
	 * @param em
	 * @param mem
	 */
	public static void saveMember(EntityManager em,  Member mem) {
		em.persist(mem);
	}
	
	/**
	 * Update Member Data
	 * @param em
	 * @param id
	 * @param updaetName
	 * @param updaeAge
	 */
	public static void updateMember(EntityManager em, String id, String updaetName, Integer updaeAge) {
		Member member = em.find(Member.class, id);
		member.update(updaetName, updaeAge);
		
	}
	
	/**
	 * Remove Member Data
	 * @param em
	 * @param id
	 */
	public static void removeMember(EntityManager em, String id) {
		Member removeMem = em.find(Member.class, id);
		
		//엔티티삭제
		em.remove(removeMem);
	}
	
	
	/**
	 * Detach Member Entity
	 * @param emf
	 * @param id
	 */
	public static void detachMember(EntityManagerFactory emf, String id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			
			Member member = em.find(Member.class,id);
			
			//준영속화
			em.detach(member);
			
			member.update(member.getUserName(), member.getAge() + 1);
			
			tx.commit();
		} catch(Exception ex) {
			tx.rollback();
		} finally {
			em.close();
		}
	}
	
	/**
	 * Detach Member And merge Entity
	 * @param emf
	 * @param id
	 */
	public static void mergeMember(EntityManagerFactory emf, String id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			
			Member member = em.find(Member.class,id);
			
			//준영속화
			em.detach(member);
			
			member.update(member.getUserName(), member.getAge() + 1);
			
			em.merge(member);
			
			tx.commit();
		} catch(Exception ex) {
			tx.rollback();
		} finally {
			em.close();
		}
	}
	
	/**
	 * Clear Persistence Container And Merge Entity
	 * @param emf
	 * @param id
	 */
	public static void clearMember(EntityManagerFactory emf, String id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			
			Member member = em.find(Member.class,id);
			Member member2 = em.find(Member.class,"2");
			
			//준영속화
			em.clear();
			
			member.update(member.getUserName(), member.getAge() + 10);
			//member2.update(member2.getUserName(), member2.getAge() + 20);

			em.merge(member);
						
			tx.commit();
		} catch(Exception ex) {
			logger.error(ex.getMessage(),ex);
			tx.rollback();
		} finally {
			em.close();
		}
	}
	
	

}
