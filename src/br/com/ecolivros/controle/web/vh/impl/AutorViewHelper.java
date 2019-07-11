package br.com.ecolivros.controle.web.vh.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ecolivros.controle.web.vh.IViewHelper;
import br.com.ecolivros.core.aplicacao.Resultado;
import br.com.ecolivros.dominio.Autor;
import br.com.ecolivros.dominio.EntidadeDominio;

public class AutorViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String sNomeArtistico = request.getParameter("txtNomeArtistico");
		String sNomeCompleto = request.getParameter("txtNomeCompleto");
		String id = request.getParameter("txtIdAutor");

		Autor autor = new Autor();
		autor.setNomeArtistico(sNomeArtistico);
		autor.setNomeCompleto(sNomeCompleto);

		if(id != null && !id.trim().equals("")){
			autor.setId(Integer.parseInt(id));
		}
		
		return autor;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
	}
}
