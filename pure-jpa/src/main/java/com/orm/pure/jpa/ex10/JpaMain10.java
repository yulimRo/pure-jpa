package com.orm.pure.jpa.ex10;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.LoggerFactory;

import com.querydsl.jpa.impl.JPAQuery;

import ch.qos.logback.classic.Logger;

public class JpaMain10 {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(JpaMain10.class);
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpatest");
		try {

			//findMemberJpql(emf);
			findMemberCriteria(emf);
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
    
    /**
     * template
     * @param emf
     */
    private static void findMemberJpql(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            
            Member member = new Member();
            
            member.setUsername("kim");
            em.persist(member);
            
            String jpql = "select m from Member m where m.username = 'kim' ";
            List<Member> findMem = em.createQuery(jpql).getResultList();
            //Member findMem = em.find(Member.class, member.getId());
            
            logger.debug("find Member=" + findMem.get(0).getUsername());
            
            tx.commit();
        } catch(Exception ex) {
        	logger.debug(ex.getMessage(), ex);
            tx.rollback();
        } finally {
            em.close();
        }
    }
    
    private static void findMemberCriteria(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        EntityTransaction tx = em.getTransaction();
        try {
        	
        	tx.begin();
        	Member member = new Member();
        	
        	member.setUsername("kim");
        	em.persist(member);
        	
        	tx.commit();
        	
        	CriteriaQuery<Member> query = cb.createQuery(Member.class);
        	Root<Member> m = query.from(Member.class);
        	
        	CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "kim"));
        	
        	List<Member> result = em.createQuery(cq).getResultList();
        	Member find = result.get(0);
            logger.debug("## find Member=" + find.getUsername());
            
            
        } catch(Exception ex) {
        	logger.debug(ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }
    
    
}
