<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<h1>Keskeiset  huomiot</h1>

<c:forEach var="reportHighlight" items="${report.reportHighlights}">

	<c:if test="${reportHighlight.reportPart.title != reportPartTitle}">
		<h3>${reportHighlight.reportPart.title}</h3>
		<c:set var="reportPartTitle" value="${reportHighlight.reportPart.title}" />
	</c:if>
	
	<c:if test="${reportHighlight.questionGroup.title != questionGroupTitle}">
		<h3>${reportHighlight.questionGroupOrderNumber}. ${reportHighlight.questionGroup.title}</h3>
		<c:set var="questionGroupTitle" value="${reportHighlight.questionGroup.title}" />
	</c:if>
	
<!-- Multiple choice question -->

	<c:if test="${reportHighlight.answer['class'] == 'class fi.testcenter.domain.answer.MultipleChoiceAnswer'}">
	
			<div class="noPageBreak">
			
			<c:if test="${reportHighlight.subQuestionOrderNumber != null and reportHighlight.subQuestionOrderNumber != 0}">
				<c:set var="questionNumber" value="${reportHighlight.questionGroupOrderNumber}.${reportHighlight.questionOrderNumber}.${reportHighlight.subQuestionOrderNumber}" />
			</c:if>
			<c:if test="${reportHighlight.subQuestionOrderNumber == null or reportHighlight.subQuestionOrderNumber == 0}">
				<c:set var="questionNumber" value="${reportHighlight.questionGroupOrderNumber}.${reportHighlight.questionOrderNumber}" />
			</c:if>
			<h3>${questionNumber}. ${reportHighlight.answer.question.question}</h3>
				
				<c:if test="${reportHighlight.answer.showScore == true}">
					<h3 style="display: inline; float:right;">${reportHighlight.answer.score}/${reportHighlight.answer.maxScore}</h3>
				</c:if>
				
				<table>
					<c:forEach var="option" items="${reportHighlight.answer.question.options}" varStatus="optionsCounter">
						<tr>
							<c:choose>
								<c:when test="${reportHighlight.answer.chosenOptionIndex == optionsCounter.index}">
									<td	style="padding-left: 1.5em;">
										&#9745;
										&nbsp;
										
									</td>
								</c:when>
									<c:otherwise>
										<td style="padding-left: 1.5em;">
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
										
			</div> <!-- Page break ok -->
				<c:set var="remarks" value="${reportHighlight.answer.remarks}" />
				<c:if test="${remarks !='' and remarks != null}"> 
					<div class="noPageBreak">
						<h4>Huomioita:</h4>
						<p>${reportHighlight.answer.remarks}</p>
					</div>
				</c:if>
				
	</c:if>
		
	<!-- Text answer -->
	
	<c:if test="${reportHighlight.answer['class'] == 'class fi.testcenter.domain.answer.TextAnswer'}">
	<div class="noPageBreak">
		<c:if test="${reportHighlight.subQuestionOrderNumber != null and reportHighlight.subQuestionOrderNumber != 0}">
			<c:set var="questionNumber" value="${reportHighlight.questionGroupOrderNumber}.${reportHighlight.questionOrderNumber}.${reportHighlight.subQuestionOrderNumber}" />
		</c:if>
		<c:if test="${reportHighlight.subQuestionOrderNumber == null or reportHighlight.subQuestionOrderNumber == 0}">
			<c:set var="questionNumber" value="${reportHighlight.questionGroupOrderNumber}.${reportHighlight.questionOrderNumber}" />
		</c:if>
		<h3>${questionNumber}. ${reportHighlight.answer.question.question}</h3>
		<p style="font-size: 1.2em;">${reportHighlight.answer.answer}</p>
		<br>
	</div>
	</c:if>
		
</c:forEach>
