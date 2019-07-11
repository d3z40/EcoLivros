package br.com.ecolivros.controle.web.vh.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ecolivros.controle.web.vh.IViewHelper;
import br.com.ecolivros.core.aplicacao.Resultado;
import br.com.ecolivros.core.impl.dao.ItemPedidoDAO;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.ItemPedido;

public class ItemPedidoViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String operacao = request.getParameter("operacao");
		ItemPedido itemPedido = null;
		String idItemPedido;
		
		if(operacao.equals("SALVAR")) {
			
		} else if(operacao.equals("CONSULTAR") || operacao.equals("ALTERAR")) {
			idItemPedido = request.getParameter("IdItemPedido");
			int id = 0;
			
			if(idItemPedido != null && !idItemPedido.trim().equals("")) {
				id = Integer.parseInt(idItemPedido);
				
				try {
					itemPedido = new ItemPedidoDAO().getItemPedidoById(id);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			Resultado resultado = (Resultado) request.getSession().getAttribute("resultado");
			idItemPedido = request.getParameter("IdItemPedido");
			int id = 0;
			
			if(idItemPedido != null && !idItemPedido.trim().equals("")) {
				id = Integer.parseInt(idItemPedido);
			}
			
			if (resultado != null) {
				for(EntidadeDominio e : resultado.getEntidades()) {
					if(e != null && e.getId() != null && id > 0 && e.getId() == id) {
						itemPedido = (ItemPedido) e;
					}
				}
			}
		}
		
		return itemPedido;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		RequestDispatcher d = null;
		
		String operacao = request.getParameter("operacao");
		
		if(resultado.getMsg() == null) {
			request.getSession().setAttribute("resultado", resultado);
			
			if (operacao.equals("SALVAR")) {
				d = request.getRequestDispatcher("perfil.jsp");
			} else if (operacao.equals("VISUALIZAR")) {
				
			} else if (operacao.equals("CONSULTAR") || operacao.equals("ALTERAR")) {
				try {
					int IdItemPedido = Integer.parseInt(request.getParameter("IdItemPedido"));
					List<ItemPedido> itensPedido = new ItemPedidoDAO().getItensById(IdItemPedido);
					request.setAttribute("itensPedidos", itensPedido);
				} catch (SQLException e) {
					resultado.setMsg("Não foi possível listar ItemPedido.");
				}
				
				d = request.getRequestDispatcher("detalha_itempedido.jsp");
			}
		} else {
			request.setAttribute("msg", resultado.getMsg());
			
			d = request.getRequestDispatcher("msg-erro.jsp");
		}
				
		if (d != null)
			d.forward(request, response);
	}
}