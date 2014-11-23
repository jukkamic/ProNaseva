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
				<h1>Lisää valinnaisia kysymyksiä</h1>
			</div>
			<br><br>
			<sf:form modelAttribute="chosenQuestions" action="addChosenQuestions" method="POST">
				<c:forEach var="question" items="${optionalQuestions}" varStatus="questionCounter">
				<label class="checkbox">											
					<sf:checkbox value="${questionCounter.index}" 
								path="chosenQuestions" label="${question.question}" />
						</label>
						<br>
				</c:forEach>
			
			<button type="submit" class="btn btn-primary"> Valitse kysymykset</button>
			
			</sf:form>
			
			
			
			
			
			
			
					
</div>
	
<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />