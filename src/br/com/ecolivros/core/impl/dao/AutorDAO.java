package br.com.ecolivros.core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ecolivros.dominio.Autor;
import br.com.ecolivros.dominio.EntidadeDominio;

public class AutorDAO extends AbstractJdbcDAO {

	public AutorDAO () {
		super("autor", "id");
	}

	public List<EntidadeDominio> getLista() throws SQLException {
		openConnection();
		List<EntidadeDominio> autores = new	ArrayList<EntidadeDominio>();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM autor");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			Autor autor	= new Autor();
			autor.setId(rs.getInt("id"));
			autor.setNomeArtistico(rs.getString("sNomeArtistico"));
			autor.setNomeCompleto(rs.getString("sNomeCompleto"));

			autores.add(autor);
		}
		rs.close();
		stmt.close();
		connection.close();
		
		return autores;
	}

	public EntidadeDominio getEntidadeDominio(EntidadeDominio entidadeDominio) throws SQLException {
		openConnection();
		Autor autor = (Autor) entidadeDominio;
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM autor WHERE id = ?");
		stmt.setLong(1, autor.getId());;
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			autor.setId(rs.getInt("id"));
			autor.setNomeArtistico(rs.getString("sNomeArtistico"));
			autor.setNomeCompleto(rs.getString("sNomeCompleto"));
		}
		rs.close();
		stmt.close();
		connection.close();

		return autor;
	}

	public EntidadeDominio getEntidadeDominio(int idAutor) throws SQLException {
		openConnection();
		Autor autor = new Autor();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM autor WHERE id = ?");
		stmt.setLong(1, idAutor);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			autor.setId(rs.getInt("id"));
			autor.setNomeArtistico(rs.getString("sNomeArtistico"));
			autor.setNomeCompleto(rs.getString("sNomeCompleto"));
		}
		
		rs.close();
		stmt.close();
		connection.close();

		return autor;
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