<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


	
<!-- SubQuestions loop -->


<c:forEach var="listedSubQuestion" items="${mainQuestion.subQuestions}" varStatus="subQuestionCounter">
<c:set var="subQuestion" value="${listedSubQuestion.question}" />
	

<!-- Multiple choice question -->
<c:if
	test="${subQuestion.class == 'class fi.testcenter.domain.MultipleChoiceQuestion'}">
								
	<c:set var="maxPointsForQuestion" value="0" />	
	<c:set var="scoredQuestions" value="TRUE" />			

	<div class="noPageBreak">
	<h3>${subQuestion.question}</h3>
						<table>
							<c:forEach var="option" items="${subQuestion.options}" varStatus="optionsCounter">
								<c:if test="${option.points > maxPointsForQuestion}">
									<c:set var="maxPointsForQuestion" value="${option.points}" />
								</c:if>
								<tr>
								<c:choose>
										<c:when test="${subQuestion.answer.chosenOptionIndex == optionsCounter.index}">
											<td>
												&#9745;
												&nbsp;
												<c:set var="totalScore" value="${totalScore + option.points}" />
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
			
			<c:set var="maxTotalScore" value="${maxTotalScore + maxPointsForQuestion}" />

	</div>
	
	<div class="noPageBreak">
	<h4 style="padding-left: 0;">Huomioita:</h4>
	<p style="padding-left: 0;">${subQuestion.answer.remarks}</p>
	</div>
	
</c:if>

<!--  Text area question -->
<c:if
	test="${subQuestion.class == 'class fi.testcenter.domain.TextareaQuestion'}">
	<h3 style="padding-left: 0;">${subQuestion.question}</h3>
	<p style="padding-left: 0;">${subQuestion.answer}</p>
</c:if>

<!-- Text field question -->
<c:if
	test="${subQuestion.class == 'class fi.testcenter.domain.TextfieldQuestion'}">
	<h3 style="padding-left: 0;">${subQuestion.question}</h3>
	<p style="padding-left: 0;">${subQuestion.answer}</p>
</c:if> 

</c:forEach>