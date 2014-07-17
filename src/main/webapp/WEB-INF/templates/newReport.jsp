<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/templates/includes/header.jsp" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<body>
	<div id="wrap">
		<div class="container">
			<div class="page-header">
				<h1>Raportti</h1>
			</div>

			<br> <br>



			<h2>1. Tarkastuskohteet</h2>

			<sf:form modelAttribute="report" action="submitReport" method="post">

				
				<c:forEach var="question" items="${report.questions}">
					
					<label>${question.question}</label>

					<div class="Demo-boot" style="padding-top: 15px;">
						<div class="btn-group" data-toggle="buttons">
							<label class="btn btn-primary"> <sf:radiobutton
									path="${question.choice}" value="0" /> 0 p
							</label> <label class="btn btn-primary"> <sf:radiobutton
									path="${question.choice}" value="1" /> 1 p
							</label> <label class="btn btn-primary"> <sf:radiobutton
									path="${question.choice}" value="2" /> 2 p
							</label>
						</div>
					</div>
					<br>

					<label for="remarks">Kommentti:</label>
					<br>
					<sf:textarea id="remarks" rows="5" cols="70"
						path="${question.remark}" />

					<br>

				</c:forEach>
				<button class="btn btn-large btn-primary" action="submit">Valmis</button>
		</sf:form>
</div>

				<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />