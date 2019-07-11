package br.com.ecolivros.controle.web.vh.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ecolivros.controle.web.vh.IViewHelper;
import br.com.ecolivros.core.aplicacao.Resultado;
import br.com.ecolivros.core.util.UsuarioLogado;
import br.com.ecolivros.dominio.Carrinho;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.enuns.SituacaoEstoque;

public class CarrinhoViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String operacao = request.getParameter("operacao");
		Carrinho carrinho = null; 
		String idCarrinho;
		
		if(operacao.equals("SALVAR")) {
			String idLivro = request.getParameter("idLivro");
			String qtde = request.getParameter("txtQtde");
			if (qtde == null) {
				qtde = "1";
			}
			
			carrinho = new Carrinho();
			
			carrinho.setIdLivro(Integer.parseInt(idLivro));
			carrinho.setIdUsuario(UsuarioLogado.getUsuario().getId());
			carrinho.setQtde(Integer.parseInt(qtde));
			carrinho.setSituacaoEstoque(SituacaoEstoque.PENDENTE);
			
		} else if (operacao.equals("ALTERAR")) {
			String id = request.getParameter("IdCarrinho");
			String qtde = request.getParameter("Qtde");
			
			carrinho = new Carrinho();
			carrinho.setId(Integer.parseInt(id));
			carrinho.setQtde(Integer.parseInt(qtde));
			
		} else if(operacao.equals("EXCLUIR") || operacao.equals("CONSULTAR")) {
			idCarrinho = request.getParameter("IdCarrinho");
			int id = 0;
			
			if(idCarrinho != null && !idCarrinho.trim().equals("")) {
				id = Integer.parseInt(idCarrinho);
				
				carrinho = new Carrinho();
				carrinho.setId(id);
			}
		} else {
			Resultado resultado = (Resultado) request.getSession().getAttribute("resultado");
			idCarrinho = request.getParameter("IdCarrinho");
			int id = 0;
			
			if(idCarrinho != null && !idCarrinho.trim().equals("")) {
				id = Integer.parseInt(idCarrinho);
			}
			
			if (resultado != null) {
				for(EntidadeDominio e : resultado.getEntidades()) {
					if(e != null && e.getId() != null && id > 0 && e.getId() == id){
						carrinho = (Carrinho) e;
					}
				}
			}
		}
		return carrinho;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		RequestDispatcher d = null;
		
		String operacao = request.getParameter("operacao");
		
		if(resultado.getMsg() == null) {
			request.getSession().setAttribute("resultado", resultado);
			if (operacao.equals("SALVAR")) {
				d = request.getRequestDispatcher("index_cliente.jsp");
			} else if (operacao.equals("ALTERAR")) {
				d = request.getRequestDispatcher("carrinho.jsp");
			} else if (operacao.equals("VISUALIZAR")) {
				d = request.getRequestDispatcher("cadastra_pedido.jsp");
			} else if (operacao.equals("EXCLUIR")) {
				d = request.getRequestDispatcher("carrinho.jsp");				
			} else {
				d = request.getRequestDispatcher("carrinho.jsp");
			}
		} else {
			request.setAttribute("msg", resultado.getMsg());
			
			d = request.getRequestDispatcher("msg-erro.jsp");
		}
				
		if (d != null)
			d.forward(request, response);
	}
}