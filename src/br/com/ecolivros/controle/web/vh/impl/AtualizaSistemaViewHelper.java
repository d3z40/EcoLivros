package br.com.ecolivros.controle.web.vh.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ecolivros.controle.web.vh.IViewHelper;
import br.com.ecolivros.core.aplicacao.Resultado;
import br.com.ecolivros.dominio.AtualizaSistema;
import br.com.ecolivros.dominio.EntidadeDominio;

public class AtualizaSistemaViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		return new AtualizaSistema();
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getRequestDispatcher("AtualizaSistema.jsp").forward(request, response);
	}
}