<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inscription</title>
<jsp:include page="partial/_links.jsp"></jsp:include>
</head>
<body>
	<h1 class="container text-center text-primary my-5">Inscription</h1>
	
	<c:url var="signup" value="/signup"></c:url>
	<div class="container p-4 border border-2 rounded-4 col-sm-4">
		<form method="post">
			<div class="mb-4 row">
				<label for="last_name" class="col-sm-3 col-form-label">Nom :</label>
				<div class="col-sm-9">
					<input class="form-control" type="text" name="last_name" id="last_name" required autocomplete="family-name" value="${error_user.lastName}">
				</div>
			</div>
			<div class="mb-4 row">
				<label for="first_name" class="col-sm-3 col-form-label">Prénom :</label>
				<div class="col-sm-9">
					<input class="form-control" type="text" name="first_name" id="first_name" required autocomplete="name" value="${error_user.firstName}">
				</div>
			</div>
			<div class="mb-4 row">
				<label for="email" class="col-sm-3 col-form-label">Email :</label> 
				<div class="col-sm-9">
					<input class="form-control" type="email" name="email" id="email" required autocomplete="email" value="${error_user.email}">
				</div> 
			</div>
			<div class="mb-4 row">
				<label for="password" class="col-sm-3 col-form-label">Mot de passe :</label>
				<div class="col-sm-9">
					<input class="form-control" type="password" name="password" id="password" required autocomplete="new-password">
				</div>
			</div>

			<div class="row">
				<div class="offset-9 col-sm-3">
					<button class="btn btn-primary w-100" type="submit" formaction="${ signup }">S'inscrire</button>
				</div>	
			</div>
		</form>
	</div>	
	
	<c:if test="${!empty error}">
		<div class="alert alert-danger alert-dismissible container col-sm-4 fade show" role="alert">
			<c:out value="${error}"></c:out>
			 <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
	</c:if>
</body>
</html>