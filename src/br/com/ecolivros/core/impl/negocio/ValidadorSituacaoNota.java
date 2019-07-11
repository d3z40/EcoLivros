package br.com.ecolivros.core.impl.negocio;

import br.com.ecolivros.core.IStrategy;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.ItemNota;
import br.com.ecolivros.dominio.Nota;
import br.com.ecolivros.enuns.SituacaoEstoque;

public class ValidadorSituacaoNota implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof Nota){
			Nota nota = (Nota) entidade;
			
			StringBuilder msg = new StringBuilder();
			int i = 0;
			
			SituacaoEstoque situacaoEstoque = nota.getSituacaoEstoque();
			
			if(situacaoEstoque != null && !situacaoEstoque.equals(SituacaoEstoque.PENDENTE)) {
				msg.append("\"Situação do Estoque\" diferente de " + SituacaoEstoque.PENDENTE.getDescricao() + ", não permitido excluir nota.");
			}
			
			if (nota.getItensNota() != null && nota.getItensNota().size() > 0) {
				String msgTemp;
				ValidadorSituacaoItemNota strategyItemNota = new ValidadorSituacaoItemNota();
				
				for (ItemNota item : nota.getItensNota()) {
					i++;
					msgTemp = strategyItemNota.processar(item);
					
					if (msgTemp != null && !msgTemp.trim().equals(""))
						msg.append("Item " + i + ": " + msgTemp + "\n");
				}
			}
			
			return msg.toString();
		} else {
			return "Deve ser registrado uma nota!";
		}
	}
}