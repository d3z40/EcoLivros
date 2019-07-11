package br.com.ecolivros.dominio;

public class LivroCategoria extends EntidadeDominio {
	private Livro livro;
	private Categoria categoria;
	
	/**
	 * @return the livro
	 */
	public Livro getLivro() {
		return livro;
	}
	
	/**
	 * @param livro the livro to set
	 */
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	
	/**
	 * @return the categoria
	 */
	public Categoria getCategoria() {
		return categoria;
	}
	
	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}