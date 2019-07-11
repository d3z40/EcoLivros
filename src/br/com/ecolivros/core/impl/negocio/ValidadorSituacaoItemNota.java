package br.com.ecolivros.core.impl.negocio;

import br.com.ecolivros.core.IStrategy;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.ItemNota;
import br.com.ecolivros.enuns.SituacaoEstoque;

public class ValidadorSituacaoItemNota implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof ItemNota){
			ItemNota itemNota = (ItemNota) entidade;
			
			StringBuilder msg = new StringBuilder();
			
			SituacaoEstoque situacaoEstoque = itemNota.getSituacaoEstoque();
			
			if(situacaoEstoque != null && !situacaoEstoque.equals(SituacaoEstoque.PENDENTE)) {
				msg.append("\"Situação do Estoque\" diferente de " + SituacaoEstoque.PENDENTE.getDescricao() + ", não permitido excluir/alterar nota.");
			}
			
			return msg.toString();
		} else {
			return "Deve ser registrado um Item!";
		}
	}
}
