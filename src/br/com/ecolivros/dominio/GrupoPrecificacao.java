package br.com.ecolivros.dominio;

public class GrupoPrecificacao extends EntidadeDominio {
	private double margem;

	/**
	 * @return the margem
	 */
	public double getMargem() {
		return margem;
	}

	/**
	 * @param margem the margem to set
	 */
	public void setMargem(double margem) {
		this.margem = margem;
	}
}