package br.com.usjt.dao;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.usjt.entity.Senha;

@Repository
public class SenhaDAO {
	@PersistenceContext
	EntityManager manager;

	public void gerarSenha(Senha senha) {
		List<Senha> list = manager.createQuery("select s from Senha s").getResultList();
		
		if (!list.isEmpty()) {
			String lastNome = list.get(list.size()-1).getNome();
			System.out.println(lastNome.substring(2));
		}else
			System.out.println("lastNome é nulo");
		//		List<Senha> senhas =  manager.createQuery("select s from senha s where s.id = last_insert_id()").getResultList();
//		System.out.println(x.toString());  
//		Senha lastSenha = manager.find(Senha.class, last);
//		int ultimoNome = Integer.parseInt(lastSenha.getNome().substring(2));
//		System.out.println(ultimoNome); 
//		
//		
//		Date date = new Date();
//		senha.setDataEntrada(date);
//		
//		
//		manager.persist(senha);
		
	}
}
