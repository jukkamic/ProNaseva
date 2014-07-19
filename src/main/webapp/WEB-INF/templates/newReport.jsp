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

			<sf:form modelAttribute="report" action="submitReport" method="post">
				
				
				<!-- QuestionGroup loop -->
				<c:forEach var="questionGroup" items="${report.questionGroups}" varStatus="questionGroupCounter">
					<h2>${questionGroupCounter.count}. ${questionGroup.title}</h2>
					<br>
					
					<!--  Questions loop -->					
					<c:forEach var="question" items="${questionGroup.questions}" varStatus="questionCounter">
												
						<!-- MultipleChoiceQuestion (multiple choice + remarks) -->
						<c:if test="${question.class == 'class fi.testcenter.domain.MultipleChoiceQuestion'}">
							<h3><p>${questionGroupCounter.count}.${questionCounter.count}. ${question.question}</p></h3>
						
							
							<div class="Demo-boot" style="padding-top: 15px;">
								<div class="btn-group" data-toggle="buttons">
									<c:forEach var="option" items="${question.options}">
									<label class="btn btn-primary"> 
										<sf:radiobutton	path="${question.chosenOption}" value="${option}"/> ${option.option}
									</label> 
									</c:forEach>
								</div>
							</div>
							<br>
							
							<p><h4>Huomioita:</h4></p>
							<sf:textarea id="remarks" rows="5" cols="70" path="${question.remarks}" />
							<br><br><br>
						</c:if>
						
						
						<!-- TextQuestion (textfield question) -->
						
						<c:if test="${question.class == 'class fi.testcenter.domain.TextfieldQuestion'}">
							<p><h3>${questionGroupCounter.count}.${questionCounter.count}. ${question.question}</h3></p>
							<br>
							<sf:textarea id="remarks" rows="5" cols="70" path="${question.answer}" />
							<br>
						</c:if>
						
					</c:forEach>
					<br><br><br><br>
				</c:forEach>

				<button class="btn btn-large btn-primary" action="submit">Valmis</button>
				
			</sf:form>
			<br><br><br><br>
		</div>

		<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />