package br.com.ecolivros.core.impl.negocio;

import br.com.ecolivros.core.IStrategy;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.ItemPedido;
import br.com.ecolivros.enuns.SituacaoItem;

public class ValidadorDadosObrigatoriosItemPedido implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof ItemPedido){
			ItemPedido itemPedido = (ItemPedido) entidade;
			
			StringBuilder msg = new StringBuilder();
			
			int idEstoque = itemPedido.getIdEstoque();
			int idLivro = itemPedido.getIdLivro();
			int qtde = itemPedido.getQtde();
			double valor = itemPedido.getValor();
			SituacaoItem situacaoItem = itemPedido.getSituacaoItem();
			
			if(idEstoque <= 0)
				msg.append("IdEstoque null, avise o desenvolvedor!\\n");
			
			if(idLivro <= 0)
				msg.append("Livro é de preenchimento obrigatório!\n");
			
			if(qtde <= 0 || valor <= 0)
				msg.append("Qtde e Valor são de preenchimento obrigatório!\\n");
			
			if(situacaoItem == null)
				msg.append("Situação Item não preenchida, avise o analista de TI!!!\n");
			
			return msg.toString();
		} else {
			return "Deve ser registrado um Item!";
		}
	}
}