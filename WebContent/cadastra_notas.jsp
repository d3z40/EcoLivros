<!DOCTYPE html>

<%@page import="br.com.ecolivros.enuns.SituacaoEstoque"%>
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
  	Nota nota = (Nota) request.getAttribute("nota");
  %>
  <!-- Main content -->
  <div class="main-content">
    <!-- Top navbar -->
    <nav class="navbar navbar-top navbar-expand-md navbar-dark" id="navbar-main">
      <div class="container-fluid">
        <!-- Brand -->
        <a class="h4 mb-0 text-white text-uppercase d-none d-lg-inline-block">Cadastro de Notas</a>
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
            <h1 class="display-2 text-white">Notas</h1>
            <p class="text-white mt-0 mb-5">Esse é o cadastro de notas. Vocé poderá incluir e alterar as informações de uma nota.</p>
          </div>
        </div>
      </div>
    </div>
    <!-- Page content -->
    <div class="container-fluid mt--9">
      <form action="SalvarNota" method="POST">
		  <div class="row">
			<div class="col-xl-12 order-xl-5">
			  <div class="card bg-secondary shadow">
				<div class="card-body">
					<div class="card-header bg-white border-0">
					  <div class="row align-items-center">
						<div class="col-8">
						  <h3 class="mb-0">Nota</h3>
						</div>
						<div class="col-4 text-right">
						  <button type="submit" class="btn btn-primary">Salvar</button>
						</div>
					  </div>
					</div>
					<h6 class="heading-small text-muted mb-4">Informações do Livro</h6>
					<input type="hidden" name="txtIdNota" value="<% if (nota != null) out.print(nota.getId()); %>" />
					<input type="hidden" name="operacao" value="<% if (nota == null) out.print("SALVAR"); else out.print("ALTERAR");%>" />
					<div class="pl-lg-4">
					  <div class="row">
						<div class="col-lg-6">
						  <div class="form-group">
							<label class="form-control-label" for="txtNumeroNota">Número Nota</label>
							<input type="text" id="txtNumeroNota" name="txtNumeroNota" value="<%if (nota != null) out.print(nota.getNumeroNota()); %>" class="form-control form-control-alternative" placeholder="Número Nota">
						  </div>
						</div>
						<div class="col-lg-6">
						  <div class="form-group">
							<label class="form-control-label" for="txtSituacaoEstoque">Situação Estoque</label>
							<select id="txtSituacaoEstoque" name="txtSituacaoEstoque" class="form-control">
								<%
									if (nota != null) {
										for (SituacaoEstoque se : SituacaoEstoque.values()) {
											if (se.getCodigo() == nota.getSituacaoEstoque().getCodigo()) {
								%>
								<option id=<%=se.getCodigo() %> value=<%=se.getCodigo() %>><%=se.getDescricao() %> </option>
								<%
											}
										}
									} else {
								%>
								<option id=<%=SituacaoEstoque.PENDENTE.getCodigo() %> value=<%=SituacaoEstoque.PENDENTE.getCodigo() %>><%=SituacaoEstoque.PENDENTE.getDescricao() %> </option>
								<%	
									}
								%>
							</select>
						  </div>
						</div>
					  </div>
					</div>
					<div class="pl-lg-4">
					  <div class="row">
						<div class="col-lg-6">
						  <div class="form-group">
							<label class="form-control-label" for="txtFornecedor">Fornecedor</label>
							<select id="txtFornecedor" name="txtFornecedor" class="form-control">
								<%
									List<EntidadeDominio> fornecedores = new FornecedorDAO().getLista();

									for (EntidadeDominio ed : fornecedores) {
										Fornecedor fornecedor = (Fornecedor) ed;
								%>
								<option id=<%=fornecedor.getId() %> value=<%=fornecedor.getId() %> <%if (nota != null && nota.getFornecedor().getId() == fornecedor.getId()) out.print("selected"); %>><%=fornecedor.getRazaoSocial() %> </option>
								<%
									}
								%>
							</select>
						  </div>
						</div>
					  </div>
					</div>
				<hr class="my-4" />
				<h6 class="heading-small text-muted mb-4">Itens</h6>
				<div class="table-responsive">
	              <table id="tblItensNota" class="table align-items-center table-flush">
		            <thead class="thead-light">
		              <tr>
		              	<th scope="col">Select</th>
		              	<th scope="col"></th>
		                <th scope="col">Livro</th>
		                <th scope="col">Qtde</th>
		                <th scope="col">Valor</th>
		                <th scope="col">Situação Estoque</th>
		                <th scope="col">Ação</th>
		              </tr>
		            </thead>
		              <tbody>
		            	<%
		            		int i = 0;
		            		if (nota != null && nota.getItensNota() != null && !nota.getItensNota().isEmpty()) {
			            		for (ItemNota itemNota : nota.getItensNota()) {
		            	%>
		            	  <tr>
		            	    <td width="10">
		            	      <input type="checkbox" id="chkSelect^<%=i %>" name="chkSelect^<%=i %>">
		            	    </td>
		            	    <td width="0">
		            	      <input type="hidden" id="txtIdItemNota^<%=i %>" name="txtIdItemNota^<%=i %>" value="<%if (itemNota != null) out.print(itemNota.getIdNota().intValue() + "|" + itemNota.getId()); %>" class="form-control form-control-alternative" placeholder="Id">
		            	    </td>
		            	    <td>
			                  <select id="txtLivro^<%=i %>" name="txtLivro^<%=i %>" class="form-control">
			                        <option id="#" value="#" selected >Selecione </option>
									<%
										List<EntidadeDominio> livros = new LivroDAO().getLista();
	
										for (EntidadeDominio edo : livros) {
											Livro livro = (Livro) edo;
									%>
									<option id=<%=livro.getId() %> value=<%=livro.getId() %> <%if (itemNota != null && itemNota.getLivro().getId() == livro.getId()) out.print("selected"); %>><%=livro.getTitulo() %> </option>
							        <%
										}
									%>
							  </select>
			                </td>
			                <td>
			                  <input type="text" id="txtQtde^<%=i %>" name="txtQtde^<%=i %>" value="<%if (itemNota != null) out.print(itemNota.getQtde()); %>" class="form-control form-control-alternative" placeholder="Qtde">
			                </td>
			                <td>
			                  <input type="text" id="txtValor^<%=i %>" name="txtValor^<%=i %>" value="<%if (itemNota != null) out.print(itemNota.getValor()); %>" class="form-control form-control-alternative" placeholder="Valor">
			                </td>
			                <td>
			                  <select id="txtSituacaoEstoque^<%=i %>" name="txtSituacaoEstoque^<%=i %>" class="form-control">
			                    <option id="<%=itemNota.getSituacaoEstoque().getCodigo() %>" value="<%=itemNota.getSituacaoEstoque().getCodigo() %>" selected ><%=itemNota.getSituacaoEstoque().getDescricao() %> </option>
			                  </select>
			                </td>
			                <td class="text-center">
			                  <div class="dropdown">
			                    <a class="btn btn-sm btn-icon-only text-light" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			                      <i class="fas fa-ellipsis-v"></i>
			                    </a>
			                    <div class="dropdown-menu dropdown-menu-right dropdown-menu-arrow">
			                      <a class="dropdown-item" href="SalvarNota?operacao=EXCLUIR&IdNota=<%=nota.getId() %>&idItemNota=<%=itemNota.getId() %>"><i class="ni ni-button-power text-red"></i> Excluir</a>
			                   </div>
			                  </div>
			                </td>
			              </tr>
		            	<%
		            				i += 1;
			            		}			            		
		            		}
		            		
		            		if (i < 10) {
		            			while (i < 10) {
			            %>
		            	  <tr>
		            	    <td width="10">
		            	      <input type="checkbox" id="chkSelect^<%=i %>" name="chkSelect^<%=i %>">
		            	    </td>
		            	    <td width="0">
		            	      <input readonly type="hidden" id="txtIdItemNota^<%=i %>" name="txtIdItemNota^<%=i %>" value="<%if (nota != null) out.print(nota.getId() + "|"); %>" class="form-control form-control-alternative" placeholder="Id">
		            	    </td>
			                <td>
			                  <select id="txtLivro^<%=i %>" name="txtLivro^<%=i %>" class="form-control">
			                  		<option id="id" value="value" selected >Selecione </option>
									<%
										List<EntidadeDominio> livros = new LivroDAO().getLista();
	
										for (EntidadeDominio edo : livros) {
											Livro livro = (Livro) edo;
									%>
									<option id=<%=livro.getId() %> value=<%=livro.getId() %> ><%=livro.getTitulo() %> </option>
									<%
										}
									%>
							  </select>
			                </td>
			                <td>
			                  <input type="text" id="txtQtde^<%=i %>" name="txtQtde^<%=i %>" value="" class="form-control form-control-alternative" placeholder="Qtde">
			                </td>
			                <td>
			                  <input type="text" id="txtValor^<%=i %>" name="txtValor^<%=i %>" value="" class="form-control form-control-alternative" placeholder="Valor">
			                </td>
			                <td>
			                  <select id="txtSituacaoEstoque^<%=i %>" name="txtSituacaoEstoque^<%=i %>" class="form-control">
			                    <option id="<%=SituacaoEstoque.PENDENTE.getCodigo() %>" value="<%=SituacaoEstoque.PENDENTE.getCodigo() %>" selected ><%=SituacaoEstoque.PENDENTE.getDescricao() %> </option>
			                  </select>
			                </td>
			                <td class="text-center">
			                  <div class="dropdown">
			                    <a class="btn btn-sm btn-icon-only text-light" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			                      <i class="fas fa-ellipsis-v"></i>
			                    </a>
			                    <div class="dropdown-menu dropdown-menu-right dropdown-menu-arrow">
			                      <a class="dropdown-item" href="#"><i class="ni ni-button-power text-red"></i> Excluir</a>
			                   </div>
			                  </div>
			                </td>
			              </tr>
		            	<%
		            				i += 1;
		            			}
		            		}
		            	%>
		              </tbody>
		          </table>
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
