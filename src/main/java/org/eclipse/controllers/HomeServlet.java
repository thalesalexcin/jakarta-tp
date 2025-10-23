package org.eclipse.controllers;

import java.io.IOException;

import org.eclipse.models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		var authUser = request.getSession().getAttribute("auth_user");
		if (authUser instanceof User user) {
			request.setAttribute("first_name", user.getFirstName());
			request.setAttribute("last_name", user.getLastName());
		}
		
		//TODO add other listings here
		request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
	}
}
