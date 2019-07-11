package br.com.ecolivros.core.impl.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.ecolivros.core.util.GeradorDeGrafico;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Relatorio;
import br.com.ecolivros.enuns.TipoRelatorio;

public class RelatorioDAO extends AbstractJdbcDAO {

	public RelatorioDAO() {
		super("", "");
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		List<Relatorio> relatorios;
		Relatorio relatorio = (Relatorio) entidade;
		
		if (relatorio.getTipoRelatorio().equals(TipoRelatorio.PORDIA)) {
			relatorios = getRelatorioByDia(relatorio);
			GeradorDeGrafico.geraRelatorioLinhasByDia(relatorios);
		} else if (relatorio.getTipoRelatorio().equals(TipoRelatorio.PORMES)) {
			relatorios = getRelatorioByMes(relatorio);
			GeradorDeGrafico.geraRelatorioLinhasByMes(relatorios);
		}
		
		relatorios = getRelatorioBarraByData(relatorio);
		GeradorDeGrafico.geraRelatorioBarraByData(relatorios);
	}
	
	private List<Relatorio> getRelatorioByDia(Relatorio rel) throws SQLException {
		openConnection();
		PreparedStatement pst;
		ResultSet rs;
		List<Relatorio> relatorios = new ArrayList<Relatorio>();
		Relatorio relatorio;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("l.sTitulo, ");
		sql.append("i.dtCadastro, ");
		sql.append("SUM(i.nQtde) AS nQtde, ");
		sql.append("SUM(i.nValor) AS nValor ");
		sql.append("FROM itempedido i ");
		sql.append("INNER JOIN livro l ON l.id = i.iLivro ");
		sql.append("WHERE i.iSituacaoItem = 1 AND i.dtCadastro BETWEEN ? AND ? ");
		sql.append("GROUP BY l.sTitulo, i.dtCadastro");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setDate(1, new java.sql.Date(rel.getDataInicial().getTime()));
		pst.setDate(2, new java.sql.Date(rel.getDataFinal().getTime()));
		
		rs = pst.executeQuery();
		
		while (rs.next()) {
			String titulo = rs.getString("sTitulo");
			Date data = rs.getDate("dtCadastro");
			double qtde = rs.getDouble("nQtde");
			double valor = rs.getDouble("nValor");
			
			relatorio = new Relatorio();
			relatorio.setTitulo(titulo);
			relatorio.setData(data);
			relatorio.setQtde(qtde);
			relatorio.setValor(valor);
			relatorio.setTipoRelatorio(TipoRelatorio.PORDIA);
			
			relatorios.add(relatorio);
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return relatorios;
	}
	
	private List<Relatorio> getRelatorioByMes(Relatorio rel) throws SQLException {
		openConnection();
		PreparedStatement pst;
		ResultSet rs;
		List<Relatorio> relatorios = new ArrayList<Relatorio>();
		Relatorio relatorio;
		
		Calendar dataInicial = Calendar.getInstance();
		dataInicial.setTime(rel.getDataInicial());
		
		Calendar dataFinal = Calendar.getInstance();
		dataFinal.setTime(rel.getDataFinal());
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("l.sTitulo, ");
		sql.append("MONTH(i.dtCadastro) AS iMes, ");
		sql.append("YEAR(i.dtCadastro) AS iAno, ");
		sql.append("SUM(i.nQtde) AS nQtde, ");
		sql.append("SUM(i.nValor) AS nValor ");
		sql.append("FROM itempedido i ");
		sql.append("INNER JOIN livro l ON l.id = i.iLivro ");
		sql.append("WHERE i.iSituacaoItem = 1 AND MONTH(i.dtCadastro) BETWEEN ? AND ? AND YEAR(i.dtCadastro) BETWEEN ? AND ? ");
		sql.append("GROUP BY l.sTitulo, MONTH(i.dtCadastro), YEAR(i.dtCadastro)");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setInt(1, dataInicial.get(Calendar.MONTH) + 1);
		pst.setInt(2, dataFinal.get(Calendar.MONTH) + 1);
		pst.setInt(3, dataInicial.get(Calendar.YEAR));
		pst.setInt(4, dataFinal.get(Calendar.YEAR));
		
		rs = pst.executeQuery();
		
		while (rs.next()) {
			String titulo = rs.getString("sTitulo");
			int mes = rs.getInt("iMes");
			int ano = rs.getInt("iAno");
			double qtde = rs.getDouble("nQtde");
			double valor = rs.getDouble("nValor");
			
			relatorio = new Relatorio();
			relatorio.setTitulo(titulo);
			relatorio.setMes(mes);
			relatorio.setAno(ano);
			relatorio.setQtde(qtde);
			relatorio.setValor(valor);
			relatorio.setTipoRelatorio(TipoRelatorio.PORMES);
			
			relatorios.add(relatorio);
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return relatorios;
	}

	private List<Relatorio> getRelatorioBarraByData(Relatorio rel) throws SQLException {
		openConnection();
		PreparedStatement pst;
		ResultSet rs;
		List<Relatorio> relatorios = new ArrayList<Relatorio>();
		Relatorio relatorio;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("l.sTitulo, ");
		sql.append("SUM(i.nQtde) AS nQtde, ");
		sql.append("SUM(i.nValor) AS nValor ");
		sql.append("FROM itempedido i ");
		sql.append("INNER JOIN livro l ON l.id = i.iLivro ");
		sql.append("WHERE i.iSituacaoItem = 1 AND i.dtCadastro BETWEEN ? AND ? ");
		sql.append("GROUP BY l.sTitulo");
		
		pst = connection.prepareStatement(sql.toString());
		pst.setDate(1, new java.sql.Date(rel.getDataInicial().getTime()));
		pst.setDate(2, new java.sql.Date(rel.getDataFinal().getTime()));
		
		rs = pst.executeQuery();
		
		while (rs.next()) {
			String sTitulo = rs.getString("sTitulo");
			double nQtde = rs.getDouble("nQtde");
			double nValor = rs.getDouble("nValor");
			
			relatorio = new Relatorio();
			relatorio.setTitulo(sTitulo);
			relatorio.setQtde(nQtde);
			relatorio.setValor(nValor);
			
			relatorios.add(relatorio);
		}
		
		rs.close();
		pst.close();
		connection.close();
		
		return relatorios;
	}
	
	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		return null;
	}
}