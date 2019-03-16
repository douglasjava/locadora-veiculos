package com.algaworks.curso.jpa2.criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.hibernate.dialect.pagination.TopLimitHandler;
import org.hibernate.hql.internal.classic.HavingParser;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.algaworks.curso.jpa2.dao.CarroDAO;
import com.algaworks.curso.jpa2.modelo.Aluguel;
import com.algaworks.curso.jpa2.modelo.Carro;
import com.algaworks.curso.jpa2.modelo.Carro_;
import com.algaworks.curso.jpa2.modelo.ModeloCarro;
import com.algaworks.curso.jpa2.modelo.ModeloCarro_;
import com.algaworks.curso.jpa2.modelo.Motorista;
import com.algaworks.curso.jpa2.modelo.Pessoa;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class ExemplosCriteria {

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
	public void projecoes() {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);

		Root<Carro> carro = criteriaQuery.from(Carro.class);
		criteriaQuery.select(carro.get("placa"));

		TypedQuery<String> query = em.createQuery(criteriaQuery);
		List<String> placas = query.getResultList();

		for (String placa : placas) {
			System.out.println(placa);
		}

	}

	@Test
	public void funcoesAgrecagacao() {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteriaQuery = builder.createQuery(BigDecimal.class);

		Root<Aluguel> aluguel = criteriaQuery.from(Aluguel.class);
		criteriaQuery.select(builder.sum(aluguel.get("valorTotal")));

		TypedQuery<BigDecimal> query = em.createQuery(criteriaQuery);
		BigDecimal total = query.getSingleResult();

		System.out.println("Soma de todos os alugueis: " + total);

	}

	@Test
	public void resultadoComplexo() {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);

		Root<Carro> carro = criteriaQuery.from(Carro.class);
		criteriaQuery.multiselect(carro.get("placa"), carro.get("valorDiaria"));

		TypedQuery<Object[]> query = em.createQuery(criteriaQuery);
		List<Object[]> resultado = query.getResultList();

		for (Object[] valores : resultado) {
			System.out.println(valores[0] + " - " + valores[1]);
		}

	}

	@Test
	public void resultadoTupla() {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = builder.createTupleQuery();

		Root<Carro> carro = criteriaQuery.from(Carro.class);
		criteriaQuery.multiselect(carro.get("placa").alias("placaCarro"), carro.get("valorDiaria").alias("valorCarro"));

		TypedQuery<Tuple> query = em.createQuery(criteriaQuery);
		List<Tuple> resultado = query.getResultList();

		for (Tuple tupla : resultado) {
			System.out.println(tupla.get("placaCarro") + " - " + tupla.get("valorCarro"));

		}
	}

	@Test
	public void resultadoConstrutores() {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<PrecoCarro> criteriaQuery = builder.createQuery(PrecoCarro.class);

		Root<Carro> carro = criteriaQuery.from(Carro.class);
		criteriaQuery.select(builder.construct(PrecoCarro.class, carro.get("placa"), carro.get("valorDiaria")));

		TypedQuery<PrecoCarro> query = em.createQuery(criteriaQuery);
		List<PrecoCarro> resultado = query.getResultList();

		for (PrecoCarro precoCarro : resultado) {
			System.out.println(precoCarro.getPlaca() + " - " + precoCarro.getValor());
		}

	}

	@Test
	public void exemploFuncoes() {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Carro> criteriaQuery = builder.createQuery(Carro.class);

		Root<Carro> carro = criteriaQuery.from(Carro.class);
		Predicate predicate = builder.equal(builder.upper(carro.get("cor")), "Prata".toUpperCase());

		criteriaQuery.select(carro);
		criteriaQuery.where(predicate);

		TypedQuery<Carro> query = em.createQuery(criteriaQuery);
		List<Carro> carros = query.getResultList();

		for (Carro c : carros) {
			System.out.println(c.getPlaca() + " - " + c.getCor());
		}

	}

	@Test
	public void exemploOrdenacao() {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Carro> criteriaQuery = builder.createQuery(Carro.class);

		Root<Carro> carro = criteriaQuery.from(Carro.class);
		Order order = builder.desc(carro.get("valorDiaria"));
		
		criteriaQuery.select(carro);
		criteriaQuery.orderBy(order);
		

		TypedQuery<Carro> query = em.createQuery(criteriaQuery);
		query.setMaxResults(1);
		List<Carro> carros = query.getResultList();
		

		for (Carro c : carros) {
			System.out.println(c.getPlaca() + " - " + c.getValorDiaria());
		}

	}

	@Test
	public void exemploJoinEFecth() {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Carro> criteriaQuery = builder.createQuery(Carro.class);

		Root<Carro> carro = criteriaQuery.from(Carro.class);

		Join<Carro, ModeloCarro> modelo = (Join) carro.fetch("modelo");

		TypedQuery<Carro> query = em.createQuery(criteriaQuery);
		List<Carro> carros = query.getResultList();

		for (Carro c : carros) {
			System.out.println(c.getPlaca() + " - " + c.getModelo().getDescricao());
		}

	}

	@Test
	public void exemploJoin() {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Carro> criteriaQuery = builder.createQuery(Carro.class);

		Root<Carro> carro = criteriaQuery.from(Carro.class);
		Join<Carro, ModeloCarro> modelo = (Join) carro.join("modelo");

		criteriaQuery.select(carro);
		criteriaQuery.where(builder.equal(modelo.get("descricao"), "Fit"));

		TypedQuery<Carro> query = em.createQuery(criteriaQuery);
		List<Carro> carros = query.getResultList();

		for (Carro c : carros) {
			System.out.println(c.getPlaca() + " - " + c.getModelo().getDescricao());
		}
	}

	@Test
	public void mediaDasDiariasDosCarros() {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Double> criteriaQuery = builder.createQuery(Double.class);

		Root<Carro> carro = criteriaQuery.from(Carro.class);
		criteriaQuery.select(builder.avg(carro.get("valorDiaria")));

		TypedQuery<Double> query = em.createQuery(criteriaQuery);
		Double total = query.getSingleResult();

		System.out.println("Média da diária: " + total);

	}

	@Test
	public void carrosComValoresAcimaDaMedia() {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Carro> criteriaQuery = builder.createQuery(Carro.class);

		Subquery<Double> subquery = criteriaQuery.subquery(Double.class);

		Root<Carro> carro = criteriaQuery.from(Carro.class);
		Root<Carro> carroSub = subquery.from(Carro.class);

		subquery.select(builder.avg(carroSub.get("valorDiaria")));

		criteriaQuery.select(carro);
		criteriaQuery.where(builder.greaterThanOrEqualTo(carro.get("valorDiaria"), subquery));

		TypedQuery<Carro> query = em.createQuery(criteriaQuery);
		List<Carro> carros = query.getResultList();

		for (Carro c : carros) {
			System.out.println(c.getPlaca() + " - " + c.getValorDiaria());

		}

	}

	/***
	 * Ver aula 8.10-metamodel, com acesso a internet liberado, para baixar as
	 * novas dependências POM
	 */
	@Test
	public void exemploMetamodel() {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Carro> criteriaQuery = builder.createQuery(Carro.class);

		Root<Carro> carro = criteriaQuery.from(Carro.class);
		Join<Carro, ModeloCarro> modelo = (Join) carro.fetch(Carro_.modelo);

		criteriaQuery.select(carro);
		criteriaQuery.where(builder.equal(modelo.get(ModeloCarro_.descricao), "Fit"));

		TypedQuery<Carro> query = em.createQuery(criteriaQuery);
		List<Carro> carros = query.getResultList();

		for (Carro c : carros) {

			System.out.println(c.getPlaca() + " - " + c.getModelo().getDescricao());

		}

	}

	@Test
	public void exemploMotoristaMes() {

		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Aluguel> criteriaQuery = builder.createQuery(Aluguel.class);

			Root<Aluguel> a = criteriaQuery.from(Aluguel.class);
			Join<Aluguel, Motorista> pessoa = a.join("motorista");

			criteriaQuery.select(a);

			List<Predicate> predicates = new ArrayList<Predicate>();
			ParameterExpression<Calendar> dataInicio = builder.parameter(Calendar.class, "dataInicio");
			ParameterExpression<Calendar> dataFim = builder.parameter(Calendar.class, "dataFim");
			predicates.add(builder.between(a.get("dataPedido"), dataInicio, dataFim));

			criteriaQuery.where(predicates.toArray(new Predicate[0]));
			

			TypedQuery<Aluguel> query = em.createQuery(criteriaQuery);

			Calendar dataEntregaInicial = Calendar.getInstance();
			dataEntregaInicial.set(Calendar.DAY_OF_MONTH, 1);
			dataEntregaInicial.set(Calendar.MONTH, 4);
			dataEntregaInicial.set(Calendar.YEAR, 2017);
			dataEntregaInicial.set(Calendar.HOUR_OF_DAY, 0);
			dataEntregaInicial.set(Calendar.MINUTE, 0);
			dataEntregaInicial.set(Calendar.SECOND, 0);

			Calendar dataEntregaFinal = Calendar.getInstance();
			dataEntregaFinal.set(Calendar.DAY_OF_MONTH, 31);
			dataEntregaFinal.set(Calendar.MONTH, 4);
			dataEntregaFinal.set(Calendar.YEAR, 2017);
			dataEntregaFinal.set(Calendar.HOUR_OF_DAY, 0);
			dataEntregaFinal.set(Calendar.MINUTE, 0);
			dataEntregaFinal.set(Calendar.SECOND, 0);

			query.setParameter("dataInicio", dataEntregaInicial);
			query.setParameter("dataFim", dataEntregaFinal);

			List<Aluguel> algueis = query.getResultList();

			for (Aluguel aluguel : algueis) {
				System.out.println(aluguel.getMotorista().getNome());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
