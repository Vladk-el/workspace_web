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
			avec une balise EL : ${coyote.prenom}
		</p>
		
	</body>
</html>