package com.sdzee.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sdzee.beans.Coyote;

public class TestJSP extends HttpServlet{

	// http://openclassrooms.com/courses/creez-votre-application-web-avec-java-ee/la-technologie-jsp-2-2
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/test_jsp.jsp").forward(request, response);
	}
}
