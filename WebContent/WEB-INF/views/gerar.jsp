<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="author" content="PedroGallonAlves-816124368,JhonnanthnBalsas-#,MilenaCaroline-#,FilipeRoque-#,VictorHiga-#">
	<title>FilaStarke</title>
	<link href="assets/css/bootstrap.min.css" rel="stylesheet">
	<link href="assets/css/style.css" rel="stylesheet">
</head>

<body>
	<c:import url="nav.jsp" />
	<div class="container">
		<div class="row gerador">
			
			
			<form action="senha_gerar" method="post">
				<div class="form-group">
				<label for="senha_tipo">Tipo:</label>
					<select class="form-control" name="senha_tipo" id="senha_tipo">
						<option value="comum">Comum</option>
						<option value="preferencial">Preferencial</option>
						<option value="emergencial">Emergencial</option>
					</select>
				</div>
				<div class="form-group">
				<label for="senha_servico">Serviço:</label>
					<select class="form-control" name="senha_servico" id="senha_servico">
                        <option value="" selected disabled>Selecione um serviço</option>
                        <c:forEach var="servicos" items="${servicos}">
                            <option value="${servicos.id}">${servicos.nome}</option>
                        </c:forEach>
                    </select>
				</div>
				<div class="form-group">
				<button class="form-control btn btn-primary" type="submit" value="submit">Gerar</button>
</div>
		</form>
		</div>
	</div> 
	<script src="assets/js/jquery-3.3.1.min.js"></script>
	<script src="assets/js/bootstrap.min.js"></script>
	<script src="assets/js/popper.min.js"></script>
	<script>
		$(document).ready(function(){
			$(".navGerar").addClass("active");
		});
	</script>
	
</body>

</html>