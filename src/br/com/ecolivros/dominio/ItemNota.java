package br.com.ecolivros.dominio;

import br.com.ecolivros.enuns.SituacaoEstoque;

public class ItemNota extends EntidadeDominio {
	private Integer idNota;
	private Livro livro;
	private int qtde;
	private double valor;
	private SituacaoEstoque situacaoEstoque;
	
	/**
	 * @return the idNota
	 */
	public Integer getIdNota() {
		return idNota;
	}
	
	/**
	 * @param idNota the idNota to set
	 */
	public void setIdNota(Integer idNota) {
		this.idNota = idNota;
	}
	
	/**
	 * @return the livro
	 */
	public Livro getLivro() {
		return livro;
	}
	
	/**
	 * @param livro the livro to set
	 */
	public void setLivro(Livro livro) {
		this.livro = livro;
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