package com.algaworks.curso.jpa2.modeloLazy;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.algaworks.curso.jpa2.dao.FuncionarioDAO;
import com.algaworks.curso.jpa2.modelo.Funcionario;

public class LazyFuncionarios extends LazyDataModel<Funcionario> implements Serializable {

	private static final long serialVersionUID = 1L;

	private FuncionarioDAO funcionarioDao;

	public LazyFuncionarios(FuncionarioDAO funcionarioDao) {
		this.funcionarioDao = funcionarioDao;
	}

	@Override
	public List<Funcionario> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		List<Funcionario> funcionarios = this.funcionarioDao.buscarPaginacao(first, pageSize);

		this.setRowCount(this.funcionarioDao.buscaQuantidades().intValue());

		return funcionarios;
	}
}
