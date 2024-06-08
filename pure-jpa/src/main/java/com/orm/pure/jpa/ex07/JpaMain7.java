package com.orm.pure.jpa.ex07;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orm.pure.jpa.ex06.JpaMain6;
import com.orm.pure.jpa.ex07.domain.Album;
import com.orm.pure.jpa.ex07.domain.Member7;
import com.orm.pure.jpa.ex07.domain.Order7;
import com.orm.pure.jpa.ex07.domain.Product7;
import com.orm.pure.jpa.ex07.domain.Team7;

public class JpaMain7 {

	private static final Logger logger = LoggerFactory.getLogger(JpaMain6.class);

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpatest");

		try {
		
			//Album 객체 정보로 item 테이블에 데이터 INSERT
			//saveAlbum(emf);
			
			//Member7, Product7, Order7, Team7 데이터 넣기
			saveData(emf);
		} finally {
			emf.close();
		}
	}

	public static void template(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {

		} catch (Exception ex) {
			tx.rollback();
		} finally {
			em.close();
		}

	}
	
	public static void saveAlbum(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			
			Album album = new Album();
			album.setArtist("아티스트");
			album.setName("이름");

			em.persist(album);
			tx.commit();
		} catch (Exception ex) {
			tx.rollback();
		} finally {
			em.close();
		}
	}

	
	public static void saveData(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			
			//회원저장
			for(int i =1; i <4; i++) {
				Member7 member7 = new Member7();
				member7.setName("홍길동"+i);
				em.persist(member7);
			}
			
			//팀저장
			for(int i =1; i <3; i++) {
				Team7 team7 = new Team7();
				team7.setName("팀"+i);
				em.persist(team7);
			}
			
			//상품저장
			for(int i =0; i <5; i++) {
				Product7 product7 = new Product7();
				product7.setName("상품"+i);
				em.persist(product7);
			}
			
			em.flush();
			em.clear();
			
			logger.debug("===========================================================================================");
			
			//team 즉시 로딩 -> left outer join
			Member7 findMem1 = em.find(Member7.class, 1L);
			Member7 findMem2 = em.find(Member7.class, 2L);

			Team7 findTeam1 = em.find(Team7.class, 5L);
			Team7 findTeam2 = em.find(Team7.class, 6L);
			
			findMem1.setTeam(findTeam1);
			findMem2.setTeam(findTeam2);

			Product7 findProduct1 = em.find(Product7.class, 8L);
			Product7 findProduct2 = em.find(Product7.class, 9L);
			Product7 findProduct3 = em.find(Product7.class, 10L);

			Order7 order = new Order7();
			order.setMember(findMem1);
			order.setProduct(findProduct1);
			//findMem1.getOrders().add(order);


			Order7 order2 = new Order7();
			order2.setMember(findMem2);
			order2.setProduct(findProduct2);
			

			Order7 order3 = new Order7();
			order3.setMember(findMem2);
			order3.setProduct(findProduct3);
			
			
			em.persist(order);
			em.persist(order2);
			em.persist(order3);


			logger.debug("==== 주문 엔티티 조회 ");
			List<Order7> orders = findMem1.getOrders(); //PersisBag 컬렉션 래퍼
			
			tx.commit();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			tx.rollback();
		} finally {
			em.close();
		}
	}

}
