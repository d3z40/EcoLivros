package br.com.ecolivros.core.impl.negocio;

import java.sql.SQLException;

import br.com.ecolivros.core.IStrategy;
import br.com.ecolivros.core.impl.dao.CarrinhoDAO;
import br.com.ecolivros.core.impl.dao.EstoqueDAO;
import br.com.ecolivros.dominio.Carrinho;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.enuns.SituacaoEstoque;

public class ValidadorDadosObrigatoriosCarrinho implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof Carrinho){
			Carrinho carrinho = (Carrinho) entidade;
			
			int idUsuario = carrinho.getIdUsuario();
			int idLivro = carrinho.getIdLivro();
			
			if(idUsuario <= 0 || idLivro <= 0)
				return "Erro grave, Usuário ou Livro não encontrado, avise o Desenvolvedor.";
			
			try {
				if (new CarrinhoDAO().jaExisteLivroCarrinho(idLivro))
					return "Este livro já está no carrinho.";
				
				int qtdeEstoque = new EstoqueDAO().getQtdeEstoqueByIdLivroSituacaoEstoque(idLivro, SituacaoEstoque.PENDENTE);
				if (qtdeEstoque <= 0)	
					return "Chegou tarde, esses livro já acabou no estoque.";
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			return "Deve ser registrado um carrinho!";
		}
		return null;
	}
}