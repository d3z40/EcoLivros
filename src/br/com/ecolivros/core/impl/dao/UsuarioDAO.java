package br.com.ecolivros.core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Usuario;
import br.com.ecolivros.enuns.TipoUsuario;

public class UsuarioDAO extends AbstractJdbcDAO {

	public UsuarioDAO() {
		super("usuario", "id");
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		PreparedStatement pst = null;
		Usuario usuario = (Usuario) entidade;
		
		try {
			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO usuario (sNome, sEmail, sSenha, sCPF, iTipoUsuario, dtCadastro) ");
			sql.append("VALUES (?,?,?,?,?,?)");

			pst = connection.prepareStatement(sql.toString());
			pst.setString(1, usuario.getNome());
			pst.setString(2, usuario.getEmail());
			pst.setString(3, usuario.getSenha());
			pst.setString(4, usuario.getCpf());
			pst.setInt(5, usuario.getTipoUsuario().getCodigo());
			Timestamp time = new Timestamp(usuario.getDtCadastro().getTime());
			pst.setTimestamp(6, time);
			
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
		PreparedStatement pst = null;
		Usuario usuario = (Usuario) entidade;
		
		try {
			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE usuario SET sNome = ?, dtCadastro = ? WHERE id = ?");
			
			pst = connection.prepareStatement(sql.toString());
			pst.setString(1, usuario.getNome());
			Timestamp time = new Timestamp(usuario.getDtCadastro().getTime());
			pst.setTimestamp(2, time);
			pst.setInt(3, usuario.getId());
			
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
		PreparedStatement pst = null;
		List<EntidadeDominio> usuarios = new ArrayList<EntidadeDominio>();
		
		Usuario usuario = (Usuario) entidade;
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM usuario WHERE sEmail = ? AND BINARY sSenha = ?");
			
			pst = connection.prepareStatement(sql.toString());
			pst.setString(1, usuario.getEmail());
			pst.setString(2, usuario.getSenha());
			
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				usuario = new Usuario();
				usuario.setId(Integer.parseInt(rs.getString("id")));
				usuario.setNome(rs.getString("sNome"));
				usuario.setEmail(rs.getString("sEmail"));
				usuario.setSenha(rs.getString("sSenha"));
				usuario.setCpf(rs.getString("sCPF"));
				usuario.setTipoUsuario(TipoUsuario.getTipoUsuario(Integer.parseInt(rs.getString("iTipoUsuario"))));
				usuario.setDtCadastro(rs.getDate("dtCadastro"));
				
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return usuarios;
	}
	
	public EntidadeDominio getEntidadeDominio(int idUsuario) throws SQLException {
		openConnection();
		PreparedStatement pst = null;
		
		Usuario usuario = null;
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM usuario WHERE id = ?");
			
			pst = connection.prepareStatement(sql.toString());
			pst.setInt(1, idUsuario);
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				usuario = new Usuario();
				usuario.setId(Integer.parseInt(rs.getString("id")));
				usuario.setNome(rs.getString("sNome"));
				usuario.setEmail(rs.getString("sEmail"));
				usuario.setSenha(rs.getString("sSenha"));
				usuario.setCpf(rs.getString("sCPF"));
				usuario.setTipoUsuario(TipoUsuario.getTipoUsuario(Integer.parseInt(rs.getString("iTipoUsuario"))));
				usuario.setDtCadastro(rs.getDate("dtCadastro"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return usuario;
	}
	
	public boolean emailExiste(String email) throws SQLException {
		openConnection();
		PreparedStatement pst = null;
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM usuario WHERE sEmail = ?");
			
			pst = connection.prepareStatement(sql.toString());
			pst.setString(1, email);
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	public boolean cpfExiste(String cpf) throws SQLException {
		openConnection();
		PreparedStatement pst = null;
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM usuario WHERE sCPF = ?");
			
			pst = connection.prepareStatement(sql.toString());
			pst.setString(1, cpf);
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
}