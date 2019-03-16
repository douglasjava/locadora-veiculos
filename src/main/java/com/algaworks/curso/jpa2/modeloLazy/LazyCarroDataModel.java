package com.algaworks.curso.jpa2.modeloLazy;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.algaworks.curso.jpa2.dao.CarroDAO;
import com.algaworks.curso.jpa2.modelo.Carro;

public class LazyCarroDataModel extends LazyDataModel<Carro> implements Serializable {

	private static final long serialVersionUID = 1L;

	private CarroDAO carroDao;

	public LazyCarroDataModel(CarroDAO carroDAO) {
		this.carroDao = carroDAO;
	}

	@Override
	public List<Carro> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		List<Carro> carros = this.carroDao.buscarComPaginacao(first, pageSize);

		this.setRowCount(this.carroDao.encontrarQuantidadeDeCarros().intValue());

		return carros;
	}

}
