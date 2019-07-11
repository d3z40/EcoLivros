package br.com.ecolivros.controle.web.vh.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ecolivros.controle.web.vh.IViewHelper;
import br.com.ecolivros.core.aplicacao.Resultado;
import br.com.ecolivros.core.impl.dao.TrocaDAO;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Troca;

public class TrocaViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String operacao = request.getParameter("operacao");
		Troca trocaPai = null;
		List<Troca> trocas = null;
		
		if(operacao.equals("SALVAR")) {
			trocas = new ArrayList<Troca>();
			trocaPai = new Troca();
			Troca troca;
			
			int qtdeTroca = Integer.parseInt(request.getParameter("txtQtdeTroca"));
			
			for (int i = 0; i < qtdeTroca; i++) {
				String idItemPedido = request.getParameter("idItemPedido^" + i);
				String aprovacao = request.getParameter("radAprovacao^" + i);
				
				if (idItemPedido != null && aprovacao != null && !idItemPedido.trim().equals("") && !aprovacao.trim().equals("")) {
					troca = new Troca();
					troca.setIdItemPedido(Integer.parseInt(idItemPedido));
					troca.setAprovado(Byte.parseByte(aprovacao));
					
					trocas.add(troca);
				}
			}
		}
		
		if (trocas != null && trocaPai != null && !trocas.isEmpty())
			trocaPai.setTrocas(trocas);
		
		return trocaPai;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		RequestDispatcher d = null;
		
		String operacao = request.getParameter("operacao");
		
		if(resultado.getMsg() == null) {
			request.getSession().setAttribute("resultado", resultado);
			
			if (operacao.equals("VISUALIZAR")) {
				try {
					String filter = request.getParameter("search");
					List<EntidadeDominio> trocas = new TrocaDAO().getLista(filter);
					request.setAttribute("trocas", trocas);
				} catch (SQLException e) {
					resultado.setMsg("Não foi possível listar cupons.");
				}
				
				d = request.getRequestDispatcher("consulta_trocas_cliente.jsp");
			} else {
				request.getRequestDispatcher("consulta_trocas.jsp").forward(request, response);
			}
		} else {
			request.setAttribute("msg", resultado.getMsg());
			
			d = request.getRequestDispatcher("msg-erro.jsp");
		}
			
		if (d != null)
			d.forward(request, response);
	}
}