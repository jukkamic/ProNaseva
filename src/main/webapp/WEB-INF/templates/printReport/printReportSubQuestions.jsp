<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


	
<!-- SubQuestions loop -->


<c:forEach var="subQuestion" items="${mainQuestion.subQuestions}" varStatus="subQuestionCounter">


<!-- Multiple choice question -->
<c:if
	test="${subQuestion['class'] == 'class fi.testcenter.domain.question.MultipleChoiceQuestion' and report.answers[answerIndexCounter].removeAnswerFromReport != 'true'}">

	<div class="noPageBreak">
	<div class="multipleChoice">
	<h3>${questionGroupNumber}.${mainQuestionNumber}.${subQuestionCounter.count}. ${subQuestion.question}</h3>
	<c:if test="${report.answers[answerIndexCounter].showScore == true}">
		<h3 style="display: inline; float:right;">${report.answers[answerIndexCounter].score}/${report.answers[answerIndexCounter].maxScore}</h3>
	</c:if>
		<div class="indentAnswer">
		<table style="margin-left: 3em;">
			<c:forEach var="option" items="${subQuestion.options}" varStatus="optionsCounter">

				<tr>
				<c:choose>
						<c:when test="${report.answers[answerIndexCounter].chosenOptionIndex == optionsCounter.index}">
							<td> 
								<p>&#9745; &nbsp;</p>
						
							</td>
						</c:when>
						<c:otherwise>
							<td>
								<p>&#9744; &nbsp;</p>
							</td>
						</c:otherwise>
						</c:choose>
						
						
						<td>	
							<p>${option.option}</p>
						</td>
					</tr>
			</c:forEach> 
			</table>
			</div>
		</div>

	</div>
		<div class = "indentAnswer">
			<c:set var="remarks" value="${report.answers[answerIndexCounter].remarks}" />
			<c:if test="${remarks !='' and remarks != null}"> 
				<div class="noPageBreak">
				<h4 style="padding-left: 0;">Huomioita:</h4>
				<p style="padding-left: 0;">${report.answers[answerIndexCounter].remarks}</p>
				
				</div>
			</c:if>
		</div>
	
</c:if>
						
<!-- Points question -->
	<c:if test="${subQuestion['class'] == 'class fi.testcenter.domain.question.PointsQuestion' and report.answers[answerIndexCounter].removeAnswerFromReport != 'true'}">

		<div class="noPageBreak">
		
		<br>
		<h3 style="display: inline">${questionGroupNumber}.${mainQuestionNumber}.${subQuestionCounter.count}. ${subQuestion.question}</h3>
			<c:if test="${report.answers[answerIndexCounter].givenPoints != '-1'}">
				<h3 style="float: right; display: inline;">${report.answers[answerIndexCounter].givenPoints}/${report.answers[answerIndexCounter].question.maxPoints}</h3>
			</c:if>
		
		</div>
		
		<div class = "indentAnswer">
			
			<c:set var="remarks" value="${report.answers[answerIndexCounter].remarks}" />
			<c:if test="${remarks !='' and remarks != null}"> 
				<div class="noPageBreak">
					<h4>Huomioita:</h4>
					<p>${report.answers[answerIndexCounter].remarks}</p>
				</div>
			</c:if>
		</div>
	</c:if>

<!-- Text question -->
<c:if
	test="${subQuestion['class'] == 'class fi.testcenter.domain.question.TextQuestion' and report.answers[answerIndexCounter].removeAnswerFromReport != 'true'}">
	<h3>${questionGroupNumber}.${mainQuestionNumber}.${subQuestionCounter.count}. ${subQuestion.question}</h3>
	<p class="indentAnswer">${report.answers[answerIndexCounter].answer}</p>
</c:if> 

<c:set var="answerIndexCounter" value="${answerIndexCounter + 1}" scope="request" />
					

</c:forEach>