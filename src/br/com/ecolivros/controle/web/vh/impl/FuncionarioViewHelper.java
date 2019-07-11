package br.com.ecolivros.controle.web.vh.impl;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ecolivros.controle.web.vh.IViewHelper;
import br.com.ecolivros.core.aplicacao.Resultado;
import br.com.ecolivros.core.impl.dao.AcessoDAO;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Funcionario;

public class FuncionarioViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String id = request.getParameter("txtIdFuncionario");
		String idUsuario = request.getParameter("txtIdUsuario");
		String idAcesso = request.getParameter("txtIdAcesso");
		String numRegistro = request.getParameter("txtNumRegistro");
		
		Funcionario funcionario = new Funcionario();
		funcionario.setIdUsuario(Integer.parseInt(idUsuario));
		try {
			funcionario.setAcesso(new AcessoDAO().getEntidadeDominio(Integer.parseInt(idAcesso)));
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
		funcionario.setNumRegistro(numRegistro);;
		
		if(id != null && !id.trim().equals("")){
			funcionario.setId(Integer.parseInt(id));
		}
		return funcionario;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (resultado.getMsg() != null) {
			request.setAttribute("msg", resultado.getMsg());
			
			request.getRequestDispatcher("msg-erro.jsp").forward(request, response);
		} else  {
			request.getRequestDispatcher("login.html").forward(request, response);
		}
	}
}