package br.com.usjt.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.usjt.entity.Atendimento;
import br.com.usjt.entity.Senha;
import br.com.usjt.entity.Servico;
import br.com.usjt.entity.Subservico;
import br.com.usjt.service.AtendimentoService;
import br.com.usjt.service.SenhaService;
import br.com.usjt.service.ServicoService;
import br.com.usjt.service.SubservicoService;
//TODO FAZER A ESTIMATIVA BASEADA NO TIPO DA SENHA!!!!!!!!

@Transactional
@Controller
public class GeneratorController {
	SenhaService senhaService;
	ServicoService servicoService;
	SubservicoService subservicoService;
	AtendimentoService atendimentoService;
	
	@Autowired
	public GeneratorController(SenhaService senhaService, ServicoService servicoService, SubservicoService subservicoService,AtendimentoService atendimentoService) {
		this.senhaService = senhaService;
		this.servicoService = servicoService;
		this.subservicoService = subservicoService;
		this.atendimentoService = atendimentoService;
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping("index")
	public String inicio() {
		return "index";
	}
	
	// Carrega Servicos
	@RequestMapping("/gerar_senha")
	public String criar_senha_gerador(Model model) {
		try {
			List<Servico> servicos = servicoService.listarServicos();
			model.addAttribute("servicos", servicos);
			return "gerar";
		} catch (Exception e) {
			e.printStackTrace();
			return "erro";
		}
	}
	
	//  Cria senha no servico escolhido + subservico de ordem 1 + tipo
	@RequestMapping("/senha_gerar")
	public String gerarSenha(@RequestParam(name="senha_tipo") String tipo, @RequestParam(name="senha_servico") String idServico,Model model) {
		try {
			Senha senha = new Senha();
			Servico servico = servicoService.loadServico(idServico);
			senha.setServico(servico);
			Subservico subservico = subservicoService.loadSubservico(idServico,1);
			senha.setSubservico(subservico);
			senha.setTipo(tipo);
			Atendimento atendimento = new Atendimento();
			atendimento.setSenha(senha);
			atendimento.setSubservico(subservico);
			senhaService.gerarSenha(senha);
			atendimentoService.gerarAtendimento(atendimento);
			model.addAttribute("atendimento", atendimento);
			criar_senha_gerador(model);
			return "gerar";
		} catch (Exception e) {
			e.printStackTrace();
			return "erro";
		}
	}
	
	@RequestMapping(value = "/atender_proxima_senha", method = RequestMethod.GET)
	@ResponseBody
	public Atendimento atenderSenha(HttpSession session, HttpServletResponse response, Model model, @RequestParam(name="servico")String servico, 
			@RequestParam(name="subservico")String subservico) {
		try {
//			List<Senha> senhas = senhaService.listarSenhasAtendimento(servico, Integer.parseInt(subservico));
			ProximaChamada proxChamada = (ProximaChamada) session.getAttribute("proximaChamada");
			if(proxChamada == null)	proxChamada = new ProximaChamada();
			int qntChamadas = proxChamada.getRG() + proxChamada.getCNPJ() + proxChamada.getCNH();
			if(qntChamadas > 3 )			
				proxChamada.setTipo("comum");
			if(qntChamadas == 3)			
				proxChamada.setTipo("emergencial");
			if(qntChamadas < 3)			
				proxChamada.setTipo("preferencial");
			
			if(servico.equals("RG"))		
				proxChamada.setRG(proxChamada.getRG()+1);
			else if(servico.equals("CJ"))		
				proxChamada.setCNH(proxChamada.getCNPJ()+1);
			else if(servico.equals("CN"))		
				proxChamada.setCNPJ(proxChamada.getCNH()+1);
			
			session.setAttribute("proximaChamada", proxChamada);
			Senha senha = senhaService.buscaProximaSenha(proxChamada, servico, subservico);
			senha.setStatus("atendimento");
			senhaService.updateSenha(senha);

			Atendimento atendimento = atendimentoService.loadAtendimento(senha);
			atendimento.setDataEntrada(new Date());
			long x = (atendimento.getDataEntrada().getTime()-atendimento.getDataGerado().getTime())/1000;
			atendimento.setEspera((int) x/60);
			atendimentoService.updateAtendimento(atendimento);
			System.out.println(atendimento);
			return atendimento;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("/senha_proxima")
	public String proximaSenha(@RequestParam(name="id") int id,Model model) {
		try {
			Senha senha = senhaService.loadSenha(id);
			senhaService.proximaSenha(senha);
			return "gerar";
		} catch (Exception e) {
			e.printStackTrace();
			return "erro";
		}
	}
	
	@RequestMapping(value = "/listar_senhas_atendimento", method = RequestMethod.GET)
	public @ResponseBody List<Senha> listarSenhasAtendimento(HttpSession session, HttpServletResponse response, Model model, String servico, String subservico) {			
		try {
			List<Senha> senhas = senhaService.listarSenhasAtendimento(servico, Integer.parseInt(subservico));
			return senhas;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
		
	@RequestMapping("/senha_listar")
	public String listarSenhas(Model model) {
		try {
			List<Senha> senhas = senhaService.listarSenha();
			model.addAttribute("senhas", senhas);
			return "listar";
		} catch (Exception e) {
			e.printStackTrace();
			return "erro";
		}
	}
	
	@RequestMapping("/senha_atender")
	public String senhaAtender(Model model, HttpSession session) {			
		try {
			List<Servico> servicos = servicoService.listarServicos();
			ProximaChamada proxChamada;
			proxChamada = (ProximaChamada) session.getAttribute("proximaChamada");
			if(proxChamada == null)
				session.setAttribute("proximaChamada", new ProximaChamada());
			model.addAttribute("servicos", servicos);
			
			return "atender";
		} catch (Exception e) {
			e.printStackTrace();
			return "erro";
		}
	}
	
	@RequestMapping(value = "/listar_subservico", method = RequestMethod.GET)
	@ResponseBody
	public List<Subservico> listarSubServico(@RequestParam String id, HttpServletRequest request, HttpServletResponse response, Model model) {			
		try {
			List<Subservico> subServicos = subservicoService.loadSubservico(id);
			return subServicos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
