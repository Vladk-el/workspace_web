<%@page import="com.sdzee.beans.Coyote"%>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Test</title>
    </head>

    <body>
        <p>Ceci est une page générée depuis une JSP.</p>
        
        <!-- Récupération de l'attribut "auteur" -->
        <p>
        	L'auteur de ce document est : 
        	<%
        		String auteur = (String) request.getAttribute("auteur");
        		out.println(auteur);
        	%>	
       	</p>
       	
       	<!-- Récupération de l'objet "coyote" -->
       	<p>
       		Récupération du coyote : 
       		<%
       			Coyote coyote = (Coyote) request.getAttribute("coyote");
       			out.println(coyote.getPrenom());
       			out.println(coyote.getNom());
       		%>
       	</p>
       	
       	<%-- Ceci est un commentaire JSP, non visible dans la page HTML finale.  --%>
       	
       	<%-- Déclaration d'une String dans une balise de déclaration : déconseillé --%>
       	
       	<%! String chaine = "Salut les zéros.";  %>
       	
       	<p>
       		<% out.println(chaine); %>
       	</p>
       	
       	<%-- Balise d'expression --%>
       	
       	<p>
       		<% out.println("Bip bip !"); %> ou <%= "Bip bip !" %>
       	</p>
       	
       	<%-- Directive taglib
       		
       		<%@ taglib uri="maTagLib.tld" prefix="tagExemple" %>
       		
       	 --%>
       	 
       	 <%-- Directive page --%>
       	 
       	 <%@ page import="java.util.List, java.util.Date"  %>
       	
       	<%-- Directive include --%>
       	
       	<%@ include file="uneAutreJSP.jsp" %>
       	
       	<%-- Inclusion dynamique 
       	
       	 L'inclusion dynamique d'une page fonctionne par URL relative : 
		<jsp:include page="page.jsp" />
		
		 Son équivalent en code Java  est : 
		<% request.getRequestDispatcher( "page.jsp" ).include( request, response ); %>
		
		 Et il est impossible d'inclure une page externe comme ci-dessous : 
		<jsp:include page="http://www.siteduzero.com" />
       	
       	--%>
       	
       	<%-- Ne marche pas car les fichiers osnt compilés séparément ! 
	       	<%@ page import="java.util.ArrayList" %>
	       	<jsp:include page="test_inc.jsp" />
       	--%>
       	
       	<%-- L'action suivante récupère un bean de type Coyote et nommé "coyote" dans la portée requête s'il existe, ou en crée un sinon.
			<jsp:useBean id="coyote" class="com.sdzee.beans.Coyote" scope="request" />
			
			 Elle a le même effet que le code Java suivant : 
			<% 
			com.sdzee.beans.Coyote coyote = (com.sdzee.beans.Coyote) request.getAttribute( "coyote" ); 
			if ( coyote == null ){
			    coyote = new com.sdzee.beans.Coyote();
			    request.setAttribute( "coyote", coyote );
			}
			%>
		 --%>
		 
		 <jsp:useBean id="coyoteBean" class="com.sdzee.beans.Coyote">
		   <%-- Ici, vous pouvez placer ce que vous voulez : définir des propriétés, créer d'autres objets, etc. --%>
		   <jsp:setProperty name="coyoteBean" property="prenom" value="Eliott" />
		   <jsp:setProperty name="coyoteBean" property="nom" value="Laversin" />
		   <p>Nouveau bean !</p>
		</jsp:useBean>
		
		<%-- getProperty() --%>
		<%-- L'action suivante affiche le contenu de la propriété 'prenom' du bean 'coyote' : --%>
		<p>
			<jsp:getProperty name="coyoteBean" property="prenom" /> <===> <%= coyoteBean.getPrenom() %>
		</p>	
		<p>
			<jsp:getProperty name="coyoteBean" property="nom" /> <===> <%= coyoteBean.getNom() %>	
		</p>	
		
		<%-- L'action suivante associe directement la valeur récupérée depuis le paramètre de la requête nommé ici 'prenomCoyote' à la propriété 'prenom' : 
			<jsp:setProperty name="coyote" property="prenom" param="prenomCoyote"/>
			
			 Elle a le même effet que le code Java suivant :
			<% coyote.setPrenom( request.getParameter("prenomCoyote") ); %>	
		--%>
		
		<%-- L'action suivante associe automatiquement la valeur récupérée depuis chaque paramètre de la requête à la propriété de même nom :
		<jsp:setProperty name="coyote" property="*" />
		 
		 Elle a le même effet que le code Java suivant :
		<% coyote.setNom( request.getParameter("nom") ); %>
		<% coyote.setPrenom( request.getParameter("prenom") ); %>
		<% coyote.setGenius( Boolean.valueOf( request.getParameter("genius") ) ); %>
		 --%>
		 
		 <%-- forward --%>
		 <%-- Le forwarding vers une page de l'application fonctionne par URL relative : 
			<jsp:forward page="/page.jsp" />
			
			 Son équivalent en code Java  est : 
			<% request.getRequestDispatcher( "/page.jsp" ).forward( request, response ); %>
			
			 Et il est impossible de rediriger vers un site externe comme ci-dessous : 
			<jsp:forward page="http://www.siteduzero.com" />
		 --%>
		 
		
    </body>
</html>