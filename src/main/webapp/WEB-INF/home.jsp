<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Page d'accueil</title>
<jsp:include page="partial/_links.jsp"></jsp:include>
</head>
<body class="bg-light">
	<jsp:include page="partial/_menu.jsp"></jsp:include>
	
	<h1 class="container text-center my-4">
		<span class="text-secondary">Bienvenue,</span>
		<span class="text-primary">${first_name} ${last_name}</span>
	</h1>
</body>
</html>