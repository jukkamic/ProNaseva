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
				<h1>Käyttäjätilit</h1>
			</div>
			<br><br>
			
			
			<table class="table table-striped">
				<tr>
					<th />
					<th>Nimi</th>
					<th>Käyttäjänimi</th>
					<th>Käyttöoikeudet</th>
				</tr>						
				<c:forEach var="user" items="${users}">
						<tr>
							<td><a class="btn btn-primary" style="text-decoration: none;" href="editUser?id=${user.id}">
								Valitse</a></td>
							<td>${user.lastName}, ${user.firstName}</td>
							<td>${user.userName}</td>
							<td>
								<c:choose>
									<c:when test="${user.role == 'ROLE_ADMIN'}">
									Admin
									</c:when>
									<c:when test="${user.role == 'ROLE_TESTER'}">
									Testaaja
									</c:when>
									<c:when test="${user.role == 'ROLE_CLIENT'}">
									Asiakas
									</c:when>
								</c:choose>
							</td>
						</tr>
				</c:forEach>
			</table>
			
		<br><br>
		
		<a class="btn btn-primary" href="newUser">Lisää käyttäjä</a>
		
		</div>
		<br>
		
		 
	
<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />