package br.com.ecolivros.core.impl.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import br.com.ecolivros.dominio.EntidadeDominio;

public class GeradorDeGraficoDAO extends AbstractJdbcDAO {

	public GeradorDeGraficoDAO() {
		super("", "id");
	}
	
	public GeradorDeGraficoDAO(Connection connection) {
		super(connection, "", "id");
		ctrlTransaction = false;
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		PreparedStatement pst;
		DefaultCategoryDataset dsQtde;
		DefaultCategoryDataset dsValor;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("l.sTitulo, ");
		sql.append("SUM(i.nQtde) AS nQtde, ");
		sql.append("SUM(i.nValor) AS nValor ");
		sql.append("FROM itempedido i ");
		sql.append("INNER JOIN livro l ON l.id = i.iLivro ");
		sql.append("WHERE i.iSituacaoItem = 1 ");
		sql.append("GROUP BY l.sTitulo");
		
		pst = connection.prepareStatement(sql.toString());
		
		ResultSet rs = pst.executeQuery();
		
		dsQtde = new DefaultCategoryDataset();
		dsValor = new DefaultCategoryDataset();
		
		while (rs.next()) {
			String sTitulo = rs.getString("sTitulo");
			double nQtde = rs.getDouble("nQtde");
			double nValor = rs.getDouble("nValor");
			
			dsQtde.addValue(nQtde, "Venda", sTitulo);
			dsValor.addValue(nValor, "Venda", sTitulo);
		}
		
		JFreeChart graficoQtde = ChartFactory.createBarChart3D("Qtde de Venda de Livros", "Livros", "Qtde", dsQtde, PlotOrientation.VERTICAL, true, true, false);
		JFreeChart graficoValor = ChartFactory.createBarChart3D("Valor de Venda de Livros", "Livros", "Valor", dsValor, PlotOrientation.VERTICAL, true, true, false);
		
		CategoryPlot plotQtde = graficoQtde.getCategoryPlot();
		plotQtde.getRenderer().setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		plotQtde.getRenderer().setBaseItemLabelsVisible(true);
		
		CategoryPlot plotValor = graficoValor.getCategoryPlot();
		plotValor.getRenderer().setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		plotValor.getRenderer().setBaseItemLabelsVisible(true);
		
		try {
			File file = new File("Imagens/GraficoQtde.jpeg");
			OutputStream arquivo = new FileOutputStream(file);
			ChartUtilities.writeChartAsJPEG(arquivo, graficoQtde, 550, 400);
			arquivo.close();
			
			file = new File("Imagens/GraficoValor.jpeg");
			arquivo = new FileOutputStream(file);
			ChartUtilities.writeChartAsJPEG(arquivo, graficoValor, 550, 400);
			arquivo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		rs.close();
		pst.close();
		connection.close();
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		return null;
	}
}