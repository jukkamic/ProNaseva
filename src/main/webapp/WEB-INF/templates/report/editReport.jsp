<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
			
<sf:form id="editReportForm" modelAttribute="report" action="saveReport" method="post">


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
			<sf:input path="reportDate" name="date" class="datepicker" id="date" value="${report.reportDate}"/>
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
				<c:forEach var="navbarReportPart" items="${report.reportTemplate.reportParts}" varStatus="navbarCounter">
			  		<c:if test="${navbarCounter.index == editReportPartNumber}">
			  			<li role="presentation" class="active"><a onclick="navigateToReportPart(${navbarCounter.index});">${navbarReportPart.title}</a></li>
			  		</c:if>
			  		<c:if test="${navbarCounter.index != editReportPartNumber}">
			  			<li role="presentation"><a onclick="navigateToReportPart(${navbarCounter.index});">${navbarReportPart.title}</a></li>
			  		</c:if>
				</c:forEach>
			</ul>
						
			<br>
			

<!-- Report part loop -->
			
	<c:set var="bootstrapPanelCounter" value="0" />
	
	<div class="panel-group" id="accordion">

	<c:set var="answerIndexCounter" value="${initialAnswerIndexCounter}" scope="request" />

	
	<c:set var="reportPart" value="${report.reportTemplate.reportParts[editReportPartNumber]}" />
	
	<h3>${reportPart.title}</h3>
	<br>			

<!-- QuestionGroup loop -->
						
	
	<c:forEach var="questionGroup" items="${reportPart.questionGroups}" varStatus="questionGroupCounter">
		
		<c:set var="bootstrapPanelCounter" value="${bootstrapPanelCounter + 1}" />
		
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a
						style="font-size: 1.5em; text-decoration: none; display: block;"
						data-toggle="collapse" data-parent="#accordion"
						href="#panel${bootstrapPanelCounter}">${questionGroup.title}
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
				
								
<!-- Questions loop -->
									
			<c:forEach var="question" items="${questionGroup.questions}" varStatus="questionCounter">
										
										
		<!-- Multiple choice question -->
										
		<c:if test='${question["class"] == "class fi.testcenter.domain.question.MultipleChoiceQuestion"}'>
		<h3>${questionCounter.count}. ${question.question}</h3>
		<c:if test="${loginRole == '[ROLE_ADMIN]' }">
			<div class="checkbox" style="font-size: 1.2em;">
			<label>											
			<sf:checkbox value='true'
				path="answers[${answerIndexCounter}].highlightAnswer" label="Huomiot-osioon" />
			
			</label>
			
			</div>
			<br>
		</c:if>
		<c:choose>
			<c:when test="${question.multipleSelectionsAllowed == true}">
				<c:forEach var="option" items="${question.options}">
					<label class="checkbox">											
						<sf:checkbox value="${option.option}" 
							path="answers[${answerIndexCounter}].chosenSelections" label="${option.option}" />
					</label>
					<br>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<div class="Demo-boot" style="padding-top: 15px;">
					<div class="btn-group" data-toggle="buttons">
						<c:forEach var="option" items="${question.options}" varStatus="optionsCounter">
							
							<!-- Jos kysymykselle on ennalta tehty valinta esim. muokattaessa 
								raporttia uudelleen, kyseinen valintanappi näkyy valittuna. -->
							<c:choose>
							<c:when test="${report.answers[answerIndexCounter].chosenOptionIndex == optionsCounter.index}"> 
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
									<sf:radiobutton id="button" path="answers[${answerIndexCounter}].chosenOptionIndex" 
									value="${optionsCounter.index}" /> ${option.radiobuttonText}
								</c:when>
								<c:otherwise>
									<sf:radiobutton id="button" path="answers[${answerIndexCounter}].chosenOptionIndex"
									value="${optionsCounter.index}" /> ${option.option}
								</c:otherwise>
								</c:choose>
							</label>
						</c:forEach> 
						 <c:choose>
								<c:when test="${report.answers[answerIndexCounter].chosenOptionIndex == -1}"> 
									<label class="btn btn-default active">
									</c:when>
								<c:otherwise>
									<label class="btn btn-default">
								</c:otherwise>
							</c:choose> 
							<sf:radiobutton id="button" path="answers[${answerIndexCounter}].chosenOptionIndex" 
									value="-1" /> Ei valintaa
							
					</div>
				</div>
				</c:otherwise>
				</c:choose> 
			
				<br>
				<h4>Huomioita:</h4>
				<sf:textarea rows="5" style="width:100%;" path="answers[${answerIndexCounter}].remarks" 
					value="report.answers[${answerIndexCounter}].remarks}" />
				<br><br>
			</c:if> 
		
		<!-- Points question -->
			<c:if test='${question["class"] == "class fi.testcenter.domain.question.PointsQuestion"}'>
			<h3>${questionCounter.count}. ${question.question}</h3>
				<c:if test="${loginRole == '[ROLE_ADMIN]' }">
					<div class="checkbox" style="font-size: 1.2em;">
						<label>											
							<sf:checkbox value='true'
								path="answers[${answerIndexCounter}].highlightAnswer" label="Huomiot-osioon" />
						</label>
				
					</div>
					<br>
				</c:if>
		
		
			<div class="Demo-boot" style="padding-top: 15px;">
					<div class="btn-group" data-toggle="buttons">
						<c:forEach var="points" begin="0" end="${question.maxPoints}">
							
							<!-- Jos kysymykselle on ennalta tehty valinta esim. muokattaessa 
									raporttia uudelleen, kyseinen valintanappi näkyy valittuna. -->
								<c:choose>
								<c:when test="${report.answers[answerIndexCounter].givenPoints == points}"> 
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
							<sf:radiobutton id="button" path="answers[${answerIndexCounter}].givenPoints"
									value="${points}" /> ${points}

							</label>
						</c:forEach> 
						 <c:choose>
								<c:when test="${report.answers[answerIndexCounter].givenPoints == -1}"> 
									<label class="btn btn-default active">
									</c:when>
								<c:otherwise>
									<label class="btn btn-default">
								</c:otherwise>
							</c:choose> 
							<sf:radiobutton id="button" path="answers[${answerIndexCounter}].givenPoints" 
									value="-1" /> Ei valintaa
							
					</div>
				</div>
				
				<br>
				<h4>Huomioita:</h4>
				<sf:textarea rows="5" style="width:100%;" path="answers[${answerIndexCounter}].remarks" 
					value="report.answers[${answerIndexCounter}].remarks}" />
				<br><br>
			</c:if> 
		
		<!-- Text question -->
			<c:if test='${question["class"] == "class fi.testcenter.domain.question.TextQuestion"}'>
				
				<h3>${questionCounter.count}. ${question.question}</h3>
				<c:if test="${loginRole == '[ROLE_ADMIN]' }">
								<div class="checkbox" style="font-size: 1.2em;">
								<label>											
								<sf:checkbox value='true'
									path="answers[${answerIndexCounter}].highlightAnswer" label="Huomiot-osioon" />
								
								</label>
								
								</div>
								<br>
				</c:if>
				
				<c:choose>
					<c:when test="${question.textAreaInput == true }">
						<sf:textarea rows="5" style="width:100%;" path="answers[${answerIndexCounter}].answer" />
					</c:when>
					<c:otherwise>
						<sf:input class="form-control" path="answers[${answerIndexCounter}].answer" />
					</c:otherwise>
				</c:choose>
				<br>
				
			</c:if>
		<!-- Cost listing question -->
			<c:if test='${question["class"] == "class fi.testcenter.domain.question.CostListingQuestion"}'>
				<h3>${questionCounter.count}. ${question.questionTopic}</h3>
					<c:forEach var="listQuestion" items="${question.questions}" varStatus="costListingAnswerCounter">
						<h4>${listQuestion}</h4>
						<sf:input style="width: 5em" path="answers[${answerIndexCounter}].answers[${costListingAnswerCounter.index}]" /> €
						<br>
					</c:forEach>
					<br>
				<h4><b>${question.total}</b></h4>
				<sf:input style="width: 5em" path="answers[${answerIndexCounter}].total" /> €
			</c:if>

		
		<!-- ListAndScoreImportantPoints -->
			<c:if test='${question["class"] == "class fi.testcenter.domain.question.ImportantPointsQuestion"}'>
				<h3>${questionCounter.count}. ${question.question}</h3>
				<c:if test="${loginRole == '[ROLE_ADMIN]' }">
									<div class="checkbox" style="font-size: 1.2em;">
									<label>											
									<sf:checkbox value='true'
										path="answers[${answerIndexCounter}].highlightAnswer" label="Huomiot-osioon" />
									
									</label>
									
									</div>
									<br>
					</c:if>

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
									<c:when test="${report.answers[answerIndexCounter].answerItems[questionItemCounter.index].importance == importanceNumber.index}"> 
										<label class="btn btn-primary active">
										</c:when>
									<c:otherwise>
										<label class="btn btn-primary">
									</c:otherwise>
								</c:choose>  
								<sf:radiobutton id="button" path="answers[${answerIndexCounter}].answerItems[${questionItemCounter.index}].importance"
										value="${importanceNumber.index}" /> ${importanceNumber.index} 
										</label>
							</c:forEach>
							<c:choose>
									<c:when test="${report.answers[answerIndexCounter].answerItems[questionItemCounter.index].importance == -1}"> 
										<label class="btn btn-default active">
										</c:when>
									<c:otherwise>
										<label class="btn btn-default">
									</c:otherwise>
								</c:choose>  
								<sf:radiobutton id="button" path="answers[${answerIndexCounter}].answerItems[${questionItemCounter.index}].importance"
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
									<c:when test="${report.answers[answerIndexCounter].answerItems[questionItemCounter.index].score == score.index}"> 
										<label class="btn btn-primary active">
										</c:when>
									<c:otherwise>
										<label class="btn btn-primary">
									</c:otherwise>
								</c:choose>  
								<sf:radiobutton id="button" path="answers[${answerIndexCounter}].answerItems[${questionItemCounter.index}].score"
										value="${score.index}" /> ${score.index} 
										</label>
							</c:forEach>
								<c:choose>
									<c:when test="${report.answers[answerIndexCounter].answerItems[questionItemCounter.index].score == -1}"> 
										<label class="btn btn-default active">
										</c:when>
									<c:otherwise>
										<label class="btn btn-default">
									</c:otherwise>
								</c:choose>  
								<sf:radiobutton id="button" path="answers[${answerIndexCounter}].answerItems[${questionItemCounter.index}].score"
										value="-1" /> Ei valinta
										</label>
							
							<br><br>
						</div>
						</div>
						</tr>
						
						</table>
					</div>
					</c:forEach>
					
						
			</c:if>	
		<!-- Subquestions --> 
			
			<c:set var="answerIndexCounter" value="${answerIndexCounter + 1}" scope="request" />
			
			<c:if test="${not empty question.subQuestions}">
				
				<c:set var="mainQuestion" value="${question}" scope="request" />
				<div style="margin-left: 3em;">
					<jsp:include page="/WEB-INF/templates/report/editReportSubQuestions.jsp" />
				</div>	
				<br>			
			</c:if>
		
		<c:set var="questionCount" value="${questionCounter.count + 1}" scope="request" />
						
<!-- Questions loop end -->									
			</c:forEach> 
		
		
		
		
	<!-- Optional questions -->
	
		<input type="hidden" id="addQuestionToReportPart" name="addQuestionToReportPart" value="${editReportPartNumber}"/>
		<input type="hidden" id="addQuestionToGroup" name="addQuestionToGroup" />
		<c:if test='${not empty questionGroup.optionalQuestions}'>
			
			<c:set var="optionalQuestionsAnswer" value="${report.answers[answerIndexCounter]}" scope="request" />
			
			<jsp:include page="/WEB-INF/templates/report/editOptionalQuestions.jsp" />
			<c:set var="answerIndexCounter" value="${answerIndexCounter + 1}" scope="request" />
			
			<a href="#" onclick="addOptionalQuestion(${questionGroupCounter.index})" class="btn btn-primary"> Lisää kysymys</a>
		
		</c:if>
		</div>
		</div>
	</div>
<!-- QuestionGroup loop end -->	
	</c:forEach> 
	<br><br>
	</div>
				
	<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-save" style="text-decoration: none;"></span> Tallenna</button>
	<a class="btn btn-primary remove" href="/ProNaseva"><span class="glyphicon glyphicon-remove" style="text-decoration: none;"></span> Hylkää muutokset</a>
	
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
$("[data-toggle='buttons'] .btn").live('click', function(evt){
    $(this).removeClass('active');
});
</script>


<script>
   function navigateToReportPart(reportPartIndex)
   {
      
      document.getElementById('editReportForm').submit();
   }

   function addOptionalQuestion(questionGroupIndex)
   {
	   
		document.getElementById("addQuestionToGroup").value = questionGroupIndex;
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