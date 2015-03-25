<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>
			Index of - <c:out value="${location}" default="/"/>
		</title>
		
		<c:set var="name" value="                                     " />
		<c:set var="last_modified" value="               " />
		<c:set var="size" value="    " />
		<c:set var="description" value="   " />
		
	</head>
	<body>
	
		<h1>Index of <c:out value="${location}" default="/"/></h1>
		
		<pre><img src="<c:url value='/data/img/blank.gif'/>" alt="Icon "/><a href="?C=N;O=D">Name</a><c:out value="${name}"/><a href="?C=M;O=A">Last modified</a><c:out value="${last_modified}"/><a href="?C=S;O=A">Size</a><c:out value="${size}"/><a href="?C=D;O=A">Description</a><hr><img src="<c:url value='/data/img/back.gif'/>" alt="[DIR]"/><a href="<c:out value='${parent_directory}' default='/'/>">Parent Directory</a>
<c:forEach items="${files}" var="file"><c:if test="${!file.isDirectory()}"><img src="<c:url value='/data/img/image.gif'/>" alt="[IMG]"/> ${file.getName()}   ${file.lastModified()}   ${file.length()}</c:if><c:if test="${file.isDirectory()}"><img src="<c:url value='/data/img/folder.gif'/>" alt="[DIR]"/> <a href="${location}${file.getName()}/">${file.getName()}</a>   ${file.lastModified()}   ${file.length()}</c:if>
</c:forEach>
<hr></pre>
				 
	</body>
</html>


<%-- <img src="<c:url value='/data/img/image.gif'/>" alt="[IMG]"/> <a href="1409517124741.jpg">1409517124741.jpg</a>       04-Sep-2014 23:23  101K 
<img src="<c:url value='/data/img/image.gif'/>" alt="[IMG]"/> <a href="1409517124741.jpg">1409517124741.jpg</a>       04-Sep-2014 23:23  101K 
<img src="<c:url value='/data/img/folder.gif'/>" alt="[DIR]"/> <a href="1409517124741.jpg">1409517124741.jpg</a>       04-Sep-2014 23:23  101K 
<img src="<c:url value='/data/img/image.gif'/>" alt="[IMG]"/> <a href="1409517124741.jpg">1409517124741.jpg</a>       04-Sep-2014 23:23  101K 
		<hr></pre>--%>