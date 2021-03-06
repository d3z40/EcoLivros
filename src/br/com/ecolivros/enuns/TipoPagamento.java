package br.com.ecolivros.enuns;

public enum TipoPagamento {
	
	CARTAODECREDITO(1, "Cartão de Crédito"),
	BOLETO(2, "Boleto"),
	CUPOM(3, "Cupom");
	
	private TipoPagamento(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	private int codigo;
	private String descricao;
	
	/**
	 * @return the codigo
	 */
	public int getCodigo() {
		return codigo;
	}
	
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
	
	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}