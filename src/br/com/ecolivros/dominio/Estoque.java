package br.com.ecolivros.dominio;

import br.com.ecolivros.enuns.SituacaoEstoque;

public class Estoque extends EntidadeDominio {
	private int idLivro;
	private double valor;
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