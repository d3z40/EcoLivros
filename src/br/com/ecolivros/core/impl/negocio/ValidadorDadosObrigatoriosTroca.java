package br.com.ecolivros.core.impl.negocio;

import br.com.ecolivros.core.IStrategy;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Troca;

public class ValidadorDadosObrigatoriosTroca implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if (entidade instanceof Troca) {
			Troca trocaMain = (Troca) entidade;
			if (trocaMain != null && trocaMain.getTrocas() != null && !trocaMain.getTrocas().isEmpty()) {
				for (Troca troca : trocaMain.getTrocas()) {
					if (troca.getIdItemPedido() == 0)
						return "Item Pedido null.";
				}
			}
		}
		return null;
	}
}