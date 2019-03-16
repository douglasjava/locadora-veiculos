package com.algaworks.curso.jpa2.controle;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.curso.jpa2.dao.CarroDAO;
import com.algaworks.curso.jpa2.dao.MotoristaDAO;
import com.algaworks.curso.jpa2.exceptions.NegocioException;
import com.algaworks.curso.jpa2.modelo.Aluguel;
import com.algaworks.curso.jpa2.modelo.ApoliceSeguro;
import com.algaworks.curso.jpa2.modelo.Carro;
import com.algaworks.curso.jpa2.modelo.Motorista;
import com.algaworks.curso.jpa2.service.CadastroAluguelService;
import com.algaworks.curso.jpa2.util.jsf.FacesUtil;

@Named
@ViewScoped
public class NovoAluguelBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Aluguel aluguel;

	private List<Carro> carros;
	private List<Motorista> motoristas;

	@Inject
	private CadastroAluguelService cadastroAluguelService;

	@Inject
	private CarroDAO carroDAO;

	@Inject
	private MotoristaDAO motoristaDao;

	public void salvar() {
		try {
			this.cadastroAluguelService.salvar(aluguel);
			FacesUtil.addSuccessMessage("Aluguel salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

		this.limpar();
	}

	@PostConstruct
	public void inicializar() {
		this.limpar();
		this.motoristas = this.motoristaDao.buscarTodos();
		this.carros = this.carroDAO.buscarTodos();
	}

	public void limpar() {
		this.aluguel = new Aluguel();
		this.aluguel.setApoliceSeguro(new ApoliceSeguro());
	}

	public Aluguel getAluguel() {
		return aluguel;
	}

	public void setAluguel(Aluguel aluguel) {
		this.aluguel = aluguel;
	}

	public List<Carro> getCarros() {
		return carros;
	}

	public List<Motorista> getMotoristas() {
		return motoristas;
	}

	public void setMotoristas(List<Motorista> motoristas) {
		this.motoristas = motoristas;
	}

	public CadastroAluguelService getCadastroAluguelService() {
		return cadastroAluguelService;
	}

	public void setCadastroAluguelService(CadastroAluguelService cadastroAluguelService) {
		this.cadastroAluguelService = cadastroAluguelService;
	}

	public CarroDAO getCarroDAO() {
		return carroDAO;
	}

	public void setCarroDAO(CarroDAO carroDAO) {
		this.carroDAO = carroDAO;
	}

	public MotoristaDAO getMotoristaDao() {
		return motoristaDao;
	}

	public void setMotoristaDao(MotoristaDAO motoristaDao) {
		this.motoristaDao = motoristaDao;
	}

	public void setCarros(List<Carro> carros) {
		this.carros = carros;
	}

}
