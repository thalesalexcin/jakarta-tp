<c:url value="/listings" var="myListings"></c:url>
<c:url value="/favorites" var="favorites"></c:url>
<c:url value="/profile" var="profile"></c:url>
<c:url value="/signout" var="logout"></c:url>
<c:url value="/home" var="home"></c:url>
<nav class="navbar navbar-expand-lg bg-primary-subtle text-primary-emphasis">
  <div class="container-fluid">
    <a class="navbar-brand" href="${home}">Immo TeP</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
		<li class="nav-item">
			<a class="nav-link" href="${myListings}">Mes annonces</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" href="${favorites}">Mes favoris</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" href="${profile}">Mon profil</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" href="${logout}">Déconnexion</a>
		</li>
	</ul>
    </div>
  </div>
</nav>
