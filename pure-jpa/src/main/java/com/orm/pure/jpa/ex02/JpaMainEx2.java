package com.orm.pure.jpa.ex02;

import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orm.pure.jpa.domain.Member;

public class JpaMainEx2 {
	
	private static Logger logger = LoggerFactory.getLogger(JpaMainEx2.class);
	
	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpatest");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			
			//1. 회원등록
			//Member newMem = makeMember(em);
			//saveMember(em, newMem);
			
			//2. 회원수정
			//updateMember(em, "1","홍길똥",20);
			
			//3. 회원삭제
			//removeMember(em, "1");
			
			findMember(em, "1");
			
			tx.commit();
		} catch(Exception ex) {
			logger.debug(ex.getMessage(), ex);
			tx.rollback();
		} finally {
			em.close();
			emf.close();
		}
	}
	
	/**
	 * Create New Member Object For Save
	 * @param em
	 * @return
	 */
	private static Member makeMember(EntityManager em) {
		Random random = new Random();
		Integer age = random.nextInt(100);
		List<String> ids = em.createQuery("select id from Member m order by m.username desc", String.class).getResultList();
		String newId = "1";
		
		if(ids.size() > 0) {
			newId = String.valueOf(Integer.parseInt(ids.get(0)) + 1);			
		}
		
		Member newMember = Member.builder()
				.id(newId)
				.userName("홍길동")
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
	
	public static void removeMember(EntityManager em, String id) {
		Member removeMem = em.find(Member.class, id);
		
		//엔티티삭제
		em.remove(removeMem);
	}
	
	public static Member findMember(EntityManager em, String id) {
		Member member = em.find(Member.class, id);
		logger.debug("### findMember(" + id + ")=" + member.getUserName() + "," + member.getAge());
		return em.find(Member.class, id);
	}

}
