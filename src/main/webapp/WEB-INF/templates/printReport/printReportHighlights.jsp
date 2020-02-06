<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<h1>Keskeiset  huomiot</h1>

<c:forEach var="reportHighlight" items="${report.reportHighlights}">

	<c:if test="${reportHighlight.reportPart.title != reportPartTitle}">
		<h2>${reportHighlight.reportPart.title}</h2>
		<c:set var="reportPartTitle" value="${reportHighlight.reportPart.title}" />
	</c:if>
	
	<c:if test="${reportHighlight.questionGroup.title != questionGroupTitle}">
		<h3>${reportHighlight.questionGroupOrderNumber}. ${reportHighlight.questionGroup.title}</h3>
		<c:set var="questionGroupTitle" value="${reportHighlight.questionGroup.title}" />
	</c:if>
	
<!-- Multiple choice question -->

	<c:if test="${reportHighlight.answer['class'] == 'class fi.testcenter.domain.answer.MultipleChoiceAnswer'}">

		<div class="noPageBreak">
		<div class="multipleChoice">
		
		<c:if test="${reportHighlight.subQuestionOrderNumber != null and reportHighlight.subQuestionOrderNumber != 0}">
			<c:set var="questionNumber" value="${reportHighlight.questionGroupOrderNumber}.${reportHighlight.questionOrderNumber}.${reportHighlight.subQuestionOrderNumber}" />
		</c:if>
		<c:if test="${reportHighlight.subQuestionOrderNumber == null or reportHighlight.subQuestionOrderNumber == 0}">
			<c:set var="questionNumber" value="${reportHighlight.questionGroupOrderNumber}.${reportHighlight.printReportQuestionOrderNumber}" />
		</c:if>
		<h3>${questionNumber}. ${reportHighlight.answer.question.question}</h3>
			
			<c:if test="${reportHighlight.answer.showScore == true}">
				<h3 style="display: inline; float:right;">${reportHighlight.answer.score}/${reportHighlight.answer.maxScore}</h3>
			</c:if>
			<div class="indentAnswer">
			<table style="margin-left: 2.2em">
				<c:forEach var="option" items="${reportHighlight.answer.question.options}" varStatus="optionsCounter">
					<tr>
						<c:choose>
							<c:when test="${reportHighlight.answer.chosenOptionIndex == optionsCounter.index}">
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
					<td>	
						${option.option}
					</td>
				</tr>
			</c:forEach> 
		</table>
		</div>
										
			</div> <!-- Page break ok -->
			<div class = "indentAnswer">
				<c:set var="remarks" value="${reportHighlight.answer.remarks}" />
				<c:if test="${remarks !='' and remarks != null}"> 
					<div class="noPageBreak">
						<h4>Huomioita:</h4>
						<p>${reportHighlight.answer.remarks}</p>
					</div>
				</c:if>
			</div>
			</div>
	</c:if>
	
<!-- Points question -->
		<c:if test="${reportHighlight.answer['class'] == 'class fi.testcenter.domain.answer.PointsAnswer'}">
	
			<div class="noPageBreak">
			
				<c:if test="${reportHighlight.subQuestionOrderNumber != null and reportHighlight.subQuestionOrderNumber != 0}">
					<c:set var="questionNumber" value="${reportHighlight.questionGroupOrderNumber}.${reportHighlight.questionOrderNumber}.${reportHighlight.subQuestionOrderNumber}" />
				</c:if>
				<c:if test="${reportHighlight.subQuestionOrderNumber == null or reportHighlight.subQuestionOrderNumber == 0}">
					<c:set var="questionNumber" value="${reportHighlight.questionGroupOrderNumber}.${reportHighlight.printReportQuestionOrderNumber}" />
				</c:if>
				<br>
				<h3 style="display: inline; margin-top: 6px;">${questionNumber}. ${reportHighlight.answer.question.question}</h3>
				<c:if test="${reportHighlight.answer.givenPoints != '-1'}">
					<h3 style="float: right; display: inline; margin-top: 6px;">${reportHighlight.answer.givenPoints}/${reportHighlight.answer.question.maxPoints}</h3>
					
				</c:if>
				<br>
			
			</div>
			
			<div class = "indentAnswer">
				
				<c:set var="remarks" value="${reportHighlight.answer.remarks}" />
				<c:if test="${remarks !='' and remarks != null}"> 
					<div class="noPageBreak">
						<h4>Huomioita:</h4>
						<p>${remarks}</p>
					</div>
				</c:if>
			</div>
		</c:if>
	<!-- Text answer -->
	
	<c:if test="${reportHighlight.answer['class'] == 'class fi.testcenter.domain.answer.TextAnswer'}">
	<div class="noPageBreak">
		<c:if test="${reportHighlight.subQuestionOrderNumber != null and reportHighlight.subQuestionOrderNumber != 0}">
			<c:set var="questionNumber" value="${reportHighlight.questionGroupOrderNumber}.${reportHighlight.questionOrderNumber}.${reportHighlight.subQuestionOrderNumber}" />
		</c:if>
		<c:if test="${reportHighlight.subQuestionOrderNumber == null or reportHighlight.subQuestionOrderNumber == 0}">
			<c:set var="questionNumber" value="${reportHighlight.questionGroupOrderNumber}.${reportHighlight.printReportQuestionOrderNumber}" />
		</c:if>
		<h3 style="margin: 0; padding: 0;">${questionNumber}. ${reportHighlight.answer.question.question}</h3>
		<p class="indentAnswer">${reportHighlight.answer.answer}</p>
		<br>
	</div>
	</c:if>
		
</c:forEach>
