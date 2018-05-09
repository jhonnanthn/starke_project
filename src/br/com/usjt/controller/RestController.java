package br.com.usjt.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.usjt.entity.Atendimento;
import br.com.usjt.entity.Senha;
import br.com.usjt.entity.Servico;
import br.com.usjt.entity.Subservico;
import br.com.usjt.service.AtendimentoService;
import br.com.usjt.service.SenhaService;
import br.com.usjt.service.ServicoService;
import br.com.usjt.service.SubservicoService;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	private ServicoService servicoS;
	private SubservicoService subservicoS;
	private SenhaService senhaS;
	private AtendimentoService atendimentoS;

	@Autowired
	public RestController(ServicoService a, SubservicoService b, 
			SenhaService c, AtendimentoService d) {
		servicoS = a;
		subservicoS = b;
		senhaS = c;
		atendimentoS = d;
	}

	
	@RequestMapping(method = RequestMethod.GET, value = "rest/servicos")
	public @ResponseBody List<Servico> listarServicos() throws IOException {
		List<Servico> servicos = null;
		servicos = servicoS.listarServicos();
		return servicos;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "rest/senhas")
	public @ResponseBody List<Senha> listarSenhas() throws IOException {
		List<Senha> senhas = null;
		senhas = senhaS.listarSenha();
		return senhas;
	}
	

	@RequestMapping(method = RequestMethod.GET, value = "rest/criar_senha/{tipo}/{id_servico}")
	public synchronized @ResponseBody Senha listarChamados(@PathVariable("tipo") String tipo,
			@PathVariable("id_servico") String idServico) {

		Senha senha = new Senha();
		Servico servico = servicoS.loadServico(idServico);
		senha.setServico(servico);
		Subservico subservico = subservicoS.loadSubservico(idServico, 1);
		senha.setSubservico(subservico);
		senha.setTipo(tipo);
		Atendimento atendimento = new Atendimento();
		atendimento.setSenha(senha);
		atendimento.setSubservico(subservico);
		
		senhaS.gerarSenha(senha);
		
		atendimentoS.gerarAtendimento(atendimento);

		return senha;
	}
	
}
