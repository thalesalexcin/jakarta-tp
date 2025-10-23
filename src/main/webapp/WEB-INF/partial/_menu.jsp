<nav class="container my-5">
	<ul id="menu">
		<c:url value="/listings" var="myListings"></c:url>
		<li>
			<a href="${myListings}">Mes annonces</a>
		</li>
		<c:url value="/saved" var="saved"></c:url>
		<li>
			<a href="${saved}">Mes favoris</a>
		</li>
		<c:url value="/profil" var="profil"></c:url>
		<li>
			<a href="${profil}">Mon profil</a>
		</li>
		<c:url value="/signout" var="logout"></c:url>
		<li>
			<a href="${logout}">Déconnexion</a>
		</li>
	</ul>
</nav>