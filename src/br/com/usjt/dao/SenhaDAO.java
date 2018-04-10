package br.com.usjt.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.usjt.entity.Atendimento;
import br.com.usjt.entity.Senha;

@Repository
public class SenhaDAO {
	@PersistenceContext
	EntityManager manager;
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void gerarSenha(Senha senha) {
		Query query = manager.createQuery("select s from Senha s where id_servico = :id_servico");
		query.setParameter("id_servico", senha.getServico().getId());
		List<Senha> list = query.getResultList();
		
		int horaAtual = new Date().getHours();
		Query query2 = manager.createQuery("select a from Atendimento a where HOUR(a.dataGerado) > "+(horaAtual-2)+" AND HOUR(a.dataGerado) < "+(horaAtual+2));
		List<Atendimento> atendimentos = query2.getResultList();
		int sumFila = 0, sumAtendimento = 0;
		for (Atendimento a : atendimentos) {
			if (a.getDataEntrada() != null)
				sumFila +=a.getEspera();
			if (a.getDataSaida() != null)
				sumAtendimento +=a.getDuracao();
		}
		Calendar cFila  = Calendar.getInstance(), cAtendimento = Calendar.getInstance();
		cFila.add(Calendar.HOUR_OF_DAY, sumFila);
		cAtendimento.add(Calendar.HOUR_OF_DAY, sumAtendimento);
		String idServico = senha.getServico().getId();
		
		senha.setEstimativaFila(cFila.getTime());
		senha.setEstimativaAtendimento(cAtendimento.getTime());
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
	
	public Senha loadSenha(int id) {
		return manager.find(Senha.class, id);
	}
	public void updateSenha(Senha senha) {
		manager.merge(senha);
	}
}
