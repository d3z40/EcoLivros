package br.com.ecolivros.controle.web.command;

import br.com.ecolivros.core.aplicacao.Resultado;
import br.com.ecolivros.dominio.EntidadeDominio;

public interface ICommand {
	public Resultado execute(EntidadeDominio entidade);
}