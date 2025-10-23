<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Connexion</title>
<jsp:include page="partial/_links.jsp"></jsp:include>
</head>
<body>
	<h1 class="container text-center text-primary my-5">Connexion</h1>
	
	<c:url var="signin" value="/signin"></c:url>
	<c:url var="signup" value="/signup"></c:url>
	<div class="container p-4 border border-2 rounded-4 col-sm-4">
		<form method="post">
			<div class="mb-4 row">
				<label for="a" class="col-sm-3 col-form-label">Email :</label> 
				<div class="col-sm-9">
					<input class="form-control" type="email" name="email" id="email" required autocomplete="email">
				</div> 
			</div>
			<div class="mb-4 row">
				<label for="b" class="col-sm-3 col-form-label">Mot de passe :</label>
				<div class="col-sm-9">
					<input class="form-control" type="password" name="password" id="password" required autocomplete="current-password">
				</div>
			</div>

			<div class="row">
				<div class="offset-9 col-sm-3">
					<button class="btn btn-primary w-100" type="submit" formaction="${ signin }">Connexion</button>
				</div>	
			</div>
		</form>
	</div>
	
	<div class="container mt-2 col-sm-4 text-center">
		<a href="${signup}" class="link-primary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">Inscription</a>
	</div>
	
	
	<c:if test="${!empty error}">
		<div class="alert alert-danger alert-dismissible container col-sm-4 fade show" role="alert">
			<c:out value="${error}"></c:out>
			 <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
	</c:if>
</body>
</html>