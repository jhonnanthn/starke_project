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
		<meta name="author" content="PedroGallonAlves-816124368,JhonnanthnBalsas-#,MilenaCaroline-#,FilipeRoque-#,VictorHiga-#">
		<title>FilaStarke</title>
		<link href="assets/css/geral-style.css" rel="stylesheet">
		<link href="assets/css/atender-style.css" rel="stylesheet">
	</head>
	<body>
		<c:import url="nav.jsp" />
		<div class="container">
			<section class="s-container">
				<div class="s-container-result">
					<section class="table">
						<div class="thead">
							<div class="table-line">
								<p> </p>
								<p>Senha</p>
								<p>Tipo</p>
								<p>Horário de Início</p>
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
							<button class="form-control btn btn-primary" type="submit" value="submit">Procurar</button>
						</div>
					</form>
				</div>
			</section>
		</div> 
		<script src="assets/js/jquery-3.3.1.min.js"></script>
		<script src="assets/js/popper.min.js"></script>	
		<script>
			$('#senha_servico').change(function(e){
				console.log($('#senha_servico').val());
      			$.ajax({
      			    type : "GET",
      				url: "${pageContext.request.contextPath}/listar_subservico", 
      				data: {
      					id : $('#senha_servico').val()
      				},
      				success: function(result){
             			console.log(result);
             			$("#subServico").html('<option value="full" selected>Todos</option>');
             			for(var i = 0; i < result.length; i++){
                 			$("#subServico").append('<option value="' + result[i].id + 
                 					'">' + result[i].nome + '</option>');
             			}
          			}
      			});
        	});
		</script>
	</body>
</html>