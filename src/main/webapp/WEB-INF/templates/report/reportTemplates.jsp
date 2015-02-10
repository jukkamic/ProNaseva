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

	<a href="/ProNaseva/admin/saveReportTemplate?name=Audi puhelinkyselyraportti" class="btn btn-large btn btn-primary"><span class="glyphicon glyphicon-save" style="text-decoration: none;"></span> Vie Audi puhelutestiraportti <br>kantaan</a>
	<br><br>
	<a href="/ProNaseva/admin/saveReportTemplate?name=Volvo Auto raporttipohja" class="btn btn-large btn btn-primary"><span class="glyphicon glyphicon-save" style="text-decoration: none;"></span> Vie Volvo-raporttipohja <br>kantaan</a>
	<br><br>
	<a href="/ProNaseva/admin/saveReportTemplate?name=Autoasi raporttipohja" class="btn btn-large btn btn-primary"><span class="glyphicon glyphicon-save" style="text-decoration: none;"></span> Vie Autoasi-raporttipohja <br>kantaan</a>
	<br><br>
	<a href="/ProNaseva/admin/saveReportTemplate?name=Autonomi korjaamoraporttipohja" class="btn btn-large btn btn-primary"><span class="glyphicon glyphicon-save" style="text-decoration: none;"></span> Vie Autonomi-korjaamotestipohja <br>kantaan</a>
	<br><br>
	<a href="/ProNaseva/admin/saveReportTemplate?name=Autokeskus korjaamotesti" class="btn btn-large btn btn-primary"><span class="glyphicon glyphicon-save" style="text-decoration: none;"></span> Vie Autokeskus-korjaamotestipohja<br>kantaan</a>
	<br><br>
	<c:if test="${not empty unusedTemplates}">	
		<br>
		<h3>Käyttämättömät raporttipohjat:</h3>
		<br>
			<table class="table table-striped">
				<tr>
					<th></th>
					
					<th>
						Raporttipohja
					</th>
				</tr>
				
				<c:forEach var="reportTemplate" items="${unusedTemplates}">
						<tr>
							<td><a class="btn btn-default" style="text-decoration: none;" href="/ProNaseva/admin/deleteReportTemplate?id=${reportTemplate.id}">
								Poista</a>
							</td>
							<td>
								${reportTemplate.templateName}
							</td>
						</tr>
				</c:forEach>
			</table>
		</c:if>
			



</div>
	<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />
