<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/templates/includes/printReportHeader.jsp" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<body>
					
			<h1>Test-Center raportti</h1>
			
			<br>
			<br>
			<div style="border-bottom: 1px solid #eee;">
			<h4>Maahantuoja: ${report.importer.name}</h4>
			<h4>Tarkastettu korjaamo: ${report.workshop.name}</h4>
			
			<br><br>
			</div>
			<br><br>
			
				<!-- QuestionGroup loop -->
				<c:forEach var="questionGroup" items="${report.questionGroups}"
						varStatus="questionGroupCounter">

				<!-- Muuttujat monivalintakysymysten kysymysryhmäkohtaiseen pisteytykseen -->
				<c:set var="maxTotalScore" value="0" />
				<c:set var="totalScore" value="0" />
				<c:set var="scoredQuestions" value="FALSE" />
										
				
				<h2 class="newpage" style="border-bottom: 1px solid #eee;">${questionGroup.title}</h2>
				
								
				<!-- Questions loop -->
									
				<c:forEach var="question" items="${questionGroup.questions}" varStatus="questionCounter">
		
				
				<!-- Multiple choice question -->
					<c:if test="${question.class == 'class fi.testcenter.domain.MultipleChoiceQuestion'}">
						<c:set var="maxPointsForQuestion" value="0" />	
						<c:set var="scoredQuestions" value="TRUE" />			
											
						<h3>${questionCounter.count}. ${question.question}</h3>
						<br>
						<table>
							<c:forEach var="option" items="${question.options}" varStatus="optionsCounter">
								<c:if test="${option.points > maxPointsForQuestion}">
									<c:set var="maxPointsForQuestion" value="${option.points}" />
								</c:if>
								
									<tr>
																	
										<c:choose>
											<c:when test="${question.answer.chosenOptionIndex == optionsCounter.index}">
												<td>
												<span class="glyphicon glyphicon-ok" style="font-size: 1em; text-decoration: none; color: black;"></span>
												&nbsp;
												<c:set var="totalScore" value="${totalScore + option.points}" />
												</td>
											</c:when>
											<c:otherwise>
												<td style="width: 2em;"></td>
											</c:otherwise>
										</c:choose>
										
										<td>	
											${option.option}
										</td>
									</tr>
							</c:forEach> 
							</table>
							
							<c:set var="maxTotalScore" value="${maxTotalScore + maxPointsForQuestion}" />
							<br>
							<h4 style="margin-left: 32px;">Huomioita:</h4>
							<p style="margin-left: 16px;">${question.answer.remarks}</p>
											
							<br><br>
					</c:if>

					<!--  Text area question -->
					<c:if test="${question.class == 'class fi.testcenter.domain.TextareaQuestion'}">
						<h3>${questionCounter.count}. ${question.question}</h3>
						<br>
						<p style="width:100%; margin-left: 16px;">${question.answer.answer}</p>
					</c:if>
										
					<!-- Text field question -->
					<c:if test="${question.class == 'class fi.testcenter.domain.TextfieldQuestion'}">
						<h3>${questionCounter.count}. ${question.question}</h3>
						<br>
						<p style="width:100%; margin-left: 16px;">${question.answer.answer}</p> 
					</c:if>
							
				</c:forEach> <!-- Question loop end -->
				
													
				<c:if test="${scoredQuestions == 'TRUE'}">
				<br>
				<h4 style="font-weight: bold; margin-left: 32px;">Pisteet: ${totalScore}/${maxTotalScore}</h4>
				</c:if>
					
		</c:forEach> <!-- Question group loop end -->

	<br><br>
</body>
				
<!-- 	<script>
		window.print();
		window.location = "/ProNaseva/printDone"
		</script> --> 