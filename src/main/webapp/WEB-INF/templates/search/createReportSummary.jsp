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
				<h1>Raporttiyhteenvedot</h1>
			</div>

			<br>
			<br>

			<sf:form action="createReportSummary">

				<button class="btn btn-large btn-primary" type="submit"><span class="glyphicon glyphicon-arrow-right" style="text-decoration: none;"></span> Lataa korjaamoraporttien yhteenveto</button>
			</sf:form>
			
		</div>

		<br>


<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />