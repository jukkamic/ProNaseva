<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/templates/includes/header.jsp" />  

 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<body>
<security:authentication property="authorities" var="loginRole" scope="request" />
	<div id="wrap">
		<div class="container">
			<div class="page-header">
				<jsp:include page="/WEB-INF/templates/includes/pageHeaderRow.jsp" /> 
				<h1>Raportin muokkaus</h1>
			</div>
			<br><br>
			
<sf:form id="editReportForm" modelAttribute="report" action="saveWorkshopVisitReport" method="post">


<!-- Raportin yleistiedot -->

			<div style="border-bottom: 1px solid #eee;">
			<h4>Maahantuoja: ${report.importer.name}</h4>
			
			<label for="workshopSelect"><h4>Valitse korjaamo: </h4></label>
				
				<sf:select style="width: auto; max-width: 100%; display: inline;" id="workshopSelect" path="workshopId"
					class="form-control">
					<c:forEach var="workshop" items="${workshops}">
						<c:choose>
							<c:when test='${workshop.id == report.workshop.id}'>
								<option selected="selected" value="${workshop.id}">${workshop}</option>
							</c:when>
							<c:otherwise>
								<option value="${workshop.id}">${workshop}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</sf:select>
			<br>
			<label for="date"><h4>Tarkastuksen päivämäärä: </h4></label>
			<sf:input path="testDateString" name="date" class="datepicker" id="date" value="${report.testDateString}"/>
			<br>
			<h4>			
			<c:choose>
				<c:when test="${edit == 'TRUE'}">
					<span class="label label-warning">Muokkaus</span>
				</c:when>
				<c:otherwise>
					<span class="label label-warning">Luonnos</span>
				</c:otherwise>
			</c:choose>
			</h4>
						
			<br><br>
			</div>
			
			<br>

<!-- Raportinosien nav-pills listaus -->

<input type="hidden" id="navigateToReportPart" name="navigateToReportPart">
			<ul class="nav nav-pills nav-stacked" role="tablist">
				<c:forEach var="navbarReportPart" items="${report.reportParts}" varStatus="navbarCounter">
			  		<c:if test="${navbarCounter.index == editReportPartNumber}">
			  			<li role="presentation" class="active"><a onclick="navigateToReportPart(${navbarCounter.index});">${navbarReportPart.reportTemplatePart.title}</a></li>
			  		</c:if>
			  		<c:if test="${navbarCounter.index != editReportPartNumber}">
			  			<li role="presentation"><a onclick="navigateToReportPart(${navbarCounter.index});">${navbarReportPart.reportTemplatePart.title}</a></li>
			  		</c:if>
				</c:forEach>
			</ul>
						
			<br>
			

<!-- Report part loop -->
			
	<c:set var="bootstrapPanelCounter" value="0" />
	
	<div class="panel-group" id="accordion">

	<c:set var="answerIndexCounter" value="${initialAnswerIndexCounter}" scope="request" />

	
	<c:set var="reportPart" value="${report.reportParts[editReportPartNumber]}" />
	
	<h3>${reportPart.reportTemplatePart.title}</h3>
	<br>			

<!-- QuestionGroup loop -->
						
	
	<c:forEach var="questionGroup" items="${reportPart.reportQuestionGroups}" varStatus="questionGroupCounter">
		
		<c:set var="bootstrapPanelCounter" value="${bootstrapPanelCounter + 1}" />
		<c:set var="questionCount" value="1" scope="request" />
		
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
									
		<c:forEach var="answer" items="${questionGroup.answers}" varStatus="questionCounter">
			
		<br>
		<c:if test="${answer.question.question != null}">
			<h3 style="display: inline; padding-right: 0; margin-right: 0;">${answer.answerOrderNumber}.</h3>
			<c:if test="${answer.subquestionAnswerOrderNumber != null && answer.subquestionAnswerOrderNumber != 0}">
				<h3 style="display: inline; padding-left: 0; margin-left: 0; padding-right: 0; margin-right: 0;">${answer.subquestionAnswerOrderNumber}.</h3>
			</c:if>
			<h3 style="display: inline; padding-left: 0; margin-left: 0; "> ${answer.question.question}</h3>
		</c:if>
		
<!-- Multiple choice question -->
										
		<c:if test='${answer["class"] == "class fi.testcenter.domain.answer.MultipleChoiceAnswer"}'>
		
		<div class="checkbox" style="font-size: 1.2em;">
			<label>											
			<sf:checkbox value='true'
				path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].removeAnswerFromReport" label="Hylkää kysymys raportista" />
			
			</label>
			<br>
		</div>
		<c:if test="${loginRole == '[ROLE_ADMIN]' }">
			<div class="checkbox" style="font-size: 1.2em;">
			<label>											
			<sf:checkbox value='true'
				path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].highlightAnswer" label="Huomiot-osioon" />
			
			</label>
			
			</div>
			
		</c:if>

		<c:choose>
			<c:when test="${answer.question.multipleSelectionsAllowed == true}">
				<c:forEach var="option" items="${answer.question.optionsList}" varStatus="optionsCounter">
					<label class="checkbox">											
						<sf:checkbox value="${optionsCounter.index}" 
							path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].chosenOptionsIndex" label="${option.multipleChoiceOption}" />
					</label>
					<br>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<div class="Demo-boot" style="padding-top: 15px;">
					<div class="btn-group" data-toggle="buttons">
						<c:forEach var="option" items="${answer.question.optionsList}" varStatus="optionsCounter">
							
							<!-- Jos kysymykselle on ennalta tehty valinta esim. muokattaessa 
								raporttia uudelleen, kyseinen valintanappi näkyy valittuna. -->
							<c:set var="contains" value="false" />
							<c:forEach var="chosenOption" items="${answer.chosenOptions}" >
								<c:if test="${chosenOption == option}">
								<c:set var="contains" value="true" />
								</c:if>
							</c:forEach>
							<c:choose>
							<c:when test="${contains == 'true'}"> 
								<label class="btn btn-showSelections active">
								</c:when>
							<c:otherwise>
								<label class="btn btn-showSelections">
							</c:otherwise>
							</c:choose>   
							
							<!-- Jos MultipleChoiceOption-oliolle on asetettu pitkää valintanapin tekstiä
									varten erillinen radiobuttonText, jossa napin teksti on jaettu kahdelle 
									riville <br> tägillä, näytetään radiobuttonText, muuten option teksti jossa 
									ei ole tägejä -->
							<c:choose>
								<c:when test="${option.radiobuttonText != null }">
									<sf:radiobutton id="button" path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].chosenOptionsIndex" 
									value="${optionsCounter.index}" /> ${option.radiobuttonText}
								</c:when>
								<c:otherwise>
									<sf:radiobutton id="button" path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].chosenOptionsIndex"
									value="${optionsCounter.index}" /> ${option.multipleChoiceOption}
								</c:otherwise>
								</c:choose>
							</label>
						</c:forEach> 
						 <c:choose>
								<c:when test="${contains == 'true'}"> 
									<label class="btn btn-default active" style="min-height: 4em; border-color: #000">
									</c:when>
								<c:otherwise>
									<label class="btn btn-default" style="min-height: 4em; border-color: #000">
								</c:otherwise>
							</c:choose> 
							<sf:radiobutton id="button" path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].chosenOptionsIndex" 
									value="-1" /> Ei valintaa
							
					</div>
				</div>
				</c:otherwise>
				</c:choose> 
			
				<br>
				<h4>Huomioita:</h4>
				<sf:textarea rows="5" style="width:100%;" path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].remarks" 
					value="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].remarks}" />
				<br><br>
			</c:if> 
		
		<!-- Points question -->
			<c:if test='${answer["class"] == "class fi.testcenter.domain.answer.PointsAnswer"}'>
			
			<div class="checkbox" style="font-size: 1.2em;">
				<label>											
				<sf:checkbox value='true'
					path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].removeAnswerFromReport" label="Hylkää kysymys raportista" />
				
				</label>
				<br>
			</div>
				<c:if test="${loginRole == '[ROLE_ADMIN]' }">
					<div class="checkbox" style="font-size: 1.2em;">
						<label>											
							<sf:checkbox value='true'
								path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].highlightAnswer" label="Huomiot-osioon" />
						</label>
				
					</div>
					<br>
				</c:if>
		
			<div class="Demo-boot" style="padding-top: 15px;">
					<div class="btn-group" data-toggle="buttons">
						<c:forEach var="points" begin="0" end="${answer.question.maxPoints}">
							
							<!-- Jos kysymykselle on ennalta tehty valinta esim. muokattaessa 
									raporttia uudelleen, kyseinen valintanappi näkyy valittuna. -->
								<c:choose>
								<c:when test="${reportParts[editReportPartNumber].reportQuestionGroups[questionGroupCounter.index].answers[questionCounter.index].givenPoints == points}"> 
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
							<sf:radiobutton id="button" path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].givenPoints"
									value="${points}" /> &nbsp&nbsp${points}&nbsp&nbsp

							</label>
						</c:forEach> 
						 <c:choose>
								<c:when test="${reportParts[editReportPartNumber].reportQuestionGroups[questionGroupCounter.index].answers[questionCounter.index].givenPoints == -1}"> 
									<label class="btn btn-default active">
									</c:when>
								<c:otherwise>
									<label class="btn btn-default">
								</c:otherwise>
							</c:choose> 
							<sf:radiobutton id="button" path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].givenPoints" 
									value="-1" /> Ei valintaa
							
					</div>
				</div>
				
				<br>
				<h4>Huomioita:</h4>
				<sf:textarea rows="5" style="width:100%;" path="eportParts[${editReportPartNumber}].questionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].remarks" 
					value="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].remarks}" />
				<br><br>
			</c:if> 
		
		<!-- Text question -->
			<c:if test='${answer["class"] == "class fi.testcenter.domain.answer.TextAnswer"}'>
				
				
				<div class="checkbox" style="font-size: 1.2em;">
					<label>											
					<sf:checkbox value='true'
						path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].removeAnswerFromReport" label="Hylkää kysymys raportista" />
					
					</label>
					<br>
				</div>
				<c:if test="${loginRole == '[ROLE_ADMIN]' }">
								<div class="checkbox" style="font-size: 1.2em;">
								<label>											
								<sf:checkbox value='true'
									path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].highlightAnswer" label="Huomiot-osioon" />
								
								</label>
								
								</div>
								<br>
				</c:if>
					

				<c:choose>
					<c:when test="${answer.question.textAreaInput == true }">
						<sf:textarea rows="5" style="width:100%;" path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].answer" />
					</c:when>
					<c:otherwise>
						<sf:input class="form-control" path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].answer" />
					</c:otherwise>
				</c:choose>
				<br>
				
			</c:if>
		<!-- Cost listing question -->
  			<c:if test='${answer["class"] == "class fi.testcenter.domain.answer.CostListingAnswer"}'>
				
				<div class="checkbox" style="font-size: 1.2em;">
					<label>											
					<sf:checkbox value='true'
						path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].removeAnswerFromReport" label="Hylkää kysymys raportista" />
					
					</label>
					<br>
				</div>
					<c:forEach var="listQuestion" items="${answer.question.questionItems}" varStatus="costListingAnswerCounter">
						<h4>${listQuestion}</h4>
						<sf:input style="width: 5em" path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].answersIn[${costListingAnswerCounter.index}]" /> €
						<br>
					</c:forEach>
					<br>
				<h4><b>${answer.question.total}</b></h4>
				<sf:input style="width: 5em" path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].totalIn" /> €
				<br>
					<h4>Huomioita:</h4>
					<sf:textarea rows="5" style="width:100%;" path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].remarks" 
						value="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].remarks}" />
				<br><br>
			</c:if>  

		
		<!-- ListAndScoreImportantPoints -->
	   			<c:if test='${answer["class"] == "class fi.testcenter.domain.answer.ImportantPointsAnswer"}'>
	 			
				<div class="checkbox" style="font-size: 1.2em;">
					<label>											
					- <sf:checkbox value='true'
						path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].removeAnswerFromReport" label="Hylkää kysymys raportista" />
					 
					</label>
					<br>
				</div>

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
										<label class="btn btn-primary active">
										</c:when>
									<c:otherwise>
										<label class="btn btn-primary">
									</c:otherwise>
								</c:choose>  
							
 								<sf:radiobutton id="button" path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].answerItems[${questionItemCounter.index}].importance"
										value="${importanceNumber.index}" /> &nbsp&nbsp${importanceNumber.index}&nbsp&nbsp 
										</label>
							</c:forEach>
							

							<c:choose>
									<c:when test="${answer.answerItems[questionItemCounter.index].importance == -1}"> 
										<label class="btn btn-default active">
										</c:when>
									<c:otherwise>
										<label class="btn btn-default">
									</c:otherwise>
								</c:choose>  
								<sf:radiobutton id="button" path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].answerItems[${questionItemCounter.index}].importance"
										value="-1" /> Ei valintaa
								</label>
							 
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
										<label class="btn btn-primary active">
										</c:when>
									<c:otherwise>
										<label class="btn btn-primary">
									</c:otherwise>
								</c:choose>  
								<sf:radiobutton id="button" path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].answerItems[${questionItemCounter.index}].score"
										value="${score.index}" /> &nbsp&nbsp${score.index}&nbsp&nbsp 
										</label>
							</c:forEach>  
							<c:choose>
 									<c:when test="${answer.answerItems[questionItemCounter.index].score == -1}"> 
										<label class="btn btn-default active">
										</c:when>
									<c:otherwise>
										<label class="btn btn-default">
									</c:otherwise>
								</c:choose>   
								<sf:radiobutton id="button" path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].answerItems[${questionItemCounter.index}].score"
										value="-1" /> Ei valinta
										</label> 
							
							<br><br>
						</div>
						</div>
						</tr>
						
						</table>
					</div>

					</c:forEach>
					<br>
					<h4>Huomioita:</h4>
					<sf:textarea rows="5" style="width:100%;" path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].remarks" 
						value="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].remarks}" /> 
					<br><br>
					 
			</c:if>	    
			
<!-- Optional questions -->
  		<input type="hidden" id="optionalQuestionsAnswerIndex" name="optionalQuestionsAnswerIndex" />
  		<input type="hidden" id="addOptionalToQuestionGroup" name="addOptionalToQuestionGroup" />
  		
		<c:if test='${answer["class"] == "class fi.testcenter.domain.answer.OptionalQuestionsAnswer"}'>
			
			<c:set var="optionalQuestionsAnswer" value="${answer}" scope="request" />
			<c:set var="questionGroupCounter" value="${questionGroupCounter}" scope="request" />
			<c:set var="reportPartCounter" value="${reportPartCounter}" scope="request" />
			<c:set var="questionCounter" value="${questionCounter}" scope="request" />
			
			<jsp:include page="/WEB-INF/templates/report/editOptionalQuestions.jsp" />
						
			<a href="#" onclick="addOptionalQuestion(${questionGroupCounter.index}, ${questionCounter.index})" class="btn btn-primary"> Valitse kysymykset</a>
		
		</c:if> 
						
<!-- Questions loop end -->									
			</c:forEach> 
		</div>
		</div>
	</div>
<!-- QuestionGroup loop end -->	
	</c:forEach> 
	<br><br>
	</div>
				
	<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-save" style="text-decoration: none;"></span> Tallenna</button>
	<a class="btn btn-primary remove" href="#"><span class="glyphicon glyphicon-remove" style="text-decoration: none;"></span> Hylkää muutokset</a>
	
	<c:if test="${edit == 'TRUE'}">
		<a href="#" class="btn btn-large btn btn-danger deleteReport"><span class="glyphicon glyphicon-remove" style="text-decoration: none;"></span> Poista</a>
	</c:if>
	
	<br><br>
	<br>
</sf:form>
				
</div>

<script type="text/javascript">
            // When the document is ready
            $(document).ready(function () {
                
                $('#date').datepicker({
                   
                    language: "fi",
        			autoclose: true
              });  
            });
</script>
<script>
   function navigateToReportPart(reportPartIndex)
   {
	   document.getElementById("navigateToReportPart").value = reportPartIndex;
	   
      document.getElementById('editReportForm').submit();
   }

   function addOptionalQuestion(questionGroup, answerIndex)
   {
	 
	   document.getElementById("addOptionalToQuestionGroup").value = questionGroup;
	   document.getElementById("optionalQuestionsAnswerIndex").value = answerIndex;
		document.getElementById("editReportForm").submit();
		
	   }
</script>

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

 $(document).on("click", ".remove", function(e) {
     bootbox.dialog({
   	  message: "Hylkää muutokset?",
   	  title: "Vahvista",
   	  buttons: {
   	    cancel: {
   	      label: "Peruuta",
   	      className: "btn-primary",
   	      callback: function() {
   	        
   	      }
   	    },
   	    confirm: {
   	      label: "Hylkää muutokset",
   	      className: "btn-danger",
   	      callback: function() {
   	    	window.location.href = "/ProNaseva/"
   	      }
   	    }
   	  }
   	});
     });
 
</script>
		

		
		<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />