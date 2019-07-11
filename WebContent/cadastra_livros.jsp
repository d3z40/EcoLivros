<!DOCTYPE html>

<%@page import="br.com.ecolivros.core.util.UsuarioLogado"%>
<%@ page import="br.com.ecolivros.dominio.*, br.com.ecolivros.core.impl.dao.*, java.util.*"%>

<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="Start your development with a Dashboard for Bootstrap 4.">
  <meta name="author" content="Creative Tim">
  <title>EcoLivros</title>
  <!-- Favicon -->
  <link href="./assets/img/brand/favicon.png" rel="icon" type="image/png">
  <!-- Fonts -->
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet">
  <!-- Icons -->
  <link href="./assets/vendor/nucleo/css/nucleo.css" rel="stylesheet">
  <link href="./assets/vendor/@fortawesome/fontawesome-free/css/all.min.css" rel="stylesheet">
  <!-- Argon CSS -->
  <link type="text/css" href="./assets/css/argon.css?v=1.0.0" rel="stylesheet">
  <style>
    table {
      font-family: arial, sans-serif;
      border-collapse: collapse;
    }

    td, th {
      border: 1px solid #dddddd;
      text-align: left;
      padding: 8px;
    }
  </style>
</head>

<body>
  <!-- Sidenav -->
  <nav class="navbar navbar-vertical fixed-left navbar-expand-md navbar-light bg-white" id="sidenav-main">
    <div class="container-fluid">
      <!-- Toggler -->
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#sidenav-collapse-main" aria-controls="sidenav-main" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <!-- Brand -->
      <a class="navbar-brand pt-0" href="./dashboard.jsp">
        <img src="./assets/img/brand/carbooks.png" class="navbar-brand-img" alt="...">
      </a>
      <!-- Collapse -->
      <div class="collapse navbar-collapse" id="sidenav-collapse-main">
        <!-- Collapse header -->
        <div class="navbar-collapse-header d-md-none">
          <div class="row">
            <div class="col-6 collapse-brand">
              <a href="./dashboard.jsp">
                <img src="./assets/img/brand/blue.png">
              </a>
            </div>
            <div class="col-6 collapse-close">
              <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#sidenav-collapse-main" aria-controls="sidenav-main" aria-expanded="false" aria-label="Toggle sidenav">
                <span></span>
                <span></span>
              </button>
            </div>
          </div>
        </div>
        <!-- Navigation -->
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link" href="./dashboard.jsp">
              <i class="ni ni-tv-2 text-primary"></i> Dashboard
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="./cadastra_livros.jsp">
              <i class="ni ni-book-bookmark text-green"></i> Cadastro Livro
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="./consulta_livros.jsp">
              <i class="ni ni-books text-yellow"></i> Consulta Livro
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="./cadastra_notas.jsp">
              <i class="ni ni-collection text-red"></i> Cadastrar Notas
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="./consulta_notas.jsp">
              <i class="ni ni-bullet-list-67 text-blue"></i> Consultar Notas
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="./consulta_trocas.jsp">
              <i class="ni ni-paper-diploma text-black"></i> Consultar Trocas
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="./analises.jsp">
              <i class="ni ni-single-copy-04 text-black"></i> Relatórios
            </a>
          </li>
          <li class="dropdown-divider"></li>
          <li class="nav-item">
            <a class="nav-link" href="./AtualizacaoDoSistema?operacao=SALVAR">
              <i class="ni ni-settings-gear-65 text-blue"></i> Atualização do Sistema
            </a>
          </li>
		  <li class="dropdown-divider"></li>
	      <li class="nav-item">
            <a class="nav-link" href="./login.jsp">
              <i class="ni ni ni-user-run text-black"></i> Sair
            </a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <%
  	Usuario usuario = UsuarioLogado.getUsuario();
  	Livro livro = (Livro) request.getAttribute("livro");
  %>
  <!-- Main content -->
  <div class="main-content">
    <!-- Top navbar -->
    <nav class="navbar navbar-top navbar-expand-md navbar-dark" id="navbar-main">
      <div class="container-fluid">
        <!-- Brand -->
        <a class="h4 mb-0 text-white text-uppercase d-none d-lg-inline-block">Cadastro de Livros</a>
        <!-- User -->
        <ul class="navbar-nav align-items-center d-none d-md-flex">
          <li class="nav-item dropdown">
            <a class="nav-link pr-0" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              <span class="avatar avatar-sm rounded-circle">
                <img alt="Image placeholder" src="./assets/img/theme/user.svg">
              </span>
              <span class="mb-0 text-sm  font-weight-bold"><%=usuario.getNome().substring(0, usuario.getNome().indexOf(" ")) %></span>
            </a>
            <div class="dropdown-menu dropdown-menu-arrow dropdown-menu-right">
              <div class=" dropdown-header noti-title">
                <h6 class="text-overflow m-0">Bem Vindo!</h6>
              </div>
              <a href="./profile.html" class="dropdown-item">
                <i class="ni ni-single-02"></i>
                <span>Meu Perfil</span>
              </a>
              <div class="dropdown-divider"></div>
              <a href="./login.jsp" class="dropdown-item">
                <i class="ni ni-user-run"></i>
                <span>Sair</span>
              </a>
            </div>
          </li>
        </ul>
      </div>
    </nav>
    <!-- Header -->
    <div class="header pb-8 pt-3 pt-lg-1 d-flex align-items-center" style="min-height: 350px; background-image: url(../assets/img/theme/livros-cover.jpg); background-size: cover; background-position: center top;">
      <!-- Mask -->
      <span class="mask bg-gradient-default opacity-8"></span>
      <!-- Header container -->
      <div class="container-fluid d-flex align-items-center">
        <div class="row">
          <div class="col-lg-13 col-md-12">
            <h1 class="display-2 text-white">Livros</h1>
            <p class="text-white mt-0 mb-5">Esse é o cadastro de livros. Vocé poderá incluir e alterar as informações de um livro.</p>
          </div>
        </div>
      </div>
    </div>
    <!-- Page content -->
    <div class="container-fluid mt--9">
      <form action="SalvarLivro" method="POST">
		  <div class="row">
			<div class="col-xl-12 order-xl-5">
			  <div class="card bg-secondary shadow">
				<div class="card-body">
					<div class="card-header bg-white border-0">
					  <div class="row align-items-center">
						<div class="col-8">
						  <h3 class="mb-0">Livro</h3>
						</div>
						<div class="col-4 text-right">
						  <button type="submit" class="btn btn-primary">Salvar</button>
						</div>
					  </div>
					</div>
					<h6 class="heading-small text-muted mb-4">Informações do Livro</h6>
					<input type="hidden" name="txtIdLivro" value="<% if (livro != null) out.print(livro.getId()); %>" />
					<input type="hidden" name="operacao" value="<% if (livro == null) out.print("SALVAR"); else out.print("ALTERAR");%>" />
					<div class="pl-lg-4">
					  <div class="row">
						<div class="col-lg-6">
						  <div class="form-group">
							<label class="form-control-label" for="txtCodigo">Código</label>
							<input type="text" id="txtCodigo" name="txtCodigo" value="<%if (livro != null) out.print(livro.getCodigo()); %>" class="form-control form-control-alternative" placeholder="Código">
						  </div>
						</div>
						<div class="col-lg-6">
						  <div class="form-group">
							<label class="form-control-label" for="txtTitulo">Título</label>
							<input type="text" id="txtTitulo" name="txtTitulo" value="<%if(livro != null) out.print(livro.getTitulo()); %>" class="form-control form-control-alternative" placeholder="Título">
						  </div>
						</div>
					  </div>
					  <div class="row">
						<div class="col-lg-6">
						  <div class="form-group">
							<label class="form-control-label" for="txtAno">Ano</label>
							<input type="text" id="txtAno" name="txtAno" value="<%if (livro != null) out.print(livro.getAno()); %>" class="form-control form-control-alternative" placeholder="Ano">
						  </div>
						</div>
						<div class="col-lg-6">
						  <div class="form-group">
							<label class="form-control-label" for="txtEdicao">Edição</label>
							<input type="text" id="txtEdicao" name="txtEdicao" value="<%if (livro != null) out.print(livro.getEdicao()); %>" class="form-control form-control-alternative" placeholder="Edição">
						  </div>
						</div>
					  </div>
					  <div class="row">
						<div class="col-lg-6">
						  <div class="form-group">
							<label class="form-control-label" for="txtISBN">ISBN</label>
							<input type="text" id="txtISBN" name="txtISBN" value="<%if (livro != null) out.print(livro.getISBN()); %>" class="form-control form-control-alternative" placeholder="ISBN">
						  </div>
						</div>
						<div class="col-lg-6">
						  <div class="form-group">
							<label class="form-control-label" for="txtNumPags">Número de Pags.</label>
							<input type="text" id="txtNumPags" name="txtNumPags" value="<%if (livro != null) out.print(livro.getNumPags()); %>" class="form-control form-control-alternative" placeholder="Número de Pags.">
						  </div>
						</div>
					  </div>
					  <div class="row">
						<div class="col-md-12">
						  <div class="form-group">
							<label class="form-control-label" for="txtSinopse">Sínopse</label>
							<textarea rows="4" id="txtSinopse" name="txtSinopse" class="form-control form-control-alternative" placeholder="Sínopse"><%if (livro != null) out.print(livro.getSinopse()); %></textarea>
						  </div>
						</div>
					  </div>
					  <div class="row">
						<div class="col-lg-3">
						  <div class="form-group">
							<label class="form-control-label" for="txtAltura">Altura</label>
							<input type="text" id="txtAltura" name="txtAltura" value="<%if (livro != null) out.print(livro.getAltura()); %>" class="form-control form-control-alternative" placeholder="Altura">
						  </div>
						</div>
						<div class="col-lg-3">
						  <div class="form-group">
							<label class="form-control-label" for="txtLargura">Largura</label>
							<input type="text" id="txtLargura" name="txtLargura" value="<%if (livro != null) out.print(livro.getLargura()); %>" class="form-control form-control-alternative" placeholder="Largura">
						  </div>
						</div>
						<div class="col-lg-3">
						  <div class="form-group">
							<label class="form-control-label" for="txtPeso">Peso</label>
							<input type="text" id="txtPeso" name="txtPeso" value="<%if (livro != null) out.print(livro.getPeso()); %>" class="form-control form-control-alternative" placeholder="Peso">
						  </div>
						</div>
						<div class="col-lg-3">
						  <div class="form-group">
							<label class="form-control-label" for="txtProfundidade">Profundidade</label>
							<input type="text" id="txtProfundidade" name="txtProfundidade" value="<%if (livro != null) out.print(livro.getProfundidade()); %>" class="form-control form-control-alternative" placeholder="Profundidade">
						  </div>
						</div>
					  </div>
					  <div class="row">
						<div class="col-lg-6">
						  <div class="form-group">
							<label class="form-control-label" for="txtAtivo">Ativo</label>
							<select id="txtAtivo" name="txtAtivo" class="form-control">
							<%
								if (livro == null || (livro != null && !livro.isAtivo())) {
							%>  
								<option id=0 value=0 selected>Não</option>
							<%
								} else if (livro != null && livro.isAtivo()) {
							%>
								<option id=0 value=0 >Não</option>
								<option id=1 value=1 selected>Sim</option>
							<%	
								}
							%>
							</select>
						  </div>
						</div>
						<div class="col-lg-6">
						  <div class="form-group">
							<label class="form-control-label" for="txtCodBarras">Código de Barras</label>
							<input type="text" id="txtCodBarras" name="txtCodBarras" value="<%if (livro != null) out.print(livro.getCodBarras()); %>" class="form-control form-control-alternative" placeholder="Código de Barras">
						  </div>
						</div>
					  </div>
					  <div class="row">
						<div class="col-md-12">
						  <div class="form-group">
							<label class="form-control-label" for="txtMotivo">Motivo</label>
							<textarea rows="4" id="txtMotivo" name="txtMotivo" class="form-control form-control-alternative" placeholder="Motivo" <%if (livro == null || (livro != null && !livro.isAtivo())) out.print("readonly"); %>><%if (livro != null) out.print(livro.getMotivo()); else out.print("Novo Livro."); %></textarea>
						  </div>
						</div>
					  </div>
					</div>
					<hr class="my-4" />
					<!-- Address -->
					<h6 class="heading-small text-muted mb-4">Relacionamentos</h6>
					<div class="pl-lg-4">
					  <div class="row">
						<div class="col-lg-6">
						  <div class="form-group">
							<label class="form-control-label" for="txtEditora">Editora</label>
							<select id="txtEditora" name="txtEditora" class="form-control">
								<%
									List<EntidadeDominio> editoras = new EditoraDAO().getLista();

									for (EntidadeDominio ed : editoras) {
										Editora editora = (Editora) ed;
								%>
								<option id=<%=editora.getId() %> value=<%=editora.getId() %> <%if (livro != null && livro.getEditora().getId() == editora.getId()) out.print("selected"); %>><%=editora.getRazao() %> </option>
								<%
									}
								%>
							</select>
						  </div>
						</div>
						<div class="col-lg-6">
						  <div class="form-group">
							<label class="form-control-label" for="txtGrupoPrecificacao">Precificação</label>
							<select id="txtGrupoPrecificacao" name="txtGrupoPrecificacao" class="form-control">
								<%
									List<EntidadeDominio> gruposPrec = new GrupoPrecificacaoDAO().getLista();

									for (EntidadeDominio ed : gruposPrec) {
										GrupoPrecificacao grupoPrec = (GrupoPrecificacao) ed;
								%>
								<option id=<%=grupoPrec.getId() %> value=<%=grupoPrec.getId() %> <%if (livro != null && livro.getGrupoPrecificacao().getId() == grupoPrec.getId()) out.print("selected"); %>><%=grupoPrec.getMargem() %></option>
								<%
									}
								%>
							</select>
						  </div>
						</div>
					  </div>
					  <div class="row">
						<div class="col-lg-6">
						  <div class="form-group">
							<label class="form-control-label" for="txtAutor">Autor</label>
							<table>
								<%
									List<EntidadeDominio> autores = new AutorDAO().getLista();
		
									for (EntidadeDominio ed : autores) {
										Autor autor = (Autor) ed;
										boolean autorCadastrado = false;
										
										if (livro != null) {
											
											for (Autor livroAutor : livro.getAutores()) {
												if (livroAutor.getId() == autor.getId()) {
													autorCadastrado = true;
												}
											}				
										}
								%>
								<tr>
									<td width="10"><input type="checkbox" id=chkAutor<%=autor.getId() %> name=chkAutor<%=autor.getId() %> <%if (autorCadastrado) out.print("checked"); %>></td>
									<td width="150"><%=autor.getNomeArtistico() %></td>
								</tr>
								<%
									}
								%>
							</table>
						  </div>
						</div>
						<div class="col-lg-6">
						  <div class="form-group">
							<label class="form-control-label" for="txtCategoria">Categorias</label>
							<table>
								<%
									List<EntidadeDominio> categorias = new CategoriaDAO().getLista();
		
									for (EntidadeDominio ed : categorias) {
										Categoria categoria = (Categoria) ed;
										boolean categoriaCadastrada = false;
										
										if (livro != null) {
											
											for (Categoria livroCategoria : livro.getCategorias()) {
												if (livroCategoria.getId() == categoria.getId()) {
													categoriaCadastrada = true;
												}
											}				
										}
								%>
								<tr>
									<td width="10"><input type="checkbox" id=chkCategoria<%=categoria.getId() %> name=chkCategoria<%=categoria.getId() %> <%if (categoriaCadastrada) out.print("checked"); %>></td>
									<td width="150"><%=categoria.getDescricao() %></td>
								</tr>
								<%
									}
								%>
							</table>
						  </div>
						</div>
					  </div>
					</div>
				</div>
			  </div>
			</div>
		  </div>
		  </form>
      </div>
      <!-- Footer -->
      <footer class="footer">
        <div class="row align-items-center justify-content-xl-between">
          <div class="col-xl-6">
            <div class="copyright text-center text-xl-left text-muted">
              &copy; 2019
            </div>
          </div>
        </div>
      </footer>
    </div>
  <!-- Argon Scripts -->
  <!-- Core -->
  <script src="./assets/vendor/jquery/dist/jquery.min.js"></script>
  <script src="./assets/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
  <!-- Argon JS -->
  <script src="./assets/js/argon.js?v=1.0.0"></script>
</body>

</html>
