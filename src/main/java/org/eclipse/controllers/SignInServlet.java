package org.eclipse.controllers;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.eclipse.models.User;
import org.eclipse.repositories.UserDao;

@WebServlet("/signin")
public class SignInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDao dao;
	
	public void init(ServletConfig config) throws ServletException {
		dao = new UserDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("auth_user") == null) {
			request.getRequestDispatcher("/WEB-INF/sign-in.jsp").forward(request, response);			
		} else {
			response.sendRedirect(request.getContextPath() + "/home");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		User user = dao.findByEmailAndPassword(email, password);
		
		if (user != null) {
			request.getSession().setAttribute("auth_user", user);
			response.sendRedirect(request.getContextPath() + "/home");	
		} else {
			request.setAttribute("error", "Identifiants invalides.");
			request.getRequestDispatcher("/WEB-INF/sign-in.jsp").forward(request, response);	
		}
	}

}
