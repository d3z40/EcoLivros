package br.com.ecolivros.controle.web.vh.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ecolivros.controle.web.vh.IViewHelper;
import br.com.ecolivros.core.aplicacao.Resultado;
import br.com.ecolivros.dominio.Categoria;
import br.com.ecolivros.dominio.EntidadeDominio;

public class CategoriaViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String sDescricao = request.getParameter("txtDescricao");
		String id = request.getParameter("txtIdCategoria");

		Categoria categoria = new Categoria();
		categoria.setDescricao(sDescricao);

		if(id != null && !id.trim().equals("")){
			categoria.setId(Integer.parseInt(id));
		}

		return categoria;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
	}
}
