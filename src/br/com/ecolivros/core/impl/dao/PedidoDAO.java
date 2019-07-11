package br.com.ecolivros.core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ecolivros.core.util.UsuarioLogado;
import br.com.ecolivros.dominio.Carrinho;
import br.com.ecolivros.dominio.Endereco;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Estoque;
import br.com.ecolivros.dominio.FormaDePagamento;
import br.com.ecolivros.dominio.ItemPedido;
import br.com.ecolivros.dominio.Pedido;
import br.com.ecolivros.enuns.Frete;
import br.com.ecolivros.enuns.SituacaoEstoque;
import br.com.ecolivros.enuns.SituacaoItem;

public class PedidoDAO extends AbstractJdbcDAO {

	public PedidoDAO() {
		super("pedido", "id");
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		Integer idPedido = null;
		StringBuilder sql;
		PreparedStatement pst = null;
		
		Pedido pedido = (Pedido) entidade;

		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			sql.append("INSERT INTO pedido (iUsuario, sNumeroPedido, iFrete, iEndereco, nTotal, dtCadastro) ");
			sql.append("VALUES (?,?,?,?,?,?)");
			
			pst = connection.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			
			pst.setInt(1, pedido.getIdUsuario());
			pst.setString(2, pedido.getNumPedido());
			pst.setInt(3, pedido.getFrete().getCodigo());
			pst.setInt(4, pedido.getEndereco().getId());
			pst.setDouble(5, pedido.getTotal());
			
			Timestamp time = new Timestamp(pedido.getDtCadastro().getTime());
			pst.setTimestamp(6, time);

			pst.executeUpdate();
			
			ResultSet rs = pst.getGeneratedKeys();
			boolean insertedPedido = rs.next();
			if (insertedPedido) {
				idPedido = rs.getInt(1);
				pedido.setId(idPedido);
				rs.close();
			}
			
			if (insertedPedido && pedido.getFormasDePgto().size() > 0) {
				for (FormaDePagamento formaDePagamento : pedido.getFormasDePgto()) {
					formaDePagamento.setIdPedido(idPedido);
					new FormaDePagamentoDAO(connection).salvar(formaDePagamento);
				}
			}
			
			if (insertedPedido && pedido.getItensPedido().size() > 0) {
				for (ItemPedido itemPedido : pedido.getItensPedido()) {
					itemPedido.setIdPedido(idPedido);
					new ItemPedidoDAO(connection).salvar(itemPedido);
					
					Estoque estoque = new Estoque();
					estoque.setId(itemPedido.getIdEstoque());
					estoque.setSituacaoEstoque(SituacaoEstoque.BAIXADO);
					new EstoqueDAO(connection).alterar(estoque);
				}
			}
			
			CarrinhoDAO carrinhoDAO = new CarrinhoDAO(connection);
			List<Carrinho> carrinhos = new CarrinhoDAO().getCarrinhoByIdUsuarioSituacao(SituacaoEstoque.PARCIAL);
			for (Carrinho carrinho : carrinhos) {
				carrinhoDAO.alterarSituacaoEstoqueByIdCarrinhoSituacaoEstoque(carrinho.getId(), SituacaoEstoque.BAIXADO);
			}
			
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		StringBuilder sql;
		PreparedStatement pst = null;
		
		Pedido pedido = (Pedido) entidade;
		
		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			
			sql.append("UPDATE pedido SET sNumPedido = ? WHERE id = ?");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setString(1, pedido.getNumPedido());
			pst.setInt(2, pedido.getId());
			
			pst.executeUpdate();
			
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		PreparedStatement pst;
		List<EntidadeDominio> pedidos = new	ArrayList<EntidadeDominio>();
		
		Pedido pedido = (Pedido) entidade;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM pedido WHERE id = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, pedido.getId());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idUsuario = rs.getInt("iUsuario");
			int idEndreco = rs.getInt("iEndereco");
			int iFrete = rs.getInt("iFrete");
			String numPedido = rs.getString("sNumeroPedido");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			pedido	= new Pedido();
			pedido.setId(id);
			pedido.setIdUsuario(idUsuario);
			pedido.setNumPedido(numPedido);
			pedido.setDtCadastro(dtCadastro);
			
			pedido.setFormasDePgto(new FormaDePagamentoDAO().getFormaDePgtoIdPedido(id));
			pedido.setItensPedido(new ItemPedidoDAO().getItemPedidoIdPedido(id));
			pedido.setEndereco((Endereco) new EnderecoDAO().getEntidadeDominioEndreco(idEndreco));
			
			for (Frete frete : Frete.values()) {
				if (iFrete == frete.getCodigo()) {
					pedido.setFrete(frete);
					break;
				}
			}
			
			String uf = pedido.getEndereco().getUf();
			for (Frete frete : Frete.values()) {
				if (uf.equals(frete.name())) {
					pedido.setFrete(frete);
					break;
				}
			}
			
			pedidos.add(pedido);
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return pedidos;
	}
	
	public Pedido getPedido(List<EntidadeDominio> carrinhos) {
		EstoqueDAO estoqueDAO = new EstoqueDAO();
		List<Estoque> listEstoques = new ArrayList<Estoque>();
		List<ItemPedido> itensPedido = new ArrayList<ItemPedido>();
		CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
		ItemPedido itemPedido;
		double totalPedido = 0;
		
		for (EntidadeDominio ed : carrinhos) {
			Carrinho carrinho = (Carrinho) ed;
			
			try {
				Estoque[] estoques = estoqueDAO.addListByIdLivroTop(carrinho.getIdLivro(), carrinho.getQtde());
				
				if (estoques != null && estoques.length > 0) {
					double somaValor = 0;
					for (Estoque estoque : estoques) {
						somaValor += estoque.getValor();
					}
				
					double valorMedio = somaValor / carrinho.getQtde();
					for (Estoque estoque : estoques) {
						estoque.setValor(valorMedio);
						
						listEstoques.add(estoque);
					}
				}
				
				carrinhoDAO.alterarSituacaoEstoqueByIdCarrinhoSituacaoEstoque(carrinho.getId(), SituacaoEstoque.PARCIAL);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		for (Estoque estoque : listEstoques) {
			itemPedido = new ItemPedido();
			itemPedido.setIdEstoque(estoque.getId());
			itemPedido.setIdLivro(estoque.getIdLivro());
			itemPedido.setQtde(1); //Qtde sempre será 1, por que aponta para cada item no estoque, individualmente.
			itemPedido.setValor(estoque.getValor());
			itemPedido.setSituacaoItem(SituacaoItem.AGUARDANDO);
			
			totalPedido += estoque.getValor();
			
			itensPedido.add(itemPedido);
		}
		
		Pedido pedido = new Pedido();
		pedido.setIdUsuario(UsuarioLogado.getUsuario().getId());
		pedido.setItensPedido(itensPedido);
		pedido.setTotal(totalPedido);
		
		return pedido;
	}
	
	public List<EntidadeDominio> getLista(String filter) throws SQLException {
		openConnection();
		PreparedStatement pst;
		List<EntidadeDominio> pedidos = new	ArrayList<EntidadeDominio>();
		
		Pedido pedido = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM pedido WHERE iUsuario = ?");
		if (!filter.equals("")) {
			sql.append(" AND sNumeroPedido LIKE '%" + filter + "%'");
		}
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, UsuarioLogado.getUsuario().getId());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idUsuario = rs.getInt("iUsuario");
			String numPedido = rs.getString("sNumeroPedido");
			double total = rs.getDouble("nTotal");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			pedido	= new Pedido();
			pedido.setId(id);
			pedido.setIdUsuario(idUsuario);
			pedido.setNumPedido(numPedido);
			pedido.setTotal(total);
			pedido.setDtCadastro(dtCadastro);
			
			pedidos.add(pedido);
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return pedidos;
	}
	
	public List<EntidadeDominio> getLista() throws SQLException {
		return getLista("");
	}
	
	public Pedido getPedidoById(int idPedido) throws SQLException {
		openConnection();
		PreparedStatement pst;
		Pedido pedido = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM pedido WHERE id = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, idPedido);
		
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()) {
			int id = rs.getInt("id");
			int idUsuario = rs.getInt("iUsuario");
			int idEndreco = rs.getInt("iEndereco");
			int iFrete = rs.getInt("iFrete");
			String numPedido = rs.getString("sNumeroPedido");
			double total = rs.getDouble("nTotal");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			pedido	= new Pedido();
			pedido.setId(id);
			pedido.setIdUsuario(idUsuario);
			pedido.setNumPedido(numPedido);
			pedido.setTotal(total);
			pedido.setDtCadastro(dtCadastro);
			
			pedido.setFormasDePgto(new FormaDePagamentoDAO().getFormaDePgtoIdPedido(id));
			pedido.setItensPedido(new ItemPedidoDAO().getItemPedidoIdPedido(id));
			pedido.setEndereco((Endereco) new EnderecoDAO().getEntidadeDominioEndreco(idEndreco));
			
			for (Frete frete : Frete.values()) {
				if (iFrete == frete.getCodigo()) {
					pedido.setFrete(frete);
					break;
				}
			}
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return pedido;
	}
	
	
	public Integer getSeqPedidoByIdUsuario(int idUsuario) throws SQLException {
		openConnection();
		PreparedStatement pst;
		Integer valor = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(id) AS nValor FROM pedido WHERE iUsuario = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, idUsuario);
		
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()) {
			valor = rs.getInt("nValor");
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return valor;
	}
	
	public List<EntidadeDominio> getTrocasByPedidoEUsuario() throws SQLException {
		openConnection();
		PreparedStatement pst;
		List<EntidadeDominio> pedidos = new	ArrayList<EntidadeDominio>();
		
		Pedido pedido = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM pedido WHERE iUsuario = ?");
				
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, UsuarioLogado.getUsuario().getId());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idUsuario = rs.getInt("iUsuario");
			String numPedido = rs.getString("sNumeroPedido");
			double total = rs.getDouble("nTotal");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			pedido	= new Pedido();
			pedido.setId(id);
			pedido.setIdUsuario(idUsuario);
			pedido.setNumPedido(numPedido);
			pedido.setTotal(total);
			pedido.setDtCadastro(dtCadastro);
			
			pedidos.add(pedido);
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return pedidos;
	}
}