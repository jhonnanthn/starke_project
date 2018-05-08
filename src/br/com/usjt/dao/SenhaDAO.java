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

	@SuppressWarnings({ "unchecked" })
	public void gerarSenha(Senha senha) {

		// Query para buscar o ultimo nome
		Query query = manager.createQuery("select s from Senha s where id_servico = :id_servico");
		query.setParameter("id_servico", senha.getServico().getId());
		List<Senha> list = query.getResultList();

		// int horaAtual = new Date().getHours();

		// Query para gerar médias de espera na fila e duração do antedimento
		senha = getEstimativas(senha);

		String idServico = senha.getServico().getId();
		String novoNome;
		// Caso lista tenha Senhas com o mesmo cod de servico, pega o nome do ultimo,
		// subtrai as letras, verifica se é o ultimo (999) e então define o próximo nome
		// (numeNovo)
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

		// DataEntrada e Status são default
		senha.setDataEntrada(new Date());
		senha.setStatus("aguardando");
		manager.persist(senha);

	}

	public Senha loadSenha(int id) {
		return manager.find(Senha.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Senha> listarSenha() {
		Query q = manager.createQuery(
				"select s from Senha s WHERE NOT s.status = 'finalizado' order by tipo desc, estimativa_fila");
		q.setMaxResults(20);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Senha> listarSenha(String servico, int subservico) {

		return manager
				.createQuery("select s from Senha s  where id_servico = '" + servico + "' and id_subservico = "
						+ subservico + " AND  NOT s.status = 'finalizado'  order by tipo desc, data_entrada")
				.getResultList();
	}

	public void updateSenha(Senha senha) {
		manager.merge(senha);
	}

	@SuppressWarnings("unchecked")
	// Define o que sera feito ao acabar o atendimento; finalizar senha ou enviar
	// para proxima fila.
	public Senha proximaSenha(Senha senha) {
		int next = senha.getSubservico().getOrdem() + 1;
		String idServico = senha.getServico().getId();

		// utilizada mais tarde para alterar Atendimento
		int oldSub = senha.getSubservico().getId();
		// Select o próximo subservico de um Servico, se existir, baseado no campo ordem
		Query query = manager
				.createQuery("select s from Subservico s where s.servico.id = :idServico AND s.ordem = :next");
		query.setParameter("idServico", idServico);
		query.setParameter("next", next);
		Subservico subservico;
		try {
			subservico = (Subservico) query.getSingleResult();
		} catch (Exception e) {
			System.out.println("Não há proximo subservico");
			subservico = null;
		}
		// Se existe, então senha será inserida nesse proximo subservico.
		// São alterados Status, idSubservico e inseridas novas estimativas.
		if (subservico != null) {
			senha.setStatus("aguardando");
			senha.setSubservico(subservico);
			// Denovo essa joça, deveria ser feita num func mas estava com pressa n me
			// julguem
			senha = getEstimativas(senha);

			// Após passar senha para proximo servico, é gerado o Atendimento do novo estado
			// da Senha
			Atendimento at = new Atendimento();
			at.setSenha(senha);
			at.setSubservico(subservico);
			at.setDataGerado(new Date());
			manager.persist(at);

			// Se não existe proximo, só finaliza a senha atual
		} else {
			senha.setStatus("finalizado");
			senha.setDataSaida(new Date());

		}
		// atualiza senha no matter what
		manager.merge(senha);

		// finaliza o Atendimento anterior no matter what
		Query x = manager.createQuery("select a from Atendimento a where a.subservico.id = :id and a.senha.id = :id2");
		x.setParameter("id", oldSub);
		x.setParameter("id2", senha.getId());
		Atendimento atendimento = (Atendimento) x.getSingleResult();
		atendimento.setDataSaida(new Date());
		long l = (atendimento.getDataSaida().getTime() - atendimento.getDataGerado().getTime()) / 1000;
		atendimento.setDuracao((int) l / 60);

		manager.merge(atendimento);

		return senha;
	}

	@SuppressWarnings("unchecked")
	public Senha getEstimativas(Senha senha) {
		Query q1 = manager.createQuery("select a from Atendimento a where a.dataEntrada IS NOT NULL and a.senha.tipo = :tipo");
		q1.setParameter("tipo", senha.getTipo());
		List<Atendimento> atendimentos = q1.getResultList();

		Query q2 = manager.createQuery("select s from Senha s where s.status = 'finalizado' and s.tipo = :tipo");
		q2.setParameter("tipo", senha.getTipo());
		List<Senha> senhas = q2.getResultList();
		int sumFila = 0;
		long sumAtendimento = 0;
		int mediaAtendimento = 0, mediaFila = 0;
		// TODO É aqui pra consertar a porra da estimativa, jhonas.
		if (senhas.size() > 0) {
			for (Senha s : senhas) {
				sumAtendimento += (s.getDataSaida().getTime() - s.getDataEntrada().getTime()) / 1000 / 60;
			}
			mediaAtendimento = (int) (sumAtendimento / senhas.size());
		}
		if (atendimentos.size() > 0) {
			for (Atendimento a : atendimentos) {
				sumFila += a.getEspera();
			}
			mediaFila = sumFila / atendimentos.size();
		}

		// Cria Calendar para poder adcionar Minutos facilmente, e depois transforma em
		// Date
		// Data estimada de Fila = Data Atual + media da fila
		// Data estimada de Atendimento = Data Atual + media de fila + media de
		// atendimento
		Calendar cFila = Calendar.getInstance(), cAtendimento = Calendar.getInstance();
		cFila.add(Calendar.MINUTE, mediaFila);
		cAtendimento.add(Calendar.MINUTE, mediaAtendimento);
		senha.setEstimativaFila(cFila.getTime());
		senha.setEstimativaAtendimento(cAtendimento.getTime());
		
		return senha;
	}

	public Senha buscaProximaSenha(String proxChamada, String servico, String subservico) {
		Query query = manager.createQuery(
				"select s from Senha s where s.tipo = :tipo and s.servico.id = :servico and s.subservico.id = :subservico and s.status = 'aguardando' order by tipo desc, data_entrada");
		String tipo = proxChamada;
		query.setParameter("tipo", tipo);
		query.setParameter("servico", servico);
		query.setParameter("subservico", (Integer.parseInt(subservico)));
		query.setMaxResults(1);

		List senha = query.getResultList();

		if (senha.size() == 0) {
			query = manager.createQuery(
					"select s from Senha s where s.tipo = :tipo and s.servico.id = :servico and s.subservico.id = :subservico and s.status = 'aguardando' order by tipo desc, data_entrada");
			tipo = "emergencial";
			query.setParameter("tipo", tipo);
			query.setParameter("servico", servico);
			query.setParameter("subservico", (Integer.parseInt(subservico)));
			query.setMaxResults(1);
			senha = query.getResultList();
		}

		if (senha.size() == 0) {
			query = manager.createQuery(
					"select s from Senha s where s.tipo = :tipo and s.servico.id = :servico and s.subservico.id = :subservico and s.status = 'aguardando' order by tipo desc, data_entrada");
			tipo = "comum";
			query.setParameter("tipo", tipo);
			query.setParameter("servico", servico);
			query.setParameter("subservico", (Integer.parseInt(subservico)));
			query.setMaxResults(1);
			senha = query.getResultList();
		}

		return (Senha) senha.get(0);

	}
}
