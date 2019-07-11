package br.com.ecolivros.dominio;

import java.util.List;

import br.com.ecolivros.enuns.Frete;

public class Pedido extends EntidadeDominio {
	private int idUsuario;
	private String numPedido;
	private Frete frete;
	private List<FormaDePagamento> formasDePgto;
	private List<ItemPedido> itensPedido;
	private Endereco endereco;
	private double total;
	
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
	 * @return the numPedido
	 */
	public String getNumPedido() {
		return numPedido;
	}

	/**
	 * @param numPedido the numPedido to set
	 */
	public void setNumPedido(String numPedido) {
		this.numPedido = numPedido;
	}

	/**
	 * @return the frete
	 */
	public Frete getFrete() {
		return frete;
	}
	
	/**
	 * @param frete the frete to set
	 */
	public void setFrete(Frete frete) {
		this.frete = frete;
	}
	
	/**
	 * @return the formasDePgto
	 */
	public List<FormaDePagamento> getFormasDePgto() {
		return formasDePgto;
	}

	/**
	 * @param formasDePgto the formasDePgto to set
	 */
	public void setFormasDePgto(List<FormaDePagamento> formasDePgto) {
		this.formasDePgto = formasDePgto;
	}

	/**
	 * @return the itensPedido
	 */
	public List<ItemPedido> getItensPedido() {
		return itensPedido;
	}

	/**
	 * @param itensPedido the itensPedido to set
	 */
	public void setItensPedido(List<ItemPedido> itensPedido) {
		this.itensPedido = itensPedido;
	}

	/**
	 * @return the endereco
	 */
	public Endereco getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return the total
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(double total) {
		this.total = total;
	}
}