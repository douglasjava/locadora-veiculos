package com.algaworks.curso.jpa2.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.curso.jpa2.dao.FabricanteDAO;
import com.algaworks.curso.jpa2.exceptions.NegocioException;
import com.algaworks.curso.jpa2.modelo.Fabricante;
import com.algaworks.curso.jpa2.modeloLazy.LazyFabricanteDataModel;
import com.algaworks.curso.jpa2.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaFabricanteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	FabricanteDAO fabricanteDAO;

	private List<Fabricante> fabricantes = new ArrayList<>();

	private LazyFabricanteDataModel lazyFabricantes;

	private Fabricante fabricanteSelecionado;

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void excluir() {
		try {
			fabricanteDAO.excluir(fabricanteSelecionado);
			this.fabricantes.remove(fabricanteSelecionado);
			FacesUtil.addSuccessMessage("Fabricante " + fabricanteSelecionado.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	@PostConstruct
	public void inicializar() {
		//fabricantes = fabricanteDAO.buscarTodos();
		lazyFabricantes = new LazyFabricanteDataModel(fabricanteDAO);
	}

	public Fabricante getFabricanteSelecionado() {
		return fabricanteSelecionado;
	}

	public void setFabricanteSelecionado(Fabricante fabricanteSelecionado) {
		this.fabricanteSelecionado = fabricanteSelecionado;
	}

	public LazyFabricanteDataModel getLazyFabricantes() {
		return lazyFabricantes;
	}

	public void setLazyFabricantes(LazyFabricanteDataModel lazyFabricantes) {
		this.lazyFabricantes = lazyFabricantes;
	}
}
