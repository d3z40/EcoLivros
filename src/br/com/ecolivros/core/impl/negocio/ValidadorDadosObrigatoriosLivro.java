package br.com.ecolivros.core.impl.negocio;

import java.util.List;

import br.com.ecolivros.core.IStrategy;
import br.com.ecolivros.dominio.Autor;
import br.com.ecolivros.dominio.Categoria;
import br.com.ecolivros.dominio.Editora;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.GrupoPrecificacao;
import br.com.ecolivros.dominio.Livro;

public class ValidadorDadosObrigatoriosLivro implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof Livro){
			Livro livro = (Livro) entidade;
			
//			int id = livro.getId();
			String codigo = livro.getCodigo();
			String titulo = livro.getTitulo();
//			String ano = livro.getAno();
//			String edicao = livro.getEdicao();
			String ISBN = livro.getISBN();
			int numPags = livro.getNumPags();
//			String sinopse = livro.getSinopse();
			double altura = livro.getAltura();
			double largura = livro.getLargura();
			double peso = livro.getPeso();
			double profundidade = livro.getProfundidade();
//			String codBarras = livro.getCodBarras();
			List<Autor> autores = livro.getAutores();
			List<Categoria> categorias = livro.getCategorias();
			Editora editora = livro.getEditora();
			GrupoPrecificacao grupoPrecificacao = livro.getGrupoPrecificacao();
//			boolean ativo = livro.isAtivo();
//			String motivo = livro.getMotivo();
			
			if(numPags <= 0 || altura <= 0 || largura <= 0 || peso <= 0 || profundidade <= 0 ||
					autores.isEmpty() || categorias.isEmpty() || editora == null || grupoPrecificacao == null)
				return "Nº Pags, Altura, Largura, Peso, Profundidade, Autor, Categória, Editora e Grupo Prec. são de preenchimento obrigatório!";
			
			if(codigo.trim().equals("") || titulo.trim().equals("") || ISBN.trim().equals(""))
				return "Código, Título e ISBN são de preenchimento obrigatório!";
		} else {
			return "Deve ser registrado um Livro!";
		}
		return null;
	}
}