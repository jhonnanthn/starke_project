package br.com.usjt.controller;

public final class ProximaChamada {
	private int RG = 0;
	private int CNPJ = 0;
	private int CNH = 0;
	
	private String tipo = "";

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getRG() {
		return RG;
	}

	public int getCNPJ() {
		return CNPJ;
	}

	public int getCNH() {
		return CNH;
	}

	public void setRG(int rG) {
		RG = rG;
	}

	public void setCNPJ(int cNPJ) {
		CNPJ = cNPJ;
	}

	public void setCNH(int cNH) {
		CNH = cNH;
	}
}
