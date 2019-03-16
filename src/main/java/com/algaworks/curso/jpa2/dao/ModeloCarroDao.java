package com.algaworks.curso.jpa2.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.algaworks.curso.jpa2.exceptions.NegocioException;
import com.algaworks.curso.jpa2.modelo.ModeloCarro;
import com.algaworks.curso.jpa2.util.jpa.Transactional;

public class ModeloCarroDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Transactional
	public ModeloCarro buscaPeloCodigo(Long codigo) {
		return manager.find(ModeloCarro.class, codigo);
	}

	public void salvar(ModeloCarro modeloCarro) {
		manager.merge(modeloCarro);
	}

	@SuppressWarnings("unchecked")
	public List<ModeloCarro> listModeloCarro() {
		return manager.createQuery("from ModeloCarro").getResultList();
	}

	@Transactional
	public void excluir(ModeloCarro modeloCarro) throws NegocioException {
		try {
			modeloCarro = buscaPeloCodigo(modeloCarro.getCodigo());
			manager.remove(modeloCarro);
			manager.flush();
		} catch (Exception e) {
			throw new NegocioException("Este modelo não pode ser excluído");
		}
	}

	@SuppressWarnings("unchecked")
	public List<ModeloCarro> buscaPaginacao(int first, int pageSize) {

		return manager.createQuery("from ModeloCarro")
					  .setFirstResult(first)
					  .setMaxResults(pageSize)
					  .getResultList();
	}

	public Long buscaQuantidade() {

		return manager.createQuery("select count(m) from ModeloCarro m", Long.class).getSingleResult();
	}
}
