<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


	
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
							path="reportParts[${mainReportPartIndex}].questionGroups[${mainQuestionGroupIndex}].questions[${mainQuestionIndex}].subQuestions[${subQuestionCounter.index}].answer.chosenSelections" 
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
						<c:when test="${subQuestion.answer.chosenOptionIndex == optionsCounter.index}">
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
							<sf:radiobutton id="button" path="reportParts[${mainReportPartIndex}].questionGroups[${mainQuestionGroupIndex}].questions[${mainQuestionIndex}].subQuestions[${subQuestionCounter.index}].answer.chosenOptionIndex" 
							value="${optionsCounter.index}" /> ${option.radiobuttonText}
						</c:when>
						<c:otherwise>
							<sf:radiobutton id="button" path="reportParts[${mainReportPartIndex}].questionGroups[${mainQuestionGroupIndex}].questions[${mainQuestionIndex}].subQuestions[${subQuestionCounter.index}].answer.chosenOptionIndex"
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
		<sf:textarea rows="5" style="width:100%;" path="reportParts[${mainReportPartIndex}].questionGroups[${mainQuestionGroupIndex}].questions[${mainQuestionIndex}].subQuestions[${subQuestionCounter.index}].answer.remarks" 
			value="${question.answer.remarks}" />
		<br><br>
	</c:if>

	<!--  Text area question -->
	<c:if
		test="${subQuestion.class == 'class fi.testcenter.domain.TextareaQuestion'}">
		<h3>${subQuestion.question}</h3>
		<br>
		<sf:textarea rows="5" style="width:100%;" path="reportParts[${mainReportPartIndex}].questionGroups[${mainQuestionGroupIndex}].questions[${mainQuestionIndex}].subQuestions[${subQuestionCounter.index}].answer.answer" 
			value="${question.answer.answer}" />
	</c:if>
	
	<!-- Text field question -->
	<c:if
		test="${subQuestion.class == 'class fi.testcenter.domain.TextfieldQuestion'}">
		<h3>${subQuestion.question}</h3>
		<br>
		<sf:input type="text" style="width:100%;" path="reportParts[${mainReportPartIndex}].questionGroups[${mainQuestionGroupIndex}].questions[${mainQuestionIndex}].subQuestions[${subQuestionCounter.index}].answer.answer" />  
	</c:if>
</c:forEach>