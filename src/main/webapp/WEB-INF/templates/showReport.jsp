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
						
						<!-- Muuttujat monivalintakysymysten kysymysryhmäkohtaiseen pisteytykseen -->
						<c:set var="maxTotalScore" value="0" />
						<c:set var="totalScore" value="0" />
						<c:set var="scoredQuestions" value="FALSE" />
						
						
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
																		
											<c:set var="maxPointsForQuestion" value="0" />	
											<c:set var="scoredQuestions" value="TRUE" />			

											
											<h3>${questionCounter.count}. ${question.question}</h3>
											<div class="Demo-boot" style="padding-top: 15px;">
												<div class="btn-group" data-toggle="buttons">
													<c:forEach var="option" items="${question.options}" varStatus="optionsCounter">
																																																											
														<c:if test="${option.points > maxPointsForQuestion}">
																<c:set var="maxPointsForQuestion" value="${option.points}" />
														</c:if>
														
														<!-- Jos MultipleChoiceOption-oliolle on asetettu pitkää valintanapin tekstiä
																varten erillinen radiobuttonText, jossa napin teksti on jaettu kahdelle 
																riville <br> tägillä, näytetään radiobuttonText, muuten option teksti jossa 
																ei ole tägejä -->
														<c:choose>
															<c:when test="${option.radiobuttonText != null }">
																<c:choose>
																	<c:when test="${question.answer.chosenOptionIndex == optionsCounter.index}">
																		<button class="btn btn-large btn-primary" type="button">
																			${option.radiobuttonText}
																																						
																			<c:set var="totalScore" value="${totalScore + option.points}" />
																																																								
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
																	<c:when test="${question.answer.chosenOptionIndex == optionsCounter.index}">
																		<button class="btn btn-large btn-primary" type="button">
																			${option.option}
																			<c:set var="totalScore" value="${totalScore + option.points}" />
																			
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
													
													<c:set var="maxTotalScore" value="${maxTotalScore + maxPointsForQuestion}" />
																											
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
									
									<c:if test="${scoredQuestions == 'TRUE'}">
									<br>
									<h4 style="float: right; font-weight: bold;">Pisteet: ${totalScore}/${maxTotalScore}</h4>
									
									</c:if>
									</div>
									</div>
					</div>
					</c:forEach>
										
				</div>
				<br>

				<a class="btn btn-primary" href="editReport"><span class="glyphicon glyphicon-pencil" style="text-decoration: none;"></span> Muokkaa</a>
				<a class="btn btn-primary" href="/ProNaseva/printReport/"><span class="glyphicon glyphicon-print" style="text-decoration: none;"></span> Tulosta</a>
				
				<c:if test="${edit == 'TRUE'}">
					<a href="#" class="btn btn-large btn btn-danger deleteReport"><span class="glyphicon glyphicon-remove" style="text-decoration: none;"></span> Poista</a>
				</c:if>
				
				<br><br>
				<br>
				
		</div>
				
		<script>
        $(document).on("click", ".deleteReport", function(e) {
            bootbox.dialog({
          	  message: "Poista raportti?",
          	  title: "Vahvista",
          	  buttons: {
          	    cancel: {
          	      label: "Peruuta",
          	      className: "btn-primary",
          	      callback: function() {
          	        
          	      }
          	    },
          	    confirm: {
          	      label: "Poista",
          	      className: "btn-danger",
          	      callback: function() {
          	    	window.location.href = "deleteReport"
          	      }
          	    }
          	  }
          	});
            });
       </script>
		
		<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />