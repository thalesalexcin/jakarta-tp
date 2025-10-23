package org.eclipse.controllers;

import java.io.IOException;

import org.eclipse.models.User;
import org.eclipse.repositories.UserDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserDao dao; 

	@Override
	public void init() throws ServletException {
		dao = new UserDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		request.getRequestDispatcher("/WEB-INF/sign-up.jsp").forward(request, response);			
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		User user = new User(0, firstName, lastName, email, password);
		if (dao.save(user) == null) {
			request.setAttribute("error_user", user);
			request.setAttribute("error", "Une erreur s'est produite à la création de votre compte");
			request.getRequestDispatcher("/WEB-INF/sign-up.jsp").forward(request, response);
			return;
		}
		
		response.sendRedirect(request.getContextPath() + "/signin");
	}
}
