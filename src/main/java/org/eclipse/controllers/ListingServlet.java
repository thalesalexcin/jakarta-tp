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

@WebServlet("/listing")
public class ListingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserDao userDao;
	private ListingDao listingDao;
	
	@Override
	public void init() throws ServletException {
		userDao = new UserDao();
		listingDao = new ListingDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		if (id != 0) {
			Listing listing = listingDao.findById(id);
			if (listing == null) {
				response.sendRedirect(request.getContextPath() + "/home");
				return;
			}
			
			var authUser = request.getSession().getAttribute("auth_user");
			if (authUser instanceof User user) {
				user = userDao.findById(user.getId());
				if (listing.getOwnerId() != user.getId()) {
					response.sendRedirect(request.getContextPath() + "/home");
					return;
				}
			}
			
			//check if user has right to edit
			request.setAttribute("listing", listing);
		}
		request.setAttribute("listing_id", id);
		request.getRequestDispatcher("/WEB-INF/edit-listing.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		Double price = Double.parseDouble(request.getParameter("price"));
		String description = request.getParameter("description");
		String city = request.getParameter("city");
		
		var authUser = request.getSession().getAttribute("auth_user");
		if (authUser instanceof User user) {
			user = userDao.findById(user.getId());
			if (id == 0) {
				Listing listing = new Listing(0, title, description, price, city, user.getId());
				boolean success = listingDao.save(listing) != null;
				if(success) {
					response.sendRedirect(request.getContextPath() + "/listings");
					return;
				} else {
					request.setAttribute("error", "Impossible de rajouter une annonce");
					request.setAttribute("listing", listing);
					request.getRequestDispatcher("/WEB-INF/edit-listing.jsp").forward(request, response);
				}
			} else {
				Listing listing = listingDao.findById(id);
				if (listing == null) {
					response.sendRedirect(request.getContextPath() + "/home");
					return;
				}
				
				if (listing.getOwnerId() != user.getId()) {
					response.sendRedirect(request.getContextPath() + "/home");
					return;
				}
				
				request.setAttribute("listing", listing);
				listing.setTitle(title);
				listing.setDescription(description);
				listing.setPrice(price);
				listing.setCity(city);
				boolean success = listingDao.update(listing) != null;
				if(success) {
					response.sendRedirect(request.getContextPath() + "/listings");
					return;
				}
				request.setAttribute("listing", listing);
			}
		}
		
		request.getRequestDispatcher("/WEB-INF/edit-listing.jsp").forward(request, response);
	}
}
