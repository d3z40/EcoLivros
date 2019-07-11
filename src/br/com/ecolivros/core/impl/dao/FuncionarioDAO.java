package br.com.ecolivros.core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ecolivros.dominio.Acesso;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Funcionario;

public class FuncionarioDAO extends AbstractJdbcDAO {

	public FuncionarioDAO() {
		super("funcionario", "id");
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		PreparedStatement pst = null;
		
		Funcionario funcionario = (Funcionario) entidade;
		
		try {
			connection.setAutoCommit(false);

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO funcionario (iUsuario, iAcesso, sNumRegistro, dtCadastro) ");
			sql.append("VALUES (?,?,?,?)");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setInt(1, funcionario.getIdUsuario());
			pst.setInt(2, funcionario.getAcesso().getId());
			pst.setString(3, funcionario.getNumRegistro());
			
			Timestamp time = new Timestamp(funcionario.getDtCadastro().getTime());
			pst.setTimestamp(14, time);
			
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
		
		Funcionario funcionario = (Funcionario) entidade;
		
		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			
			sql.append("UPDATE funcionario SET iAcesso = ?, numRegistro = ? WHERE id = ?");
			
			pst = connection.prepareStatement(sql.toString());
			pst.setInt(1, funcionario.getAcesso().getId());
			pst.setString(2, funcionario.getNumRegistro());
			pst.setInt(3, funcionario.getId());
			
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
		List<EntidadeDominio> funcionarios = new ArrayList<EntidadeDominio>();
		
		Funcionario funcionario = (Funcionario) entidade;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM funcionario");
		sql.append(" WHERE iUsuario = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, funcionario.getIdUsuario());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idUsuario = rs.getInt("iUsuario");
			int idAcesso = rs.getInt("iAcesso");
			String numRegistro = rs.getString("sNumRegistro");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			funcionario	= new Funcionario();
			funcionario.setId(id);
			funcionario.setIdUsuario(idUsuario);
			funcionario.setAcesso((Acesso) new AcessoDAO().getEntidadeDominio(idAcesso));
			funcionario.setNumRegistro(numRegistro);
			funcionario.setDtCadastro(dtCadastro);
			
			funcionarios.add(funcionario);
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return funcionarios;
	}
}