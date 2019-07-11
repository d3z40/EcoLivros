package br.com.ecolivros.core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Fornecedor;
import br.com.ecolivros.dominio.ItemNota;
import br.com.ecolivros.dominio.Nota;
import br.com.ecolivros.enuns.SituacaoEstoque;

public class NotaDAO extends AbstractJdbcDAO {

	public NotaDAO() {
		super("nota", "id");
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		int idNota;
		
		Nota nota = (Nota) entidade;
		
		try {
			connection.setAutoCommit(false);

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO nota (iFornecedor, sNumeroNota, iSituacaoEstoque, dtCadastro) ");
			sql.append("VALUES (?,?,?,?)");
			
			pst = connection.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			
			pst.setInt(1, nota.getFornecedor().getId());
			pst.setString(2, nota.getNumeroNota());
			pst.setInt(3, nota.getSituacaoEstoque().getCodigo());
			
			Timestamp time = new Timestamp(nota.getDtCadastro().getTime());
			pst.setTimestamp(4, time);
			
			pst.executeUpdate();
			
			ResultSet rs = pst.getGeneratedKeys();
			
			if (rs.next()) {
				idNota = rs.getInt(1);
				
				for (ItemNota itemNota : nota.getItensNota()) {
					itemNota.setIdNota(idNota);
					
					new ItemNotaDAO(connection).salvar(itemNota);
				}
			}
			
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
				if (pst1 != null)
					pst1.close();
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
		PreparedStatement pst1 = null;
		
		Nota nota = (Nota) entidade;
		
		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			
			sql.append("UPDATE nota SET iFornecedor = ?, sNumeroNota = ?, iSituacaoEstoque = ?, dtCadastro = ? WHERE id = ?");
			
			pst = connection.prepareStatement(sql.toString());
			pst.setInt(1, nota.getFornecedor().getId());
			pst.setString(2, nota.getNumeroNota());
			pst.setInt(3, nota.getSituacaoEstoque().getCodigo());
			
			Timestamp time = new Timestamp(nota.getDtCadastro().getTime());
			pst.setTimestamp(4, time);
			
			pst.setInt(5, nota.getId());
			
			pst.executeUpdate();
			
			for (ItemNota itemNota : nota.getItensNota()) {
				if (itemNota.getId() != null) {
					new ItemNotaDAO(connection).alterar(itemNota);
				} else if (itemNota.getIdNota() != null) {
					new ItemNotaDAO(connection).salvar(itemNota);
				}
			}
			
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
				
				if (pst1 != null)
					pst1.close();
				
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
		List<EntidadeDominio> notas = new ArrayList<EntidadeDominio>();
		
		Nota nota = (Nota) entidade;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM nota");
		sql.append(" WHERE id = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, nota.getId());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idFornecedor = rs.getInt("iFornecedor");
			String numeroNota = rs.getString("sNumeroNota");
			int situacaoEstoque = rs.getInt("iSituacaoEstoque");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			nota = new Nota();
			nota.setId(id);
			nota.setFornecedor((Fornecedor) new FornecedorDAO().getEntidadeDominio(idFornecedor));
			nota.setNumeroNota(numeroNota);
			nota.setDtCadastro(dtCadastro);
//			nota.setItensNota(new ItemNotaDAO().getListaByNota(id));
			
			for (SituacaoEstoque situacaoEst : SituacaoEstoque.values()) {
				if (situacaoEst.getCodigo() == situacaoEstoque)
					nota.setSituacaoEstoque(situacaoEst);
			}
			
			notas.add(nota);
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return notas;
	}
	
	/* (non-Javadoc)
	 * @see br.com.ecolivros.core.impl.dao.AbstractJdbcDAO#excluir(br.com.ecolivros.dominio.EntidadeDominio)
	 */
	@Override
	public void excluir(EntidadeDominio entidade) {
		Nota nota = (Nota) entidade;
		
		if (nota.getItensNota() != null && !nota.getItensNota().isEmpty()) {
			for (ItemNota itemNota : nota.getItensNota()) {
				new ItemNotaDAO().excluir(itemNota);
			}
		} else {
			try {
				nota.setItensNota(new ItemNotaDAO().getListaByNota(nota.getId()));
				for (ItemNota itemNota : nota.getItensNota()) {
					new ItemNotaDAO().excluir(itemNota);
				}
				super.excluir(entidade);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public EntidadeDominio getEntidadeDominio(EntidadeDominio entidade) throws SQLException {
		openConnection();
		PreparedStatement pst;
		
		Nota nota = (Nota) entidade;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM nota");
		sql.append(" WHERE id = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, nota.getId());
		
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()) {
			int id = rs.getInt("id");
			int idFornecedor = rs.getInt("iFornecedor");
			String numeroNota = rs.getString("sNumeroNota");
			int situacaoEstoque = rs.getInt("iSituacaoEstoque");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			nota = new Nota();
			nota.setId(id);
			nota.setFornecedor((Fornecedor) new FornecedorDAO().getEntidadeDominio(idFornecedor));
			nota.setNumeroNota(numeroNota);
			nota.setDtCadastro(dtCadastro);
			
			for (SituacaoEstoque situacaoEst : SituacaoEstoque.values()) {
				if (situacaoEst.getCodigo() == situacaoEstoque)
					nota.setSituacaoEstoque(situacaoEst);
			}
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return nota;
	}
	
	public EntidadeDominio getEntidadeDominio(int idNota) throws SQLException {
		openConnection();
		PreparedStatement pst;
		
		Nota nota = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM nota");
		sql.append(" WHERE id = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, idNota);
		
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()) {
			int id = rs.getInt("id");
			int idFornecedor = rs.getInt("iFornecedor");
			String numeroNota = rs.getString("sNumeroNota");
			int situacaoEstoque = rs.getInt("iSituacaoEstoque");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			nota = new Nota();
			nota.setId(id);
			nota.setFornecedor((Fornecedor) new FornecedorDAO().getEntidadeDominio(idFornecedor));
			nota.setNumeroNota(numeroNota);
			nota.setDtCadastro(dtCadastro);
			
			for (SituacaoEstoque situacaoEst : SituacaoEstoque.values()) {
				if (situacaoEst.getCodigo() == situacaoEstoque)
					nota.setSituacaoEstoque(situacaoEst);
			}
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return nota;
	}
	
	public List<EntidadeDominio> getLista(String filter) throws SQLException {
		openConnection();
		List<EntidadeDominio> notas = new	ArrayList<EntidadeDominio>();
		Nota nota = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM nota");
		if (!filter.equals("")) {
			sql.append(" WHERE sNumeroNota LIKE '%" + filter + "%'");
		}
		
		PreparedStatement stmt = connection.prepareStatement(sql.toString());
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idFornecedor = rs.getInt("iFornecedor");
			String numeroNota = rs.getString("sNumeroNota");
			int situacaoEstoque = rs.getInt("iSituacaoEstoque");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			nota = new Nota();
			nota.setId(id);
			nota.setFornecedor((Fornecedor) new FornecedorDAO().getEntidadeDominio(idFornecedor));
			nota.setNumeroNota(numeroNota);
			nota.setDtCadastro(dtCadastro);
			
			for (SituacaoEstoque situacaoEst : SituacaoEstoque.values()) {
				if (situacaoEst.getCodigo() == situacaoEstoque)
					nota.setSituacaoEstoque(situacaoEst);
			}
			
			notas.add(nota);
		}
		
		rs.close();
		stmt.close();
		connection.close();
		
		return notas;
	}
	
	public List<EntidadeDominio> getLista() throws SQLException {
		return getLista("");
	}
	
	public Nota getSituacaoById(int idNota) throws SQLException {
		openConnection();
		PreparedStatement pst;
		
		Nota nota = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT iSituacaoEstoque FROM nota");
		sql.append(" WHERE id = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, idNota);
		
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()) {
			int situacaoEstoque = rs.getInt("iSituacaoEstoque");
			
			for (SituacaoEstoque situacaoEst : SituacaoEstoque.values()) {
				if (situacaoEst.getCodigo() == situacaoEstoque) {
					nota = new Nota();
					nota.setSituacaoEstoque(situacaoEst);
					break;
				}
			}
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return nota;
	}
	
	public void atualizarSituacaoEstoque(List<Integer> idsNota, SituacaoEstoque situacaoEstoque) {
		openConnection();
		StringBuilder sql;
		PreparedStatement pst = null;
		
		try {
			connection.setAutoCommit(false);
			
			for (Integer idNota : idsNota) {
				sql = new StringBuilder();
				sql.append("UPDATE nota SET iSituacaoEstoque = " + situacaoEstoque.getCodigo() + " WHERE id = " + idNota + ";");
				
				pst = connection.prepareStatement(sql.toString());
				pst.executeUpdate();
			}
			
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
}