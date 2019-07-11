package br.com.ecolivros.core.impl.negocio;

import br.com.ecolivros.core.IStrategy;
import br.com.ecolivros.dominio.CartaoDeCredito;
import br.com.ecolivros.dominio.EntidadeDominio;

public class ValidadorDadosObrigatoriosCartaoDeCredito implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof CartaoDeCredito){
			CartaoDeCredito cartaoDeCredito = (CartaoDeCredito) entidade;
			
			int idUsuario = cartaoDeCredito.getIdUsuario();
			String nomeNoCartao = cartaoDeCredito.getNomeNoCartao();
			String numeroDoCartao = cartaoDeCredito.getNumeroDoCartao();
			int mesValidade = cartaoDeCredito.getMesValidade();
			int anoValidade = cartaoDeCredito.getAnoValidade();
			int codigoSeguranca = cartaoDeCredito.getCodigoSeguranca();
			
			if(idUsuario <= 0)
				return "Erro grave, IdUsuario não encontrado, avise o Desenvolvedor.";
				
			if(nomeNoCartao == null || numeroDoCartao == null || mesValidade <= 0 || anoValidade <= 0 || codigoSeguranca <= 0)
				return "Nome no Cartão, Número do Cartão, Mês e Ano Validade e Código de Segurança são de preenchimento obrigatório!";
			
			if(nomeNoCartao.trim().equals("") || numeroDoCartao.trim().equals(""))
				return "Nome no Cartão e Número do Cartão são de preenchimento obrigatório!";
		} else {
			return "Deve ser registrado um cartão de crédito!";
		}
		return null;
	}
}