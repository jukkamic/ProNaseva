<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>



	
<!-- Questions loop -->



<c:forEach var="subQuestion" items="${mainQuestion.subQuestions}" varStatus="subQuestionCounter">
	
	<!-- Multiple choice question -->

	<c:if test='${subQuestion["class"] == "class fi.testcenter.domain.question.MultipleChoiceQuestion"}'>
	
	
					<h3>${subQuestion.question}</h3>
					<c:if test="${loginRole == '[ROLE_ADMIN]' }">
							<label class="checkbox">											
								<sf:checkbox value='true' path="answers[${answerIndexCounter}].highlightAnswer" label="Huomiot-osioon" />
							</label>
						<br>
					</c:if>
					
						
		<c:choose>
			<c:when test="${subQuestion.multipleSelectionsAllowed == true}">
					<c:forEach var="option" items="${subQuestion.options}">
						<label class="checkbox" style="">											
						<sf:checkbox value="${option.option}" 
							path="answers[${answerIndexCounter}].chosenSelections" 
							label="${option.option}" />
						</label>
						<br>
					</c:forEach>
			</c:when>
	<c:otherwise>
		
		<div class="Demo-boot" style="padding-top: 15px;">
			<div class="btn-group" data-toggle="buttons">
				<c:forEach var="option" items="${subQuestion.options}" varStatus="optionsCounter">
					
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
	<c:if test='${subQuestion["class"] == "class fi.testcenter.domain.question.PointsQuestion"}'>
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
						<c:forEach var="points" begin="0" end="${subQuestion.maxPoints}">
							
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
	<c:if test='${subQuestion["class"] == "class fi.testcenter.domain.question.TextQuestion"}'>
		<h3>${subQuestion.question}</h3>
		<c:if test="${loginRole == '[ROLE_ADMIN]' }">
				<label class="checkbox">											
				<sf:checkbox value='true' path="answers[${answerIndexCounter}].highlightAnswer" label="Huomiot-osioon" />
				</label>
			<br>
		</c:if>
		
		<br>
		<c:choose>
				<c:when test="${subQuestion.textAreaInput == true }">
					<sf:textarea rows="5" style="width:100%;" path="answers[${answerIndexCounter}].answer" />
				</c:when>
				<c:otherwise>
					<sf:input class="form-control" path="answers[${answerIndexCounter}].answer" />
				</c:otherwise>
		</c:choose>
		
	</c:if>
	
<!-- Cost listing question -->
						<c:if test='${subQuestion["class"] == "class fi.testcenter.domain.question.CostListingQuestion"}'>
							<h3>${subQuestion.questionTopic}</h3>
								<c:forEach var="listQuestion" items="${subQuestion.questions}" varStatus="costListingAnswerCounter">
									<h4>${listQuestion}</h4>
									<sf:input style="width: 5em" path="answers[${answerIndexCounter}].answers[${costListingAnswerCounter.index}]" /> €
									<br>
								</c:forEach>
								<h4><b>${question.total}</b></h4>
								<sf:input style="width: 5em" path="answers[${answerIndexCounter}].total" /> €
								<br>
						</c:if>
										
	
	
	
	<c:set var="answerIndexCounter" value="${answerIndexCounter + 1}" scope="request" />
</c:forEach>