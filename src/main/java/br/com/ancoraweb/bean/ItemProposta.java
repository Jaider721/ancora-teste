package br.com.ancoraweb.bean;

public class ItemProposta {
	
    private String descricao;
    private String und;
    
    private int item;
    private int qtd;
    
    private float precoUnitario;
    private float precoTotal;
    
    private Long proposta;
    
    private Long codItem;
    
    public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getUnd() {
		return und;
	}
	public void setUnd(String und) {
		this.und = und;
	}
	public int getQtd() {
		return qtd;
	}
	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
	public float getPrecoUnitario() {
		return precoUnitario;
	}
	public void setPrecoUnitario(float precoUnitario) {
		this.precoUnitario = precoUnitario;
	}
	public float getPrecoTotal() {
		return precoTotal;
	}
	public void setPrecoTotal(float precoTotal) {
		this.precoTotal = precoTotal;
	}
	public int getItem() {
		return item;
	}
	public void setItem(int item) {
		this.item = item;
	}
	public Long getProposta() {
		return proposta;
	}
	public void setProposta(Long proposta) {
		this.proposta = proposta;
	}
	public Long getCodItem() {
		return codItem;
	}
	public void setCodItem(Long codItem) {
		this.codItem = codItem;
	}

}
