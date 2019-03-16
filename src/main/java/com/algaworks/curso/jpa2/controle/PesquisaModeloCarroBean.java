package com.algaworks.curso.jpa2.controle;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.curso.jpa2.dao.ModeloCarroDao;
import com.algaworks.curso.jpa2.exceptions.NegocioException;
import com.algaworks.curso.jpa2.modelo.ModeloCarro;
import com.algaworks.curso.jpa2.modeloLazy.LazyModeloCarroDataModel;
import com.algaworks.curso.jpa2.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaModeloCarroBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<ModeloCarro> modelosCarro;
	
	private LazyModeloCarroDataModel lazyModeloCarro;
	
	private ModeloCarro modeloCarroSelecionado;
	
	@Inject
	ModeloCarroDao modeloCarroDAO;

	public List<ModeloCarro> getModelosCarro() {
		return modelosCarro;
	}
	
	@PostConstruct
	public void inicializar() {
		//this.modelosCarro = this.modeloCarroDAO.listModeloCarro();
		this.lazyModeloCarro = new LazyModeloCarroDataModel(modeloCarroDAO);
	}
	
	public void excluir() {
		try {
			modeloCarroDAO.excluir(modeloCarroSelecionado);
			this.modelosCarro.remove(modeloCarroSelecionado);
			FacesUtil.addSuccessMessage("Modelo " + modeloCarroSelecionado.getDescricao() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public ModeloCarro getModeloCarroSelecionado() {
		return modeloCarroSelecionado;
	}
	public void setModeloCarroSelecionado(ModeloCarro modeloCarroSelecionado) {
		this.modeloCarroSelecionado = modeloCarroSelecionado;
	}

	public LazyModeloCarroDataModel getLazyModeloCarro() {
		return lazyModeloCarro;
	}

	public void setLazyModeloCarro(LazyModeloCarroDataModel lazyModeloCarro) {
		this.lazyModeloCarro = lazyModeloCarro;
	}
	
}
