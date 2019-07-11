package br.com.ecolivros.dominio;

import br.com.ecolivros.enuns.SituacaoItem;

public class ItemPedido extends EntidadeDominio {
	private int idPedido;
	private int idEstoque;
	private int idLivro;
	private int qtde;
	private double valor;
	private SituacaoItem situacaoItem;
	
	/**
	 * @return the idPedido
	 */
	public int getIdPedido() {
		return idPedido;
	}

	/**
	 * @param idPedido the idPedido to set
	 */
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

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
	 * @return the idEstoque
	 */
	public int getIdEstoque() {
		return idEstoque;
	}

	/**
	 * @param idEstoque the idEstoque to set
	 */
	public void setIdEstoque(int idEstoque) {
		this.idEstoque = idEstoque;
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
	 * @return the valor
	 */
	public double getValor() {
		return valor;
	}
	
	/**
	 * @param valor the valor to set
	 */
	public void setValor(double valor) {
		this.valor = valor;
	}

	/**
	 * @return the situacaoItem
	 */
	public SituacaoItem getSituacaoItem() {
		return situacaoItem;
	}

	/**
	 * @param situacaoItem the situacaoItem to set
	 */
	public void setSituacaoItem(SituacaoItem situacaoItem) {
		this.situacaoItem = situacaoItem;
	}
}