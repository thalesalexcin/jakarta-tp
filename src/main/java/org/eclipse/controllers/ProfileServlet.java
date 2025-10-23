package org.eclipse.controllers;

import java.io.IOException;

import org.eclipse.models.User;
import org.eclipse.repositories.UserDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserDao dao; 

	@Override
	public void init() throws ServletException {
		dao = new UserDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		var authUser = request.getSession().getAttribute("auth_user");
		if (authUser instanceof User user) {		
			user = dao.findById(user.getId());
			request.setAttribute("profile_user", user);
		}
		
		request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);			
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		var authUser = request.getSession().getAttribute("auth_user");
		if (authUser instanceof User user) {		
			user = dao.findById(user.getId());
			request.setAttribute("profile_user", user);
			
			String currentPassword = request.getParameter("current_password");
			var testUser = dao.findByEmailAndPassword(user.getEmail(), currentPassword);
			if (testUser == null) {
				request.setAttribute("error", "Mot de passe invalide.");
				request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
				return;
			}
			
			String action = request.getParameter("action");
			switch(action) {
			case "edit": {
				String firstName = request.getParameter("first_name");
				String lastName = request.getParameter("last_name");
				String email = request.getParameter("email");
				user.setEmail(email);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				boolean success = dao.update(user) != null;
				if (!success) {
					request.setAttribute("error", "Impossible de modifier les coordonées.");	
				} else {
					request.setAttribute("profile_user", user);
				}
			}
			break;
			case "change": {
				String newPassword = request.getParameter("new_password");
				boolean success = dao.updatePassword(user.getId(), newPassword);
				if (!success) {
					request.setAttribute("error", "Impossible de modifier le mot de passe.");	
				}
			}
				break;
			case "delete":
				boolean success = dao.remove(user.getId());
				if (success) {
					request.getSession().invalidate();
					response.sendRedirect(request.getContextPath() + "/home");
					return;
				} else {
					request.setAttribute("error", "Impossible de supprimer le compte.");	
				}
			}	
		}
		
		request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
	}
}
