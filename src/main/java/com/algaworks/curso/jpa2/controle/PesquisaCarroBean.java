package com.algaworks.curso.jpa2.controle;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.algaworks.curso.jpa2.dao.CarroDAO;
import com.algaworks.curso.jpa2.exceptions.NegocioException;
import com.algaworks.curso.jpa2.modelo.Carro;
import com.algaworks.curso.jpa2.modeloLazy.LazyCarroDataModel;
import com.algaworks.curso.jpa2.util.jsf.FacesUtil;

@Named
@SessionScoped
public class PesquisaCarroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	CarroDAO carroDAO;

	private List<Carro> carros = new ArrayList<>();

	private LazyCarroDataModel lazyCarros;

	private Carro carroSelecionado;

	private Carro carroSelecionadoParaExcluir;
	
	private StreamedContent fotoCarro;

	public List<Carro> getCarros() {
		return carros;
	}

	public void excluir() {
		try {
			carroDAO.excluir(carroSelecionadoParaExcluir);
			this.carros.remove(carroSelecionadoParaExcluir);
			FacesUtil.addSuccessMessage("Carro: " + carroSelecionadoParaExcluir.getModelo().getDescricao() + " - "
					+ carroSelecionadoParaExcluir.getPlaca() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	@PostConstruct
	public void inicializar() {
		// carros = carroDAO.buscarTodos();
		lazyCarros = new LazyCarroDataModel(carroDAO);
	}

	public void buscarCarroComAcessorios() {
		carroSelecionado = carroDAO.buscarCarroComAcessorios(carroSelecionado.getCodigo());
	}
	
	public void buscarFoto(){
		try {
			this.buscarCarroComAcessorios();
			if(carroSelecionado.getFoto() != null){
				fotoCarro = new DefaultStreamedContent(new ByteArrayInputStream(carroSelecionado.getFoto()));
			}else {
				fotoCarro = null;
			}
		} catch (Exception e) {
			FacesUtil.addErrorMessage("Erro ao recuperar foto de carro " + e.getMessage());
			e.printStackTrace();
		}
	}

	public Carro getCarroSelecionado() {
		return carroSelecionado;
	}

	public void setCarroSelecionado(Carro carroSelecionado) {
		this.carroSelecionado = carroSelecionado;
	}

	public LazyCarroDataModel getLazyCarros() {
		return lazyCarros;
	}

	public void setLazyCarros(LazyCarroDataModel lazyCarros) {
		this.lazyCarros = lazyCarros;
	}

	public Carro getCarroSelecionadoParaExcluir() {
		return carroSelecionadoParaExcluir;
	}

	public void setCarroSelecionadoParaExcluir(Carro carroSelecionadoParaExcluir) {
		this.carroSelecionadoParaExcluir = carroSelecionadoParaExcluir;
	}

	public StreamedContent getFotoCarro() {
		return fotoCarro;
	}

	public void setFotoCarro(StreamedContent fotoCarro) {
		this.fotoCarro = fotoCarro;
	}

}
