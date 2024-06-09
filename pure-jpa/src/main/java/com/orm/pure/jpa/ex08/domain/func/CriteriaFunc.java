package com.orm.pure.jpa.ex08.domain.func;

import com.orm.pure.jpa.ex08.domain.Member8;
import com.orm.pure.jpa.ex08.domain.Member8_;
import com.orm.pure.jpa.ex08.domain.Team8;
import com.orm.pure.jpa.ex08.dto.MemberDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class CriteriaFunc {
    private static final Logger logger = LoggerFactory.getLogger(CriteriaFunc.class);


    /**
     * Member8 INNER/LEFT,RIGHT JOIN Team8
     *
     * @param em
     * @param cb
     */
    @SuppressWarnings("null")
    public static void JoinByCriteria(EntityManager em, CriteriaBuilder cb) {
        logger.debug("===================================================================");
        /*
         *	select m, t
         * from m inner join m.team  t
         * where t.name='팀0'
         */

        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Member8> m = cq.from(Member8.class);

        Join<Member8, Team8> t = m.join("team", JoinType.INNER);    //내부조인
//		Join<Member8, Team8> t = m.join("team");						//내부조인
//		Join<Member8, Team8> t = m.join("team", JoinType.LEFT);		    //외부조인
//		Join<Member8, Team8> t = m.join("team", JoinType.RIGHT);		//외부조인 ( Mysql 지원하지않음)

        cq.multiselect(m, t);

//		cq.mul tiselect(m, t)
//		.where(cb.equal(t.get("name"), "팀0"));


        TypedQuery<Object[]> query = em.createQuery(cq);
        List<Object[]> result = query.getResultList();

        if (result != null || result.size() > 0) {
            for (Object[] obj : result) {

                String memberName = "";
                String teamName = "";

                if (obj[0] != null) {
                    memberName = ((Member8) obj[0]).getName();
                }

                if (obj[1] != null) {
                    teamName = ((Team8) obj[1]).getName();
                }

                logger.debug("[Criteria Join] member=" + memberName + ", team=" + teamName);
            }
        }
    }


    /**
     * Member8 FETCH JOIN Team8
     *
     * @param em
     * @param cb
     */
    public static void fetchJoinByCriteria(EntityManager em, CriteriaBuilder cb) {
        logger.debug("===================================================================");
        /*
         *	select m
         * from m join fetch m.team
         */

        CriteriaQuery<Member8> cq = cb.createQuery(Member8.class);
        Root<Member8> m = cq.from(Member8.class);

        m.fetch("team", JoinType.LEFT);


        TypedQuery<Member8> query = em.createQuery(cq);
        List<Member8> result = query.getResultList();

        result.stream().forEach(x -> logger.debug("[Criteria Fetch Join] member=" + x.getName() + ", team=" + x.getTeam().getName()));

    }

    /**
     * Example Of SubQuery By Criteria
     *
     * @param em
     * @param cb
     */
    public static void subQueryByCriteria(EntityManager em, CriteriaBuilder cb) {
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
        Root<Member8> m2 = subQuery.from(Member8.class);
        subQuery.select(cb.avg(m2.get("age")));

        Root<Member8> m = cq.from(Member8.class);

        cq.select(m)
                .where(cb.ge(m.<Integer>get("age"), subQuery));

        TypedQuery<Member8> query = em.createQuery(cq);
        List<Member8> result = query.getResultList();

        result.stream().forEach(x -> logger.debug("[Criteria SubQuery] member=" + x.getName() + ", age=" + x.getAge()));

    }


    /**
     * Example Of SubQuery By Criteria 2 ( 상호관련서브쿼리)
     *
     * @param em
     * @param cb
     */
    public static void subQueryByCriteria2(EntityManager em, CriteriaBuilder cb) {
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
     *
     * @param em
     * @param cb
     */
    public static void inByCriteria(EntityManager em, CriteriaBuilder cb) {
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
     *
     * @param em
     * @param cb
     */
    public static void CaseByCriteria(EntityManager em, CriteriaBuilder cb) {
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
                        .when(cb.ge(m.<Integer>get("age"), 60), 600)
                        .when(cb.le(m.<Integer>get("age"), 15), 500)
                        .otherwise(1000));

        TypedQuery<Object[]> query6 = em.createQuery(cq);
        List<Object[]> result = query6.getResultList();

        result.stream().forEach(x -> logger.debug("[Criteria Case] member=" + x[0] + ", age=" + x[1]));

    }


    /**
     * Parameter + Meta Model 사용
     *
     * @param em
     * @param cb
     */
    public static void ParameterAndMetaModelByCriteria(EntityManager em, CriteriaBuilder cb) {
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

        result.stream().forEach(x -> logger.debug("[Criteria Case] member=" + x.getName() + ", age=" + x.getName()));

    }



    /**
     * Basic Select
     *
     * @param em
     * @param cb
     */
    public static void basicSelectCriteria(EntityManager em, CriteriaBuilder cb) {
        logger.debug("===================================================================");

        //Criteria Query 생성, 반환타입 지정
        CriteriaQuery<Member8> cq = cb.createQuery(Member8.class);

        //반환타입 미지정 및 반환타입이 2개이상 (Object[])
        //CriteriaQuery<Object> cq = cb.createQuery();
        //반환타입 투플
        //CriteriaQuery<Tuple> cq = cb.createTupleQuery();

        //쿼리루트(=조회 시작점) m: 별칭 *엔티티에만 별칭을 붙일 수 있다.
        Root<Member8> m = cq.from(Member8.class);

        //추가 검색조건 (제네릭으로 반환타입정보 명시)
        Predicate ageGt = cb.gt(m.<Integer>get("age"), 2);

        //정렬조건
        Order IdDesc = cb.desc(m.get("id"));

        //SELECT
        cq.select(m)
        .where(ageGt)		    //where
        .orderBy(IdDesc);		//order절

        //Criteria Query에서 반환타입을 명시해서 별도 반환타입 명시 x
        TypedQuery<Member8> query = em.createQuery(cq);
        List<Member8> member = query.getResultList();

        //기본회원조회
        member.stream().forEach(x -> logger.debug("[Criteria] 회원조회" + x.getId() + "이름 : " + x.getName() + ", 나이:" + x.getAge()));


    }



    /**
     * multiSelect & Condition
     *
     * @param em
     * @param cb
     */
    public static void multiSelectAndConditionCriteria(EntityManager em, CriteriaBuilder cb) {
        logger.debug("===================================================================");

        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

        Root<Member8> m = cq.from(Member8.class);

        //정렬조건
        Order IdDesc2 = cb.desc(m.get("id"));

        //select
        //여러건 지정
        cq.multiselect(m.get("id"), m.get("name"))
        .orderBy(IdDesc2);		//order절

        //위에와 동일
        //cq.select(cb.array(m.get("id"), m.get("name")))
        //.orderBy(IdDesc2);

        //Criteria Query에서 반환타입을 명시해서 별도 반환타입 명시 x
        TypedQuery<Object[]> query2 = em.createQuery(cq);
        List<Object[]> member2 = query2.getResultList();

        //기본회원조회
        member2.stream().forEach(x -> logger.debug("[Criteria] 회원조회 " + x[0] + " 이름 : " + x[1]));


        logger.debug("===================================================================");

        CriteriaQuery<Object> cq3 = cb.createQuery(Object.class);

        Root<Member8> m3 = cq3.from(Member8.class);

        //distinct 적용
        cq3.select(m3.get("name"))
        .distinct(true);

        //Criteria Query에서 반환타입을 명시해서 별도 반환타입 명시 x
        TypedQuery<Object> query3 = em.createQuery(cq3);
        List<Object> member3 = query3.getResultList();

        //기본회원조회
        member3.stream().forEach(x -> logger.debug("[Criteria] distinct 적용함 회원조회 이름 : " + x));

        logger.debug("===================================================================");

        CriteriaQuery<MemberDto> cq4 = cb.createQuery(MemberDto.class);
        Root<Member8> m4 = cq4.from(Member8.class);

        cq4.select(cb.construct(MemberDto.class, m4.get("id"), m4.get("name"),m4.get("age")));

        TypedQuery<MemberDto> query4 = em.createQuery(cq4);
        List<MemberDto> mem4 = query4.getResultList();

        mem4.stream().forEach(x -> logger.debug("생성자를 통한 조회=" + x.getId() + ", " + x.getName()));

        logger.debug("===================================================================");

        // 가장 나이가 많은 사람 과 적은 사람 조회
        CriteriaQuery<Object[]> cq5 = cb.createQuery(Object[].class);
        Root<Member8> m5 = cq5.from(Member8.class);

        Expression maxAge = cb.max(m5.<Integer>get("age"));
        Expression minAge = cb.min(m5.<Integer>get("age"));

        cq5.multiselect(m5.get("team").get("name"),maxAge, minAge);
        cq5.groupBy(m5.get("team").get("name"));
        cq5.having(cb.gt(minAge, 1)); //m.age > 4


        TypedQuery<Object[]> query5 = em.createQuery(cq5);
        List<Object[]> mem5 = query5.getResultList();

        mem5.stream().forEach(x -> logger.debug("groupBy & having 조회=" + x[0]+",최대=" + x[1] + ",최소="  + x[2]));
        logger.debug("===================================================================");

    }


    public static void testCriteria(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        logger.debug("================================testCriteria===================================");
        try {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            //basicSelectCriteria(em,cb);
            multiSelectAndConditionCriteria(em,cb);


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
            //parameter
            //ParameterAndMetaModelByCriteria(em,cb);


            logger.debug("===================================================================");


        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }

}
