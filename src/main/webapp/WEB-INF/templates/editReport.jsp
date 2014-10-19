<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/templates/includes/header.jsp" />  

 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

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
			<sf:form modelAttribute="formAnswers" action="submitReport" method="post">

			<!-- Report part loop -->
			
			<c:set var="bootstrapPanelCounter" value="0" />
			
			
			<c:set var="textAnswerCounter" value="0" />
			<c:set var="multipleChoiceAnswerCounter" value="0" />
			<c:forEach var="reportPart" items="${reportTemplate.reportParts}" varStatus="reportPartCounter">
			<h3>${reportPart.title}</h3>
			<br>			

				<!-- QuestionGroup loop -->
						
	
					<c:forEach var="questionGroup" items="${reportPart.questionGroups}" varStatus="questionGroupCounter">
						
						<c:set var="bootstrapPanelCounter" value="${bootstrapPanelCounter + 1}" />

								<h4 class="panel-title">
									<a
										style="font-size: 1.5em; text-decoration: none; display: block;"
										data-toggle="collapse" data-parent="#accordion"
										href="#panel${bootstrapPanelCounter}">${questionGroup.title}
									</a>
								</h4>
						

								<!-- Ensimmäisen kysymysryhmän luokka on "collapse start" jotta javascript 
									tietää mihin nostaa näkymä avattaessa Accordion Menun paneeleja. -->
								
								
									<!-- Questions loop -->
									
									<c:forEach var="question" items="${questionGroup.questions}" varStatus="questionCounter">
													
										<c:set var="formAnswerListIndex" value="${(reportPartCounter.count * questionGroupCounter.count * questionCounter.count) - 1}" />							
										
										<!-- Text field question -->
										<c:if test="${question.class == 'class fi.testcenter.domain.TextfieldQuestion'}">
											<h3>${questionCounter.count}. ${question.question}</h3>
											<br>
											<sf:input path="formTextAnswers[${textAnswerCounter}].answer" />
											<c:set var="textAnswerCounter" value="${textAnswerCounter + 1}" />
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
				<a class="btn btn-primary remove" href="#"><span class="glyphicon glyphicon-remove" style="text-decoration: none;"></span> Hylkää muutokset</a>
				
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