package br.com.ecolivros.core.impl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Estoque;
import br.com.ecolivros.enuns.SituacaoEstoque;

public class EstoqueDAO extends AbstractJdbcDAO {

	public EstoqueDAO() {
		super("estoque", "id");
	}
	
	public EstoqueDAO(Connection connection) {
		super(connection, "estoque", "id");
		ctrlTransaction = false;
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		StringBuilder sql;
		PreparedStatement pst = null;
		
		Estoque estoque = (Estoque) entidade;

		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			sql.append("INSERT INTO estoque (iLivro, nValor, iSituacaoEstoque, dtCadastro) ");
			sql.append("VALUES (?,?,?,?)");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setInt(1, estoque.getIdLivro());
			pst.setDouble(2, estoque.getValor());
			pst.setInt(3, estoque.getSituacaoEstoque().getCodigo());
			
			Timestamp time = new Timestamp(estoque.getDtCadastro().getTime());
			pst.setTimestamp(4, time);

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
		openConnection();
		StringBuilder sql;
		PreparedStatement pst = null;
		
		Estoque estoque = (Estoque) entidade;
		
		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			
			sql.append("UPDATE estoque SET iSituacaoEstoque = ? WHERE id = ?");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setInt(1, estoque.getSituacaoEstoque().getCodigo());
			pst.setInt(2, estoque.getId());
			
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
		List<EntidadeDominio> estoques = new ArrayList<EntidadeDominio>();
		
		Estoque estoque = (Estoque) entidade;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM estoque WHERE iLivro = ? ORDER BY dtCadastro");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, estoque.getIdLivro());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idLivro = rs.getInt("iLivro");
			double valor = rs.getInt("nValor");
			int iSituacaoEstoque = rs.getInt("iSituacaoEstoque");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			estoque	= new Estoque();
			estoque.setId(id);
			estoque.setIdLivro(idLivro);
			estoque.setValor(valor);
			estoque.setDtCadastro(dtCadastro);
			
			for (SituacaoEstoque situacaoEstoque : SituacaoEstoque.values()) {
				if (iSituacaoEstoque == situacaoEstoque.getCodigo()) {
					estoque.setSituacaoEstoque(situacaoEstoque);
					break;
				}
			}
			estoques.add(estoque);
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return estoques;
	}
	
	public List<EntidadeDominio> getLista() throws SQLException {
		openConnection();
		PreparedStatement pst;
		List<EntidadeDominio> estoques = new ArrayList<EntidadeDominio>();
		
		Estoque estoque = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM estoque ORDER BY dtCadastro");
		
		pst = connection.prepareStatement(sql.toString());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idLivro = rs.getInt("iLivro");
			double valor = rs.getInt("nValor");
			int iSituacaoEstoque = rs.getInt("iSituacaoEstoque");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			estoque	= new Estoque();
			estoque.setId(id);
			estoque.setIdLivro(idLivro);
			estoque.setValor(valor);
			estoque.setDtCadastro(dtCadastro);
			
			for (SituacaoEstoque situacaoEstoque : SituacaoEstoque.values()) {
				if (iSituacaoEstoque == situacaoEstoque.getCodigo()) {
					estoque.setSituacaoEstoque(situacaoEstoque);
					break;
				}
			}
			estoques.add(estoque);
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return estoques;
	}
	
	public void salvarList(List<Estoque> estoques) throws SQLException {
		openConnection();
		boolean isSecondOrMore;
		StringBuilder sql;
		PreparedStatement pst = null;
		
		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			sql.append("INSERT INTO estoque (iLivro, nValor, iSituacaoEstoque, dtCadastro) VALUES ");
			isSecondOrMore = false;
			
			for (Estoque estoque : estoques) {
				if (isSecondOrMore) {
					sql.append(",");
				}
				
				sql.append("(" + estoque.getIdLivro() + ", " + estoque.getValor() + ", " + estoque.getSituacaoEstoque().getCodigo() + ", current_date())");
				isSecondOrMore = true;
			}
			
			pst = connection.prepareStatement(sql.toString());
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
	
	public Estoque[] addListByIdLivroTop(int idLivro, int limit) throws SQLException {
		openConnection();
		PreparedStatement pst;
		int i = 0;
		Estoque estoque = null;
		Estoque[] estoques = new Estoque[limit];
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM estoque WHERE iLivro = ? AND iSituacaoEstoque = ? ORDER BY dtCadastro LIMIT ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, idLivro);
		pst.setInt(2, SituacaoEstoque.PENDENTE.getCodigo());
		pst.setInt(3, limit);
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int iLivro = rs.getInt("iLivro");
			double valor = rs.getInt("nValor");
			int iSituacaoEstoque = rs.getInt("iSituacaoEstoque");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			estoque	= new Estoque();
			estoque.setId(id);
			estoque.setIdLivro(iLivro);
			estoque.setValor(valor);
			estoque.setDtCadastro(dtCadastro);
			
			for (SituacaoEstoque situacaoEstoque : SituacaoEstoque.values()) {
				if (iSituacaoEstoque == situacaoEstoque.getCodigo()) {
					estoque.setSituacaoEstoque(situacaoEstoque);
					break;
				}
			}
			
			estoques[i++] = estoque;
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return estoques;
	}
	
	public boolean getExisteByIdLivroSituacao(int idLivro, SituacaoEstoque situacaoEstoque) throws SQLException {
		openConnection();
		PreparedStatement pst;
		boolean existeEstoque = false;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT MAX(id) AS id FROM estoque WHERE iLivro = ? AND iSituacaoEstoque = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, idLivro);
		pst.setInt(2, situacaoEstoque.getCodigo());
		
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()) {
			String id =  rs.getString("id");
			if (id != null) {
				int idEstoque = Integer.parseInt(id);
				if (idEstoque > 0) {
					existeEstoque  = true;
				}
			}
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return existeEstoque;
	}
	
	public Integer getQtdeEstoqueByIdLivroSituacaoEstoque(int idLivro, SituacaoEstoque situacaoEstoque) throws SQLException {
		openConnection();
		PreparedStatement pst;
		int qtdeEstoque = 0;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(id) AS iQtdeEstoque FROM estoque WHERE iLivro = ? AND iSituacaoEstoque = ? ORDER BY dtCadastro");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, idLivro);
		pst.setInt(2, situacaoEstoque.getCodigo());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			qtdeEstoque = rs.getInt("iQtdeEstoque");
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return qtdeEstoque;
	}
	
	public Double getMediaValorByIdLivro(int idLivro) throws SQLException {
		openConnection();
		PreparedStatement pst;
		double mediaValor = 0;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("e.iLivro, ");
		sql.append("SUM(e.nValor) / COUNT(e.id) AS nMediaValor ");
		sql.append("FROM estoque AS e ");
		sql.append("WHERE e.iSituacaoEstoque = ? AND iLivro = ? ");
		sql.append("GROUP BY e.iLivro");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, SituacaoEstoque.PENDENTE.getCodigo());
		pst.setInt(2, idLivro);
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			mediaValor = rs.getInt("nMediaValor");
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return mediaValor;
	}
}