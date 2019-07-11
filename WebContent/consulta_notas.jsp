<!DOCTYPE html>

<%@page import="br.com.ecolivros.core.util.UsuarioLogado"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
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
        <a class="h4 mb-0 text-white text-uppercase d-none d-lg-inline-block">Consulta de Notas</a>
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
            <p class="text-white mt-0 mb-5">Esse é a consulta de notas. Vocé poderá consultar, deletar e selecionar para alterar as informações de uma nota.</p>
          </div>
        </div>
      </div>
    </div>
    <!-- Page content -->
    <%
 	  	List<EntidadeDominio> notas = (List<EntidadeDominio>) request.getAttribute("notas");
	%>
    <div class="container-fluid mt--9">
      <form action="SalvarNota" method="post" class="form-horizontal">
	      <div class="row">
	        <div class="col-xl-12 order-xl-5">
	          <div class="card shadow">
	            <div class="card-header border-4">
	              <div class="row">
	                <div class="col-lg-5">
	                  <div class="form-group">
	                    <h3 class="mb-0">Notas</h3>
	                  </div>
	                </div>
	                <div class="col-lg-5">
	                  <div class="form-group">
	                    <div class="input-group input-group-alternative">
	                      <div class="input-group-prepend">
	                        <span class="input-group-text"><i class="fas fa-search"></i></span>
	                      </div>
	                      <input id="search" name="search" class="form-control" placeholder="Pesquisar" type="text">
	                  </div>
	                  </div>
	                </div>
	                <div class="col-lg-0">
	                  <div class="form-group">
	                    <input type="submit" id="operacao" name="operacao" value="VISUALIZAR" class="btn btn-primary" value="Pesquisar">
	                  </div>
	                </div>
	              </div>
	            </div>
	            <div class="table-responsive">
	              <table id="tblLivro" class="table align-items-center table-flush">
		            <thead class="thead-light">
		              <tr>
		                <th scope="col">Núm. Nota</th>
		                <th scope="col">Fornecedor</th>
		                <th scope="col">Sit. Estoque</th>
		                <th scope="col">Ação</th>
		              </tr>
		            </thead>
		              <tbody>
		            	<%
		            		if (notas != null) {
			            		for (EntidadeDominio ed : notas) {
			            			Nota nt = (Nota) ed;
			            			
		            	%>
		            	  <tr>
	                		<td scope="row">
			                  <div class="media align-items-center">
			                    <div class="media-body">
			                      <span class="mb-0 text-sm"><b><%=nt.getNumeroNota() %></b></span>
			                    </div>
			                  </div>
			                </td>
			                <td>
			                  <%=nt.getFornecedor().getRazaoSocial() %>
			                </td>
			                <td>
			                  <%=nt.getSituacaoEstoque().getDescricao() %>
			                </td>
			                <td class="text-center">
			                  <div class="dropdown">
			                    <a class="btn btn-sm btn-icon-only text-light" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			                      <i class="fas fa-ellipsis-v"></i>
			                    </a>
			                    <div class="dropdown-menu dropdown-menu-right dropdown-menu-arrow">
			                      <a class="dropdown-item" href="SalvarNota?operacao=CONSULTAR&IdNota=<%=nt.getId() %>"><i class="ni ni-ruler-pencil text-blue"></i> Alterar</a>
			                      <a class="dropdown-item" href="SalvarNota?operacao=EXCLUIR&IdNota=<%=nt.getId() %>"><i class="ni ni-button-power text-red"></i> Excluir</a>
			                   </div>
			                  </div>
			                </td>
			              </tr>
		            	<%
			            		}
		            		}
		            	%>
		              </tbody>
		          </table>
	            </div>
	          </div>
	        </div>
	      </div>
	  </form>
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
  </div>
  
  <!-- Argon Scripts -->
  <!-- Core -->
  <script src="./assets/vendor/jquery/dist/jquery.min.js"></script>
  <script src="./assets/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
  <!-- Argon JS -->
  <script src="./assets/js/argon.js?v=1.0.0"></script>
</body>

</html>
