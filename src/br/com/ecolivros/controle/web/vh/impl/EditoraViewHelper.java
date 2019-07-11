package br.com.ecolivros.controle.web.vh.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ecolivros.controle.web.vh.IViewHelper;
import br.com.ecolivros.core.aplicacao.Resultado;
import br.com.ecolivros.dominio.Editora;
import br.com.ecolivros.dominio.EntidadeDominio;

public class EditoraViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String sRazao = request.getParameter("txtRazao");
		String id = request.getParameter("txtIdEditora");

		Editora editora = new Editora();
		editora.setRazao(sRazao);

		if(id != null && !id.trim().equals("")){
			editora.setId(Integer.parseInt(id));
		}

		return editora;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	}
}