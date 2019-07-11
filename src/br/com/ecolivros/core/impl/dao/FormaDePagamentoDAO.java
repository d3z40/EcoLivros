package br.com.ecolivros.core.impl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.ecolivros.core.util.UsuarioLogado;
import br.com.ecolivros.dominio.Boleto;
import br.com.ecolivros.dominio.CartaoDeCredito;
import br.com.ecolivros.dominio.Cupom;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.FormaDePagamento;
import br.com.ecolivros.dominio.Pagamento;
import br.com.ecolivros.enuns.TipoPagamento;

public class FormaDePagamentoDAO extends AbstractJdbcDAO {

	public FormaDePagamentoDAO() {
		super("formadepagamento", "id");
	}
	
	public FormaDePagamentoDAO(Connection connection) {
		super(connection, "formadepagamento", "id");
		ctrlTransaction = false;
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		StringBuilder sql;
		PreparedStatement pst = null;
		
		FormaDePagamento formaDePagamento = (FormaDePagamento) entidade;

		try {
			connection.setAutoCommit(false);
			
			if (formaDePagamento.getTipoPagamento().getCodigo() == TipoPagamento.BOLETO.getCodigo()) {
				int seq = new BoletoDAO(connection).getMaxId();
				
				Boleto boleto = new Boleto();
				boleto.setIdUsuario(UsuarioLogado.getUsuario().getId());
				
				String codBarras = String.format("%03d%05d", UsuarioLogado.getUsuario().getId(), ++seq);
				
				Calendar data = Calendar.getInstance();
				data.setTime(new Date());
				data.add(Calendar.DAY_OF_MONTH, 5);
				codBarras += String.format("%02d%02d%04d",
											data.get(Calendar.DAY_OF_MONTH),
											data.get(Calendar.MONTH),
											data.get(Calendar.YEAR));
				
				codBarras += String.format("%6.0f", formaDePagamento.getValor());
				
				boleto.setCodigoBarras(codBarras);
				boleto.setDtCadastro(new Date());
				
				Integer idBoleto = new BoletoDAO(connection).salvar(boleto);
				boleto.setId(idBoleto);
				
				formaDePagamento.setPagamento(boleto);
			} else if (formaDePagamento.getTipoPagamento().getCodigo() == TipoPagamento.CUPOM.getCodigo()) {
				Cupom cupom = (Cupom) new CupomDAO().getCupomById(formaDePagamento.getPagamento().getId());
				cupom.setValorUtilizado(cupom.getValorUtilizado() + formaDePagamento.getValor());
				new CupomDAO(connection).alterar(cupom);
			}
			
			sql = new StringBuilder();
			sql.append("INSERT INTO formadepagamento (iPedido, iPagamento, nValor, iTipoPagamento, dtCadastro) ");
			sql.append("VALUES (?,?,?,?,?)");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setInt(1, formaDePagamento.getIdPedido());
			pst.setInt(2, formaDePagamento.getPagamento().getId());
			pst.setDouble(3, formaDePagamento.getValor());
			pst.setInt(4, formaDePagamento.getTipoPagamento().getCodigo());
			
			Timestamp time = new Timestamp(formaDePagamento.getDtCadastro().getTime());
			pst.setTimestamp(5, time);
			
			pst.executeUpdate();
			
			if (ctrlTransaction)
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
				if (ctrlTransaction)
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
		
		FormaDePagamento formaDePagamento = (FormaDePagamento) entidade;
		
		try {
			connection.setAutoCommit(false);
			
			sql = new StringBuilder();
			
			sql.append("UPDATE formadepagamento SET nValor = ? WHERE id = ?");
			
			pst = connection.prepareStatement(sql.toString());
			
			pst.setDouble(1, formaDePagamento.getValor());
			pst.setInt(2, formaDePagamento.getId());
			
			pst.executeUpdate();
			
			if (ctrlTransaction)
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
				if (ctrlTransaction)
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
		List<EntidadeDominio> formasDePagamento = new	ArrayList<EntidadeDominio>();
		
		FormaDePagamento formaDePagamento = (FormaDePagamento) entidade;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM formadepagamento");
		sql.append(" WHERE iPedido = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, formaDePagamento.getIdPedido());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idPedido = rs.getInt("iPedido");
			int idPagamento = rs.getInt("iPagamento");
			double valor = rs.getDouble("nValor");
			int iTipoPagamento = rs.getInt("iTipoPagamento");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			formaDePagamento	= new FormaDePagamento();
			formaDePagamento.setId(id);
			formaDePagamento.setIdPedido(idPedido);
			formaDePagamento.setValor(valor);
			formaDePagamento.setDtCadastro(dtCadastro);
			
			for (TipoPagamento tipoPagamento : TipoPagamento.values()) {
				if (tipoPagamento.getCodigo() == iTipoPagamento) {
					formaDePagamento.setTipoPagamento(tipoPagamento);
					break;
				}
			}
			
			Pagamento pagamento = null;
			if (iTipoPagamento == TipoPagamento.BOLETO.getCodigo()) {
				pagamento = new BoletoDAO(connection).getBoletoById(idPagamento);
			} else if (iTipoPagamento == TipoPagamento.CARTAODECREDITO.getCodigo()) {
				pagamento = new CartaoDeCreditoDAO(connection).getCartaoDeCreditoById(idPagamento);
			} else if (iTipoPagamento == TipoPagamento.CUPOM.getCodigo()) {
				pagamento = new CupomDAO(connection).getCupomById(idPagamento);
			}
			
			formaDePagamento.setPagamento(pagamento);
			
			formasDePagamento.add(formaDePagamento);
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return formasDePagamento;
	}
	
	public List<FormaDePagamento> getFormaDePgtoIdPedido(int iPedido) throws SQLException {
		openConnection();
		PreparedStatement pst;
		List<FormaDePagamento> formasDePgto = new	ArrayList<FormaDePagamento>();
		
		FormaDePagamento formaDePagamento = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM formadepagamento");
		sql.append(" WHERE iPedido = ?");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, iPedido);
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int idPedido = rs.getInt("iPedido");
			int idPagamento = rs.getInt("iPagamento");
			double valor = rs.getDouble("nValor");
			int iTipoPagamento = rs.getInt("iTipoPagamento");
			Date dtCadastro = rs.getDate("dtCadastro");
			
			formaDePagamento	= new FormaDePagamento();
			formaDePagamento.setId(id);
			formaDePagamento.setIdPedido(idPedido);
			formaDePagamento.setValor(valor);
			formaDePagamento.setDtCadastro(dtCadastro);
			
			for (TipoPagamento tipoPagamento : TipoPagamento.values()) {
				if (tipoPagamento.getCodigo() == iTipoPagamento) {
					formaDePagamento.setTipoPagamento(tipoPagamento);
					break;
				}
			}
			
			Pagamento pagamento = null;
			if (iTipoPagamento == TipoPagamento.BOLETO.getCodigo()) {
				pagamento = new BoletoDAO().getBoletoById(idPagamento);
			} else if (iTipoPagamento == TipoPagamento.CARTAODECREDITO.getCodigo()) {
				pagamento = new CartaoDeCreditoDAO().getCartaoDeCreditoById(idPagamento);
			} else if (iTipoPagamento == TipoPagamento.CUPOM.getCodigo()) {
				pagamento = new CupomDAO().getCupomById(idPagamento);
			}
			
			formaDePagamento.setPagamento(pagamento);
			
			formasDePgto.add(formaDePagamento);
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return formasDePgto;
	}
	
	public List<FormaDePagamento> getFormasDePagamentoByUsuario(int idUsuario) throws SQLException {
		List<FormaDePagamento> formasDePagamento = new ArrayList<FormaDePagamento>();	
		FormaDePagamento formaDePagamento;
		List<CartaoDeCredito> cartoesDeCredito = new ArrayList<CartaoDeCredito>();
		List<Cupom> cupons = new ArrayList<Cupom>();
		
		cartoesDeCredito = new CartaoDeCreditoDAO().getCartaoDeCreditoByUsuario(idUsuario) ;
		cupons = new CupomDAO().getCupomByUsuario(idUsuario);
		
		for (CartaoDeCredito cartaoDeCredito : cartoesDeCredito) {
			formaDePagamento = new FormaDePagamento();
			formaDePagamento.setPagamento(cartaoDeCredito);
			formaDePagamento.setTipoPagamento(TipoPagamento.CARTAODECREDITO);
			
			formasDePagamento.add(formaDePagamento);
		}
		
		for (Cupom cupom : cupons) {
			formaDePagamento = new FormaDePagamento();
			formaDePagamento.setPagamento(cupom);
			formaDePagamento.setTipoPagamento(TipoPagamento.CUPOM);
			
			formasDePagamento.add(formaDePagamento);
		}
		
		return formasDePagamento;
	}
}