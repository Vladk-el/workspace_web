<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Intro JSP</title>
</head>
<body>
	<div id="affichage">
		<h1>Affichage</h1>
		<p>
			Affichage simple :
			<c:out value="Hello introJSP" />
		</p>

		<p>Affichage par défaut :
		<p>
			<c:out value="${bean}" default="test" />
		</p>
		ou bien
		<p>
			<c:out value="${bean}">
						test
					</c:out>
		</p>
		</p>
	</div>

	<div id="variables">
		<h1>Variables</h1>
		<p>
			Set :
			<c:set var="message" value="Hey mother fucker" scope="request" />
		</p>
		<p>
			Get :
			<c:out value="${requestScope.message}">
						Default value
					  </c:out>
		</p>
		<p>
			Modification :
			<c:set var="message" value="Hey father fucker" />
		</p>
		<p>
			==>
			<c:out value="${requestScope.message}" />
		</p>
	</div>

	<div id="conditions">
		<h1>Conditions</h1>
		<p>
			if :
			<c:if test="${ 12 > 7}" var="results" scope="session">
					Ce test est vrai
			</c:if>
		</p>

		<p>
			choose :
			<c:choose>
				<c:when test="${results}">
					Le test précédent est vrai
				</c:when>
				<c:otherwise>Le test précédent est faux.</c:otherwise>
			</c:choose>
		</p>
	</div>

	<div id="boucles">
		<h1>Boucles</h1>
		<p>
			<table>
				<c:forEach var="i" begin="0" end="7" step="1">
					<tr>
						<td><c:out value="${i}" /></td>
						<td><c:out value="${i * i * i}" /></td>
					</tr>
				</c:forEach>
			</table>
		</p>
	</div>
</body>
</html>