package br.com.usjt.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.usjt.entity.Servico;

@Repository
public class ServicoDAO {
	@PersistenceContext
	EntityManager manager;
	public ServicoDAO() {
		// TODO Auto-generated constructor stub
	}
		
	@SuppressWarnings("unchecked")
	public List<Servico> listarServicos() {
		return manager.createQuery("select c from Servico c").getResultList();
	}

	public Servico loadServico(String idServico) {
		return manager.find(Servico.class, idServico);
	}
}
