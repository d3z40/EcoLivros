package br.com.ecolivros.core.impl.negocio;

import java.sql.SQLException;

import br.com.ecolivros.core.IStrategy;
import br.com.ecolivros.core.impl.dao.CarrinhoDAO;
import br.com.ecolivros.core.impl.dao.EstoqueDAO;
import br.com.ecolivros.dominio.Carrinho;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.enuns.SituacaoEstoque;

public class ValidadorDadosObrigatoriosAlterarCarrinho implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof Carrinho){
			Carrinho carrinho = (Carrinho) entidade;
			
			try {
				Carrinho carroTemp = new CarrinhoDAO().getCarrinhoById(carrinho.getId());
				int qtdeCarrinho = carrinho.getQtde();
				
				int qtdeEstoque = new EstoqueDAO().getQtdeEstoqueByIdLivroSituacaoEstoque(carroTemp.getIdLivro(), SituacaoEstoque.PENDENTE);
				if (qtdeCarrinho > qtdeEstoque)	
					return "Qtde informada é maior do que qtde em estoque.";
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			return "Deve ser registrado um carrinho!";
		}
		return null;
	}
}