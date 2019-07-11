package br.com.ecolivros.core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ecolivros.dominio.Autor;
import br.com.ecolivros.dominio.Categoria;
import br.com.ecolivros.dominio.Editora;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.GrupoPrecificacao;
import br.com.ecolivros.dominio.Livro;
import br.com.ecolivros.dominio.LivroAutor;
import br.com.ecolivros.dominio.LivroCategoria;

public class LivroDAO extends AbstractJdbcDAO {

	public LivroDAO() {
		super("livro", "id");
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		PreparedStatement pst = null;
		Livro livro = (Livro) entidade;
		int idLivro = 0;
		
		try {
			connection.setAutoCommit(false);

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO livro (sCodigo, sTitulo, sAno, sEdicao, sISBN, nNumPaginas, " +
					"sSinopse, nAltura, nLargura, nPeso, nProfundidade, sCodBarras, iEditora, " +
					"iGrupoPrecificacao, iAtivo, sMotivo, dtCadastro) ");
			sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			pst = connection.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);

			pst.setString(1, livro.getCodigo());
			pst.setString(2, livro.getTitulo());
			pst.setString(3, livro.getAno());
			pst.setString(4, livro.getEdicao());
			pst.setString(5, livro.getISBN());
			pst.setInt(6, livro.getNumPags());
			pst.setString(7, livro.getSinopse());
			pst.setDouble(8, livro.getAltura());
			pst.setDouble(9, livro.getLargura());
			pst.setDouble(10, livro.getPeso());
			pst.setDouble(11, livro.getProfundidade());
			pst.setString(12, livro.getCodBarras());
			pst.setInt(13, livro.getEditora().getId());
			pst.setInt(14, livro.getGrupoPrecificacao().getId());
			pst.setInt(15, (livro.isAtivo() ? 1 : 0));
			pst.setString(16, livro.getMotivo());

			Timestamp time = new Timestamp(livro.getDtCadastro().getTime());
			pst.setTimestamp(17, time);

			pst.executeUpdate();
			
			ResultSet rs = pst.getGeneratedKeys();
			boolean insertedBook = rs.next();
			if (insertedBook) {
				idLivro = rs.getInt(1);
				livro.setId(idLivro);
			}
			
			if (insertedBook && livro.getCategorias().size() > 0) {
				for (Categoria categoria : livro.getCategorias()) {
					LivroCategoria livroCategoria = new LivroCategoria();
					livroCategoria.setLivro(livro);
					livroCategoria.setCategoria(categoria);;
					livroCategoria.setDtCadastro(new Date());
					
					new LivroCategoriaDAO(connection).salvar(livroCategoria);
				}
			}
			
			if (insertedBook && livro.getAutores().size() > 0) {
				for (Autor autor : livro.getAutores()) {
					LivroAutor livroAutor = new LivroAutor();
					livroAutor.setLivro(livro);
					livroAutor.setAutor(autor);
					livroAutor.setDtCadastro(new Date());
					
					new LivroAutorDAO(connection).salvar(livroAutor);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		StringBuilder sql;
		PreparedStatement pst = null;
		Livro livro = (Livro) entidade;

		try {
			connection.setAutoCommit(false);
			
			new LivroCategoriaDAO().excluir(livro);
			new LivroAutorDAO().excluir(livro);
			
			sql = new StringBuilder();
			sql.append("UPDATE livro SET sCodigo = ?, sTitulo = ?, sAno = ?, sEdicao = ?, sISBN = ?, nNumPaginas = ?, " +
					"sSinopse = ?, nAltura = ?, nLargura = ?, nPeso = ?, nProfundidade = ?, sCodBarras = ?, iEditora = ?, " +
					"iGrupoPrecificacao = ?, iAtivo = ?, sMotivo = ?, dtCadastro = ? WHERE id = ?");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setString(1, livro.getCodigo());
			pst.setString(2, livro.getTitulo());
			pst.setString(3, livro.getAno());
			pst.setString(4, livro.getEdicao());
			pst.setString(5, livro.getISBN());
			pst.setInt(6, livro.getNumPags());
			pst.setString(7, livro.getSinopse());
			pst.setDouble(8, livro.getAltura());
			pst.setDouble(9, livro.getLargura());
			pst.setDouble(10, livro.getPeso());
			pst.setDouble(11, livro.getProfundidade());
			pst.setString(12, livro.getCodBarras());
			pst.setInt(13, livro.getEditora().getId());
			pst.setInt(14, livro.getGrupoPrecificacao().getId());
			pst.setInt(15, (livro.isAtivo() ? 1 : 0));
			pst.setString(16, livro.getMotivo());
			
			Timestamp time = new Timestamp(livro.getDtCadastro().getTime());
			pst.setTimestamp(17, time);
			pst.setInt(18, livro.getId());
			
			pst.executeUpdate();
			
			sql = new StringBuilder();
			sql.append("INSERT INTO livro_categoria (iLivro, iCategoria) VALUES");
			
			if (livro.getCategorias().size() > 0) {
				for (Categoria categoria : livro.getCategorias()) {
					LivroCategoria livroCategoria = new LivroCategoria();
					livroCategoria.setLivro(livro);
					livroCategoria.setCategoria(categoria);;
					livroCategoria.setDtCadastro(new Date());
					
					new LivroCategoriaDAO(connection).salvar(livroCategoria);
				}
			}
			
			if (livro.getAutores().size() > 0) {
				for (Autor autor : livro.getAutores()) {
					LivroAutor livroAutor = new LivroAutor();
					livroAutor.setLivro(livro);
					livroAutor.setAutor(autor);
					livroAutor.setDtCadastro(new Date());
					
					new LivroAutorDAO(connection).salvar(livroAutor);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		List<EntidadeDominio> livros = new ArrayList<EntidadeDominio>();
		livros.add(entidade);
		
		return livros;
	}
	
	public List<EntidadeDominio> getLista(String filter) throws SQLException {
		openConnection();
		List<Autor> autores;
		List<Categoria> categorias;
		List<EntidadeDominio> livros = new	ArrayList<EntidadeDominio>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM livro");
		if (!filter.equals("")) {
			sql.append(" WHERE sTitulo LIKE '%" + filter + "%'");
		}
		
		PreparedStatement stmt = connection.prepareStatement(sql.toString());
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			String titulo = rs.getString("sTitulo");
			int idEditora = rs.getInt("iEditora");
			int numPags = rs.getInt("nNumPaginas");
			int ativo = rs.getInt("iAtivo");
			String ISBN = rs.getString("sISBN");
			String motivo = rs.getString("sMotivo");
			int idGrupoPrecificacao = rs.getInt("iGrupoPrecificacao");
			
			Editora editora = (Editora) new EditoraDAO().getEntidadeDominio(idEditora);
			GrupoPrecificacao grupoPrecificacao = (GrupoPrecificacao) new GrupoPrecificacaoDAO().getEntidadeDominio(idGrupoPrecificacao);
			List<EntidadeDominio> livrosAutores = new LivroAutorDAO().getEntidadeDominioLivro(id);
			List<EntidadeDominio> livrosCategorias = new LivroCategoriaDAO().getEntidadeDominioLivro(id);
			
			autores = new ArrayList<Autor>();
			for (EntidadeDominio ed : livrosAutores) {
				LivroAutor livroAutor = (LivroAutor) ed;
				autores.add(livroAutor.getAutor());
			}
			
			categorias = new ArrayList<Categoria>();
			for (EntidadeDominio ed : livrosCategorias) {
				LivroCategoria livroCategoria = (LivroCategoria) ed;
				categorias.add(livroCategoria.getCategoria());
			}
			
			Livro livro	= new Livro();
			livro.setId(id);
			livro.setTitulo(titulo);
			livro.setNumPags(numPags);
			livro.setISBN(ISBN);
			livro.setAtivo((ativo == 1) ? true : false);
			livro.setMotivo(motivo);
			livro.setEditora(editora);
			livro.setAutores(autores);
			livro.setCategorias(categorias);
			livro.setGrupoPrecificacao(grupoPrecificacao);
			
			livros.add(livro);
		}

		rs.close();
		stmt.close();
		connection.close();
		
		return livros;
	}
	
	public List<EntidadeDominio> getLista() throws SQLException {
		return getLista("");
	}
	
	public List<EntidadeDominio> getLivrosAtivos() throws SQLException {
		openConnection();
		List<Autor> autores;
		List<Categoria> categorias;
		List<EntidadeDominio> livros = new	ArrayList<EntidadeDominio>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM livro WHERE iAtivo = 1");
		
		PreparedStatement stmt = connection.prepareStatement(sql.toString());
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			String titulo = rs.getString("sTitulo");
			int idEditora = rs.getInt("iEditora");
			int numPags = rs.getInt("nNumPaginas");
			int ativo = rs.getInt("iAtivo");
			String ISBN = rs.getString("sISBN");
			String motivo = rs.getString("sMotivo");
			int idGrupoPrecificacao = rs.getInt("iGrupoPrecificacao");
			
			Editora editora = (Editora) new EditoraDAO().getEntidadeDominio(idEditora);
			GrupoPrecificacao grupoPrecificacao = (GrupoPrecificacao) new GrupoPrecificacaoDAO().getEntidadeDominio(idGrupoPrecificacao);
			List<EntidadeDominio> livrosAutores = new LivroAutorDAO().getEntidadeDominioLivro(id);
			List<EntidadeDominio> livrosCategorias = new LivroCategoriaDAO().getEntidadeDominioLivro(id);
			
			autores = new ArrayList<Autor>();
			for (EntidadeDominio ed : livrosAutores) {
				LivroAutor livroAutor = (LivroAutor) ed;
				autores.add(livroAutor.getAutor());
			}
			
			categorias = new ArrayList<Categoria>();
			for (EntidadeDominio ed : livrosCategorias) {
				LivroCategoria livroCategoria = (LivroCategoria) ed;
				categorias.add(livroCategoria.getCategoria());
			}
			
			Livro livro	= new Livro();
			livro.setId(id);
			livro.setTitulo(titulo);
			livro.setNumPags(numPags);
			livro.setISBN(ISBN);
			livro.setAtivo((ativo == 1) ? true : false);
			livro.setMotivo(motivo);
			livro.setEditora(editora);
			livro.setAutores(autores);
			livro.setCategorias(categorias);
			livro.setGrupoPrecificacao(grupoPrecificacao);
			
			livros.add(livro);
		}

		rs.close();
		stmt.close();
		connection.close();
		
		return livros;
	}
	
	public EntidadeDominio getEntidadeDominio(EntidadeDominio entidadeDominio)  throws SQLException {
		openConnection();
		List<Autor> autores;
		List<Categoria> categorias;
		Livro livro = (Livro) entidadeDominio;
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM livro WHERE id = ?");
		stmt.setLong(1, livro.getId());
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			String codigo = rs.getString("sCodigo");
			String ano = rs.getString("sAno");
			String titulo = rs.getString("sTitulo");
			int idEditora = rs.getInt("iEditora");
			String edicao = rs.getString("sEdicao");
			String ISBN = rs.getString("sISBN");
			int numPags = rs.getInt("nNumPaginas");
			String sinopse = rs.getString("sSinopse");
			double altura = rs.getDouble("nAltura");
			double largura = rs.getDouble("nLargura");
			double peso = rs.getDouble("nPeso");
			double profundidade = rs.getDouble("nProfundidade");
			int idGrupoPrecificacao = rs.getInt("iGrupoPrecificacao");
			String codBarras = rs.getString("sCodBarras");
			int ativo = rs.getInt("iAtivo");
			String motivo = rs.getString("sMotivo");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			Editora editora = (Editora) new EditoraDAO().getEntidadeDominio(idEditora);
			GrupoPrecificacao grupoPrecificacao = (GrupoPrecificacao) new GrupoPrecificacaoDAO().getEntidadeDominio(idGrupoPrecificacao);
			List<EntidadeDominio> livrosAutores = new LivroAutorDAO().getEntidadeDominioLivro(id);
			List<EntidadeDominio> livrosCategorias = new LivroCategoriaDAO().getEntidadeDominioLivro(id);
			
			livro.setId(id);
			livro.setCodigo(codigo);
			livro.setAno(ano);
			livro.setTitulo(titulo);
			livro.setEditora(editora);
			livro.setEdicao(edicao);
			livro.setISBN(ISBN);
			livro.setNumPags(numPags);
			livro.setSinopse(sinopse);
			livro.setAltura(altura);
			livro.setLargura(largura);
			livro.setPeso(peso);
			livro.setProfundidade(profundidade);
			livro.setGrupoPrecificacao(grupoPrecificacao);
			livro.setCodBarras(codBarras);
			livro.setAtivo(ativo == 1 ? true : false);
			livro.setMotivo(motivo);
			livro.setDtCadastro(dtCadastro);
			
			autores = new ArrayList<Autor>();
			for (EntidadeDominio ed : livrosAutores) {
				LivroAutor livroAutor = (LivroAutor) ed;
				autores.add(livroAutor.getAutor());
			}
			
			livro.setAutores(autores);
			
			categorias = new ArrayList<Categoria>();
			for (EntidadeDominio ed : livrosCategorias) {
				LivroCategoria livroCategoria = (LivroCategoria) ed;
				categorias.add(livroCategoria.getCategoria());
			}
			
			livro.setCategorias(categorias);
		}
		
		rs.close();
		stmt.close();
		connection.close();

		return livro;
	}
	
	public EntidadeDominio getEntidadeDominio(int idLivro)  throws SQLException {
		openConnection();
		List<Autor> autores;
		List<Categoria> categorias;
		Livro livro = new Livro();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM livro WHERE id = ?");
		stmt.setLong(1, idLivro);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			String codigo = rs.getString("sCodigo");
			String ano = rs.getString("sAno");
			String titulo = rs.getString("sTitulo");
			int idEditora = rs.getInt("iEditora");
			String edicao = rs.getString("sEdicao");
			String ISBN = rs.getString("sISBN");
			int numPags = rs.getInt("nNumPaginas");
			String sinopse = rs.getString("sSinopse");
			double altura = rs.getDouble("nAltura");
			double largura = rs.getDouble("nLargura");
			double peso = rs.getDouble("nPeso");
			double profundidade = rs.getDouble("nProfundidade");
			int idGrupoPrecificacao = rs.getInt("iGrupoPrecificacao");
			String codBarras = rs.getString("sCodBarras");
			int ativo = rs.getInt("iAtivo");
			String motivo = rs.getString("sMotivo");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			Editora editora = (Editora) new EditoraDAO().getEntidadeDominio(idEditora);
			GrupoPrecificacao grupoPrecificacao = (GrupoPrecificacao) new GrupoPrecificacaoDAO().getEntidadeDominio(idGrupoPrecificacao);
			List<EntidadeDominio> livrosAutores = new LivroAutorDAO().getEntidadeDominioLivro(id);
			List<EntidadeDominio> livrosCategorias = new LivroCategoriaDAO().getEntidadeDominioLivro(id);
			
			livro.setId(id);
			livro.setCodigo(codigo);
			livro.setAno(ano);
			livro.setTitulo(titulo);
			livro.setEditora(editora);
			livro.setEdicao(edicao);
			livro.setISBN(ISBN);
			livro.setNumPags(numPags);
			livro.setSinopse(sinopse);
			livro.setAltura(altura);
			livro.setLargura(largura);
			livro.setPeso(peso);
			livro.setProfundidade(profundidade);
			livro.setGrupoPrecificacao(grupoPrecificacao);
			livro.setCodBarras(codBarras);
			livro.setAtivo(ativo == 1 ? true : false);
			livro.setMotivo(motivo);
			livro.setDtCadastro(dtCadastro);
			
			autores = new ArrayList<Autor>();
			for (EntidadeDominio ed : livrosAutores) {
				LivroAutor livroAutor = (LivroAutor) ed;
				autores.add(livroAutor.getAutor());
			}
			
			livro.setAutores(autores);
			
			categorias = new ArrayList<Categoria>();
			for (EntidadeDominio ed : livrosCategorias) {
				LivroCategoria livroCategoria = (LivroCategoria) ed;
				categorias.add(livroCategoria.getCategoria());
			}
			
			livro.setCategorias(categorias);
		}
		
		rs.close();
		stmt.close();
		connection.close();

		return livro;
	}
	
	public String getTitulo(int idLivro)  throws SQLException {
		openConnection();
		String titulo = null;
		
		PreparedStatement stmt = connection.prepareStatement("SELECT sTitulo FROM livro WHERE id = ?");
		stmt.setLong(1, idLivro);
		ResultSet rs = stmt.executeQuery();
		
		if (rs.next()) {
			titulo = rs.getString("sTitulo");
		}
		
		rs.close();
		stmt.close();
		connection.close();

		return titulo;
	}

	/* (non-Javadoc)
	 * @see br.com.ecolivros.core.impl.dao.AbstractJdbcDAO#excluir(br.com.ecolivros.dominio.EntidadeDominio)
	 */
	@Override
	public void excluir(EntidadeDominio entidade) {
		new LivroCategoriaDAO().excluir(entidade);
		new LivroAutorDAO().excluir(entidade);
		super.excluir(entidade);
	}
	
	public void ativarById(int idLivro, int ativo, String motivo) throws SQLException {
		openConnection();
		StringBuilder sql;
		PreparedStatement pst = null;

		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			sql.append("UPDATE livro SET iAtivo = ?, sMotivo = ?, dtCadastro = ? WHERE id = ?");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setInt(1, ativo);
			pst.setString(2, motivo);
			
			Timestamp time = new Timestamp(new Date().getTime());
			pst.setTimestamp(3, time);
			pst.setInt(4, idLivro);
			
			pst.executeUpdate();
			
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}