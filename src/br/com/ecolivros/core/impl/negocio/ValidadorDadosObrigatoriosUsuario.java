package br.com.ecolivros.core.impl.negocio;

import java.sql.SQLException;

import br.com.ecolivros.core.IStrategy;
import br.com.ecolivros.core.impl.dao.UsuarioDAO;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Usuario;

public class ValidadorDadosObrigatoriosUsuario implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder erro = new StringBuilder();
		
		if(entidade instanceof Usuario){
			Usuario usuario = (Usuario) entidade;
			
			String nome = usuario.getNome();
			String cpf = usuario.getCpf();
			String email = usuario.getEmail();
			String senha = usuario.getSenha();
			String senhaRepetida = usuario.getSenhaRepetida();
			String chkRegister = usuario.getChkRegister();
			
			if (!senha.equals(senhaRepetida) && chkRegister == null)
				erro.append("Senhas diferentes e Não aceitou a Politica de Privacidade.\n");
			else if (!senha.equals(senhaRepetida) && chkRegister != null && chkRegister.equals("on"))
				erro.append("Senhas diferentes.\n");
			else if (senha.equals(senhaRepetida) && chkRegister == null)
				erro.append("Não aceitou a Politica de Privacidade.\n");
						
			try {
				if (new UsuarioDAO().emailExiste(email))
					erro.append("Já existe cadastro para este e-mail.\n");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if (new UsuarioDAO().cpfExiste(cpf))
					erro.append("Já existe cadastro para este CPF.\n");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(nome == null || cpf == null || email == null || senha == null)
				erro.append("Nome, CPF, E-mail e Senha são de preenchimento obrigatório!\n");
			if(nome.trim().equals("") || cpf.trim().equals("") || email.trim().equals("") || senha.trim().equals(""))
				erro.append("Nome, CPF, E-mail e Senha são de preenchimento obrigatório!\n");
			
			if (erro.length() > 0)
				return erro.toString();
		} else {
			return "Deve ser registrado um usuário!";
		}
		return null;
	}
}