package br.com.ecolivros.core.impl.negocio;

import br.com.ecolivros.core.IStrategy;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Usuario;

public class ValidadorCpf implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if (entidade instanceof Usuario) {
			Usuario usuario = (Usuario) entidade;
			if (usuario.getCpf() == null)
				return "CPF não pode ser nulo!";
			if (usuario.getCpf().trim().equals(""))
				return "CPF deve ser preenchido!";
		} else {
			return "CPF não pode ser válidado, pois entidade não é de um tipo válido!";
		}
		return null;
	}
}