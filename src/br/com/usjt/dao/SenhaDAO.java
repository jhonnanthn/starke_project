package br.com.usjt.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.usjt.entity.Senha;

@Repository
public class SenhaDAO {
	@PersistenceContext
	EntityManager manager;
	@SuppressWarnings("unchecked")
	public void gerarSenha(Senha senha) {
		Query query = manager.createQuery("select s from Senha s where id_servico = :id_servico");
		query.setParameter("id_servico", senha.getServico().getId());
		List<Senha> list = query.getResultList();
		
		String idServico = senha.getServico().getId();
		String novoNome;
		if (!list.isEmpty()) {
			String lastNome = list.get(list.size()-1).getNome();
			int n = Integer.parseInt(lastNome.substring(2))+1;
			if (n>999) {
				novoNome = idServico+"001";
			}else {
				String nFormatado = String.format("%03d", n);
				novoNome = idServico+nFormatado;
			}
			
		}else{
			novoNome = idServico+"001";
		}
			
		senha.setNome(novoNome);
		Date date = new Date();
		senha.setDataEntrada(date);
		senha.setStatus("ativo");
		manager.persist(senha);
		
	}
}
