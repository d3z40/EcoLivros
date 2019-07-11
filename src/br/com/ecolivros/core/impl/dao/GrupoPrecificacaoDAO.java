package br.com.ecolivros.core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.GrupoPrecificacao;

public class GrupoPrecificacaoDAO extends AbstractJdbcDAO {

	public GrupoPrecificacaoDAO() {
		super("grupoprecificacao", "id");
	}

	public List<EntidadeDominio> getLista() throws SQLException {
		openConnection();
		List<EntidadeDominio> precificacoes = new	ArrayList<EntidadeDominio>();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM grupoprecificacao");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			GrupoPrecificacao precificacao	= new GrupoPrecificacao();
			precificacao.setId(rs.getInt("id"));
			precificacao.setMargem(rs.getDouble("nMargem"));

			precificacoes.add(precificacao);
		}
		
		rs.close();
		stmt.close();
		connection.close();

		return precificacoes;
	}

	public EntidadeDominio getEntidadeDominio(EntidadeDominio entidadeDominio) throws SQLException {
		openConnection();
		GrupoPrecificacao precificacao = (GrupoPrecificacao) entidadeDominio;

		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM grupoprecificacao WHERE id = ?");
		stmt.setLong(1, precificacao.getId());
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			precificacao.setId(rs.getInt("id"));
			precificacao.setMargem(rs.getDouble("nMargem"));
		}
		
		rs.close();
		stmt.close();
		connection.close();

		return precificacao;
	}
	
	public EntidadeDominio getEntidadeDominio(int idGrupoPrecificacao) throws SQLException {
		openConnection();
		GrupoPrecificacao precificacao = new GrupoPrecificacao();

		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM grupoprecificacao WHERE id = ?");
		stmt.setLong(1, idGrupoPrecificacao);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			precificacao.setId(rs.getInt("id"));
			precificacao.setMargem(rs.getDouble("nMargem"));
		}

		rs.close();
		stmt.close();
		connection.close();

		return precificacao;
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