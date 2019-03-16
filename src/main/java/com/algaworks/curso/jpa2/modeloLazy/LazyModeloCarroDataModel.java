package com.algaworks.curso.jpa2.modeloLazy;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.algaworks.curso.jpa2.dao.ModeloCarroDao;
import com.algaworks.curso.jpa2.modelo.ModeloCarro;

public class LazyModeloCarroDataModel extends LazyDataModel<ModeloCarro> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ModeloCarroDao modeloCarroDao;
	
	public LazyModeloCarroDataModel(ModeloCarroDao modeloCarroDao) {
		this.modeloCarroDao = modeloCarroDao;
	}

	@Override
	public List<ModeloCarro> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
	
		List<ModeloCarro> modelos = this.modeloCarroDao.buscaPaginacao(first, pageSize);
		
		this.setRowCount(this.modeloCarroDao.buscaQuantidade().intValue());
		
		return modelos;
		
	}
	
}
