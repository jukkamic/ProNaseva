<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>



<c:forEach var="answer" items="${optionalQuestionsAnswer.optionalAnswers}" varStatus="counter">
	
<c:set var="optionalQuestionCounter" value="${questionCount + counter.index}" />

	
	<!-- Points question -->
	<c:if test='${answer["class"] == "class fi.testcenter.domain.answer.PointsAnswer"}'>
	
	<h3>${optionalQuestionCounter}. ${answer.question.question}</h3>
		<c:if test="${loginRole == '[ROLE_ADMIN]' }">
			<div class="checkbox" style="font-size: 1.2em;">
				<label>											
					<sf:checkbox value='true'
						path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].optionalAnswers[${counter.index}].highlightAnswer" label="Huomiot-osioon" />
				</label>
		
			</div>
			<br>
		</c:if>
	
	
	<div class="Demo-boot" style="padding-top: 15px;">
			<div class="btn-group" data-toggle="buttons">
				<c:forEach var="points" begin="0" end="${answer.question.maxPoints}">
					
					<!-- Jos kysymykselle on ennalta tehty valinta esim. muokattaessa 
							raporttia uudelleen, kyseinen valintanappi näkyy valittuna. -->
						<c:choose>
						<c:when test="${optionalQuestionsAnswer.optionalAnswers[counter.index].givenPoints == points}"> 
							<label class="btn btn-showSelections active">
							</c:when>
						<c:otherwise>
							<label class="btn btn-showSelections">
						</c:otherwise>
					</c:choose>   
					
					<!-- Jos MultipleChoiceOption-oliolle on asetettu pitkää valintanapin tekstiä
							varten erillinen radiobuttonText, jossa napin teksti on jaettu kahdelle 
							riville <br> tägillä, näytetään radiobuttonText, muuten option teksti jossa 
							ei ole tägejä -->
					<sf:radiobutton id="button" path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].optionalAnswers[${counter.index}].givenPoints"
							value="${points}" /> &nbsp&nbsp${points}&nbsp&nbsp
	
					</label>
				</c:forEach> 
				 <c:choose>
						<c:when test="${optionalQuestionsAnswer.optionalAnswers[counter.index].givenPoints == -1}"> 
							<label class="btn btn-default active" style="min-height: 4em; border-color: #000">
							</c:when>
						<c:otherwise>
							<label class="btn btn-default" style="min-height: 4em; border-color: #000">
						</c:otherwise>
					</c:choose> 
					<sf:radiobutton id="button" path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].optionalAnswers[${counter.index}].givenPoints" 
							value="-1" /> Ei valintaa
					
			</div>
		</div>
		
		<br>
		<h4>Huomioita:</h4>
		<sf:textarea rows="5" style="width:100%;" path="reportParts[${editReportPartNumber}].reportQuestionGroups[${questionGroupCounter.index}].answers[${questionCounter.index}].optionalAnswers[${counter.index}].remarks" 
			value="report.answers[${answerIndexCounter}].optionalAnswers[${counter.index}].remarks}" />
		<br><br>
	</c:if> 
	
</c:forEach>