package br.com.ecolivros.core.impl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ecolivros.core.util.ClonaList;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.ItemPedido;
import br.com.ecolivros.enuns.SituacaoItem;

public class ItemPedidoDAO extends AbstractJdbcDAO {

	public ItemPedidoDAO() {
		super("itempedido", "id");
	}
	
	public ItemPedidoDAO(Connection connection) {
		super(connection, "itempedido", "id");
		ctrlTransaction = false;
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		StringBuilder sql;
		PreparedStatement pst = null;
		
		ItemPedido itemPedido = (ItemPedido) entidade;

		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			sql.append("INSERT INTO itempedido (iPedido, iEstoque, iLivro, nQtde, nValor, iSituacaoItem, dtCadastro) ");
			sql.append("VALUES (?,?,?,?,?,?,?)");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setInt(1, itemPedido.getIdPedido());
			pst.setInt(2, itemPedido.getIdEstoque());
			pst.setInt(3, itemPedido.getIdLivro());
			pst.setInt(4, itemPedido.getQtde());
			pst.setDouble(5, itemPedido.getValor());
			pst.setInt(6, SituacaoItem.ENTREGUE.getCodigo());
			
			Timestamp time = new Timestamp(itemPedido.getDtCadastro().getTime());
			pst.setTimestamp(7, time);

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
		
		ItemPedido itemPedido = (ItemPedido) entidade;
		
		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			
			sql.append("UPDATE itempedido SET iSituacaoItem = ? WHERE id = ?");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setInt(1, SituacaoItem.AGUARDANDO.getCodigo());
			pst.setInt(2, itemPedido.getId());
			
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
		List<EntidadeDominio> itensPedido = new	ArrayList<EntidadeDominio>();
		
		ItemPedido itemPedido = (ItemPedido) entidade;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM itempedido");
		sql.append(" WHERE iPedido = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, itemPedido.getIdPedido());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idPedido = rs.getInt("iPedido");
			int idLivro = rs.getInt("iLivro");
			int idEstoque = rs.getInt("iEstoque");
			int qtde = rs.getInt("nQtde");
			double valor = rs.getDouble("nValor");
			int iSituacaoItem = rs.getInt("iSituacaoItem");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			itemPedido	= new ItemPedido();
			itemPedido.setId(id);
			itemPedido.setIdPedido(idPedido);
			itemPedido.setIdLivro(idLivro);
			itemPedido.setIdEstoque(idEstoque);
			itemPedido.setQtde(qtde);
			itemPedido.setValor(valor);
			itemPedido.setDtCadastro(dtCadastro);
			
			for (SituacaoItem situacaoItem : SituacaoItem.values()) {
				if (iSituacaoItem == situacaoItem.getCodigo()) {
					itemPedido.setSituacaoItem(situacaoItem);
					break;
				}
			}
			
			itensPedido.add(itemPedido);
		}
		
		rs.close();
		pst.close();
		if (ctrlTransaction)
			connection.close();
		
		return itensPedido;
	}
	
	public List<ItemPedido> getItemPedidoIdPedido(int iPedido) throws SQLException {
		openConnection();
		PreparedStatement pst;
		List<ItemPedido> itensPedido = new	ArrayList<ItemPedido>();
		
		ItemPedido itemPedido = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM itempedido");
		sql.append(" WHERE iPedido = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, iPedido);
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idPedido = rs.getInt("iPedido");
			int idLivro = rs.getInt("iLivro");
			int idEstoque = rs.getInt("iEstoque");
			int qtde = rs.getInt("nQtde");
			double valor = rs.getDouble("nValor");
			int iSituacaoItem = rs.getInt("iSituacaoItem");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			itemPedido	= new ItemPedido();
			itemPedido.setId(id);
			itemPedido.setIdPedido(idPedido);
			itemPedido.setIdLivro(idLivro);
			itemPedido.setIdEstoque(idEstoque);
			itemPedido.setQtde(qtde);
			itemPedido.setValor(valor);
			itemPedido.setDtCadastro(dtCadastro);
			
			for (SituacaoItem situacaoItem : SituacaoItem.values()) {
				if (iSituacaoItem == situacaoItem.getCodigo()) {
					itemPedido.setSituacaoItem(situacaoItem);
					break;
				}
			}
			
			itensPedido.add(itemPedido);
		}
		
		rs.close();
		pst.close();
		if (ctrlTransaction)
			connection.close();
		
		return itensPedido;
	}
	
	public List<ItemPedido> organizaItensCarrinho(List<ItemPedido> itensPedido) throws SQLException {
		ItemPedido itemPedidoTemp;
		List<Integer> idsLivro = new ArrayList<Integer>();
		List<ItemPedido> itensPedidoOrganizado = new ArrayList<ItemPedido>();
		
		List<ItemPedido> itensPedidoCopia = ClonaList.clonaListItemPedido(itensPedido);
		
		for (ItemPedido itemPedido : itensPedidoCopia) {
			if (!idsLivro.contains(itemPedido.getIdLivro())) {
				idsLivro.add(itemPedido.getIdLivro());
				itensPedidoOrganizado.add(itemPedido);
			} else {
				int index = idsLivro.indexOf(itemPedido.getIdLivro());
				itemPedidoTemp = itensPedidoOrganizado.get(index);
				itemPedidoTemp.setQtde(itemPedidoTemp.getQtde() + itemPedido.getQtde());
			}
		}
		
		return itensPedidoOrganizado;
	}
	
	public List<ItemPedido> getItensById(int idItemPedido) throws SQLException {
		openConnection();
		PreparedStatement pst;
		List<ItemPedido> itensPedido = new	ArrayList<ItemPedido>();
		
		ItemPedido itemPedido = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM itempedido");
		sql.append(" WHERE id = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, idItemPedido);
		
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()) {
			int idPedido = rs.getInt("iPedido");
			int idLivro = rs.getInt("iLivro");
			
			itemPedido	= new ItemPedido();
			itemPedido.setIdPedido(idPedido);
			itemPedido.setIdLivro(idLivro);
		}
		
		rs.close();
		pst.close();
		
		if (itemPedido != null) {
			sql = new StringBuilder();
			sql.append("SELECT * FROM itempedido");
			sql.append(" WHERE iPedido = ? AND iLivro = ?");
			
			pst = connection.prepareStatement(sql.toString());
			pst.setInt(1, itemPedido.getIdPedido());
			pst.setInt(2, itemPedido.getIdLivro());
			
			rs = pst.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				int idPedido = rs.getInt("iPedido");
				int idLivro = rs.getInt("iLivro");
				int idEstoque = rs.getInt("iEstoque");
				int qtde = rs.getInt("nQtde");
				double valor = rs.getDouble("nValor");
				int iSituacaoItem = rs.getInt("iSituacaoItem");
				Date dtCadastro = rs.getDate("dtCadastro");
				
				itemPedido	= new ItemPedido();
				itemPedido.setId(id);
				itemPedido.setIdPedido(idPedido);
				itemPedido.setIdLivro(idLivro);
				itemPedido.setIdEstoque(idEstoque);
				itemPedido.setQtde(qtde);
				itemPedido.setValor(valor);
				itemPedido.setDtCadastro(dtCadastro);
				
				for (SituacaoItem situacaoItem : SituacaoItem.values()) {
					if (iSituacaoItem == situacaoItem.getCodigo()) {
						itemPedido.setSituacaoItem(situacaoItem);
						break;
					}
				}
				
				itensPedido.add(itemPedido);
			}
			
			rs.close();
			pst.close();
			if (ctrlTransaction)
				connection.close();	
		}
		
		return itensPedido;
	}
	
	public ItemPedido getItemPedidoById(int idItemPedido) throws SQLException {
		openConnection();
		PreparedStatement pst;
		ItemPedido itemPedido = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM itempedido");
		sql.append(" WHERE id = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, idItemPedido);
		
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()) {
			int id = rs.getInt("id");
			int idPedido = rs.getInt("iPedido");
			int idLivro = rs.getInt("iLivro");
			int idEstoque = rs.getInt("iEstoque");
			int qtde = rs.getInt("nQtde");
			double valor = rs.getDouble("nValor");
			int iSituacaoItem = rs.getInt("iSituacaoItem");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			itemPedido	= new ItemPedido();
			itemPedido.setId(id);
			itemPedido.setIdPedido(idPedido);
			itemPedido.setIdLivro(idLivro);
			itemPedido.setIdEstoque(idEstoque);
			itemPedido.setQtde(qtde);
			itemPedido.setValor(valor);
			itemPedido.setDtCadastro(dtCadastro);
			
			for (SituacaoItem situacaoItem : SituacaoItem.values()) {
				if (iSituacaoItem == situacaoItem.getCodigo()) {
					itemPedido.setSituacaoItem(situacaoItem);
					break;
				}
			}
		}
		
		rs.close();
		pst.close();
		if (ctrlTransaction)
			connection.close();	
	
		return itemPedido;
	}
	
	public void alterarSituacaoItem(int idItemPedido, SituacaoItem situacaoItem) throws SQLException {
		openConnection();
		StringBuilder sql;
		PreparedStatement pst = null;
		
		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			
			sql.append("UPDATE itempedido SET iSituacaoItem = ? WHERE id = ?");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setInt(1, situacaoItem.getCodigo());
			pst.setInt(2, idItemPedido);
			
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