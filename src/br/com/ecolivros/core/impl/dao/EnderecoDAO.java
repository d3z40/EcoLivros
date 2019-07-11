package br.com.ecolivros.core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ecolivros.dominio.Endereco;
import br.com.ecolivros.dominio.EntidadeDominio;

public class EnderecoDAO extends AbstractJdbcDAO {

	public EnderecoDAO() {
		super("endereco", "id");
	}
	
	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		PreparedStatement pst = null;
		Endereco endereco = (Endereco) entidade;
		
		try {
			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO endereco (iUsuario, sDescricao, sLogradouro, nNumero, sBairro, sCidade, sEstado, sCEP, sUF, dtCadastro) ");
			sql.append("VALUES (?,?,?,?,?,?,?,?,?)");
			
			pst = connection.prepareStatement(sql.toString());
			pst.setInt(1, endereco.getIdUsuario());
			pst.setString(2, endereco.getDescricao());
			pst.setString(3, endereco.getLogradouro());
			pst.setInt(4, endereco.getNumero());
			pst.setString(5, endereco.getBairro());
			pst.setString(6, endereco.getCidade());
			pst.setString(7, endereco.getEstado());
			pst.setString(8, endereco.getCep());
			pst.setString(9, endereco.getUf());
			
			Timestamp time = new Timestamp(endereco.getDtCadastro().getTime());
			pst.setTimestamp(10, time);
			
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
		
		Endereco endereco = (Endereco) entidade;
		
		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			sql.append("UPDATE endereco SET sDescricao = ?, sLogradouro = ?, nNumero = ?, sBairro = ?, sCidade = ?, " +
					"sEstado = ?, sCEP = ?, sUf = ?, dtCadastro = ? WHERE id = ?");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setString(1, endereco.getDescricao());
			pst.setString(2, endereco.getLogradouro());
			pst.setInt(3, endereco.getNumero());
			pst.setString(4, endereco.getBairro());
			pst.setString(5, endereco.getCidade());
			pst.setString(6, endereco.getEstado());
			pst.setString(7, endereco.getCep());
			pst.setString(8, endereco.getUf());
			
			Timestamp time = new Timestamp(endereco.getDtCadastro().getTime());
			pst.setTimestamp(9, time);
			
			pst.setInt(10, endereco.getId());
			
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
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		PreparedStatement pst;
		List<EntidadeDominio> enderecos = new	ArrayList<EntidadeDominio>();
		
		Endereco endereco = (Endereco) entidade;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM endereco");
		sql.append(" WHERE iUsuario = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, endereco.getIdUsuario());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {			
			int id = rs.getInt("id");
			int idUsuario = rs.getInt("iUsuario");
			String descricao = rs.getString("sDescricao");
			String logradouro = rs.getString("sLogradouro");
			int numero = rs.getInt("nNumero");
			String bairro = rs.getString("sBairro");
			String cidade = rs.getString("sCidade");
			String estado = rs.getString("sEstado");
			String cep = rs.getString("sCEP");
			String uf = rs.getString("sUf");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			endereco	= new Endereco();
			endereco.setId(id);
			endereco.setIdUsuario(idUsuario);
			endereco.setDescricao(descricao);
			endereco.setLogradouro(logradouro);
			endereco.setNumero(numero);
			endereco.setBairro(bairro);
			endereco.setCidade(cidade);
			endereco.setEstado(estado);
			endereco.setCep(cep);
			endereco.setUf(uf);
			endereco.setDtCadastro(dtCadastro);
						
			enderecos.add(endereco);
		}

		rs.close();
		pst.close();
		connection.close();
		
		return enderecos;
	}
	
	public List<EntidadeDominio> getEntidadeDominio(int idUsuario) throws SQLException {
		openConnection();
		PreparedStatement pst;
		List<EntidadeDominio> enderecos = new	ArrayList<EntidadeDominio>();
		
		Endereco endereco = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM endereco");
		sql.append(" WHERE iUsuario = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, idUsuario);
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {			
			int id = rs.getInt("id");
			int iUsuario = rs.getInt("iUsuario");
			String descricao = rs.getString("sDescricao");
			String logradouro = rs.getString("sLogradouro");
			int numero = rs.getInt("nNumero");
			String bairro = rs.getString("sBairro");
			String cidade = rs.getString("sCidade");
			String estado = rs.getString("sEstado");
			String cep = rs.getString("sCEP");
			String uf = rs.getString("sUf");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			endereco	= new Endereco();
			endereco.setId(id);
			endereco.setIdUsuario(iUsuario);
			endereco.setDescricao(descricao);
			endereco.setLogradouro(logradouro);
			endereco.setNumero(numero);
			endereco.setBairro(bairro);
			endereco.setCidade(cidade);
			endereco.setEstado(estado);
			endereco.setCep(cep);
			endereco.setUf(uf);
			endereco.setDtCadastro(dtCadastro);
						
			enderecos.add(endereco);
		}

		rs.close();
		pst.close();
		connection.close();
		
		return enderecos;
	}
	
	public EntidadeDominio getEntidadeDominioEndreco(int idEndreco) throws SQLException {
		openConnection();
		PreparedStatement pst;
		Endereco endereco = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM endereco");
		sql.append(" WHERE id = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, idEndreco);
		
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()) {			
			int id = rs.getInt("id");
			int iUsuario = rs.getInt("iUsuario");
			String descricao = rs.getString("sDescricao");
			String logradouro = rs.getString("sLogradouro");
			int numero = rs.getInt("nNumero");
			String bairro = rs.getString("sBairro");
			String cidade = rs.getString("sCidade");
			String estado = rs.getString("sEstado");
			String cep = rs.getString("sCEP");
			String uf = rs.getString("sUf");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			endereco	= new Endereco();
			endereco.setId(id);
			endereco.setIdUsuario(iUsuario);
			endereco.setDescricao(descricao);
			endereco.setLogradouro(logradouro);
			endereco.setNumero(numero);
			endereco.setBairro(bairro);
			endereco.setCidade(cidade);
			endereco.setEstado(estado);
			endereco.setCep(cep);
			endereco.setUf(uf);
			endereco.setDtCadastro(dtCadastro);
		}

		rs.close();
		pst.close();
		connection.close();
		
		return endereco;
	}
	
	public List<EntidadeDominio> getLista(String filter) throws SQLException {
		openConnection();
		List<EntidadeDominio> enderecos = new	ArrayList<EntidadeDominio>();
		Endereco endereco = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM endereco");
		if (!filter.equals("")) {
			sql.append(" WHERE sDescricao LIKE '%" + filter + "%'");
		}
		
		PreparedStatement stmt = connection.prepareStatement(sql.toString());
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int iUsuario = rs.getInt("iUsuario");
			String descricao = rs.getString("sDescricao");
			String logradouro = rs.getString("sLogradouro");
			int numero = rs.getInt("nNumero");
			String bairro = rs.getString("sBairro");
			String cidade = rs.getString("sCidade");
			String estado = rs.getString("sEstado");
			String cep = rs.getString("sCEP");
			String uf = rs.getString("sUf");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			endereco	= new Endereco();
			endereco.setId(id);
			endereco.setIdUsuario(iUsuario);
			endereco.setDescricao(descricao);
			endereco.setLogradouro(logradouro);
			endereco.setNumero(numero);
			endereco.setBairro(bairro);
			endereco.setCidade(cidade);
			endereco.setEstado(estado);
			endereco.setCep(cep);
			endereco.setUf(uf);
			endereco.setDtCadastro(dtCadastro);
			
			enderecos.add(endereco);
		}

		rs.close();
		stmt.close();
		connection.close();
		
		return enderecos;
	}
	
	public List<EntidadeDominio> getLista() throws SQLException {
		return getLista("");
	}
}