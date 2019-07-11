package br.com.ecolivros.core.util;

import java.util.ArrayList;
import java.util.List;

import br.com.ecolivros.dominio.ItemPedido;

public class ClonaList {
	
	public static List<ItemPedido> clonaListItemPedido(List<ItemPedido> itensPedido) {
		List<ItemPedido> listCopy = new ArrayList<ItemPedido>();
		ItemPedido itemPedidoCopy;
		
		for (ItemPedido itemPedido : itensPedido) {
			itemPedidoCopy = new ItemPedido();
			itemPedidoCopy.setId(itemPedido.getId());
			itemPedidoCopy.setIdEstoque(itemPedido.getIdEstoque());
			itemPedidoCopy.setIdLivro(itemPedido.getIdLivro());
			itemPedidoCopy.setIdPedido(itemPedido.getIdPedido());
			itemPedidoCopy.setQtde(itemPedido.getQtde());
			itemPedidoCopy.setSituacaoItem(itemPedido.getSituacaoItem());
			itemPedidoCopy.setValor(itemPedido.getValor());
			itemPedidoCopy.setDtCadastro(itemPedido.getDtCadastro());
			
			listCopy.add(itemPedidoCopy);
		}
		
		return listCopy;
	}
}