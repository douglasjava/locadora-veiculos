package com.algaworks.curso.jpa2.criteria;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.algaworks.curso.jpa2.modelo.Carro;

@SuppressWarnings("unchecked")
public class ConsultaNativas {

	private static EntityManagerFactory factory;
	private EntityManager em;

	@BeforeClass
	public static void init() {
		factory = Persistence.createEntityManagerFactory("locadoraVeiculoPU");
	}

	@Before
	public void setUp() {
		em = factory.createEntityManager();
	}

	@After
	public void tearDown() {
		this.em.close();
	}

	@Test
	public void consultaNativa() {

		Query query = em.createNamedQuery("select * from Carro", Carro.class);
		List<Carro> carros = query.getResultList();

		for (Carro carro : carros) {
			System.out.println(carro.getPlaca());
		}

	}

}
