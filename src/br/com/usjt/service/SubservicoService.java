package br.com.usjt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.usjt.dao.SubservicoDAO;
import br.com.usjt.entity.Subservico;

@Service
public class SubservicoService {
	SubservicoDAO subservicoDAO;
	
	@Autowired
	public SubservicoService(SubservicoDAO subservicoDAO) {
		this.subservicoDAO = subservicoDAO;
	}
	
	public Subservico loadSubservico(String idServico, int ordem) {
		return subservicoDAO.loadSubservico(idServico, ordem);
	}
}
