package br.com.usjt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.usjt.dao.AtendimentoDAO;
import br.com.usjt.entity.Atendimento;
import br.com.usjt.entity.Senha;
@Service
public class AtendimentoService {
	private AtendimentoDAO atendimentoDAO;
	@Autowired
	public AtendimentoService(AtendimentoDAO atendimentoDAO) {
		this.atendimentoDAO = atendimentoDAO;
	}
	
	public void gerarAtendimento(Atendimento atendimento) {
		atendimentoDAO.gerarAtendimento(atendimento);
	}
	
	public Atendimento loadAtendimento(Senha senha) {
		return atendimentoDAO.loadAtendimento(senha);
	}

	public List<Atendimento> loadAtendimentos(int senha) {
		return atendimentoDAO.loadAtendimentos(senha);
	}
	public void updateAtendimento(Atendimento atendimento) {
		atendimentoDAO.updateAtendimento(atendimento);
	}
}
