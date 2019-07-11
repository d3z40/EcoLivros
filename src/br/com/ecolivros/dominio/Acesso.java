package br.com.ecolivros.dominio;

import java.util.List;

public class Acesso extends EntidadeDominio {
	private String descricao;
	private List<EntidadeDominio> controlesAcesso;
	
	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the controlesAcesso
	 */
	public List<EntidadeDominio> getControlesAcesso() {
		return controlesAcesso;
	}

	/**
	 * @param controlesAcesso the controlesAcesso to set
	 */
	public void setControlesAcesso(List<EntidadeDominio> controlesAcesso) {
		this.controlesAcesso = controlesAcesso;
	}
}