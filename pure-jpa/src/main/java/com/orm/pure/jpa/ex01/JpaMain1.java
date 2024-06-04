package com.orm.pure.jpa.ex01;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orm.pure.jpa.ex01.domain.Member;

public class JpaMain1 {
	
	private static final Logger logger = LoggerFactory.getLogger(JpaMain1.class);
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpatest");

		try {
			//엔티티 CRUD
			//MemberEntityCRUD(emf);
			
			//엔티티 생명주기
			//entityLifeCycle(emf);
			
			//영속성컨텍스트 특징
			//persistenceContextFeature(emf);
			
			//flush
			flushEntity(emf);
			
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
	
	public static void MemberEntityCRUD(EntityManagerFactory emf) {
		logger.debug("===================== Start MemberEntityCRUD ===============================");
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			Member member = new Member();
			member.setId(1L);
			member.setAge(10);
			member.setUsername("노유림");
			
			//회원 엔티티 저장
			em.persist(member);
			logger.debug("==========================================================================");
			Member findMember = em.find(Member.class, member.getId());
			logger.debug("==찾은 회원 정보 =" + findMember.getId() + ","+ findMember.getUsername());
			
			logger.debug("===============================엔티티수정===================================");
			//엔티티 수정
			findMember.setAge(20);
			logger.debug("==========================================================================");
			tx.commit();
			logger.debug("================================엔티티삭제=====================================");
			//트랜젝션 재사용 안됨
			tx.begin();
			//회원 엔티티 삭제
			em.remove(findMember);
			tx.commit();
			
			Member removeMember = em.find(Member.class, findMember.getId());
			
			if(removeMember != null) {
				logger.debug("== 회원 정보=" + removeMember.getId() );
			} else {
				logger.debug("없다!");
			}
		
			
		} catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			tx.rollback();
		} finally {
			em.close();
		}
		
		logger.debug("===================== End MemberEntityCRUD ===============================");
	}
	
	public static void entityLifeCycle(EntityManagerFactory emf) {
		logger.debug("===================== Start EntityLifeCycle ===============================");
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			Member member = new Member();
			member.setId(1L);
			member.setAge(10);
			member.setUsername("노유림");
			
			//회원 엔티티 저장
			em.persist(member);
			
			//영속 -> 준영속
			em.detach(member);
			
			Member findMember = em.find(Member.class, member.getId());
			
			if(findMember == null) {
				logger.debug("[detach]회원 없다.");
			}
			
			//준영속 -> 영속
			em.merge(member);
			
			Member mergeMember = em.find(Member.class, member.getId());
			
			if(mergeMember == null) {
				logger.debug("[merge]회원 없다.");
			} else {
				logger.debug("[merge]회원 있다. (" + mergeMember.getId() + ")");
			}
			
			//영속 컨텍스트 초기화
			em.clear();
			
			Member clearMember = em.find(Member.class, mergeMember.getId());
			
			if(clearMember == null) {
				logger.debug("[clear]회원 없다.");
			} else {
				logger.debug("[clear]회원 있다. (" + clearMember.getId() + ")");
			}

			tx.commit();
		
			
		} catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			tx.rollback();
		} finally {
			em.close();
		}
		
		logger.debug("===================== End EntityLifeCycle ===============================");
	}
	
	public static void persistenceContextFeature(EntityManagerFactory emf) {
		logger.debug("===================== Start persistenceContextFeature ===============================");
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			Member member = new Member();
			member.setId(1L);
			member.setAge(10);
			member.setUsername("노유림");
			
			//회원 엔티티 저장 -> INSERT 문 쓰기지연SQL 저장소에 저장
			em.persist(member);
			
			//변경감지 + UPDATE문 쓰기지연 SQL 저장소에 저장
			member.setAge(20);
			

			logger.debug("트랜잭션 실행 전");
			//이때 INSERT, UPDATE문 실행 및 DB와 동기화
			tx.commit();
			logger.debug("트랜잭션 실행 후");
			
			//SELECT문은 이떄 실행 -> 1차캐시에 해당 식별자의 영속화된 엔티티가 있기 떄문.
			Member findMember = em.find(Member.class, member.getId());
			if(findMember == null ) {
				logger.debug("엔티티 없다.");
			} else {
				logger.debug("엔티티 발견=" + findMember.getId() + "," + findMember.getUsername());
			}
			
			//준영속화
			em.detach(findMember);
			
			//이때는 SELECT문 실행 -> 엔티티 준영속화 되어 1차 캐시에 해당 식별자의 영속화엔티티 없기 때문.
			Member findMember2 = em.find(Member.class, findMember.getId());
			
		} catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			tx.rollback();
		} finally {
			em.close();
		}
		
		logger.debug("===================== End persistenceContextFeature ===============================");
	}

	public static void flushEntity(EntityManagerFactory emf) {
		logger.debug("===================== Start persistenceContextFeature ===============================");
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			Member member = new Member();
			member.setId(1L);
			member.setAge(10);
			member.setUsername("노유림");
			
			//회원 엔티티 저장 -> INSERT 문 쓰기지연SQL 저장소에 저장
			em.persist(member);
	
			logger.debug("Flush 실행 전");
			//[flush 발생] DB 동기화
			em.flush();
			logger.debug("Flush 실행 후");
			
			//변경감지 + UPDATE문 쓰기지연 SQL 저장소에 저장
			member.setAge(20);
			
			//해당 설정이 있을 경우, COMMIT 할 경우에만 flush() 실행
			//em.setFlushMode(FlushModeType.COMMIT);
			
			String jpql = "select m from Member m";
			
			// [flush 발생] 
			// 플러시 옵션이 있을 경우, jpql 에서 flush() 발생 안하여 UPDATE문 실행 안됨
			List<Member> members = em.createQuery(jpql).getResultList();
			
			if(members.size() > 0) {
				for(Member m : members) {
					logger.debug("List<Member> 회원=" + m.getId() );					
				}
			}	
			
			//변경감지
			member.setUsername("유림이");
			
			logger.debug("트랜잭션 실행 전");
			// [flush 발생] UPDATE문만 실행 -> flush()로 한번 동기화 시켜줬기 때문.
			tx.commit();
			logger.debug("트랜잭션 실행 후");
			
		} catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			tx.rollback();
		} finally {
			em.close();
		}
		
		logger.debug("===================== End persistenceContextFeature ===============================");
	}
}

