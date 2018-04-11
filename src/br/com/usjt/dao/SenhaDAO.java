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
import br.com.usjt.entity.Subservico;

@Repository
public class SenhaDAO {
	@PersistenceContext
	EntityManager manager;

	@SuppressWarnings({ "unchecked", "deprecation" })
	public void gerarSenha(Senha senha) {
		Query query = manager.createQuery("select s from Senha s where id_servico = :id_servico");
		query.setParameter("id_servico", senha.getServico().getId());
		List<Senha> list = query.getResultList();

		// int horaAtual = new Date().getHours();
		Query query2 = manager.createQuery("select a from Atendimento a where a.dataEntrada IS NOT NULL");
		List<Atendimento> atendimentos = query2.getResultList();
		int sumFila = 0, sumAtendimento = 0;
		int contA = 0, contB = 0;
		for (Atendimento a : atendimentos) {

			sumFila += a.getEspera();
			contA++;

			if (a.getDataSaida() != null) {
				sumAtendimento += a.getDuracao();
				contB++;
			}
		}

		int mediaFila = sumFila / contA;
		int mediaAtendimento = sumAtendimento / contB;

		Calendar cFila = Calendar.getInstance(), cAtendimento = Calendar.getInstance();
		cFila.add(Calendar.MINUTE, mediaFila);
		cAtendimento.add(Calendar.MINUTE, mediaFila + mediaAtendimento);
		String idServico = senha.getServico().getId();

		senha.setEstimativaFila(cFila.getTime());
		senha.setEstimativaAtendimento(cAtendimento.getTime());
		String novoNome;
		if (!list.isEmpty()) {
			String lastNome = list.get(list.size() - 1).getNome();
			int n = Integer.parseInt(lastNome.substring(2)) + 1;
			if (n > 999) {
				novoNome = idServico + "001";
			} else {
				String nFormatado = String.format("%03d", n);
				novoNome = idServico + nFormatado;
			}

		} else {
			novoNome = idServico + "001";
		}

		senha.setNome(novoNome);
		Date date = new Date();
		senha.setDataEntrada(date);
		senha.setStatus("aguardando");
		manager.persist(senha);

		

	}

	public Senha loadSenha(int id) {
		return manager.find(Senha.class, id);
	}

	public void updateSenha(Senha senha) {
		manager.merge(senha);
	}

	public Senha proximaSenha(Senha senha) {
		int next = senha.getSubservico().getOrdem() + 1;
		String idServico = senha.getServico().getId();
		int oldSub = senha.getSubservico().getId();

		Query query = manager.createQuery(
				"select s from Subservico s where s.servico.id = :idServico AND s.ordem = :next");
		query.setParameter("idServico", idServico);
		query.setParameter("next", next);
		Subservico subservico = (Subservico) query.getSingleResult();
		
		if (subservico != null) {
			senha.setStatus("aguardando");
			senha.setSubservico(subservico);
			
			Query query2 = manager.createQuery("select a from Atendimento a where a.dataEntrada IS NOT NULL");
			List<Atendimento> atendimentos = query2.getResultList();
			int sumFila = 0, sumAtendimento = 0;
			int contA = 0, contB = 0;
			for (Atendimento a : atendimentos) {
				sumFila += a.getEspera();
				contA++;
				if (a.getDataSaida() != null) {
					sumAtendimento += a.getDuracao();
					contB++;
				}
			}
			int mediaFila = sumFila / contA;
			int mediaAtendimento = sumAtendimento / contB;

			Calendar cFila = Calendar.getInstance(), cAtendimento = Calendar.getInstance();
			cFila.add(Calendar.MINUTE, mediaFila);
			cAtendimento.add(Calendar.MINUTE, mediaFila + mediaAtendimento);
			senha.setEstimativaFila(cFila.getTime());
			senha.setEstimativaAtendimento(cAtendimento.getTime());
			
			
			Atendimento at = new Atendimento();
			at.setSenha(senha);
			at.setSubservico(subservico);
			at.setDataGerado(new Date());
			manager.persist(at);
			
			
		}else {
			senha.setStatus("finalizado");
			senha.setDataSaida(new Date());
			
		}
		manager.merge(senha);
		
		Query x = manager.createQuery("select a from Atendimento a where a.subservico.id = :id and a.senha.id = :id2");
		x.setParameter("id", oldSub);
		x.setParameter("id2", senha.getId());
		Atendimento atendimento = (Atendimento) x.getSingleResult();
		atendimento.setDataSaida(new Date());
		long l = (atendimento.getDataSaida().getTime()-atendimento.getDataGerado().getTime())/1000;
		atendimento.setDuracao((int) l/60);
		
		manager.merge(atendimento);

		return senha;
	}
}
