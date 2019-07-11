package br.com.ecolivros.dominio;

public class Autor extends EntidadeDominio {
	private String nomeArtistico;
	private String nomeCompleto;

	/**
	 * @return the nomeArtistico
	 */
	public String getNomeArtistico() {
		return nomeArtistico;
	}

	/**
	 * @param nomeArtistico the nomeArtistico to set
	 */
	public void setNomeArtistico(String nomeArtistico) {
		this.nomeArtistico = nomeArtistico;
	}

	/**
	 * @return the nomeCompleto
	 */
	public String getNomeCompleto() {
		return nomeCompleto;
	}

	/**
	 * @param nomeCompleto the nomeCompleto to set
	 */
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
}
