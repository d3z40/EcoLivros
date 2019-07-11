package br.com.ecolivros.core.impl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.ecolivros.dominio.Acesso;
import br.com.ecolivros.dominio.EntidadeDominio;

public class AcessoDAO extends AbstractJdbcDAO {

	public AcessoDAO() {
		super("acesso", "id");
	}
	
	public AcessoDAO(Connection connection) {
		super(connection, "acesso", "id");
	}
	
	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		return null;
	}
	
	public EntidadeDominio getEntidadeDominio(EntidadeDominio entidadeDominio) throws SQLException {
		openConnection();
		Acesso acesso = (Acesso) entidadeDominio;

		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM acesso WHERE id = ?");
		stmt.setLong(1, acesso.getId());
		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			int id = rs.getInt("id");
			String descricao =rs.getString("sDescricao");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			acesso = new Acesso();
			acesso.setId(id);
			acesso.setDescricao(descricao);
			acesso.setDtCadastro(dtCadastro);
			acesso.setControlesAcesso(new ControleDeAcessoDAO().getEntidadeDominioAcesso(id));
		}
		
		rs.close();
		stmt.close();
		connection.close();

		return acesso;
	}
	
	public EntidadeDominio getEntidadeDominio(int idAcesso) throws SQLException {
		openConnection();
		Acesso acesso = null;

		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM acesso WHERE id = ?");
		stmt.setLong(1,idAcesso);
		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			int id = rs.getInt("id");
			String descricao =rs.getString("sDescricao");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			acesso = new Acesso();
			acesso.setId(id);
			acesso.setDescricao(descricao);
			acesso.setDtCadastro(dtCadastro);
			acesso.setControlesAcesso(new ControleDeAcessoDAO().getEntidadeDominioAcesso(id));
		}
		
		rs.close();
		stmt.close();
		connection.close();

		return acesso;
	}
}