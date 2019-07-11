package br.com.ecolivros.dominio;

import br.com.ecolivros.enuns.BandeiraCartaoDeCredito;

public class CartaoDeCredito extends Pagamento {
	private int idFormaDePagamento;
	private int idUsuario;
	private String nomeNoCartao;
	private String numeroDoCartao;
	private int mesValidade;
	private int anoValidade;
	private int codigoSeguranca;
	private BandeiraCartaoDeCredito bandeira;
	
	/**
	 * @return the idFormaDePagamento
	 */
	public int getIdFormaDePagamento() {
		return idFormaDePagamento;
	}

	/**
	 * @param idFormaDePagamento the idFormaDePagamento to set
	 */
	public void setIdFormaDePagamento(int idFormaDePagamento) {
		this.idFormaDePagamento = idFormaDePagamento;
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
	 * @return the nomeNoCartao
	 */
	public String getNomeNoCartao() {
		return nomeNoCartao;
	}
	
	/**
	 * @param nomeNoCartao the nomeNoCartao to set
	 */
	public void setNomeNoCartao(String nomeNoCartao) {
		this.nomeNoCartao = nomeNoCartao;
	}
	
	/**
	 * @return the numeroDoCartao
	 */
	public String getNumeroDoCartao() {
		return numeroDoCartao;
	}
	
	/**
	 * @param numeroDoCartao the numeroDoCartao to set
	 */
	public void setNumeroDoCartao(String numeroDoCartao) {
		this.numeroDoCartao = numeroDoCartao;
	}
	
	/**
	 * @return the mesValidade
	 */
	public int getMesValidade() {
		return mesValidade;
	}
	
	/**
	 * @param mesValidade the mesValidade to set
	 */
	public void setMesValidade(int mesValidade) {
		this.mesValidade = mesValidade;
	}
	
	/**
	 * @return the anoValidade
	 */
	public int getAnoValidade() {
		return anoValidade;
	}
	
	/**
	 * @param anoValidade the anoValidade to set
	 */
	public void setAnoValidade(int anoValidade) {
		this.anoValidade = anoValidade;
	}
	
	/**
	 * @return the codigoSeguranca
	 */
	public int getCodigoSeguranca() {
		return codigoSeguranca;
	}
	
	/**
	 * @param codigoSeguranca the codigoSeguranca to set
	 */
	public void setCodigoSeguranca(int codigoSeguranca) {
		this.codigoSeguranca = codigoSeguranca;
	}
	
	/**
	 * @return the bandeira
	 */
	public BandeiraCartaoDeCredito getBandeira() {
		return bandeira;
	}
	
	/**
	 * @param bandeira the bandeira to set
	 */
	public void setBandeira(BandeiraCartaoDeCredito bandeira) {
		this.bandeira = bandeira;
	}
}