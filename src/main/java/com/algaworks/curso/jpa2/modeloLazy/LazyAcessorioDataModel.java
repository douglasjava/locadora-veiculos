package com.algaworks.curso.jpa2.modeloLazy;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.algaworks.curso.jpa2.dao.AcessorioDAO;
import com.algaworks.curso.jpa2.modelo.Acessorio;

public class LazyAcessorioDataModel extends LazyDataModel<Acessorio> implements Serializable {

	private static final long serialVersionUID = 1L;

	private AcessorioDAO acessorioDao;

	public LazyAcessorioDataModel(AcessorioDAO acessorioDAO) {
		this.acessorioDao = acessorioDAO;
	}

	@Override
	public List<Acessorio> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
	
		List<Acessorio> acessorios = this.acessorioDao.buscaComPaginacao(first, pageSize);

		this.setRowCount(acessorioDao.encontrarQuantidadesDeCarro().intValue());

		return acessorios;
	}

}
