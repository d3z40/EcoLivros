package br.com.ecolivros.core.impl.negocio;

import java.sql.SQLException;

import br.com.ecolivros.core.IStrategy;
import br.com.ecolivros.core.impl.dao.CupomDAO;
import br.com.ecolivros.dominio.Cupom;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.FormaDePagamento;
import br.com.ecolivros.dominio.Pagamento;
import br.com.ecolivros.enuns.TipoPagamento;

public class ValidadorDadosObrigatoriosFormaDePagamento implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof FormaDePagamento){
			FormaDePagamento formaDePagamento = (FormaDePagamento) entidade;
			
			StringBuilder msg = new StringBuilder();
			
			Pagamento pagamento = formaDePagamento.getPagamento();
			double valor = formaDePagamento.getValor();
			
			if(pagamento == null && formaDePagamento.getTipoPagamento() != TipoPagamento.BOLETO)
				msg.append("FormaDePgto é de preenchimento obrigatório!\n");
			
			if (pagamento != null) {
				if (pagamento instanceof Cupom) {
					try {
						Cupom cupom = (Cupom) new CupomDAO().getCupomById(pagamento.getId());
						if ((cupom.getValorCupom() - cupom.getValorUtilizado()) < formaDePagamento.getValor()) {
							msg.append("Valor informado no cupom é maior do que resta.!\n");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			
			if(valor <= 0)
				msg.append("Valor é de preenchimento obrigatório!\\n");
			
			return msg.toString();
		} else {
			return "Deve ser registrado uma Forma de Pagamento!";
		}
	}
}