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
  
  <link href="bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
  <script src="bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
  <script src="bootstrap-4.3.1-dist/js/jquery-3.3.1.min.js"></script>
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
      <a class="navbar-brand pt-0" href="./index_cliente.jsp">
        <img src="./assets/img/brand/carbooks.png" class="navbar-brand-img" alt="...">
      </a>
      <!-- Collapse -->
      <div class="collapse navbar-collapse" id="sidenav-collapse-main">
        <!-- Collapse header -->
        <div class="navbar-collapse-header d-md-none">
          <div class="row">
            <div class="col-6 collapse-brand">
              <a href="./index_cliente.jsp">
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
        <%
        	List<EntidadeDominio> autores = new AutorDAO().getLista();
            for (EntidadeDominio ed : autores) {
            	Autor autor = (Autor) ed;	
        %>
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link" href="index_cliente.jsp?idAutor=<%=autor.getId()%>">
              <i class="ni ni-circle-08 text-primary"></i> <%=autor.getNomeArtistico() %>
            </a>
          </li>
        </ul>
        <%
            }
        %>
      </div>
    </div>
  </nav>
  <!-- Main content -->
  <div class="main-content">
    <%
    	Usuario usuario = UsuarioLogado.getUsuario();
    %>
    <!-- Header -->
    <div class="header bg-gradient-primary pb-8 pt-5 pt-md-8">
      <div class="container-fluid">
        <div class="header-body">			
			<div class="container">
				<div class="row">
					<!------ shopping Demo-3 ---------->
					<div class="container">
						<h3 class="h3">Livros </h3>
						<div class="row">
						    <%
						       int i;
						       boolean exibir;
						       String iAutor = request.getParameter("idAutor");
					      	   List<EntidadeDominio> livros = new LivroDAO().getLivrosAtivos();
						       for (EntidadeDominio ed : livros) {
						    	   i = 0;
						    	   exibir = false;
						    	   Livro livro = (Livro) ed;
						    	   
						    	   if (iAutor != null && !iAutor.trim().equals("")) {
						    		   for (Autor autor : livro.getAutores()) {
						    			   if (Integer.parseInt(iAutor) == autor.getId()) {
						    				   exibir = true;
						    			   }
						    		   }   
						    	   } else {
						    		   exibir = true;
						    	   }
						    	   
						    	   if (exibir) {
						    %>
							<div class="col-md-3 col-sm-6">
								<div class="product-grid3">
									<div class="product-image3">
										<a href="descricao_produto.jsp?idLivro=<%=livro.getId()%>">
											<img class="pic-1" src="images/<%=livro.getId() + "-" + ++i%>-new.jpg">
											<img class="pic-2" src="images/<%=livro.getId() + "-" + ++i%>-new.jpg">
										</a>
										<ul class="social">
											<li><a href="SalvarCarrinho?operacao=SALVAR&idLivro=<%=livro.getId() %>&idUsuario=<%=usuario.getId() %>"><i class="fa fa-shopping-cart"></i></a></li>
										</ul>
									</div>
									<div class="product-content">
										<div class="title"><a href="#"><%=livro.getTitulo() %></a></div>
										<div class="price">R$ <%out.print(new EstoqueDAO().getMediaValorByIdLivro(livro.getId())); %></div>
									</div>
								</div>
							</div>
							<%
						    	   }
						       }
							%>
						</div>
					</div>
				</div>
			</div>
        </div>
      </div>
    </div>
    <!-- Top navbar -->
    <nav class="navbar navbar-top navbar-expand-md navbar-dark" id="navbar-main">
      <div class="container-fluid">
        <!-- Brand -->
        <a class="h4 mb-0 text-white text-uppercase d-none d-lg-inline-block"></a>
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
              <a href="./perfil.jsp" class="dropdown-item">
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
    <!-- Page content -->
    <div class="container-fluid mt--7">
      <div class="row mt-5">
        <div class="col-xl-8 mb-5 mb-xl-0">
        </div>
        <div class="col-xl-4">
        </div>
      </div>
      <!-- Footer -->
      <footer class="footer">
        <div class="row mt-5 align-items-center justify-content-xl-between">
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
  <!-- Optional JS -->
  <script src="./assets/vendor/chart.js/dist/Chart.min.js"></script>
  <script src="./assets/vendor/chart.js/dist/Chart.extension.js"></script>
  <!-- Argon JS -->
  <script src="./assets/js/argon.js?v=1.0.0"></script>
</body>

</html>
