package br.com.usjt.service;

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


}
