<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="author"
	content="PedroGallonAlves-816124368,JhonnanthnBalsas-#,MilenaCaroline-#,FilipeRoque-#,VictorHiga-#">
<title>FilaStarke</title>
<link href="assets/css/bootstrap.min.css" rel="stylesheet">
<link href="assets/css/style.css" rel="stylesheet">
</head>

<body>
	<c:import url="nav.jsp" />
	<div class="container">
		<div class="row gerador">
			<h3 class="col-12">FilaStärke</h3>
			<div class="col-4">
				<div class="form-group">
					<button class="btn btn-primary form-control"
						onclick="senhaGerador();">Gerar Senha</button>
				</div>
				<div class="form-group">
					<button class="btn btn-primary form-control"
						onclick="senhaListar();">Listar Senhas</button>
				</div>
			</div>
		</div>
	</div>
	<script src="assets/js/jquery-3.3.1.min.js"></script>
	<script src="assets/js/bootstrap.min.js"></script>
	<script src="assets/js/popper.min.js"></script>
	<script>
		$(document).ready(function() {
			$(".navHome").addClass("active");
		});

		function senhaGerador() {
			document.location.href = "criar_senha_gerador";
		}
		function senhaListar() {
			document.location.href = "senha_gerador";
		}
	</script>

</body>

</html>