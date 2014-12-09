package com.sdzee.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;

import com.sdzee.beans.Coyote;

public class ProblemeDeVue extends HttpServlet {

	private static final long serialVersionUID = 2629833397732147527L;

	// http://openclassrooms.com/courses/creez-votre-application-web-avec-java-ee/des-problemes-de-vue
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Création message
		String paramAuteur = request.getParameter("auteur");
		String message = "Transmission de la variable : OK ! ==> " + paramAuteur;
		
		// Création du bean
		Coyote bean = new Coyote();
		bean.setNom("Coyote");
		bean.setPrenom("Wile E.");
		
		// Création d'une liste de 4 éléments
		List<Integer> list = new ArrayList<Integer>();
		list.add(27);
		list.add(12);
		list.add(138);
		list.add(6);
		
		// Utilisation de JodaTime
		DateTime dt = new DateTime();
		Integer jourDuMois = dt.getDayOfMonth();
		
		// Stockage des éléments dans la request
		request.setAttribute("test", message);
		request.setAttribute("coyote", bean);
		request.setAttribute("liste", list);
		request.setAttribute("jour", jourDuMois);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/problemeDeVue.jsp").forward(request, response);
	}
}
