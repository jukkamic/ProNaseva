<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
						
<!-- Multiple choice question -->


<c:forEach var="question" items="${optionalQuestionsAnswer.questions}" varStatus="counter">	
<c:set var="questionCounter" value="${questionCount + counter.index}" />
	<!-- Points question -->
	<c:if test='${question["class"] == "class fi.testcenter.domain.question.PointsQuestion"}'>
	<h3>${questionCounter}. ${question.question}</h3>
		<c:if test="${loginRole == '[ROLE_ADMIN]' }">
			<div class="checkbox" style="font-size: 1.2em;">
				<label>											
					<sf:checkbox value='true'
						path="answers[${answerIndexCounter}].answers[${counter.index}].highlightAnswer" label="Huomiot-osioon" />
				</label>
		
			</div>
			<br>
		</c:if>
	
	
	<div class="Demo-boot" style="padding-top: 15px;">
			<div class="btn-group" data-toggle="buttons">
				<c:forEach var="points" begin="0" end="${question.maxPoints}">
					
					<!-- Jos kysymykselle on ennalta tehty valinta esim. muokattaessa 
							raporttia uudelleen, kyseinen valintanappi näkyy valittuna. -->
						<c:choose>
						<c:when test="${report.answers[answerIndexCounter].answers[counter.index].givenPoints == points}"> 
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
					<sf:radiobutton id="button" path="answers[${answerIndexCounter}].answers[${counter.index}].givenPoints"
							value="${points}" /> ${points}
	
					</label>
				</c:forEach> 
				 <c:choose>
						<c:when test="${report.answers[answerIndexCounter].answers[counter.index].givenPoints == -1}"> 
							<label class="btn btn-default active">
							</c:when>
						<c:otherwise>
							<label class="btn btn-default">
						</c:otherwise>
					</c:choose> 
					<sf:radiobutton id="button" path="answers[${answerIndexCounter}].answers[${counter.index}].givenPoints" 
							value="-1" /> Ei valintaa
					
			</div>
		</div>
		
		<br>
		<h4>Huomioita:</h4>
		<sf:textarea rows="5" style="width:100%;" path="answers[${answerIndexCounter}].answers[${counter.index}].remarks" 
			value="report.answers[${answerIndexCounter}].answers[${counter.index}].remarks}" />
		<br><br>
	</c:if> 


</c:forEach>