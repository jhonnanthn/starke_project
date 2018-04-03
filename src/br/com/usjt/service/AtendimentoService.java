package br.com.usjt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.usjt.dao.AtendimentoDAO;
import br.com.usjt.entity.Atendimento;
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
	
	public void loadAtendimento(Atendimento atendimento) {
		atendimentoDAO.loadAtendimento(atendimento);
	}
}
