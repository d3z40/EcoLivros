package br.com.ecolivros.core;

import br.com.ecolivros.core.aplicacao.Resultado;
import br.com.ecolivros.dominio.EntidadeDominio;

public interface IFachada {
	public Resultado salvar(EntidadeDominio entidade);
	public Resultado alterar(EntidadeDominio entidade);
	public Resultado excluir(EntidadeDominio entidade);
	public Resultado consultar(EntidadeDominio entidade);
	public Resultado visualizar(EntidadeDominio entidade);
}