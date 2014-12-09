<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Tests EL</title>
	</head>
	<body>
		<h1>Hello :)</h1>
		
		<p>
			6 * 7 = ${ 6 * 7}
		</p>
		
		<p>
			12 est supérieur à 8 ${ 12 gt 8 }
		</p>
		
		<jsp:useBean id="coyote" class="com.sdzee.beans.Coyote" />
		<!-- Initialisation de la propriété prenom -->
		<jsp:setProperty property="prenom" name="coyote" value="Wile E."/>
		<!-- Affichage de la propriété prenom -->
		<p>
			avec une balise jsp : <jsp:getProperty property="prenom" name="coyote"/>
		</p>
		<p>
			avec une balise EL : ${coyote.getPrenom()}
		</p>
		<p>
			Le prenom de coyote est il vide ? ${empty coyote.getPrenom() ? "oui" : "mais non !"}
		</p>
		
		<%-- les collections --%>
		
		<%-- List --%>
		<%
			java.util.List<String> legumes = new java.util.ArrayList<String>();
			legumes.add("poireau");
			legumes.add("haricot");
			legumes.add("carotte");
			legumes.add("patate");
			request.setAttribute("legumes", legumes);
		%>
		
		<p>
			Pour accéder à un élément :
			<ul>
				<li>${legumes.get(0)}</li>
				<li>${legumes[1]}</li>
				<li>${legumes['2']}</li>
				<li>${legumes["3"]}</li>
			</ul>
		</p>
		
		<%-- Map --%>
		<%
			java.util.Map<String, Integer> desserts = new java.util.HashMap<String, Integer>();
			desserts.put("cookies", 8);
			desserts.put("glaces", 3);
			desserts.put("muffins", 6);
			desserts.put("tartes aux pommes", 2);
			request.setAttribute("desserts", desserts);
		%>
		
		<p>
			Pour accéder à un élément :
			<ul>
				<li>${desserts.cookies}</li>
				<li>${desserts.get("glaces")}</li>
				<li>${desserts['muffins']}</li>
				<li>${desserts["tartes aux pommes"]}</li>
			</ul>
		</p>
		
		<%-- Les objets implicites --%>
		<%-- Paramètres --%>
		
		<p>
			paramètre nom : ${param.nom}
		</p>
		<p>
			paramètre avec plusieurs nom : ${paramValues.nom[0]}, ${paramValues.nom[1]}, ${paramValues.nom[2]}
		</p>
		
		
	</body>
</html>