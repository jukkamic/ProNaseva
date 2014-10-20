<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


	
<!-- Questions loop -->


<c:forEach var="listedSubQuestion" items="${mainQuestion.subQuestions}" varStatus="subQuestionCounter">
<c:set var="subQuestion" value="${listedSubQuestion.question}" />
	

<!-- Multiple choice question -->
<c:if
	test="${subQuestion.class == 'class fi.testcenter.domain.MultipleChoiceQuestion'}">

	
	<h3>${subQuestion.question}</h3>
	<div class="Demo-boot" style="padding-top: 15px;">
		<div class="btn-group" data-toggle="buttons">
			<c:forEach var="option" items="${subQuestion.options}" varStatus="optionsCounter">
																																																	
				<c:if test="${option.points > maxPointsForQuestion}">
						
				</c:if>
				
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
<c:if
	test="${subQuestion.class == 'class fi.testcenter.domain.TextQuestion'}">
	<h3>${subQuestion.question}</h3>
	<br>
	<p style="font-size: 1.2em;">${report.answers[answerIndexCounter].answer}</p>
</c:if>


</c:forEach>