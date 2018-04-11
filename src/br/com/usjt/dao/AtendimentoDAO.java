package br.com.usjt.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.usjt.entity.Atendimento;
import br.com.usjt.entity.Senha;

@Repository
public class AtendimentoDAO {
	@PersistenceContext
	EntityManager manager;

	public void gerarAtendimento(Atendimento atendimento) {
		atendimento.setDataGerado(new Date());
		manager.persist(atendimento);
	}
	
	public Atendimento loadAtendimento(Senha senha) {
		Query query = manager.createQuery("select a from Atendimento a where a.subservico.id = :id and a.senha.id = :id2");
		query.setParameter("id", senha.getSubservico().getId());
		query.setParameter("id2", senha.getId());
		Atendimento atendimento = (Atendimento) query.getSingleResult();
		
		return atendimento;
	}
	
	public void updateAtendimento(Atendimento atendimento) {
		manager.merge(atendimento);
	}

}
