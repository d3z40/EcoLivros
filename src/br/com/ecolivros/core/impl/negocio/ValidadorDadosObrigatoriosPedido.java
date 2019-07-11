package br.com.ecolivros.core.impl.negocio;

import java.util.List;

import br.com.ecolivros.core.IStrategy;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.FormaDePagamento;
import br.com.ecolivros.dominio.ItemPedido;
import br.com.ecolivros.dominio.Pedido;
import br.com.ecolivros.enuns.Frete;

public class ValidadorDadosObrigatoriosPedido implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof Pedido){
			Pedido pedido = (Pedido) entidade;
			
			StringBuilder msg = new StringBuilder();
			int i = 0;
			double totalFormaPgto = 0;
			double totalItens = 0;
			
			String numPedido = pedido.getNumPedido();
			Frete frete = pedido.getFrete();
			List<FormaDePagamento> formasDePgto = pedido.getFormasDePgto();
			List<ItemPedido> itensPedido = pedido.getItensPedido();
			
			if(numPedido != null && numPedido.trim().equals(""))
				msg.append("Número Pedido é de preenchimento obrigatório!\n");
			
			if(frete == null)
				msg.append("Frete é de preenchimento obrigatório!\n");
			
			ComplementarDtCadastro cDtCadastro = new ComplementarDtCadastro();
			
			if (formasDePgto.size() > 0) {
				String msgTemp;
				ValidadorDadosObrigatoriosFormaDePagamento strategyFormaDePagamento = new ValidadorDadosObrigatoriosFormaDePagamento();
				
				for (FormaDePagamento formaDePgto : formasDePgto) {
					i++;
					msgTemp = strategyFormaDePagamento.processar(formaDePgto);
					
					if (msgTemp != null && !msgTemp.trim().equals(""))
						msg.append("Forma De Pgto " + i + ": " + msgTemp + "\n");
					
					msgTemp = cDtCadastro.processar(formaDePgto);
					
					if (msgTemp != null && !msgTemp.trim().equals(""))
						msg.append("Forma De Pgto " + i + ": " + msgTemp + "\n");
					
					totalFormaPgto += formaDePgto.getValor();
				}
			}
			
			if (itensPedido.size() > 0) {
				String msgTemp;
				ValidadorDadosObrigatoriosItemPedido strategyItemPedido = new ValidadorDadosObrigatoriosItemPedido();
				
				for (ItemPedido itemPedido : itensPedido) {
					i++;
					msgTemp = strategyItemPedido.processar(itemPedido);
					
					if (msgTemp != null && !msgTemp.trim().equals(""))
						msg.append("Item " + i + ": " + msgTemp + "\n");
					
					msgTemp = cDtCadastro.processar(itemPedido);
					
					if (msgTemp != null && !msgTemp.trim().equals(""))
						msg.append("Item " + i + ": " + msgTemp + "\n");
					
					totalItens += itemPedido.getValor();
				}
			}
			
			totalItens += frete.getValor();
			if (totalItens != totalFormaPgto) {
				msg.append("A soma dos valores dos itens e frete estão diferentes da soma das formas de pgto.\n");
			}
			
			return msg.toString();
		} else {
			return "Deve ser registrado um pedido!";
		}
	}
}