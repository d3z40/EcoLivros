package br.com.ecolivros.dominio;

public class Cupom extends Pagamento {
	private int idUsuario;
	private String identificador;
	private double valorCupom;
	private double valorUtilizado;
	
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
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}
	
	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	
	/**
	 * @return the valorCupom
	 */
	public double getValorCupom() {
		return valorCupom;
	}
	
	/**
	 * @param valorCupom the valorCupom to set
	 */
	public void setValorCupom(double valorCupom) {
		this.valorCupom = valorCupom;
	}
	
	/**
	 * @return the valorUtilizado
	 */
	public double getValorUtilizado() {
		return valorUtilizado;
	}
	
	/**
	 * @param valorUtilizado the valorUtilizado to set
	 */
	public void setValorUtilizado(double valorUtilizado) {
		this.valorUtilizado = valorUtilizado;
	}
}