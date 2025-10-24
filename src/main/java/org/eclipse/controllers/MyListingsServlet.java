package org.eclipse.controllers;

import java.io.IOException;

import org.eclipse.models.Listing;
import org.eclipse.models.User;
import org.eclipse.repositories.ListingDao;
import org.eclipse.repositories.UserDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/listings")
public class MyListingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserDao userDao;
	private ListingDao listingDao;
	
	@Override
	public void init() throws ServletException {
		userDao = new UserDao();
		listingDao = new ListingDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO add filter for when there is a session but user no longer exists
		var authUser = request.getSession().getAttribute("auth_user");
		if (authUser instanceof User user) {
			user = userDao.findById(user.getId());
			var myListings = listingDao.findAllByOwnerId(user.getId());
			request.setAttribute("listings", myListings);
		}
		
		request.getRequestDispatcher("/WEB-INF/my-listings.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Listing listing = listingDao.findById(id);
		if (listing == null) {
			response.sendRedirect(request.getContextPath() + "/listings");
			return;
		}
		var authUser = request.getSession().getAttribute("auth_user");
		if (authUser instanceof User user) {
			user = userDao.findById(user.getId());
			if(listing.getOwnerId() != user.getId()) {
				response.sendRedirect(request.getContextPath() + "/listings");
				return;
			}
			
			boolean success = listingDao.remove(listing.getId());
			if(success) {
				response.sendRedirect(request.getContextPath() + "/listings");
				return;
			} else {
				request.setAttribute("error", "Impossible de supprimer l'annonce");
				request.getRequestDispatcher("/WEB-INF/my-listings.jsp").forward(request, response);
			}
		}
	}
}
