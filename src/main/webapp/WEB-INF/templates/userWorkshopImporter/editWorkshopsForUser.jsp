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
				<h1>Korjaamot käyttäjälle<br>${user.firstName} ${user.lastName }</h1>
			</div>

			<br>
			<br>
			<sf:form modelAttribute="user" action="saveUserWorkshopList" method="post">
			
			<c:forEach items="${user.importer.workshops}" var="workshop">
				<c:set var="contains" value="false" />
					<c:forEach var="ws" items="${user.workshops}">
						<c:if test="${ws.id == workshop.id }" >
							<c:set var="contains" value="true" />
						</c:if>
					</c:forEach>
				<c:if test="${contains == 'true'}">
					<sf:checkbox path="chosenWorkshopIds" checked="checked" value="${workshop.id}" label="${workshop.name}"/>
				</c:if>
				<c:if test="${contains == 'false'}">
					<sf:checkbox path="chosenWorkshopIds" value="${workshop.id}" label="${workshop.name} ${workshop.city}"/>
				</c:if>
				
				<br>
			</c:forEach>
			
			

				<br><br>
		
				<button class="btn btn-large btn-primary" type="submit"><span class="glyphicon glyphicon-ok" style="text-decoration: none;"></span> Valitse</button>
				
			</sf:form>
		</div>
		<br>
		

<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />