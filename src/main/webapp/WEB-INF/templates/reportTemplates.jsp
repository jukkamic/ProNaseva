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
				<h1>Reporttipohjat</h1>
			</div>

			<br>
			<br>

	<a href="/ProNaseva/admin/saveReportTemplate?name=Volvo" class="btn btn-large btn btn-primary"><span class="glyphicon glyphicon-save" style="text-decoration: none;"></span> Vie Volvo-raporttipohja <br>kantaan</a>

	<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />
