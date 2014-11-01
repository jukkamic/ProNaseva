<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/templates/includes/header.jsp" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<body>

<security:authentication property="authorities" var="loginRole" scope="request" />

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
			<h4>Raportin päivämäärä: ${report.reportDate}</h4>
			
			<h4>
			<c:choose>
				
				<c:when test="${report.reportStatus == 'DRAFT'}">
					<span class="label label-warning">Luonnos</span>
				</c:when>
				<c:when test="${report.reportStatus == 'AWAIT_APPROVAL'}">
					<span class="label label-info">Vahvistettavana</span>
				</c:when>
				<c:when test="${report.reportStatus == 'APPROVED'}">
					<span class="label label-success">Valmis</span>
				</c:when>
			</c:choose></h4>
						
			<br><br>
			</div>
			<br>
<sf:form modelAttribute="readyReport" action="saveSmileyAndHighlights">
 <div class="panel-group" id="accordion"> 
			<c:set var="bootstrapPanelCounter" value="1" scope="request" />
						
			<c:if test="${empty report.reportHighlights and loginRole == '[ROLE_ADMIN]'}">
				<div class="alert alert-warning">
					<h4>Valitse raportin huomionarvoiset vastaukset!</h4>
				</div>
				<br>
			</c:if>			


		<h3>Raportin yhteenveto</h3>
			<br>
			<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a style="font-size: 1.5em; text-decoration: none; display: block;"
										data-toggle="collapse" data-parent="#accordion"
										href="#${bootstrapPanelCounter}">Tulokset
									</a>
								</h4>
							</div> 
							
								<div id="${bootstrapPanelCounter}" class="panel-collapse collapse start"> 
								<div class="panel-body">
			<br>
			
			<div style="border-bottom: 3px solid #eee;">
			<h3 style="display: inline"><b>Yleisarvosana: </b></h3>


			<div class="Demo-boot" style="padding-top: 15px;">
				<div class="btn-group" data-toggle="buttons">
					
						<c:if test="${readyReport.overallResultSmiley == 'SMILE' }"> 
							<label class="btn btn-primary active">
 						</c:if>
						<c:if test="${readyReport.overallResultSmiley != 'SMILE' }"> 
							<label class="btn btn-primary">
 						</c:if>
					
						<sf:radiobutton id="button" path="overallResultSmiley" value="SMILE" /> <i class="fa fa-smile-o fa-2x"></i>
			
						</label>
						
						<c:if test="${readyReport.overallResultSmiley == 'NEUTRAL' }"> 
							<label class="btn btn-primary active">
 						</c:if>
						<c:if test="${readyReport.overallResultSmiley != 'NEUTRAL' }"> 
							<label class="btn btn-primary">
 						</c:if>
					
						<sf:radiobutton id="button" path="overallResultSmiley" value="NEUTRAL"/> <i class="fa fa-meh-o fa-2x"></i>
			
						</label>
						
						<c:if test="${readyReport.overallResultSmiley == 'FROWN' }"> 
							<label class="btn btn-primary active">
 						</c:if>
						<c:if test="${readyReport.overallResultSmiley != 'FROWN' }"> 
							<label class="btn btn-primary">
 						</c:if>
					
						<sf:radiobutton id="button" path="overallResultSmiley" value="FROWN"/> <i class="fa fa-frown-o fa-2x"></i>
			
						</label>
					</div>
				</div>
				<br><br>
				</div>
		
				<c:set var="highlightGroupsScore" value="0" />
				<c:set var="highlightGroupsMaxScore" value="0" />
				<c:set var="showQuestionGroupHighlightsTitle" value="false" />
				<div style="border-bottom: 3px solid #eee;">
				
				
				<c:forEach var="questionGroupScore" items="${readyReport.questionGroupScore}" varStatus="questionGroupScoreCounter">
					
						<c:if test="${questionGroupScore.questionGroup.showInReportSummary == true}">
								<c:if test="${showQuestionGroupHighlightsTitle == 'false'}">
									<h3><b>Yhteenveto: </b></h3>
									
									<c:set var="showQuestionGroupHighlightsTitle" value="true" />
								</c:if>
								<h3>${questionGroupScore.questionGroup.title}</h3>
								<h4>Pisteet: ${questionGroupScore.score}/${questionGroupScore.maxScore}</h4>
								<c:set var="highlightGroupsScore" value="${highlightGroupsScore + questionGroupScore.score}" />
								<c:set var="highlightGroupsMaxScore" value="${highlightGroupsMaxScore + questionGroupScore.maxScore}" />
								
									<div class="Demo-boot" style="padding-top: 15px;">
									<div class="btn-group" data-toggle="buttons">
									
										<c:if test="${questionGroupScore.scoreSmiley == 'SMILE' }"> 
											<label class="btn btn-primary active">
				 						</c:if>
										<c:if test="${questionGroupScore.scoreSmiley != 'SMILE' }"> 
											<label class="btn btn-primary inline">
				 						</c:if>
									
										<sf:radiobutton id="button" path="questionGroupScore[${questionGroupScoreCounter.index}].scoreSmiley" value="SMILE" /> <i class="fa fa-smile-o fa-2x"></i>
							
										</label>
										
										<c:if test="${questionGroupScore.scoreSmiley == 'NEUTRAL' }"> 
											<label class="btn btn-primary active">
				 						</c:if>
										<c:if test="${questionGroupScore.scoreSmiley != 'NEUTRAL' }"> 
											<label class="btn btn-primary">
				 						</c:if>
									
										<sf:radiobutton id="button" path="questionGroupScore[${questionGroupScoreCounter.index}].scoreSmiley" value="NEUTRAL"/> <i class="fa fa-meh-o fa-2x"></i>
							
										</label>
										
										<c:if test="${questionGroupScore.scoreSmiley == 'FROWN' }"> 
											<label class="btn btn-primary active">
				 						</c:if>
										<c:if test="${questionGroupScore.scoreSmiley != 'FROWN' }"> 
											<label class="btn btn-primary">
				 						</c:if>
									
										<sf:radiobutton id="button"path="questionGroupScore[${questionGroupScoreCounter.index}].scoreSmiley" value="FROWN"/> <span class="fa fa-frown-o fa-2x"></span>
							
										</label>
									</div>
								</div>
							<br>	
						</c:if>
						
					</c:forEach>
								
				<br>
				<c:if test="${showQuestionGroupHighlightsTitle == 'true'}">

				<h4><b>Yhteensä: ${highlightGroupsScore}/${highlightGroupsMaxScore}</b></h4>
			
				<br>
				</c:if>			
			</div>
			<br>
			<div style="border-bottom: 3px solid #eee;">
			<h3><b>Raportin osien pisteet: </b></h3>
					<c:forEach var="reportPartScore" items="${readyReport.reportPartScore}" varStatus="reportPartScoreCounter">
					<c:if test="${reportPartScore.reportPart.showScoreInReportHighlights == 'true' }">
						<h3>${reportPartScore.reportPart.title}</h3>
						<c:if test="${reportPartScore.maxScore > 0}">
							<h4>Pisteet: ${reportPartScore.scorePercentage} %</h4>
						</c:if>
						
						<c:if test="${reportPartScore.maxScore == 0}">
							<h4>Pisteet: --</h4>
						</c:if>
						
						<c:if test="${reportPartScore.maxScore > 0 }">
							<div class="Demo-boot" style="padding-top: 15px;">
								<div class="btn-group" data-toggle="buttons">
							
								<c:if test="${reportPartScore.scoreSmiley == 'SMILE' }"> 
									<label class="btn btn-primary active">
		 						</c:if>
								<c:if test="${reportPartScore.scoreSmiley  != 'SMILE' }"> 
									<label class="btn btn-primary">
		 						</c:if>
							
								<sf:radiobutton id="button" path="reportPartScore[${reportPartScoreCounter.index}].scoreSmiley" value="SMILE" /> <i class="fa fa-smile-o fa-2x"></i>
					
								</label>
								
								<c:if test="${reportPartScore.scoreSmiley  == 'NEUTRAL' }"> 
									<label class="btn btn-primary active">
		 						</c:if>
								<c:if test="${reportPartScore.scoreSmiley  != 'NEUTRAL' }"> 
									<label class="btn btn-primary">
		 						</c:if>
							
								<sf:radiobutton id="button" path="reportPartScore[${reportPartScoreCounter.index}].scoreSmiley" value="NEUTRAL"/> <i class="fa fa-meh-o fa-2x"></i>
					
								</label>
								
								<c:if test="${reportPartScore.scoreSmiley == 'FROWN' }"> 
									<label class="btn btn-primary active">
		 						</c:if>
								<c:if test="${reportPartScore.scoreSmiley != 'FROWN' }"> 
									<label class="btn btn-primary">
		 						</c:if>
							
								<sf:radiobutton id="button"path="reportPartScore[${reportPartScoreCounter.index}].scoreSmiley" value="FROWN"/> <i class="fa fa-frown-o fa-2x"></i>
					
								</label>
							</div>
							</div>
							</c:if>
							<br>	
						
						</c:if>
					</c:forEach>
								
				<br>
				</div>
				<br>
				
				<h3><b>Raportin kokonaispisteet: ${report.totalScorePercentage} %</b></h3>
								
			</div>
			<br><br>
			
			<c:set var="bootstrapPanelCounter" value="1" scope="request" />
			</div>
			</div>
								
 			<c:if test="${not empty report.reportHighlights}">
 				<jsp:include page="/WEB-INF/templates/showReportHighlightAnswers.jsp" />
 				<c:set var="bootstrapPanelCounter" value="2" scope="request" />
			</c:if>
				

			
			<!-- Report part loop -->
			
			<c:set var="answerIndexCounter" value="0" scope="request" />
			<c:set var="questionGroupScoreIndexCounter" value="0" scope="request" />
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


										<div id="${bootstrapPanelCounter}" class="panel-collapse collapse">
							
						
								<div class="panel-body">
								
									<!-- Questions loop -->
									
									<c:forEach var="question" items="${questionGroup.questions}"
										varStatus="questionCounter">

										<!-- Multiple choice question -->
										<c:if test='${question["class"] == "class fi.testcenter.domain.MultipleChoiceQuestion"}'>
																												
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
																	<c:when test="${report.answers[answerIndexCounter].chosenOptionIndex == optionsCounter.index}">
																		<button class="btn btn-large btn-primary disabled" type="button">
																			${option.radiobuttonText}
																																																			
																		</button>
																	</c:when>
																	<c:otherwise>
																		<button class="btn btn-large btn-default" type="button" disabled>
																			${option.radiobuttonText}
																		</button>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${report.answers[answerIndexCounter].chosenOptionIndex == optionsCounter.index}">
																		<button class="btn btn-large btn-primary disabled" type="button">
																			${option.option}
																			
																		</button>
																	</c:when>
																	<c:otherwise>
																		<button class="btn btn-large btn-default" type="button" disabled>
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
										<c:if test='${question["class"] == "class fi.testcenter.domain.TextQuestion"}'>
											<h3>${questionCounter.count}. ${question.question}</h3>
											<br>
											<p style="font-size: 1.2em;">${report.answers[answerIndexCounter].answer}</p>
										</c:if>
										
										<c:set var="answerIndexCounter" value="${answerIndexCounter + 1}" scope="request" />
										
											<!-- Show subquestions -->
										
										<c:if test="${not empty question.subQuestions}">
											<c:set var="mainQuestion" value="${question}" scope="request" />
											<div style="margin-left: 3em;">
												<jsp:include page="/WEB-INF/templates/showReportSubQuestions.jsp" />
											</div>				
										</c:if>
										
										
									</c:forEach> <!-- Questions loop end -->
									
									<c:if test="${report.questionGroupScore[questionGroupScoreIndexCounter].showScore == true}">
									<br>
									<h4 style="float: right; font-weight: bold;">Pisteet: ${report.questionGroupScore[questionGroupScoreIndexCounter].score} / 
										${report.questionGroupScore[questionGroupScoreIndexCounter].maxScore} </h4>
									
									</c:if>
									
									</div></div></div>
					
						<c:set var="questionGroupScoreIndexCounter" value="${questionGroupScoreIndexCounter + 1}" scope="request" />
					</c:forEach> <!-- Question group loop end -->
					
					<br><br>
					</c:forEach> <!-- Report part loop end -->
					</div>			
				
	
				<c:if test="${report.reportStatus == 'DRAFT' or report.reportStatus == 'AWAIT_APPROVAL' or loginRole == '[ROLE_ADMIN]' }">
					<a class="btn btn-primary" href="/ProNaseva/editReport"><span class="glyphicon glyphicon-pencil" style="text-decoration: none;"></span> Muokkaa</a>
				</c:if>
				<c:if test="${report.reportStatus == 'DRAFT' and loginRole != '[ROLE_ADMIN]' }">
						<a class="btn btn-primary" href="/ProNaseva/submitReportForApproval/"><span class="glyphicon glyphicon-ok" style="text-decoration: none;"></span> Lähetä vahvistettavaksi</a>
				</c:if>
					
				<c:if test="${loginRole == '[ROLE_ADMIN]' and report.reportStatus != 'APPROVED' }">
					<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-save" style="text-decoration: none;"></span> Tallenna</button>
					<a class="btn btn-primary" href="/ProNaseva/approveReport/"><span class="glyphicon glyphicon-ok" style="text-decoration: none;"></span> Vahvista raportti</a>
				</c:if>
				<a class="btn btn-primary" href="/ProNaseva/printReport/"><span class="glyphicon glyphicon-print" style="text-decoration: none;"></span> Tulosta</a>
				
				<c:if test="${loginRole == '[ROLE_ADMIN]' or report.reportStatus == 'DRAFT' or report.reportStatus == 'AWAIT_APPROVAL'}">
					<a href="#" class="btn btn-large btn btn-danger deleteReport"><span class="glyphicon glyphicon-remove" style="text-decoration: none;"></span> Poista</a>
				</c:if>
								
				<br><br>
				<br>
</sf:form>
		</div>
				
		<script>
        $(document).on("click", ".deleteReport", function(e) {
            bootbox.dialog({
          	  message: "Poista raportti?",
          	  title: "Vahvista",
          	  buttons: {
          		confirm: {
            	      label: "Poista",
            	      className: "btn-primary",
            	      callback: function() {
            	    	window.location.href = "deleteReport"
            	      }
            	    },
            	  cancel: {
          	      label: "Peruuta",
          	      className: "btn-danger",
          	      callback: function() {
          	        
          	      }
          	    },
          	    
          	  }
          	});
            });
       </script>
		
		<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />