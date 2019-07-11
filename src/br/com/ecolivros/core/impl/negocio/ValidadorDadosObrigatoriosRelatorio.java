package br.com.ecolivros.core.impl.negocio;

import java.util.Date;

import br.com.ecolivros.core.IStrategy;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Relatorio;

public class ValidadorDadosObrigatoriosRelatorio implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof Relatorio){
			Relatorio relatorio = (Relatorio) entidade;
			
			Date dataInicial = relatorio.getDataInicial();
			Date dataFinal = relatorio.getDataFinal();
			
			if(dataInicial == null || dataFinal == null)
				return "Data Inicial e Data Final são obrigatórias.";
			
			if(dataInicial != null || dataFinal != null) {
				if (dataInicial.after(dataFinal))
					return "Data Inicial deve ser menor que Data Final.";
			}
		} else {
			return "Deve ser registrado um relatorio!";
		}
		return null;
	}
}