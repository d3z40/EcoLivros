package br.com.ecolivros.controle.web.vh.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ecolivros.controle.web.vh.IViewHelper;
import br.com.ecolivros.core.aplicacao.Resultado;
import br.com.ecolivros.core.util.UsuarioLogado;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Usuario;
import br.com.ecolivros.enuns.TipoUsuario;

public class LoginViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String email = request.getParameter("txtEmail");
		String senha = request.getParameter("txtSenha");
		
		Usuario u = new Usuario();
		u.setEmail(email);
		u.setSenha(senha);
		
		return u;
	}
	
	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		RequestDispatcher d = null;
		
		if (resultado.getMsg() != null) {
			request.setAttribute("msg", resultado.getMsg());
			
			d = request.getRequestDispatcher("msg-erro.jsp");
		} else if (resultado.getEntidades().isEmpty()) {
			request.setAttribute("msg", "Usuário e/ou Senha incorretos.");
			
			d = request.getRequestDispatcher("msg-erro.jsp");
		} else {
			for (EntidadeDominio ed : resultado.getEntidades()) {
				if (ed instanceof Usuario) {
					Usuario usuario = (Usuario) ed;
					
					if (usuario.getEmail().equals(request.getParameter("txtEmail")) && 
							usuario.getSenha().equals(request.getParameter("txtSenha"))) {
						
						UsuarioLogado.setUsuario(usuario);
						
						if (usuario.getTipoUsuario() == TipoUsuario.CLIENTE) {
							//request.setAttribute("usuario", usuario);
							
							d = request.getRequestDispatcher("index_cliente.jsp");
						} else if (usuario.getTipoUsuario() == TipoUsuario.FUNCIONARIO) {
							d = request.getRequestDispatcher("dashboard.jsp");
						}
					}
				}
			}
		}
		
		d.forward(request, response);
	}
}