package br.com.ecolivros.dominio;

public class LivroAutor extends EntidadeDominio {
	private Livro livro;
	private Autor autor;
	
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
	 * @return the autor
	 */
	public Autor getAutor() {
		return autor;
	}
	
	/**
	 * @param autor the autor to set
	 */
	public void setAutor(Autor autor) {
		this.autor = autor;
	}
}