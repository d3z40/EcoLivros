package br.com.ecolivros.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import br.com.ecolivros.dominio.Relatorio;

public class GeradorDeGrafico {
	
	public static void geraRelatorioLinhasByDia(List<Relatorio> relatorios) {
		DefaultCategoryDataset dsQtde = new DefaultCategoryDataset();
		DefaultCategoryDataset dsValor = new DefaultCategoryDataset();
		
		for (Relatorio relatorio : relatorios) {
			dsQtde.addValue(relatorio.getQtde(), relatorio.getTitulo(), relatorio.getData());
			dsValor.addValue(relatorio.getValor(), relatorio.getTitulo(), relatorio.getData());
		}
		
		JFreeChart graficoQtde = ChartFactory.createLineChart("Qtde de Venda de Livros", "Meses", "Qtdes", dsQtde, PlotOrientation.VERTICAL, true, true, false);
		JFreeChart graficoValor = ChartFactory.createLineChart("Valor de Venda de Livros", "Meses", "Valores", dsValor, PlotOrientation.VERTICAL, true, true, false);
		
		CategoryPlot plotQtde = graficoQtde.getCategoryPlot();
		plotQtde.getRenderer().setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		plotQtde.getRenderer().setBaseItemLabelsVisible(true);
		
		CategoryPlot plotValor = graficoValor.getCategoryPlot();
		plotValor.getRenderer().setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		plotValor.getRenderer().setBaseItemLabelsVisible(true);
		
		try {
			File file = new File("/home/d3z40/eclipse-workspace/EcoLivros/WebContent/images/GraficoLinha_Qtde.jpeg");
			OutputStream arquivo = new FileOutputStream(file);
			ChartUtilities.writeChartAsJPEG(arquivo, graficoQtde, 550, 400);
			arquivo.close();
			
			file = new File("/home/d3z40/eclipse-workspace/apache-tomcat-9.0.11/wtpwebapps/EcoLivros/images/GraficoLinha_Qtde.jpeg");
			arquivo = new FileOutputStream(file);
			ChartUtilities.writeChartAsJPEG(arquivo, graficoQtde, 550, 400);
			arquivo.close();
			
			file = new File("/home/d3z40/eclipse-workspace/EcoLivros/WebContent/images/GraficoLinha_Valor.jpeg");
			arquivo = new FileOutputStream(file);
			ChartUtilities.writeChartAsJPEG(arquivo, graficoValor, 550, 400);
			arquivo.close();
			
			file = new File("/home/d3z40/eclipse-workspace/apache-tomcat-9.0.11/wtpwebapps/EcoLivros/images/GraficoLinha_Valor.jpeg");
			arquivo = new FileOutputStream(file);
			ChartUtilities.writeChartAsJPEG(arquivo, graficoValor, 550, 400);
			arquivo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void geraRelatorioLinhasByMes(List<Relatorio> relatorios) {
		DefaultCategoryDataset dsQtde = new DefaultCategoryDataset();
		DefaultCategoryDataset dsValor = new DefaultCategoryDataset();
		
		for (Relatorio relatorio : relatorios) {
			dsQtde.addValue(relatorio.getQtde(), relatorio.getTitulo(), relatorio.getMes() + "/" + relatorio.getAno());
			dsValor.addValue(relatorio.getValor(), relatorio.getTitulo(), relatorio.getMes() + "/" + relatorio.getAno());
		}
		
		JFreeChart graficoQtde = ChartFactory.createLineChart("Qtde de Venda de Livros", "Meses", "Qtdes", dsQtde, PlotOrientation.VERTICAL, true, true, false);
		JFreeChart graficoValor = ChartFactory.createLineChart("Valor de Venda de Livros", "Meses", "Valores", dsValor, PlotOrientation.VERTICAL, true, true, false);
		
		CategoryPlot plotQtde = graficoQtde.getCategoryPlot();
		plotQtde.getRenderer().setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		plotQtde.getRenderer().setBaseItemLabelsVisible(true);
		
		CategoryPlot plotValor = graficoValor.getCategoryPlot();
		plotValor.getRenderer().setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		plotValor.getRenderer().setBaseItemLabelsVisible(true);
		
		try {
			File file = new File("/home/d3z40/eclipse-workspace/EcoLivros/WebContent/images/GraficoLinha_Qtde.jpeg");
			OutputStream arquivo = new FileOutputStream(file);
			ChartUtilities.writeChartAsJPEG(arquivo, graficoQtde, 550, 400);
			arquivo.close();
			
			file = new File("/home/d3z40/eclipse-workspace/apache-tomcat-9.0.11/wtpwebapps/EcoLivros/images/GraficoLinha_Qtde.jpeg");
			arquivo = new FileOutputStream(file);
			ChartUtilities.writeChartAsJPEG(arquivo, graficoQtde, 550, 400);
			arquivo.close();
			
			file = new File("/home/d3z40/eclipse-workspace/EcoLivros/WebContent/images/GraficoLinha_Valor.jpeg");
			arquivo = new FileOutputStream(file);
			ChartUtilities.writeChartAsJPEG(arquivo, graficoValor, 550, 400);
			arquivo.close();
			
			file = new File("/home/d3z40/eclipse-workspace/apache-tomcat-9.0.11/wtpwebapps/EcoLivros/images/GraficoLinha_Valor.jpeg");
			arquivo = new FileOutputStream(file);
			ChartUtilities.writeChartAsJPEG(arquivo, graficoValor, 550, 400);
			arquivo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void geraRelatorioBarraByData(List<Relatorio> relatorios) {
		DefaultCategoryDataset dsQtde = new DefaultCategoryDataset();
		DefaultCategoryDataset dsValor = new DefaultCategoryDataset();
		
		for (Relatorio relatorio : relatorios) {
			dsQtde.addValue(relatorio.getQtde(), "Livros", relatorio.getTitulo());
			dsValor.addValue(relatorio.getValor(), "Livros", relatorio.getTitulo());
		}
		
		JFreeChart graficoQtde = ChartFactory.createBarChart("Qtde de Venda de Livros", "Livros", "Qtdes", dsQtde, PlotOrientation.VERTICAL, true, true, false);
		JFreeChart graficoValor = ChartFactory.createBarChart("Valor de Venda de Livro", "Livros", "Valores", dsValor, PlotOrientation.VERTICAL, true, true, false);
		
		CategoryPlot plotQtde = graficoQtde.getCategoryPlot();
		plotQtde.getRenderer().setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		plotQtde.getRenderer().setBaseItemLabelsVisible(true);
		
		CategoryPlot plotValor = graficoValor.getCategoryPlot();
		plotValor.getRenderer().setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		plotValor.getRenderer().setBaseItemLabelsVisible(true);
		
		try {
			File file = new File("/home/d3z40/eclipse-workspace/EcoLivros/WebContent/images/GraficoBarra_Qtde.jpeg");
			OutputStream arquivo = new FileOutputStream(file);
			ChartUtilities.writeChartAsJPEG(arquivo, graficoQtde, 550, 400);
			arquivo.close();
			
			file = new File("/home/d3z40/eclipse-workspace/apache-tomcat-9.0.11/wtpwebapps/EcoLivros/images/GraficoBarra_Qtde.jpeg");
			arquivo = new FileOutputStream(file);
			ChartUtilities.writeChartAsJPEG(arquivo, graficoQtde, 550, 400);
			arquivo.close();
			
			file = new File("/home/d3z40/eclipse-workspace/EcoLivros/WebContent/images/GraficoBarra_Valor.jpeg");
			arquivo = new FileOutputStream(file);
			ChartUtilities.writeChartAsJPEG(arquivo, graficoValor, 550, 400);
			arquivo.close();
			
			file = new File("/home/d3z40/eclipse-workspace/apache-tomcat-9.0.11/wtpwebapps/EcoLivros/images/GraficoBarra_Valor.jpeg");
			arquivo = new FileOutputStream(file);
			ChartUtilities.writeChartAsJPEG(arquivo, graficoValor, 550, 400);
			arquivo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}