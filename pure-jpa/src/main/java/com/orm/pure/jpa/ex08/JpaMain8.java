package com.orm.pure.jpa.ex08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orm.pure.jpa.ex07.domain.Album;
import com.orm.pure.jpa.ex08.domain.Member8;
import com.orm.pure.jpa.ex08.domain.Member8_;
import com.orm.pure.jpa.ex08.domain.Order8;
import com.orm.pure.jpa.ex08.domain.Product8;
import com.orm.pure.jpa.ex08.domain.Team8;

public class JpaMain8 {

	private static final Logger logger = LoggerFactory.getLogger(JpaMain8.class);

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpatest");

		try {
			//saveData(emf);
			//testJQPL(emf);
			testCriteria(emf);
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
			for(int i =0; i <5; i++) {
				Member8 member8 = new Member8();
				member8.setName("홍길동"+i);
				member8.setAge(i+3+i);
				em.persist(member8);
			}
			
			//팀저장
			for(int i =0; i <3; i++) {
				Team8 team8 = new Team8();
				team8.setName("팀"+i);
				em.persist(team8);
			}
			
			//상품저장
			for(int i =0; i <10; i++) {
				Product8 product8 = new Product8();
				product8.setName("상품"+i);
				em.persist(product8);
			}
			
			em.flush();
			em.clear();
			
			logger.debug("===========================================================================================");
			
			//team 즉시 로딩 -> left outer join
			Member8 findMem1 = em.find(Member8.class, 1L);
			Member8 findMem2 = em.find(Member8.class, 2L);
			Member8 findMem3 = em.find(Member8.class, 3L);

			Team8 findTeam1 = em.find(Team8.class, 1L);
			Team8 findTeam2 = em.find(Team8.class, 2L);
			
			findMem1.setTeam(findTeam1);
			findMem2.setTeam(findTeam2);
			findMem3.setTeam(findTeam2);
			

			Product8 findProduct1 = em.find(Product8.class, 1L);
			Product8 findProduct2 = em.find(Product8.class, 2L);
			Product8 findProduct3 = em.find(Product8.class, 3L);
			Product8 findProduct4 = em.find(Product8.class, 4L);
			
			Order8 order = new Order8();
			order.setMember(findMem1);
			order.setProduct(findProduct1);
			//findMem1.getOrders().add(order);
			
			
			Order8 order2 = new Order8();
			order2.setMember(findMem2);
			order2.setProduct(findProduct2);
			

			Order8 order3 = new Order8();
			order3.setMember(findMem2);
			order3.setProduct(findProduct3);
			
			Order8 order4 = new Order8();
			order3.setMember(findMem2);
			order3.setProduct(findProduct4);
			
			
			em.persist(order);
			em.persist(order2);
			em.persist(order3);
			em.persist(order4);
			
			
			logger.debug("==== 주문 엔티티 조회 ");
			List<Order8> orders = findMem1.getOrders(); //PersisBag 컬렉션 래퍼
			
			tx.commit();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			tx.rollback();
		} finally {
			em.close();
		}
	}
	
	public static void testJQPL(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		logger.debug("================================testJPQL===================================");
		try {
			tx.begin();
			
//			//1. 기본 쿼리
//			String jpql = "select m8.id, m8.name from Member8 m8";
//			
//			Query query = em.createQuery(jpql);			
//			
//			//반환값을 모르기 때문에 반환타입 Object
//			List<Object> members = query.getResultList();
//			
//			for(Object obj : members) {
//				logger.debug("== Object[] 타입 회원객체=" + ((Object[])obj)[0] + "," + ((Object[])obj)[1]);
//			}
//			
////			//1. 기본 쿼리
////			String jpql1_2 = "select o.product from Order8 o";
////			
////			Query query1_2 = em.createQuery(jpql1_2);			
////			
////			//반환값을 모르기 때문에 반환타입 Object
////			List<Object[]> objs = em.createQuery(jpql1_2).getResultList();
////			
////			for(Object[] obj : objs) {
////				Product8 order = (Product8) obj[0];
////				logger.debug("== [jpql1_2] Object[] 타입=" + order.getId());
////			}
//			
//
//			//2. 조회 칼럼 명시
//			String jpql2 = "select m8 from Member8 m8";
//			
//			//반환타입 명확할 때
//			//jpql2 반환타입으로 Object[] 시 ClassCaseException
//			TypedQuery<Member8> typedQuery = em.createQuery(jpql2,Member8.class);
//			
//			List<Member8> members2 = typedQuery.getResultList();
//			for(Member8 mem : members2) {
//				logger.debug("== List<Member8>  타입 회원객체=" + mem.getId() + "," + mem.getName());
//			}
//
//			//3.위치기반파라미터
//			String jpql3 = "select m8 from Member8 m8 where m8.name = ?1";
//			
//			TypedQuery<Member8> typedQuery3 = em.createQuery(jpql3,Member8.class).setParameter(1, "홍길동1");
//			
//			List<Member8> members3 = typedQuery3.getResultList();
//			for(Member8 mem : members3) {
//				logger.debug("== jpql3 쿼리 조회 회원객체=" + mem.getId() + "," + mem.getName());
//			}
//
//			//4.이름기준파라미터
//			String jpql4 = "select m8 from Member8 m8 where m8.name = :userName";
//			
//			TypedQuery<Member8> typedQuery4 = em.createQuery(jpql4,Member8.class).setParameter("userName", "홍길동2");
//			
//			List<Member8> members4 = typedQuery4.getResultList();
//			for(Member8 mem : members4) {
//				logger.debug("== jpql4 쿼리 조회 회원객체=" + mem.getId() + "," + mem.getName());
//			}
//			
//
//			logger.debug("================================jpql4_1===================================");
//			//4. jpql4 변형 - distinct(객체자체엔적용x)
//			String jpql4_1 = "select distinct m8.name from Member8 m8 where m8.name = :userName";
//			
//			TypedQuery<String> typedQuery4_1 = em.createQuery(jpql4_1,String.class).setParameter("userName", "홍길동2");
//			
//			List<String> members4_1 = typedQuery4_1.getResultList();
//			for(String mem : members4_1) {
//				logger.debug("== jpql4_1 쿼리 조회 회원객체=" + mem);
//			}
//			
//			//집합 관련 
//			//null 값은 무시되어 통계에 잡히지 않는다.
//			//distinct를 집합 함수 안에 사용해서 중복된 값을 없애고 집합을 구할 수 있다.
//			//distinct를 count에서 사용할때 임베디드 타입은 지원하지 않는다.
//			logger.debug("================================jpql5===================================");
//			String jpql5 = "select avg(o.orderAmount), count(o.orderAmount), max(o.orderAmount), min(o.orderAmount) from Order8 o";
//			
//			TypedQuery<Object[]> typedQuery5 = em.createQuery(jpql5,Object[].class);
//			
//			List<Object[]> result = typedQuery5.getResultList();
//			for(Object[] item : result) {
//				logger.debug("== jpql5 쿼리 조회=" + item[0] + "," + item[1] + "," + item[2] + "," + item[3]);
//			}
//			
//			Object[] resultSingle = typedQuery5.getSingleResult();
//			logger.debug("== jpql5 쿼리 단일조회=" + resultSingle[0] + ", " + resultSingle[1] + ", " + resultSingle[2] + ", " + resultSingle[3]);
//			
//			
//
//			logger.debug("================================jpql6===================================");
//			String jpql6 = "select new com.orm.pure.jpa.ex08.dto.MemberDto(m.id, m.name)  from Member8 m";
//			
//			TypedQuery<MemberDto> typedQuery6 = em.createQuery(jpql6,MemberDto.class);
//			
//			List<MemberDto> result6 = typedQuery6.getResultList();
//			for(MemberDto item : result6) {
//				logger.debug("== jpql6 쿼리 조회=" + item.getId());
//			}
//			
//
//			logger.debug("================================jpql7===================================");
//			String jpql7 = "select new com.orm.pure.jpa.ex08.dto.MemberDto(m.id, m.name)  from Member8 m";
//			
//			//[Mysql] select member8x0_.member_id as col_0_0_, member8x0_.name as col_1_0_ from member8 member8x0_ limit ?, ?
//			TypedQuery<MemberDto> typedQuery7 = em.createQuery(jpql7,MemberDto.class);
//			
//			typedQuery7.setFirstResult(1);
//			typedQuery7.setMaxResults(2);
//			
//			List<MemberDto> result7 = typedQuery7.getResultList();
//			for(MemberDto item : result7) {
//				logger.debug("== jpql7 쿼리 조회=" + item.getId());
//			}
//			
//			logger.debug("================================jpql8===================================");
//			String jpql8 = "select m from Member8 m inner join m.team t where t.id=:teamId"; //inner 생략가능
//			
//			List<Member8> result8 = em.createQuery(jpql8)
//					.setParameter("teamId", 5L ).getResultList();
//			
//			for(Member8 item : result8) {
//				logger.debug("== jpql8 쿼리 조회=" + item.getId());
//			}
//					
//			
//			String jpql8_1 = "select m,t from Member8 m inner join m.team t where t.id=:teamId"; //inner 생략가능
//			
//			List<Object[]> result8_1 = em.createQuery(jpql8_1)
//					.setParameter("teamId", 5L ).getResultList();
//			for(Object[] item : result8_1) {
//				
//				Member8 member = (Member8) item[0];
//				Team8 team = (Team8) item[1];
//				logger.debug("== jpql8_1 쿼리 조회=" + member.getId()+ ", " + team.getId());
//			}
//			
//			//cross 조인
//			String jpql8_2 = "select m,t from Member8 m, Team8 t where m.id = t.id"; //inner 생략가능
//			
//			List<Object[]> result8_2 = em.createQuery(jpql8_2).getResultList();
//			
//			if(result8_2 == null || result8_2.size() == 0) {
//				logger.debug("데이터 없음!!");
//			}else {
//				for(Object[] item : result8_2) {
//					
//					Member8 member = (Member8) item[0];
//					Team8 team = (Team8) item[1];
//					logger.debug("== jpql8_2 쿼리 조회=" + member.getId()+ ", " + team.getId());
//				}
//			}
//			
//			
//			
//			//outer join
//			String jpql8_3 = "select m,t from Member8 m left outer join m.team t on m.id = :memberId"; //inner 생략가능
//			
//			List<Object[]> result8_3 = em.createQuery(jpql8_3)
//					.setParameter("memberId", 6L).getResultList();
//			
//			if(result8_3 == null || result8_3.size() == 0) {
//				logger.debug("데이터 없음!!");
//			}else {
//				for(Object[] item : result8_3) {
//					
//					Member8 member = (Member8) item[0];
//					Team8 team = (Team8) item[1];
//					
//					if(member != null && team != null) {
//						logger.debug("== result8_3 쿼리 조회=" + member.getId()+ ", " + team.getId());						
//					}else {
//						logger.debug("없음!!!!");
//					}
//				}
//			}
//			
//			//fetch 조인
//			
//			/*
//			 * 연관된 엔티티나 컬렉션을 한번 같이 조회 
//			 * inner join
//			 * 페치 조인 엔티티 별칭 사용 불가
//			 * 실제 엔티티 (프록시 x), 
//			 * 일반 inner join 은 반환타입 엔티티 값만 조회됨 
//			 * 글로벌로딩전략 보다 우선순위 높음!
//			 * 컬렉션 페치 조인 시 페이징 API 사용 X
//			 * <-> DTO
//			 */
//			
//			String jpql9 = "select m from Member8 m join fetch m.team";
//			
//			List<Member8> members = em.createQuery(jpql9).getResultList();
//			
//			for(Member8 mem : members) {
//				logger.debug("= 페치조인 회원조회"+ mem.getId() + ", " + mem.getTeam().getName());
//			}
//			
//			//컬렉션 페치 조회
//			String jpql9_2 = "select distinct t from Team8 t join fetch t.members";
//			
//			List<Team8> teams = em.createQuery(jpql9_2).getResultList();
//			
//			logger.debug("###" + teams.size() + "");
//			
//			for(Team8 team : teams) {
//				logger.debug("= 페치조인 팀조회"+ team.getId());
//				for(Member8 mem : team.getMembers()) {
//					logger.debug("= 페치조인 팀내 회원조회"+ mem.getId());
//				}
//			}
//			
//			//경로표현식
//			//상태필드경로, 단일값연관경로, 커렉션값 연관경로
//			
//			String jpql10 = "select m.team from Member8 m";
//			String jpql11 = "select t.members from Team8 t";
//			String jpql12 = "select t.members.name from Team8 t"; //오류
//			String jpql13 = "select m.name from Team8 t join t.members m";
			
			//서브쿼리(Where, having 절에만 사용가능)
			//서브쿼리함수(EXISTS, ALL/ANY/SOME)
			
			//NamedQuery
			//xml 정의도 가능 (XML 우선권가짐)
			List<Member8> members10 = em.createNamedQuery("Member8.findByName",Member8.class).setParameter("username", "홍길동1").getResultList();
			for(Member8 mem : members10) {
				logger.debug("= 네임드쿼리 회원조회"+ mem.getId());
			}
			
			
			tx.commit();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			tx.rollback();
		} finally {
			em.close();
		}
	}

	public static void testCriteria(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		logger.debug("================================testCriteria===================================");
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			
//			//Criteria Query 생성, 반환타입 지정
//			CriteriaQuery<Member8> cq = cb.createQuery(Member8.class);
//			
//			//반환타입 미지정 및 반환타입이 2개이상 (Object[])
//			//CriteriaQuery<Object> cq = cb.createQuery();
//
//			//반환타입 투플
//			//CriteriaQuery<Tuple> cq = cb.createTupleQuery();
//			
//			//쿼리루트(=조회 시작점) m: 별칭 *엔티티에만 별칭을 붙일 수 있다. 
//			Root<Member8> m = cq.from(Member8.class);
//			
//			//검색조건
//			Predicate equalId = cb.equal(m.get("id"), 1L);
//			
//			//추가 검색조건 (제네릭으로 반환타입정보 명시)
//			Predicate ageGt = cb.gt(m.<Integer>get("age"), 10);
//			
//			//정렬조건
//			Order IdDesc = cb.desc(m.get("id"));
//			
//			//SELECT
//			cq.select(m)
//			.where(equalId)		//where절
//			.where(ageGt)
//			.orderBy(IdDesc);		//order절
//			
//			//Criteria Query에서 반환타입을 명시해서 별도 반환타입 명시 x
//			TypedQuery<Member8> query = em.createQuery(cq);
//			List<Member8> member = query.getResultList();
//			
//			//기본회원조회
//			member.stream().forEach(x -> logger.debug("[Criteria] 회원조회" + x.getId() + "이름 : " + x.getName() + ", 나이:" + x.getAge()));
//			
//			logger.debug("===================================================================");
//			
//			CriteriaQuery<Object[]> cq2 = cb.createQuery(Object[].class);
//			
//			Root<Member8> m2 = cq2.from(Member8.class);
//						
//			//정렬조건
//			Order IdDesc2 = cb.desc(m2.get("id"));
//			
//			//select
//			//여러건 지정
////			cq2.multiselect(m2.get("id"), m2.get("name"))
////			.orderBy(IdDesc2);		//order절
//			
//			//위에와 동일
//			cq2.select(cb.array(m2.get("id"), m2.get("name")))
//			.orderBy(IdDesc2);		//order절
//
//			//Criteria Query에서 반환타입을 명시해서 별도 반환타입 명시 x
//			TypedQuery<Object[]> query2 = em.createQuery(cq2);
//			List<Object[]> member2 = query2.getResultList();
//			
//			//기본회원조회
//			member2.stream().forEach(x -> logger.debug("[Criteria] 회원조회 " + x[0] + " 이름 : " + x[1]));
//			logger.debug("===================================================================");
//			
//			CriteriaQuery<Object> cq3 = cb.createQuery(Object.class);
//			
//			Root<Member8> m3 = cq3.from(Member8.class);
//			
//			//distinct 적용
//			cq3.select(m3.get("name"))
//			.distinct(true);
//			
//
//			//Criteria Query에서 반환타입을 명시해서 별도 반환타입 명시 x
//			TypedQuery<Object> query3 = em.createQuery(cq3);
//			List<Object> member3 = query3.getResultList();
//			
//			//기본회원조회
//			member3.stream().forEach(x -> logger.debug("[Criteria] 회원조회 이름 : " + x));
//			
//			logger.debug("===================================================================");
//			
//			CriteriaQuery<MemberDto> cq4 = cb.createQuery(MemberDto.class);
//			Root<Member8> m4 = cq4.from(Member8.class);
//			
//			cq4.select(cb.construct(MemberDto.class, m4.get("id"), m4.get("name"),m4.get("age")));
//			
//			TypedQuery<MemberDto> query4 = em.createQuery(cq4);
//			List<MemberDto> mem4 = query4.getResultList();
//			
//			mem4.stream().forEach(x -> logger.debug("생성자를 통한 조회=" + x.getId() + ", " + x.getName()));
//
//			logger.debug("===================================================================");
//			
//			// 가장 나이가 많은 사람 과 적은 사람 조회
//			CriteriaQuery<Object[]> cq5 = cb.createQuery(Object[].class);
//			Root<Member8> m5 = cq5.from(Member8.class);
//			
//			Expression maxAge = cb.max(m5.<Integer>get("age"));			
//			Expression minAge = cb.min(m5.<Integer>get("age"));		
//			
//			//cq5.multiselect(m5.get("team").get("name"),maxAge, minAge);
//			//cq5.groupBy(m5.get("team").get("name"));
//			//cq5.having(cb.gt(minAge, 4)); //m.age > 4
//			//cq5.orderBy(cb.desc(m5.get("age")));
//
//			cq5.multiselect(m5.get("team").get("name"), m5.get("name"));
//			cq5.orderBy(cb.desc(m5.get("age")));
//			
//			TypedQuery<Object[]> query5 = em.createQuery(cq5);
//			List<Object[]> mem5 = query5.getResultList();
//			
//			//mem5.stream().forEach(x -> logger.debug("생성자를 통한 조회=" + x[0]+",최대=" + x[1] + ",최소="  + x[2]));
//			mem5.stream().forEach(x -> logger.debug("생성자를 통한 조회=" + x[0]+", " + x[1] ));
//			logger.debug("===================================================================");
			
			//Criteria를 이용한 JOIN
			//JoinByCriteria(em, cb);
			
			//상호관련없는서브쿼리
			//subQueryByCriteria(em, cb);
			//상호관련있는서브쿼리
			//subQueryByCriteria2(em, cb);
			
			//in식
			//inByCriteria(em, cb);
			
			//case
			//CaseByCriteria(em, cb);
			
			//parameter
			ParameterAndMetaModelByCriteria(em,cb);
		
			
			logger.debug("===================================================================");
			
			
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			em.close();
		}
	}
	

	/**
	 * Member8 INNER/LEFT,RIGHT JOIN Team8
	 * @param em
	 * @param cb
	 */
	@SuppressWarnings("null")
	public static void JoinByCriteria (EntityManager em, CriteriaBuilder cb) {
		logger.debug("===================================================================");
		/*
		 *	select m, t
		 * from m inner join m.team  t
		 * where t.name='팀0'
		 */

		CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
		Root<Member8> m = cq.from(Member8.class);
				
		Join<Member8, Team8> t = m.join("team", JoinType.INNER); 	//내부조인
//		Join<Member8, Team8> t = m.join("team");							//내부조인
//		Join<Member8, Team8> t = m.join("team", JoinType.LEFT);		//외부조인
//		Join<Member8, Team8> t = m.join("team", JoinType.RIGHT);		//외부조인 ( Mysql 지원하지않음)
		
		cq.multiselect(m, t);
		
//		cq.multiselect(m, t)
//		.where(cb.equal(t.get("name"), "팀0"));
		

		TypedQuery<Object[]> query = em.createQuery(cq);
		List<Object[]> result = query.getResultList();

		if(result != null || result.size() > 0) {
			for(Object[] obj :  result) {

				String memberName = "";
				String teamName = "";
				
				if(obj[0] != null) {
					memberName = ((Member8) obj[0]).getName();
				}
				
				if(obj[1] != null) {
					teamName = ((Team8) obj[1]).getName();
				}
				
				logger.debug("[Criteria Join] member=" + memberName + ", team=" + teamName);
			}
		}
	}
	

	/**
	 * Member8 FETCH JOIN Team8
	 * @param em
	 * @param cb
	 */
	public static void fetchJoinByCriteria (EntityManager em, CriteriaBuilder cb) {
		logger.debug("===================================================================");
		/*
		 *	select m
		 * from m join fetch m.team
		 */

		CriteriaQuery<Member8> cq = cb.createQuery(Member8.class);
		Root<Member8> m = cq.from(Member8.class);
		
		m.fetch("team",JoinType.LEFT);
		

		TypedQuery<Member8> query = em.createQuery(cq);
		List<Member8> result = query.getResultList();
		
		result.stream().forEach(x -> logger.debug("[Criteria Fetch Join] member=" + x.getName() + ", team=" + x.getTeam().getName()));
		
	}
	/**
	 * Example Of SubQuery By Criteria
	 * @param em
	 * @param cb
	 */
	public static void subQueryByCriteria (EntityManager em, CriteriaBuilder cb) {
		logger.debug("===================================================================");
		/*
		 *	select m.name
		 * from m
		 * where m.age > (select avg(m.age) from Member8 m
		 */

		//main query
		CriteriaQuery<Member8> cq = cb.createQuery(Member8.class);
		//sub query
		Subquery<Double> subQuery = cq.subquery(Double.class);
		Root<Member8> m2 =subQuery.from(Member8.class);
		subQuery.select(cb.avg(m2.get("age")));
		
		Root<Member8> m = cq.from(Member8.class);
		
		cq.select(m)
		.where(cb.ge(m.<Integer>get("age"),subQuery));

		TypedQuery<Member8> query = em.createQuery(cq);
		List<Member8> result = query.getResultList();
		
		result.stream().forEach(x -> logger.debug("[Criteria SubQuery] member=" + x.getName() + ", age=" + x.getAge()));
		
	}
	
	
	/**
	 * Example Of SubQuery By Criteria 2 ( 상호관련서브쿼리)
	 * @param em
	 * @param cb
	 */
	public static void subQueryByCriteria2 (EntityManager em, CriteriaBuilder cb) {
		logger.debug("===================================================================");
		/*
		 *	select m from Member8 m
		 * where exists (select t from m.team t where t.name='팀0';
		 */

		//main query
		CriteriaQuery<Member8> cq = cb.createQuery(Member8.class);
		Root<Member8> m = cq.from(Member8.class);

		//sub query
		Subquery<Team8> subQuery = cq.subquery(Team8.class);
		Root<Member8> subM = subQuery.correlate(m); // 메인쿼리의 별칭 가져옴
		Join<Member8, Team8> t = subM.join("team");
				
		subQuery.select(t)
		.where(cb.equal(t.get("name"), "팀1"));
		
		cq.select(m)
		.where(cb.exists(subQuery));

		TypedQuery<Member8> query = em.createQuery(cq);
		List<Member8> result = query.getResultList();
		
		result.stream().forEach(x -> logger.debug("[Criteria SubQuery2] member=" + x.getName() + ", age=" + x.getAge() + ", team=" + x.getTeam().getName()));
		
	}
	
	/**
	 * Example Of In
	 * @param em
	 * @param cb
	 */
	public static void inByCriteria (EntityManager em, CriteriaBuilder cb) {
		logger.debug("===================================================================");
		/*
		 *	select m from Member8 m
		 * where m.name in ('홍길동0','홍길동1');
		 */

		//main query
		CriteriaQuery<Member8> cq = cb.createQuery(Member8.class);
		Root<Member8> m = cq.from(Member8.class);

		cq.select(m)
		.where(cb.in(m.get("name"))
				.value("홍길동0"));
		
		TypedQuery<Member8> query6 = em.createQuery(cq);
		List<Member8> result = query6.getResultList();
		
		result.stream().forEach(x -> logger.debug("[Criteria In] member=" + x.getName() + ", age=" + x.getAge()));
		
	}
	
	
	/**
	 * Case 식
	 * @param em
	 * @param cb
	 */
	public static void CaseByCriteria (EntityManager em, CriteriaBuilder cb) {
		logger.debug("===================================================================");
		/*
		 *	select m.name, 
		 *    case when m.age >= 60 then 600
		 *    when m.age <= 15 then 500
		 *    else 1000
		 * from Member8 m
		 */

		//main query
		CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
		Root<Member8> m = cq.from(Member8.class);

		cq.multiselect(m.get("name"),
				cb.selectCase()
				.when(cb.ge(m.<Integer>get("age"), 60),600)
				.when(cb.le(m.<Integer>get("age"),15), 500)
				.otherwise(1000));
		
		TypedQuery<Object[]> query6 = em.createQuery(cq);
		List<Object[]> result = query6.getResultList();
		
		result.stream().forEach(x -> logger.debug("[Criteria Case] member=" + x[0]+ ", age=" + x[1]));
		
	}
	
	

	/**
	 * Parameter + Meta Model 사용
	 * @param em
	 * @param cb
	 */
	public static void ParameterAndMetaModelByCriteria (EntityManager em, CriteriaBuilder cb) {
		logger.debug("===================================================================");
		/*
		 *	select m
		 * from Member8 m
		 * where name = :userName
		 */

		//main query
		CriteriaQuery<Member8> cq = cb.createQuery(Member8.class);
		Root<Member8> m = cq.from(Member8.class);

		cq.select(m)
		.where(cb.equal(m.get(Member8_.name), cb.parameter(String.class, "userName")));
		
		
		TypedQuery<Member8> query6 = em.createQuery(cq).setParameter("userName", "홍길동1");
		List<Member8> result = query6.getResultList();
		
		result.stream().forEach(x -> logger.debug("[Criteria Case] member=" + x.getName()+ ", age=" + x.getName()));
		
	}
	
}
