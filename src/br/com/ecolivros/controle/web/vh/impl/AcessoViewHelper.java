package br.com.ecolivros.controle.web.vh.impl;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ecolivros.controle.web.vh.IViewHelper;
import br.com.ecolivros.core.aplicacao.Resultado;
import br.com.ecolivros.core.impl.dao.ControleDeAcessoDAO;
import br.com.ecolivros.dominio.Acesso;
import br.com.ecolivros.dominio.EntidadeDominio;

public class AcessoViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String id = request.getParameter("txtIdAcesso");
		String descricao = request.getParameter("txtDescricao");
		
		Acesso acesso = new Acesso();
		acesso.setDescricao(descricao);
		
		if(id != null && !id.trim().equals("")){
			acesso.setId(Integer.parseInt(id));
			try {
				acesso.setControlesAcesso(new ControleDeAcessoDAO().getEntidadeDominioAcesso(Integer.parseInt(id)));
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		}

		return acesso;
	}
	
	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (resultado.getMsg() != null) {
			request.setAttribute("msg", resultado.getMsg());
			
			request.getRequestDispatcher("msg-erro.jsp").forward(request, response);
		} else  {
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}