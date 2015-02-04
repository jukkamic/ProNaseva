<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/templates/includes/header.jsp" />  
 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<body>

<security:authentication property="authorities" var="loginRole" scope="request" />

	<div id="wrap">
		<div class="container">
			<div class="page-header">
				<jsp:include page="/WEB-INF/templates/includes/pageHeaderRow.jsp" />
				<h1>Raportti</h1>
			
			</div>
<sf:form modelAttribute="report" >

	<br>
	<br>
	<div style="border-bottom: 1px solid #eee;">
	<h4>Maahantuoja: ${report.importer.name}</h4>
	<h4>Tarkastettu korjaamo: ${report.workshop.name}</h4>
	<h4>Tarkastuksen päivämäärä: ${report.testDateString}</h4>
	
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

	<div class="panel-group" id="accordion"> 
			<c:set var="bootstrapPanelCounter" value="0" />
					
		<c:if test="${successMessage != null and message != ''}">
			<div class="alert alert-success">
				<h4>${message}</h4>
			</div>
			<br>
		</c:if>
	
	

			
<!-- QuestionGroup loop -->

<c:forEach var="questionGroup" items="${report.reportQuestionGroups}"
	varStatus="questionGroupCounter">
	<c:set var="questionCount" value="1" scope="request" />
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
					href="#panel${bootstrapPanelCounter}">${questionGroup.reportTemplateQuestionGroup.title}
				</a>
			</h4>
		</div>


	<!-- Ensimmäisen kysymysryhmän luokka on "collapse start" jotta javascript 
		tietää mihin nostaa näkymä avattaessa Accordion Menun paneeleja. -->
		
	<c:choose>
		<c:when test="${questionGroupCounter.count == 1}">
			<div id="panel${bootstrapPanelCounter}" class="panel-collapse collapse start">
		</c:when>
		<c:otherwise>
			<div id="panel${bootstrapPanelCounter}" class="panel-collapse collapse">
	</c:otherwise>
	</c:choose>
<div class="panel-body">
						
	<!-- Answers loop -->
							
	<c:forEach var="answer" items="${questionGroup.answers}"
		varStatus="answerCounter">
	
	<c:set var="question" value="${answer.question}" />
	
	<br>
	<c:if test="${question.question != null}">
		<h3 style="display: inline; padding-right: 0; margin-right: 0;">${answer.answerOrderNumber}.</h3>
		<c:if test="${answer.subquestionAnswerOrderNumber != null && answer.subquestionAnswerOrderNumber != 0}">
			<h3 style="display: inline; padding-left: 0; margin-left: 0; padding-right: 0; margin-right: 0;">${answer.subquestionAnswerOrderNumber}.</h3>
		</c:if>
		<h3 style="display: inline; padding-left: 0; margin-left: 0; "> ${question.question}</h3>
	</c:if>
	
	<!-- Multiple choice question -->
		<c:if test='${question["class"] == "class fi.testcenter.domain.question.MultipleChoiceQuestion"}'>
																				
			
			<c:if test="${answer.removeAnswerFromReport == 'true'}">
				<div class="checkbox" style="font-size: 1.2em;">
					<label>											
						<sf:checkbox value='true'
							path="reportQuestionGroups[${questionGroupCounter.index}].answers[${answerCounter.index}].removeAnswerFromReport" disabled="true" label="Hylkää kysymys raportista" />

					</label>
					<br>
				</div>
			</c:if> 
				<c:if test="${answer.removeAnswerFromReport == 'false'}">
		
				
		<c:choose>
		<c:when test="${question.multipleSelectionsAllowed == true}">
						<c:forEach var="option" items="${question.optionsList}">
							<label class="checkbox" style="">											
							<sf:checkbox value="${option.multipleChoiceOption}" 
								path="reportQuestionGroups[${questionGroupCounter.index}].answers[${answerCounter.index}].chosenSelections" label="${option.multipleChoiceOption}" disabled="true" />
							</label>
							<br>
						</c:forEach>
		</c:when>
		<c:otherwise>
		<div class="Demo-boot" style="padding-top: 15px;">
				<div class="btn-group" data-toggle="buttons">
					<c:forEach var="option" items="${question.optionsList}" varStatus="optionsCounter">

						
						<!-- Jos MultipleChoiceOption-oliolle on asetettu pitkää valintanapin tekstiä
								varten erillinen radiobuttonText, jossa napin teksti on jaettu kahdelle 
								riville <br> tägillä, näytetään radiobuttonText, muuten option teksti jossa 
								ei ole tägejä -->
						<c:set var="contains" value="false" />
						<c:forEach var="chosenOption" items="${answer.chosenOptions}" >
							<c:if test="${chosenOption == option}">
								<c:set var="contains" value="true" />
							</c:if>
						</c:forEach>
												
						<c:choose>
							<c:when test="${option.radiobuttonText != null }">
								<c:choose>
									<c:when test="${contains == 'true'}">
										<button class="btn btn-large btn-selectedOption disabled" disabled type="button">
											${option.radiobuttonText}
																																											
										</button>
									</c:when>
									<c:otherwise>
										<<button class="btn btn-large btn-showSelections" disabled type="button">
											${option.radiobuttonText}
										</button>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${contains == 'true'}">
										<button class="btn btn-large btn-selectedOption disabled" disabled type="button">
											${option.multipleChoiceOption}
											
										</button>
									</c:when>
									<c:otherwise>
										<button class="btn btn-large btn-showSelections" type="button" disabled>
											${option.multipleChoiceOption}
										</button>
									</c:otherwise>
								</c:choose>	
							</c:otherwise>
						</c:choose>														
					</c:forEach> 
					
																
				</div>
			</div>
			</c:otherwise>
			</c:choose>
			<br>
			<c:if test="${answer.remarks != 'null' && answer.remarks != '' }">
				<h4>Huomioita:</h4>
				<p style="font-size: 1.2em;">${answer.remarks}</p>
			</c:if>
			<br>
			
			<br>
			</c:if>
		</c:if>

		<!-- Points question -->
		
		<c:if test='${question["class"] == "class fi.testcenter.domain.question.PointsQuestion"}'>
																				
			
			<c:if test="${answer.removeAnswerFromReport == 'true'}">
				<div class="checkbox" style="font-size: 1.2em;">
					<label>											
						<sf:checkbox value='true'
							path="reportQuestionGroups[${questionGroupCounter.index}].answers[${answerCounter.index}].removeAnswerFromReport" disabled="true" label="Hylkää kysymys raportista" />

					</label>
					<br>
				</div>
			</c:if>
			<c:if test="${answer.removeAnswerFromReport == 'false'}">

										
		<div class="Demo-boot" style="padding-top: 15px;">
				<div class="btn-group" data-toggle="buttons">
					<c:forEach var="points" begin="0" end="${question.maxPoints}">
							
						<c:choose>
							<c:when test="${answer.givenPoints == points}">
								<button class="btn btn-large btn-primary disabled" type="button">
									${points}
									
								</button>
							</c:when>
							<c:otherwise>
								<button class="btn btn-large btn-default" type="button" disabled>
									${points}
								</button>
							</c:otherwise>
						</c:choose>	
																
					</c:forEach> 
				</div>
			</div>

			<br>
			<c:if test="${answer.remarks != 'null' && answer.remarks != '' }">
				<h4>Huomioita:</h4>
				<p style="font-size: 1.2em;">${answer.remarks}</p>
			</c:if>
			<br><br>
			</c:if>
		</c:if>

		<!--  Text question -->
		<c:if test='${question["class"] == "class fi.testcenter.domain.question.TextQuestion"}'>
			
			<c:if test="${answer.removeAnswerFromReport == 'true'}">
				<div class="checkbox" style="font-size: 1.2em;">
					<label>											
						<sf:checkbox value='true'
							path="reportQuestionGroups[${questionGroupCounter.index}].answers[${answerCounter.index}].removeAnswerFromReport" disabled="true" label="Hylkää kysymys raportista" />

					</label>
					<br>
				</div>
			</c:if>
			<c:if test="${answer.removeAnswerFromReport == 'false'}">

			<br>
			<p style="font-size: 1.2em;">${answer.answer}</p>
			</c:if>
		</c:if>
		
								
	<!-- Cost listing question -->
		<c:if test='${question["class"] == "class fi.testcenter.domain.question.CostListingQuestion"}'>
			
			<c:if test="${answer.removeAnswerFromReport == 'true'}">
				<div class="checkbox" style="font-size: 1.2em;">
					<label>											
						<sf:checkbox value='true'
							path="reportQuestionGroups[${questionGroupCounter.index}].answers[${answerCounter.index}].removeAnswerFromReport" disabled="true" label="Hylkää kysymys raportista" />

					</label>
					<br>
				</div>
			</c:if>
				<c:if test="${answer.removeAnswerFromReport == 'false'}">
				
				
				<c:forEach var="listQuestion" items="${question.questionItems}" varStatus="costListingAnswerCounter">
					
					<h4>${listQuestion}</h4>
					
					<p style="font-size: 1.2em;">${answer.answersOut[costListingAnswerCounter.index]}</p>
					<br>
				</c:forEach>
					<h4><b>${question.total}</b></h4>
					<p style="font-size: 1.2em;">${answer.total} €</p>
				<br>
				<br>
				<c:if test="${answer.remarks != 'null' && answer.remarks != '' }">
					<h4>Huomioita:</h4>
					<p style="font-size: 1.2em;">${answer.remarks}</p>
				</c:if>
				<br><br>
				</c:if>
		</c:if>
								
<!-- ListAndScoreImportantPoints -->
		<c:if test='${question["class"] == "class fi.testcenter.domain.question.ImportantPointsQuestion"}'>
			
			<c:if test="${answer.removeAnswerFromReport == 'true'}">
				<div class="checkbox" style="font-size: 1.2em;">
					<label>											
						<sf:checkbox value='true'
							path="reportQuestionGroups[${questionGroupCounter.index}].answers[${answerCounter.index}].removeAnswerFromReport" disabled="true" label="Hylkää kysymys raportista" />

					</label>
					<br>
				</div>
			</c:if>
				<c:if test="${answer.removeAnswerFromReport == 'false'}">
			

				<c:forEach var="questionItem" items="${question.questionItems}" varStatus="questionItemCounter">
				<div style="border-bottom: 3px solid #eee;">
					<h3>${questionItem}</h3>
					<table>
					<tr>
					<td width="40%" style="padding-bottom: 2em">
					<h4>Tärkeys: </h4>
					<div class="Demo-boot" style="padding-top: 15px;">
					<div class="btn-group" data-toggle="buttons">

					<c:forEach begin="1" end="${question.numberOfItemsToChoose}" varStatus="importanceNumber">
						<c:choose>
							<c:when test="${answer.answerItems[questionItemCounter.index].importance == importanceNumber.index}"> 
								<button class="btn btn-large btn-primary disabled" type="button">
									${importanceNumber.index}
								</button>
								</c:when>
							<c:otherwise>
								<button class="btn btn-large btn-default" type="button" disabled>
									${importanceNumber.index}
								</button>
							</c:otherwise>
						</c:choose>  

					</c:forEach>

					  
					
					<br><br>
					</div>
					</div>
					</td>
					<td width="20%"></td>
					<td width="40%" style="padding-bottom: 2em">
					<h4>Arvosana: </h4>
					<div class="Demo-boot" style="padding-top: 15px;">
					<div class="btn-group" data-toggle="buttons">
					
						<c:forEach begin="1" end="${question.maxScoreForQuestionItem}" varStatus="score">
						<c:choose>
							<c:when test="${answer.answerItems[questionItemCounter.index].score == score.index}">
								<button class="btn btn-large btn-primary disabled" type="button">
									${score.index} 
								</button>
								</c:when>
							<c:otherwise>
								<button class="btn btn-large btn-default" type="button" disabled>
									${score.index} 
								</button>
							</c:otherwise>
						</c:choose>  

					</c:forEach>


					<br><br>
					</div>
					</div>
					</tr>
					
					</table>
				</div>
				</c:forEach>
			</c:if>	
			<br>
			<c:if test="${answer.remarks != 'null' && answer.remarks != '' }">
				<h4>Huomioita:</h4>
				<p style="font-size: 1.2em;">${answer.remarks}</p>
			</c:if>
			<br><br>
			
		</c:if>	
			
</c:forEach> <!-- Questions loop end -->
		 
		<c:if test="${questionGroup.score != -1}">
		<br>
		<h4 style="float: right; font-weight: bold;">Pisteet: ${questionGroup.score} / 
			${questionGroup.maxScore} </h4>
		
		</c:if>
		
		</div></div></div>


</c:forEach> <!-- Question group loop end -->
			
			<br><br>
	
			</div>			
		

		<c:if test="${report.reportStatus == 'DRAFT' or report.reportStatus == 'AWAIT_APPROVAL' or loginRole == '[ROLE_ADMIN]' }">
			<a class="btn btn-primary" href="/ProNaseva/editReport"><span class="glyphicon glyphicon-pencil" style="text-decoration: none;"></span> Muokkaa</a>
		</c:if>
		<c:if test="${report.reportStatus == 'DRAFT' and loginRole != '[ROLE_ADMIN]' }">
				<a class="btn btn-primary" href="/ProNaseva/submitReportForApproval/"><span class="glyphicon glyphicon-ok" style="text-decoration: none;"></span> Lähetä vahvistettavaksi</a>
		</c:if>
			
		<c:if test="${loginRole == '[ROLE_ADMIN]' and report.reportStatus != 'APPROVED' }">
			
			<a class="btn btn-primary" href="/ProNaseva/approveReport/"><span class="glyphicon glyphicon-ok" style="text-decoration: none;"></span> Vahvista raportti</a>
		</c:if>
		
		
		<a class="btn btn-primary" href="/ProNaseva/getPdf/"><span class="glyphicon glyphicon-download" style="text-decoration: none;"></span> Lataa pdf</a>
		
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
            	    	window.location.href = "/ProNaseva/deleteReport"
            	      }
            	    },
            	  
          	    
          	  }
          	});
            });
       </script>
		
		<jsp:include page="/WEB-INF/templates/includes/footer.jsp" /> 