package com.algaworks.curso.jpa2.criteria;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.algaworks.curso.jpa2.modelo.Carro;

public class ExemploExibirImagem {

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
	public void buscarFoto() throws Exception {
		Carro carro = em.find(Carro.class, 20L);
		
		if(carro.getFoto() != null){
			//BufferedImage image = ImageIO.read(new ByteArrayInputStream(carro.getFoto()));
			//JOptionPane.showMessageDialog(null, new JLabel(new ImageIcon(image)));			
			JOptionPane.showMessageDialog(null, new JLabel(new ImageIcon(carro.getFoto())));
		}else {
			System.out.println("Carro não tem foto");
		}
	
	}

}
