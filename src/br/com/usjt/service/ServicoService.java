package br.com.usjt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.usjt.dao.ServicoDAO;
import br.com.usjt.entity.Servico;

@Service
public class ServicoService {

	private ServicoDAO servicoDAO;
	
	@Autowired
	public ServicoService(ServicoDAO serviceDAO) {
		this.servicoDAO = serviceDAO;
	}
	
	public List<Servico> listarServicos() {
		return servicoDAO.listarServicos();
	}

	public Servico loadServico(String idServico) {
		return servicoDAO.loadServico(idServico);
	}
}
