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
				<h1>Uusi raportti</h1>
			</div>
			<br>
			<br>
			<div style="border-bottom: 1px solid #eee;">
			<h4>Maahantuoja: ${report.importer.name}</h4>
			<h4>Tarkastettu korjaamo: ${report.workshop.name}</h4>
			<span class="label label-warning">Kesken</span>
			<br><br>
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

								<!-- Ensimmäisen kysymysryhmän luokka on "collapse start" jotta javascript 
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
									
									<c:forEach var="question" items="${questionGroup.questions}" varStatus="questionCounter">
										
										
										<!-- Multiple choice question -->
										
										<c:if test="${question.class == 'class fi.testcenter.domain.MultipleChoiceQuestion'}">
																				
											<h3>${questionCounter.count}. ${question.question}</h3>
											<div class="Demo-boot" style="padding-top: 15px;">
												<div class="btn-group" data-toggle="buttons">
													<c:forEach var="option" items="${question.options}" varStatus="optionsCounter">
														
														<!-- Jos kysymykselle on ennalta tehty valinta esim. muokattaessa 
																raporttia uudelleen, kyseinen valintanappi näkyy valittuna. -->
														<c:choose>
															<c:when test="${question.answer.chosenOptionIndex == optionsCounter.index}">
																<label class="btn btn-primary active">
															</c:when>
															<c:otherwise>
																<label class="btn btn-primary">
															</c:otherwise>
														</c:choose> 
														
														<!-- Jos MultipleChoiceOption-oliolle on asetettu pitkää valintanapin tekstiä
																varten erillinen radiobuttonText, jossa napin teksti on jaettu kahdelle 
																riville <br> tägillä, näytetään radiobuttonText, muuten option teksti jossa 
																ei ole tägejä -->
														<c:choose>
															<c:when test="${option.radiobuttonText != null }">
																<sf:radiobutton id="button" path="questionGroups[${questionGroupCounter.index}].questions[${questionCounter.index}].answer.chosenOptionIndex" 
																value="${optionsCounter.index}" /> ${option.radiobuttonText}
															</c:when>
															<c:otherwise>
																<sf:radiobutton id="button" path="questionGroups[${questionGroupCounter.index}].questions[${questionCounter.index}].answer.chosenOptionIndex"
																value="${optionsCounter.index}" /> ${option.option}
															</c:otherwise>
															</c:choose>
														</label>
													</c:forEach> 
												</div>
											</div>
											
											<br>
											<h4>Huomioita:</h4>
											<sf:textarea rows="5" style="width:100%;" path="questionGroups[${questionGroupCounter.index}].questions[${questionCounter.index}].answer.remarks" />
											<br><br>
										</c:if>

										<!--  Text area question -->
										<c:if
											test="${question.class == 'class fi.testcenter.domain.TextareaQuestion'}">
											<h3>${questionCounter.count}. ${question.question}</h3>
											<br>
											<sf:textarea rows="5" style="width:100%;" path="questionGroups[${questionGroupCounter.index}].questions[${questionCounter.index}].answer.answer" />
										</c:if>
										
										<!-- Text field question -->
										<c:if
											test="${question.class == 'class fi.testcenter.domain.TextfieldQuestion'}">
											<h3>${questionCounter.count}. ${question.question}</h3>
											<br>
											<sf:input type="text" style="width:100%;" path="questionGroups[${questionGroupCounter.index}].questions[${questionCounter.index}].answer.answer" /> 
										</c:if>
										
										
									</c:forEach> <!-- Questions end -->
									</div>
									</div>
									</div>
									
												
					</c:forEach> <!-- PanelGroup end -->
					</div>
										
					
				<br>
				
				<button type="submit" class="btn btn-primary">Tallenna</button>
				<a class="btn btn-primary" href="/ProNaseva/">Hylkää</a>
				
				<br><br>
				<br></sf:form>
				
		</div>
		<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />