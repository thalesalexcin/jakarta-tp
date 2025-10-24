<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mes Annonces</title>
<jsp:include page="partial/_links.jsp"></jsp:include>
</head>
<body class="bg-light mb-5">
	<jsp:include page="partial/_menu.jsp"></jsp:include>
	<h1 class="container text-center text-primary my-5">Mes Annonces</h1>

	<c:if test="${!empty error}">
		<div
			class="alert alert-danger alert-dismissible container col-sm-4 fade show" role="alert">
			<c:out value="${error}"></c:out>
			<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
	</c:if>

	<c:url var="add_listing" value="/listing">
		<c:param name="id" value="0"></c:param>
	</c:url>
	<div class="container d-flex justify-content-end mr-4 mb-2">
		<a href="${add_listing}" class="btn btn-sm btn-primary">Nouvelle Annonce</a>
	</div>
	<div class="container table-responsive">
		<table class="table table-striped table-bordered table-dark">
			<thead>
				<tr>
					<th scope="col">Titre</th>
					<th scope="col">Description</th>
					<th scope="col">Prix</th>
					<th scope="col">Ville</th>
					<th scope="col">Options</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${listings}" var="listing">
					<tr>
						<th scope="row">${ listing.title }</th>
						<td>${ listing.description }</td>
						<td>${ listing.price }</td>
						<td>${ listing.city }</td>
						<td>
							<c:url var="edit_listing" value="/listing">
								<c:param name="id" value="${listing.id}"></c:param>
							</c:url>
							<c:url var="delete_listing" value="/listings"></c:url>
							<form action="${delete_listing}" method="post">
								<a href="${edit_listing}" class="btn col-sm-5 btn-sm btn-primary">Modifier</a>
								<button type="submit" class="btn col-sm-5 btn-sm btn-danger">Supprimer</button>
								<input type="hidden" name="id" value="${listing.id}">
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>