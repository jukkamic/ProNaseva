<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


	
<!-- Questions loop -->


<c:forEach var="subQuestion" items="${mainQuestion.subQuestions}" varStatus="subQuestionCounter">

	

<!-- Multiple choice question -->
<c:if
	test="${subQuestion['class'] == 'class fi.testcenter.domain.question.MultipleChoiceQuestion'}">

	
	<h3>${subQuestion.question}</h3>
	<c:if test="${report.answers[answerIndexCounter].removeAnswerFromReport == 'true'}">
		<div class="checkbox" style="font-size: 1.2em;">
		<label>											
				<sf:checkbox value='true'
					path="answers[${answerIndexCounter}].removeAnswerFromReport" disabled="true" label="Hylkää kysymys raportista" />
		
			</label>
			<br>
		</div>
	</c:if>
	<c:if test="${report.answers[answerIndexCounter].removeAnswerFromReport == 'false'}">
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
</c:if>

<!-- Points question -->

<c:if test='${question["class"] == "class fi.testcenter.domain.question.PointsQuestion"}'>
																		
	${subQuestion.question}</h3>
	<c:if test="${report.answers[answerIndexCounter].removeAnswerFromReport == 'true'}">
		<div class="checkbox" style="font-size: 1.2em;">
		<label>											
				<sf:checkbox value='true'
					path="answers[${answerIndexCounter}].removeAnswerFromReport" disabled="true" label="Hylkää kysymys raportista" />
	
			</label>
			<br>
		</div>
	</c:if>
	<c:if test="${report.answers[answerIndexCounter].removeAnswerFromReport == 'false'}">
	<c:if test="${loginRole == '[ROLE_ADMIN]' }">
			<div class="checkbox" style="font-size: 1.2em;">
			<label>											
			<sf:checkbox value='true' path="answers[${answerIndexCounter}].highlightAnswer" label="Huomiot-osioon" />  
			
			</label>
			
			</div>
			<br>
	</c:if>
								
<div class="Demo-boot" style="padding-top: 15px;">
		<div class="btn-group" data-toggle="buttons">
			<c:forEach var="points" begin="0" end="${subQuestion.maxPoints}">
					
				<c:choose>
					<c:when test="${report.answers[answerIndexCounter].givenPoints == points}">
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
	<p style="font-size: 1.2em;">${report.answers[answerIndexCounter].remarks}</p>
	<br><br>
	</c:if>
</c:if>


<!--  Text question -->
<c:if
	test="${subQuestion['class'] == 'class fi.testcenter.domain.question.TextQuestion'}">
	<h3>${subQuestion.question}</h3>
	<c:if test="${report.answers[answerIndexCounter].removeAnswerFromReport == 'true'}">
			<div class="checkbox" style="font-size: 1.2em;">
			<label>											
				<sf:checkbox value='true'
					path="answers[${answerIndexCounter}].removeAnswerFromReport" disabled="true" label="Hylkää kysymys raportista" />

			</label>
			<br>
		</div>
	</c:if>
		<c:if test="${report.answers[answerIndexCounter].removeAnswerFromReport == 'false'}">
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
</c:if>



<c:set var="answerIndexCounter" value="${answerIndexCounter + 1}" scope="request" />

</c:forEach>