package br.com.ecolivros.enuns;

public enum TipoAcesso {
	
	CADASTRAR(1, "Salvar"),
	ALTERAR(2, "Alterar"),
	EXCLUIR(3, "Excluir"),
	CONSULTAR(4, "Consultar"),
	LIBERAR(5, "Liberar");
	
	private int codigo;
	private String descricao;
	
	/**
	 * @param codigo
	 * @param descricao
	 */
	private TipoAcesso(int codigo, String descricao) {
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