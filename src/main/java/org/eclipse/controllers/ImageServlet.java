package org.eclipse.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.models.Listing;
import org.eclipse.models.User;
import org.eclipse.repositories.ListingDao;
import org.eclipse.repositories.UserDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/image")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024,  // 1 MB before writing to disk
	    maxFileSize = 1024 * 1024 * 10,   // 10 MB max file size
	    maxRequestSize = 1024 * 1024 * 20 // 20 MB total request size
	)
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	private ListingDao listingDao;

	@Override
	public void init() throws ServletException {
		userDao = new UserDao();
		listingDao = new ListingDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imageName = request.getParameter("id");
        if (imageName == null || imageName.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        String imageRealPath = getServletContext().getRealPath("/WEB-INF/images/" + imageName + ".jpg");
        File imageFile = new File(imageRealPath);
        if (!imageFile.exists()) {
        	String noPhotoPath = getServletContext().getRealPath("/WEB-INF/images/no_photo.jpg");
        	imageFile = new File(noPhotoPath);
        }
        
        if (!imageFile.exists()) {
        	response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        response.setContentType(getServletContext().getMimeType(imageFile.getName()));
        response.setContentLengthLong(imageFile.length());

        try (FileInputStream fis = new FileInputStream(imageFile)) {
        	try (OutputStream os = response.getOutputStream()) {
        		fis.transferTo(os);        		
        	}
        }
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rawId = request.getParameter("id");
		int id = Integer.parseInt(rawId);
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
			
			Part filePart = request.getPart("image");
			String fileName = String.format("%d.jpg",listing.getId());
			String uploadDir = getServletContext().getRealPath("/WEB-INF/images/");
		    File uploadDirFile = new File(uploadDir);
		    if (!uploadDirFile.exists()) 
		    	uploadDirFile.mkdirs();
		    
		    filePart.write(uploadDir + "/" + fileName);
		    response.sendRedirect(request.getContextPath() + "/listings");
		}
	}
}
