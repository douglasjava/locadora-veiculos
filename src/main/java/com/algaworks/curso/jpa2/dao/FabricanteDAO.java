package com.algaworks.curso.jpa2.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.algaworks.curso.jpa2.exceptions.NegocioException;
import com.algaworks.curso.jpa2.modelo.Fabricante;
import com.algaworks.curso.jpa2.util.jpa.Transactional;

public class FabricanteDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public void salvar(Fabricante fabricante) {
		em.merge(fabricante);
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("unchecked")
	public List<Fabricante> buscarTodos() {
		return em.createQuery("from Fabricante").getResultList();
	}

	@Transactional
	public void excluir(Fabricante fabricante) throws NegocioException {
		fabricante = em.find(Fabricante.class, fabricante.getCodigo());
		em.remove(fabricante);
	}

	@Transactional
	public Fabricante buscarPeloCodigo(Long long1) {
		return em.find(Fabricante.class, long1);
	}

	@SuppressWarnings("unchecked")
	public List<Fabricante> buscarPaginacao(int first, int pageSize) {
		
		return em.createQuery("from Fabricante")
				 .setFirstResult(first)
				 .setMaxResults(pageSize)
				 .getResultList();
	}

	public Long buscarQuantidadesDeFabricantes() {
		return em.createQuery("select count(f) from Fabricante f", Long.class).getSingleResult();
	}

}
