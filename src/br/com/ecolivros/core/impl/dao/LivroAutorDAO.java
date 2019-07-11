package br.com.ecolivros.core.impl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.com.ecolivros.dominio.Autor;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Livro;
import br.com.ecolivros.dominio.LivroAutor;

public class LivroAutorDAO extends AbstractJdbcDAO {

	public LivroAutorDAO() {
		super("livro_autor", "id");
	}
	
	public LivroAutorDAO(Connection connection) {
		super(connection, "livro_autor", "id");
		ctrlTransaction = false;
	}
	
	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		PreparedStatement pst = null;
		LivroAutor livroAutor = (LivroAutor) entidade;
		
		try {
			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO livro_autor (iLivro, iAutor, dtCadastro) ");
			sql.append("VALUES (?,?,?)");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setInt(1, livroAutor.getLivro().getId());
			pst.setInt(2, livroAutor.getAutor().getId());
			
			Timestamp time = new Timestamp(livroAutor.getDtCadastro().getTime());
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
		sb.append("DELETE FROM livro_autor WHERE iLivro = ?");
		
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
		LivroAutor livroAutor = (LivroAutor) entidadeDominio;
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM livro_autor WHERE id = ?");
		stmt.setLong(1, livroAutor.getId());;
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int iLivro = rs.getInt("iLivro");
			int iAutor = rs.getInt("iAutor");
			
			Livro livro = (Livro) new LivroDAO().getEntidadeDominio(iLivro);
			Autor autor = (Autor) new AutorDAO().getEntidadeDominio(iAutor);
			
			livroAutor.setId(id);
			livroAutor.setLivro(livro);
			livroAutor.setAutor(autor);
		}
		rs.close();
		stmt.close();
		connection.close();

		return livroAutor;
	}
	
	public List<EntidadeDominio> getEntidadeDominioLivro(int idLivro) throws SQLException {
		openConnection();
		LivroAutor livroAutor;
		List<EntidadeDominio> livrosAutores = new ArrayList<EntidadeDominio>();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM livro_autor WHERE iLivro = ?");
		stmt.setInt(1, idLivro);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int iAutor = rs.getInt("iAutor");
			
			Autor autor = (Autor) new AutorDAO().getEntidadeDominio(iAutor);
			
			livroAutor = new LivroAutor();
			livroAutor.setId(id);
			livroAutor.setAutor(autor);
			
			livrosAutores.add(livroAutor);
		}
		
		rs.close();
		stmt.close();
		connection.close();
		
		return livrosAutores;
	}
	
	public List<EntidadeDominio> getEntidadeDominioAutor(int idAutor) throws SQLException {
		openConnection();
		LivroAutor livroAutor;
		List<EntidadeDominio> livrosAutores = new ArrayList<EntidadeDominio>();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM livro_autor WHERE iAutor = ?");
		stmt.setInt(1, idAutor);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int iLivro = rs.getInt("iLivro");
			
			Livro livro = (Livro) new LivroDAO().getEntidadeDominio(iLivro);
			
			livroAutor = new LivroAutor();
			livroAutor.setId(id);
			livroAutor.setLivro(livro);
			
			livrosAutores.add(livroAutor);
		}
		
		rs.close();
		stmt.close();
		connection.close();

		return livrosAutores;
	}
	
	public List<EntidadeDominio> getLista() throws SQLException {
		openConnection();
		List<EntidadeDominio> livrosAutores = new	ArrayList<EntidadeDominio>();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM livro_autor");
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idLivro = rs.getInt("iLivro");
			int idAutor = rs.getInt("iAutor");
			
			Livro livro = (Livro) new LivroDAO().getEntidadeDominio(idLivro);
			
			Autor autor	= (Autor) new AutorDAO().getEntidadeDominio(idAutor);
			
			LivroAutor livroAutor = new LivroAutor();
			livroAutor.setId(id);
			livroAutor.setLivro(livro);
			livroAutor.setAutor(autor);
			
			livrosAutores.add(autor);
		}
		
		rs.close();
		stmt.close();
		connection.close();

		return livrosAutores;
	}
}