package br.com.ecolivros.controle.web.vh.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ecolivros.controle.web.vh.IViewHelper;
import br.com.ecolivros.core.aplicacao.Resultado;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.GrupoPrecificacao;

public class GrupoPrecificacaoViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String sMargem = request.getParameter("txtMargem");
		String id = request.getParameter("txtGrupoPrecificacao");

		GrupoPrecificacao precificacao = new GrupoPrecificacao();
		precificacao.setMargem(Double.parseDouble(sMargem));

		if(id != null && !id.trim().equals("")){
			precificacao.setId(Integer.parseInt(id));
		}
		
		return precificacao;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
	}
}
