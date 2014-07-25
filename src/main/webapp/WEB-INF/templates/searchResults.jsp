<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/templates/includes/header.jsp" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<body>
	<div id="wrap">
		<div class="container">
			<div class="page-header">
				<h1>Hakutulos</h1>
			</div>
			<br><br>
			
			<table class="table table-striped">
				<c:forEach var="report" items="${dbReports}">
						<tr>
							<td><a class="btn btn-primary" style="text-decoration: none;"href="searchReportSelect?id">
								Valitse</a></td>
							<td>[pvm]</td>
							<td>Maahantuoja: ${report.importer.name}<br>
								Korjaamo: ${report.workshop.name}
							</td>
						</tr>
						
				</c:forEach>
			</table>
			
		<br>
		<a class="btn btn-primary" href="/ProNaseva/" style="margin-left: 6px">Alkuun</a>
		</div>
		<br>
	
<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />