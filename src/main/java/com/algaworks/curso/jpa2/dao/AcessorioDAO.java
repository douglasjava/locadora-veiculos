package com.algaworks.curso.jpa2.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.algaworks.curso.jpa2.exceptions.NegocioException;
import com.algaworks.curso.jpa2.modelo.Acessorio;
import com.algaworks.curso.jpa2.util.jpa.Transactional;

public class AcessorioDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Acessorio buscarPeloCodigo(Long codigo) {
		return manager.find(Acessorio.class, codigo);
	}
	
	public void salvar(Acessorio fabricante) {
		manager.merge(fabricante);
	}

	@SuppressWarnings("unchecked")
	public List<Acessorio> buscarTodos() {
		return manager.createQuery("from Acessorio").getResultList();
	}
	
	@Transactional
	public void excluir(Acessorio acessorio) throws NegocioException {
		acessorio = buscarPeloCodigo(acessorio.getCodigo());
		try {
			manager.remove(acessorio);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Acessorio está sendo usado por algum carro");
		}
	}

	@SuppressWarnings("unchecked")
	public List<Acessorio> buscaComPaginacao(int first, int pageSize) {
		
		return manager.createQuery("from Acessorio")
					  .setFirstResult(first)
					  .setMaxResults(pageSize)
					  .getResultList();
	}

	public Long encontrarQuantidadesDeCarro() {

		return manager.createQuery("select count(a) from Acessorio a", Long.class).getSingleResult();
	}
}
