<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mon Profil</title>
<jsp:include page="partial/_links.jsp"></jsp:include>
</head>
<body class="bg-light mb-5">
	<jsp:include page="partial/_menu.jsp"></jsp:include>
	<h1 class="container text-center text-primary my-5">Mon Profil</h1>
	
	<c:if test="${!empty error}">
		<div class="alert alert-danger alert-dismissible container col-sm-4 fade show" role="alert">
			<c:out value="${error}"></c:out>
			 <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
	</c:if>
	
	<h2 class="container text-center text-secondary my-3">Mes Coordonnées</h2>
	<c:url var="profile" value="/profile"></c:url>
	<div class="container p-4 border border-2 rounded-4 col-sm-4">
		<form method="post">
			<div class="mb-4 row">
				<label for="last_name" class="col-sm-3 col-form-label">Nom :</label>
				<div class="col-sm-9">
					<input class="form-control" type="text" name="last_name" id="last_name" required autocomplete="family-name" value="${profile_user.lastName}">
				</div>
			</div>
			<div class="mb-4 row">
				<label for="first_name" class="col-sm-3 col-form-label">Prénom :</label>
				<div class="col-sm-9">
					<input class="form-control" type="text" name="first_name" id="first_name" required autocomplete="name" value="${profile_user.firstName}">
				</div>
			</div>
			<div class="mb-4 row">
				<label for="email" class="col-sm-3 col-form-label">Email :</label> 
				<div class="col-sm-9">
					<input class="form-control" type="email" name="email" id="email" required autocomplete="email" value="${profile_user.email}">
				</div> 
			</div>
			<div class="mb-4 row">
				<label for="current_password_profile" class="col-sm-3 col-form-label">Mot de Passe :</label>
				<div class="col-sm-9">
					<input class="form-control" type="password" name="current_password" id="current_password_profile" required autocomplete="current-password">
				</div>
			</div>

			<div class="row">
				<div class="offset-9 col-sm-3">
					<button class="btn btn-primary w-100" type="submit" formaction="${ profile }">Sauvegarder</button>
				</div>	
			</div>
			<input class="form-control" type="hidden" name="action" value="edit">
		</form>
	</div>	
	
	<h2 class="container text-center text-secondary my-3">Changer Mot de Passe</h2>
	<c:url var="change_password" value="/change-password"></c:url>
	<div class="container p-4 border border-2 rounded-4 col-sm-4">
		<form method="post">
			<div class="mb-4 row">
				<label for="new_password" class="col-sm-3 col-form-label">Nouveau :</label>
				<div class="col-sm-9">
					<input class="form-control" type="password" name="new_password" id="new_password" required autocomplete="new-password">
				</div>
			</div>
			<div class="mb-4 row">
				<label for="current_password" class="col-sm-3 col-form-label">Actuel :</label>
				<div class="col-sm-9">
					<input class="form-control" type="password" name="current_password" id="current_password" required autocomplete="current-password">
				</div>
			</div>
			<div class="row">
				<div class="offset-9 col-sm-3">
					<button class="btn btn-primary w-100" type="submit" formaction="${ profile }">Changer</button>
				</div>	
			</div>
			<input class="form-control" type="hidden" name="action" value="change">
		</form>
	</div>
	
	<h2 class="container text-center text-secondary my-3">Supprimer Compte</h2>
	<c:url var="delete_profile" value="/delete-profile"></c:url>
	<div class="container p-4 border border-2 rounded-4 col-sm-4">
		<form method="post">
			<div class="mb-4 row">
				<label for="current_password_delete" class="col-sm-3 col-form-label">Mot de Passe :</label>
				<div class="col-sm-9">
					<input class="form-control" type="password" name="current_password" id="current_password_delete" required autocomplete="current-password">
				</div>
			</div>
			<div class="row">
				<div class="offset-9 col-sm-3">
					<button class="btn btn-danger w-100" type="submit" formaction="${ profile }">Supprimer</button>
				</div>	
			</div>
			
			<input class="form-control" type="hidden" name="action" value="delete">
		</form>
	</div>
</body>
</html>