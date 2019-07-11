package br.com.ecolivros.core.impl.negocio;

import br.com.ecolivros.core.IStrategy;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.ItemPedido;
import br.com.ecolivros.enuns.SituacaoItem;

public class ValidadorSituacaoItemPedido implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof ItemPedido){
			ItemPedido itemPedido = (ItemPedido) entidade;
			
			StringBuilder msg = new StringBuilder();
			
			SituacaoItem situacaoItem = itemPedido.getSituacaoItem();
			
			if(situacaoItem != null && !situacaoItem.equals(SituacaoItem.ENTREGUE)) {
				msg.append("\"Situação do Item\" diferente de " + SituacaoItem.ENTREGUE.getDescricao() + ", não permitido trocar/alterar item.");
			}
			
			return msg.toString();
		} else {
			return "Deve ser registrado um Item!";
		}
	}
}