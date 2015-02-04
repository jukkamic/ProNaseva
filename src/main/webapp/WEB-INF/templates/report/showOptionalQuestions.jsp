<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
						

<c:forEach var="optionalAnswer" items="${optionalQuestionsAnswer.optionalAnswers}" varStatus="counter">	

<!-- Points question -->

	<c:if test='${optionalAnswer["class"] == "class fi.testcenter.domain.answer.PointsAnswer"}'>
																			
		<h3>${optionalAnswer.answerOrderNumber}. ${optionalAnswer.question.question}</h3>
		<c:if test="${loginRole == '[ROLE_ADMIN]' }">
				<div class="checkbox" style="font-size: 1.2em;">
				<label>											
				<sf:checkbox value='true'
					path="reportParts[${reportPartCounter.index}].reportQuestionGroups[${questionGroupCounter.index}].answers[${answerCounter.index}].optionalAnswers[${counter.index}].highlightAnswer" label="Huomiot-osioon" />  
				
				</label>
				
				</div>
				<br>
		</c:if>
									
	<div class="Demo-boot" style="padding-top: 15px;">
			<div class="btn-group" data-toggle="buttons">
				<c:forEach var="points" begin="0" end="${optionalAnswer.question.maxPoints}">
						
					<c:choose>
						<c:when test="${optionalQuestionsAnswer.optionalAnswers[counter.index].givenPoints == points}">
							<button class="btn btn-large btn-selectedOption disabled" disabled type="button">
								${points}
								
							</button>
						</c:when>
						<c:otherwise>
							<button class="btn btn-large btn-showSelections" disabled type="button">
								${points}
							</button>
						</c:otherwise>
					</c:choose>	
															
				</c:forEach> 
			</div>
		</div>

		<br>
		
		
		<c:if test="${optionalQuestionsAnswer.optionalAnswers[counter.index].remarks != 'null' && optionalQuestionsAnswer.optionalAnswers[counter.index].remarks != '' }">
					
		<h4>Huomioita:</h4>
		<p style="font-size: 1.2em;">${optionalQuestionsAnswer.optionalAnswers[counter.index].remarks}</p>
		</c:if>
		<br><br>
	</c:if>

<!--  Text question -->
	<c:if test='${answer["class"] == "class fi.testcenter.domain.answer.TextAnswer"}'>
		<h3>${optionalAnswer.answerOrderNumber}. ${answer.question.question}</h3>
		<c:if test="${loginRole == '[ROLE_ADMIN]' }">
				<div class="checkbox" style="font-size: 1.2em;">
				<label>											
				<sf:checkbox value='true'
					path="reportParts[${reportPartCounter.index}].reportQuestionGroups[${questionGroupCounter.index}].answers[${answerCounter.index}].optionalAnswers[${counter.index}].highlightAnswer" label="Huomiot-osioon" />
				
				</label>
				
				</div>
				<br>
		</c:if>
		
		<br>
		<p style="font-size: 1.2em;">${optionalQuestionsAnswer.answers[counter.index].answer}</p>
	</c:if>
	
	 

</c:forEach>