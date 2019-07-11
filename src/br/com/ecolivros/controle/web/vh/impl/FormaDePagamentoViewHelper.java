package br.com.ecolivros.controle.web.vh.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ecolivros.controle.web.vh.IViewHelper;
import br.com.ecolivros.core.aplicacao.Resultado;
import br.com.ecolivros.core.impl.dao.CartaoDeCreditoDAO;
import br.com.ecolivros.core.impl.dao.CupomDAO;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.FormaDePagamento;
import br.com.ecolivros.dominio.Pedido;
import br.com.ecolivros.enuns.TipoPagamento;

public class FormaDePagamentoViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String operacao = request.getParameter("operacao");
		FormaDePagamento formaDePagamento = null;
		List<FormaDePagamento> formasDePagamento = null;
		
		if (operacao.equals("SALVAR") || operacao.equals("ALTERAR")) {
			int index = 0;
			int qtdeFormaPgto = (int) request.getSession().getServletContext().getAttribute("QtdeFormaPgto");
			if (qtdeFormaPgto > 0) {
				index = qtdeFormaPgto + 1;
			}
			
			String chk;
			String[] chkSelectFormaPgto = new String[index];
			String[] idPagamentoFormaPgto = new String[index];
			String[] tipoPagamentoFormaPgto = new String[index];
			String[] valorFormaPgto = new String[index];

			for (int i = 0; i < index; i++) {
				chk = request.getParameter("chkSelectFormaPgto^" + i);
				if (chk != null && chk.equals("on")) {
					chkSelectFormaPgto[i] = "on";
					idPagamentoFormaPgto[i] = request.getParameter("txtIdPagamentoFormaPgto^" + i);
					tipoPagamentoFormaPgto[i] = request.getParameter("txtTipoPagamentoFormaPgto^" + i);
					valorFormaPgto[i] = request.getParameter("txtValorFormaPgto^" + i);
				} else {
					chkSelectFormaPgto[i] = "off";
				}
			}

			formasDePagamento = new ArrayList<FormaDePagamento>();
			
			try {
				for (int i = 0; i < index; i++) {
					if (chkSelectFormaPgto[i] != null && chkSelectFormaPgto[i].equals("on")) {
						formaDePagamento = new FormaDePagamento();
						
						if (tipoPagamentoFormaPgto[i] != null && !tipoPagamentoFormaPgto[i].trim().equals("")) {
							for (TipoPagamento tipoPagamento : TipoPagamento.values()) {
								if (tipoPagamento.name().equals(tipoPagamentoFormaPgto[i].trim())) {
									formaDePagamento.setTipoPagamento(tipoPagamento);
									break;
								}
							}
							
							if (idPagamentoFormaPgto[i] != null && !idPagamentoFormaPgto[i].trim().equals("")) {
								if (formaDePagamento.getTipoPagamento().getCodigo() == TipoPagamento.CARTAODECREDITO.getCodigo()) {
									formaDePagamento.setPagamento(new CartaoDeCreditoDAO().getCartaoDeCreditoById(Integer.parseInt(idPagamentoFormaPgto[i].trim())));
								} else if (formaDePagamento.getTipoPagamento().getCodigo() == TipoPagamento.CUPOM.getCodigo()) {
									formaDePagamento.setPagamento(new CupomDAO().getCupomById(Integer.parseInt(idPagamentoFormaPgto[i].trim())));
								}
							}
						}
						
						if (valorFormaPgto[i] != null && !valorFormaPgto[i].trim().equals(""))
							formaDePagamento.setValor(Double.parseDouble(valorFormaPgto[i]));
						
						formasDePagamento.add(formaDePagamento);
					}
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (formasDePagamento != null && formasDePagamento.size() >= 0) {
			Pedido pedido = new Pedido();
			pedido.setFormasDePgto(formasDePagamento);
			
			return pedido;
		} else {
			return formaDePagamento;
		}
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		RequestDispatcher d = null;
		
		String operacao = request.getParameter("operacao");
		
		if(resultado.getMsg() == null) {
			request.getSession().setAttribute("resultado", resultado);
			
			if (operacao.equals("SALVAR") || operacao.equals("ALTERAR") || operacao.equals("EXCLUIR"))
				d = request.getRequestDispatcher("perfil.jsp");
		} else {
			request.setAttribute("msg", resultado.getMsg());
			
			d = request.getRequestDispatcher("msg-erro.jsp");
		}
		
		if (d != null)
			d.forward(request, response);
	}
}