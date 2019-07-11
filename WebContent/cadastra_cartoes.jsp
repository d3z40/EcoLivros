<!DOCTYPE html>

<%@page import="br.com.ecolivros.core.util.UsuarioLogado"%>
<%@page import="br.com.ecolivros.enuns.BandeiraCartaoDeCredito"%>
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
        	Usuario usuario = UsuarioLogado.getUsuario();
        	CartaoDeCredito cartaoDeCredito = (CartaoDeCredito) request.getAttribute("cartaoDeCredito");
        %>
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link" href="./perfil.jsp">
              <i class="ni ni-tv-2 text-primary"></i> Perfil
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="./cadastra_enderecos.jsp">
              <i class="ni ni-book-bookmark text-green"></i> Cadastro de Endereço
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="./consulta_enderecos.jsp">
              <i class="ni ni-books text-yellow"></i> Consulta Endereços
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="./cadastra_cartoes.jsp">
              <i class="ni ni-collection text-red"></i> Cadastro de Cartão
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="./consulta_cartoes.jsp">
              <i class="ni ni-bullet-list-67 text-blue"></i> Consulta Cartões
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="./carrinho.jsp">
              <i class="ni ni-cart text-orange"></i> Carrinho
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="./consulta_pedidos.jsp">
              <i class="ni ni-bag-17 text-cyan"></i> Meus Pedidos
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="./consulta_cupons.jsp">
              <i class="ni ni-ungroup text-green"></i> Meus Cupons
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="./consulta_trocas_cliente.jsp">
              <i class="ni ni-send text-red"></i> Minhas Trocas
            </a>
          </li>
		  <li class="nav-item">
            <a class="nav-link" href="./login.jsp">
              <i class="ni ni ni-user-run text-black"></i> Sair
            </a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <!-- Main content -->
  <div class="main-content">
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
    <!-- Header -->
    <div class="header pb-8 pt-3 pt-lg-1 d-flex align-items-center" style="min-height: 350px; background-image: url(../assets/img/theme/livros-cover.jpg); background-size: cover; background-position: center top;">
      <!-- Mask -->
      <span class="mask bg-gradient-default opacity-8"></span>
      <!-- Header container -->
      <div class="container-fluid d-flex align-items-center">
        <div class="row">
          <div class="col-lg-13 col-md-12">
            <h1 class="display-2 text-white">Perfil</h1>
            <p class="text-white mt-0 mb-5">Esse é o cadastro de cartões. Vocé poderá incluir e alterar as informações de um cartão.</p>
          </div>
        </div>
      </div>
    </div>
    <!-- Page content -->
    <div class="container-fluid mt--9">
      <form action="SalvarCartaoDeCredito" method="POST">
		  <div class="row">
			<div class="col-xl-12 order-xl-5">
			  <div class="card bg-secondary shadow">
				<div class="card-body">
					<div class="card-header bg-white border-0">
					  <div class="row align-items-center">
						<div class="col-8">
						  <h3 class="mb-0">Cartão</h3>
						</div>
						<div class="col-4 text-right">
						  <button type="submit" class="btn btn-primary">Salvar</button>
						</div>
					  </div>
					</div>
					<h6 class="heading-small text-muted mb-4">Informações do Usuário</h6>
					<input type="hidden" name="txtIdCartaoDeCredito" value="<% if (cartaoDeCredito != null) out.print(cartaoDeCredito.getId()); %>" />
					<input type="hidden" name="operacao" value="<% if (cartaoDeCredito != null) out.print("ALTERAR"); else out.print("SALVAR");%>" />
					<div class="pl-lg-4">
					  <div class="row">
						<div class="col-md-12">
						  <div class="form-group">
							<label class="form-control-label" for="txtNomeNoCartao">Nome no Cartão</label>
							<input type="text" id="txtNomeNoCartao" name="txtNomeNoCartao" value="<%if (cartaoDeCredito != null) out.print(cartaoDeCredito.getNomeNoCartao()); %>" class="form-control form-control-alternative" placeholder="Nome no Cartão">
						  </div>
						</div>
					  </div>
					  <div class="row">
						<div class="col-lg-3">
						  <div class="form-group">
							<label class="form-control-label" for="txtNumeroDoCartao">Número do Cartão</label>
							<input type="text" id="txtNumeroDoCartao" name="txtNumeroDoCartao" value="<%if (cartaoDeCredito != null) out.print(cartaoDeCredito.getNumeroDoCartao()); %>" class="form-control form-control-alternative" placeholder="Número do Cartão">
						  </div>
						</div>
						<div class="col-lg-3">
						  <div class="form-group">
							<label class="form-control-label" for="txtMesValidade">Mês Validade</label>
							<input type="text" id="txtMesValidade" name="txtMesValidade" value="<%if (cartaoDeCredito != null) out.print(cartaoDeCredito.getMesValidade()); %>" class="form-control form-control-alternative" placeholder="Mês Validade">
						  </div>
						</div>
						<div class="col-lg-3">
						  <div class="form-group">
							<label class="form-control-label" for="txtAnoValidade">Ano Validade</label>
							<input type="text" id="txtAnoValidade" name="txtAnoValidade" value="<%if (cartaoDeCredito != null) out.print(cartaoDeCredito.getAnoValidade()); %>" class="form-control form-control-alternative" placeholder="Ano Validade">
						  </div>
						</div>
						<div class="col-lg-3">
						  <div class="form-group">
							<label class="form-control-label" for="txtCodigoSeguranca">Código Segurança</label>
							<input type="text" id="txtCodigoSeguranca" name="txtCodigoSeguranca" value="<%if (cartaoDeCredito != null) out.print(cartaoDeCredito.getCodigoSeguranca()); %>" class="form-control form-control-alternative" placeholder="Código Seguranca">
						  </div>
						</div>
					  </div>
					  <div class="row">
					    <div class="col-lg-6">
						  <div class="form-group">
							<label class="form-control-label" for="txtBandeira">Bandeira</label>
							<select id="txtBandeira" name="txtBandeira" class="form-control">
								<%
									for (BandeiraCartaoDeCredito bandeira : BandeiraCartaoDeCredito.values()) {
								%>
								<option id=<%=bandeira.getCodigo() %> value=<%=bandeira.getCodigo() %> <%if (cartaoDeCredito != null && cartaoDeCredito.getBandeira().getCodigo() == bandeira.getCodigo()) out.print("selected"); %>><%=bandeira.getDescricao() %> </option>
								<%
									}
								%>
							</select>
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
