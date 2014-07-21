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
			<br><br>
			<sf:form modelAttribute="report" action="submitReport" method="post">


				<!-- QuestionGroup loop -->
		
				<div class="panel-group" id="accordion">
					<c:forEach var="questionGroup" items="${report.questionGroups}"
						varStatus="questionGroupCounter">

						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a
										style="font-size: 1.5em; text-decoration: none; display: block;"
										data-toggle="collapse" data-parent="#accordion"
										href="#${questionGroupCounter.count}">${questionGroup.title}</a>
								</h4>
							</div>
							<div id="${questionGroupCounter.count}"
								class="panel-collapse collapse">
								<div class="panel-body">

									<!-- Questions loop -->
		
									<c:forEach var="question" items="${questionGroup.questions}"
										varStatus="questionCounter">


										<!-- Multiple choice question -->
			
										<c:if
											test="${question.class == 'class fi.testcenter.domain.MultipleChoiceQuestion'}">
											<h3>${questionCounter.count}.${question.question}</h3>

											<div class="Demo-boot" style="padding-top: 15px;">
												<div class="btn-group" data-toggle="buttons">

													<c:forEach var="option" items="${question.options}">
														<label class="btn btn-primary" for="button"> <sf:radiobutton
																id="button" path="${question.chosenOption}"
																value="${option}" /> ${option}
														</label>
													</c:forEach>

												</div>
											</div>
											<br>
											<h4>Huomioita:</h4>
											<sf:textarea id="remarks" rows="5" cols="70"
												path="${question.remarks}" />
											<br><br>
											
										</c:if>

										<!--  Text field question -->

										<c:if
											test="${question.class == 'class fi.testcenter.domain.TextfieldQuestion'}">
											<h3>${questionCounter.count}.${question.question}</h3>
											<br>
											<sf:textarea id="remarks" rows="5" cols="70"
												path="${question.answer}" />

										</c:if>
									</c:forEach>

									<br><br>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
				<br>
				<button class="btn btn-large btn-primary" action="submit">Valmis</button>

			</sf:form>
		</div>

		<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />