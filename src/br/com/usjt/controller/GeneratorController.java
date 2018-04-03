package br.com.usjt.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.usjt.entity.Servico;
import br.com.usjt.service.GeneratorService;
import br.com.usjt.service.ServicoService;

/**
 * 
 * @author starke
 *
 */
@Transactional
@Controller
public class GeneratorController {
	GeneratorService generator;
	ServicoService servicoService;
	
	@Autowired
	public GeneratorController(GeneratorService generator, ServicoService servicoService) {
		this.generator = generator;
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
}
