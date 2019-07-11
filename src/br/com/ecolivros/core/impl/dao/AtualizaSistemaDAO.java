package br.com.ecolivros.core.impl.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.ecolivros.core.util.UsuarioLogado;
import br.com.ecolivros.dominio.Cupom;
import br.com.ecolivros.dominio.EntidadeDominio;
import br.com.ecolivros.dominio.Estoque;
import br.com.ecolivros.dominio.ItemNota;
import br.com.ecolivros.dominio.Livro;
import br.com.ecolivros.dominio.PreCupom;
import br.com.ecolivros.enuns.SituacaoEstoque;

public class AtualizaSistemaDAO extends AbstractJdbcDAO {

	public AtualizaSistemaDAO() {
		super("", "id");
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		Estoque estoque;
		List<Integer> idsNota = new ArrayList<Integer>();
		List<Estoque> estoques = new ArrayList<Estoque>();
		
		LivroDAO livroDAO = new LivroDAO();
		EstoqueDAO estoqueDAO = new EstoqueDAO();
		PreCupomDAO preCupomDAO = new PreCupomDAO();
		Cupom cupom;
		CupomDAO cupomDAO = new CupomDAO();
		
		ItemNotaDAO itemNotaDAO = new ItemNotaDAO();
		List<ItemNota> itensNota = itemNotaDAO.getListaBySituacaoEstoque(SituacaoEstoque.PENDENTE);
		
		for (ItemNota itemNota : itensNota) {
			for (int i = 0; i < itemNota.getQtde(); i++) {
				estoque = new Estoque();
				estoque.setIdLivro(itemNota.getLivro().getId());
				estoque.setValor(itemNota.getValor() + (itemNota.getValor() * (itemNota.getLivro().getGrupoPrecificacao().getMargem() / 100)));
				estoque.setSituacaoEstoque(SituacaoEstoque.PENDENTE);
				
				estoques.add(estoque);
			}
			
			if (!idsNota.contains(itemNota.getIdNota())) {
				idsNota.add(itemNota.getIdNota());	
			}
			itemNota.setSituacaoEstoque(SituacaoEstoque.BAIXADO);
		}
		
		if (itensNota != null && itensNota.size() > 0) {
			new EstoqueDAO().salvarList(estoques);
			itemNotaDAO.atualizarSituacaoEstoque(itensNota);
			new NotaDAO().atualizarSituacaoEstoque(idsNota, SituacaoEstoque.BAIXADO);
		}
		
		List<EntidadeDominio> livros = livroDAO.getLista();
		if (livros != null && livros.size() > 0) {
			for (EntidadeDominio ed : livros) {
				Livro livro = (Livro) ed;
				boolean existeLivroEstoquePendente = estoqueDAO.getExisteByIdLivroSituacao(livro.getId(), SituacaoEstoque.PENDENTE);
				
				if (!livro.isAtivo() && existeLivroEstoquePendente) {
					livroDAO.ativarById(livro.getId(), 1, "Atualização Estoque");
				} else if (livro.isAtivo() && !existeLivroEstoquePendente) {
					livroDAO.ativarById(livro.getId(), 0, "Atualização Estoque");
				}
			}
		}
			
		List<PreCupom> preCupons = preCupomDAO.getPreCupomBySituacao(SituacaoEstoque.PENDENTE);
		for (PreCupom preCupom : preCupons) {
			cupom = new Cupom();
			cupom.setIdUsuario(preCupom.getIdUsuario());
			cupom.setValorCupom(preCupom.getValor());
			cupom.setValorUtilizado(0);
			cupom.setDtCadastro(new Date());
			
			String identificadorCupom = String.format("%03d_", UsuarioLogado.getUsuario().getId());
			
			Calendar dataAtual = Calendar.getInstance();
			dataAtual.setTime(new Date());
			identificadorCupom += String.format("%04d%02d%02d%02d%02d%02d", dataAtual.get(Calendar.YEAR),
																	         dataAtual.get(Calendar.MONTH),
																	         dataAtual.get(Calendar.DAY_OF_MONTH),
																	         dataAtual.get(Calendar.HOUR_OF_DAY),
																	         dataAtual.get(Calendar.MINUTE),
																	         dataAtual.get(Calendar.SECOND));
			
			cupom.setIdentificador(identificadorCupom);
			
			cupomDAO.salvar(cupom);
			preCupomDAO.alterarSituacaoPreCupom(preCupom.getIdUsuario(), SituacaoEstoque.BAIXADO);
		}
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		return null;
	}
}