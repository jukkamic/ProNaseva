<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>



	
<!-- Questions loop -->



<c:forEach var="listedSubQuestion" items="${mainQuestion.subQuestions}" varStatus="subQuestionCounter">
<c:set var="subQuestion" value="${listedSubQuestion.question}" />
	
	
	<!-- Multiple choice question -->
	
	<c:if test="${subQuestion.class == 'class fi.testcenter.domain.MultipleChoiceQuestion'}">
		<c:choose>
			<c:when test="${subQuestion.multipleSelectionsAllowed == true}">
					<h3>${subQuestion.question}</h3>
					<c:forEach var="option" items="${subQuestion.options}">
						<label class="checkbox" style="">											
						<sf:checkbox value="${option.option}" 
							path="report.answers[${answerIndexCounter}].chosenSelections" 
							label="${option.option}" />
						</label>
						<br>
					</c:forEach>
			</c:when>
	<c:otherwise>
		<h3>${subQuestion.question}</h3>
		<div class="Demo-boot" style="padding-top: 15px;">
			<div class="btn-group" data-toggle="buttons">
				<c:forEach var="option" items="${subQuestion.options}" varStatus="optionsCounter">
					
					<!-- Jos kysymykselle on ennalta tehty valinta esim. muokattaessa 
							raporttia uudelleen, kyseinen valintanappi näkyy valittuna. -->
					<c:choose>
						<c:when test="report.answers[${answerIndexCounter}].chosenOptionIndex == optionsCounter.index}">
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
							<sf:radiobutton id="button" path="report.answers[${answerIndexCounter}].chosenOptionIndex" 
							value="${optionsCounter.index}" /> ${option.radiobuttonText}
						</c:when>
						<c:otherwise>
							<sf:radiobutton id="button" path="report.answers[${answerIndexCounter}].chosenOptionIndex"
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
		<sf:textarea rows="5" style="width:100%;" path="report.answers[${answerIndexCounter}].remarks" 
			value="report.answers[${answerIndexCounter}].remarks}" />
		<br><br>
	</c:if>



	<!-- Text question -->
	<c:if
		test="${subQuestion.class == 'class fi.testcenter.domain.TextQuestion'}">
		<h3>${subQuestion.question}</h3>
		<br>
		<c:choose>
				<c:when test="${subQuestion.textAreaInput == true }">
					<sf:textarea rows="5" style="width:100%;" path="report.answers[${answerIndexCounter}].answer" />
				</c:when>
				<c:otherwise>
					<sf:input path="report.answers[${answerIndexCounter}].answer" />
				</c:otherwise>
		</c:choose>
		<sf:input type="text" style="width:100%;" path="reportParts[${mainReportPartIndex}].questionGroups[${mainQuestionGroupIndex}].questions[${mainQuestionIndex}].subQuestions[${subQuestionCounter.index}].question.answer.answer" />  
	</c:if>
</c:forEach>