<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/templates/includes/header.jsp" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<body>
	<div id="wrap">
		<div class="container">
			<div class="page-header">
				<jsp:include page="/WEB-INF/templates/includes/pageHeaderRow.jsp" />
				<h1>Raportit</h1>
			</div>
			<br><br>
			
			
						
			<c:if test="${not empty awaitApproval}">
			<h3>Vahvistusta odottavat raportit</h3>
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
				<c:forEach begin="0" end="4" var="report" items="${awaitApproval}">
						<tr>
							<td><a class="btn btn-default" style="text-decoration: none;" href="searchReportSelect?id=${report.id}">
								Näytä</a>
							</td>
							<td>
								${report.testDate}
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
		
		<c:if test="${fn:length(awaitApproval) > 4}">
			<a class="btn btn-primary" href="/ProNaseva/showAllAwaitApproval"> Hae kaikki</a>
			<br><br>
		</c:if>
				
			
			
		<br>
		</c:if>
			
		<security:authentication property="authorities" var="loginRole" scope="request" />
		
		<c:if test="${empty awaitApproval and loginRole == '[ROLE_ADMIN]'}">
			<div class="alert alert-info">
				<h4>Ei vahvistettavana olevia raportteja</h4>
			</div>
			<br>
		</c:if>
		
		<c:if test="${empty reportSearchList}">
			<div class="alert alert-info">
				<h4>Ei omia raportteja</h4>
			</div>
		</c:if>
		<c:if test="${not empty reportSearchList}">	
			<h3>Omat raportit</h3>
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
						Raporttityyppi
					</th>
					<th>
						Tila
					</th>
				</tr>
				<c:forEach var="report" begin="0" end="4" items="${reportSearchList}">
						<tr>
							<td><a class="btn btn-default" style="text-decoration: none;" href="searchReportSelect?id=${report.id}">
								Näytä</a>
							</td>
							<td>
								${report.testDate}
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
								<c:if test="${report.reportClass == 'PhoneCallTestReport'}">
									<p>Puhelinkysely</p>
								</c:if>
								<c:if test="${report.reportClass == 'WorkshopVisitReport'}">
									<p>Korjaamotesti</p>
								</c:if>
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
			
		<c:if test="${fn:length(reportSearchList) > 5}">
			<a class="btn btn-primary" href="/ProNaseva/showAllUserOwnReports?showAllUserReports=true"> Hae kaikki</a>
			<br><br>
		</c:if>
		
		</c:if>
		
	
		</div>
		<br>
	
<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />