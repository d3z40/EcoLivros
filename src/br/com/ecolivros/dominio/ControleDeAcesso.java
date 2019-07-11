package br.com.ecolivros.dominio;

import br.com.ecolivros.enuns.TipoAcesso;

public class ControleDeAcesso extends EntidadeDominio {
	private int idAcesso;
	private TipoAcesso tipoAcesso;
	
	/**
	 * @return the idAcesso
	 */
	public int getIdAcesso() {
		return idAcesso;
	}

	/**
	 * @param idAcesso the idAcesso to set
	 */
	public void setIdAcesso(int idAcesso) {
		this.idAcesso = idAcesso;
	}

	/**
	 * @return the tipoAcesso
	 */
	public TipoAcesso getTipoAcesso() {
		return tipoAcesso;
	}
	
	/**
	 * @param tipoAcesso the tipoAcesso to set
	 */
	public void setTipoAcesso(TipoAcesso tipoAcesso) {
		this.tipoAcesso = tipoAcesso;
	}
}