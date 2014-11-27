<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<!-- Raportin tulostus selaimella pdf-tiedostoksi. Toimii ainakin FireFox 32.0.3 jossa
lisäosana PDFCreator -->


<jsp:include page="/WEB-INF/templates/includes/printReportHeader.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body>

<div style="margin-left: 49px; margin-right: 28px;">

<table>
	   <thead >
	   		<tr>
	   			<td style="width:595pt; height: 150px; margin-top:0; padding-top:0;">
							<div style="float: right; width: 75%; border: bottom; height: 70px;">
								<div style="text-align:left; margin: 0; padding-top:0;">
									<h2 style="margin-top:0; padding-top:0; padding-bottom:21px; margin-bottom:0;">${report.importer.name}</h2>
									<h2 style="margin-top:0; padding-top:0; padding-bottom:21åx; margin-bottom:0;">${report.workshop.name}</h2>
								</div>
							</div>
				</td>
	   		</tr>
	   	</thead>

	   <tbody>
	    <tr><td>
			<div style="margin-top: 70px; margin-left: 98px;" class="coverPage">
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
						<h2 style="display: inline; padding-right: 42px;">Yleisarvosana : </h2>
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
					<h2>${questionGroupCounter.count}. ${questionGroup.title}</h2>
				</c:when>
				<c:otherwise>
					<h2 class="newpage">${questionGroupCounter.count}. ${questionGroup.title}</h2>
				</c:otherwise>
			</c:choose>
			
							
			<!-- Questions loop -->
			<c:set var="questionOrderNumber" value="0" scope="request" />
			<c:forEach var="question" items="${questionGroup.questions}" varStatus="questionCounter">
			<c:if test="${report.answers[answerIndexCounter].removeAnswerFromReport != 'true'}">
				<c:set var="questionOrderNumber" value="${questionOrderNumber + 1}" scope="request" />
			</c:if>
			
			<!-- Multiple choice question -->
				<c:if test="${question['class'] == 'class fi.testcenter.domain.question.MultipleChoiceQuestion' and report.answers[answerIndexCounter].removeAnswerFromReport != 'true'}">
					
					<div class="noPageBreak">
					<div class="multipleChoice">					
					<h3>${questionGroupCounter.count}.${questionOrderNumber}. ${question.question}</h3>
					
					
					<c:if test="${report.answers[answerIndexCounter].showScore == true}">
						<h3 style="display: inline; float:right;">${report.answers[answerIndexCounter].score}/${report.answers[answerIndexCounter].maxScore}</h3>
					</c:if>
					
					<c:choose>
					<c:when test="${question.multipleSelectionsAllowed == true}">
						<div class="indentAnswer">
						<table style="margin-left: 31px">
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
									
									
							<td>	
								<p>${option.option}</p>
							</td>
						</tr>
					</c:forEach>
				</table>
				</div>
			
			</c:when>
				<c:otherwise>
					<div class="multipleChoice">
					<div class="indentAnswer">
					<table style="margin-left: 31px">
					
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
						</div>
						</div>
						
						</c:otherwise>
						
						</c:choose>
					</div> 
					</div> <!-- Page break ok -->
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


			<!-- Points question -->
				<c:if test="${question['class'] == 'class fi.testcenter.domain.question.PointsQuestion' and report.answers[answerIndexCounter].removeAnswerFromReport != 'true'}">
			
					<div class="noPageBreak">
					<div class="multipleChoice">
					
					<h3 style="display: inline; text-align: top;">${questionGroupCounter.count}.${questionOrderNumber}. ${question.question}</h3>
					
						<c:if test="${report.answers[answerIndexCounter].givenPoints != '-1'}">
							<h3 style="display: inline; float:right; text-align: top;">${report.answers[answerIndexCounter].givenPoints}/${report.answers[answerIndexCounter].question.maxPoints}</h3>
							<br><br>
						</c:if>
					</div>
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
									
	<!-- Text field question -->
				<c:if test="${question['class'] == 'class fi.testcenter.domain.question.TextQuestion' and report.answers[answerIndexCounter].removeAnswerFromReport != 'true'}">
				
					<div class="noPageBreak">
					
						<h3>${questionGroupCounter.count}.${questionOrderNumber}. ${question.question}</h3>
						
						<p class="indentAnswer">${report.answers[answerIndexCounter].answer}</p> 
					</div>
					
				</c:if>
	
											
			<!-- Cost listing question -->
				<c:if test='${question["class"] == "class fi.testcenter.domain.question.CostListingQuestion" and report.answers[answerIndexCounter].removeAnswerFromReport != "true"}'>
				<div class="costListing">
					<h3>${questionGroupCounter.count}.${questionOrderNumber}. ${question.questionTopic}</h3>
												
					<table style="width: 550px;">
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
		<div class="importantPoints">
					<c:if test='${question["class"] == "class fi.testcenter.domain.question.ImportantPointsQuestion" and report.answers[answerIndexCounter].removeAnswerFromReport != "true"}'>
					<div class="importantPoints">
						<h3>${questionGroupCounter.count}.${questionOrderNumber}. ${question.question}</h3>
						<br>
						
						<table style="width: 550px;">
							<thead>
									<tr>
									<th>
									<p><b>Tärkeys</b></p>
									</th>
									<th></th>
									<th>
									<p><b>Pisteet</b></p>
									</th>
									</tr>
						  </thead>
						  <tbody>
									
									<c:forEach var="questionItem" items="${question.questionItems}" varStatus="questionItemCounter">
									
									<tr>
										<td>
											<c:if test="${report.answers[answerIndexCounter].answerItems[questionItemCounter.index].importance != -1}"> 
													<p>${report.answers[answerIndexCounter].answerItems[questionItemCounter.index].importance} </p>
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
	</div>
	<!-- Subquestions -->
				<c:if test="${not empty question.subQuestions}">
					<c:set var="mainQuestion" value="${question}" scope="request" />
					<c:set var="questionGroupNumber" value="${questionGroupCounter.count}" scope="request" />
					<c:set var="mainQuestionNumber" value="${questionOrderNumber}" scope="request" />
					<div style="margin-left: 42px;">
						<jsp:include page="/WEB-INF/templates/printReport/printReportSubQuestions.jsp" />
					</div>				
				</c:if>
				
			</c:forEach> <!-- Question loop end -->
	
	<!-- Optional questions -->
			<c:if test='${not empty questionGroup.optionalQuestions}'>
				<c:set var="questionGroupNumber" value="${questionGroupCounter.count}" scope="request" />
				<c:set var="optionalQuestionsAnswer" value="${report.answers[answerIndexCounter]}" scope="request" />
				<c:set var="questionOrderNumber" value="${questionOrderNumber + 1}" scope="request" />
			
				<jsp:include page="/WEB-INF/templates/printReport/printOptionalQuestions.jsp" />
				<c:set var="answerIndexCounter" value="${answerIndexCounter + 1}" scope="request" />
			</c:if>
			
			
			<c:if test="${report.questionGroupScore[questionGroupScoreIndexCounter].showScore == true}">
				<h3 style="padding-top: 24px; text-align: right;">Pisteet: ${report.questionGroupScore[questionGroupScoreIndexCounter].score} / 
				${report.questionGroupScore[questionGroupScoreIndexCounter].maxScore}</h3>
			</c:if>
	
	<c:set var="questionGroupScoreIndexCounter" value="${questionGroupScoreIndexCounter + 1}" scope="request" />
	
	</c:forEach> <!-- Question group loop end -->
	
</c:forEach> <!-- Report part loop end -->
	<br><br>
		 
</td></tr>
	    
<tfoot><tr><td><div style="margin-top: 98px;"></div></td></tr></tfoot>
</tbody></table>
	
	
</div>

</body>

</html>