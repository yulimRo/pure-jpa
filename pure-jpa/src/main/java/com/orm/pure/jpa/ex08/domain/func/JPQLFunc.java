package com.orm.pure.jpa.ex08.domain.func;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class JPQLFunc {

    private static final Logger logger = LoggerFactory.getLogger(JPQLFunc.class);


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
//			List<Member8> members10 = em.createNamedQuery("Member8.findByName",Member8.class).setParameter("username", "홍길동1").getResultList();
//			for(Member8 mem : members10) {
//				logger.debug("= 네임드쿼리 회원조회"+ mem.getId());
//			}
//

            tx.commit();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            tx.rollback();
        } finally {
            em.close();
        }
    }

}
