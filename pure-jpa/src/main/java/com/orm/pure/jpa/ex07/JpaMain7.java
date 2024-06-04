package com.orm.pure.jpa.ex07;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orm.pure.jpa.ex06.JpaMain6;
import com.orm.pure.jpa.ex07.domain.Album;

public class JpaMain7 {

	private static final Logger logger = LoggerFactory.getLogger(JpaMain6.class);

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpatest");

		try {
		
			saveAlbum(emf);
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


}
