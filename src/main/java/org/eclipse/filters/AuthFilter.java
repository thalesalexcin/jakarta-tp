package org.eclipse.filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class AuthFilter extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		var path = req.getServletPath();
		var authUser = req.getSession().getAttribute("auth_user");
		if (!path.contains("sign") && authUser == null) {
			resp.sendRedirect(req.getContextPath() + "/signin");	
		} else if (path.contains("sign") && !path.contains("signout") && authUser != null) {
			resp.sendRedirect(req.getContextPath() + "/home");	
		} else {
			chain.doFilter(request, response);			
		}
	}
}
