package br.com.ecolivros.dominio;

public class Funcionario extends Usuario {
	private int idUsuario;
	private EntidadeDominio acesso;
	private String numRegistro;
	
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
	 * @return the acesso
	 */
	public EntidadeDominio getAcesso() {
		return acesso;
	}
	
	/**
	 * @param acesso the acesso to set
	 */
	public void setAcesso(EntidadeDominio acesso) {
		this.acesso = acesso;
	}
	
	/**
	 * @return the numRegistro
	 */
	public String getNumRegistro() {
		return numRegistro;
	}
	
	/**
	 * @param numRegistro the numRegistro to set
	 */
	public void setNumRegistro(String numRegistro) {
		this.numRegistro = numRegistro;
	}
}