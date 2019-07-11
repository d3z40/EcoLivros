package br.com.ecolivros.controle.web.vh.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ecolivros.controle.web.vh.IViewHelper;
import br.com.ecolivros.core.aplicacao.Resultado;
import br.com.ecolivros.core.impl.dao.PedidoDAO;
import br.com.ecolivros.core.util.UsuarioLogado;
import br.com.ecolivros.dominio.Endereco;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.FormaDePagamento;
import br.com.ecolivros.dominio.Pedido;
import br.com.ecolivros.enuns.Frete;

public class PedidoViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String operacao = request.getParameter("operacao");
		Pedido pedido = null;
		String idPedido;
		
		if(operacao.equals("SALVAR") || operacao.equals("ALTERAR")) {
			pedido = (Pedido) request.getSession().getServletContext().getAttribute("pedido");
			
			EntidadeDominio ed = new FormaDePagamentoViewHelper().getEntidade(request);
			if (ed != null && ed instanceof Pedido) {
				Pedido pedidoTemp = (Pedido) ed;
				pedido.setFormasDePgto(pedidoTemp.getFormasDePgto());
			} else if (ed != null && ed instanceof FormaDePagamento) {
				FormaDePagamento formaDePgto = (FormaDePagamento) ed;
				List<FormaDePagamento> formasDePgto = new ArrayList<FormaDePagamento>();
				formasDePgto.add(formaDePgto);
				
				pedido.setFormasDePgto(formasDePgto);
			}
			
			Endereco enderecoPedido = (Endereco) new EnderecoPedidoViewHelper().getEntidade(request);
			if (enderecoPedido != null) {
				pedido.setEndereco(enderecoPedido);
				
				for (Frete frete : Frete.values()) {
					if (enderecoPedido.getUf().equals(frete.name())) {
						pedido.setFrete(frete);
						pedido.setTotal(pedido.getTotal() + frete.getValor());
						break;
					}
				}
			}
			
			try {			
				String numeroPedido = String.format("%03d_", UsuarioLogado.getUsuario().getId());
				
				Calendar dataAtual = Calendar.getInstance();
				dataAtual.setTime(new Date());
				numeroPedido += String.format("%04d%02d%02d%02d%02d%02d_", dataAtual.get(Calendar.YEAR),
																		   dataAtual.get(Calendar.MONTH),
																		   dataAtual.get(Calendar.DAY_OF_MONTH),
																		   dataAtual.get(Calendar.HOUR_OF_DAY),
																		   dataAtual.get(Calendar.MINUTE),
																		   dataAtual.get(Calendar.SECOND));
				
				numeroPedido += String.format("%05d", new PedidoDAO().getSeqPedidoByIdUsuario(UsuarioLogado.getUsuario().getId()) + 1);
				
				pedido.setNumPedido(numeroPedido);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if(operacao.equals("CONSULTAR")) {
			idPedido = request.getParameter("IdPedido");
			int id = 0;
			
			if(idPedido != null && !idPedido.trim().equals("")) {
				id = Integer.parseInt(idPedido);
				
				pedido = new Pedido();
				pedido.setId(id);
			}
		} else {
			Resultado resultado = (Resultado) request.getSession().getAttribute("resultado");
			idPedido = request.getParameter("IdPedido");
			int id = 0;
			
			if(idPedido != null && !idPedido.trim().equals("")) {
				id = Integer.parseInt(idPedido);
			}
			
			if (resultado != null) {
				for(EntidadeDominio e : resultado.getEntidades()) {
					if(e != null && e.getId() != null && id > 0 && e.getId() == id) {
						pedido = (Pedido) e;
					}
				}
			}
		}
		
		return pedido;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		RequestDispatcher d = null;
		
		String operacao = request.getParameter("operacao");
		
		if(resultado.getMsg() == null) {
			request.getSession().setAttribute("resultado", resultado);
			
			if (operacao.equals("SALVAR") || operacao.equals("ALTERAR")) {
				d = request.getRequestDispatcher("perfil.jsp");
			} else if (operacao.equals("VISUALIZAR")) {
				try {
					String filter = request.getParameter("search");
					List<EntidadeDominio> pedidos = new PedidoDAO().getLista(filter);
					request.setAttribute("pedidos", pedidos);
				} catch (SQLException e) {
					resultado.setMsg("Não foi possível listar pedidos.");
				}
				
				d = request.getRequestDispatcher("consulta_pedidos.jsp");
				
			} else if (operacao.equals("CONSULTAR")) {
				try {
					int IdPedido = Integer.parseInt(request.getParameter("IdPedido"));
					Pedido pedido = new PedidoDAO().getPedidoById(IdPedido);
					request.setAttribute("pedido", pedido);
				} catch (SQLException e) {
					resultado.setMsg("Não foi possível listar pedido.");
				}
				
				d = request.getRequestDispatcher("detalha_pedido.jsp");
			} else if (operacao.equals("EXCLUIR")) {
				d = request.getRequestDispatcher("consulta_pedido.jsp");				
			}
		} else {
			request.setAttribute("msg", resultado.getMsg());
			
			d = request.getRequestDispatcher("msg-erro.jsp");
		}
				
		if (d != null)
			d.forward(request, response);
	}
}