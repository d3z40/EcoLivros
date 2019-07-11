package br.com.ecolivros.core.impl.negocio;

import br.com.ecolivros.core.IStrategy;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.ItemNota;
import br.com.ecolivros.dominio.Livro;
import br.com.ecolivros.enuns.SituacaoEstoque;

public class ValidadorDadosObrigatoriosItemNota implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof ItemNota){
			ItemNota itemNota = (ItemNota) entidade;
			
			StringBuilder msg = new StringBuilder();
			
			Livro livro = itemNota.getLivro();
			int qtde = itemNota.getQtde();
			double valor = itemNota.getValor();
			SituacaoEstoque situacaoEstoque = itemNota.getSituacaoEstoque();
			
			if(livro == null)
				msg.append("Livro é de preenchimento obrigatório!\n");
			
			if(situacaoEstoque == null)
				msg.append("Situação Estoque não preenchida, avise o analista de TI!!!\n");
			
			if(qtde <= 0 || valor <= 0)
				msg.append("Qtde e Valor são de preenchimento obrigatório!\\n");
						
			return msg.toString();
		} else {
			return "Deve ser registrado um Item!";
		}
	}
}