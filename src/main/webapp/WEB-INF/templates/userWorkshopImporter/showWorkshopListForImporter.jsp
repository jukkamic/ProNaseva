<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/templates/includes/header.jsp" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<body>
	<div id="wrap">
		<div class="container">
			<div class="page-header">
				<jsp:include page="/WEB-INF/templates/includes/pageHeaderRow.jsp" />
				<h1>Korjaamot maahantuojalle<br>${importer.name}</h1>
			</div>

			<br>
			<br>
			<table class="table table-striped">
				<tr>
					
					<th>Nimi</th>
					<th>Paikkakunta</th>
				</tr>			
				
			<c:forEach items="${importer.workshops}" var="workshop">
				<tr>
					<td>${workshop.name}</td>
					<td>${workshop.city}</td>
				</tr>
			</c:forEach>
			</table>
				<br><br>
		
				<a class="btn btn-large btn-primary" href="showImporter?id=${importer.id}"><span class="glyphicon glyphicon-arrow-left" style="text-decoration: none;"></span> Palaa</a>
				
			
		</div>
		<br>
		

<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />