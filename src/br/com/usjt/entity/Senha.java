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
@Table(name="senha")
public class Senha {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@NotNull
	@ManyToOne
	@JoinColumn(name="id_servico")
	private Servico servico;
	@NotNull
	@ManyToOne
	@JoinColumn(name="id_subservico")
	private Subservico subservico;
	@NotNull
	@ManyToOne
	@JoinColumn(name="id_fila")
	private Fila fila;
	
	@Column(name="tipo")
	private String tipo;
	@Column(name="nome")
    private String nome;
	@Column(name="status")
	private String status;
	@Column(name="data_entrada")
	private Date dataEntrada;
	@Column(name="data_saida")
	private Date dataSaida;
	
	public Subservico getSubservico() {
		return subservico;
	}
	public void setSubservico(Subservico subservico) {
		this.subservico = subservico;
	}
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
	public Fila getFila() {
		return fila;
	}
	public void setFila(Fila fila) {
		this.fila = fila;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
}
