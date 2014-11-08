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
				<h1>Korjaamot</h1>
			</div>
			<br><br>
			
			<table class="table table-striped">
				<tr>
					<th />
					<th>Nimi</th>
					<th>Osoite</th>
				</tr>						
			
				<c:forEach var="workshop" begin="${workshopListStart}" end="${workshopListEnd}" items="${workshops}">
						<tr>
							<td><a class="btn btn-default" style="text-decoration: none;" href="showWorkshop?id=${workshop.id}">
								Valitse</a></td>
							<td>${workshop.name}</td>
							<td>${workshop.streetAddress}</td>

						</tr>
				</c:forEach>
			</table>
				
		<c:if test="${pageCount > 1}">
 			<div style="text-align: center">  
				<ul class="pagination pagination-centered pagination-lg"> 

				
 						<c:if test="${currentPage > 1 }">
								<li><a href="/ProNaseva/showWorkshopList?page=${currentPage - 1}">&laquo;</a></li>
							</c:if>
						<c:if test="${currentPage == 1 }">
								<li class="disabled"><a href="#">&laquo;</a></li>
						</c:if>
 					<c:forEach begin="1" end="${pageCount}" varStatus="pageCounter">
	 						
	 					<c:if test="${currentPage == pageCounter.index}">
	 							<li class="active"><a href="#">${pageCounter.count} <span class="sr-only">(current)</span></a></li>
	 					</c:if>
	 					<c:if test="${currentPage != pageCounter.index}">
	 							<li><a href="/ProNaseva/showWorkshopList?page=${pageCounter.index}">${pageCounter.count} </a></li>
	 					</c:if>
 					</c:forEach>			
															
					<c:if test="${currentPage < pageCount }">
						<li><a href="/ProNaseva/showWorkshopList?page=${currentPage + 1}">&raquo;</a></li>
					</c:if>
					<c:if test="${currentPage == pageCount}">
						<li class="disabled"><a href="#">&raquo;</a></li>
					</c:if>
										
				</ul>  
			</div>   
		</c:if>	
		
		
		<a class="btn btn-primary" href="newWorkshop"><span class="glyphicon glyphicon-plus" style="text-decoration: none;"></span> Lisää korjaamo</a>
		
		</div>
		<br>
		
		 
	
<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />