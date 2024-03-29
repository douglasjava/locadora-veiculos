package com.algaworks.curso.jpa2.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.curso.jpa2.dao.FuncionarioDAO;
import com.algaworks.curso.jpa2.exceptions.NegocioException;
import com.algaworks.curso.jpa2.modelo.Funcionario;
import com.algaworks.curso.jpa2.modeloLazy.LazyFuncionarios;
import com.algaworks.curso.jpa2.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaFuncionarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	FuncionarioDAO funcionarioDAO;

	private List<Funcionario> funcionarios = new ArrayList<>();

	private LazyFuncionarios lazyFuncionarios;

	private Funcionario funcionarioSelecionado;

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void excluir() {
		try {
			funcionarioDAO.excluir(funcionarioSelecionado);
			this.funcionarios.remove(funcionarioSelecionado);
			FacesUtil.addSuccessMessage("Funcionário " + funcionarioSelecionado.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	@PostConstruct
	public void inicializar() {
		funcionarios = funcionarioDAO.buscarTodos();
		//lazyFuncionarios = new LazyFuncionarios(funcionarioDAO);
	}

	public Funcionario getFuncionarioSelecionado() {
		return funcionarioSelecionado;
	}

	public void setFuncionarioSelecionado(Funcionario funcionarioSelecionado) {
		this.funcionarioSelecionado = funcionarioSelecionado;
	}

	public LazyFuncionarios getLazyFuncionarios() {
		return lazyFuncionarios;
	}

	public void setLazyFuncionarios(LazyFuncionarios lazyFuncionarios) {
		this.lazyFuncionarios = lazyFuncionarios;
	}

}