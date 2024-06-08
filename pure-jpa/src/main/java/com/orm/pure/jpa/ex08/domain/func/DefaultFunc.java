package com.orm.pure.jpa.ex08.domain.func;

import com.orm.pure.jpa.ex07.domain.Album;
import com.orm.pure.jpa.ex08.domain.Member8;
import com.orm.pure.jpa.ex08.domain.Order8;
import com.orm.pure.jpa.ex08.domain.Product8;
import com.orm.pure.jpa.ex08.domain.Team8;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class DefaultFunc {
    private static final Logger logger = LoggerFactory.getLogger(DefaultFunc.class);


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
            for(int i =1; i <5; i++) {
                Member8 member8 = new Member8();
                member8.setName("홍길동"+i);
                member8.setAge(i+3+i);
                em.persist(member8);
            }

            //팀저장
            for(int i =1; i <3; i++) {
                Team8 team8 = new Team8();
                team8.setName("팀"+i);
                em.persist(team8);
            }

            //상품저장
            for(int i =1; i <10; i++) {
                Product8 product8 = new Product8();
                product8.setName("상품"+i);
                em.persist(product8);
            }

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
}
