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
			<br>
			<br>
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

								<!-- Ensimmäisen kysymysryhmän luokka on "collapse in" jotta javascript 
									tietää mihin nostaa näkymä avattaessa Accordion Menun paneeleja. -->
								<c:choose>
									<c:when test="${questionGroupCounter.count == 1}">
										<div id="${questionGroupCounter.count}" class="panel-collapse collapse start">
									</c:when>
									<c:otherwise>
										<div id="${questionGroupCounter.count}" class="panel-collapse collapse">
								</c:otherwise>
							</c:choose>
								<div class="panel-body">
								
									<!-- Questions loop -->
									<c:forEach var="question" items="${questionGroup.questions}"
										varStatus="questionCounter">

										<!-- Multiple choice question -->
										<c:if
											test="${question.class == 'class fi.testcenter.domain.MultipleChoiceQuestion'}">
											<h3>${questionCounter.count}. ${question.question}</h3>
											<div class="Demo-boot" style="padding-top: 15px;">
												<div class="btn-group" data-toggle="buttons">
													<c:forEach var="option" items="${question.options}" varStatus="optionsCounter">
														<label class="btn btn-primary"> 
															<c:choose>
															<c:when test="${option.radiobuttonText != null }">
																<sf:radiobutton id="button" path="questionGroups[${questionGroupCounter.index}].questions[${questionCounter.index}].chosenOptionIndex" 
																value="${optionsCounter.index}" /> ${option.radiobuttonText}
															</c:when>
															<c:otherwise>
																														
								<sf:radiobutton id="button" path="questionGroups[${questionGroupCounter.index}].questions[${questionCounter.index}].chosenOptionIndex" 
								value="${optionsCounter.index}" /> ${option.option}
								
															</c:otherwise>
															</c:choose>
														</label>
													</c:forEach> 
												</div>
											</div>
											<br>
											<h4>Huomioita:</h4>
											<sf:textarea rows="5" style="width:100%;" path="questionGroups[${questionGroupCounter.index}].questions[${questionCounter.index}].remarks" />
											<br><br>
										</c:if>

										<!--  Text area question -->
										<c:if
											test="${question.class == 'class fi.testcenter.domain.TextareaQuestion'}">
											<h3>${questionCounter.count}. ${question.question}</h3>
											<br>
											<sf:textarea rows="5" style="width:100%;" path="questionGroups[${questionGroupCounter.index}].questions[${questionCounter.index}].answer" />
										</c:if>
										
										<!-- Text field question -->
										<c:if
											test="${question.class == 'class fi.testcenter.domain.TextfieldQuestion'}">
											<h3>${questionCounter.count}. ${question.question}</h3>
											<br>
											<sf:input type="text" style="width:100%;" path="questionGroups[${questionGroupCounter.index}].questions[${questionCounter.index}].answer" />
										</c:if>
									</c:forEach>
									</div>
									</div>
							</div>
					</c:forEach>
				</div>
				<br>
				<button class="btn btn-large btn-primary" action="submit">Valmis</button>
				<br><br>
				<br></sf:form>
		</div>
		<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />