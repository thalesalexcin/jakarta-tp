package org.eclipse.controllers;

import java.io.IOException;

import org.eclipse.models.User;
import org.eclipse.repositories.UserDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
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
			request.setAttribute("first_name", user.getFirstName());
			request.setAttribute("last_name", user.getLastName());
		}
		
		request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
	}
}
