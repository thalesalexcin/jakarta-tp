package org.eclipse.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.models.Listing;
import org.eclipse.models.User;
import org.eclipse.repositories.FavoriteDao;
import org.eclipse.repositories.ListingDao;
import org.eclipse.repositories.UserDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserDao userDao;
	private ListingDao listingDao;
	private FavoriteDao favoriteDao;
	
	@Override
	public void init() throws ServletException {
		userDao = new UserDao();
		listingDao = new ListingDao();
		favoriteDao = new FavoriteDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		var authUser = request.getSession().getAttribute("auth_user");
		if (authUser instanceof User user) {
			user = userDao.findById(user.getId());
			
			var otherListings = listingDao.findAllByNotOwnerId(user.getId());
			
			List<Boolean> listingFavorites = new ArrayList<Boolean>();
			for (Listing listing : otherListings) {
				boolean isFavorite = favoriteDao.isFavorite(user.getId(), listing.getId());
				listingFavorites.add(isFavorite);
			}
			
			List<User> listingOwners = new ArrayList<User>();
			for (Listing listing : otherListings) {
				User owner = userDao.findById(listing.getOwnerId());
				if (owner != null) {
					listingOwners.add(owner);					
				}
			}
			
			request.setAttribute("listings", otherListings);
			request.setAttribute("listings_favorites", listingFavorites);
			request.setAttribute("listings_owner", listingOwners);
			request.setAttribute("first_name", user.getFirstName());
			request.setAttribute("last_name", user.getLastName());
		}
		
		request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
	}
}
