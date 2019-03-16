package com.algaworks.curso.jpa2.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.curso.jpa2.dao.AluguelDAO;
import com.algaworks.curso.jpa2.dao.ModeloCarroDao;
import com.algaworks.curso.jpa2.modelo.Aluguel;
import com.algaworks.curso.jpa2.modelo.Carro;
import com.algaworks.curso.jpa2.modelo.ModeloCarro;

@Named
@ViewScoped
public class PesquisaAluguelBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ModeloCarro> modelosCarros;
	private Aluguel aluguel;
	private Carro carro;

	private List<Aluguel> alugueis;

	@Inject
	private ModeloCarroDao modeloCarroDao;

	@Inject
	private AluguelDAO aluguelDAO;

	@PostConstruct
	public void inicializar() {
		this.aluguel = new Aluguel();
		this.carro = new Carro();
		this.modelosCarros = this.modeloCarroDao.listModeloCarro();

		this.alugueis = new ArrayList<Aluguel>();
	}

	public void pesquisar() {
		this.alugueis = aluguelDAO.buscarPorDataDeEntregaEModeloCarro(this.aluguel.getDataEntrega(), this.carro.getModelo());
	}

	public List<ModeloCarro> getModelosCarros() {
		return modelosCarros;
	}

	public void setModelosCarros(List<ModeloCarro> modelosCarros) {
		this.modelosCarros = modelosCarros;
	}

	public Aluguel getAluguel() {
		return aluguel;
	}

	public void setAluguel(Aluguel aluguel) {
		this.aluguel = aluguel;
	}

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	public List<Aluguel> getAlugueis() {
		return alugueis;
	}

	public void setAlugueis(List<Aluguel> alugueis) {
		this.alugueis = alugueis;
	}

	public ModeloCarroDao getModeloCarroDao() {
		return modeloCarroDao;
	}

	public void setModeloCarroDao(ModeloCarroDao modeloCarroDao) {
		this.modeloCarroDao = modeloCarroDao;
	}

	public AluguelDAO getAluguelDAO() {
		return aluguelDAO;
	}

	public void setAluguelDAO(AluguelDAO aluguelDAO) {
		this.aluguelDAO = aluguelDAO;
	}

}
