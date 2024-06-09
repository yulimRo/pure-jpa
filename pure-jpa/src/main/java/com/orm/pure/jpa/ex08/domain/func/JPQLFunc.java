package com.orm.pure.jpa.ex08.domain.func;

import com.orm.pure.jpa.ex01.domain.Member;
import com.orm.pure.jpa.ex08.domain.Member8;
import com.orm.pure.jpa.ex08.domain.Order8;
import com.orm.pure.jpa.ex08.domain.Product8;
import com.orm.pure.jpa.ex08.domain.Team8;
import com.orm.pure.jpa.ex08.dto.MemberDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.List;

public class JPQLFunc {

    private static final Logger logger = LoggerFactory.getLogger(JPQLFunc.class);

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

    public static void basicSelectMember(EntityManagerFactory emf) {
        logger.debug("================basicSelectMember===================");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            logger.debug("================기본 회원 조회");
            //회원조회
			String jpql = "select m.id, m.name from Member8 m";
            //반환값미정
			Query query = em.createQuery(jpql);
			//반환값을 모르기 때문에 반환타입 Object[]
			List<Object[]> members = query.getResultList();

			for(Object[] obj : members) {
				logger.debug("==회원객체=" + obj[0]+ ", 이름=" + obj[1]);
			}

            logger.debug("================Order 테이블의 Product 엔티티 조회");
            //엔티티조회
			String jpql1_2 = "select o.product from Order8 o";
            //반환타입미정
			List<Object> objs = em.createQuery(jpql1_2).getResultList();

			for(Object obj : objs) {
				Product8 order = (Product8) obj;
				logger.debug("==상품객체=" + order.getId());
			}

            logger.debug("================반환값명시하여 Member8 엔티티 조회");
			String jpql2 = "select m8 from Member8 m8";

			//반환타입 명확할 때
			//jpql2 반환타입으로 Object[] 시 ClassCaseException
			TypedQuery<Member8> typedQuery = em.createQuery(jpql2,Member8.class);

			List<Member8> members2 = typedQuery.getResultList();
			for(Member8 mem : members2) {
				logger.debug("== List<Member8>타입 회원객체=" + mem.getId() + "," + mem.getName());
			}


            logger.debug("================위치기반/이름기반 파라미터 바인딩");
			//위치기반파라미터
			String jpql3 = "select m8 from Member8 m8 where m8.name = ?1";
			TypedQuery<Member8> typedQuery3 = em.createQuery(jpql3,Member8.class).setParameter(1, "홍길동1");
			List<Member8> members3 = typedQuery3.getResultList();
			for(Member8 mem : members3) {
				logger.debug("==[위치기반파라미터] 회원객체=" + mem.getId() + "," + mem.getName());
			}

			//이름기준파라미터
			String jpql4 = "select m8 from Member8 m8 where m8.name = :userName";
			TypedQuery<Member8> typedQuery4 = em.createQuery(jpql4,Member8.class).setParameter("userName", "홍길동2");
			List<Member8> members4 = typedQuery4.getResultList();
			for(Member8 mem : members4) {
				logger.debug("==[이름기반파라미터] 회원객체=" + mem.getId() + "," + mem.getName());
			}

            logger.debug("================distinct 적용 회원 조회");
			//distinct(객체자체엔적용x)
			String jpql4_1 = "select distinct m8.name from Member8 m8 where m8.name = :userName";
			TypedQuery<String> typedQuery4_1 = em.createQuery(jpql4_1,String.class).setParameter("userName", "홍길동2");
			List<String> members4_1 = typedQuery4_1.getResultList();
			for(String mem : members4_1) {
				logger.debug("회원객체=" + mem);
			}

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            em.close();
        }

    }

    public static void selectStatisticsData(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        logger.debug("================================selectStatisticsData===================================");
        try {

            /*
                집합관련함수
                - null값은 무시되어 통계에 포함X
                - distinct를 집합 함수 안에 사용해서 중복된 값을 없애고 집합을 구할 수 있다.
                - distinct를 count에서 사용할때 임베디드 타입은 지원하지 않는다.
             */
			String jpql5 = "select avg(o.orderAmount), count(o.orderAmount), max(o.orderAmount), min(o.orderAmount) from Order8 o";

			TypedQuery<Object[]> typedQuery5 = em.createQuery(jpql5,Object[].class);

			List<Object[]> result = typedQuery5.getResultList();
			for(Object[] item : result) {
				logger.debug("== 통계 조회=" + item[0] + "," + item[1] + "," + item[2] + "," + item[3]);
			}

			Object[] resultSingle = typedQuery5.getSingleResult();
			logger.debug("== 통계 단일조회=" + resultSingle[0] + ", " + resultSingle[1] + ", " + resultSingle[2] + ", " + resultSingle[3]);


        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            em.close();
        }

    }


    public static void selectMemberDTO(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            logger.debug("================================selectMemberDTO===================================");
            String jpql6 = "select new com.orm.pure.jpa.ex08.dto.MemberDto(m.id, m.name,m.age)  from Member8 m";

            TypedQuery<MemberDto> typedQuery6 = em.createQuery(jpql6, MemberDto.class);

            List<MemberDto> result6 = typedQuery6.getResultList();
            for (MemberDto item : result6) {
                logger.debug("== MemberDto 조회=" + item.getId());
            }


            logger.debug("================================selectMemberDTOWithPaging===================================");
            String jpql7 = "select new com.orm.pure.jpa.ex08.dto.MemberDto(m.id, m.name,m.age)  from Member8 m";

            //[Mysql] select member8x0_.member_id as col_0_0_, member8x0_.name as col_1_0_ from member8 member8x0_ limit ?, ?
            TypedQuery<MemberDto> typedQuery7 = em.createQuery(jpql7, MemberDto.class);

            typedQuery7.setFirstResult(0);
            typedQuery7.setMaxResults(2);

            List<MemberDto> result7 = typedQuery7.getResultList();
            for (MemberDto item : result7) {
                logger.debug("== MemberDTO 페이징 조회=" + item.getId());
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }


    public static void selectJoinEntity(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {

            logger.debug("================================inner join(반환타입:Member8)===================================");
			String jpql8 = "select m from Member8 m inner join m.team t where t.id=:teamId"; //inner 생략가능

			List<Member8> result8 = em.createQuery(jpql8).setParameter("teamId", 1L ).getResultList();

			for(Member8 item : result8) {
				logger.debug("==회원 inner join 팀 (team_id=1L)" + item.getId());
			}

            logger.debug("================================inner join(반환타입:Object[])===================================");
			String jpql8_1 = "select m,t from Member8 m join m.team t where t.id=:teamId"; //inner 생략가능

            //엔티티 두개 조회 -> TypedQuery 사용 X
			List<Object[]> result8_1 = em.createQuery(jpql8_1).setParameter("teamId", 1L).getResultList();

			for(Object[] item : result8_1) {
				Member8 member = (Member8) item[0];
				Team8 team = (Team8) item[1];
				logger.debug("== 회원 inner join 팀 (team_id=1L, 프로펙션 m,t)=" + member.getId()+ ", " + team.getId());
			}

            logger.debug("================================cross join(반환타입:Object[])===================================");

            String jpql8_2 = "select m,t from Member8 m, Team8 t where m.team.id = t.id";

			List<Object[]> result8_2 = em.createQuery(jpql8_2).getResultList();

			if(result8_2 == null || result8_2.size() == 0) {
				logger.debug("데이터 없음!!");
			}else {
				for(Object[] item : result8_2) {
					Member8 member = (Member8) item[0];
					Team8 team = (Team8) item[1];
					logger.debug("== Member8 cross join Team8 조회=" + member.getId()+ ", " + team.getId());
				}
			}

            logger.debug("================================outer join(반환타입:Object[])===================================");

			String jpql8_3 = "select m,t from Member8 m left outer join m.team t on m.id = :memberId";

			List<Object[]> result8_3 = em.createQuery(jpql8_3).setParameter("memberId", 1L).getResultList();

			if(result8_3 == null || result8_3.size() == 0) {
				logger.debug("데이터 없음!!");
			}else {
				for(Object[] item : result8_3) {

					Member8 member = (Member8) item[0];
					Team8 team = (Team8) item[1];

					if(member != null && team != null) {
						logger.debug("== Member8 left outre Team8 조회=" + member.getId()+ ", " + team.getId());
					}else {
                        logger.debug("== Member8 left outre Team8 조회=" + member.getId());
					}
				}
			}


			//fetch 조인

            /*
			 * 연관된 엔티티나 컬렉션을 한번 같이 조회
			 * inner join
			 * 페치 조인 엔티티 별칭 사용 불가
			 * 실제 엔티티 (프록시 x),
			 * 일반 inner join 은 반환타입 엔티티 값만 조회됨
			 * 글로벌로딩전략 보다 우선순위 높음!
			 * 컬렉션 페치 조인 시 페이징 API 사용 X
			 * <-> DTO
			 */

            logger.debug("================================fetch join(반환타입:Member8)===================================");
            String jpql9 = "select m from Member8 m join fetch m.team";

            List<Member8> members = em.createQuery(jpql9).getResultList();

			for(Member8 mem : members) {
				logger.debug("= 페치조인 회원조회"+ mem.getId() + ", " + mem.getTeam().getName());
			}

			//컬렉션 페치 조회
			String jpql9_2 = "select distinct t from Team8 t join fetch t.members";

			List<Team8> teams = em.createQuery(jpql9_2).getResultList();

			logger.debug("###" + teams.size() + "");

			for(Team8 team : teams) {
				logger.debug("= 페치조인 팀조회"+ team.getId());
				for(Member8 mem : team.getMembers()) {
					logger.debug("= 페치조인 팀내 회원조회"+ mem.getId());
				}
			}

        } catch (Exception ex) {
            tx.rollback();
        } finally {
            em.close();
        }

    }
    public static void pathExpression(EntityManagerFactory emf) {

        logger.debug("================================pathExpression===================================");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            /*
                경로표현식
                - 상태필드경로 : 값 저장하기 위한 필드
                - 연관필드 (단일 값 연관필드) :One의 대상인 엔티티
                - 연관필드 (컬렉션 값 연관필드) : Many의 대상인 엔티티
             */
			String jpql0 = "select m.name from Member8 m"; //상태 필드
            List<Object> result0 = em.createQuery(jpql0).getResultList();
            result0.stream().forEach(x -> logger.debug("상태필드경로조회" + x));


			String jpql1 = "select m.team from Member8 m"; //묵시적 내부조인(계속탐색가능)

            List<Object> result1 = em.createQuery(jpql1).getResultList();
            result1.stream().forEach(x -> logger.debug("단일값 연관필드조회" + ((Team8) x).getName() ));

			String jpql2 = "select t.members from Team8 t"; //묵시적 내부조인

            //select members1_.member_id as member_i1_0_, members1_.age as age2_0_, members1_.name as name3_0_, members1_.team_id as team_id4_0_ from team8 team8x0_ inner join member8 members1_ on team8x0_.team_id=members1_.team_id
            List<Member8> result2 = em.createQuery(jpql2).getResultList();
            result2.stream().forEach(x -> logger.debug("컬렉션값 연관필드조회" + x.getName()));

            //오류
			String jpql3 = "select t.members.name from Team8 t"; //오류, 컬렉션 값 연관 경로는 이후 탐색 불가

			String jpql4 = "select m.name from Team8 t join t.members m"; //컬렉션 값 연관 경로는 별칭얻으면 가능
            List<String> result4 = em.createQuery(jpql4).getResultList();
            result4.stream().forEach(x -> logger.debug("컬렉션값 연관필드의 내부필드값조회" + x));
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
        } finally {
            em.close();
        }

    }

    public static void selectSubQuery(EntityManagerFactory emf) {

        logger.debug("================================selectSubQuery===================================");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            //팀1 소속인 회원
            String jpql = "select m from Member8 m where exists (select t from m.team t where t.name='팀1')";
            List<Member8> members = em.createQuery(jpql).getResultList();
            members.stream().forEach(x -> logger.debug("서브쿼리(EXISTS) 회원조회" + x.getId()));


            //전체 상품 각각의 재고보다 주문량이 많은 주문들
            String jpql2 = "select o from Order8 o where o.orderAmount > ALL (select p.stockAmount from Product8 p)";
            List<Order8> orders = em.createQuery(jpql2).getResultList();
            orders.stream().forEach(x -> logger.debug("서브쿼리(ALL) 회원조회" + x.getId()));

            //NamedQuery
            //xml 정의도 가능 (XML 우선권가짐)
			List<Member8> members10 = em.createNamedQuery("Member8.findByName",Member8.class).setParameter("username", "홍길동1").getResultList();
			for(Member8 mem : members10) {
				logger.debug("= 네임드쿼리 회원조회"+ mem.getId());
			}


        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
        } finally {
            em.close();
        }

    }


    public static void selectConditionExpression(EntityManagerFactory emf) {

        logger.debug("================================selectConditionExpression===================================");
        EntityManager em = emf.createEntityManager();
        try {

            String jpql = "select m from Member8 m where m.age between 15 and 30 and m.name like '%2%' and m.team is not null";
            List<Member8> members = em.createQuery(jpql).getResultList();

            members.stream().forEach(x -> logger.debug("잡다한 조건문 회원조회=" + x.getId()));


            String jpql2 = "select m from Member8 m where m.orders is not empty and :orderParam member of m.orders";
            Order8 order8 = em.find(Order8.class, 1L);
            List<Member8> members2 = em.createQuery(jpql2).setParameter("orderParam",order8).getResultList();

            String jpql3 = "select " +
                    "case when m.age <= 10 then '학생요금'" +
                    "when m.age >= 60 then '어른요금'" +
                    "else '일반요금'" +
                    "end from Member8 m";
            List<String> members3 = em.createQuery(jpql3).getResultList();
            members3.stream().forEach(x -> logger.debug("CASE식 적용 조회=" + x));

            //name이 NULL이면
            String jpql4 = "select coalesce(m.name,'빵꾸') from Member8 m";
            List<String> members4 = em.createQuery(jpql4).getResultList();
            members4.stream().forEach(x -> logger.debug("COALESCE 적용 조회=" + x));
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
        } finally {
            em.close();
        }

    }
    public static void selectNamedQuery(EntityManagerFactory emf) {

        logger.debug("================================selectNamedQuery===================================");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            //NamedQuery
            //xml 정의도 가능 (XML 우선권가짐)
            List<Member8> members = em.createNamedQuery("Member8.findByName", Member8.class).setParameter("username", "홍길동1").getResultList();
            members.stream().forEach(x -> logger.debug("Member8.findByName(네임드쿼리) 회원조회" + x.getId()));


        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            em.close();
        }

    }

}
