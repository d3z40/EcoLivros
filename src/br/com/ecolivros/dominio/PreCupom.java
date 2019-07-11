package br.com.ecolivros.dominio;

import br.com.ecolivros.enuns.SituacaoEstoque;

public class PreCupom extends EntidadeDominio {
	private int idUsuario;
	private int idItemPedido;
	private double valor;
	private SituacaoEstoque situacaoEstoque;
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
	 * @return the idItemPedido
	 */
	public int getIdItemPedido() {
		return idItemPedido;
	}
	/**
	 * @param idItemPedido the idItemPedido to set
	 */
	public void setIdItemPedido(int idItemPedido) {
		this.idItemPedido = idItemPedido;
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