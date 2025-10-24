package org.eclipse.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.models.Favorite;
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
			
			List<User> listingOwners = new ArrayList<User>();
			for (Listing listing : listings) {
				User owner = userDao.findById(listing.getOwnerId());
				if (owner != null) {
					listingOwners.add(owner);					
				}
			}
			
			request.setAttribute("listings_owner", listingOwners);
			request.setAttribute("listings", listings);
		}
		
		request.getRequestDispatcher("/WEB-INF/my-favorites.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		String from = request.getParameter("from");
		String redirectTo = switch(from) {
			case "home" -> "/home";
			case "favorites" -> "/favorites";
			default -> "/home";
		};
		
		Listing listing = listingDao.findById(id);
		if (listing == null) {
			response.sendRedirect(request.getContextPath() + redirectTo);
			return;
		}
		
		var authUser = request.getSession().getAttribute("auth_user");
		if (authUser instanceof User user) {
			user = userDao.findById(user.getId());
			if (listing.getOwnerId() == user.getId()) { //can't add favorite to own listing
				response.sendRedirect(getServletContext() + redirectTo);
				return;
			}
			
			Favorite favorite = favoriteDao.findByUserAndListing(user.getId(), listing.getId());
			if (favorite != null) {
				favoriteDao.remove(favorite.getId());
			} else {
				favorite = new Favorite(0, listing.getId(), user.getId());
				favoriteDao.save(favorite);
			}
		}
		
		response.sendRedirect(request.getContextPath() + redirectTo);
	}

}
