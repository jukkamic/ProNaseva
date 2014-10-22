<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


	
<!-- SubQuestions loop -->


<c:forEach var="subQuestion" items="${mainQuestion.subQuestions}" varStatus="subQuestionCounter">


<!-- Multiple choice question -->
<c:if
	test="${subQuestion.class == 'class fi.testcenter.domain.MultipleChoiceQuestion'}">

	<div class="noPageBreak">
	<h3>${questionGroupNumber}.${mainQuestionNumber}.${subQuestionCounter.count}. ${subQuestion.question}</h3>
	<c:if test="${report.answers[answerIndexCounter].showScore == true}">
		<h3 style="display: inline; float:right;">${report.answers[answerIndexCounter].score}/${report.answers[answerIndexCounter].maxScore}</h3>
	</c:if>
						<table>
							<c:forEach var="option" items="${subQuestion.options}" varStatus="optionsCounter">

								<tr>
								<c:choose>
										<c:when test="${report.answers[answerIndexCounter].chosenOptionIndex == optionsCounter.index}">
											<td>
												&#9745;
												&nbsp;
										
											</td>
										</c:when>
										<c:otherwise>
											<td>
												&#9744;
												&nbsp;
											</td>
										</c:otherwise>
										</c:choose>
										
										
										<td style="padding-right: 2em;">	
											${option.option}
										</td>
									</tr>
							</c:forEach> 
							</table>
			
	</div>
	
		<c:set var="remarks" value="${report.answers[answerIndexCounter].remarks}" />
			<c:if test="${remarks !='' and remarks != null}"> 
		<div class="noPageBreak">
		<h4 style="padding-left: 0;">Huomioita:</h4>
		<p style="padding-left: 0;">${report.answers[answerIndexCounter].remarks}</p>
		</div>
	</c:if>
	
</c:if>

<!-- Text question -->
<c:if
	test="${subQuestion.class == 'class fi.testcenter.domain.TextQuestion'}">
	<h3 style="padding-left: 0;">${questionGroupNumber}.${mainQuestionNumber}.${subQuestionCounter.count}. ${subQuestion.question}</h3>
	<p style="padding-left: 0;">${report.answers[answerIndexCounter].answer}</p>
</c:if> 

<c:set var="answerIndexCounter" value="${answerIndexCounter + 1}" scope="request" />
					

</c:forEach>