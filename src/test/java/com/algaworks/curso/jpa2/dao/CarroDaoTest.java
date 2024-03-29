package com.algaworks.curso.jpa2.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.algaworks.curso.jpa2.modelo.Carro;
import com.jintegrity.core.JIntegrity;
import com.jintegrity.helper.JPAHelper;

public class CarroDaoTest {

	private JIntegrity helper = new JIntegrity();

	private CarroDAO carroDAO;

	@Before
	public void init() {
		helper.insert();

		this.carroDAO = new CarroDAO();
		this.carroDAO.setEntityManager(JPAHelper.currentEntityManager());
	}

	@Test
	public void buscarCarroPeloCodigo() {
		Carro carro = this.carroDAO.buscarPeloCodigo(23L);

		assertEquals(23L, carro.getCodigo().longValue());
		assertEquals("AAA-1111", carro.getPlaca());
	}

}
