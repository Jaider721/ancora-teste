package br.com.ancoraweb.bean;

import java.util.Date;

public class Proposta {
	
	private String proosta;
    
	private Date data;
    
    private String observacao;

	public String getProosta() {
		return proosta;
	}

	public void setProosta(String proosta) {
		this.proosta = proosta;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

    
}
