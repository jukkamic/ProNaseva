<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<!-- Raportin tulostus selaimella pdf-tiedostoksi. Toimii ainakin FireFox 32.0.3 jossa
lisäosana PDFCreator -->


<jsp:include page="/WEB-INF/templates/includes/printReportHeader.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body>

<div style="margin-left: 3.5em; margin-right: 2em;">
<table>
	   <thead >
	   		<tr>
	   			<td style="width:595pt; height: 10em; margin-top:0; padding-top:0;">
							<div style="float: right; width: 70%; border: bottom; height: 5em;">
								<div style="text-align:left; margin: 0em 0em 0em 0em; padding-top:0;">
									<h2 style="margin-top:0; padding-top:0; padding-bottom:1.5em; margin-bottom:0;">${report.importer.name}</h2>
									<h2 style="margin-top:0; padding-top:0; padding-bottom:1.5em; margin-bottom:0;">${report.workshop.name}</h2>
								</div>
							</div>
				</td>
	   		</tr>
	   	</thead>

	   <tbody>
	    <tr><td>
			<div style="margin-top: 5em; margin-left: 7em;">
			<h1>${report.importer.name}</h1>
			<h1>Korjaamotestiraportti</h1>
				<br>
				<br>
				<div style="border-bottom: 1px solid #eee;">
					<h2>${report.workshop.name}</h2>
					<h2>${report.reportDate}</h2>
					<br><br>
					
				</div>
				<br><br>
			
			<table>
				<tr>
					<td>
						<h2 style="display: inline; padding-right: 3em;">Yleisarvosana : </h2>
					</td>
					<td>
						<c:if test="${report.overallResultSmiley == 'SMILE' }">
							<i class="fa fa-smile-o fa-5x"></i>
						</c:if>
						<c:if test="${report.overallResultSmiley == 'NEUTRAL' }">
							<i class="fa fa-meh-o fa-5x"></i>
						</c:if>
						<c:if test="${report.overallResultSmiley == 'FROWN' }">
							<i class="fa fa-frown-o fa-5x"></i>
						</c:if>
				</td>
				</tr>
			</table>
						
</div>
<div class="newpage"></div>	
		
<jsp:include page="/WEB-INF/templates/printReport/printReportSummary.jsp" />

<div class="newpage"></div>	
					
<jsp:include page="/WEB-INF/templates/printReport/printReportHighlights.jsp" />
			

<!-- PRINT REPORT CONTENT -->
<!-- Report part loop -->	

<c:set var="answerIndexCounter" value="0" scope="request" />
<c:set var="questionGroupScoreIndexCounter" value="0" scope="request" />

<c:forEach var="reportPart" items="${report.reportTemplate.reportParts}" varStatus="reportPartCounter">
<h1 class="newpage" style="border-bottom: 1px solid #eee;">${reportPart.title}</h1>

			<!-- QuestionGroup loop -->
			
			<c:forEach var="questionGroup" items="${reportPart.questionGroups}"
					varStatus="questionGroupCounter">

			<!-- Muuttujat monivalintakysymysten kysymysryhmäkohtaiseen pisteytykseen -->
			<c:set var="maxTotalScore" value="0" />
			<c:set var="totalScore" value="0" />
			<c:set var="scoredQuestions" value="FALSE" />
			<c:choose>
				<c:when test="${questionGroupCounter.count == 1}">
					<h2 style="border-bottom: 1px solid #eee;">${questionGroupCounter.count}. ${questionGroup.title}</h2>
				</c:when>
				<c:otherwise>
					<h2 class="newpage" style="border-bottom: 1px solid #eee;">${questionGroupCounter.count}. ${questionGroup.title}</h2>
				</c:otherwise>
			</c:choose>
			
							
			<!-- Questions loop -->
			
			<c:forEach var="question" items="${questionGroup.questions}" varStatus="questionCounter">
				
			
			<!-- Multiple choice question -->
				<c:if test="${question['class'] == 'class fi.testcenter.domain.question.MultipleChoiceQuestion'}">
			
					<div class="noPageBreak">
					<div class="multipleChoice">					
					<h3>${questionGroupCounter.count}.${questionCounter.count}. ${question.question}</h3>
					<c:if test="${report.answers[answerIndexCounter].showScore == true}">
						<h3 style="display: inline; float:right;">${report.answers[answerIndexCounter].score}/${report.answers[answerIndexCounter].maxScore}</h3>
					</c:if>
					
					<c:choose>
					<c:when test="${question.multipleSelectionsAllowed == true}">
					
						<table>
							<c:forEach var="option" items="${question.options}">
							<tr>
								<c:set var="selected" value="false" />
									<c:forEach var="chosenSelection" items="${report.answers[answerIndexCounter].chosenSelections}">
										<c:if test="${chosenSelection == option}">
											<c:set var="selected" value="true" />
										</c:if>
									</c:forEach>
								
								<c:choose>
									<c:when test="${selected == 'false'}">
											<td>
											<p>&#9744; &nbsp;</p>
											
											</td>
									</c:when>
									<c:otherwise>
											<td>
											<p>&#9745; &nbsp;</p>
																					
											</td>
									</c:otherwise>
								</c:choose>
									
									
							<td style="padding-right: 2em;">	
								<p>${option.option}</p>
							</td>
						</tr>
					</c:forEach>
				</table>
				
				
			</c:when>
				<c:otherwise>
					
					<table class="multipleChoice">
					
						<c:forEach var="option" items="${question.options}" varStatus="optionsCounter">
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
					
						</c:otherwise>
						</c:choose>
					</div>
											
						</div> <!-- Page break ok -->
						<c:set var="remarks" value="${report.answers[answerIndexCounter].remarks}" />
						<c:if test="${remarks !='' and remarks != null}"> 
							<div class="noPageBreak">
								<h4>Huomioita:</h4>
								<p>${report.answers[answerIndexCounter].remarks}</p>
							</div>
						</c:if>
						
				</c:if>
									
	<!-- Text field question -->
				<c:if test="${question['class'] == 'class fi.testcenter.domain.question.TextQuestion'}">
				
					<div class="noPageBreak">
					
						<h3>${questionGroupCounter.count}.${questionCounter.count}. ${question.question}</h3>
						<p>${report.answers[answerIndexCounter].answer}</p> 
					</div>
					
				</c:if>
	
											
			<!-- Cost listing question -->
				<c:if test='${question["class"] == "class fi.testcenter.domain.question.CostListingQuestion"}'>
				<div class="costListing">
					<h3>${questionGroupCounter.count}.${questionCounter.count}. ${question.questionTopic}</h3>
												
					<table>
					<c:forEach var="listQuestion" items="${question.questions}" varStatus="costListingAnswerCounter">
						<tr>
						<td style="width: 70%">
						<p>${listQuestion}</p>
						</td>
						<td style="width: 30%; text-align: right;">
						<p>${report.answers[answerIndexCounter].answers[costListingAnswerCounter.index]} €</p>
						</td>
						</tr>
					</c:forEach>
					
					<tr>
						<td style="width: 70%">
							<p><b>${question.total}</b></p>
						</td>
						<td style="width: 30%; text-align: right;">
							<p>${report.answers[answerIndexCounter].total} €</p>
						</td>
					</tr>
							
					</table>
				</div>
				</c:if>
										
		<!-- ListAndScoreImportantPoints -->
					<c:if test='${question["class"] == "class fi.testcenter.domain.question.ImportantPointsQuestion"}'>
					<div class="importantPoints">
						<h3>${questionGroupCounter.count}.${questionCounter.count}. ${question.question}</h3>
						<br>
						
						<table>
							<thead>
									<tr>
									<th>
									<p style="text-align: center"><b>Tärkeys</b></p>
									</th>
									<th></th>
									<th>
									<p style="text-align: center"><b>Pisteet</b></p>
									</th>
									</tr>
						  </thead>
						  <tbody>
									
									<c:forEach var="questionItem" items="${question.questionItems}" varStatus="questionItemCounter">
									
									<tr>
										<td>
											<c:if test="${report.answers[answerIndexCounter].answerItems[questionItemCounter.index].importance != -1}"> 
													<p> style="margin: 0 0 0 0; text-align: center; vertical-align: top">${report.answers[answerIndexCounter].answerItems[questionItemCounter.index].importance} </p>
										</c:if>
										</td>
										<td>							
											<p>${questionItem}</p>
										</td>
										<td>
											<c:if test="${report.answers[answerIndexCounter].answerItems[questionItemCounter.index].score != -1}">
													<p>${report.answers[answerIndexCounter].answerItems[questionItemCounter.index].score}</p>
											</c:if>
										</td>
									</tr>
									</c:forEach>
							</tbody>
								</table>
							</div>			
				</c:if>	
				<c:set var="answerIndexCounter" value="${answerIndexCounter + 1}" scope="request" />
	
	<!-- Subquestions -->
				<c:if test="${not empty question.subQuestions}">
					<c:set var="mainQuestion" value="${question}" scope="request" />
					<c:set var="questionGroupNumber" value="${questionGroupCounter.count}" scope="request" />
					<c:set var="mainQuestionNumber" value="${questionCounter.count}" scope="request" />
					<div style="margin-left: 3em;">
						<jsp:include page="/WEB-INF/templates/printReport/printReportSubQuestions.jsp" />
					</div>				
				</c:if>
				
			</c:forEach> <!-- Question loop end -->
									
										
			<c:if test="${report.questionGroupScore[questionGroupScoreIndexCounter].showScore == true}">
				<h4 style="font-weight: bold; padding-right: 2em; padding-top: 2em; text-align: right;">Pisteet: ${report.questionGroupScore[questionGroupScoreIndexCounter].score} / 
				${report.questionGroupScore[questionGroupScoreIndexCounter].maxScore}</h4>
			</c:if>
	
	<c:set var="questionGroupScoreIndexCounter" value="${questionGroupScoreIndexCounter + 1}" scope="request" />
	
	</c:forEach> <!-- Question group loop end -->
	
</c:forEach> <!-- Report part loop end -->
	<br><br>
		 
</td></tr>
	    
<tfoot><tr><td><div style="margin-top: 7em;"></div></td></tr></tfoot>
</tbody></table>
	
	
</div>

</body>

</html>