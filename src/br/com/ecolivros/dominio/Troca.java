package br.com.ecolivros.dominio;

import java.util.List;

import br.com.ecolivros.enuns.SituacaoItem;

public class Troca extends EntidadeDominio {
	private int idPedido;
	private int idUsuario;
	private String numeroPedido;
    private double total;
    private int idLivro;
    private String titulo;
    private int idItemPedido;
    private double valor;
    private SituacaoItem situacaoItem;
    private byte aprovado;
	private List<Troca> trocas;
	
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
	 * @return the numeroPedido
	 */
	public String getNumeroPedido() {
		return numeroPedido;
	}
	
	/**
	 * @param numeroPedido the numeroPedido to set
	 */
	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
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
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	
	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
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

	/**
	 * @return the aprovado
	 */
	public byte getAprovado() {
		return aprovado;
	}

	/**
	 * @param aprovado the aprovado to set
	 */
	public void setAprovado(byte aprovado) {
		this.aprovado = aprovado;
	}

	/**
	 * @return the trocas
	 */
	public List<Troca> getTrocas() {
		return trocas;
	}

	/**
	 * @param trocas the trocas to set
	 */
	public void setTrocas(List<Troca> trocas) {
		this.trocas = trocas;
	}
}