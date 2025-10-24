<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modifier Annonce</title>
<jsp:include page="partial/_links.jsp"></jsp:include>
</head>
<body class="bg-light mb-5">
	<jsp:include page="partial/_menu.jsp"></jsp:include>
	<h1 class="container text-center text-primary my-5">Modifier Annonce</h1>
	
	<c:if test="${!empty error}">
		<div class="alert alert-danger alert-dismissible container col-sm-4 fade show" role="alert">
			<c:out value="${error}"></c:out>
			 <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
	</c:if>
	
	<c:url var="save_listing" value="/listing"></c:url>
	<div class="container p-4 border border-2 rounded-4 col-sm-4">
		<form method="post">
			<div class="mb-4 row">
				<label for="title" class="col-sm-3 col-form-label">Titre :</label>
				<div class="col-sm-9">
					<input class="form-control" type="text" name="title" id="title" required value="${listing.title}">
				</div>
			</div>
			<div class="mb-4 row">
				<label for="description" class="col-sm-3 col-form-label">Description :</label>
				<div class="col-sm-9">
					<input class="form-control" type="text" name="description" id="description" value="${listing.description}">
				</div>
			</div>
			<div class="mb-4 row">
				<label for="price" class="col-sm-3 col-form-label">Prix :</label> 
				<div class="col-sm-9">
					<input class="form-control" type="number" step="any" name="price" id="price" required value="${listing.price}">
				</div> 
			</div>
			<div class="mb-4 row">
				<label for="city" class="col-sm-3 col-form-label">Ville :</label>
				<div class="col-sm-9">
					<input class="form-control" type="text" name="city" id="city" value="${listing.city}">
				</div>
			</div>

			<div class="row">
				<div class="offset-9 col-sm-3">
					<button class="btn btn-primary w-100" type="submit" formaction="${ save_listing }">Sauvegarder</button>
				</div>	
			</div>
			<input class="form-control" type="hidden" name="id" value="${listing_id}">
		</form>
	</div>	
</body>
</html>