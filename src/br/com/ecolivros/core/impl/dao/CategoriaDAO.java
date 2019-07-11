package br.com.ecolivros.core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ecolivros.dominio.Categoria;
import br.com.ecolivros.dominio.EntidadeDominio;

public class CategoriaDAO extends AbstractJdbcDAO {

	public CategoriaDAO() {
		super("categoria", "id");
	}

	public List<EntidadeDominio> getLista() throws SQLException {
		openConnection();
		List<EntidadeDominio> categorias = new	ArrayList<EntidadeDominio>();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM categoria");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			Categoria categoria	= new Categoria();
			categoria.setId(rs.getInt("id"));
			categoria.setDescricao(rs.getString("sDescricao"));

			categorias.add(categoria);
		}
		
		rs.close();
		stmt.close();
		connection.close();

		return categorias;
	}

	public EntidadeDominio getEntidadeDominio(EntidadeDominio entidadeDominio) throws SQLException {
		openConnection();
		Categoria categoria = (Categoria) entidadeDominio;

		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM categoria WHERE id = ?");
		stmt.setLong(1, categoria.getId());
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			categoria.setId(rs.getInt("id"));
			categoria.setDescricao(rs.getString("sDescricao"));
		}
		
		rs.close();
		stmt.close();
		connection.close();

		return categoria;
	}
	
	public EntidadeDominio getEntidadeDominio(int idCategoria) throws SQLException {
		openConnection();
		Categoria categoria = new Categoria();
		
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM categoria WHERE id = ?");
		stmt.setLong(1, idCategoria);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			categoria.setId(rs.getInt("id"));
			categoria.setDescricao(rs.getString("sDescricao"));
		}
		
		rs.close();
		stmt.close();
		connection.close();
		
		return categoria;
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		return null;
	}
}