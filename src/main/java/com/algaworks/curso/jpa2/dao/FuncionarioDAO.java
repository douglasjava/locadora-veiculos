package com.algaworks.curso.jpa2.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.algaworks.curso.jpa2.exceptions.NegocioException;
import com.algaworks.curso.jpa2.modelo.Funcionario;

public class FuncionarioDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Funcionario buscarPeloCodigo(Long codigo) {
		return manager.find(Funcionario.class, codigo);
	}

	public void salvar(Funcionario funcionario) {
		manager.merge(funcionario);
	}

	@SuppressWarnings("unchecked")
	public List<Funcionario> buscarTodos() {
		return manager.createQuery("from Funcionario").getResultList();
	}

	public void excluir(Funcionario funcionario) throws NegocioException {
		funcionario = buscarPeloCodigo(funcionario.getCodigo());
		try {
			manager.remove(funcionario);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Funcionário não pode ser excluído.");
		}
	}

	@SuppressWarnings("unchecked")
	public List<Funcionario> buscarPaginacao(int first, int pageSize) {
		
		List<Funcionario> funcionarios = null;
		try {
			funcionarios = manager.createNativeQuery("select p.codigo, p.nome, p.data_nascimento, p.cpf, f.matricula, p.sexo from pessoa p inner join funcionario f on p.codigo = f.codigo where p.TIPO_PESSOA = 2")
													.setFirstResult(first)
													.setMaxResults(pageSize)
													.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return funcionarios;
	}

	public Long buscaQuantidades() {

		return manager.createQuery("select count(f) from Funcionario f", Long.class).getSingleResult();

	}
}
