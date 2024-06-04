package com.orm.pure.jpa.ex06;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orm.pure.jpa.ex06.domain.Member6_0;
import com.orm.pure.jpa.ex06.domain.MemberProduct;
import com.orm.pure.jpa.ex06.domain.MemberProduct2;
import com.orm.pure.jpa.ex06.domain.MemberProductId2;
import com.orm.pure.jpa.ex06.domain.Member_6;
import com.orm.pure.jpa.ex06.domain.Member_6_2;
import com.orm.pure.jpa.ex06.domain.Member_6_3;
import com.orm.pure.jpa.ex06.domain.Member_6_3_1;
import com.orm.pure.jpa.ex06.domain.Member_6_3_2;
import com.orm.pure.jpa.ex06.domain.Member_6_4;
import com.orm.pure.jpa.ex06.domain.Member_6_5;
import com.orm.pure.jpa.ex06.domain.Member_6_6;
import com.orm.pure.jpa.ex06.domain.Member_6_7;
import com.orm.pure.jpa.ex06.domain.Member_6_8;
import com.orm.pure.jpa.ex06.domain.oneway.Locker;
import com.orm.pure.jpa.ex06.domain.oneway.Locker_2;
import com.orm.pure.jpa.ex06.domain.oneway.Locker_3;
import com.orm.pure.jpa.ex06.domain.oneway.Product;
import com.orm.pure.jpa.ex06.domain.oneway.Product6_2;
import com.orm.pure.jpa.ex06.domain.oneway.Product6_6;
import com.orm.pure.jpa.ex06.domain.oneway.Team_6;
import com.orm.pure.jpa.ex06.domain.twoway.Team_6_0;
import com.orm.pure.jpa.ex06.domain.twoway.Team_6_2;


public class JpaMain6 {
	
	private static final Logger logger = LoggerFactory.getLogger(JpaMain6.class);
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpatest");

		try {
			//N:1 다대일 단방향
			//ManyToOneOneWay(emf);
			
			//N:1 다대일 양방향
			//ManyToOneTwoWay(emf);
			
			//1:N 일대다 단방향
			//OneToManyOneWay(emf);
			
			//1:1 단방향
			//OneToOneOneWay(emf);
			
			//1:1 양방향
			//OneToOneTwoWay(emf);
			
			//1:1 양방향(주테이블주인)
			//OneToOneTwoWaySubEntity(emf);
			
			//N:N 단방향
			//ManyToManyOneWay(emf);
			
			//N:N 양방향
			//ManyToManyTwoWay(emf);
			
			//다대다 연결엔티티 사용 (@IdClass)
			ManyToManyWithConnectEntity(emf);

			
			//다대다 연결엔티티 사용 (@EmbeddedId)
			//ManyToManyWithConnectEntity2(emf);
			
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
	
	public static void ManyToOneOneWay(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			
			tx.begin();
			
			Member_6 member = new Member_6();
			member.setUsername("노유림");
			member.setAge(10);
			
			Team_6 team = new Team_6();
			team.setName("team1");
			
			//team 객체 영속화
			em.persist(team);
			//연관관계 주인쪽에서 엔티티 정보 초기화
			member.setTeam(team);
			
			//연관관계 주인만 영속화 하면 -> 연관관계 엔티티 모두 영속화
			em.persist(member);
			
			tx.commit();
			
		} catch(Exception ex) {
			tx.rollback();
		} finally {
			em.close();
		}
		
	}
	
	public static void ManyToOneTwoWay(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			
			tx.begin();
			
			Member_6_2 member = new Member_6_2();
			member.setUsername("노유림");
			member.setAge(10);
			
			Team_6_2 team = new Team_6_2();
			team.setName("team6_2");
			
			//team 객체 영속화
			em.persist(team);
			//연관관계 주인쪽에서 엔티티 정보 초기화
			member.setTeam(team);
			//순수객체일때 연관과녜 고려하여 추가 ( 해당 로직으로는 외래키 관리 x)
			team.addMember(member);
			
			//연관관계 주인만 영속화 하면 -> 연관관계 엔티티 모두 영속화
			em.persist(member);
			
			tx.commit();
			
		} catch(Exception ex) {
			tx.rollback();
		} finally {
			em.close();
		}
		
	}
	
	
	public static void OneToManyOneWay(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			
			tx.begin();
			
			Member6_0 member = new Member6_0();
			member.setUsername("member1");
			Member6_0 member2 = new Member6_0();
			member2.setUsername("member2");
			
			Team_6_0 team1 = new Team_6_0();
			team1.setName("team1");
			
			team1.getMember().add(member);
			team1.getMember().add(member2);
			
			em.persist(member);
			em.persist(member2);
			em.persist(team1);
			
			//INSERT-MEM1
			//INSERT-MEM2
			//INSERT-TEAM
			//UPDATE-mem1(fk)
			//UPDATE-mem2(fk)
			tx.commit();
			
		} catch(Exception ex) {
			tx.rollback();
		} finally {
			em.close();
		}
		
	}
	
	

	public static void OneToOneOneWay(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			
			tx.begin();
			
			Locker locker = new Locker();
			em.persist(locker);
			
			Member_6_3 member = new Member_6_3();
			member.setUsername("노유림");
			member.setAge(10);
			member.setLocker(locker);
			
			em.persist(member);
			
			tx.commit();
			
		} catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			tx.rollback();
		} finally {
			em.close();
		}
		
	}
	
	public static void OneToOneTwoWay(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			
			tx.begin();
			
			Locker_2 locker = new Locker_2();
			em.persist(locker);
			
			//연관관계주인
			Member_6_3_1 member = new Member_6_3_1();
			member.setUsername("노유림");
			member.setAge(10);
			member.setLocker(locker);
			em.persist(member);
			
			//연관관계엔티티 세팅
			locker.setMember(member);
			
			em.persist(locker);
			
			tx.commit();
			
		} catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			tx.rollback();
		} finally {
			em.close();
		}
		
	}
	
	public static void OneToOneTwoWaySubEntity(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			
			tx.begin();
			
			Locker_3 locker = new Locker_3();
			em.persist(locker);
			
			//주테이블
			Member_6_3_2 member = new Member_6_3_2();
			member.setUsername("노유림");
			member.setAge(10);
			
			//Member는 연관관계주인이 아니기 때문에, 해당 로직만 세팅하고 ,
			//locker.setMember(member);을 수행하지 않으면 locker 테이블의 member_id fk는 null 이다. 
			member.setLocker(locker);
			
			em.persist(member);
			
			
			//보조테이블(외래키관리 = 연관관계주인)
			locker.setMember(member);
			
			em.persist(locker);
			
			tx.commit();
			
		} catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			tx.rollback();
		} finally {
			em.close();
		}
		
	}
	
	public static void ManyToManyOneWay(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			
			tx.begin();
			
			Member_6_4 member = new Member_6_4();
			member.setUsername("노유림");
			em.persist(member);
			
			Product product1 = new Product();
			product1.setName("상품1");
			Product product2 = new Product();
			product2.setName("상품2");
			member.getProducts().add(product1);
			member.getProducts().add(product2);
			
			em.persist(product1);
			em.persist(product2);
			em.persist(member);
			
			em.flush();
			
			em.clear();
			
			Member_6_4 findMem = em.find(Member_6_4.class, member.getId());
			
			//객체 탐색그래프 -> member_product, product INNER JOIN 
			List<Product> productList = findMem.getProducts();
			
			if(productList != null) {
				for(Product p : productList) {
					logger.debug("회원상품=" + p.getName());
				}
			}
			
			tx.commit();
			
		} catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			tx.rollback();
		} finally {
			em.close();
		}
		
	}
	
	public static void ManyToManyTwoWay(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			
			tx.begin();
			
			Member_6_5 member = new Member_6_5();
			member.setUsername("노유림");
			em.persist(member);
			
			Product6_2 product1 = new Product6_2();
			product1.setName("상품1");
			
			Product6_2 product2 = new Product6_2();
			product2.setName("상품2");
			
			em.persist(product1);
			em.persist(product2);
			
			member.getProducts().add(product1);
			member.getProducts().add(product2);
			
			//편의성 메서드 작성 필요
			product2.getMembers().add(member);
			product1.getMembers().add(member);
			
			em.flush();
			em.clear();
		
			Product6_2 findProduct = em.find(Product6_2.class, product1.getId());
			//연관관계주인이 아니더라도 아래와 같이 객체 그래프 탐색 시 INNER JOIN
			List<Member_6_5> mems = findProduct.getMembers();
			
			for(Member_6_5 m : mems) {
				logger.debug("회원=" + m.getId());
			}
			
			tx.commit();
			
		} catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			tx.rollback();
		} finally {
			em.close();
		}
		
	}
	
	public static void ManyToManyWithConnectEntity(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			
			tx.begin();
			
			Member_6_6 member = new Member_6_6();
			member.setUsername("노유림");
			em.persist(member);
			
			Product6_6 product1 = new Product6_6();
			product1.setName("상품1");
			
			Product6_6 product2 = new Product6_6();
			product2.setName("상품2");
			
			em.persist(product1);
			em.persist(product2);
			
			MemberProduct memProd = new MemberProduct();
			
			memProd.setMember(member);
			memProd.setProduct(product2);
			memProd.setOrderAmount(100);
			em.persist(memProd);
			
			tx.commit();
			
		} catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			tx.rollback();
		} finally {
			em.close();
		}
		
	}
	
	public static void ManyToManyWithConnectEntity2(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			
			tx.begin();
			
			Member_6_8 member = new Member_6_8();
			member.setUsername("노유림");
			em.persist(member);
			
			Product6_6 product1 = new Product6_6();
			product1.setName("상품1");
				
			Product6_6 product2 = new Product6_6();
			product2.setName("상품2");
			
			em.persist(product1);
			em.persist(product2);
			
			MemberProductId2 memProdId = new MemberProductId2();
			memProdId.setMember(member);
			memProdId.setProduct(product2);
			//em.persist(memProdId);
			
			MemberProduct2 memProd = new MemberProduct2();
			memProd.setId(memProdId); //MemberProductId2 식별자 클래스 자체 주입
			memProd.setOrderAmount(100);
			
			em.persist(memProd);
			
			
			tx.commit();
			
		} catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			tx.rollback();
		} finally {
			em.close();
		}
		
	}
	
}

