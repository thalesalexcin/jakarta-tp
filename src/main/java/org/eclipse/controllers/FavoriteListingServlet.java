package org.eclipse.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.models.Favorite;
import org.eclipse.models.Listing;
import org.eclipse.models.User;
import org.eclipse.repositories.FavoriteDao;
import org.eclipse.repositories.ListingDao;
import org.eclipse.repositories.UserDao;

@WebServlet("/favorites")
public class FavoriteListingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private FavoriteDao favoriteDao;
	private UserDao userDao;
	private ListingDao listingDao;
       
	@Override
	public void init() throws ServletException {
		favoriteDao = new FavoriteDao();
		userDao = new UserDao();
		listingDao = new ListingDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		var authUser = request.getSession().getAttribute("auth_user");
		if (authUser instanceof User user) {
			user = userDao.findById(user.getId());
			var allFavorites = favoriteDao.findAllByOwnerId(user.getId());
			
			List<Listing> listings = new ArrayList<Listing>();
			for (Favorite favorite : allFavorites) {
				var listing = listingDao.findById(favorite.getListingId());
				if (listing != null) {
					listings.add(listing);					
				}
			}
			request.setAttribute("listings", listings);
		}
		
		request.getRequestDispatcher("/WEB-INF/my-favorites.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
