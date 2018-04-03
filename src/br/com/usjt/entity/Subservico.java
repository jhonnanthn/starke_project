package br.com.usjt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="subservico")
public class Subservico {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="id_servico")
	private Servico servico;
	
	@Column(name="ordem")
	@NotNull
	private int ordem;
	
	@Column(name="nome")
	@NotNull
	private String nome;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Servico getServico() {
		return servico;
	}
	public void setServico(Servico servico) {
		this.servico = servico;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getOrdem() {
		return ordem;
	}
	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}
	@Override
	public String toString() {
		return "Subservico [id=" + id + ", servico=" + servico + ", ordem=" + ordem + ", nome=" + nome + "]";
	}
	
}
