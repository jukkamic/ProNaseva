<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<c:forEach var="question" items="${optionalQuestionsAnswer.questions}" varStatus="counter">	
<c:set var="questionCounter" value="${questionOrderNumber + counter.index}" />

<!-- Points question -->

	<c:if test='${question["class"] == "class fi.testcenter.domain.question.PointsQuestion"}'>
		<div class="noPageBreak">
		<div class="multipleChoice">																	
		<h3 style="display: inline; text-align: top;">${questionGroupNumber}.${questionCounter}. ${question.question}</h3>
		
		<c:if test="${report.answers[answerIndexCounter].answers[counter.index].givenPoints != '-1'}">
		
			<h3 style="display: inline; float:right; text-align: top;">${report.answers[answerIndexCounter].answers[counter.index].givenPoints}/${report.answers[answerIndexCounter].questions[counter.index].maxPoints}</h3>
		</c:if>
		
		</div>
		</div>
		
		<br>
		<div class="noPageBreak">
			<c:set var="remarks" value="${report.answers[answerIndexCounter].answers[counter.index].remarks}" />
			<c:if test="${remarks !='' and remarks != null}"> 
				<h4 style="padding-left: 0;">Huomioita:</h4>
				<p style="padding-left: 0;">${report.answers[answerIndexCounter].answers[counter.index].remarks}</p>

			</c:if>
		</div>
		
	</c:if>

<br>

<!--  Text question -->
	<%-- <c:if test='${question["class"] == "class fi.testcenter.domain.question.TextQuestion"}'>
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
		
		<br>
		<p style="font-size: 1.2em;">${report.answers[answerIndexCounter].answer}</p>
	</c:if>
	
	 --%>
<!-- Cost listing question -->
<%-- <c:if test='${question["class"] == "class fi.testcenter.domain.question.CostListingQuestion"}'>
	<h3>${questionCounter}. ${question.questionTopic}</h3>
		<c:if test="${loginRole == '[ROLE_ADMIN]' }">
				<div class="checkbox" style="font-size: 1.2em;">
				<label>											
				<sf:checkbox value='true'
					path="answers[${answerIndexCounter}].highlightAnswer" label="Huomiot-osioon" />
				
				</label>
				
				</div>
				<br>
		</c:if>
		
		<c:forEach var="listQuestion" items="${question.questions}" varStatus="costListingAnswerCounter">
			
			<h4>${listQuestion}</h4>
			<c:set var="listingAnswer" value="${report.answers[answerIndexcounter]}" />
			<p style="font-size: 1.2em;">${report.answers[answerIndexCounter].answers[costListingAnswerCounter.index]} €</p>
			<br>
		</c:forEach>
			<h4><b>${question.total}</b></h4>
			<p style="font-size: 1.2em;">${report.answers[answerIndexCounter].total} €</p>
		<br>
</c:if>
	
<!-- ListAndScoreImportantPoints -->
<c:if test='${question["class"] == "class fi.testcenter.domain.question.ImportantPointsQuestion"}'>
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
						<button class="btn btn-large btn-primary disabled" type="button">
							${importanceNumber.index}
						</button>
						</c:when>
					<c:otherwise>
						<button class="btn btn-large btn-default" type="button" disabled>
							${importanceNumber.index}
						</button>
					</c:otherwise>
				</c:choose>  

			</c:forEach>

			  
			
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
					<c:when test="${report.answers[answerIndexCounter].answerItems[questionItemCounter.index].score == score.index}">
						<button class="btn btn-large btn-primary disabled" type="button">
							${score.index} 
						</button>
						</c:when>
					<c:otherwise>
						<button class="btn btn-large btn-default" type="button" disabled>
							${score.index} 
						</button>
					</c:otherwise>
				</c:choose>  

			</c:forEach>


			<br><br>
			</div>
			</div>
			</tr>
			
			</table>
		</div>
		</c:forEach>
		
			
</c:if>	 --%>

</c:forEach>