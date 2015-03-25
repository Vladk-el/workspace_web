package com.vladkel.list.files;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListFiles extends HttpServlet {
	
	private static final String VIEW = "/WEB-INF/jsp/ListFiles.jsp";
	
	private static final String LOCATION = "location";
	private static final String PARENT_DIRECTORY = "parent_directory";
	private static final String FILES = "files";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// Get values
		String path = this.getServletConfig().getInitParameter("path");
		String askedResource = request.getPathInfo();
		String currentLocation = request.getRequestURI();
		String lastLocation = currentLocation.substring(0, currentLocation.substring(0, currentLocation.length() - 1).lastIndexOf("/") + 1);
		File here = new File(path, askedResource);
		
		// Controls
		if("/".equals(askedResource)){
			lastLocation = currentLocation;
		}
		System.out.println("askedResource : " + request.getPathInfo());
		System.out.println("currentLocation : " + currentLocation);
		System.out.println("lastLocation : " + lastLocation);
		System.out.println("here.location : " + here.getAbsolutePath());
				
		// Set parametters
		request.setAttribute(LOCATION, currentLocation);
		request.setAttribute(PARENT_DIRECTORY, lastLocation);

		if(here.exists() && here.isDirectory()){
			List<File> files = Arrays.asList(here.listFiles());
			request.setAttribute(FILES, files);
		}
				
		System.out.println();
		this.getServletContext().getRequestDispatcher( VIEW ).forward(request, response);
	}

}
