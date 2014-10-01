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
			<div style="border-bottom: 1px solid #eee;">
			<h4>Maahantuoja: ${report.importer.name}</h4>
			<h4>Tarkastettu korjaamo: ${report.workshop.name}</h4>
			<span class="label label-success">Valmis</span>
			<br><br>
			</div>
			<br><br>
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
														
														
														<!-- Jos MultipleChoiceOption-oliolle on asetettu pitkää valintanapin tekstiä
																varten erillinen radiobuttonText, jossa napin teksti on jaettu kahdelle 
																riville <br> tägillä, näytetään radiobuttonText, muuten option teksti jossa 
																ei ole tägejä -->
														<c:choose>
															<c:when test="${option.radiobuttonText != null }">
																<c:choose>
																	<c:when test="${question.chosenOptionIndex == optionsCounter.index}">
																		<button class="btn btn-large btn-primary" type="button">
																			${option.radiobuttonText}
																		</button>
																	</c:when>
																	<c:otherwise>
																		<button class="btn btn-large btn-primary" type="button" disabled>
																			${option.radiobuttonText}
																		</button>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${question.chosenOptionIndex == optionsCounter.index}">
																		<button class="btn btn-large btn-primary" type="button">
																			${option.option}
																		</button>
																	</c:when>
																	<c:otherwise>
																		<button class="btn btn-large btn-primary" type="button" disabled>
																			${option.option}
																		</button>
																	</c:otherwise>
																</c:choose>	
															</c:otherwise>
														</c:choose>														
													</c:forEach> 
												</div>
											</div>
											<br>
											<h4>Huomioita:</h4>
											<p>${question.answer.remarks}</p>
											
											<br><br>
										</c:if>

										<!--  Text area question -->
										<c:if
											test="${question.class == 'class fi.testcenter.domain.TextareaQuestion'}">
											<h3>${questionCounter.count}. ${question.question}</h3>
											<br>
											<p>${question.answer}</p>
										</c:if>
										
										<!-- Text field question -->
										<c:if
											test="${question.class == 'class fi.testcenter.domain.TextfieldQuestion'}">
											<h3>${questionCounter.count}. ${question.question}</h3>
											<br>
											<p>${question.answer}</p>
										</c:if> 
									</c:forEach>
									</div>
									</div>
					</div>
					</c:forEach>
				</div>
				<br>

				<a class="btn btn-primary" href="/ProNaseva/">Alkuun</a>
				<a class="btn btn-primary" href="/ProNaseva/printReport/">Tulosta</a>
				
				<br><br>
				<br>
				
		</div>
		<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />