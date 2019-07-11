package br.com.ecolivros.dominio;

import java.util.Date;

public class EntidadeDominio implements IEntidade {
	private Integer id;
	private Date dtCadastro;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the dtCadastro
	 */
	public Date getDtCadastro() {
		return dtCadastro;
	}
	
	/**
	 * @param dtCadastro the dtCadastro to set
	 */
	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}
}