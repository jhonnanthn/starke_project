package br.com.usjt.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.usjt.entity.Atendimento;
import br.com.usjt.entity.Senha;

@Transactional
@Repository
public class AtendimentoDAO {
	@PersistenceContext
	EntityManager manager;
	
	public void gerarAtendimento(Atendimento atendimento) {
		atendimento.setDataGerado(new Date());
		manager.persist(atendimento);
	}

	public Atendimento loadAtendimento(Senha senha) {
		// Busca baseada no idSenha e idSubservico. Deve apenas existir uma combinação
		// deles no banco
		Query query = manager
				.createQuery("select a from Atendimento a where a.subservico.id = :id and a.senha.id = :id2");
		query.setParameter("id", senha.getSubservico().getId());
		query.setParameter("id2", senha.getId());
		Atendimento atendimento = (Atendimento) query.getSingleResult();
		return atendimento;
	}
	
	@SuppressWarnings("unchecked")
	public List<Atendimento> loadAtendimentos(int senha) {
		Query query = manager.createQuery("select a from Atendimento a inner join a.senha as s where a.senha.id = :id");
		query.setParameter("id", senha);
		List<Atendimento> atendimento = (List<Atendimento>) query.getResultList();
		return atendimento;
	}

	public void updateAtendimento(Atendimento atendimento) {
		manager.merge(atendimento);
	}

}
