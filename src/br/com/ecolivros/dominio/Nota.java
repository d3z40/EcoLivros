package br.com.ecolivros.dominio;

import java.util.List;

import br.com.ecolivros.enuns.SituacaoEstoque;

public class Nota extends EntidadeDominio {
	private Fornecedor fornecedor;
	private String numeroNota;
	private SituacaoEstoque situacaoEstoque;
	private List<ItemNota> itensNota;
	
	/**
	 * @return the fornecedor
	 */
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	
	/**
	 * @param fornecedor the fornecedor to set
	 */
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	/**
	 * @return the numeroNota
	 */
	public String getNumeroNota() {
		return numeroNota;
	}

	/**
	 * @param numeroNota the numeroNota to set
	 */
	public void setNumeroNota(String numeroNota) {
		this.numeroNota = numeroNota;
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
	
	/**
	 * @return the itensNota
	 */
	public List<ItemNota> getItensNota() {
		return itensNota;
	}
	
	/**
	 * @param itensNota the itensNota to set
	 */
	public void setItensNota(List<ItemNota> itensNota) {
		this.itensNota = itensNota;
	}
}