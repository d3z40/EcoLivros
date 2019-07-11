package br.com.ecolivros.core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ecolivros.dominio.Editora;
import br.com.ecolivros.dominio.EntidadeDominio;

public class EditoraDAO extends AbstractJdbcDAO {

	public EditoraDAO() {
		super("editora", "id");
	}

	public List<EntidadeDominio> getLista() throws SQLException {
		openConnection();
		List<EntidadeDominio> editoras = new	ArrayList<EntidadeDominio>();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM editora");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			Editora editora	= new Editora();
			editora.setId(rs.getInt("id"));
			editora.setRazao(rs.getString("sRazao"));

			editoras.add(editora);
		}
		
		rs.close();
		stmt.close();
		connection.close();

		return editoras;
	}

	public EntidadeDominio getEntidadeDominio(EntidadeDominio entidadeDominio) throws SQLException {
		openConnection();
		Editora editora = (Editora) entidadeDominio;

		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM editora WHERE id = ?");
		stmt.setLong(1, editora.getId());
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			editora.setId(rs.getInt("id"));
			editora.setRazao(rs.getString("sRazao"));
		}
		
		rs.close();
		stmt.close();
		connection.close();

		return editora;
	}
	
	public EntidadeDominio getEntidadeDominio(int idEditora) throws SQLException {
		openConnection();
		Editora editora = new Editora();

		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM editora WHERE id = ?");
		stmt.setInt(1, idEditora);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			editora.setId(rs.getInt("id"));
			editora.setRazao(rs.getString("sRazao"));
		}
		
		rs.close();
		stmt.close();
		connection.close();

		return editora;
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