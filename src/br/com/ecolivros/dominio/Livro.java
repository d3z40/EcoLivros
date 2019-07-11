package br.com.ecolivros.dominio;

import java.util.ArrayList;
import java.util.List;

public class Livro extends EntidadeDominio {
	private String codigo;
	private String titulo;
	private String ano;
	private String edicao;
	private String ISBN;
	private int numPags;
	private String sinopse;
	private double altura;
	private double largura;
	private double peso;
	private double profundidade;
	private String codBarras;
	private List<Autor> autores;
	private List<Categoria> categorias;
	private Editora editora;
	private GrupoPrecificacao grupoPrecificacao;
	private boolean ativo;
	private String motivo;
	
	/**
	 * 
	 */
	public Livro() {
		autores = new ArrayList<Autor>();
		categorias = new ArrayList<Categoria>();
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	
	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	/**
	 * @return the ano
	 */
	public String getAno() {
		return ano;
	}
	
	/**
	 * @param ano the ano to set
	 */
	public void setAno(String ano) {
		this.ano = ano;
	}
	
	/**
	 * @return the edicao
	 */
	public String getEdicao() {
		return edicao;
	}
	
	/**
	 * @param edicao the edicao to set
	 */
	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}
	
	/**
	 * @return the iSBN
	 */
	public String getISBN() {
		return ISBN;
	}
	
	/**
	 * @param iSBN the iSBN to set
	 */
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	
	/**
	 * @return the numPags
	 */
	public int getNumPags() {
		return numPags;
	}
	
	/**
	 * @param numPags the numPags to set
	 */
	public void setNumPags(int numPags) {
		this.numPags = numPags;
	}
	
	/**
	 * @return the sinopse
	 */
	public String getSinopse() {
		return sinopse;
	}
	
	/**
	 * @param sinopse the sinopse to set
	 */
	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}
	
	/**
	 * @return the altura
	 */
	public double getAltura() {
		return altura;
	}
	
	/**
	 * @param altura the altura to set
	 */
	public void setAltura(double altura) {
		this.altura = altura;
	}
	
	/**
	 * @return the largura
	 */
	public double getLargura() {
		return largura;
	}
	
	/**
	 * @param largura the largura to set
	 */
	public void setLargura(double largura) {
		this.largura = largura;
	}
	
	/**
	 * @return the peso
	 */
	public double getPeso() {
		return peso;
	}
	
	/**
	 * @param peso the peso to set
	 */
	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	/**
	 * @return the profundidade
	 */
	public double getProfundidade() {
		return profundidade;
	}
	
	/**
	 * @param profundidade the profundidade to set
	 */
	public void setProfundidade(double profundidade) {
		this.profundidade = profundidade;
	}
	
	/**
	 * @return the codBarras
	 */
	public String getCodBarras() {
		return codBarras;
	}
	
	/**
	 * @param codBarras the codBarras to set
	 */
	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}
	
	/**
	 * @return the autores
	 */
	public List<Autor> getAutores() {
		return autores;
	}

	/**
	 * @param autores the autores to set
	 */
	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	/**
	 * @return the categorias
	 */
	public List<Categoria> getCategorias() {
		return categorias;
	}
	
	/**
	 * @param categorias the categorias to set
	 */
	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	
	/**
	 * @return the editora
	 */
	public Editora getEditora() {
		return editora;
	}
	
	/**
	 * @param editora the editora to set
	 */
	public void setEditora(Editora editora) {
		this.editora = editora;
	}
	
	/**
	 * @return the grupoPrecificacao
	 */
	public GrupoPrecificacao getGrupoPrecificacao() {
		return grupoPrecificacao;
	}
	
	/**
	 * @param grupoPrecificacao the grupoPrecificacao to set
	 */
	public void setGrupoPrecificacao(GrupoPrecificacao grupoPrecificacao) {
		this.grupoPrecificacao = grupoPrecificacao;
	}
	
	/**
	 * @return the ativo
	 */
	public boolean isAtivo() {
		return ativo;
	}
	
	/**
	 * @param ativo the ativo to set
	 */
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	/**
	 * @return the motivo
	 */
	public String getMotivo() {
		return motivo;
	}
	
	/**
	 * @param motivo the motivo to set
	 */
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	public void addAutor(Autor autor) {
		autores.add(autor);
	}
	
	public void addCategoria(Categoria categoria) {
		categorias.add(categoria);
	}
}