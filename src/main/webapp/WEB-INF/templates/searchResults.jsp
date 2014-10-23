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
				<h1>Raportit</h1>
			</div>
			<br><br>
			
			<table class="table table-striped">
			
				<tr>
					<th></th>
					<th>
						Pvm
					</th>
					<th>
						Maahantuoja
					</th>
					<th>
						Korjaamo
					</th>
					<th>
						Tarkastaja
					</th>
				</tr>
				<c:forEach var="report" items="${reportSearchList}">
						<tr>
							<td><a class="btn btn-default" style="text-decoration: none;" href="searchReportSelect?id=${report.id}">
								Näytä</a>
							</td>
							<td>
								${report.reportDate}
							</td>
							<td>${report.importer.name}
							</td>
							<td>
								${report.workshop.name}
							</td>
							<td>
								${report.user.lastName}, ${report.user.firstName}								
							</td>
						</tr>
						
				</c:forEach>
			</table>
			
		<br>

		</div>
		<br>
	
<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />