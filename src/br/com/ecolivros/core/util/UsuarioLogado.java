package br.com.ecolivros.core.util;

import br.com.ecolivros.dominio.Usuario;

public class UsuarioLogado {
	private static Usuario usuario;

	/**
	 * @return the usuario
	 */
	public static Usuario getUsuario() {
		return usuario;
	}
	
	/**
	 * @param usuario the usuario to set
	 */
	public static void setUsuario(Usuario usuario) {
		UsuarioLogado.usuario = usuario;
	}	
}