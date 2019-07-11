package br.com.ecolivros.controle.web.vh.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ecolivros.controle.web.vh.IViewHelper;
import br.com.ecolivros.core.aplicacao.Resultado;
import br.com.ecolivros.core.impl.dao.AutorDAO;
import br.com.ecolivros.core.impl.dao.CategoriaDAO;
import br.com.ecolivros.core.impl.dao.LivroDAO;
import br.com.ecolivros.dominio.Autor;
import br.com.ecolivros.dominio.Categoria;
import br.com.ecolivros.dominio.Editora;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.GrupoPrecificacao;
import br.com.ecolivros.dominio.Livro;

public class LivroViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String operacao = request.getParameter("operacao");
		Livro livro = null; 
		String idLivro;
		
		if(operacao.equals("SALVAR") || operacao.equals("ALTERAR")) {
			idLivro = request.getParameter("txtIdLivro");
			String codigo = request.getParameter("txtCodigo");
			String titulo = request.getParameter("txtTitulo");
			String ano = request.getParameter("txtAno");
			String edicao = request.getParameter("txtEdicao");
			String ISBN = request.getParameter("txtISBN");
			String numPags = request.getParameter("txtNumPags");
			String sinopse = request.getParameter("txtSinopse");
			String altura = request.getParameter("txtAltura");
			String largura = request.getParameter("txtLargura");
			String peso = request.getParameter("txtPeso");
			String profundidade = request.getParameter("txtProfundidade");
			String codBarras = request.getParameter("txtCodBarras");
			String idEditora = request.getParameter("txtEditora");
			String idGrupoPrecificacao = request.getParameter("txtGrupoPrecificacao");
			String ativo = request.getParameter("txtAtivo");
			String motivo = request.getParameter("txtMotivo");
			
			livro = new Livro();
			livro.setCodigo(codigo);
			livro.setTitulo(titulo);
			livro.setAno(ano);
			livro.setEdicao(edicao);
			livro.setISBN(ISBN);
			livro.setNumPags((numPags != null && !numPags.equals("")) ? Integer.parseInt(numPags) : 0);
			livro.setSinopse(sinopse);
			livro.setAltura((altura != null && !altura.equals("")) ? Double.parseDouble(altura) : 0);
			livro.setLargura((largura != null && !largura.equals("")) ? Double.parseDouble(largura) : 0);
			livro.setPeso((peso != null && !peso.equals("")) ? Double.parseDouble(peso) : 0);
			livro.setProfundidade((profundidade != null && !profundidade.equals("")) ? Double.parseDouble(profundidade) : 0);
			livro.setCodBarras(codBarras);
			
			Editora editora = new Editora();
			editora.setId(Integer.parseInt(idEditora));
			livro.setEditora(editora);
			
			GrupoPrecificacao grupoPrecificacao = new GrupoPrecificacao();
			grupoPrecificacao.setId(Integer.parseInt(idGrupoPrecificacao));
			livro.setGrupoPrecificacao(grupoPrecificacao);
			
			livro.setAtivo(ativo.equals("1") ? true : false);
			livro.setMotivo(motivo);
			livro.setDtCadastro(new Date());
			
			try {
				List<EntidadeDominio> autores = new AutorDAO().getLista();
				for (EntidadeDominio ed : autores) {
					Autor autor = (Autor) ed;
					
					String value = request.getParameter("chkAutor" + autor.getId());
					
					if (value != null && value.equals("on"))
						livro.addAutor(autor);
				}
				
				List<EntidadeDominio> categorias = new CategoriaDAO().getLista();
				for (EntidadeDominio ed : categorias) {
					Categoria categoria = (Categoria) ed;
					
					String value = request.getParameter("chkCategoria" + categoria.getId());
					
					if (value != null && value.equals("on"))
						livro.addCategoria(categoria);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if (idLivro != null && !idLivro.equals(""))
				livro.setId(Integer.parseInt(idLivro));
		} else if(operacao.equals("EXCLUIR") || operacao.equals("CONSULTAR")) {
			idLivro = request.getParameter("IdLivro");
			int id = 0;
			
			if(idLivro != null && !idLivro.trim().equals("")) {
				id = Integer.parseInt(idLivro);
				
				livro = new Livro();
				livro.setId(id);
			}
		} else {
			Resultado resultado = (Resultado) request.getSession().getAttribute("resultado");
			idLivro = request.getParameter("txtIdLivro");
			int id = 0;
			
			if(idLivro != null && !idLivro.trim().equals("")) {
				id = Integer.parseInt(idLivro);
			}
			
			if (resultado != null) {
				for(EntidadeDominio e: resultado.getEntidades()) {
					if(e != null && e.getId() != null && id > 0 && e.getId() == id){
						livro = (Livro) e;
					}
				}
			}
		}
		
		return livro;
	}
	
	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		RequestDispatcher d = null;
		
		String operacao = request.getParameter("operacao");
		
		if(resultado.getMsg() == null) {
			request.getSession().setAttribute("resultado", resultado);
			
			if (operacao.equals("SALVAR")) {
				resultado.setMsg("Livro cadastrado com sucesso!");
				
				d = request.getRequestDispatcher("cadastra_livros.jsp");
				
			} else if (operacao.equals("VISUALIZAR")) {
				try {
					String filter = request.getParameter("search");
					List<EntidadeDominio> livros = new LivroDAO().getLista(filter);
					request.setAttribute("livros", livros);
				} catch (SQLException e) {
					resultado.setMsg("Não foi possível listar livros.");;
				}
				
				d = request.getRequestDispatcher("consulta_livros.jsp");
				
			} else if (operacao.equals("CONSULTAR")) {
				try {
					int IdLivro = Integer.parseInt(request.getParameter("IdLivro"));
					Livro livro = (Livro) new LivroDAO().getEntidadeDominio(IdLivro);
					request.setAttribute("livro", livro);
				} catch (SQLException e) {
					resultado.setMsg("Não foi possível listar livros.");;
				}
				
				d = request.getRequestDispatcher("cadastra_livros.jsp");
				
			}  else if (operacao.equals("ALTERAR")) {
				d = request.getRequestDispatcher("cadastra_livros.jsp");
				
			} else if (operacao.equals("EXCLUIR")) {
				d = request.getRequestDispatcher("consulta_livros.jsp");				
			}
		} else {
			request.setAttribute("msg", resultado.getMsg());
			
			d = request.getRequestDispatcher("msg-erro.jsp");
		}
		
		if (d != null)
			d.forward(request,response);
	}
}