package com.algaworks.curso.jpa2.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.curso.jpa2.dao.AcessorioDAO;
import com.algaworks.curso.jpa2.exceptions.NegocioException;
import com.algaworks.curso.jpa2.modelo.Acessorio;
import com.algaworks.curso.jpa2.modeloLazy.LazyAcessorioDataModel;
import com.algaworks.curso.jpa2.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaAcessorioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	AcessorioDAO acessorioDAO;

	private List<Acessorio> acessorios = new ArrayList<>();

	private LazyAcessorioDataModel lazyAcessorios;

	private Acessorio acessorioSelecionado;

	public List<Acessorio> getAcessorios() {
		return acessorios;
	}

	public void excluir() {
		try {
			acessorioDAO.excluir(acessorioSelecionado);
			this.acessorios.remove(acessorioSelecionado);
			FacesUtil.addSuccessMessage("Acessório " + acessorioSelecionado.getDescricao() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	@PostConstruct
	public void inicializar() {
		//acessorios = acessorioDAO.buscarTodos();
		lazyAcessorios = new LazyAcessorioDataModel(acessorioDAO);
	}

	public LazyAcessorioDataModel getLazyAcessorios() {
		return lazyAcessorios;
	}

	public void setLazyAcessorios(LazyAcessorioDataModel lazyAcessorios) {
		this.lazyAcessorios = lazyAcessorios;
	}

	public Acessorio getAcessorioSelecionado() {
		return acessorioSelecionado;
	}

	public void setAcessorioSelecionado(Acessorio acessorioSelecionado) {
		this.acessorioSelecionado = acessorioSelecionado;
	}
}
