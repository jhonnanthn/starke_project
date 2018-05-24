<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="author" content="PedroGallonAlves-816124368,JhonnanthnBalsas-#,MilenaCaroline-816120204,FilipeRoque-#,VictorHiga-#">
		<title>FilaStarke</title>
		<link href="assets/css/geral-style.css" rel="stylesheet">
		<link href="assets/css/atender-style.css" rel="stylesheet">
		<link rel="stylesheet" href="assets/css/bootstrap.min.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  		<script src="assets/js/jquery-3.3.1.min.js"></script>
 		<script src="assets/js/bootstrap.min.js"></script>
	</head>
	<body>
		<c:import url="nav.jsp" />
		<!-- Modal -->
		<div class="modal fade" data-backdrop="static" id="modalProsseguir" role="dialog">
		    <div class="modal-dialog">
		      <div class="modal-content">
		        <div class="modal-header">
		          <h4 class="modal-title">Atender Senha</h4>
		        </div>
		        <div class="modal-body">
		          	<p class="modal-subtitle">A senha <span class="senha"></span> está sendo chamada no painel. <br/>Qual ação deseja realizar?</p>
	        		<div class="modal-button">
	          			<button type="button" class="btn btn-success btnProsseguir" data-dismiss="modal" data-toggle="modal">Prosseguir</button>
	        			<button type="button" class="btn btn-danger btnFinalizar" data-dismiss="modal">Cancelar</button>
	        		</div>
		        </div>
		      </div>
		    </div>
		</div>
		
		<!-- Modal -->
		<div class="modal fade" data-backdrop="static" id="modalEncaminhar" role="dialog">
		    <div class="modal-dialog">
		      <div class="modal-content">
		        <div class="modal-header">
		          <h4 class="modal-title">Atendendo senha</h4>
		        </div>
		        <div class="modal-body">
		         	<h2 class="modal-title"><span class="senha"></span></h2>
		          	<p class="nome-servico"></p>
					<div class="modal-process">
						<h2>Processos</h2>
						<section class="table-processos">
							<div class="thead-processos">
								<div class="table-line">
									<p>Serviço</p>
									<p>Início</p>
									<p>Término</p>
								</div>
							</div>
							<div class="tbody-processos">
								
							</div>
						</section>
					</div>
	        		<p class="modal-subtitle">A senha <span class="senha"></span> está sendo atendida. <br/>Qual ação deseja realizar agora?</p>
	        		<div class="modal-button">
	          			<button type="button" class="btn btn-success btnEncaminhar" data-dismiss="modal" data-toggle="modal">Encaminhar</button>
	        			<button type="button" class="btn btn-danger encerrar" data-dismiss="modal">Cancelar</button>
	        		</div>
		        </div>
		      </div>
		    </div>
		</div>
		
		<div class="container">
			<section class="s-container">
				<!--  <input type="hidden" name="proximaChamada" id="proximaChamada" value="${proximaChamada }"> -->
				<div class="s-container-result">
					<section class="table">
						<div class="thead">
							<div class="table-line">
								<p>Senha</p>
								<p>Tipo</p>
								<p>Status</p>
								<p>Horário de Início</p>
								<p>Opção</p>
							</div>
						</div>
						<div class="tbody">
						</div>
					</section>
				</div>
				<div class="s-container-form">
					<div class="s-text-container"></div>
					<form action="" method="post">
						<div class="form-group">
							<label for="senha_servico">Serviço</label>
							<select class="form-control" name="senha_servico" id="senha_servico" required>
		                        <option value="" selected disabled>Selecione um serviço</option>
		                        <c:forEach var="servicos" items="${servicos}">
		                            <option value="${servicos.id}">${servicos.nome}</option>
		                        </c:forEach>
		                    </select>
						</div>
						<div class="form-group">
							<label for="senha_servico">Subserviço</label>
							<select class="form-control" name="subServico" id="subServico" required>
		                        <option value="" selected disabled>Selecione um subserviço</option>
		                    </select>
						</div>
						<div class="form-group">
							<button class="form-control btn btn-primary senha_chamar">Procurar</button>
						</div>
					</form>
				</div>
			</section>
			<div class="form-group input-table">
				<button class="form-control btn btn-primary atender">Atender</button>
<!-- 				<button class="form-control btn btn-primary encerrar">Encerrar Atendimento</button> -->
			</div>
		</div> 
		<script src="assets/js/jquery-3.3.1.min.js"></script>
		<script src="assets/js/popper.min.js"></script>	
		<script src="assets/js/script.js"></script>	
		<script src="assets/js/bootstrap-3-3-1.min.js"></script>
		<script src="assets/js/sweetalert.min.js"></script>
		
		<script>
			var senhaChamada = null;
			$(".input-table .atender").click(function(e){
				$.ajax({
      			    type : "GET",
      				url: "${pageContext.request.contextPath}/atender_proxima_senha", 
      				data : {
  			    		servico : $("#senha_servico option:selected").val(),
  			    		subservico : $("#subServico option:selected").val()
  			    	},
      				success: function(result){
      					if(result == "" || result == null){
              				swal("Error!", "Não foi possível identificar a senha.", "error");
      					} else{
      						senhaChamada = result;  
      						$(".senha").text(result.nome);
      						$('#modalProsseguir').modal('show');
      					}
          			},
          			error: function(result){
          				swal("Error!", "Não foi possível atender a senha.", "error");
          			}
      			});
			});
			
			$(".encerrar").click(function(e){
				$.ajax({
      			    type : "GET",
      				url: "${pageContext.request.contextPath}/finalizaSenha", 
      				data : {
  			    		id : senhaChamada.id
  			    	},
      				success: function(result){
      					swal("Senha encerrada com sucesso.")
      					.then((value) => {
          					location.reload(true);
      					});
          			},
          			error: function(result){
          				swal("Error!", "Não foi possível encerrar a senha.", "error");
          			}
      			});
			});
			
			$(".btnProsseguir").click(function(e){
				$.ajax({
      			    type : "GET",
      				url: "${pageContext.request.contextPath}/atenderSenha_prosseguir", 
      				data : {
  			    		id : senhaChamada.id
  			    	},
      				success: function(result){
      					console.log(result);
      					$("#modalEncaminhar .nome-servico").html(result.servico.nome + " - " + result.tipo);
      					$.ajax({
      	      			    type : "GET",
      	      				url: "${pageContext.request.contextPath}/load_processos", 
      	      				data : {
      	  			    		id : senhaChamada.id
      	  			    	},
      	      				success: function(data){
      	      					console.log(data);
      	      					$(".tbody-processos").html("");
      	      					for (var i = 0; i < data.length; i++) {
      	      						var entrada = new Date(data[i].dataEntrada);
      	      						var saida = data[i].dataSaida ? new Date(data[i].dataSaida) : null;
	      	      					$(".tbody-processos").append('<div class="table-line"><p>' + data[i].subservico.nome +
	      	      						'</p><p>' + truncate(entrada.getHours(), 2) + ':' + truncate(entrada.getMinutes(), 2) + 
	  	      							':' + truncate(entrada.getSeconds(), 2) + '</p><p>' +
	  	      							(saida ? truncate(saida.getHours(), 2) + ':' + truncate(saida.getMinutes(), 2) + 
	  		  	      					':' + truncate(saida.getSeconds(), 2) : '-') + '</p></div>');
								}
      	      					$("#modalEncaminhar").modal();
      	          			},
      	          			error: function(data){
      	      					console.log(data);
      	          				//swal("Error!", "Não foi possível prosseguir com a senha.", "error");
      	          			}
      	      			});
          			},
          			error: function(result){
          				swal("Error!", "Não foi possível prosseguir com a senha.", "error");
          			}
      			});
			});
			
			$(".btnEncaminhar").click(function(e){
				$.ajax({
      			    type : "GET",
      				url: "${pageContext.request.contextPath}/senha_proxima", 
      				data : {
  			    		senha : senhaChamada.id
  			    	},
      				success: function(result){
      					swal("Senha encaminhada com sucesso.")
      					.then((value) => {
          					location.reload(true);
      					});
          			},
          			error: function(result){
          				swal("Error!", "Não foi possível encaminhar a senha.", "error");
          			}
      			});
			});
			
			$(".btnFinalizar").click(function(e){
				$.ajax({
      			    type : "GET",
      				url: "${pageContext.request.contextPath}/atenderSenha_cancelar", 
      				data : {
  			    		id : senhaChamada.id
  			    	},
      				success: function(result){
      					swal("Senha finalizada com sucesso.")
      					.then((value) => {
          					location.reload(true);
      					});
          			},
          			error: function(result){
          				swal("Error!", "Não foi possível finalizar a senha.", "error");
          			}
      			});
			});
			
			$(document).on("click", ".voltarParaFila",function(e){
				id = $(this).data("id");
				$.ajax({
      			    type : "GET",
      				url: "${pageContext.request.contextPath}/atenderSenha_voltarFila", 
      				data : {
  			    		id : id
  			    	},
      				success: function(result){
      					swal("Senha voltou para a fila com sucesso.")
      					.then((value) => {
          					location.reload(true);
      					});
          			},
          			error: function(result){
          				swal("Error!", "Não foi possível voltar a senha para a fila.", "error");
          			}
      			});
			});
				
			$('#senha_servico').change(function(e){
      			$.ajax({
      			    type : "GET",
      				url: "${pageContext.request.contextPath}/listar_subservico", 
      				data: {
      					id : $('#senha_servico').val()
      				},
      				success: function(result){
             			$("#subServico").html('');
             			for(var i = 0; i < result.length; i++){
                 			$("#subServico").append('<option value="' + result[i].id + 
                 					'">' + result[i].nome + '</option>');
             			}
          			}
      			});
      	  	});
			
			$('.senha_chamar').click(function(e){
			    e.preventDefault();
			    $.ajax({
      			    type : "GET",
      			    data : {
      			    		servico : $("#senha_servico option:selected").val(),
      			    		subservico : $("#subServico option:selected").val()
      			    },
      				url: "${pageContext.request.contextPath}/listar_senhas_atendimento", 
      				success: function(result){
      					//console.log(result);
      					$(".tbody").html("");
      					if(result.length == 0)
      						$(".input-table").hide();
      					else
      						$(".input-table").show();
               			for (var i = 0; i < result.length; i++) {
               				var date = new Date(result[i].dataEntrada);
               				$(".tbody").append('<div class="table-line">' +
     							'<p id="senha' + i + '" data-id="' + result[i].id +'">' + result[i].nome +'</p>' +
   								'<p>' + result[i].tipo +'</p>' +
   								'<p>' + result[i].status +'</p>' +
   								'<p>' + truncate(date.getDate(), 2) + "/" + 
   								truncate(date.getMonth() + 1, 2) + "/" + date.getFullYear() +
   								" " + truncate(date.getHours(),2) + ":" + truncate(date.getMinutes(),2) + ":" + 
   								truncate(date.getSeconds(),2) +'</p>' +
   								'<p><a type="button" data-id="' + result[i].id+ '" class="btn btn-danger voltarParaFila' + (result[i].status == 'em fila' ? ' disabled' : '') + '"><i class="fa fa-warning"></i></a></p>' +
    							'</div>');
						}
          			}
      			});
      	  	});
		</script>
	</body>
</html>