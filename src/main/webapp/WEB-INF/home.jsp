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
	
	<h3 class="container text-center my-2">
		<span class="text-tertiary">Les dernières annonces</span>
	</h3>
	
	<div class="container table-responsive">
		<table class="table table-striped table-bordered table-dark">
			<thead>
				<tr>
					<th scope="col">Titre</th>
					<th scope="col">Description</th>
					<th scope="col">Prix</th>
					<th scope="col">Ville</th>
					<th scope="col">Annonceur</th>
					<th scope="col">Options</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${listings}" var="listing" varStatus="listingStatus">
					<tr>
						<th scope="row">${ listing.title }</th>
						<td>${ listing.description }</td>
						<td>${ listing.price }</td>
						<td>${ listing.city }</td>
						<td>${ listings_owner[listingStatus.index].lastName } ${ listings_owner[listingStatus.index].firstName }</td>
						<td>
							<c:url var="toggle_favorite" value="/favorites"></c:url>
							<form action="${toggle_favorite}" method="post">
								<c:choose>
									<c:when test="${listings_favorites[listingStatus.index]}">
										<button type="submit" class="btn col-sm-12 btn-sm btn-danger">Enlever des favoris</button>
									</c:when>
									<c:otherwise>
										<button type="submit" class="btn col-sm-12 btn-sm btn-primary">Ajouter des favoris</button>
									</c:otherwise>
								</c:choose>
								<input type="hidden" name="from" value="home">
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