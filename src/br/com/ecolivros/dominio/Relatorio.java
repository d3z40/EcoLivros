package br.com.ecolivros.dominio;

import java.util.Date;

import br.com.ecolivros.enuns.TipoRelatorio;

public class Relatorio extends EntidadeDominio {
	private String titulo;
	private Date dataInicial;
	private Date dataFinal;
	private Date data;
	private int mes;
	private int ano;
	private double qtde;
	private double valor;
	private TipoRelatorio tipoRelatorio; 
	
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
	 * @return the dataInicial
	 */
	public Date getDataInicial() {
		return dataInicial;
	}

	/**
	 * @param dataInicial the dataInicial to set
	 */
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	/**
	 * @return the dataFinal
	 */
	public Date getDataFinal() {
		return dataFinal;
	}

	/**
	 * @param dataFinal the dataFinal to set
	 */
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	/**
	 * @return the data
	 */
	public Date getData() {
		return data;
	}
	
	/**
	 * @param data the data to set
	 */
	public void setData(Date data) {
		this.data = data;
	}
	
	/**
	 * @return the mes
	 */
	public int getMes() {
		return mes;
	}
	
	/**
	 * @param mes the mes to set
	 */
	public void setMes(int mes) {
		this.mes = mes;
	}
	
	/**
	 * @return the ano
	 */
	public int getAno() {
		return ano;
	}
	
	/**
	 * @param ano the ano to set
	 */
	public void setAno(int ano) {
		this.ano = ano;
	}
	
	/**
	 * @return the qtde
	 */
	public double getQtde() {
		return qtde;
	}
	
	/**
	 * @param qtde the qtde to set
	 */
	public void setQtde(double qtde) {
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
	 * @return the tipoRelatorio
	 */
	public TipoRelatorio getTipoRelatorio() {
		return tipoRelatorio;
	}

	/**
	 * @param tipoRelatorio the tipoRelatorio to set
	 */
	public void setTipoRelatorio(TipoRelatorio tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}
}