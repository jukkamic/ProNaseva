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
				<security:authentication property="principal.username" var="loginId" scope="request" />
				<div style="float: right; font-size: 1.3em">
				${loginId}
				&nbsp;
				<a class="btn btn-default" style="text-decoration: none" href="j_spring_security_logout">Kirjaudu ulos</a>
				 
				</div>
				<br><br><br>
				<h1>Hakutulos</h1>
			</div>
			<br><br>
			
			<table class="table table-striped">
				<c:forEach var="report" items="${reportSearchList}">
						<tr>
							<td><a class="btn btn-primary" style="text-decoration: none;"href="searchReportSelect?id=${report[0]}">
								Valitse</a></td>
							<td>[pvm]</td>
							<td>Maahantuoja: ${report[1].name}<br>
								Korjaamo: ${report[2].name}
							</td>
						</tr>
						
				</c:forEach>
			</table>
			
		<br>
		<a class="btn btn-primary" href="/ProNaseva/" style="margin-left: 6px">Alkuun</a>
		</div>
		<br>
	
<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />