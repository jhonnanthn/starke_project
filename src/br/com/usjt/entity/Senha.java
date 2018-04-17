package br.com.usjt.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="senha")
public class Senha {
	@Id
	@NotNull
	@Column(name="id")
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
	@Column(name="estimativa_fila")
	private Date estimativaFila;
	@Column(name="estimativa_atendimento")
	private Date estimativaAtendimento;
	
	
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
	
	public Date getEstimativaFila() {
		return estimativaFila;
	}
	public void setEstimativaFila(Date estimativaFila) {
		this.estimativaFila = estimativaFila;
	}
	public Date getEstimativaAtendimento() {
		return estimativaAtendimento;
	}
	public void setEstimativaAtendimento(Date estimativaAtendimento) {
		this.estimativaAtendimento = estimativaAtendimento;
	}
	
	@Override
	public String toString() {
		return "Senha [id=" + id + ", servico=" + servico + ", subservico=" + subservico + ", tipo=" + tipo + ", nome="
				+ nome + ", status=" + status + ", dataEntrada=" + dataEntrada + ", dataSaida=" + dataSaida
				+ ", estimativaFila=" + estimativaFila
				+ ", estimativaAtendimento=" + estimativaAtendimento + "]";
	}
	
	
}
