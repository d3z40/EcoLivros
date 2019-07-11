package br.com.ecolivros.core.impl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.com.ecolivros.dominio.Categoria;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Livro;
import br.com.ecolivros.dominio.LivroCategoria;

public class LivroCategoriaDAO extends AbstractJdbcDAO {

	public LivroCategoriaDAO() {
		super("livro_categoria", "id");
	}
	
	public LivroCategoriaDAO(Connection connection) {
		super(connection, "livro_categoria", "id");
		ctrlTransaction = false;
	}
	
	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		PreparedStatement pst = null;
		LivroCategoria livroCategoria = (LivroCategoria) entidade;
		
		try {
			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO livro_categoria (iLivro, iCategoria, dtCadastro) ");
			sql.append("VALUES (?,?,?)");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setInt(1, livroCategoria.getLivro().getId());
			pst.setInt(2, livroCategoria.getCategoria().getId());
			
			Timestamp time = new Timestamp(livroCategoria.getDtCadastro().getTime());
			pst.setTimestamp(3, time);
			
			pst.executeUpdate();
			
			if (ctrlTransaction)
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
				
				if (ctrlTransaction)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		return null;
	}
	
	@Override
	public void excluir(EntidadeDominio entidade) {
		openConnection();
		PreparedStatement pst = null;
		
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM livro_categoria WHERE iLivro = ?");
		
		try {
			connection.setAutoCommit(false);
			pst = connection.prepareStatement(sb.toString());
			pst.setInt(1, entidade.getId());
			
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
				if (ctrlTransaction)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public EntidadeDominio getEntidadeDominio(EntidadeDominio entidadeDominio) throws SQLException {
		openConnection();
		LivroCategoria livroCategoria = (LivroCategoria) entidadeDominio;
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM livro_categoria WHERE id = ?");
		stmt.setLong(1, livroCategoria.getId());;
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int iLivro = rs.getInt("iLivro");
			int iCategoria = rs.getInt("iCategoria");
			
			Livro livro = (Livro) new LivroDAO().getEntidadeDominio(iLivro);
			Categoria categoria = (Categoria) new CategoriaDAO().getEntidadeDominio(iCategoria);
			
			livroCategoria.setId(id);
			livroCategoria.setLivro(livro);
			livroCategoria.setCategoria(categoria);
		}
		
		rs.close();
		stmt.close();
		connection.close();

		return livroCategoria;
	}
	
	public List<EntidadeDominio> getEntidadeDominioLivro(int idLivro) throws SQLException {
		openConnection();
		LivroCategoria livroCategoria;
		List<EntidadeDominio> livrosCategorias = new ArrayList<EntidadeDominio>();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM livro_categoria WHERE iLivro = ?");
		stmt.setInt(1, idLivro);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int iCategoria = rs.getInt("iCategoria");
			
			Categoria categoria = (Categoria) new CategoriaDAO().getEntidadeDominio(iCategoria);
			
			livroCategoria = new LivroCategoria();
			livroCategoria.setId(id);
			livroCategoria.setCategoria(categoria);
			
			livrosCategorias.add(livroCategoria);
		}

		rs.close();
		stmt.close();
		connection.close();

		return livrosCategorias;
	}
	
	public List<EntidadeDominio> getEntidadeDominioAutor(int idCategoria) throws SQLException {
		openConnection();
		LivroCategoria livroCategoria;
		List<EntidadeDominio> livrosCategoria = new ArrayList<EntidadeDominio>();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM livro_categoria WHERE iCategoria = ?");
		stmt.setInt(1, idCategoria);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int iLivro = rs.getInt("iLivro");
			
			Livro livro = (Livro) new LivroDAO().getEntidadeDominio(iLivro);
			
			livroCategoria = new LivroCategoria();
			livroCategoria.setId(id);
			livroCategoria.setLivro(livro);
			
			livrosCategoria.add(livroCategoria);
		}
		
		rs.close();
		stmt.close();
		connection.close();
		
		return livrosCategoria;
	}
	
	public List<EntidadeDominio> getLista() throws SQLException {
		openConnection();
		List<EntidadeDominio> livrosCategorias = new	ArrayList<EntidadeDominio>();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM livro_categoria");
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int iLivro = rs.getInt("iLivro");
			int iCategoria = rs.getInt("iCategoria");
			
			Livro livro = (Livro) new LivroDAO().getEntidadeDominio(iLivro);
			
			Categoria categoria	= (Categoria) new CategoriaDAO().getEntidadeDominio(iCategoria);
			
			LivroCategoria livroCategoria = new LivroCategoria();
			livroCategoria.setId(id);
			livroCategoria.setLivro(livro);
			livroCategoria.setCategoria(categoria);
			
			livrosCategorias.add(categoria);
		}
		
		rs.close();
		stmt.close();
		connection.close();

		return livrosCategorias;
	}
}