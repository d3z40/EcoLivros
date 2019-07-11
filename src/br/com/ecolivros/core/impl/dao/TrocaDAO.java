package br.com.ecolivros.core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ecolivros.core.util.UsuarioLogado;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Estoque;
import br.com.ecolivros.dominio.ItemPedido;
import br.com.ecolivros.dominio.Pedido;
import br.com.ecolivros.dominio.PreCupom;
import br.com.ecolivros.dominio.Troca;
import br.com.ecolivros.enuns.SituacaoEstoque;
import br.com.ecolivros.enuns.SituacaoItem;

public class TrocaDAO extends AbstractJdbcDAO {

	public TrocaDAO() {
		super("", "");
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		Troca trocaPai = (Troca) entidade;
		ItemPedidoDAO itemPedidoDAO;
		PedidoDAO pedidoDAO;
		PreCupomDAO preCupomDAO;
		EstoqueDAO estoqueDAO;
		
		ItemPedido itemPedido;
		Pedido pedido;
		PreCupom preCupom;
		Estoque estoque;
		
		if (trocaPai != null && trocaPai.getTrocas() != null && !trocaPai.getTrocas().isEmpty()) {
			itemPedidoDAO = new ItemPedidoDAO();
			pedidoDAO = new PedidoDAO();
			preCupomDAO = new PreCupomDAO();
			estoqueDAO = new EstoqueDAO();
			
			for (Troca troca : trocaPai.getTrocas()) {
				if (troca.getAprovado() == 1) {
					itemPedido = itemPedidoDAO.getItemPedidoById(troca.getIdItemPedido());
					pedido = pedidoDAO.getPedidoById(itemPedido.getIdPedido());
					
					preCupom = new PreCupom();
					preCupom.setIdUsuario(pedido.getIdUsuario());
					preCupom.setIdItemPedido(itemPedido.getId());
					preCupom.setValor(itemPedido.getValor());
					preCupom.setSituacaoEstoque(SituacaoEstoque.PENDENTE);
					preCupom.setDtCadastro(new Date());
					preCupomDAO.salvar(preCupom);
					
					estoque = new Estoque();
					estoque.setId(itemPedido.getIdEstoque());
					estoque.setSituacaoEstoque(SituacaoEstoque.PENDENTE);
					estoqueDAO.alterar(estoque);
					
					itemPedidoDAO.alterarSituacaoItem(itemPedido.getId(), SituacaoItem.DEVOLVIDO);
				} else {
					itemPedidoDAO.alterarSituacaoItem(troca.getIdItemPedido(), SituacaoItem.CANCELADO);
				}
			}
		}
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		return null;
	}
	
	public List<Troca> getLista() throws SQLException {
		openConnection();
		PreparedStatement pst;
		List<Troca> trocas = new	ArrayList<Troca>();
		
		Troca troca = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("(SELECT COUNT(itemped.id) FROM itempedido AS itemped WHERE itemped.iSituacaoItem = 2) AS QtdeTroca, ");
		sql.append("p.id AS iPedido, ");
		sql.append("p.iUsuario, ");
		sql.append("p.sNumeroPedido, ");
		sql.append("p.nTotal, ");
		sql.append("l.id AS iLivro, ");
		sql.append("l.sTitulo, ");
		sql.append("i.id AS iItemPedido, ");
		sql.append("i.nValor ");
		sql.append("FROM pedido AS p ");
		sql.append("INNER JOIN itempedido AS i ON i.iPedido = p.id ");
		sql.append("INNER JOIN livro AS l ON l.id = i.iLivro ");
		sql.append("WHERE i.iSituacaoItem = 2 ");
		sql.append("ORDER BY p.iUsuario, p.id, l.id, i.id");
				
		pst = connection.prepareStatement(sql.toString());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int idPedido = rs.getInt("iPedido");
			int idUsuario = rs.getInt("iUsuario");
			String numPedido = rs.getString("sNumeroPedido");
			double total = rs.getDouble("nTotal");
			int idLivro = rs.getInt("iLivro");
			String titulo = rs.getString("sTitulo");
			int idItemPedido = rs.getInt("iItemPedido");
			double valor = rs.getDouble("nValor");
			
			troca	= new Troca();
			troca.setIdPedido(idPedido);
			troca.setIdUsuario(idUsuario);
			troca.setNumeroPedido(numPedido);
			troca.setTotal(total);
			
			troca.setIdLivro(idLivro);
			troca.setTitulo(titulo);
			troca.setIdItemPedido(idItemPedido);
			troca.setValor(valor);
			
			trocas.add(troca);
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return trocas;
	}
	
	public List<EntidadeDominio> getLista(String filter) throws SQLException {
		openConnection();
		PreparedStatement pst;
		List<EntidadeDominio> trocas = new	ArrayList<EntidadeDominio>();
		
		Troca troca = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("p.id AS iPedido, ");
		sql.append("p.iUsuario, ");
		sql.append("p.sNumeroPedido, ");
		sql.append("p.nTotal, ");
		sql.append("l.id AS iLivro, ");
		sql.append("l.sTitulo, ");
		sql.append("i.id AS iItemPedido, ");
		sql.append("i.nValor, ");
		sql.append("i.iSituacaoItem ");
		sql.append("FROM pedido AS p ");
		sql.append("INNER JOIN itempedido AS i ON i.iPedido = p.id ");
		sql.append("INNER JOIN livro AS l ON l.id = i.iLivro ");
		sql.append("WHERE i.iSituacaoItem IN (2, 3, 4) AND p.iUsuario = ? ");
		if (!filter.equals(""))
			sql.append(" AND l.sTitulo LIKE '%" + filter + "%' ");
		sql.append("ORDER BY p.iUsuario, p.id, l.id, i.id");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, UsuarioLogado.getUsuario().getId());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int idPedido = rs.getInt("iPedido");
			int idUsuario = rs.getInt("iUsuario");
			String numPedido = rs.getString("sNumeroPedido");
			double total = rs.getDouble("nTotal");
			int idLivro = rs.getInt("iLivro");
			String titulo = rs.getString("sTitulo");
			int idItemPedido = rs.getInt("iItemPedido");
			double valor = rs.getDouble("nValor");
			int sitItem = rs.getInt("iSituacaoItem");
			
			troca = new Troca();
			troca.setIdPedido(idPedido);
			troca.setIdUsuario(idUsuario);
			troca.setNumeroPedido(numPedido);
			troca.setTotal(total);
			troca.setIdLivro(idLivro);
			troca.setTitulo(titulo);
			troca.setIdItemPedido(idItemPedido);
			troca.setValor(valor);
			
			for (SituacaoItem situacaoItem : SituacaoItem.values()) {
				if (situacaoItem.getCodigo() == sitItem) {
					troca.setSituacaoItem(situacaoItem);
					break;
				}
			}
			
			trocas.add(troca);
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return trocas;
	}
	
	public int getQtdeTroca() throws SQLException {
		openConnection();
		PreparedStatement pst;
		int qtdeTroca = 0;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(itemped.id) AS nQtdeTroca FROM itempedido AS itemped WHERE itemped.iSituacaoItem = 2");
				
		pst = connection.prepareStatement(sql.toString());
		
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()) {
			qtdeTroca = rs.getInt("nQtdeTroca");
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return qtdeTroca;
	}
}