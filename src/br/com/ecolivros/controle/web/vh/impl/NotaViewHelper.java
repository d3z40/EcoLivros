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
import br.com.ecolivros.core.impl.dao.FornecedorDAO;
import br.com.ecolivros.core.impl.dao.ItemNotaDAO;
import br.com.ecolivros.core.impl.dao.NotaDAO;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Fornecedor;
import br.com.ecolivros.dominio.ItemNota;
import br.com.ecolivros.dominio.Nota;
import br.com.ecolivros.enuns.SituacaoEstoque;

public class NotaViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String operacao = request.getParameter("operacao");
		Nota nota = null;
		String idNota;
		
		if(operacao.equals("SALVAR") || operacao.equals("ALTERAR")) {
			String idFornecedor = request.getParameter("txtFornecedor");
			String numeroNota = request.getParameter("txtNumeroNota");
			String situacaoEstoque = request.getParameter("txtSituacaoEstoque");
			String id = request.getParameter("txtIdNota");
	
			nota = new Nota();
			
			try {
				if (idFornecedor != null && !idFornecedor.trim().equals(""))
					nota.setFornecedor((Fornecedor) new FornecedorDAO().getEntidadeDominio(Integer.parseInt(idFornecedor)));
				
				if(numeroNota != null && !numeroNota.trim().equals(""))
					nota.setNumeroNota(numeroNota);
				
				if(id != null && !id.trim().equals(""))
					nota.setId(Integer.parseInt(id));
	
				for (SituacaoEstoque situacaoEst : SituacaoEstoque.values()) {
					if (situacaoEst.getCodigo() == Integer.parseInt(situacaoEstoque))
						nota.setSituacaoEstoque(situacaoEst);
				}
				
				Nota notaDeItens = (Nota) new ItemNotaViewHelper().getEntidade(request);
				if (notaDeItens != null)
					nota.setItensNota(notaDeItens.getItensNota());
				
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		} else if(operacao.equals("CONSULTAR")) {
			idNota = request.getParameter("IdNota");
			String idItemNota = request.getParameter("IdItemNota");
			int id = 0;
			
			if(idNota != null && !idNota.trim().equals("")) {
				id = Integer.parseInt(idNota);
				
				nota = new Nota();
				nota.setId(id);
				
				if (idItemNota != null && !idItemNota.trim().equals("")) {
					Nota notaDeItem = (Nota) new ItemNotaViewHelper().getEntidade(request);
					nota.setItensNota(notaDeItem.getItensNota());
				}
			}
		} else if(operacao.equals("EXCLUIR")) {
			idNota = request.getParameter("IdNota");
			String idItemNota = request.getParameter("idItemNota");
			int id = 0;
			
			if(idNota != null && !idNota.trim().equals("")) {
				id = Integer.parseInt(idNota);
				
				try {
					nota = new Nota();
					nota.setId(id);
					nota.setSituacaoEstoque(new NotaDAO().getSituacaoById(id).getSituacaoEstoque());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				if (idItemNota != null && !idItemNota.trim().equals("")) {
					EntidadeDominio ed = new ItemNotaViewHelper().getEntidade(request);
					if (ed instanceof Nota) {
						Nota notaDeItem = (Nota) new ItemNotaViewHelper().getEntidade(request);
						nota.setItensNota(notaDeItem.getItensNota());
					} else if (ed instanceof ItemNota) {
						ItemNota itemNota = (ItemNota) ed;
						List<ItemNota> itens = new ArrayList<ItemNota>();
						itens.add(itemNota);
						nota.setItensNota(itens);
					}
				}
			}
		} else {
			Resultado resultado = (Resultado) request.getSession().getAttribute("resultado");
			idNota = request.getParameter("IdNota");
			int id = 0;
			
			if(idNota != null && !idNota.trim().equals("")) {
				id = Integer.parseInt(idNota);
			}
			
			if (resultado != null) {
				for(EntidadeDominio e : resultado.getEntidades()) {
					if(e != null && e.getId() != null && id > 0 && e.getId() == id) {
						nota = (Nota) e;
					}
				}
			}
		}
		
		return nota;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		RequestDispatcher d = null;
		
		String operacao = request.getParameter("operacao");
		
		if(resultado.getMsg() == null) {
			request.getSession().setAttribute("resultado", resultado);
			
			if (operacao.equals("SALVAR") || operacao.equals("ALTERAR")) {
				d = request.getRequestDispatcher("cadastra_notas.jsp");
			} else if (operacao.equals("VISUALIZAR")) {
				try {
					String filter = request.getParameter("search");
					List<EntidadeDominio> notas = new NotaDAO().getLista(filter);
					request.setAttribute("notas", notas);
				} catch (SQLException e) {
					resultado.setMsg("Não foi possível listar notas.");
				}
				
				d = request.getRequestDispatcher("consulta_notas.jsp");
				
			} else if (operacao.equals("CONSULTAR")) {
				try {
					int IdNota = Integer.parseInt(request.getParameter("IdNota"));
					Nota nota = (Nota) new NotaDAO().getEntidadeDominio(IdNota);
					nota.setItensNota(new ItemNotaDAO().getListaByNota(IdNota));
					
					request.setAttribute("nota", nota);
				} catch (SQLException e) {
					resultado.setMsg("Não foi possível listar nota.");
				}
				
				d = request.getRequestDispatcher("cadastra_notas.jsp");
			} else if (operacao.equals("EXCLUIR")) {
				d = request.getRequestDispatcher("consulta_notas.jsp");				
			}
		} else {
			request.setAttribute("msg", resultado.getMsg());
			
			d = request.getRequestDispatcher("msg-erro.jsp");
		}
				
		if (d != null)
			d.forward(request, response);
	}
}