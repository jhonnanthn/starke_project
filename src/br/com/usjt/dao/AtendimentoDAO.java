package br.com.usjt.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.usjt.entity.Atendimento;

@Repository
public class AtendimentoDAO {
	@PersistenceContext
	EntityManager manager;

	public void gerarAtendimento(Atendimento atendimento) {
		manager.persist(atendimento);		
	}
	]public void loadAtendimento(Atendimento atendimento) {
		manager.find(Atendiment.class, atendimento.getId());		
	}

}
