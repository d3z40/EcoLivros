package br.com.ecolivros.core;

import br.com.ecolivros.dominio.EntidadeDominio;

public interface IStrategy {
	public String processar(EntidadeDominio entidade);
}