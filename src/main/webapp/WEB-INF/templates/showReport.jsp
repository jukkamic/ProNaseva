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
			
			
			<!-- Report part loop -->
			
			<c:set var="bootstrapPanelCounter" value="0" />
			
			<div class="panel-group" id="accordion">
			
			<c:set var="answerIndexCounter" value="0" scope="request" />
			<c:forEach var="reportPart" items="${report.reportTemplate.reportParts}" varStatus="reportPartCounter">
			<h3>${reportPart.title}</h3>
			<br>			
								
								
					<!-- QuestionGroup loop -->
					
					<c:forEach var="questionGroup" items="${reportPart.questionGroups}"
						varStatus="questionGroupCounter">
						
						<c:set var="bootstrapPanelCounter" value="${bootstrapPanelCounter + 1}" />
						
						<!-- Muuttujat monivalintakysymysten kysymysryhmäkohtaiseen pisteytykseen -->
						<c:set var="maxTotalScore" value="0" scope="request"/>
						<c:set var="totalScore" value="0" scope="request"/>
						<c:set var="scoredQuestions" value="FALSE" scope="request"/>
						
						
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a
										style="font-size: 1.5em; text-decoration: none; display: block;"
										data-toggle="collapse" data-parent="#accordion"
										href="#${bootstrapPanelCounter}">${questionGroup.title}
									</a>
								</h4>
							</div>

								<!-- Ensimmäisen kysymysryhmän luokka on "collapse in" jotta javascript 
									tietää mihin nostaa näkymä avattaessa Accordion Menun paneeleja. -->
								<c:choose>
									<c:when test="${questionGroupCounter.count == 1 and reportPartCounter.count == 1}">
										<div id="${bootstrapPanelCounter}" class="panel-collapse collapse start">
									</c:when>
									<c:otherwise>
										<div id="${bootstrapPanelCounter}" class="panel-collapse collapse">
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
																	<c:when test="report.answers[${answerIndexCounter}].chosenOptionIndex == optionsCounter.index}">
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
																	<c:when test="report.answers[${answerIndexCounter}].chosenOptionIndex == optionsCounter.index}">
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
											<p style="font-size: 1.2em;">${report.answers[answerIndexCounter].remarks}</p>
											
											<br><br>
										</c:if>

										<!--  Text question -->
										<c:if test="${question.class == 'class fi.testcenter.domain.TextQuestion'}">
											<h3>${questionCounter.count}. ${question.question}</h3>
											<br>
											<p style="font-size: 1.2em;">${report.answers[answerIndexCounter].answer}</p>
										</c:if>
										
											<!-- Show subquestions -->
										
										<c:if test="${not empty question.subQuestions}">
											<c:set var="mainQuestion" value="${question}" scope="request" />
											<div style="margin-left: 3em;">
												<jsp:include page="/WEB-INF/templates/showReportSubQuestions.jsp" />
											</div>				
										</c:if>
										
										<c:set var="answerIndexCounter" value="${answerIndexCounter + 1}" />
									</c:forEach> <!-- Questions loop end -->
									
									<c:if test="${scoredQuestions == 'TRUE'}">
									<br>
									<h4 style="float: right; font-weight: bold;">Pisteet: </h4>
									
									</c:if>
									
									</div></div></div>
					
					
					</c:forEach> <!-- Question group loop end -->
					
					<br><br>
					</c:forEach> <!-- Report part loop end -->
					</div>			
				
				
				<a class="btn btn-primary" href="editReport"><span class="glyphicon glyphicon-pencil" style="text-decoration: none;"></span> Muokkaa</a>
				<a class="btn btn-primary" href="/ProNaseva/printReport/"><span class="glyphicon glyphicon-print" style="text-decoration: none;"></span> Tulosta</a>
				
				<a href="#" class="btn btn-large btn btn-danger deleteReport"><span class="glyphicon glyphicon-remove" style="text-decoration: none;"></span> Poista</a>
								
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