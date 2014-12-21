<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    
    
<h3>Tiivistelmä</h3>
<br>
<div class="panel panel-default">
<div class="panel-heading">
	<h4 class="panel-title">
		<a style="font-size: 1.5em; text-decoration: none; display: block;"
			data-toggle="collapse" data-parent="#accordion"
			href="#${bootstrapPanelCounter}">Huomionarvoiset vastaukset
				</a>
	</h4>
</div> 
	
<div id="${bootstrapPanelCounter}" class="panel-collapse collapse start"> 
<div class="panel-body">

<c:forEach var="reportPart" items="${report.reportParts}">
<c:forEach var="questionGroup" items="${reportPart.reportQuestionGroups}">
<c:forEach var="answer" items="${questionGroup.answers}">
			
		
	<!-- OptionalQuestionsAnswer -->
			
			<c:if test='${answer["class"] == "class fi.testcenter.domain.answer.OptionalQuestionsAnswer"}'>
							
				<c:set var="optionalAnswer" value="${answer}" scope="request" />
				<jsp:include page="/WEB-INF/templates/report/showOptionalReportHighlights.jsp" />
			</c:if>
			
	<c:if test="${answer.highlightAnswer == 'true' }">
		<div style="border-bottom: 3px solid #eee;">
		<c:if test="${answer.reportQuestionGroup.reportPart.reportTemplatePart.title != reportPartTitle}">
			<h3>${answer.reportQuestionGroup.reportPart.reportTemplatePart.title}</h3>
			<c:set var="reportPartTitle" value="${answer.reportQuestionGroup.reportPart.reportTemplatePart.title}" scope="request" />
		</c:if>
		
		<c:if test="${answer.reportQuestionGroup.reportTemplateQuestionGroup.title != questionGroupTitle}">
			<h3>${answer.reportQuestionGroup.questionGroupOrderNumber}. ${answer.reportQuestionGroup.reportTemplateQuestionGroup.title}</h3>
			<c:set var="questionGroupTitle" value="${answer.reportQuestionGroup.reportTemplateQuestionGroup.title}" scope="request" />
		</c:if>

		
<!-- Multiple choice answer -->
		
		<c:if test="${answer['class'] == 'class fi.testcenter.domain.answer.MultipleChoiceAnswer'}">
			<h3>${answer.reportQuestionGroup.questionGroupOrderNumber}.${answer.answerOrderNumber}.
			<c:if test="${answer.subquestionAnswerOrderNumber != null and answer.subquestionAnswerOrderNumber != 0}">
				${answer.subquestionAnswerOrderNumber}.
			</c:if>									
			
			${answer.question.question}</h3>
		
					<div class="Demo-boot" style="padding-top: 15px;">
						<div class="btn-group" data-toggle="buttons">
							<c:forEach var="option" items="${answer.question.options}" varStatus="optionsCounter">
																						
								<!-- Jos MultipleChoiceOption-oliolle on asetettu pitkää valintanapin tekstiä
										varten erillinen radiobuttonText, jossa napin teksti on jaettu kahdelle 
										riville <br> tägillä, näytetään radiobuttonText, muuten option teksti jossa 
										ei ole tägejä -->
								<c:choose>
									<c:when test="${option.radiobuttonText != null }">
										<c:choose>
											<c:when test="${answer.chosenOptionIndex == optionsCounter.index}">
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
											<c:when test="${answer.chosenOptionIndex == optionsCounter.index}">
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
					
					<c:if test="${answer.remarks != '' and reportHighlight.answer.remarks != null}">
						<br>
						<h4>Huomioita:</h4>
						<p style="font-size: 1.2em;">${answer.remarks}</p>
					</c:if>
					<br><br>
				</c:if>
		
<!-- Points question -->
<c:if test="${answer['class'] == 'class fi.testcenter.domain.answer.PointsAnswer'}">

	<h3>${answer.reportQuestionGroup.questionGroupOrderNumber}.${answer.answerOrderNumber}.
	<c:if test="${answer.subquestionAnswerOrderNumber != null and answer.subquestionAnswerOrderNumber != 0}">
		${answer.subquestionAnswerOrderNumber}.
</c:if>									
	
	${answer.question.question}</h3>
	
<div class="Demo-boot" style="padding-top: 15px;">
		<div class="btn-group" data-toggle="buttons">
			<c:forEach var="points" begin="0" end="${answer.question.maxPoints}">
					
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
	<h4>Huomioita:</h4>
	<p style="font-size: 1.2em;">${answer.remarks}</p>
	
	<br><br>
</c:if>

<!-- Text answer -->
		
		<c:if test="${answer['class'] == 'class fi.testcenter.domain.answer.TextAnswer'}">
			<h3>${answer.reportQuestionGroup.questionGroupOrderNumber}.${answer.answerOrderNumber}.
			<c:if test="${answer.subquestionAnswerOrderNumber != null and answer.subquestionAnswerOrderNumber != 0}">
				${answer.subquestionAnswerOrderNumber}.
			</c:if>									
			${answer.question.question}</h3>
			<p style="font-size: 1.2em;">${answer.answer}</p>
			<br>
		</c:if>
		
		<!-- Cost listing question -->
			<c:if test='${answer["class"] == "class fi.testcenter.domain.answer.CostListingAnswer"}'>
			<h3>${answer.reportQuestionGroup.questionGroupOrderNumber}.${answer.answerOrderNumber}.
			<c:if test="${answer.subquestionAnswerOrderNumber != null and answer.subquestionAnswerOrderNumber != 0}">
				${answer.subquestionAnswerOrderNumber}.
			</c:if>									
			${answer.question.question}</h3>
														
			<c:forEach var="listQuestion" items="${answer.question.questionItems}" varStatus="costListingAnswerCounter">
						
			<h4>${listQuestion}</h4>
						<c:set var="listingAnswer" value="${answer.answers}" />
						<p style="font-size: 1.2em;">${answer.answers[costListingAnswerCounter.index]} €</p>
						<br>
					</c:forEach>
						<h4><b>${answer.question.total}</b></h4>
						<p style="font-size: 1.2em;">${answer.total} €</p>
					<br>
			</c:if>

		
	</div>
	</c:if>
	</c:forEach>
	</c:forEach>
	</c:forEach>

			</div>
			</div>

</div> 
<br>