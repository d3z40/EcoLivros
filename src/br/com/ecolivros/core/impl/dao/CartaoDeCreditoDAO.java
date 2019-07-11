package br.com.ecolivros.core.impl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ecolivros.dominio.CartaoDeCredito;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.IPagamento;
import br.com.ecolivros.dominio.Pagamento;
import br.com.ecolivros.enuns.BandeiraCartaoDeCredito;

public class CartaoDeCreditoDAO extends AbstractJdbcDAO implements IPagamento {

	public CartaoDeCreditoDAO() {
		super("cartaodecredito", "id");
	}
	
	public CartaoDeCreditoDAO(Connection connection) {
		super(connection, "cartaodecredito", "id");
		ctrlTransaction = false;
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		StringBuilder sql;
		PreparedStatement pst = null;
		
		CartaoDeCredito cartaoDeCredito = (CartaoDeCredito) entidade;

		try {
			connection.setAutoCommit(false);

			sql = new StringBuilder();
			sql.append("INSERT INTO cartaodecredito (iUsuario, sNomeNoCartao, sNumeroDoCartao, " +
					"nMesValidade, nAnoValidade, nCodigoSeguranca, nBandeira, dtCadastro) ");
			sql.append("VALUES (?,?,?,?,?,?,?,?)");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setInt(1, cartaoDeCredito.getIdUsuario());
			pst.setString(2, cartaoDeCredito.getNomeNoCartao());
			pst.setString(3, cartaoDeCredito.getNumeroDoCartao());
			pst.setInt(4, cartaoDeCredito.getMesValidade());
			pst.setInt(5, cartaoDeCredito.getAnoValidade());
			pst.setInt(6, cartaoDeCredito.getCodigoSeguranca());
			pst.setInt(7, cartaoDeCredito.getBandeira().getCodigo());

			Timestamp time = new Timestamp(cartaoDeCredito.getDtCadastro().getTime());
			pst.setTimestamp(8, time);

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
		
		CartaoDeCredito cartaoDeCredito = (CartaoDeCredito) entidade;
		
		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			
			sql.append("UPDATE cartaodecredito SET sNomeNoCartao = ?, sNumeroDoCartao = ?, nMesValidade = ?, nAnoValidade = ?, " +
					"nCodigoSeguranca = ?, nBandeira = ?, dtCadastro = ? WHERE id = ?");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setString(1, cartaoDeCredito.getNomeNoCartao());
			pst.setString(2, cartaoDeCredito.getNumeroDoCartao());
			pst.setInt(3, cartaoDeCredito.getMesValidade());
			pst.setInt(4, cartaoDeCredito.getAnoValidade());
			pst.setInt(5, cartaoDeCredito.getCodigoSeguranca());
			pst.setInt(6, cartaoDeCredito.getBandeira().getCodigo());
			
			Timestamp time = new Timestamp(cartaoDeCredito.getDtCadastro().getTime());
			pst.setTimestamp(7, time);
			
			pst.setInt(8, cartaoDeCredito.getId());
			
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
		List<EntidadeDominio> cartaoDeCreditos = new	ArrayList<EntidadeDominio>();
		
		CartaoDeCredito cartaoDeCredito = (CartaoDeCredito) entidade;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM cartaodecredito");
		sql.append(" WHERE iUsuario = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, cartaoDeCredito.getIdUsuario());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idUsuario = rs.getInt("iUsuario");
			String nomeNoCartao = rs.getString("sNomeNoCartao");
			String numeroDoCartao = rs.getString("sNumeroDoCartao");
			int mesValidade = rs.getInt("nMesValidade");
			int anoValidade = rs.getInt("nAnoValidade");
			int codigoSeguranca = rs.getInt("nCodigoSeguranca");
			int bandeira = rs.getInt("nBandeira");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			cartaoDeCredito	= new CartaoDeCredito();
			cartaoDeCredito.setId(id);
			cartaoDeCredito.setIdUsuario(idUsuario);
			cartaoDeCredito.setNomeNoCartao(nomeNoCartao);
			cartaoDeCredito.setNumeroDoCartao(numeroDoCartao);
			cartaoDeCredito.setMesValidade(mesValidade);
			cartaoDeCredito.setAnoValidade(anoValidade);
			cartaoDeCredito.setCodigoSeguranca(codigoSeguranca);
			cartaoDeCredito.setDtCadastro(dtCadastro);
			
			for (BandeiraCartaoDeCredito band : BandeiraCartaoDeCredito.values()) {
				if (band.getCodigo() == bandeira) {
					cartaoDeCredito.setBandeira(band);
				}
			}
			
			cartaoDeCreditos.add(cartaoDeCredito);
		}

		rs.close();
		pst.close();
		if (ctrlTransaction)
			connection.close();
		
		return cartaoDeCreditos;
	}
	
	public List<EntidadeDominio> getEntidadeDominio(int idUsuario) throws SQLException {
		openConnection();
		PreparedStatement pst;
		List<EntidadeDominio> cartaoDeCreditos = new	ArrayList<EntidadeDominio>();
		
		CartaoDeCredito cartaoDeCredito = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM cartaodecredito");
		sql.append(" WHERE iUsuario = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, idUsuario);
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int iUsuario = rs.getInt("iUsuario");
			String nomeNoCartao = rs.getString("sNomeNoCartao");
			String numeroDoCartao = rs.getString("sNumeroDoCartao");
			int mesValidade = rs.getInt("nMesValidade");
			int anoValidade = rs.getInt("nAnoValidade");
			int codigoSeguranca = rs.getInt("nCodigoSeguranca");
			int bandeira = rs.getInt("nBandeira");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			cartaoDeCredito	= new CartaoDeCredito();
			cartaoDeCredito.setId(id);
			cartaoDeCredito.setIdUsuario(iUsuario);
			cartaoDeCredito.setNomeNoCartao(nomeNoCartao);
			cartaoDeCredito.setNumeroDoCartao(numeroDoCartao);
			cartaoDeCredito.setMesValidade(mesValidade);
			cartaoDeCredito.setAnoValidade(anoValidade);
			cartaoDeCredito.setCodigoSeguranca(codigoSeguranca);
			cartaoDeCredito.setDtCadastro(dtCadastro);
			
			for (BandeiraCartaoDeCredito band : BandeiraCartaoDeCredito.values()) {
				if (band.getCodigo() == bandeira) {
					cartaoDeCredito.setBandeira(band);
				}
			}
			
			cartaoDeCreditos.add(cartaoDeCredito);
		}

		rs.close();
		pst.close();
		if (ctrlTransaction)
			connection.close();
		
		return cartaoDeCreditos;
	}
	
	public List<CartaoDeCredito> getCartaoDeCreditoByUsuario(int iUsuario) throws SQLException {
		openConnection();
		PreparedStatement pst;
		List<CartaoDeCredito> cartaoDeCreditos = new ArrayList<CartaoDeCredito>();
		
		CartaoDeCredito cartaoDeCredito = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM cartaodecredito");
		sql.append(" WHERE iUsuario = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, iUsuario);
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idUsuario = rs.getInt("iUsuario");
			String nomeNoCartao = rs.getString("sNomeNoCartao");
			String numeroDoCartao = rs.getString("sNumeroDoCartao");
			int mesValidade = rs.getInt("nMesValidade");
			int anoValidade = rs.getInt("nAnoValidade");
			int codigoSeguranca = rs.getInt("nCodigoSeguranca");
			int bandeira = rs.getInt("nBandeira");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			cartaoDeCredito	= new CartaoDeCredito();
			cartaoDeCredito.setId(id);
			cartaoDeCredito.setIdUsuario(idUsuario);
			cartaoDeCredito.setNomeNoCartao(nomeNoCartao);
			cartaoDeCredito.setNumeroDoCartao(numeroDoCartao);
			cartaoDeCredito.setMesValidade(mesValidade);
			cartaoDeCredito.setAnoValidade(anoValidade);
			cartaoDeCredito.setCodigoSeguranca(codigoSeguranca);
			cartaoDeCredito.setDtCadastro(dtCadastro);
			
			for (BandeiraCartaoDeCredito band : BandeiraCartaoDeCredito.values()) {
				if (band.getCodigo() == bandeira) {
					cartaoDeCredito.setBandeira(band);
				}
			}
			
			cartaoDeCreditos.add(cartaoDeCredito);
		}

		rs.close();
		pst.close();
		if (ctrlTransaction)
			connection.close();
		
		return cartaoDeCreditos;
	}
	
	public EntidadeDominio getEntidadeDominioCartao(int idCartao) throws SQLException {
		openConnection();
		PreparedStatement pst;
		CartaoDeCredito cartaoDeCredito = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM cartaodecredito");
		sql.append(" WHERE id = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, idCartao);
		
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()) {
			int id = rs.getInt("id");
			int iUsuario = rs.getInt("iUsuario");
			String nomeNoCartao = rs.getString("sNomeNoCartao");
			String numeroDoCartao = rs.getString("sNumeroDoCartao");
			int mesValidade = rs.getInt("nMesValidade");
			int anoValidade = rs.getInt("nAnoValidade");
			int codigoSeguranca = rs.getInt("nCodigoSeguranca");
			int bandeira = rs.getInt("nBandeira");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			cartaoDeCredito	= new CartaoDeCredito();
			cartaoDeCredito.setId(id);
			cartaoDeCredito.setIdUsuario(iUsuario);
			cartaoDeCredito.setNomeNoCartao(nomeNoCartao);
			cartaoDeCredito.setNumeroDoCartao(numeroDoCartao);
			cartaoDeCredito.setMesValidade(mesValidade);
			cartaoDeCredito.setAnoValidade(anoValidade);
			cartaoDeCredito.setCodigoSeguranca(codigoSeguranca);
			cartaoDeCredito.setDtCadastro(dtCadastro);
			
			for (BandeiraCartaoDeCredito band : BandeiraCartaoDeCredito.values()) {
				if (band.getCodigo() == bandeira) {
					cartaoDeCredito.setBandeira(band);
				}
			}
		}

		rs.close();
		pst.close();
		if (ctrlTransaction)
			connection.close();
		
		return cartaoDeCredito;
	}
	
	public List<EntidadeDominio> getLista(String filter) throws SQLException {
		openConnection();
		List<EntidadeDominio> cartoes = new	ArrayList<EntidadeDominio>();
		CartaoDeCredito cartaoDeCredito = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM cartaodecredito");
		if (!filter.equals("")) {
			sql.append(" WHERE sNomeNoCartao LIKE '%" + filter + "%'");
		}
		
		PreparedStatement stmt = connection.prepareStatement(sql.toString());
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int iUsuario = rs.getInt("iUsuario");
			String nomeNoCartao = rs.getString("sNomeNoCartao");
			String numeroDoCartao = rs.getString("sNumeroDoCartao");
			int mesValidade = rs.getInt("nMesValidade");
			int anoValidade = rs.getInt("nAnoValidade");
			int codigoSeguranca = rs.getInt("nCodigoSeguranca");
			int bandeira = rs.getInt("nBandeira");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			cartaoDeCredito	= new CartaoDeCredito();
			cartaoDeCredito.setId(id);
			cartaoDeCredito.setIdUsuario(iUsuario);
			cartaoDeCredito.setNomeNoCartao(nomeNoCartao);
			cartaoDeCredito.setNumeroDoCartao(numeroDoCartao);
			cartaoDeCredito.setMesValidade(mesValidade);
			cartaoDeCredito.setAnoValidade(anoValidade);
			cartaoDeCredito.setCodigoSeguranca(codigoSeguranca);
			cartaoDeCredito.setDtCadastro(dtCadastro);
			
			for (BandeiraCartaoDeCredito band : BandeiraCartaoDeCredito.values()) {
				if (band.getCodigo() == bandeira) {
					cartaoDeCredito.setBandeira(band);
				}
			}
			
			cartoes.add(cartaoDeCredito);
		}

		rs.close();
		stmt.close();
		if (ctrlTransaction)
			connection.close();
		
		return cartoes;
	}
	
	public List<EntidadeDominio> getLista() throws SQLException {
		return getLista("");
	}
	
	public Pagamento getCartaoDeCreditoById(int idCartaoDeCredito) throws SQLException {
		openConnection();
		
		CartaoDeCredito cartaoDeCredito = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM cartaodecredito WHERE id = ?");
		
		PreparedStatement stmt = connection.prepareStatement(sql.toString());
		stmt.setInt(1, idCartaoDeCredito);
		
		ResultSet rs = stmt.executeQuery();
		
		if (rs.next()) {
			int id = rs.getInt("id");
			int iUsuario = rs.getInt("iUsuario");
			String nomeNoCartao = rs.getString("sNomeNoCartao");
			String numeroDoCartao = rs.getString("sNumeroDoCartao");
			int mesValidade = rs.getInt("nMesValidade");
			int anoValidade = rs.getInt("nAnoValidade");
			int codigoSeguranca = rs.getInt("nCodigoSeguranca");
			int bandeira = rs.getInt("nBandeira");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			cartaoDeCredito	= new CartaoDeCredito();
			cartaoDeCredito.setId(id);
			cartaoDeCredito.setIdUsuario(iUsuario);
			cartaoDeCredito.setNomeNoCartao(nomeNoCartao);
			cartaoDeCredito.setNumeroDoCartao(numeroDoCartao);
			cartaoDeCredito.setMesValidade(mesValidade);
			cartaoDeCredito.setAnoValidade(anoValidade);
			cartaoDeCredito.setCodigoSeguranca(codigoSeguranca);
			cartaoDeCredito.setDtCadastro(dtCadastro);
			
			for (BandeiraCartaoDeCredito band : BandeiraCartaoDeCredito.values()) {
				if (band.getCodigo() == bandeira) {
					cartaoDeCredito.setBandeira(band);
				}
			}
		}

		rs.close();
		stmt.close();
		if (ctrlTransaction)
			connection.close();
		
		return cartaoDeCredito;
	}
}