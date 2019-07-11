package br.com.ecolivros.core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.PreCupom;
import br.com.ecolivros.enuns.SituacaoEstoque;

public class PreCupomDAO extends AbstractJdbcDAO {

	public PreCupomDAO() {
		super("precupom", "id");
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		StringBuilder sql;
		PreparedStatement pst = null;
		
		PreCupom preCupom = (PreCupom) entidade;

		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			sql.append("INSERT INTO precupom (iUsuario, iItemPedido, nValor, iSituacaoEstoque, dtCadastro) ");
			sql.append("VALUES (?,?,?,?,?)");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setInt(1, preCupom.getIdUsuario());
			pst.setInt(2, preCupom.getIdItemPedido());
			pst.setDouble(3, preCupom.getValor());
			pst.setInt(4, preCupom.getSituacaoEstoque().getCodigo());
			
			Timestamp time = new Timestamp(preCupom.getDtCadastro().getTime());
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
		
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		return null;
	}
	
	public List<PreCupom> getPreCupomBySituacao(SituacaoEstoque situacaoEstoque) throws SQLException {
		openConnection();
		PreparedStatement pst;
		List<PreCupom> preCupons = new ArrayList<PreCupom>();
		
		PreCupom preCupom = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("pre.iUsuario, ");
		sql.append("SUM(pre.nValor) AS nValor ");
		sql.append("FROM precupom AS pre ");
		sql.append("WHERE pre.iSituacaoEstoque = ? ");
		sql.append("GROUP BY pre.iUsuario");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, situacaoEstoque.getCodigo());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int idUsuario = rs.getInt("iUsuario");
			double valor = rs.getInt("nValor");
			
			preCupom	= new PreCupom();
			preCupom.setIdUsuario(idUsuario);
			preCupom.setValor(valor);
			
			preCupons.add(preCupom);
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return preCupons;
	}
	
	public void alterarSituacaoPreCupom(int idUsuario, SituacaoEstoque situacaoEstoque) throws SQLException {
		openConnection();
		StringBuilder sql;
		PreparedStatement pst = null;
		
		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			
			sql.append("UPDATE precupom SET iSituacaoEstoque = ? WHERE iUsuario = ?");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setInt(1, situacaoEstoque.getCodigo());
			pst.setInt(2, idUsuario);
			
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
}