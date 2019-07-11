package br.com.ecolivros.core.impl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ecolivros.dominio.Boleto;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Pagamento;

public class BoletoDAO extends AbstractJdbcDAO {

	public BoletoDAO() {
		super("boleto", "id");
	}
	
	public BoletoDAO(Connection connection) {
		super(connection, "boleto", "id");
		ctrlTransaction = false;
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		StringBuilder sql;
		PreparedStatement pst = null;
		
		Boleto boleto = (Boleto) entidade;

		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			sql.append("INSERT INTO boleto (iUsuario, sCodigoBarras, dtCadastro) ");
			sql.append("VALUES (?,?,?)");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setInt(1, boleto.getIdUsuario());
			pst.setString(2, boleto.getCodigoBarras());
			
			Timestamp time = new Timestamp(boleto.getDtCadastro().getTime());
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
	
	public Integer salvar(Boleto boleto) throws SQLException {
		openConnection();
		StringBuilder sql;
		PreparedStatement pst = null;
		Integer idBoleto = null;
		
		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			sql.append("INSERT INTO boleto (iUsuario, sCodigoBarras, dtCadastro) ");
			sql.append("VALUES (?,?,?)");
			
			pst = connection.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			
			pst.setInt(1, boleto.getIdUsuario());
			pst.setString(2, boleto.getCodigoBarras());
			
			Timestamp time = new Timestamp(boleto.getDtCadastro().getTime());
			pst.setTimestamp(3, time);

			pst.executeUpdate();
			
			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next())
				idBoleto = rs.getInt(1);
			
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
		
		return idBoleto;
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		StringBuilder sql;
		PreparedStatement pst = null;
		
		Boleto boleto = (Boleto) entidade;
		
		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			
			sql.append("UPDATE boleto SET sCodigoBarras = ? WHERE id = ?");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setString(1, boleto.getCodigoBarras());
			pst.setInt(2, boleto.getId());
			
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
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		PreparedStatement pst;
		List<EntidadeDominio> boletos = new	ArrayList<EntidadeDominio>();
		
		Boleto boleto = (Boleto) entidade;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM boleto");
		sql.append(" WHERE iUsuario = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, boleto.getIdUsuario());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idUsuario = rs.getInt("iUsuario");
			String codigoBarras = rs.getString("sCodigoBarras");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			boleto	= new Boleto();
			boleto.setId(id);
			boleto.setIdUsuario(idUsuario);
			boleto.setCodigoBarras(codigoBarras);
			boleto.setDtCadastro(dtCadastro);
			
			boletos.add(boleto);
		}
		
		rs.close();
		pst.close();
		if (ctrlTransaction)
			connection.close();
		
		return boletos;
	}
	
	public Pagamento getBoletoById(int idBoleto) throws SQLException {
		openConnection();
		PreparedStatement pst;
		
		Boleto boleto = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM boleto");
		sql.append(" WHERE id = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, idBoleto);
		
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()) {
			int id = rs.getInt("id");
			int idUsuario = rs.getInt("iUsuario");
			String codigoBarras = rs.getString("sCodigoBarras");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			boleto	= new Boleto();
			boleto.setId(id);
			boleto.setIdUsuario(idUsuario);
			boleto.setCodigoBarras(codigoBarras);
			boleto.setDtCadastro(dtCadastro);
		}
		
		rs.close();
		pst.close();
		if (ctrlTransaction)
			connection.close();
		
		return boleto;
	}
	
	public Integer getMaxId() throws SQLException {
		openConnection();
		PreparedStatement pst;
		Integer id = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT MAX(id) AS maxId FROM boleto");
		
		pst = connection.prepareStatement(sql.toString());
		
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()) {
			id = rs.getInt("maxId");
		}
		
		rs.close();
		pst.close();
		if (ctrlTransaction)
			connection.close();
		
		return id;
	}
}