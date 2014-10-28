
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
				<h1>Raporttien hakutulos</h1>
			</div>
			<br><br>


			<br>
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
					<th>
						Tila
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
							<td>
								<c:choose>
				
									<c:when test="${report.reportStatus == 'DRAFT'}">
										<span class="label label-warning">Luonnos</span>
									</c:when>
									<c:when test="${report.reportStatus == 'AWAIT_APPROVAL'}">
										<span class="label label-info">Vahvistettavana</span>
									</c:when>
									<c:when test="${report.reportStatus == 'APPROVED'}">
										<span class="label label-success">Valmis</span>
									</c:when>
								</c:choose>
						</tr>
						
				</c:forEach>
			</table>
		<br>
		
		<a class="btn btn-primary remove" href="/ProNaseva/"><span class="glyphicon glyphicon-home" style="text-decoration: none;"></span> Alkuun</a>
		

		</div>
		<br>
	
<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />