package com.orm.pure.jpa.ex05;

import javax.persistence.*;

import com.orm.pure.jpa.ex05.MemberEx5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orm.pure.jpa.domain.Board;
import com.orm.pure.jpa.domain.BoardSeq;
import com.orm.pure.jpa.domain.BoardTableSeq;
import com.orm.pure.jpa.domain.MemberAccAll;

public class JpaMain5 {

    private static Logger logger = LoggerFactory.getLogger(JpaMain5.class);

    public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpatest");
		try {
            Long teamId = saveTeam(emf, "team");
            //Long memId = saveMember(emf, "테슽");

            //MemberEx5 member = findMem(emf, 1L);
            addTeamToMem(emf,teamId,1L);







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
    private static MemberEx5 findMem(EntityManagerFactory emf,  Long memId){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        MemberEx5 mem = new MemberEx5();
        try {
            tx.begin();

            mem = em.find(MemberEx5.class, memId);

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
        MemberEx5 member = new MemberEx5();
        try {
            tx.begin();

            member = new MemberEx5();
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
        TeamEx5 teamEx5 = new TeamEx5();
        try {
            tx.begin();

            teamEx5.setName(teamName);

            em.persist(teamEx5);

            tx.commit();
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
            tx.rollback();
        } finally {
            em.close();
        }
        return teamEx5.getId();
    }

    private static void addTeamToMem(EntityManagerFactory emf, Long teamId, Long memId){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            MemberEx5 mem = em.find(MemberEx5.class, memId);
            TeamEx5 team = em.find(TeamEx5.class, teamId);

            //양방향 연관관계로 인해 편의성메서드로 커스텀
            mem.setTeam(team);


            tx.commit();
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
            tx.rollback();
        } finally {
            em.close();
        }
    }


}
