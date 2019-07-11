package br.com.ecolivros.core.impl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ecolivros.core.util.UsuarioLogado;
import br.com.ecolivros.dominio.Cupom;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Pagamento;

public class CupomDAO extends AbstractJdbcDAO {

	public CupomDAO() {
		super("cupom", "id");
	}
	
	public CupomDAO(Connection connection) {
		super(connection, "cupom", "id");
		ctrlTransaction = false;
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		StringBuilder sql;
		PreparedStatement pst = null;
		
		Cupom cupom = (Cupom) entidade;

		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			sql.append("INSERT INTO cupom (iUsuario, sIdentificador, nValorCupom, nValorUtilizado, dtCadastro) ");
			sql.append("VALUES (?,?,?,?,?)");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setInt(1, cupom.getIdUsuario());
			pst.setString(2, cupom.getIdentificador());
			pst.setDouble(3, cupom.getValorCupom());
			pst.setDouble(4, cupom.getValorUtilizado());
			
			Timestamp time = new Timestamp(cupom.getDtCadastro().getTime());
			pst.setTimestamp(5, time);
			
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
		openConnection();
		StringBuilder sql;
		PreparedStatement pst = null;
		
		Cupom cupom = (Cupom) entidade;
		
		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			
			sql.append("UPDATE cupom SET nValorUtilizado = ? WHERE id = ?");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setDouble(1, cupom.getValorUtilizado());
			pst.setInt(2, cupom.getId());
			
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
		List<EntidadeDominio> cupons = new	ArrayList<EntidadeDominio>();
		
		Cupom cupom = (Cupom) entidade;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM cupom");
		sql.append(" WHERE iUsuario = ? AND nValorCupom > nValorUtilizado");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, cupom.getIdUsuario());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idUsuario = rs.getInt("iUsuario");
			String identificador = rs.getString("sIdentificador");
			double valorCupom = rs.getDouble("nValorCupom");
			double valorUtilizado = rs.getDouble("nValorUtilizado");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			cupom	= new Cupom();
			cupom.setId(id);
			cupom.setIdUsuario(idUsuario);
			cupom.setIdentificador(identificador);
			cupom.setValorCupom(valorCupom);
			cupom.setValorUtilizado(valorUtilizado);
			cupom.setDtCadastro(dtCadastro);
			
			cupons.add(cupom);
		}
		
		rs.close();
		pst.close();
		if (ctrlTransaction)
			connection.close();
		
		return cupons;
	}
	
	public Pagamento getCupomById(int idCupom) throws SQLException {
		openConnection();
		PreparedStatement pst;
		
		Cupom cupom = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM cupom");
		sql.append(" WHERE id = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, idCupom);
		
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()) {
			int id = rs.getInt("id");
			int idUsuario = rs.getInt("iUsuario");
			String identificador = rs.getString("sIdentificador");
			double valorCupom = rs.getDouble("nValorCupom");
			double valorUtilizado = rs.getDouble("nValorUtilizado");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			cupom	= new Cupom();
			cupom.setId(id);
			cupom.setIdUsuario(idUsuario);
			cupom.setIdentificador(identificador);
			cupom.setValorCupom(valorCupom);
			cupom.setValorUtilizado(valorUtilizado);
			cupom.setDtCadastro(dtCadastro);
		}
		
		rs.close();
		pst.close();
		if (ctrlTransaction)
			connection.close();
		
		return cupom;
	}
	
	public List<Cupom> getCupomByUsuario(int iUsuario) throws SQLException {
		openConnection();
		PreparedStatement pst;
		List<Cupom> cupons = new ArrayList<Cupom>();
		Cupom cupom = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM cupom");
		sql.append(" WHERE iUsuario = ? AND nValorCupom > nValorUtilizado");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, iUsuario);
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idUsuario = rs.getInt("iUsuario");
			String identificador = rs.getString("sIdentificador");
			double valorCupom = rs.getDouble("nValorCupom");
			double valorUtilizado = rs.getDouble("nValorUtilizado");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			cupom	= new Cupom();
			cupom.setId(id);
			cupom.setIdUsuario(idUsuario);
			cupom.setIdentificador(identificador);
			cupom.setValorCupom(valorCupom);
			cupom.setValorUtilizado(valorUtilizado);
			cupom.setDtCadastro(dtCadastro);
			
			cupons.add(cupom);
		}
		
		rs.close();
		pst.close();
		if (ctrlTransaction)
			connection.close();
		
		return cupons;
	}
	
	public List<EntidadeDominio> getLista(String filter) throws SQLException {
		openConnection();
		List<EntidadeDominio> cupons = new	ArrayList<EntidadeDominio>();
		Cupom cupom = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM cupom WHERE iUsuario = ?");
		if (!filter.equals("")) {
			sql.append(" AND sIdentificador LIKE '%" + filter + "%'");
		}
		
		PreparedStatement stmt = connection.prepareStatement(sql.toString());
		stmt.setInt(1, UsuarioLogado.getUsuario().getId());
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idUsuario = rs.getInt("iUsuario");
			String identificador = rs.getString("sIdentificador");
			double valorCupom = rs.getDouble("nValorCupom");
			double valorUtilizado = rs.getDouble("nValorUtilizado");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			cupom	= new Cupom();
			cupom.setId(id);
			cupom.setIdUsuario(idUsuario);
			cupom.setIdentificador(identificador);
			cupom.setValorCupom(valorCupom);
			cupom.setValorUtilizado(valorUtilizado);
			cupom.setDtCadastro(dtCadastro);
			
			cupons.add(cupom);
		}

		rs.close();
		stmt.close();
		if (ctrlTransaction)
			connection.close();
		
		return cupons;
	}
	
	public List<EntidadeDominio> getLista() throws SQLException {
		return getLista("");
	}
}