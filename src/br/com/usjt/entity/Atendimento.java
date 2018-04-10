package br.com.usjt.entity;

import java.util.Date;

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
@Table(name="atendimento")
public class Atendimento {
	@Id
	@NotNull
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@NotNull
	@JoinColumn(name="id_senha")
	private Senha senha;
	@ManyToOne
	@NotNull
	@JoinColumn(name="id_subservico")
	private Subservico subservico;
	@Column(name="data_gerado")
	private Date dataGerado;
	@Column(name="data_entrada")
	private Date dataEntrada;
	@Column(name="data_saida")
	private Date dataSaida;
	@Column(name="duracao")
	private int duracao;
	@Column(name="espera")
	private int espera;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Senha getSenha() {
		return senha;
	}
	public void setSenha(Senha senha) {
		this.senha = senha;
	}
	public Subservico getSubservico() {
		return subservico;
	}
	public void setSubservico(Subservico subservico) {
		this.subservico = subservico;
	}
	public Date getDataEntrada() {
		return dataEntrada;
	}
	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	public Date getDataSaida() {
		return dataSaida;
	}
	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}
	public int getDuracao() {
		return duracao;
	}
	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}
	
	public int getEspera() {
		return espera;
	}
	public void setEspera(int espera) {
		this.espera = espera;
	}
	
	public Date getDataGerado() {
		return dataGerado;
	}
	public void setDataGerado(Date dataGerado) {
		this.dataGerado = dataGerado;
	}
	
	@Override
	public String toString() {
		return "Atendimento [id=" + id + ", senha=" + senha + ", subservico=" + subservico + ", dataGerado="
				+ dataGerado + ", dataEntrada=" + dataEntrada + ", dataSaida=" + dataSaida + ", duracao=" + duracao
				+ ", espera=" + espera + "]";
	}
	public Atendimento() {
		super();
	}
	
	
}
