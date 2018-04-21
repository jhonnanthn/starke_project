package br.com.usjt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.usjt.dao.SenhaDAO;
import br.com.usjt.entity.Senha;

@Service
public class SenhaService {
	private SenhaDAO senhaDAO;
	
	@Autowired
	public SenhaService(SenhaDAO senhaDAO) {
		this.senhaDAO = senhaDAO;
	}
	
	
	public void gerarSenha(Senha senha) {
		senhaDAO.gerarSenha(senha);
	}
	
	public Senha loadSenha(int id) {
		return senhaDAO.loadSenha(id);
	}
	
	public void updateSenha(Senha senha) {
		senhaDAO.updateSenha(senha);
	}


	public Senha proximaSenha(Senha senha) {
		return senhaDAO.proximaSenha(senha);
	}

	public List<Senha> listarSenha() {
		return senhaDAO.listarSenha();
	}

	public List<Senha> listarSenhasAtendimento(String servico, int subservico) {
		return senhaDAO.listarSenha(servico, subservico);
	}
	
	public Senha buscaProximaSenha(String proximaChamada, String servico, String subservico) {
		return senhaDAO.buscaProximaSenha(proximaChamada, servico, subservico);
	}
}
