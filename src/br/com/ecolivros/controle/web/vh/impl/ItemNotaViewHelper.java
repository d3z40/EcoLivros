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
import br.com.ecolivros.core.impl.dao.ItemNotaDAO;
import br.com.ecolivros.core.impl.dao.LivroDAO;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.ItemNota;
import br.com.ecolivros.dominio.Livro;
import br.com.ecolivros.dominio.Nota;
import br.com.ecolivros.enuns.SituacaoEstoque;

public class ItemNotaViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String operacao = request.getParameter("operacao");
		ItemNota itemNota = null;
		List<ItemNota> itensNota = null;
		String idItemNota;
		
		if (operacao.equals("SALVAR") || operacao.equals("ALTERAR")) {
			String chk;
			String[] chkSelect = new String[10];
			String[] idLivro = new String[10];
			String[] qtde = new String[10];
			String[] valor = new String[10];
			String[] situacaoEstoque = new String[10];
			String[] id = new String[10];

			for (int i = 0; i < 10; i++) {
				chk = request.getParameter("chkSelect^" + i);
				if (chk != null && chk.equals("on")) {
					chkSelect[i] = "on";
					idLivro[i] = request.getParameter("txtLivro^" + i);
					qtde[i] = request.getParameter("txtQtde^" + i);
					valor[i] = request.getParameter("txtValor^" + i);
					situacaoEstoque[i] = request.getParameter("txtSituacaoEstoque^" + i);
					id[i] = request.getParameter("txtIdItemNota^" + i);
				} else {
					chkSelect[i] = "off";
				}
			}

			itensNota = new ArrayList<ItemNota>();

			try {

				for (int i = 0; i < 10; i++) {
					if (chkSelect[i] != null && chkSelect[i].equals("on")) {
						itemNota = new ItemNota();
						
						if (idLivro[i] != null && !idLivro[i].trim().equals(""))
							itemNota.setLivro((Livro) new LivroDAO().getEntidadeDominio(Integer.parseInt(idLivro[i])));
						
						if (qtde[i] != null && !qtde[i].trim().equals(""))
							itemNota.setQtde(Integer.parseInt(qtde[i]));
						
						if (valor[i] != null && !valor[i].trim().equals(""))
							itemNota.setValor(Double.parseDouble(valor[i]));
						
						if (id[i] != null && !id[i].trim().equals("")) {
							String idNota = id[i].substring(0, id[i].indexOf("|"));
							String idItem = id[i].substring(id[i].indexOf("|") + 1, id[i].length());
							
							if (idNota != null && !idNota.trim().equals(""))
								itemNota.setIdNota(Integer.parseInt(idNota));
							
							if (idItem != null && !idItem.trim().equals(""))
								itemNota.setId(Integer.parseInt(idItem));
						}
						
						if (operacao.equals("SALVAR")) {
							itemNota.setSituacaoEstoque(SituacaoEstoque.PENDENTE);
						} else {
							for (SituacaoEstoque situacaoEst : SituacaoEstoque.values()) {
								if (situacaoEst.getCodigo() == Integer.parseInt(situacaoEstoque[i]))
									itemNota.setSituacaoEstoque(situacaoEst);
							}
						}
						
						itensNota.add(itemNota);
					}
				}
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		} else if (operacao.equals("CONSULTAR")) {
			idItemNota = request.getParameter("IdItemNota");
			int id = 0;

			if (idItemNota != null && !idItemNota.trim().equals("")) {
				id = Integer.parseInt(idItemNota);

				itemNota = new ItemNota();
				itemNota.setId(id);
			}
		} else if (operacao.equals("EXCLUIR")) {
			idItemNota = request.getParameter("idItemNota");
			int id = 0;

			if (idItemNota != null && !idItemNota.trim().equals("")) {
				id = Integer.parseInt(idItemNota);

				try {
					itemNota = new ItemNota();
					itemNota.setId(id);
					itemNota.setSituacaoEstoque(new ItemNotaDAO().getSituacaoById(id).getSituacaoEstoque());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			Resultado resultado = (Resultado) request.getSession().getAttribute("resultado");
			idItemNota = request.getParameter("IdItemNota");
			int id = 0;

			if (idItemNota != null && !idItemNota.trim().equals("")) {
				id = Integer.parseInt(idItemNota);
			}

			if (resultado != null) {
				itemNota = new ItemNota();

				for (EntidadeDominio e : resultado.getEntidades()) {
					if (e != null && e.getId() != null && id > 0 && e.getId() == id) {
						itemNota = (ItemNota) e;
					}
				}
			}
		}
		
		if (itensNota != null && itensNota.size() >= 0) {
			Nota nota = new Nota();
			nota.setItensNota(itensNota);
			
			return nota;
		} else {
			return itemNota;
		}
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		RequestDispatcher d = null;
		
		String operacao = request.getParameter("operacao");
		
		if(resultado.getMsg() == null) {
			request.getSession().setAttribute("resultado", resultado);
			
			if (operacao.equals("SALVAR") || operacao.equals("ALTERAR") || operacao.equals("EXCLUIR"))
				d = request.getRequestDispatcher("cadastra_notas.jsp");
		} else {
			request.setAttribute("msg", resultado.getMsg());
			
			d = request.getRequestDispatcher("msg-erro.jsp");
		}
		
		if (d != null)
			d.forward(request, response);
	}
}