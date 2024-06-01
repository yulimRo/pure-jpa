package com.orm.pure.jpa.ex06;

import com.orm.pure.jpa.domain.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain6 {

    private static Logger logger = LoggerFactory.getLogger(JpaMain6.class);

    public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpatest");
		try {
            //Long teamId = saveTeam(emf, "team");
            //Long memId = saveMember(emf, "테슽");
            //Long memId2 = saveMember(emf, "테슽");


            //MemberEx5 member = findMem(emf, 1L);
            //saveMember(emf, "mem1");
            //addTeamToMem(emf,1L,memId);

            //Long lockId = saveLockForOneToOne(emf, "락");
            //Long memId = saveMemberForOneToOne(emf, "유림");
           // addLockToMem(emf, memId, lockId);

            //saveMemProMtoM(emf);
            //findMemAndProduct(emf);


            findMToMWithConnectTable(emf);


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
    private static MemberEx6 findMem(EntityManagerFactory emf,  Long memId){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        MemberEx6 mem = new MemberEx6();
        try {
            tx.begin();

            mem = em.find(MemberEx6.class, memId);

            tx.commit();
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
            tx.rollback();
        } finally {
            em.close();
        }
        return mem;
    }
    private static Long saveMember(EntityManagerFactory emf,  String memName){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        MemberEx6 member = new MemberEx6();
        try {
            tx.begin();

            member = new MemberEx6();
            member.setName(memName);

            em.persist(member);

            tx.commit();
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
            tx.rollback();
        } finally {
            em.close();
        }

        return member.getId();

    }

    private static Long saveTeam(EntityManagerFactory emf,  String teamName){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        TeamEx6 teamEx6 = new TeamEx6();
        try {
            tx.begin();

            teamEx6.setName(teamName);

            em.persist(teamEx6);

            tx.commit();
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
            tx.rollback();
        } finally {
            em.close();
        }
        return teamEx6.getId();
    }

    private static void addTeamToMem(EntityManagerFactory emf, Long teamId, Long memId){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            MemberEx6 mem = em.find(MemberEx6.class, memId);
            TeamEx6 team = em.find(TeamEx6.class, teamId);

            //양방향 연관관계로 인해 편의성메서드로 커스텀
            team.addMember(mem);

            logger.debug("::teams Check" + team.getMembers().size());

            tx.commit();
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
            tx.rollback();
        } finally {
            em.close();
        }
    }


    private static Long saveMemberForOneToOne(EntityManagerFactory emf, String name){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        MemberEx6_2 mem = new MemberEx6_2();
        try {
            tx.begin();

            mem.setName(name);

            em.persist(mem);

            tx.commit();
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
            tx.rollback();
        } finally {
            em.close();
        }

        return mem.getId();
    }

    private static Long saveLockForOneToOne(EntityManagerFactory emf, String name){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Lock lock = new Lock();
        try {
            tx.begin();

            lock.setName(name);

            em.persist(lock);

            tx.commit();
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
            tx.rollback();
        } finally {
            em.close();
        }

        return lock.getId();
    }

    private static void addLockToMem(EntityManagerFactory emf, Long memId, Long lockId){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        MemberEx6_2 mem = new MemberEx6_2();
        Lock lock = new Lock();
        try {
            tx.begin();

            mem = em.find(MemberEx6_2.class, memId);
            lock = em.find(Lock.class, lockId);

            mem.setLock(lock);

            tx.commit();
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
            tx.rollback();
        } finally {
            em.close();
        }
    }

    private static void saveMemProMtoM(EntityManagerFactory emf){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            ProductMtoM productMtoM = new ProductMtoM();
            productMtoM.setName("당근");

            em.persist(productMtoM);


            MemberMtom member = new MemberMtom();
            member.setName("테스터");
            member.getProducts().add(productMtoM);

            em.persist(member);




            tx.commit();
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
            tx.rollback();
        } finally {
            em.close();
        }
    }

    private static void findMemAndProduct(EntityManagerFactory emf){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            /*
                MemberMtom으로 조회 할 경우, 연결테이블 inner 조인해서 List<Product> 조회해옴

             */
//            MemberMtom mem = em.find(MemberMtom.class,1L);
//
//            List<Product> productList = mem.getProducts();
//
//            for(Product p : productList){
//                logger.debug("p" + p.getName());
//            }


            /*
                Product 도 양방향 연관관계 추가할 경우 아래 로직도 가능
             */

            ProductMtoM pr = em.find(ProductMtoM.class, 1L);
            List<MemberMtom> members = pr.getMembers();

            for(MemberMtom item : members){
                logger.debug("찾아라=" + item.getName());
            }

            tx.commit();
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
            tx.rollback();
        } finally {
            em.close();
        }
    }

    private static void findMToMWithConnectTable(EntityManagerFactory emf){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            ProductMtoM_2 product = new ProductMtoM_2();
            product.setName("토마토");

            em.persist(product);

            MemberMtom_2 member = new MemberMtom_2();
            member.setName("강아지또");

            em.persist(member);

            MemberProduct memPro = new MemberProduct();
            memPro.setOrderCnt(1);
            memPro.setMember(member);
            memPro.setProduct(product);

            em.persist(memPro);


            tx.commit();
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
            tx.rollback();
        } finally {
            em.close();
        }
    }


    private static void findMToMWithOrder(EntityManagerFactory emf){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            ProductMtoM_2 product = new ProductMtoM_2();
            product.setName("토마토");

            em.persist(product);

            MemberMtom_2 member = new MemberMtom_2();
            member.setName("강아지또");

            em.persist(member);

            Order memPro = new Order();
            memPro.setOrderCnt(1);
            memPro.setMember(member);
            memPro.setProduct(product);

            em.persist(memPro);


            tx.commit();
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
            tx.rollback();
        } finally {
            em.close();
        }
    }




}
