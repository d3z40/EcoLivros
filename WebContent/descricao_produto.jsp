<!DOCTYPE html>

<%@page import="br.com.ecolivros.enuns.SituacaoItem"%>
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
</head>

<body>
  <!-- Main content -->
  <div class="main-content">
    <!-- Top navbar -->
    <nav class="navbar navbar-top navbar-expand-md navbar-dark" id="navbar-main">
      <div class="container-fluid">
        <!-- Brand -->
        <a class="h4 mb-0 text-white text-uppercase d-none d-lg-inline-block">Descri��o</a>
        <!-- User -->
        <%
        	Usuario usuario = UsuarioLogado.getUsuario();
        %>
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
    <%
    	Livro livro = null;
        String iLivro = request.getParameter("idLivro");
    	if (iLivro != null && !iLivro.trim().equals("")) {
 	  		livro = (Livro) new LivroDAO().getEntidadeDominio(Integer.parseInt(iLivro));
    	}
	%>
    <div class="header bg-gradient-primary pb-8 pt-5 pt-md-8">
      <div class="container-fluid">
        <div class="header-body">			
			<div class="container">
				<div class="row">
					<!-- Card Projects -->
					<div class="col-md-6 col-md-offset-3">
						<div class="card">
							<div class="card-image">
								<img style="width: 100%" class="img-responsive" src="images/<%=livro.getId()%>-3.jpg">
								<h3 class="mb-0"><%if (livro != null) out.print(livro.getTitulo()); %></h3>
							</div>
							
							<div class="card-content">
								<p><%if (livro != null) out.print(livro.getSinopse()); %></p>
							</div>
							
							<div class="card-action">
							    <ul class="social">
									<li><a href="SalvarCarrinho?operacao=SALVAR&idLivro=<%=livro.getId() %>&idUsuario=<%=usuario.getId() %>"><i class="fa fa-shopping-cart"></i></a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
        </div>
      </div>
    </div>
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
