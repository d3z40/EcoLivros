package br.com.ecolivros.enuns;

public enum TipoUsuario {
	
	CLIENTE(1, "Cliente"),
	FUNCIONARIO(2, "Funcionário");
	
	private int codigo;
	private String descriao;
	
	/**
	 * @param codigo
	 * @param descriao
	 */
	private TipoUsuario(int codigo, String descriao) {
		this.codigo = codigo;
		this.descriao = descriao;
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
	 * @return the descriao
	 */
	public String getDescriao() {
		return descriao;
	}

	/**
	 * @param descriao the descriao to set
	 */
	public void setDescriao(String descriao) {
		this.descriao = descriao;
	}
	
	public static TipoUsuario getTipoUsuario(int codigoTipoUsuario) {
		for (TipoUsuario tp : TipoUsuario.values()) {
			if (tp.getCodigo() == codigoTipoUsuario)
				return tp;
		}
		return null;
	}
}