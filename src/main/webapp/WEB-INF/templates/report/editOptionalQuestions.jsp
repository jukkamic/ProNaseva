<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
						
<!-- Multiple choice question -->


<c:forEach var="question" items="${optionalQuestionsAnswer.questions}" varStatus="counter">
	
<c:set var="questionCounter" value="${questionCount + counter.index}" />
	
	<!-- Points question -->
	<c:if test='${question["class"] == "class fi.testcenter.domain.question.PointsQuestion"}'>
	<% System.out.println("Valinnainen on pointsquestion"); %>
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
	
<!-- MultipleChoiceQUestion -->
	
	
		<c:if test='${question["class"] == "class fi.testcenter.domain.question.MultipleChoiceQuestion"}'>
	<h3>${questionCounter.count}. ${question.question}</h3>
	<c:if test="${loginRole == '[ROLE_ADMIN]' }">
	<div class="checkbox" style="font-size: 1.2em;">
	<label>											
	<sf:checkbox value='true'
		path="answers[${answerIndexCounter}].answers[${counter}].highlightAnswer" label="Huomiot-osioon" />
	
	</label>
	
	</div>
	<br>
	</c:if>
	<c:choose>
	<c:when test="${question.multipleSelectionsAllowed == true}">
		<c:forEach var="option" items="${question.options}">
			<label class="checkbox">											
				<sf:checkbox value="${option.option}" 
					path="answers[${answerIndexCounter}].answers[${counter.index}].chosenSelections" label="${option.option}" />
			</label>
			<br>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div class="Demo-boot" style="padding-top: 15px;">
			<div class="btn-group" data-toggle="buttons">
				<c:forEach var="option" items="${question.options}" varStatus="optionsCounter">
					
					<!-- Jos kysymykselle on ennalta tehty valinta esim. muokattaessa 
						raporttia uudelleen, kyseinen valintanappi näkyy valittuna. -->
					<c:choose>
					<c:when test="${report.answers[answerIndexCounter].answers[counter.index].chosenOptionIndex == optionsCounter.index}"> 
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
							<sf:radiobutton id="button" path="answers[${answerIndexCounter}]answers[${counter.index}].chosenOptionIndex" 
							value="${optionsCounter.index}" /> ${option.radiobuttonText}
						</c:when>
						<c:otherwise>
							<sf:radiobutton id="button" path="answers[${answerIndexCounter}].answers[${counter.index}].chosenOptionIndex"
							value="${optionsCounter.index}" /> ${option.option}
						</c:otherwise>
						</c:choose>
					</label>
				</c:forEach> 
				 <c:choose>
						<c:when test="${report.answers[answerIndexCounter].answers[counter.index].chosenOptionIndex == -1}"> 
							<label class="btn btn-default active">
							</c:when>
						<c:otherwise>
							<label class="btn btn-default">
						</c:otherwise>
					</c:choose> 
					<sf:radiobutton id="button" path="answers[${answerIndexCounter}].answers[${counter.index}].chosenOptionIndex" 
							value="-1" /> Ei valintaa
					
			</div>
		</div>
		</c:otherwise>
		</c:choose> 
	
		<br>
		<h4>Huomioita:</h4>
		<sf:textarea rows="5" style="width:100%;" path="answers[${answerIndexCounter}].answers[${counter.index}].remarks" 
			value="report.answers[${answerIndexCounter}].remarks}" />
		<br><br>
	</c:if> 



	
	<!-- Text question -->
	<c:if test='${question["class"] == "class fi.testcenter.domain.question.TextQuestion"}'>
		
		<h3>${questionCounter.count}. ${question.question}</h3>
		<c:if test="${loginRole == '[ROLE_ADMIN]' }">
						<div class="checkbox" style="font-size: 1.2em;">
						<label>											
						<sf:checkbox value='true'
							path="answers[${answerIndexCounter}].answers[${counter.index}].highlightAnswer" label="Huomiot-osioon" />
						
						</label>
						
						</div>
						<br>
		</c:if>
		
		<c:choose>
			<c:when test="${question.textAreaInput == true }">
				<sf:textarea rows="5" style="width:100%;" path="answers[${answerIndexCounter}].answers[${counter.index}].answer" />
			</c:when>
			<c:otherwise>
				<sf:input class="form-control" path="answers[${answerIndexCounter}].answers[${counter.index}].answer" />
			</c:otherwise>
		</c:choose>
		<br>
		
	</c:if>
	<!-- Cost listing question -->
	<c:if test='${question["class"] == "class fi.testcenter.domain.question.CostListingQuestion"}'>
		<h3>${questionCounter.count}. ${question.questionTopic}</h3>
			<c:forEach var="listQuestion" items="${question.questions}" varStatus="costListingAnswerCounter">
				<h4>${listQuestion}</h4>
				<sf:input style="width: 5em" path="answers[${answerIndexCounter}].answers[${counter.index}].answers[${costListingAnswerCounter.index}]" /> €
				<br>
			</c:forEach>
			<br>
		<h4><b>${question.total}</b></h4>
		<sf:input style="width: 5em" path="answers[${answerIndexCounter}].answers[${counter.index}].total" /> €
	</c:if>
	
	
	<!-- ListAndScoreImportantPoints -->
	<c:if test='${question["class"] == "class fi.testcenter.domain.question.ImportantPointsQuestion"}'>
		<h3>${questionCounter.count}. ${question.question}</h3>
		<c:if test="${loginRole == '[ROLE_ADMIN]' }">
							<div class="checkbox" style="font-size: 1.2em;">
							<label>											
							<sf:checkbox value='true'
								path="answers[${answerIndexCounter}].answers[${counter.index}].highlightAnswer" label="Huomiot-osioon" />
							
							</label>
							
							</div>
							<br>
			</c:if>
	
			<c:forEach var="questionItem" items="${question.questionItems}" varStatus="questionItemCounter">
			<div style="border-bottom: 3px solid #eee;">
				<h3>${questionItem}</h3>
				<table>
				<tr>
				<td width="40%" style="padding-bottom: 2em">
				<h4>Tärkeys: </h4>
				<div class="Demo-boot" style="padding-top: 15px;">
				<div class="btn-group" data-toggle="buttons">
				
					<c:forEach begin="1" end="${question.numberOfItemsToChoose}" varStatus="importanceNumber">
						<c:choose>
							<c:when test="${report.answers[answerIndexCounter].answerItems[questionItemCounter.index].importance == importanceNumber.index}"> 
								<label class="btn btn-primary active">
								</c:when>
							<c:otherwise>
								<label class="btn btn-primary">
							</c:otherwise>
						</c:choose>  
						<sf:radiobutton id="button" path="answers[${answerIndexCounter}].answers[${counter.index}].answerItems[${questionItemCounter.index}].importance"
								value="${importanceNumber.index}" /> ${importanceNumber.index} 
								</label>
					</c:forEach>
					<c:choose>
							<c:when test="${report.answers[answerIndexCounter].answerItems[questionItemCounter.index].importance == -1}"> 
								<label class="btn btn-default active">
								</c:when>
							<c:otherwise>
								<label class="btn btn-default">
							</c:otherwise>
						</c:choose>  
						<sf:radiobutton id="button" path="answers[${answerIndexCounter}].answers[${counter.index}].answerItems[${questionItemCounter.index}].importance"
								value="-1" /> Ei valintaa
						</label>
					
					<br><br>
				</div>
				</div>
				</td>
				<td width="20%"></td>
				<td width="40%" style="padding-bottom: 2em">
				<h4>Arvosana: </h4>
				<div class="Demo-boot" style="padding-top: 15px;">
				<div class="btn-group" data-toggle="buttons">
				
					<c:forEach begin="1" end="${question.maxScoreForQuestionItem}" varStatus="score">
						<c:choose>
							<c:when test="${report.answers[answerIndexCounter].answers[counter.index].answerItems[questionItemCounter.index].score == score.index}"> 
								<label class="btn btn-primary active">
								</c:when>
							<c:otherwise>
								<label class="btn btn-primary">
							</c:otherwise>
						</c:choose>  
						<sf:radiobutton id="button" path="answers[${answerIndexCounter}].answers[${counter.index}].answerItems[${questionItemCounter.index}].score"
								value="${score.index}" /> ${score.index} 
								</label>
					</c:forEach>
						<c:choose>
							<c:when test="${report.answers[answerIndexCounter].answers[$counter.index].answerItems[questionItemCounter.index].score == -1}"> 
								<label class="btn btn-default active">
								</c:when>
							<c:otherwise>
								<label class="btn btn-default">
							</c:otherwise>
						</c:choose>  
						<sf:radiobutton id="button" path="answers[${answerIndexCounter}].answers[${counter.index}].answerItems[${questionItemCounter.index}].score"
								value="-1" /> Ei valinta
								</label>
					
					<br><br>
				</div>
				</div>
				</tr>
				
				</table>
			</div>
			</c:forEach>
			
				
	</c:if>	
	


</c:forEach>