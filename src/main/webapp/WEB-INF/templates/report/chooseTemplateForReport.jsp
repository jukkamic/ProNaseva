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
				<h1>Valitse raporttipohja</h1>
			</div>

			<br>
			<br>

			<form action="/ProNaseva/selectReportTemplate" method="post">

				<label for="selectedReportTemplate">Raporttipohja: </label>
				<br>
				<select style="width: auto; max-width: 100%" id="selectedReportTemplate" name="selectedReportTemplate"
					class="form-control">
					<c:forEach var="template" items="${importer.reportTemplates}">
					
						<c:if test="${template['class'] == 'class fi.testcenter.domain.reportTemplate.WorkshopVisitReportTemplate'}">
							<option value="${template.id}">Korjaamotestiraportti</option>
						</c:if>
						<c:if test="${template['class'] == 'class fi.testcenter.domain.reportTemplate.PhoneCallTestReportTemplate'}">
							<option value="${template.id}">Puhelutestiraportti</option>
						</c:if>
						
					</c:forEach>
				</select>

				<br>
				<br>
				<button class="btn btn-large btn-primary" type="submit"><span class="glyphicon glyphicon-arrow-right" style="text-decoration: none;"></span> Seuraava</button>
			</form>
			
		</div>

		<br>

	<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />
