package br.com.ecolivros.dominio;

import br.com.ecolivros.enuns.SituacaoEstoque;

public class Carrinho extends EntidadeDominio {
	private int idLivro;
	private int idUsuario;
	private int qtde;
	private SituacaoEstoque situacaoEstoque;
	
	/**
	 * @return the idLivro
	 */
	public int getIdLivro() {
		return idLivro;
	}
	
	/**
	 * @param idLivro the idLivro to set
	 */
	public void setIdLivro(int idLivro) {
		this.idLivro = idLivro;
	}
	
	/**
	 * @return the idUsuario
	 */
	public int getIdUsuario() {
		return idUsuario;
	}
	
	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	/**
	 * @return the qtde
	 */
	public int getQtde() {
		return qtde;
	}
	
	/**
	 * @param qtde the qtde to set
	 */
	public void setQtde(int qtde) {
		this.qtde = qtde;
	}

	/**
	 * @return the situacaoEstoque
	 */
	public SituacaoEstoque getSituacaoEstoque() {
		return situacaoEstoque;
	}

	/**
	 * @param situacaoEstoque the situacaoEstoque to set
	 */
	public void setSituacaoEstoque(SituacaoEstoque situacaoEstoque) {
		this.situacaoEstoque = situacaoEstoque;
	}
}