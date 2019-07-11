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
import br.com.ecolivros.dominio.Carrinho;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.enuns.SituacaoEstoque;

public class CarrinhoDAO extends AbstractJdbcDAO {

	public CarrinhoDAO() {
		super("carrinho", "id");
	}
	
	public CarrinhoDAO(Connection connection) {
		super(connection, "carrinho", "id");
		ctrlTransaction = false;
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		StringBuilder sql;
		PreparedStatement pst = null;
		
		Carrinho carrinho = (Carrinho) entidade;

		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			sql.append("INSERT INTO carrinho (iLivro, iUsuario, nQtde, iSituacaoEstoque, dtCadastro) ");
			sql.append("VALUES (?,?,?,?,?)");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setInt(1, carrinho.getIdLivro());
			pst.setInt(2, carrinho.getIdUsuario());
			pst.setInt(3, carrinho.getQtde());
			pst.setInt(4, carrinho.getSituacaoEstoque().getCodigo());
			
			Timestamp time = new Timestamp(carrinho.getDtCadastro().getTime());
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
		
		Carrinho carrinho = (Carrinho) entidade;
		
		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			
			sql.append("UPDATE carrinho SET nQtde = ? WHERE id = ?");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setInt(1, carrinho.getQtde());
			pst.setInt(2, carrinho.getId());
			
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
		List<EntidadeDominio> carrinhos = new	ArrayList<EntidadeDominio>();
		
		Carrinho carrinho = (Carrinho) entidade;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM carrinho");
		sql.append(" WHERE iUsuario = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, carrinho.getIdUsuario());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idLivro = rs.getInt("iLivro");
			int idUsuario = rs.getInt("iUsuario");
			int qtde = rs.getInt("nQtde");
			int situacaoEstoque = rs.getInt("iSituacaoEstoque");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			carrinho	= new Carrinho();
			carrinho.setId(id);
			carrinho.setIdLivro(idLivro);
			carrinho.setIdUsuario(idUsuario);
			carrinho.setQtde(qtde);
			carrinho.setDtCadastro(dtCadastro);
			
			for (SituacaoEstoque sitEstoque : SituacaoEstoque.values()) {
				if (sitEstoque.getCodigo() == situacaoEstoque) {
					carrinho.setSituacaoEstoque(sitEstoque);
					break;
				}
			}
			carrinhos.add(carrinho);
		}
		
		rs.close();
		pst.close();
		if (ctrlTransaction)
			connection.close();
		
		return carrinhos;
	}
	
	public List<EntidadeDominio> getLista() throws SQLException {
		openConnection();
		PreparedStatement pst;
		List<EntidadeDominio> carrinhos = new	ArrayList<EntidadeDominio>();
		Carrinho carrinho = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM carrinho");
		sql.append(" WHERE iUsuario = ? AND iSituacaoEstoque IN (?, ?)");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, UsuarioLogado.getUsuario().getId());
		pst.setInt(2, SituacaoEstoque.PENDENTE.getCodigo());
		pst.setInt(3, SituacaoEstoque.PARCIAL.getCodigo());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idLivro = rs.getInt("iLivro");
			int idUsuario = rs.getInt("iUsuario");
			int qtde = rs.getInt("nQtde");
			int situacaoEstoque = rs.getInt("iSituacaoEstoque");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			carrinho	= new Carrinho();
			carrinho.setId(id);
			carrinho.setIdLivro(idLivro);
			carrinho.setIdUsuario(idUsuario);
			carrinho.setQtde(qtde);
			carrinho.setDtCadastro(dtCadastro);
			
			for (SituacaoEstoque sitEstoque : SituacaoEstoque.values()) {
				if (sitEstoque.getCodigo() == situacaoEstoque) {
					carrinho.setSituacaoEstoque(sitEstoque);
					break;
				}
			}
			carrinhos.add(carrinho);
		}
		
		rs.close();
		pst.close();
		if (ctrlTransaction)
			connection.close();
		
		return carrinhos;
	}
	
	public List<Carrinho> getCarrinhoByIdUsuarioSituacao(SituacaoEstoque situacaoEstoque) throws SQLException {
		openConnection();
		PreparedStatement pst;
		List<Carrinho> carrinhos = new	ArrayList<Carrinho>();
		Carrinho carrinho = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM carrinho");
		sql.append(" WHERE iUsuario = ? AND iSituacaoEstoque IN (?)");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, UsuarioLogado.getUsuario().getId());
		pst.setInt(2, situacaoEstoque.getCodigo());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idLivro = rs.getInt("iLivro");
			int idUsuario = rs.getInt("iUsuario");
			int qtde = rs.getInt("nQtde");
			int iSituacaoEstoque = rs.getInt("iSituacaoEstoque");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			carrinho	= new Carrinho();
			carrinho.setId(id);
			carrinho.setIdLivro(idLivro);
			carrinho.setIdUsuario(idUsuario);
			carrinho.setQtde(qtde);
			carrinho.setDtCadastro(dtCadastro);
			
			for (SituacaoEstoque sitEstoque : SituacaoEstoque.values()) {
				if (sitEstoque.getCodigo() == iSituacaoEstoque) {
					carrinho.setSituacaoEstoque(sitEstoque);
					break;
				}
			}
			carrinhos.add(carrinho);
		}
		
		rs.close();
		pst.close();
		if (ctrlTransaction)
			connection.close();
		
		return carrinhos;
	}
	
	public boolean jaExisteLivroCarrinho(int idLivro) throws SQLException {
		openConnection();
		PreparedStatement pst;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM carrinho");
		sql.append(" WHERE iUsuario = ? AND iLivro = ? AND iSituacaoEstoque != ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, UsuarioLogado.getUsuario().getId());
		pst.setInt(2, idLivro);
		pst.setInt(3, SituacaoEstoque.BAIXADO.getCodigo());
		
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()) {
			return true;
		}
		
		rs.close();
		pst.close();
		if (ctrlTransaction)
			connection.close();
		
		return false;
	}
	
	public void alterarSituacaoEstoqueByIdCarrinhoSituacaoEstoque(int idCarrinho, SituacaoEstoque situacaoEstoque) throws SQLException {
		openConnection();
		StringBuilder sql;
		PreparedStatement pst = null;
		
		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			
			sql.append("UPDATE carrinho SET iSituacaoEstoque = ? WHERE id = ?");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setInt(1, situacaoEstoque.getCodigo());
			pst.setInt(2, idCarrinho);
			
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
	
	public Carrinho getCarrinhoById(int idCarrinho) throws SQLException {
		openConnection();
		PreparedStatement pst;
		Carrinho carrinho = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM carrinho");
		sql.append(" WHERE id = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, idCarrinho);
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idLivro = rs.getInt("iLivro");
			int idUsuario = rs.getInt("iUsuario");
			int qtde = rs.getInt("nQtde");
			int situacaoEstoque = rs.getInt("iSituacaoEstoque");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			carrinho	= new Carrinho();
			carrinho.setId(id);
			carrinho.setIdLivro(idLivro);
			carrinho.setIdUsuario(idUsuario);
			carrinho.setQtde(qtde);
			carrinho.setDtCadastro(dtCadastro);
			
			for (SituacaoEstoque sitEstoque : SituacaoEstoque.values()) {
				if (sitEstoque.getCodigo() == situacaoEstoque) {
					carrinho.setSituacaoEstoque(sitEstoque);
					break;
				}
			}
		}
		
		rs.close();
		pst.close();
		if (ctrlTransaction)
			connection.close();
		
		return carrinho;
	}
}