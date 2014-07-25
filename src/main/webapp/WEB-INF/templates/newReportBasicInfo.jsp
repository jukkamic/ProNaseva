<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/templates/includes/header.jsp" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<body>
	<div id="wrap">
		<div class="container">
			<div class="page-header">
				<h1>Alkutiedot</h1>
			</div>

			<br>
			<br>

			<sf:form modelAttribute="reportBasicInfo" method="post">

				<label for="importerSelect">Maahantuoja: </label>
				<br>
				<sf:select style="width: auto; max-width: 100%" id="importerSelect" path="importer"
					class="form-control">
					<c:forEach var="importer" items="${importers}">
						<option>${importer}</option>
					</c:forEach>
				</sf:select>
				<br>
				<br>
				
				<label for="workshopSelect">Korjaamo: </label>
				<br>
				<sf:select style="width: auto; max-width: 100%" id="workshopSelect" path="workshop"
					class="form-control">
					<c:forEach var="workshop" items="${workshops}">
						<option>${workshop}</option>
					</c:forEach>
				</sf:select>

				<br>
				<br>
				<button class="btn btn-large btn-primary" action="submit">Seuraava</button>
			</sf:form>
		</div>

		<br>




	<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />
