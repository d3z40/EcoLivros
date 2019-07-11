package br.com.ecolivros.enuns;

public enum TipoRelatorio {
	
	PORDIA(1, "Por Dia"),
	PORMES(2, "Por Mês"),
	PORANO(3, "Por Ano");
	
	private int codigo;
	private String descricao;
	
	/**
	 * @param codigo
	 * @param descricao
	 */
	private TipoRelatorio(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

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