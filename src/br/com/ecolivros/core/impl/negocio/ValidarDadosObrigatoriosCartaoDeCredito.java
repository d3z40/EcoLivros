package br.com.ecolivros.core.impl.negocio;

import br.com.ecolivros.core.IStrategy;
import br.com.ecolivros.dominio.CartaoDeCredito;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.enuns.BandeiraCartaoDeCredito;

public class ValidarDadosObrigatoriosCartaoDeCredito implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof CartaoDeCredito){
			CartaoDeCredito cartaoDeCredito = (CartaoDeCredito) entidade;
			
			String nomeNoCartao = cartaoDeCredito.getNomeNoCartao();
			String numeroDoCartao = cartaoDeCredito.getNumeroDoCartao();
			int mesValidade = cartaoDeCredito.getMesValidade();
			int anoValidade = cartaoDeCredito.getAnoValidade();
			int codigoSeguranca = cartaoDeCredito.getCodigoSeguranca();
			BandeiraCartaoDeCredito bandeira = cartaoDeCredito.getBandeira();
			
			if(nomeNoCartao == null || numeroDoCartao == null || mesValidade == 0 || anoValidade == 0 || codigoSeguranca == 0 || bandeira == null){
				return "Nome no Cartão, Número do Cartão de Crédito, Data de Validade, Código de Segurança e Bandeira são de preenchimento obrigatório!";
			}
			
			if(nomeNoCartao.trim().equals("") || numeroDoCartao.trim().equals(""))
				return "Nome no Cartão, Número do Cartão de Crédito e Bandeira são de preenchimento obrigatório!";
		} else {
			return "Deve ser registrado um Cartão de Crédito!";
		}
		return null;
	}
}