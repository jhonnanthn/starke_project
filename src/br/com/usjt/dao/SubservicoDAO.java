package br.com.usjt.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.usjt.entity.Subservico;

@Repository
public class SubservicoDAO {
	@PersistenceContext
	EntityManager manager;

	public Subservico loadSubservico(String idServico, int ordem) {

		Query query = manager.createQuery("select s from Subservico s where id_servico = :id_servico and s.ordem = :ordem");
		query.setParameter("id_servico", idServico);
		query.setParameter("ordem", ordem);
		Subservico sServico = (Subservico) query.getSingleResult();
		return sServico;
	}

}
