<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="reportPartTitle" value="" scope="request" />
<c:forEach var="answer" items="${optionalAnswer.optionalAnswers}">
<c:if test="${answer.highlightAnswer == 'true' }">

<div style="border-bottom: 3px solid #eee;">
<c:if test="${answer.optionalQuestionsAnswer.reportQuestionGroup.reportPart.reportTemplatePart.title != reportPartTitle}">
	<h3>${answer.optionalQuestionsAnswer.reportQuestionGroup.reportPart.reportTemplatePart.title}</h3>
	<c:set var="reportPartTitle" value="${answer.optionalQuestionsAnswer.reportQuestionGroup.reportPart.reportTemplatePart.title}" scope="request" />
</c:if>

<c:if test="${answer.optionalQuestionsAnswer.reportQuestionGroup.reportTemplateQuestionGroup.title != questionGroupTitle}">
	<h3>${answer.optionalQuestionsAnswer.reportQuestionGroup.questionGroupOrderNumber}. ${answer.optionalQuestionsAnswer.reportQuestionGroup.reportTemplateQuestionGroup.title}</h3>
	<c:set var="questionGroupTitle" value="${answer.optionalQuestionsAnswer.reportQuestionGroup.reportTemplateQuestionGroup.title}" scope="request" />
</c:if>
		

    <!-- Multiple choice answer -->
		
		<c:if test="${answer['class'] == 'class fi.testcenter.domain.answer.MultipleChoiceAnswer'}">
			<h3>${optionalAnswer.optionalQuestionsAnswer.reportQuestionGroup.questionGroupOrderNumber}.${answer.answerOrderNumber}.
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
												<button class="btn btn-large btn-selectedOption disabled" disabled type="button">
													${option.radiobuttonText}
																																													
												</button>
											</c:when>
											<c:otherwise>
												<button class="btn btn-large btn-showSelections" disabled type="button">
													${option.radiobuttonText}
												</button>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${answer.chosenOptionIndex == optionsCounter.index}">
												<button class="btn btn-large btn-selectedOption disabled" disabled type="button">
													${option.option}
													
												</button>
											</c:when>
											<c:otherwise>
												<button class="btn btn-large btn-showSelections" disabled type="button">
													${option.option}
												</button>
											</c:otherwise>
										</c:choose>	
									</c:otherwise>
								</c:choose>														
							</c:forEach> 
							
																		
						</div>
					</div>
					
					<c:if test="${answer.remarks != '' and answer.remarks != null}">
						<br>
						<h4>Huomioita:</h4>
						<p style="font-size: 1.2em;">${answer.remarks}</p>
					</c:if>
					<br><br>
				</c:if>
		
<!-- Points question -->
<c:if test="${answer['class'] == 'class fi.testcenter.domain.answer.PointsAnswer'}">

	<h3>${optionalAnswer.reportQuestionGroup.questionGroupOrderNumber}.${answer.answerOrderNumber}.
	<c:if test="${answer.subquestionAnswerOrderNumber != null and answer.subquestionAnswerOrderNumber != 0}">
		${answer.subquestionAnswerOrderNumber}.
</c:if>									
	
	${answer.question.question}</h3>
	
<div class="Demo-boot" style="padding-top: 15px;">
		<div class="btn-group" data-toggle="buttons">
			<c:forEach var="points" begin="0" end="${answer.question.maxPoints}">
					
				<c:choose>
					<c:when test="${answer.givenPoints == points}">
						<button class="btn btn-large btn-selectedOption disabled" disabled type="button">
							${points}
							
						</button>
					</c:when>
					<c:otherwise>
						<button class="btn btn-large btn-showSelections" disabled type="button">
							${points}
						</button>
					</c:otherwise>
				</c:choose>	
														
			</c:forEach> 
		</div>
	</div>

	<c:if test="${answer.remarks != '' and answer.remarks != null}">
		<br>
		<h4>Huomioita:</h4>
		<p style="font-size: 1.2em;">${answer.remarks}</p>
	</c:if>
	<br><br>
</c:if>

<!-- Text answer -->
		
		<c:if test="${answer['class'] == 'class fi.testcenter.domain.answer.TextAnswer'}">
			<h3>${optionalAnswer.reportQuestionGroup.questionGroupOrderNumber}.${answer.answerOrderNumber}.
			<c:if test="${answer.subquestionAnswerOrderNumber != null and answer.subquestionAnswerOrderNumber != 0}">
				${answer.subquestionAnswerOrderNumber}.
			</c:if>									
			${answer.question.question}</h3>
			<p style="font-size: 1.2em;">${answer.answer}</p>
			<br>
		</c:if>
		
		<!-- Cost listing question -->
			<c:if test='${answer["class"] == "class fi.testcenter.domain.answer.CostListingAnswer"}'>
			<h3>${optionalAnswer.reportQuestionGroup.questionGroupOrderNumber}.${answer.answerOrderNumber}.
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
				<c:if test="${answer.remarks != '' and answer.remarks != null}">
					<br>
					<h4>Huomioita:</h4>
					<p style="font-size: 1.2em;">${answer.remarks}</p>
				</c:if>
			</c:if>
			</div>
		</c:if>
	
</c:forEach>
			