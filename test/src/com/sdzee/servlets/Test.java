package com.sdzee.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sdzee.beans.Coyote;

public class Test extends HttpServlet {

	private static final long serialVersionUID = 1551004443120893954L;

	// TODO : http://openclassrooms.com/courses/creez-votre-application-web-avec-java-ee/la-technologie-jsp-1-2
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Récupérer des paramètres et setter des properties
		String auteur = request.getParameter("auteur");
		System.out.println("Auteur : " + auteur);
		request.setAttribute("auteur", auteur);
		
		// Créer un bean et le passer à la servlet
		Coyote coyote = new Coyote();
		coyote.setNom("Coyote");
		coyote.setPrenom("Wile E.");
		request.setAttribute("coyote", coyote);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/test.jsp").forward(request, response);
	}

}
