package br.com.ecolivros.core.impl.controle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ecolivros.core.IDAO;
import br.com.ecolivros.core.IFachada;
import br.com.ecolivros.core.IStrategy;
import br.com.ecolivros.core.aplicacao.Resultado;
import br.com.ecolivros.core.impl.dao.AtualizaSistemaDAO;
import br.com.ecolivros.core.impl.dao.AutorDAO;
import br.com.ecolivros.core.impl.dao.CarrinhoDAO;
import br.com.ecolivros.core.impl.dao.CartaoDeCreditoDAO;
import br.com.ecolivros.core.impl.dao.CategoriaDAO;
import br.com.ecolivros.core.impl.dao.CupomDAO;
import br.com.ecolivros.core.impl.dao.EditoraDAO;
import br.com.ecolivros.core.impl.dao.EnderecoDAO;
import br.com.ecolivros.core.impl.dao.FuncionarioDAO;
import br.com.ecolivros.core.impl.dao.GrupoPrecificacaoDAO;
import br.com.ecolivros.core.impl.dao.ItemPedidoDAO;
import br.com.ecolivros.core.impl.dao.LivroDAO;
import br.com.ecolivros.core.impl.dao.NotaDAO;
import br.com.ecolivros.core.impl.dao.PedidoDAO;
import br.com.ecolivros.core.impl.dao.RelatorioDAO;
import br.com.ecolivros.core.impl.dao.TrocaDAO;
import br.com.ecolivros.core.impl.dao.UsuarioDAO;
import br.com.ecolivros.core.impl.negocio.ComplementarDtCadastro;
import br.com.ecolivros.core.impl.negocio.ValidadorCpf;
import br.com.ecolivros.core.impl.negocio.ValidadorDadosObrigatoriosAlterarCarrinho;
import br.com.ecolivros.core.impl.negocio.ValidadorDadosObrigatoriosCarrinho;
import br.com.ecolivros.core.impl.negocio.ValidadorDadosObrigatoriosCartaoDeCredito;
import br.com.ecolivros.core.impl.negocio.ValidadorDadosObrigatoriosEndereco;
import br.com.ecolivros.core.impl.negocio.ValidadorDadosObrigatoriosItemPedido;
import br.com.ecolivros.core.impl.negocio.ValidadorDadosObrigatoriosLivro;
import br.com.ecolivros.core.impl.negocio.ValidadorDadosObrigatoriosLogin;
import br.com.ecolivros.core.impl.negocio.ValidadorDadosObrigatoriosNota;
import br.com.ecolivros.core.impl.negocio.ValidadorDadosObrigatoriosPedido;
import br.com.ecolivros.core.impl.negocio.ValidadorDadosObrigatoriosRelatorio;
import br.com.ecolivros.core.impl.negocio.ValidadorDadosObrigatoriosTroca;
import br.com.ecolivros.core.impl.negocio.ValidadorDadosObrigatoriosUsuario;
import br.com.ecolivros.core.impl.negocio.ValidadorSituacaoItemPedido;
import br.com.ecolivros.core.impl.negocio.ValidadorSituacaoNota;
import br.com.ecolivros.dominio.AtualizaSistema;
import br.com.ecolivros.dominio.Autor;
import br.com.ecolivros.dominio.Carrinho;
import br.com.ecolivros.dominio.CartaoDeCredito;
import br.com.ecolivros.dominio.Categoria;
import br.com.ecolivros.dominio.Cupom;
import br.com.ecolivros.dominio.Editora;
import br.com.ecolivros.dominio.Endereco;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Funcionario;
import br.com.ecolivros.dominio.GrupoPrecificacao;
import br.com.ecolivros.dominio.ItemPedido;
import br.com.ecolivros.dominio.Livro;
import br.com.ecolivros.dominio.Nota;
import br.com.ecolivros.dominio.Pedido;
import br.com.ecolivros.dominio.Relatorio;
import br.com.ecolivros.dominio.Troca;
import br.com.ecolivros.dominio.Usuario;

public class Fachada implements IFachada {

	/**
	 * Mapa de DAOS, será indexado pelo nome da entidade O valor é uma instância do
	 * DAO para uma dada entidade;
	 */
	private Map<String, IDAO> daos;

	/**
	 * Mapa para conter as regras de negócio de todas operações por entidade; O
	 * valor é um mapa que de regras de negócio indexado pela operação
	 */
	private Map<String, Map<String, List<IStrategy>>> rns;

	private Resultado resultado;

	public Fachada() {
		/* Intânciando o Map de DAOS */
		daos = new HashMap<String, IDAO>();
		/* Intânciando o Map de Regras de Negócio */
		rns = new HashMap<String, Map<String, List<IStrategy>>>();

		/* Criando instâncias dos DAOs a serem utilizados */
		UsuarioDAO usuDAO = new UsuarioDAO();
		FuncionarioDAO funDAO = new FuncionarioDAO();
		CartaoDeCreditoDAO carDAO = new CartaoDeCreditoDAO();
		EnderecoDAO endDAO = new EnderecoDAO();
		AutorDAO autorDAO = new AutorDAO();
		CategoriaDAO categoriaDAO = new CategoriaDAO();
		EditoraDAO editoraDAO = new EditoraDAO();
		GrupoPrecificacaoDAO precificacaoDAO = new GrupoPrecificacaoDAO();
		LivroDAO livroDAO = new LivroDAO();
		NotaDAO notaDAO = new NotaDAO();
		CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
		AtualizaSistemaDAO atualizaSistemaDAO = new AtualizaSistemaDAO();
		PedidoDAO pedidoDAO = new PedidoDAO();
		ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO();
		TrocaDAO trocaDAO = new TrocaDAO();
		CupomDAO cupomDAO = new CupomDAO();
		RelatorioDAO relatorioDAO = new RelatorioDAO();
		
		/* Adicionando cada dao no MAP indexando pelo nome da classe */
		daos.put(Usuario.class.getName(), usuDAO);
		daos.put(Funcionario.class.getName(), funDAO);
		daos.put(CartaoDeCredito.class.getName(), carDAO);
		daos.put(Endereco.class.getName(), endDAO);
		daos.put(Autor.class.getName(), autorDAO);
		daos.put(Categoria.class.getName(), categoriaDAO);
		daos.put(Editora.class.getName(), editoraDAO);
		daos.put(GrupoPrecificacao.class.getName(), precificacaoDAO);
		daos.put(Livro.class.getName(), livroDAO);
		daos.put(Nota.class.getName(), notaDAO);
		daos.put(Carrinho.class.getName(), carrinhoDAO);
		daos.put(AtualizaSistema.class.getName(), atualizaSistemaDAO);
		daos.put(Pedido.class.getName(), pedidoDAO);
		daos.put(ItemPedido.class.getName(), itemPedidoDAO);
		daos.put(Troca.class.getName(), trocaDAO);
		daos.put(Cupom.class.getName(), cupomDAO);
		daos.put(Relatorio.class.getName(), relatorioDAO);

		/* Criando instâncias de regras de negócio a serem utilizados */
		ValidadorDadosObrigatoriosLogin vrDadosObrigatoriosLogin = new ValidadorDadosObrigatoriosLogin();

		/*
		 * Criando uma lista para conter as regras de negócio de fornencedor quando a
		 * operação for salvar
		 */
		List<IStrategy> rnsLogin = new ArrayList<IStrategy>();
		/* Adicionando as regras a serem utilizadas na operação salvar do fornecedor */
		rnsLogin.add(vrDadosObrigatoriosLogin);

		/*
		 * Cria o mapa que poderá conter todas as listas de regras de negócio específica
		 * por operação do fornecedor
		 */
		//Map<String, List<IStrategy>> rnsMapLogin = new HashMap<String, List<IStrategy>>();
		/*
		 * Adiciona a listra de regras na operação salvar no mapa do fornecedor (lista
		 * criada na linha 70)
		 */
		//rnsMapLogin.put("CONSULTAR", rnsLogin);
		
		/* Criando instâncias de regras de negócio a serem utilizados */
		ValidadorDadosObrigatoriosUsuario vrDadosObrigatoriosUsuario = new ValidadorDadosObrigatoriosUsuario();
		ComplementarDtCadastro cDtCadastro = new ComplementarDtCadastro();
		ValidadorCpf vCpf = new ValidadorCpf();

		/*
		 * Criando uma lista para conter as regras de negócio de fornencedor quando a
		 * operação for salvar
		 */
		List<IStrategy> rnsSalvarUsuario = new ArrayList<IStrategy>();
		/* Adicionando as regras a serem utilizadas na operação salvar do fornecedor */
		rnsSalvarUsuario.add(vrDadosObrigatoriosUsuario);
		rnsSalvarUsuario.add(cDtCadastro);
		rnsSalvarUsuario.add(vCpf);

		/*
		 * Cria o mapa que poderá conter todas as listas de regras de negócio específica
		 * por operação do fornecedor
		 */
		Map<String, List<IStrategy>> rnsUsuario = new HashMap<String, List<IStrategy>>();
		/*
		 * Adiciona a listra de regras na operação salvar no mapa do fornecedor (lista
		 * criada na linha 70)
		 */
		rnsUsuario.put("SALVAR", rnsSalvarUsuario);
		rnsUsuario.put("CONSULTAR", rnsLogin);
		
		/* Criando instâncias de regras de negócio a serem utilizados */
		ValidadorDadosObrigatoriosEndereco vrDadosObrigatoriosEndereco = new ValidadorDadosObrigatoriosEndereco();

		/*
		 * Criando uma lista para conter as regras de negócio de fornencedor quando a
		 * operação for salvar
		 */
		List<IStrategy> rnsSalvarEndereco = new ArrayList<IStrategy>();
		/* Adicionando as regras a serem utilizadas na operação salvar do fornecedor */
		rnsSalvarEndereco.add(vrDadosObrigatoriosEndereco);
		rnsSalvarEndereco.add(cDtCadastro);

		/*
		 * Cria o mapa que poderá conter todas as listas de regras de negócio específica
		 * por operação do fornecedor
		 */
		Map<String, List<IStrategy>> rnsEndereco = new HashMap<String, List<IStrategy>>();
		/*
		 * Adiciona a listra de regras na operação salvar no mapa do fornecedor (lista
		 * criada na linha 70)
		 */
		rnsEndereco.put("SALVAR", rnsSalvarEndereco);
		rnsEndereco.put("ALTERAR", rnsSalvarEndereco);
		
		/* Criando instâncias de regras de negócio a serem utilizados */
		ValidadorDadosObrigatoriosCartaoDeCredito vrDadosObrigatoriosCartaoDeCredito = new ValidadorDadosObrigatoriosCartaoDeCredito();

		/*
		 * Criando uma lista para conter as regras de negócio de fornencedor quando a
		 * operação for salvar
		 */
		List<IStrategy> rnsSalvarCartaoDeCredito = new ArrayList<IStrategy>();
		/* Adicionando as regras a serem utilizadas na operação salvar do fornecedor */
		rnsSalvarCartaoDeCredito.add(vrDadosObrigatoriosCartaoDeCredito);
		rnsSalvarCartaoDeCredito.add(cDtCadastro);

		/*
		 * Cria o mapa que poderá conter todas as listas de regras de negócio específica
		 * por operação do fornecedor
		 */
		Map<String, List<IStrategy>> rnsCartaoDeCredito = new HashMap<String, List<IStrategy>>();
		/*
		 * Adiciona a listra de regras na operação salvar no mapa do fornecedor (lista
		 * criada na linha 70)
		 */
		rnsCartaoDeCredito.put("SALVAR", rnsSalvarCartaoDeCredito);
		rnsCartaoDeCredito.put("ALTERAR", rnsSalvarCartaoDeCredito);
		
		/* Criando instâncias de regras de negócio a serem utilizados */
		ValidadorDadosObrigatoriosLivro vrDadosObrigatoriosLivro = new ValidadorDadosObrigatoriosLivro();

		/*
		 * Criando uma lista para conter as regras de negócio de fornencedor quando a
		 * operação for salvar
		 */
		List<IStrategy> rnsSalvarLivro = new ArrayList<IStrategy>();
		/* Adicionando as regras a serem utilizadas na operação salvar do fornecedor */
		rnsSalvarLivro.add(vrDadosObrigatoriosLivro);
		rnsSalvarLivro.add(cDtCadastro);

		/*
		 * Cria o mapa que poderá conter todas as listas de regras de negócio específica
		 * por operação do fornecedor
		 */
		Map<String, List<IStrategy>> rnsLivro = new HashMap<String, List<IStrategy>>();
		/*
		 * Adiciona a listra de regras na operação salvar no mapa do fornecedor (lista
		 * criada na linha 70)
		 */
		rnsLivro.put("SALVAR", rnsSalvarLivro);
		rnsLivro.put("ALTERAR", rnsSalvarLivro);
		
		/* Criando instâncias de regras de negócio a serem utilizados */
		ValidadorDadosObrigatoriosNota vrDadosObrigatoriosNota = new ValidadorDadosObrigatoriosNota();

		/*
		 * Criando uma lista para conter as regras de negócio de fornencedor quando a
		 * operação for salvar
		 */
		List<IStrategy> rnsSalvarNota = new ArrayList<IStrategy>();
		/* Adicionando as regras a serem utilizadas na operação salvar do fornecedor */
		rnsSalvarNota.add(vrDadosObrigatoriosNota);
		rnsSalvarNota.add(cDtCadastro);

		/* Criando instâncias de regras de negócio a serem utilizados */
		ValidadorSituacaoNota vrDadosSituacaoNota = new ValidadorSituacaoNota();
		
		/*
		 * Criando uma lista para conter as regras de negócio de fornencedor quando a
		 * operação for salvar
		 */
		List<IStrategy> rnsExcluirNota = new ArrayList<IStrategy>();
		/* Adicionando as regras a serem utilizadas na operação salvar do fornecedor */
		rnsExcluirNota.add(vrDadosSituacaoNota);
		
		/*
		 * Criando uma lista para conter as regras de negócio de fornencedor quando a
		 * operação for salvar
		 */
		List<IStrategy> rnsAlterarNota = new ArrayList<IStrategy>();
		/* Adicionando as regras a serem utilizadas na operação salvar do fornecedor */
		rnsAlterarNota.add(vrDadosObrigatoriosNota);
		rnsAlterarNota.add(cDtCadastro);
		rnsAlterarNota.add(vrDadosSituacaoNota);
		
		/*
		 * Cria o mapa que poderá conter todas as listas de regras de negócio específica
		 * por operação do fornecedor
		 */
		Map<String, List<IStrategy>> rnsNota = new HashMap<String, List<IStrategy>>();
		/*
		 * Adiciona a listra de regras na operação salvar no mapa do fornecedor (lista
		 * criada na linha 70)
		 */
		rnsNota.put("SALVAR", rnsSalvarNota);
		rnsNota.put("EXCLUIR", rnsExcluirNota);
		rnsNota.put("ALTERAR", rnsAlterarNota);
		
		/* Criando instâncias de regras de negócio a serem utilizados */
		ValidadorDadosObrigatoriosCarrinho vrDadosObrigatoriosCarrinho = new ValidadorDadosObrigatoriosCarrinho();

		/*
		 * Criando uma lista para conter as regras de negócio de fornencedor quando a
		 * operação for salvar
		 */
		List<IStrategy> rnsSalvarCarrinho = new ArrayList<IStrategy>();
		/* Adicionando as regras a serem utilizadas na operação salvar do fornecedor */
		rnsSalvarCarrinho.add(vrDadosObrigatoriosCarrinho);
		rnsSalvarCarrinho.add(cDtCadastro);

		/*
		 * Cria o mapa que poderá conter todas as listas de regras de negócio específica
		 * por operação do fornecedor
		 */
		Map<String, List<IStrategy>> rnsCarrinho = new HashMap<String, List<IStrategy>>();
		/*
		 * Adiciona a listra de regras na operação salvar no mapa do fornecedor (lista
		 * criada na linha 70)
		 */
		rnsCarrinho.put("SALVAR", rnsSalvarCarrinho);
		//rnsCarrinho.put("ALTERAR", rnsSalvarCarrinho);
		
		/* Criando instâncias de regras de negócio a serem utilizados */
		ValidadorDadosObrigatoriosAlterarCarrinho vrDadosObrigatoriosAlterarCarrinho = new ValidadorDadosObrigatoriosAlterarCarrinho();
		
		/*
		 * Criando uma lista para conter as regras de negócio de fornencedor quando a
		 * operação for salvar
		 */
		List<IStrategy> rnsAlteraCarrinho = new ArrayList<IStrategy>();
		/* Adicionando as regras a serem utilizadas na operação salvar do fornecedor */
		rnsAlteraCarrinho.add(vrDadosObrigatoriosAlterarCarrinho);

		/*
		 * Adiciona a listra de regras na operação salvar no mapa do fornecedor (lista
		 * criada na linha 70)
		 */
		rnsCarrinho.put("ALTERAR", rnsAlteraCarrinho);
		
		/* Criando instâncias de regras de negócio a serem utilizados */
		ValidadorDadosObrigatoriosItemPedido vrDadosObrigatoriosItemPedido = new ValidadorDadosObrigatoriosItemPedido();
		
		/*
		 * Criando uma lista para conter as regras de negócio de fornencedor quando a
		 * operação for salvar
		 */
		List<IStrategy> rnsSalvarItemPedido = new ArrayList<IStrategy>();
		/* Adicionando as regras a serem utilizadas na operação salvar do fornecedor */
		rnsSalvarItemPedido.add(vrDadosObrigatoriosItemPedido);
		rnsSalvarItemPedido.add(cDtCadastro);

		/*
		 * Cria o mapa que poderá conter todas as listas de regras de negócio específica
		 * por operação do fornecedor
		 */
		Map<String, List<IStrategy>> rnsItemPedido = new HashMap<String, List<IStrategy>>();
		/*
		 * Adiciona a listra de regras na operação salvar no mapa do fornecedor (lista
		 * criada na linha 70)
		 */
		rnsItemPedido.put("SALVAR", rnsSalvarItemPedido);
		
		/* Criando instâncias de regras de negócio a serem utilizados */
		ValidadorSituacaoItemPedido vrSituacaoItemPedido = new ValidadorSituacaoItemPedido();
		
		/*
		 * Criando uma lista para conter as regras de negócio de fornencedor quando a
		 * operação for salvar
		 */
		List<IStrategy> rnsAlterarItemPedido = new ArrayList<IStrategy>();
		/* Adicionando as regras a serem utilizadas na operação salvar do fornecedor */
		rnsAlterarItemPedido.add(vrSituacaoItemPedido);
		
		/*
		 * Adiciona a listra de regras na operação salvar no mapa do fornecedor (lista
		 * criada na linha 70)
		 */
		rnsItemPedido.put("ALTERAR", rnsAlterarItemPedido);
				
		/* Criando instâncias de regras de negócio a serem utilizados */
		ValidadorDadosObrigatoriosPedido vrDadosObrigatoriosPedido = new ValidadorDadosObrigatoriosPedido();

		/*
		 * Criando uma lista para conter as regras de negócio de fornencedor quando a
		 * operação for salvar
		 */
		List<IStrategy> rnsSalvarPedido = new ArrayList<IStrategy>();
		/* Adicionando as regras a serem utilizadas na operação salvar do fornecedor */
		rnsSalvarPedido.add(vrDadosObrigatoriosPedido);
		rnsSalvarPedido.add(cDtCadastro);

		/*
		 * Cria o mapa que poderá conter todas as listas de regras de negócio específica
		 * por operação do fornecedor
		 */
		Map<String, List<IStrategy>> rnsPedido = new HashMap<String, List<IStrategy>>();
		/*
		 * Adiciona a listra de regras na operação salvar no mapa do fornecedor (lista
		 * criada na linha 70)
		 */
		rnsPedido.put("SALVAR", rnsSalvarPedido);
		
		/* Criando instâncias de regras de negócio a serem utilizados */
		ValidadorDadosObrigatoriosTroca vrDadosObrigatoriosTroca = new ValidadorDadosObrigatoriosTroca();
		
		/*
		 * Criando uma lista para conter as regras de negócio de fornencedor quando a
		 * operação for salvar
		 */
		List<IStrategy> rnsSalvarTroca = new ArrayList<IStrategy>();
		/* Adicionando as regras a serem utilizadas na operação salvar do fornecedor */
		rnsSalvarTroca.add(vrDadosObrigatoriosTroca);

		/*
		 * Cria o mapa que poderá conter todas as listas de regras de negócio específica
		 * por operação do fornecedor
		 */
		Map<String, List<IStrategy>> rnsTroca = new HashMap<String, List<IStrategy>>();
		/*
		 * Adiciona a listra de regras na operação salvar no mapa do fornecedor (lista
		 * criada na linha 70)
		 */
		rnsTroca.put("SALVAR", rnsSalvarTroca);
		
		/* Criando instâncias de regras de negócio a serem utilizados */
		ValidadorDadosObrigatoriosRelatorio vrDadosObrigatoriosRelatorio = new ValidadorDadosObrigatoriosRelatorio();
		
		/*
		 * Criando uma lista para conter as regras de negócio de fornencedor quando a
		 * operação for salvar
		 */
		List<IStrategy> rnsSalvarRelatorio = new ArrayList<IStrategy>();
		/* Adicionando as regras a serem utilizadas na operação salvar do fornecedor */
		rnsSalvarRelatorio.add(vrDadosObrigatoriosRelatorio);

		/*
		 * Cria o mapa que poderá conter todas as listas de regras de negócio específica
		 * por operação do fornecedor
		 */
		Map<String, List<IStrategy>> rnsRelatorio = new HashMap<String, List<IStrategy>>();
		/*
		 * Adiciona a listra de regras na operação salvar no mapa do fornecedor (lista
		 * criada na linha 70)
		 */
		rnsRelatorio.put("SALVAR", rnsSalvarRelatorio);
		
		/*
		 * Adiciona o mapa(criado na linha 79) com as regras indexadas pelas operações
		 * no mapa geral indexado pelo nome da entidade
		 */
		rns.put(Usuario.class.getName(), rnsUsuario);
		rns.put(CartaoDeCredito.class.getName(), rnsCartaoDeCredito);
		rns.put(Endereco.class.getName(), rnsEndereco);
		rns.put(Livro.class.getName(), rnsLivro);
		rns.put(Nota.class.getName(), rnsNota);
		rns.put(Carrinho.class.getName(), rnsCarrinho);
		rns.put(Pedido.class.getName(), rnsPedido);
		rns.put(ItemPedido.class.getName(), rnsItemPedido);
		rns.put(Troca.class.getName(), rnsTroca);
		rns.put(Relatorio.class.getName(), rnsRelatorio);
	}

	@Override
	public Resultado salvar(EntidadeDominio entidade) {
		resultado = new Resultado();
		String nmClasse = entidade.getClass().getName();

		String msg = executarRegras(entidade, "SALVAR");

		if (msg == null) {
			IDAO dao = daos.get(nmClasse);
			try {
				dao.salvar(entidade);
				List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
				entidades.add(entidade);
				resultado.setEntidades(entidades);
			} catch (SQLException e) {
				e.printStackTrace();
				resultado.setMsg("Não foi possível realizar o registro!");
			}
		} else {
			resultado.setMsg(msg);
		}
		return resultado;
	}

	@Override
	public Resultado alterar(EntidadeDominio entidade) {
		resultado = new Resultado();
		String nmClasse = entidade.getClass().getName();

		String msg = executarRegras(entidade, "ALTERAR");

		if (msg == null) {
			IDAO dao = daos.get(nmClasse);
			try {
				dao.alterar(entidade);
				List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
				entidades.add(entidade);
				resultado.setEntidades(entidades);
			} catch (SQLException e) {
				e.printStackTrace();
				resultado.setMsg("Não foi possível realizar o registro!");
			}
		} else {
			resultado.setMsg(msg);
		}
		return resultado;
	}

	@Override
	public Resultado excluir(EntidadeDominio entidade) {
		resultado = new Resultado();
		String nmClasse = entidade.getClass().getName();

		String msg = executarRegras(entidade, "EXCLUIR");

		if (msg == null) {
			IDAO dao = daos.get(nmClasse);
			try {
				dao.excluir(entidade);
				List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
				entidades.add(entidade);
				resultado.setEntidades(entidades);
			} catch (SQLException e) {
				e.printStackTrace();
				resultado.setMsg("Não foi possível realizar o registro!");
			}
		} else {
			resultado.setMsg(msg);
		}
		return resultado;
	}

	@Override
	public Resultado consultar(EntidadeDominio entidade) {
		resultado = new Resultado();
		String nmClasse = entidade.getClass().getName();

		String msg = executarRegras(entidade, "CONSULTAR");

		if (msg == null) {
			IDAO dao = daos.get(nmClasse);
			try {
				resultado.setEntidades(dao.consultar(entidade));
			} catch (SQLException e) {
				e.printStackTrace();
				resultado.setMsg("Não foi possível realizar a consulta!");
			}
		} else {
			resultado.setMsg(msg);
		}
		return resultado;
	}

	@Override
	public Resultado visualizar(EntidadeDominio entidade) {
		resultado = new Resultado();
		resultado.setEntidades(new ArrayList<EntidadeDominio>());
		resultado.getEntidades().add(entidade);
		
		return resultado;
	}

	private String executarRegras(EntidadeDominio entidade, String operacao) {
		String nmClasse = entidade.getClass().getName();
		StringBuilder msg = new StringBuilder();

		Map<String, List<IStrategy>> regrasOperacao = rns.get(nmClasse);

		if (regrasOperacao != null) {
			List<IStrategy> regras = regrasOperacao.get(operacao);

			if (regras != null) {
				for (IStrategy s : regras) {
					String m = s.processar(entidade);

					if (m != null && !m.equals("")) {
						msg.append(m);
						msg.append("\n");
					}
				}
			}
		}
		if (msg.length() > 0)
			return msg.toString();
		else
			return null;
	}
}