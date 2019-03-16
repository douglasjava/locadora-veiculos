package com.algaworks.curso.jpa2.modeloLazy;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.algaworks.curso.jpa2.dao.FabricanteDAO;
import com.algaworks.curso.jpa2.modelo.Fabricante;

public class LazyFabricanteDataModel extends LazyDataModel<Fabricante> implements Serializable {

	private static final long serialVersionUID = 1L;

	private FabricanteDAO fabricanteDao;
	
	public LazyFabricanteDataModel(FabricanteDAO fabricanteDAO) {
		this.fabricanteDao = fabricanteDAO;
	}
	
	@Override
	public List<Fabricante> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		
		List<Fabricante> fabricantes = this.fabricanteDao.buscarPaginacao(first, pageSize);
		
		this.setRowCount(this.fabricanteDao.buscarQuantidadesDeFabricantes().intValue());
		
		return fabricantes;
	}
}
