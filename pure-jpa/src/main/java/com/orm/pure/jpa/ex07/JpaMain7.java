package com.orm.pure.jpa.ex07;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.orm.pure.jpa.ex07.domain.*;
import com.sun.nio.sctp.PeerAddressChangeNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orm.pure.jpa.ex06.JpaMain6;

public class JpaMain7 {

	private static final Logger logger = LoggerFactory.getLogger(JpaMain7.class);

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpatest");

		try {
		
			//Album 객체 정보로 item 테이블에 데이터 INSERT
			//saveAlbum(emf);
			
			//Member7, Product7, Order7, Team7 데이터 넣기
			//saveData(emf);

			//게시물등록
			//saveBoardAndBoardDetail(emf);

			//지연로딩
			//selectTeamOfMember(emf);

			//부모자식데이터저장
			//saveParentChild(emf);

			//saveParentChildCasCade(emf);

			saveCollectionInMember(emf);

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

	public static void saveBoardAndBoardDetail(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {

			tx.begin();
			Board board = new Board();
			board.setName("타이틀2");
			board.setContent("컨텐츠2");

			em.persist(board); //BOARD 테이블과 BOARD_DETAIL 테이블 모두 저장

			em.flush();
			tx.commit();

		} catch (Exception ex) {
			logger.error(ex.getMessage(),ex);
			tx.rollback();
		} finally {
			em.close();
		}

	}

	public static void selectTeamOfMember(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {

			Member7 member = em.find(Member7.class, 1L);
			logger.debug("팀 클래스명 =" + member.getTeam().getClass() ); //엔티티
			logger.debug("주문 클래스명 =" + member.getOrders().getClass() ); //프록시

			Member7 member2 = em.getReference(Member7.class, 2L); //DB 조회 x 프록시 객체 생성
			logger.debug("멤버 클래스명 =" + member2.getClass() );
			String name = member2.getName(); //실제 사용으로 인한 DB 조회
			logger.debug("멤버 클래스명 =" + member2.getClass() ); //동일한 프록시 객체


		} catch (Exception ex) {
			logger.error(ex.getMessage(),ex);
			tx.rollback();
		} finally {
			em.close();
		}

	}


	public static void saveParentChild(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {

			tx.begin();

			Parent parent = new Parent();
			parent.setName("부모1");
			Parent parent2 = new Parent();
			parent2.setName("부모2");

			em.persist(parent);
			em.persist(parent2);

			Child child = new Child();
			child.setName("아이1");
			child.setName("아이2");
			child.setName("아이3");


			Child child2 = new Child();
			child2.setName("아이1");
			child2.setName("아이2");
			child2.setName("아이3");

			Child child3 = new Child();
			child3.setName("아이1");
			child3.setName("아이2");
			child3.setName("아이3");

			em.persist(child);
			em.persist(child2);
			em.persist(child3);

			tx.commit();

		} catch (Exception ex) {
			logger.error(ex.getMessage(),ex);
			tx.rollback();
		} finally {
			em.close();
		}

	}


	public static void saveParentChildCasCade(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {

			tx.begin();

			Child7_2 child1 = new Child7_2();
			Child7_2 child2 = new Child7_2();

			Parent7_2 parent = new Parent7_2();

			//parent만 영속화 하여도 child1, child2 모두 영속화 됨.
			child1.setParent(parent); //연관관계추가
			child2.setParent(parent); //연관관계추가
			parent.getChildList().add(child1); //부모 -> 자식
			parent.getChildList().add(child2); //부모 -> 자식

			em.persist(parent);
			logger.debug("==================================");

			//orphanRemoval = true 이면 해당 child 객체 삭제
			parent.getChildList().remove(0);

			tx.commit();

		} catch (Exception ex) {
			logger.error(ex.getMessage(),ex);
			tx.rollback();
		} finally {
			em.close();
		}

	}


	public static void saveCollectionInMember(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {

			tx.begin();

			Member7 member = new Member7();
			member.setName("홍길동");

			Set<String> fav = new HashSet<>();
			fav.add("햄버거");
			fav.add("치킨");
			member.setFavoriteFoods(fav);

			Address address = new Address();
			address.city = "수원";
			address.street = "덕영대로 1323번길 26-7";
			address.zipcode = "16555";

			member.getAddressHistory().add(address);

			em.persist(member);

			tx.commit();

		} catch (Exception ex) {
			logger.error(ex.getMessage(),ex);
			tx.rollback();
		} finally {
			em.close();
		}

	}

}
