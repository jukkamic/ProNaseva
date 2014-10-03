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
				<h1>Maahantuojat</h1>
			</div>
			<br><br>
			
			<table class="table table-striped">
				<tr>
					<th />
					<th>Nimi</th>
					<th>Osoite</th>
				</tr>						
				<c:forEach var="importer" items="${importers}">
						<tr>
							<td><a class="btn btn-primary" style="text-decoration: none;" href="selectImporter?id=${importer.id}">
								Valitse</a></td>
							<td>${importer.name}</td>
							<td>${importer.streetAddress}</td>

						</tr>
				</c:forEach>
			</table>
		<br><br>
		
		<a class="btn btn-primary" href="newImporter">Lisää maahantuoja</a>
		
		</div>
		<br>
		
		 
	
<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />