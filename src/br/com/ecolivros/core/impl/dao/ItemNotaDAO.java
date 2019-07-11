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
import br.com.ecolivros.dominio.ItemNota;
import br.com.ecolivros.dominio.Livro;
import br.com.ecolivros.enuns.SituacaoEstoque;

public class ItemNotaDAO extends AbstractJdbcDAO {

	public ItemNotaDAO() {
		super("itemnota", "id");
	}

	public ItemNotaDAO(Connection connection) {
		super(connection, "itemnota", "id");
		ctrlTransaction = false;
	}
	
	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		PreparedStatement pst = null;
		
		ItemNota itemNota = (ItemNota) entidade;
		
		try {
			connection.setAutoCommit(false);

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO itemnota (iNota, iLivro, iQtde, nValor, iSituacaoEstoque, dtCadastro) ");
			sql.append("VALUES (?,?,?,?,?,?)");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setInt(1, itemNota.getIdNota());
			pst.setInt(2, itemNota.getLivro().getId());
			pst.setInt(3, itemNota.getQtde());
			pst.setDouble(4, itemNota.getValor());
			pst.setInt(5, itemNota.getSituacaoEstoque().getCodigo());
			
			Timestamp time = new Timestamp(itemNota.getDtCadastro().getTime());
			pst.setTimestamp(6, time);
			
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
		
		ItemNota itemNota = (ItemNota) entidade;
		
		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			
			sql.append("UPDATE itemnota SET iLivro = ?, iQtde = ?, nValor = ?, iSituacaoEstoque = ?, dtCadastro = ? WHERE id = ?");
			
			pst = connection.prepareStatement(sql.toString());
			pst.setInt(1, itemNota.getLivro().getId());
			pst.setInt(2, itemNota.getQtde());
			pst.setDouble(3, itemNota.getValor());
			pst.setInt(4, itemNota.getSituacaoEstoque().getCodigo());
			
			Timestamp time = new Timestamp(itemNota.getDtCadastro().getTime());
			pst.setTimestamp(5, time);
			
			pst.setInt(6, itemNota.getId());
			
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
		List<EntidadeDominio> itensNota = new ArrayList<EntidadeDominio>();
		
		ItemNota itemNota = (ItemNota) entidade;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM itemnota");
		sql.append(" WHERE iNota = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, itemNota.getIdNota());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idNota = rs.getInt("iNota");
			int idLivro = rs.getInt("iLivro");
			int qtde = rs.getInt("iQtde");
			double valor = rs.getDouble("nValor");
			int situacaoEstoque = rs.getInt("iSituacaoEstoque");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			itemNota	= new ItemNota();
			itemNota.setId(id);
			itemNota.setIdNota(idNota);
			itemNota.setLivro((Livro) new LivroDAO().getEntidadeDominio(idLivro));
			itemNota.setQtde(qtde);
			itemNota.setValor(valor);
			itemNota.setDtCadastro(dtCadastro);
			
			for (SituacaoEstoque situacaoEst : SituacaoEstoque.values()) {
				if (situacaoEst.getCodigo() == situacaoEstoque)
					itemNota.setSituacaoEstoque(situacaoEst);
			}
			
			itensNota.add(itemNota);
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return itensNota;
	}
	
	/* (non-Javadoc)
	 * @see br.com.ecolivros.core.impl.dao.AbstractJdbcDAO#excluir(br.com.ecolivros.dominio.EntidadeDominio)
	 */
	@Override
	public void excluir(EntidadeDominio entidade) {
		super.excluir(entidade);
	}
	
	public List<ItemNota> getListaByNota(int iNota) throws SQLException {
		openConnection();
		PreparedStatement pst;
		List<ItemNota> itensNota = new ArrayList<ItemNota>();
		ItemNota itemNota = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM itemnota");
		sql.append(" WHERE iNota = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, iNota);
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idNota = rs.getInt("iNota");
			int idLivro = rs.getInt("iLivro");
			int qtde = rs.getInt("iQtde");
			double valor = rs.getDouble("nValor");
			int situacaoEstoque = rs.getInt("iSituacaoEstoque");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			itemNota = new ItemNota();
			itemNota.setId(id);
			itemNota.setIdNota(idNota);
			itemNota.setLivro((Livro) new LivroDAO().getEntidadeDominio(idLivro));
			itemNota.setQtde(qtde);
			itemNota.setValor(valor);
			itemNota.setDtCadastro(dtCadastro);
			
			for (SituacaoEstoque situacaoEst : SituacaoEstoque.values()) {
				if (situacaoEst.getCodigo() == situacaoEstoque)
					itemNota.setSituacaoEstoque(situacaoEst);
			}
			
			itensNota.add(itemNota);
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return itensNota;
	}
	
	public ItemNota getSituacaoById(int id) throws SQLException {
		openConnection();
		PreparedStatement pst;
		ItemNota itemNota = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT iSituacaoEstoque FROM itemnota");
		sql.append(" WHERE id = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, id);
		
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()) {
			int situacaoEstoque = rs.getInt("iSituacaoEstoque");
			
			for (SituacaoEstoque situacaoEst : SituacaoEstoque.values()) {
				if (situacaoEst.getCodigo() == situacaoEstoque) {
					itemNota = new ItemNota();
					itemNota.setSituacaoEstoque(situacaoEst);
					break;
				}
			}
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return itemNota;
	}
	
	public List<ItemNota> getListaBySituacaoEstoque(SituacaoEstoque situacaoEstoque) throws SQLException {
		openConnection();
		PreparedStatement pst;
		List<ItemNota> itensNota = new ArrayList<ItemNota>();
		ItemNota itemNota = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM itemnota");
		sql.append(" WHERE iSituacaoEstoque = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, situacaoEstoque.getCodigo());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idNota = rs.getInt("iNota");
			int idLivro = rs.getInt("iLivro");
			int qtde = rs.getInt("iQtde");
			double valor = rs.getDouble("nValor");
			int iSituacaoEstoque = rs.getInt("iSituacaoEstoque");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			itemNota = new ItemNota();
			itemNota.setId(id);
			itemNota.setIdNota(idNota);
			itemNota.setLivro((Livro) new LivroDAO().getEntidadeDominio(idLivro));
			itemNota.setQtde(qtde);
			itemNota.setValor(valor);
			itemNota.setDtCadastro(dtCadastro);
			
			for (SituacaoEstoque situacaoEst : SituacaoEstoque.values()) {
				if (situacaoEst.getCodigo() == iSituacaoEstoque)
					itemNota.setSituacaoEstoque(situacaoEst);
			}
			
			itensNota.add(itemNota);
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return itensNota;
	}
	
	public void atualizarSituacaoEstoque(List<ItemNota> itensNota) {
//		openConnection();
//		StringBuilder sql;
//		PreparedStatement pst = null;
		
		try {
//			connection.setAutoCommit(false);
			
//			sql = new StringBuilder();
			for (ItemNota itemNota : itensNota) {
				alterar(itemNota);
//				sql.append("UPDATE itemnota SET iSituacaoEstoque = " + itemNota.getSituacaoEstoque().getCodigo() + " WHERE id = " + itemNota.getId() + ";\n");
			}
			
//			pst = connection.prepareStatement(sql.toString());
//			pst.executeUpdate();
//			
//			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
//			try {
//				pst.close();
//				connection.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
		}
	}
}