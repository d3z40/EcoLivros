<!DOCTYPE html>

<%@page import="br.com.ecolivros.enuns.Frete"%>
<%@page import="br.com.ecolivros.enuns.TipoPagamento"%>
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
        %>
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link" href="./perfil.jsp">
              <i class="ni ni-tv-2 text-primary"></i> Perfil
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="./cadastra_enderecos.jsp">
              <i class="ni ni-book-bookmark text-green"></i> Cadastro de Endere�o
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="./consulta_enderecos.jsp">
              <i class="ni ni-books text-yellow"></i> Consulta Endere�os
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="./cadastra_cartoes.jsp">
              <i class="ni ni-collection text-red"></i> Cadastro de Cart�o
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="./consulta_cartoes.jsp">
              <i class="ni ni-bullet-list-67 text-blue"></i> Consulta Cart�es
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
            <h1 class="display-2 text-white">Pedido</h1>
            <p class="text-white mt-0 mb-5">Esse � o cadastro de pedido. Voc� poder� efetuar um pedido.</p>
          </div>
        </div>
      </div>
    </div>
    <!-- Page content -->
    <div class="container-fluid mt--9">
      <form action="SalvarPedido" method="POST">
		  <div class="row">
			<div class="col-xl-12 order-xl-5">
			  <div class="card bg-secondary shadow">
				<div class="card-body">
					<div class="card-header bg-white border-0">
					  <div class="row align-items-center">
						<div class="col-8">
						  <h3 class="mb-0">Pedido</h3>
						</div>
						<div class="col-4 text-right">
						  <button type="submit" class="btn btn-primary">Salvar</button>
						</div>
					  </div>
					</div>
					<h6 class="heading-small text-muted mb-4">Informa��es do Pedido</h6>
					<input type="hidden" name="operacao" value="SALVAR" />
					<div class="pl-lg-4">
					  <div class="row">
						<div class="col-lg-6">
						  <div class="form-group">
							<label class="form-control-label" for="txtNumPedido">N� Pedido</label>
							<input readonly type="text" id="txtNumPedido" name="txtNumPedido" value="" class="form-control form-control-alternative" placeholder="N� Pedido">
						  </div>
						</div>
					  </div>
				  	</div>
				  	<hr class="my-4" />
					<h6 class="heading-small text-muted mb-4">Carrinho</h6>
					<div class="table-responsive">
		              <table id="tblCarrinho" class="table align-items-center table-flush">
			            <thead class="thead-light">
			              <tr>
			              	<th scope="col">Livro</th>
			                <th scope="col">Qtde</th>
			                <th scope="col">Valor</th>
			                <th scope="col">Total</th>
			              </tr>
			            </thead>
			              <tbody>
			            	<%
			            		List<EntidadeDominio> carrinho = new CarrinhoDAO().getLista();
			            		Pedido pedido = new PedidoDAO().getPedido(carrinho);
								application.setAttribute("pedido", pedido);
								double totalPedido = 0;
								
			            		if (pedido != null && pedido.getItensPedido() != null) {
			            			int i = 0;
			            			List<ItemPedido> itensPedido = new ItemPedidoDAO().organizaItensCarrinho(pedido.getItensPedido());
				            		for (ItemPedido itemPedido : itensPedido) {
			            	%>
			            	  <tr>
			            	    <td width="650">
			            	      <input readonly type="text" id="txtLivro" name="txtLivro" value="<%if (itemPedido != null) out.print(new LivroDAO().getTitulo(itemPedido.getIdLivro())); %>" class="form-control form-control-alternative">
			            	    </td>
			            	    <td width="150">
				                  <input readonly type="text" id="txtQtde" name="txtQtde" value="<%if (itemPedido != null) out.print(itemPedido.getQtde()); %>" class="form-control form-control-alternative">
				                </td>
				                <td width="150">
				                  <input readonly type="text" id="txtValor" name="txtValor" value="<%if (itemPedido != null) out.print(String.format("R$ %,6.2f", itemPedido.getValor())); %>" class="form-control form-control-alternative">
				                </td>
				                <td width="150">
				                  <input readonly type="text" id="txtTotal" name="txtTotal" value="<%if (itemPedido != null) out.print(String.format("R$ %,6.2f", itemPedido.getQtde() * itemPedido.getValor())); %>" class="form-control form-control-alternative">
				                </td>
				              </tr>
			            	<%
				            		}
			            		}
				            %>
			              </tbody>
			          </table>
		            </div>
		            <hr class="my-4" />
					<div class="table-responsive">
		              <table id="tblTotalPedido" class="table align-items-center table-flush">
			            <thead class="thead-light">
			            </thead>
			              <tbody>
			            	  <tr>
			            	    <td width="950">
			            	      <input readonly type="text" id="txtDescSubTotalPedido" name="txtDescTotal" value="SUBTOTAL PEDIDO" class="form-control form-control-alternative">
			            	    </td>
			            	    <td width="150">
				                  <input readonly type="text" id="txtSubTotalPedido" name="txtTotalPedido" value="<%out.print(String.format("R$ %,6.2f", pedido.getTotal())); %>" class="form-control form-control-alternative">
				                </td>
				              </tr>
			              </tbody>
			          </table>
		            </div>
		            <hr class="my-4" />
					<h6 class="heading-small text-muted mb-4">Endere�os</h6>
		            <div class="pl-lg-4">
					  <div class="row">
						<div class="col-lg-6">
						  <div class="form-group">
							<label class="form-control-label" for="txtEnderecos">Endere�os</label>
							<select id="txtEnderecoPedido" name="txtEnderecoPedido" class="form-control">
								<%
									List<EntidadeDominio> enderecos = new EnderecoDAO().getEntidadeDominio(UsuarioLogado.getUsuario().getId());
									Endereco endTemp = null;
									boolean first = true;

									for (EntidadeDominio ed : enderecos) {
										Endereco endereco = (Endereco) ed;
										if (first) {
											endTemp = endereco;
											first = false;
										}
								%>
								<option id=<%=endereco.getId() %> value=<%=endereco.getId() %>><%=endereco.getDescricao() %> </option>
								<%
									}
								%>
							</select>
						  </div>
						</div>
					  </div>
					</div>
					<hr class="my-4" />
					<div class="table-responsive">
		              <table id="tblFrete" class="table align-items-center table-flush">
			            <thead class="thead-light">
			            </thead>
			              <tbody>
			            	  <tr>
			            	    <td width="950">
			            	      <input readonly type="text" id="txtDescFrete" name="txtDescFrete" value="VALOR FRETE" class="form-control form-control-alternative">
			            	    </td>
			            	    <td width="150">
			            	    <%
			            	    	for (Frete frete : Frete.values()) {
			            	    		if (frete.name().equals(endTemp.getUf())) {
			            	    			totalPedido = pedido.getTotal() + frete.getValor();
			            	    %>
			            	      <input readonly type="text" id="txtValorFrete" name="txtValorFrete" value="<%out.print(String.format("R$ %,6.2f", frete.getValor())); %>" class="form-control form-control-alternative">
			            	    <%
			            	    		}
			            	    	}
			            	    %>
				                </td>
				              </tr>
			              </tbody>
			          </table>
		            </div>
		            <hr class="my-4" />
					<div class="table-responsive">
		              <table id="tblFrete" class="table align-items-center table-flush">
			            <thead class="thead-light">
			            </thead>
			              <tbody>
			            	  <tr>
			            	    <td width="950">
			            	      <input readonly type="text" id="txtDescTotalPedido" name="txtDescTotalPedido" value="TOTAL PEDIDO" class="form-control form-control-alternative">
			            	    </td>
			            	    <td width="150">
			            	      <input readonly type="text" id="txtValorTotalPedido" name="txtValorTotalPedido" value="<%out.print(String.format("R$ %,6.2f", totalPedido)); %>" class="form-control form-control-alternative">
				                </td>
				              </tr>
			              </tbody>
			          </table>
		            </div>
		            <hr class="my-4" />
					<h6 class="heading-small text-muted mb-4">Formas de Pagamento</h6>
					<div class="table-responsive">
		              <table id="tblFormasDePagamento" class="table align-items-center table-flush">
			            <thead class="thead-light">
			              <tr>
			              	<th scope="col">Select</th>
			              	<th scope="col"></th>
			                <th scope="col">Tipo</th>
			                <th scope="col">Descri��o</th>
			                <th scope="col">Valor</th>
			              </tr>
			            </thead>
			              <tbody>
			                <%
			                	String descricaoPgto = null;
								List<FormaDePagamento> formasDePagamento = new FormaDePagamentoDAO().getFormasDePagamentoByUsuario(UsuarioLogado.getUsuario().getId());
			                	
			            		int i = 0;
			            		if (formasDePagamento != null && !formasDePagamento.isEmpty()) {
			                		for (FormaDePagamento formaDePagamento : formasDePagamento) {
			                			if (formaDePagamento.getPagamento() instanceof CartaoDeCredito) {
			                				CartaoDeCredito cdc = (CartaoDeCredito) formaDePagamento.getPagamento();
			                				descricaoPgto = cdc.getNomeNoCartao();
			                			} else if (formaDePagamento.getPagamento() instanceof Cupom) {
			                				Cupom c = (Cupom) formaDePagamento.getPagamento();
			                				descricaoPgto = c.getIdentificador() + " - TT: " + c.getValorCupom() + " Ult.: " + c.getValorUtilizado();
			                			}
							%>
			            	  <tr>
			            	    <td width="10">
			            	      <input type="checkbox" id="chkSelectFormaPgto^<%=i %>" name="chkSelectFormaPgto^<%=i %>">
			            	    </td>
			            	    <td width="0">
			            	      <input type="hidden" id="txtIdPagamentoFormaPgto^<%=i %>" name="txtIdPagamentoFormaPgto^<%=i %>" value="<%if (formaDePagamento != null) out.print(formaDePagamento.getPagamento().getId()); %>" class="form-control form-control-alternative">
			            	    </td>
				                <td width="280">
				                  <input readonly type="text" id="txtTipoPagamentoFormaPgto^<%=i %>" name="txtTipoPagamentoFormaPgto^<%=i %>" value="<%if (formaDePagamento != null) out.print(formaDePagamento.getTipoPagamento().name()); %>" class="form-control form-control-alternative">
				                </td>
				                <td width="570">
				                  <input readonly type="text" id="txtDescricaoFormaPgto^<%=i %>" name="txtDescricaoFormaPgto^<%=i %>" value="<%if (formaDePagamento != null) out.print(descricaoPgto); %>" class="form-control form-control-alternative">
				                </td>
				                <td width="200">
				                  <input type="text" id="txtValorFormaPgto^<%=i %>" name="txtValorFormaPgto^<%=i %>" value="" class="form-control form-control-alternative" placeholder="Valor">
				                </td>
				              </tr>
			            	<%
			            				i += 1;
				            		}			            		
			            		}
			            		application.setAttribute("QtdeFormaPgto", i);
				            %>
				              <tr>
			            	    <td width="10">
			            	      <input type="checkbox" id="chkSelectFormaPgto^<%=i %>" name="chkSelectFormaPgto^<%=i %>">
			            	    </td>
			            	    <td width="0">
			            	      <input type="hidden" id="txtIdPagamentoFormaPgto^<%=i %>" name="txtIdPagamentoFormaPgto^<%=i %>" value="" class="form-control form-control-alternative">
			            	    </td>
				                <td width="280">
				                  <input readonly type="text" id="txtTipoPagamentoFormaPgto^<%=i %>" name="txtTipoPagamentoFormaPgto^<%=i %>" value="<% out.print(TipoPagamento.BOLETO.name()); %>" class="form-control form-control-alternative">
				                </td>
				                <td width="570">
				                  <input readonly type="text" id="txtDescricaoFormaPgto^<%=i %>" name="txtDescricaoFormaPgto^<%=i %>" value="<%out.print(TipoPagamento.BOLETO.getDescricao()); %>" class="form-control form-control-alternative">
				                </td>
				                <td width="200">
				                  <input type="text" id="txtValorFormaPgto^<%=i %>" name="txtValorFormaPgto^<%=i %>" value="" class="form-control form-control-alternative" placeholder="Valor">
				                </td>
				              </tr>
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
