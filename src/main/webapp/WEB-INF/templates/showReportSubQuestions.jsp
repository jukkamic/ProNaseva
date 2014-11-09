<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


	
<!-- Questions loop -->


<c:forEach var="subQuestion" items="${mainQuestion.subQuestions}" varStatus="subQuestionCounter">

	

<!-- Multiple choice question -->
<c:if
	test="${subQuestion['class'] == 'class fi.testcenter.domain.MultipleChoiceQuestion'}">

	
	<h3>${subQuestion.question}</h3>
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
			<c:forEach var="option" items="${subQuestion.options}" varStatus="optionsCounter">
		
				
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
<c:if
	test="${subQuestion['class'] == 'class fi.testcenter.domain.TextQuestion'}">
	<h3>${subQuestion.question}</h3>
	<c:if test="${loginRole == '[ROLE_ADMIN]' }">
			<div class="checkbox" style="font-size: 1.2em;">
			<label>											
			<sf:checkbox value='true'
				path="answers[${answerIndexCounter}].highlightAnswer" label="Huomiot-osioon" />
			
			</label>
			
			</div>
			<br>
	</c:if>
	<br>
	<p style="font-size: 1.2em;">${report.answers[answerIndexCounter].answer}</p>
</c:if>

	<!-- Cost listing question -->
	<c:if test='${subQuestion["class"] == "class fi.testcenter.domain.CostListingQuestion"}'>
		<h3>${subQuestion.questionTopic}</h3>
			<c:if test="${loginRole == '[ROLE_ADMIN]' }">
					<div class="checkbox" style="font-size: 1.2em;">
					<label>											
					<sf:checkbox value='true'
						path="answers[${answerIndexCounter}].highlightAnswer" label="Huomiot-osioon" />
					
					</label>
					
					</div>
					<br>
			</c:if>
			
			<c:forEach var="listQuestion" items="${subQuestion.questions}" varStatus="costListingAnswerCounter">
				
				<h4>${listQuestion}</h4>
				<c:set var="listingAnswer" value="${report.answers[answerIndexcounter]}" />
				<p style="font-size: 1.2em;">${report.answers[answerIndexCounter].answers[costListingAnswerCounter.index]} €</p>
				<br>
			</c:forEach>
				<h4><b>${subQuestion.total}</b></h4>
				<p style="font-size: 1.2em;">${report.answers[answerIndexCounter].total} €</p>
			<br>
	</c:if>

<c:set var="answerIndexCounter" value="${answerIndexCounter + 1}" scope="request" />

</c:forEach>