
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
		<c:if test="${empty reportSearchList}">
			<div class="alert alert-info">
				<h4>Ei hakutuloksia</h4>
			</div>
			<br>
		</c:if>			
		<c:if test="${not empty reportSearchList}">	
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
				
				<c:forEach var="report" begin="${reportListStart}" end="${reportListEnd}" items="${reportSearchList}">
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
									<p>Puhelutestiraportti</p>
								</c:if>
								<c:if test="${report.reportClass == 'WorkshopVisitReport'}">
									<p>Korjaamotestiraportti</p>
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
			
			
			<c:if test="${pageCount > 1}">
 			<div style="text-align: center">  
				<ul class="pagination pagination-centered pagination-lg"> 

				
 						<c:if test="${currentPage > 1 }">
								<li><a href="/ProNaseva/showSearchResult?page=${currentPage - 1}">&laquo;</a></li>
							</c:if>
						<c:if test="${currentPage == 1 }">
								<li class="disabled"><a href="#">&laquo;</a></li>
						</c:if>
 					<c:forEach begin="1" end="${pageCount}" varStatus="pageCounter">
	 						
	 					<c:if test="${currentPage == pageCounter.index}">
	 							<li class="active"><a href="#">${pageCounter.count} <span class="sr-only">(current)</span></a></li>
	 					</c:if>
	 					<c:if test="${currentPage != pageCounter.index}">
	 							<li><a href="/ProNaseva/showSearchResult?page=${pageCounter.index}">${pageCounter.count} </a></li>
	 					</c:if>
 					</c:forEach>			
															
					<c:if test="${currentPage < pageCount }">
						<li><a href="/ProNaseva/showSearchResult?page=${currentPage + 1}">&raquo;</a></li>
					</c:if>
					<c:if test="${currentPage == pageCount}">
						<li class="disabled"><a href="#">&raquo;</a></li>
					</c:if>
										
				</ul>  
			</div>   
		</c:if>	
	</c:if>

		<br>
		<a class="btn btn-large btn-primary" href="/ProNaseva/modifySearch"><span class="glyphicon glyphicon-search" style="text-decoration: none;"></span> Raporttien hakuun</a>
		<a class="btn btn-large btn-primary" href="/ProNaseva/userOwnReports"><span class="glyphicon glyphicon-list" style="text-decoration: none;"></span> Omat raportit</a>		
		
</div>
	
<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />