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
			<c:choose>
				<c:when test="${edit == 'TRUE'}">
					<span class="label label-warning">Muokkaus</span>
				</c:when>
				<c:otherwise>
					<span class="label label-warning">Kesken</span>
				</c:otherwise>
			</c:choose>
						
			<br><br>
			</div>
			<br><br>
			<sf:form modelAttribute="report" action="submitReport" method="post">

			<!-- Report part loop -->
			
			<c:set var="bootstrapPanelCounter" value="0" />
			
			<div class="panel-group" id="accordion">
			
			<c:forEach var="reportPart" items="${report.reportParts}" varStatus="reportPartCounter">
			<h3>${reportPart.title}</h3>
			<br>			

				<!-- QuestionGroup loop -->
						
	
					<c:forEach var="questionGroup" items="${reportPart.questionGroups}"
						varStatus="questionGroupCounter">
						
						<c:set var="bootstrapPanelCounter" value="${bootstrapPanelCounter + 1}" />
						
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

								<!-- Ensimmäisen kysymysryhmän luokka on "collapse start" jotta javascript 
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
									
									<c:forEach var="question" items="${questionGroup.questions}" varStatus="questionCounter">
										
										
										<!-- Multiple choice question -->
										
										<c:if test="${question.class == 'class fi.testcenter.domain.MultipleChoiceQuestion'}">
											<c:choose>
												<c:when test="${question.multipleSelectionsAllowed == true}">
														<h3>${questionCounter.count}. ${question.question}</h3>
														<sf:checkboxes path="${question.answer.chosenSelections}" itemValue="option" items="${question.options}" />
										
												</c:when>
												<c:otherwise>
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
																<sf:radiobutton id="button" path="reportParts[${reportPartCounter.index}].questionGroups[${questionGroupCounter.index}].questions[${questionCounter.index}].answer.chosenOptionIndex" 
																value="${optionsCounter.index}" /> ${option.radiobuttonText}
															</c:when>
															<c:otherwise>
																<sf:radiobutton id="button" path="reportParts[${reportPartCounter.index}].questionGroups[${questionGroupCounter.index}].questions[${questionCounter.index}].answer.chosenOptionIndex"
																value="${optionsCounter.index}" /> ${option.option}
															</c:otherwise>
															</c:choose>
														</label>
													</c:forEach> 
												</div>
											</div>
											
											</c:otherwise>
											</c:choose>
											
											<br>
											<h4>Huomioita:</h4>
											<sf:textarea rows="5" style="width:100%;" path="reportParts[${reportPartCounter.index}].questionGroups[${questionGroupCounter.index}].questions[${questionCounter.index}].answer.remarks" 
												value="${question.answer.remarks}" />
											<br><br>
										</c:if>

										<!--  Text area question -->
										<c:if
											test="${question.class == 'class fi.testcenter.domain.TextareaQuestion'}">
											<h3>${questionCounter.count}. ${question.question}</h3>
											<br>
											<sf:textarea rows="5" style="width:100%;" path="reportParts[${reportPartCounter.index}].questionGroups[${questionGroupCounter.index}].questions[${questionCounter.index}].answer.answer" 
												value="${question.answer.answer}" />
										</c:if>
										
										<!-- Text field question -->
										<c:if
											test="${question.class == 'class fi.testcenter.domain.TextfieldQuestion'}">
											<h3>${questionCounter.count}. ${question.question}</h3>
											<br>
											<sf:input type="text" style="width:100%;" path="reportParts[${reportPartCounter.index}].questionGroups[${questionGroupCounter.index}].questions[${questionCounter.index}].answer.answer" 
												value="${question.answer.answer}" /> 
										</c:if>
										
										
									</c:forEach> <!-- Questions end -->
									</div>
									</div>
									</div>
									
												
					</c:forEach> <!-- QuestionGroup end -->
					
					
					
					<br><br>
					</c:forEach> <!-- Report part end -->
					
				</div>
				
				<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok" style="text-decoration: none;"></span> Tallenna</button>
				<a class="btn btn-primary" href="/ProNaseva/"><span class="glyphicon glyphicon-remove" style="text-decoration: none;"></span> Hylkää muutokset</a>
				
				<c:if test="${edit == 'TRUE'}">
					<a href="#" class="btn btn-large btn btn-danger deleteReport"><span class="glyphicon glyphicon-remove" style="text-decoration: none;"></span> Poista</a>
				</c:if>
				
				<br><br>
				<br></sf:form>
				
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