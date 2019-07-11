package br.com.ecolivros.controle.web.vh.impl;

import java.io.IOException;
import java.util.Date;

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

public class UsuarioViewHelper implements IViewHelper {

	/**
	 * Descrição do Método
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @see br.com.ecolivros.controle.web.vh.IViewHelper#getEntidade(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String operacao = request.getParameter("operacao");
		Usuario usuario = null;
		String idUsuario;

		if (operacao.equals("SALVAR") || operacao.equals("ALTERAR")) {
			String id = request.getParameter("txtIdUsuario");
			String nome = request.getParameter("txtNome");
			String email = request.getParameter("txtEmail");
			String senha = request.getParameter("txtSenha");
			String cpf = request.getParameter("txtCPF");
			String senhaRepetida = request.getParameter("txtSenhaRepetida");
			String chkRegister = request.getParameter("chkRegister");
			
			usuario = new Usuario();
			usuario.setNome(nome);
			usuario.setEmail(email);
			usuario.setSenha(senha);
			usuario.setSenhaRepetida(senhaRepetida);
			usuario.setCpf(cpf);
			usuario.setTipoUsuario(TipoUsuario.CLIENTE);
			usuario.setChkRegister(chkRegister);
			usuario.setDtCadastro(new Date());

			if (id != null && !id.trim().equals("")) {
				usuario.setId(Integer.parseInt(id));
			}
		} else if (operacao.equals("EXCLUIR") || operacao.equals("CONSULTAR")) {
			idUsuario = request.getParameter("IdUsuario");
			int id = 0;

			if (idUsuario != null && !idUsuario.trim().equals("")) {
				id = Integer.parseInt(idUsuario);

				usuario = new Usuario();
				usuario.setId(id);
			}
		} else {
			Resultado resultado = (Resultado) request.getSession().getAttribute("resultado");
			idUsuario = request.getParameter("txtIdUsuario");
			int id = 0;

			if (idUsuario != null && !idUsuario.trim().equals("")) {
				id = Integer.parseInt(idUsuario);
			}

			if (resultado != null) {
				for (EntidadeDominio e : resultado.getEntidades()) {
					if (e != null && e.getId() != null && id > 0 && e.getId() == id) {
						usuario = (Usuario) e;
					}
				}
			}
		}

		return usuario;
	}

	/**
	 * Descrição do Método
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @see br.com.ecolivros.controle.web.vh.IViewHelper#setView(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		RequestDispatcher d = null;
		
		if (resultado.getMsg() != null) {
			request.setAttribute("msg", resultado.getMsg());

			d = request.getRequestDispatcher("msg-erro.jsp");
		} else {
			String operacao = request.getParameter("operacao");
			
			if (operacao.equals("ALTERAR")) {
				int ultimaEntidade = resultado.getEntidades().size() - 1;
//				request.setAttribute("usuario", resultado.getEntidades().get(ultimaEntidade));
				UsuarioLogado.setUsuario((Usuario) resultado.getEntidades().get(ultimaEntidade));
				
				d = request.getRequestDispatcher("perfil.jsp");
			} else if (operacao.equals("SALVAR")) {
				d = request.getRequestDispatcher("login.jsp");
			}
		}
		
		if (d != null)
			d.forward(request, response);
	}
}