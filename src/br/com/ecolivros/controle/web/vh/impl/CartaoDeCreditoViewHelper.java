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
import br.com.ecolivros.core.impl.dao.CartaoDeCreditoDAO;
import br.com.ecolivros.core.util.UsuarioLogado;
import br.com.ecolivros.dominio.CartaoDeCredito;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.enuns.BandeiraCartaoDeCredito;

public class CartaoDeCreditoViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String operacao = request.getParameter("operacao");
		CartaoDeCredito cartaoDeCredito = null; 
		String idCartaoDeCredito;
		
		if(operacao.equals("SALVAR") || operacao.equals("ALTERAR")) {
			String id = request.getParameter("txtIdCartaoDeCredito");
//			String idUsuario = request.getParameter("txtIdUsuario");
			String nomeNoCartao = request.getParameter("txtNomeNoCartao");
			String numeroDoCartao = request.getParameter("txtNumeroDoCartao");
			String mesValidade = request.getParameter("txtMesValidade");
			String anoValidade = request.getParameter("txtAnoValidade");
			String codigoSeguranca = request.getParameter("txtCodigoSeguranca");
			String bandeira = request.getParameter("txtBandeira");
			
			if (mesValidade == null || mesValidade.trim().equals(""))
				mesValidade = "0";
			if (anoValidade == null || anoValidade.trim().equals(""))
				anoValidade = "0";
			if (codigoSeguranca == null || codigoSeguranca.trim().equals(""))
				codigoSeguranca = "0";
						
			cartaoDeCredito = new CartaoDeCredito();
			
			cartaoDeCredito.setIdUsuario(UsuarioLogado.getUsuario().getId());
			cartaoDeCredito.setNomeNoCartao(nomeNoCartao);
			cartaoDeCredito.setNumeroDoCartao(numeroDoCartao);
			cartaoDeCredito.setMesValidade(Integer.parseInt(mesValidade));
			cartaoDeCredito.setAnoValidade(Integer.parseInt(anoValidade));
			cartaoDeCredito.setCodigoSeguranca(Integer.parseInt(codigoSeguranca));
			cartaoDeCredito.setDtCadastro(new Date());
			
			for (BandeiraCartaoDeCredito band : BandeiraCartaoDeCredito.values()) {
				if (bandeira != null && Integer.parseInt(bandeira) == band.getCodigo()) {
					cartaoDeCredito.setBandeira(band);
				}
			}
			
			if (id != null && !id.trim().equals("")) {
				cartaoDeCredito.setId(Integer.parseInt(id));
			}
		} else if(operacao.equals("EXCLUIR") || operacao.equals("CONSULTAR")) {
			idCartaoDeCredito = request.getParameter("IdCartaoDeCredito");
			int id = 0;
			
			if(idCartaoDeCredito != null && !idCartaoDeCredito.trim().equals("")) {
				id = Integer.parseInt(idCartaoDeCredito);
				
				cartaoDeCredito = new CartaoDeCredito();
				cartaoDeCredito.setId(id);
			}
		} else {
			Resultado resultado = (Resultado) request.getSession().getAttribute("resultado");
			idCartaoDeCredito = request.getParameter("IdCartaoDeCredito");
			int id = 0;
			
			if(idCartaoDeCredito != null && !idCartaoDeCredito.trim().equals("")) {
				id = Integer.parseInt(idCartaoDeCredito);
			}
			
			if (resultado != null) {
				for(EntidadeDominio e : resultado.getEntidades()) {
					if(e != null && e.getId() != null && id > 0 && e.getId() == id){
						cartaoDeCredito = (CartaoDeCredito) e;
					}
				}
			}
		}
		return cartaoDeCredito;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		RequestDispatcher d = null;
		
		String operacao = request.getParameter("operacao");
		
		if(resultado.getMsg() == null) {
			request.getSession().setAttribute("resultado", resultado);
			
			if (operacao.equals("SALVAR") || operacao.equals("ALTERAR")) {
				d = request.getRequestDispatcher("cadastra_cartoes.jsp");
			} else if (operacao.equals("VISUALIZAR")) {
				try {
					String filter = request.getParameter("search");
					List<EntidadeDominio> cartoes = new CartaoDeCreditoDAO().getLista(filter);
					request.setAttribute("cartoes", cartoes);
				} catch (SQLException e) {
					resultado.setMsg("Não foi possível listar cartões.");
				}
				
				d = request.getRequestDispatcher("consulta_cartoes.jsp");
				
			} else if (operacao.equals("CONSULTAR")) {
				try {
					int IdCartaoDeCredito = Integer.parseInt(request.getParameter("IdCartaoDeCredito"));
					CartaoDeCredito cartaoDeCredito = (CartaoDeCredito) new CartaoDeCreditoDAO().getEntidadeDominioCartao(IdCartaoDeCredito);
					request.setAttribute("cartaoDeCredito", cartaoDeCredito);
				} catch (SQLException e) {
					resultado.setMsg("Não foi possível listar cartão.");
				}
				
				d = request.getRequestDispatcher("cadastra_cartoes.jsp");
			} else if (operacao.equals("EXCLUIR")) {
				d = request.getRequestDispatcher("consulta_cartoes.jsp");				
			}
		} else {
			request.setAttribute("msg", resultado.getMsg());
			
			d = request.getRequestDispatcher("msg-erro.jsp");
		}
				
		if (d != null)
			d.forward(request, response);
	}
}