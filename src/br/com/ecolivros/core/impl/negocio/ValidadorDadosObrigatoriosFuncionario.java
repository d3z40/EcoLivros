package br.com.ecolivros.core.impl.negocio;

import java.sql.SQLException;

import br.com.ecolivros.core.IStrategy;
import br.com.ecolivros.core.impl.dao.UsuarioDAO;
import br.com.ecolivros.dominio.Acesso;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Funcionario;
import br.com.ecolivros.dominio.Usuario;

public class ValidadorDadosObrigatoriosFuncionario implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof Usuario){
			Usuario usuario = null;
			Funcionario funcionario = (Funcionario) entidade;
			
			int idUsuario = funcionario.getIdUsuario();
			Acesso acesso = (Acesso) funcionario.getAcesso();
			String numRegistro = funcionario.getNumRegistro();
			
			try {
				usuario = (Usuario) new UsuarioDAO().getEntidadeDominio(idUsuario);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(idUsuario <= 0 || usuario == null)
				return "Erro grave, IdUsuario não encontrado, avise o Desenvolvedor.";
			
			if(acesso == null)
				return "Acesso é obrigatório!";
			
			if(numRegistro == null || numRegistro.trim().equals(""))
				return "Número do Registro é obrigatório!";
		} else {
			return "Deve ser registrado um funcionário!";
		}
		return null;
	}
}