<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


	
<!-- Questions loop -->

<p>SubQuestions</p>


<c:forEach var="listedSubQuestion" items="${mainQuestion.subQuestions}" varStatus="subQuestionCounter">
<c:set var="subQuestion" value="${listedSubQuestion.question}" />
	

<!-- Multiple choice question -->
<c:if
	test="${subQuestion.class == 'class fi.testcenter.domain.MultipleChoiceQuestion'}">
								
	<c:set var="maxPointsForQuestion" value="0" />	
	<c:set var="scoredQuestions" value="TRUE" />			

	
	<h3>${subQuestion.question}</h3>
	<div class="Demo-boot" style="padding-top: 15px;">
		<div class="btn-group" data-toggle="buttons">
			<c:forEach var="option" items="${subQuestion.options}" varStatus="optionsCounter">
																																																	
				<c:if test="${option.points > maxPointsForQuestion}">
						<c:set var="maxPointsForQuestion" value="${option.points}" />
				</c:if>
				
				<!-- Jos MultipleChoiceOption-oliolle on asetettu pitkää valintanapin tekstiä
						varten erillinen radiobuttonText, jossa napin teksti on jaettu kahdelle 
						riville <br> tägillä, näytetään radiobuttonText, muuten option teksti jossa 
						ei ole tägejä -->
				<c:choose>
					<c:when test="${option.radiobuttonText != null }">
						<c:choose>
							<c:when test="${subQuestion.answer.chosenOptionIndex == optionsCounter.index}">
								<button class="btn btn-large btn-primary" type="button">
									${option.radiobuttonText}
																												
									<c:set var="totalScore" value="${totalScore + option.points}" />
																																														
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
							<c:when test="${subQuestion.answer.chosenOptionIndex == optionsCounter.index}">
								<button class="btn btn-large btn-primary" type="button">
									${option.option}
									<c:set var="totalScore" value="${totalScore + option.points}" />
									
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
			
			<c:set var="maxTotalScore" value="${maxTotalScore + maxPointsForQuestion}" />
																	
		</div>
	</div>
	<br>
	<h4>Huomioita:</h4>
	<p style="font-size: 1.2em;">${subQuestion.answer.remarks}</p>
	
	<br><br>
</c:if>

<!--  Text area question -->
<c:if
	test="${subQuestion.class == 'class fi.testcenter.domain.TextareaQuestion'}">
	<h3>${subQuestion.question}</h3>
	<br>
	<p style="font-size: 1.2em;">${subQuestion.answer}</p>
</c:if>

<!-- Text field question -->
<c:if
	test="${subQuestion.class == 'class fi.testcenter.domain.TextfieldQuestion'}">
	<h3>${subQuestion.question}</h3>
	<br>
	<p style="font-size: 1.2em;">${subQuestion.answer}</p>
</c:if> 

</c:forEach>