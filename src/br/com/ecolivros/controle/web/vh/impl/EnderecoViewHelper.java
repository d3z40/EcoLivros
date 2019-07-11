package br.com.ecolivros.controle.web.vh.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ecolivros.controle.web.vh.IViewHelper;
import br.com.ecolivros.core.aplicacao.Resultado;
import br.com.ecolivros.core.impl.dao.EnderecoDAO;
import br.com.ecolivros.core.util.UsuarioLogado;
import br.com.ecolivros.dominio.Endereco;
import br.com.ecolivros.dominio.EntidadeDominio;

public class EnderecoViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String operacao = request.getParameter("operacao");
		Endereco endereco = null; 
		String idEndereco;
		
		if(operacao.equals("SALVAR") || operacao.equals("ALTERAR")) {
			String id = request.getParameter("txtIdEndereco");
//			String idUsuario = request.getParameter("txtIdUsuario");
			String descricao = request.getParameter("txtDescricao");
			String logradouro = request.getParameter("txtLogradouro");
			String numero = request.getParameter("txtNum");
			String bairro = request.getParameter("txtBairro");
			String cidade = request.getParameter("txtCidade");
			String estado = request.getParameter("txtEstado");			
			String cep = request.getParameter("txtCEP");
			String uf = request.getParameter("txtUf");
			
			if (numero == null || numero.trim().equals(""))
				numero = "0";
			
			endereco = new Endereco();
			
			endereco.setIdUsuario(UsuarioLogado.getUsuario().getId());
			endereco.setDescricao(descricao);
			endereco.setLogradouro(logradouro);
			endereco.setNumero(Integer.parseInt(numero));
			endereco.setBairro(bairro);
			endereco.setCidade(cidade);
			endereco.setEstado(estado);
			endereco.setCep(cep);
			endereco.setUf(uf);
			endereco.setDtCadastro(new Date());
			
			if (id != null && !id.trim().equals("")) {
				endereco.setId(Integer.parseInt(id));
			}
		} else if(operacao.equals("EXCLUIR") || operacao.equals("CONSULTAR")) {
			idEndereco = request.getParameter("IdEndereco");
			int id = 0;
			
			if(idEndereco != null && !idEndereco.trim().equals("")) {
				id = Integer.parseInt(idEndereco);
				
				endereco = new Endereco();
				endereco.setId(id);
			}
		} else {
			Resultado resultado = (Resultado) request.getSession().getAttribute("resultado");
			idEndereco = request.getParameter("txtidEndereco");
			int id = 0;
			
			if(idEndereco != null && !idEndereco.trim().equals("")) {
				id = Integer.parseInt(idEndereco);
			}
			
			if (resultado != null) {
				for(EntidadeDominio e : resultado.getEntidades()) {
					if(e != null && e.getId() != null && id > 0 && e.getId() == id){
						endereco = (Endereco) e;
					}
				}
			}
		}
		return endereco;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		RequestDispatcher d = null;
		
		String operacao = request.getParameter("operacao");
		
		if(resultado.getMsg() == null) {
			request.getSession().setAttribute("resultado", resultado);
			
			if (operacao.equals("SALVAR") || operacao.equals("ALTERAR")) {
				d = request.getRequestDispatcher("cadastra_enderecos.jsp");
				
			} else if (operacao.equals("VISUALIZAR")) {
				try {
					String filter = request.getParameter("search");
					List<EntidadeDominio> enderecos = new EnderecoDAO().getLista(filter);
					request.setAttribute("enderecos", enderecos);
				} catch (SQLException e) {
					resultado.setMsg("Não foi possível listar endereços.");
				}
				
				d = request.getRequestDispatcher("consulta_enderecos.jsp");
				
			} else if (operacao.equals("CONSULTAR")) {
				try {
					int IdEndereco = Integer.parseInt(request.getParameter("IdEndereco"));
					Endereco endereco = (Endereco) new EnderecoDAO().getEntidadeDominioEndreco(IdEndereco);
					request.setAttribute("endereco", endereco);
				} catch (SQLException e) {
					resultado.setMsg("Não foi possível listar endreço.");;
				}
				
				d = request.getRequestDispatcher("cadastra_enderecos.jsp");
				
			} else if (operacao.equals("EXCLUIR")) {
				d = request.getRequestDispatcher("consulta_enderecos.jsp");
				
			}
		} else {
			request.setAttribute("msg", resultado.getMsg());
			
			d = request.getRequestDispatcher("msg-erro.jsp");
		}
				
		if (d != null)
			d.forward(request, response);
	}
}