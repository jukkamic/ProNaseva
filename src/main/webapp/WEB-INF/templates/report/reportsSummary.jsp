<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<body>

<div style="padding-left: 3em; margin: 2em 2em 2em 0">
<br><br><br>
<h1>AUTOASI-RAPORTTIEN YHTEENVETO</h1>


<br>
<h2>Raportit:</h2>
<c:forEach var="report" items="${reportsList}" varStatus="reportCounter">
	<p>${reportCounter.count}. ${report.workshop.name}
</c:forEach>

<br>

<c:set var="reportCount" value="${fn:length(reportsList)}" scope="request" />
<c:set var="total" value="0" />
<c:forEach var="listReport" items="${reportsList}" varStatus="reportCounter">
	<c:set var="total" value="${total+listReport.totalScorePercentage}" />
	<c:set var="average" value="${total / reportCounter.count}" scope = "request" />
</c:forEach>
<br>

<h3>Raporttien kokonaispistemäärien (prosentteina) keskiarvo: <b>${average} %</b></h3>


<br><br>
<h3>Raportin osien ja kysymysryhmien tulokset:</h3>

<c:forEach var="templatePart" items="${template.reportParts}" varStatus="reportTemplatePartCounter">

<c:set var="partTotal" value="0" scope="request" />

	<c:forEach var="report" items="${reportsList}">
			
			<c:set var="part" value="${report.reportParts[reportTemplatePartCounter.index]}"/>
			
			<c:set var="partTotal" value="${partTotal + part.scorePercentage}" scope="request"/>
			
						
			
	</c:forEach>
	
	<c:set var="part" value="${reportsList[0].reportParts[reportTemplatePartCounter.index]}" />
	
	
	<c:if test="${part.score != -1}">
		<p><b>${templatePart.title}</b>: ${partTotal / reportCount} %</p>
	</c:if>
	<c:if test="${part.score == -1}">
		<p><b>${templatePart.title}</b>: Ei pisteytetty</p>
	</c:if>
	<div style="margin-left: 3em;">
	
	<c:forEach var="group" items="${templatePart.questionGroups}" varStatus="groupCounter">
		
	
			<c:set var="grouptotal" value="0" scope="request" />
			
			<c:forEach var="report" items="${reportsList}" varStatus="reportCounter">
				
				<c:set var="grouptotal" value="${grouptotal + report.reportParts[reportTemplatePartCounter.index].reportQuestionGroups[groupCounter.index].scorePercentage}" scope="request" />
				
			</c:forEach>
			
			<p><i>${groupCounter.count}. ${group.title}</i>: ${grouptotal / reportCount} % </p>
	</c:forEach>
	</div>
</c:forEach>

<br><br>

<h3>Vastauskohtaiset tiedot</h3>

<c:forEach var="part" items="${template.reportParts}" varStatus="partCounter">
	<h4>${partCounter.count}. ${part.title}</h4>
	<div style="padding-left: 3em;">
	<c:forEach var="group" items="${part.questionGroups}" varStatus="groupCounter">
		<h4>${partCounter.count}.${groupCounter.count}. ${group.title}</h4>
		<div style="padding-left: 3em;">
		
		
		<c:forEach var="question" items="${group.questions}" varStatus="questionCounter">
		
			<c:if test='${question["class"] == "class fi.testcenter.domain.question.MultipleChoiceQuestion"}'>

				<c:set var="totalScore" value="0" scope="request" />
				<c:set var="maxScoreTotal" value="0" scope="request" />
				<c:set var="scoredAnswersCount" value="0" scope="request" />
				<c:forEach var="report" items="${reportsList}">
					<c:forEach var="loopAnswer" items="${report.reportParts[partCounter.index].reportQuestionGroups[groupCounter.index].answers}">
						<c:if test="${loopAnswer.question.question == question.question}">
							<c:set var="answer" value="${loopAnswer}" scope="request" />
							
						</c:if>
					</c:forEach>	
					
					
					<c:if test="${answer.removeAnswerFromReport == 'false' }">
						<c:set var="chosenOption" value="${answer.chosenOptionIndex}" />
						
						<c:if test="${chosenOption != -1 }">
						<c:set var="score" value="${question.optionsList[chosenOption].points}" scope="request"/>
						
						<c:if test="${score != -1}">
							<c:set var="scoredAnswersCount" value="${scoredAnswersCount + 1}" scope="request" />
							<c:set var="totalScore" value="${totalScore + score}" scope="request" />
							<c:set var="maxScoreForQuestion" value="${answer.maxScore}" scope="request" />
						</c:if>
						</c:if>
					</c:if>
				</c:forEach>
				
				<c:if test="${scoredAnswersCount > 0}">
					<c:set var="average" value="${totalScore / scoredAnswersCount}" />
					<p><i>${partCounter.count}.${groupCounter.count}.${questionCounter.count} ${question.question}</i> ${average} / ${maxScoreForQuestion} pistettä 
					--- ${average / maxScoreForQuestion * 100} %</p>
				</c:if>
				
				<c:if test="${scoredAnswersCount == 0}">
					<p><i>${partCounter.count}.${groupCounter.count}.${questionCounter.count} ${question.question}</i>: Ei pisteytettyjä vastauksia.</p>
				</c:if>
				
				<div style="padding-left: 3em;">
				<c:forEach var="option" items="${question.optionsList}"  varStatus="optionCounter">
					<c:set var="chosenCount" value="0" scope="request"  />
					<c:forEach var="report" items="${reportsList}">
									
						<c:forEach var="loopAnswer" items="${report.reportParts[partCounter.index].reportQuestionGroups[groupCounter.index].answers}">
							
							<c:if test="${loopAnswer.question.question == question.question}">
								
								<c:set var="answer" value="${loopAnswer}" scope="request" />
								
							</c:if>
						</c:forEach>
						
						<c:if test="${answer.chosenOptionIndex == optionCounter.index && answer.removeAnswerFromReport != 'true'}">
							<c:set var="chosenCount" value="${chosenCount + 1}" scope="request" />
						</c:if>
					</c:forEach>
					<p>${option.multipleChoiceOption}: ${chosenCount} kpl</p>
					
				</c:forEach>
				</div>
				
			</c:if>

		

		<c:if test="${!empty question.subQuestions}">
			<div style="padding-left: 3em;">
			<c:forEach var="subQuestion" items="${question.subQuestions}" varStatus="subQuestionCounter">
				<c:if test='${subQuestion["class"] == "class fi.testcenter.domain.question.MultipleChoiceQuestion"}'>

				<c:set var="totalScore" value="0" scope="request" />
				<c:set var="maxScoreTotal" value="0" scope="request" />
				<c:set var="scoredAnswersCount" value="0" scope="request" />
				<c:forEach var="report" items="${reportsList}">
					<c:forEach var="loopAnswer" items="${report.reportParts[partCounter.index].reportQuestionGroups[groupCounter.index].answers}">
						<c:if test="${loopAnswer.question.question == subQuestion.question}">
							<c:set var="answer" value="${loopAnswer}" scope="request" />
							
						</c:if>
					</c:forEach>	
					
					
					<c:if test="${answer.removeAnswerFromReport == 'false' }">
						<c:set var="chosenOption" value="${answer.chosenOptionIndex}" />
						
						<c:if test="${chosenOption != -1 }">
						<c:set var="score" value="${subQuestion.optionsList[chosenOption].points}" scope="request"/>
						
						<c:if test="${score != -1}">
							<c:set var="scoredAnswersCount" value="${scoredAnswersCount + 1}" scope="request" />
							<c:set var="totalScore" value="${totalScore + score}" scope="request" />
							<c:set var="maxScoreForQuestion" value="${answer.maxScore}" scope="request" />
						</c:if>
						</c:if>
					</c:if>
				</c:forEach>
				
				<c:if test="${scoredAnswersCount > 0}">
					<c:set var="average" value="${totalScore / scoredAnswersCount}" />
					<p><i>${partCounter.count}.${groupCounter.count}.${questionCounter.count}.${subQuestionCounter.count} ${subQuestion.question}</i> ${average} / ${maxScoreForQuestion} pistettä 
					--- ${average / maxScoreForQuestion * 100} %</p>
					
				</c:if>
				
				<c:if test="${scoredAnswersCount == 0}">
					<p><i>${partCounter.count}.${groupCounter.count}.${questionCounter.count}.${subQuestionCounter.count} ${subQuestion.question}</i>: Ei pisteytettyjä vastauksia.</p>
				</c:if>
				
				<div style="padding-left: 3em;">
				<c:forEach var="option" items="${subQuestion.optionsList}"  varStatus="optionCounter">
					<c:set var="chosenCount" value="0" scope="request"  />
					<c:forEach var="report" items="${reportsList}">
									
						<c:forEach var="loopAnswer" items="${report.reportParts[partCounter.index].reportQuestionGroups[groupCounter.index].answers}">
							
							<c:if test="${loopAnswer.question.question == subQuestion.question}">
								
								<c:set var="answer" value="${loopAnswer}" scope="request" />
								
							</c:if>
						</c:forEach>
						
						<c:if test="${answer.chosenOptionIndex == optionCounter.index && answer.removeAnswerFromReport != 'true'}">
							<c:set var="chosenCount" value="${chosenCount + 1}" scope="request" />
						</c:if>
					</c:forEach>
					<p>${option.multipleChoiceOption}: ${chosenCount} kpl</p>
					
				</c:forEach>
				</div>
				
			</c:if>
			
			
			
			</c:forEach>
			</div>
		
		
		</c:if>
			
		</c:forEach>
		</div>
	</c:forEach>
	</div>
</c:forEach>
</div>
</body>