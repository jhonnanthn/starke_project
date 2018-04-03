package br.com.usjt.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.usjt.entity.Senha;
import br.com.usjt.entity.Servico;
import br.com.usjt.service.SenhaService;
import br.com.usjt.service.ServicoService;

/**
 * 
 * @author starke
 *
 */
@Transactional
@Controller
public class GeneratorController {
	SenhaService senhaService;
	ServicoService servicoService;
	
	@Autowired
	public GeneratorController(SenhaService senhaService, ServicoService servicoService) {
		this.senhaService = senhaService;
		this.servicoService = servicoService;
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping("index")
	public String inicio() {
		return "index";
	}
	
	@RequestMapping("/criar_senha_gerador")
	public String criar_senha_gerador(Model model) {
		try {
			List<Servico> servicos = servicoService.listarServicos();
			model.addAttribute("servicos", servicos);
			return "gerar";
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro";
		}
	}
	
	@RequestMapping("/gerar_senha")
	public String gerarSenha(Model model) {
		try {
			Senha senha = new Senha();
			Servico servico = new Servico();
			servico.setId("XX");
			senha.setServico(servico);
			senha.setTipo("comum");
			senha.setStatus("ativo");
			senhaService.gerarSenha(senha); 
			return "gerar";
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro";
		}
	}
}
