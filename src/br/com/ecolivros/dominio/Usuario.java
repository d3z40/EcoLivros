package br.com.ecolivros.dominio;

import java.util.List;

import br.com.ecolivros.enuns.TipoUsuario;

public class Usuario extends EntidadeDominio {
	private String nome;
	private String email;
	private String senha;
	private String senhaRepetida;
	private String cpf;
	private TipoUsuario tipoUsuario;
	private Usuario usuario;
	private String chkRegister;
	private List<EntidadeDominio> enderecos;
	private List<EntidadeDominio> cartoes;
	
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}
	
	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * @return the senhaRepetida
	 */
	public String getSenhaRepetida() {
		return senhaRepetida;
	}

	/**
	 * @param senhaRepetida the senhaRepetida to set
	 */
	public void setSenhaRepetida(String senhaRepetida) {
		this.senhaRepetida = senhaRepetida;
	}
	
	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}
	
	/**
	 * @param cpf the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	/**
	 * @return the tipoUsuario
	 */
	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	/**
	 * @param tipoUsuario the tipoUsuario to set
	 */
	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the chkRegister
	 */
	public String getChkRegister() {
		return chkRegister;
	}

	/**
	 * @param chkRegister the chkRegister to set
	 */
	public void setChkRegister(String chkRegister) {
		this.chkRegister = chkRegister;
	}
	
	/**
	 * @return the enderecos
	 */
	public List<EntidadeDominio> getEnderecos() {
		return enderecos;
	}
	
	/**
	 * @param enderecos the enderecos to set
	 */
	public void setEnderecos(List<EntidadeDominio> enderecos) {
		this.enderecos = enderecos;
	}
	
	/**
	 * @return the cartoes
	 */
	public List<EntidadeDominio> getCartoes() {
		return cartoes;
	}
	
	/**
	 * @param cartoes the cartoes to set
	 */
	public void setCartoes(List<EntidadeDominio> cartoes) {
		this.cartoes = cartoes;
	}
}