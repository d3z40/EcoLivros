package br.com.ecolivros.core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.com.ecolivros.dominio.ControleDeAcesso;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.enuns.TipoAcesso;

public class ControleDeAcessoDAO extends AbstractJdbcDAO {

	public ControleDeAcessoDAO() {
		super("controledeacesso", "id");
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {		
		openConnection();
		StringBuilder sql;
		PreparedStatement pst = null;
		
		ControleDeAcesso controleDeAcesso = (ControleDeAcesso) entidade;
		
		try {
			connection.setAutoCommit(false);

			sql = new StringBuilder();
			sql.append("INSERT INTO controledeacesso (idAcesso, iTipoAcesso, dtCadastro) ");
			sql.append("VALUES (?,?,?)");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setInt(1, controleDeAcesso.getIdAcesso());
			pst.setInt(2, controleDeAcesso.getTipoAcesso().getCodigo());
			
			Timestamp time = new Timestamp(controleDeAcesso.getDtCadastro().getTime());
			pst.setTimestamp(3, time);
			
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

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		return null;
	}
	
	public EntidadeDominio getEntidadeDominio(EntidadeDominio entidadeDominio) throws SQLException {
		openConnection();
		PreparedStatement stmt;
		ControleDeAcesso controleDeAcesso = (ControleDeAcesso) entidadeDominio;
		
		stmt = connection.prepareStatement("SELECT * FROM controledeacesso WHERE id = ?");
		stmt.setLong(1, controleDeAcesso.getId());
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idAcesso = rs.getInt("iAcesso");
			int iTipoAcesso = rs.getInt("iTipoAcesso");
			
			controleDeAcesso.setId(id);
			controleDeAcesso.setIdAcesso(idAcesso);
			
			for (TipoAcesso tipoAcesso : TipoAcesso.values()) {
				if (iTipoAcesso == tipoAcesso.getCodigo()) {
					controleDeAcesso.setTipoAcesso(tipoAcesso);
				}
			}
		}
		rs.close();
		stmt.close();
		connection.close();

		return controleDeAcesso;
	}
	
	public List<EntidadeDominio> getEntidadeDominioAcesso(int idAcesso) throws SQLException {
		openConnection();
		PreparedStatement stmt;
		ControleDeAcesso controleDeAcesso;
		List<EntidadeDominio> controlesDeAcesso = new ArrayList<EntidadeDominio>();
		
		stmt = connection.prepareStatement("SELECT * FROM controledeacesso WHERE idAcesso = ?");
		stmt.setLong(1, idAcesso);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int iAcesso = rs.getInt("iAcesso");
			int iTipoAcesso = rs.getInt("iTipoAcesso");
			
			controleDeAcesso = new ControleDeAcesso();
			controleDeAcesso.setId(id);
			controleDeAcesso.setIdAcesso(iAcesso);
			
			for (TipoAcesso tipoAcesso : TipoAcesso.values()) {
				if (iTipoAcesso == tipoAcesso.getCodigo()) {
					controleDeAcesso.setTipoAcesso(tipoAcesso);
				}
			}
			
			controlesDeAcesso.add(controleDeAcesso);
		}
		rs.close();
		stmt.close();
		connection.close();

		return controlesDeAcesso;
	}
	
	public List<EntidadeDominio> getEntidadeDominioTipoAcesso(int idTipoAcesso) throws SQLException {
		openConnection();
		PreparedStatement stmt;
		ControleDeAcesso controleDeAcesso;
		List<EntidadeDominio> controlesDeAcesso = new ArrayList<EntidadeDominio>();
		
		stmt = connection.prepareStatement("SELECT * FROM controledeacesso WHERE iTipoAcesso = ?");
		stmt.setLong(1, idTipoAcesso);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int iAcesso = rs.getInt("iAcesso");
			int iTipoAcesso = rs.getInt("iTipoAcesso");
			
			controleDeAcesso = new ControleDeAcesso();
			controleDeAcesso.setId(id);
			controleDeAcesso.setIdAcesso(iAcesso);
			
			for (TipoAcesso tipoAcesso : TipoAcesso.values()) {
				if (iTipoAcesso == tipoAcesso.getCodigo()) {
					controleDeAcesso.setTipoAcesso(tipoAcesso);
				}
			}
			
			controlesDeAcesso.add(controleDeAcesso);
		}
		rs.close();
		stmt.close();
		connection.close();

		return controlesDeAcesso;
	}
	
	public List<EntidadeDominio> getLista() throws SQLException {
		openConnection();
		PreparedStatement stmt;
		ControleDeAcesso controleDeAcesso;
		List<EntidadeDominio> controlesDeAcesso = new ArrayList<EntidadeDominio>();
		
		stmt = connection.prepareStatement("SELECT * FROM controledeacesso");
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int iAcesso = rs.getInt("iAcesso");
			int iTipoAcesso = rs.getInt("iTipoAcesso");
			
			controleDeAcesso = new ControleDeAcesso();
			controleDeAcesso.setId(id);
			controleDeAcesso.setIdAcesso(iAcesso);
			
			for (TipoAcesso tipoAcesso : TipoAcesso.values()) {
				if (iTipoAcesso == tipoAcesso.getCodigo()) {
					controleDeAcesso.setTipoAcesso(tipoAcesso);
				}
			}
			
			controlesDeAcesso.add(controleDeAcesso);
		}
		rs.close();
		stmt.close();
		connection.close();

		return controlesDeAcesso;
	}
}