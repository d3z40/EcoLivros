package br.com.ecolivros.controle.web.command.impl;

import br.com.ecolivros.core.aplicacao.Resultado;
import br.com.ecolivros.dominio.EntidadeDominio;

public class ExcluirCommand extends AbstractCommand {

	@Override
	public Resultado execute(EntidadeDominio entidade) {
		return fachada.excluir(entidade);
	}
}