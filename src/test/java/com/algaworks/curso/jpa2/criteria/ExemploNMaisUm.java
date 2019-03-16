package com.algaworks.curso.jpa2.criteria;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.algaworks.curso.jpa2.modelo.Carro;

public class ExemploNMaisUm {

	private static EntityManagerFactory factory;

	private EntityManager em;

	@BeforeClass
	public static void init() {
		factory = Persistence.createEntityManagerFactory("locadoraVeiculoPU");
	}

	@Before
	public void setUp() {
		this.em = factory.createEntityManager();
	}

	@After
	public void tearDown() {
		this.em.close();
	}

	@Test
	public void problema() {

		TypedQuery<Carro> query = this.em.createQuery("from Carro c inner join fetch c.modelo", Carro.class);
		List<Carro> carros = query.getResultList();

		for (Carro carro : carros) {
			System.out.println(carro.getPlaca() + " - " + carro.getModelo().getDescricao());
		}

	}

	@Test
	public void cache() {

		TypedQuery<Carro> query = this.em.createQuery("from Carro c", Carro.class);
		List<Carro> carros = query.getResultList();

		for (Carro carro : carros) {
			System.out.println(carro.getCodigo() + " - " + carro.getPlaca());
		}

		System.out.println("-----------------------------------------------");
		Carro carro = this.em.find(Carro.class, 10L);
		System.out.println(carro.getCodigo() + " - " + carro.getPlaca());
	}

}
