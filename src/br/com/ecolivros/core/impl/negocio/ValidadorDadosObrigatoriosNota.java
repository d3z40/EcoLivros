package br.com.ecolivros.core.impl.negocio;

import br.com.ecolivros.core.IStrategy;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Fornecedor;
import br.com.ecolivros.dominio.ItemNota;
import br.com.ecolivros.dominio.Nota;
import br.com.ecolivros.enuns.SituacaoEstoque;

public class ValidadorDadosObrigatoriosNota implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof Nota){
			Nota nota = (Nota) entidade;
			
			StringBuilder msg = new StringBuilder();
			int i = 0;
			
			Fornecedor fornecedor = nota.getFornecedor();
			String numeroNota = nota.getNumeroNota();
			SituacaoEstoque situacaoEstoque = nota.getSituacaoEstoque();
			
			if(fornecedor == null || numeroNota == null || situacaoEstoque == null)
				msg.append("Fornecedor, Número Nota e Situação Estoque são de preenchimento obrigatório!\n");
			
			if(numeroNota != null && numeroNota.trim().equals(""))
				msg.append("Número Nota é de preenchimento obrigatório!\n");
						
			if (nota.getItensNota().size() > 0) {
				String msgTemp;
				ValidadorDadosObrigatoriosItemNota strategyItemNota = new ValidadorDadosObrigatoriosItemNota();
				ComplementarDtCadastro cDtCadastro = new ComplementarDtCadastro();
				
				for (ItemNota item : nota.getItensNota()) {
					i++;
					msgTemp = strategyItemNota.processar(item);
					
					if (msgTemp != null && !msgTemp.trim().equals(""))
						msg.append("Item " + i + ": " + msgTemp + "\n");
					
					msgTemp = cDtCadastro.processar(item);
					
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