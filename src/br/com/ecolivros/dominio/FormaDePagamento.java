package br.com.ecolivros.dominio;

import br.com.ecolivros.enuns.TipoPagamento;

public class FormaDePagamento extends EntidadeDominio {
	private int idPedido;
	private Pagamento pagamento;
	private double valor;
	private TipoPagamento tipoPagamento;
	
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
	 * @return the pagamento
	 */
	public Pagamento getPagamento() {
		return pagamento;
	}

	/**
	 * @param pagamento the pagamento to set
	 */
	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
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
	 * @return the tipoPagamento
	 */
	public TipoPagamento getTipoPagamento() {
		return tipoPagamento;
	}

	/**
	 * @param tipoPagamento the tipoPagamento to set
	 */
	public void setTipoPagamento(TipoPagamento tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}
}