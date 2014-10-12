<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<!-- Raportin tulostus selaimella pdf-tiedostoksi. Toimii ainakin FireFox 32.0.3 jossa
lisäosana Printpdf 0.76 tai Print Edit 12.10. Print Edit näyttää print preview näkymässä
tulostavan virheellisesti mutta pdf tulostuu oikein -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<title>Test-Center</title>


<style>

body {
background-image: url('../resources/images/printReportBackground.jpg');
font-family: 'Arial';
 
}

</style>

<style media="print">

body {
font-family: 'Arial';
background-image: url('../resources/images/printReportBackground.jpg');
-webkit-print-color-adjust:exact;
background-repeat:repeat-y;
background-position: left top;
background-attachment:fixed;

background-size: 100% 100%; 

background-color: white;

 }
tfoot { display: table-footer-group; 
}

thead { display: table-header-group;
}


@page {
size: A4;
margin: 0;

}
.newpage {
  page-break-before: always;

}

.noPageBreak {
	page-break-inside: avoid;
}

</style>

<link rel="stylesheet" href="<c:url value='/resources/css/jquery-ui.css' />"/>
<link rel="stylesheet" href="<c:url value='/resources/css/jquery-ui.theme.css' />"/>


</head>

<body>



<div style="margin-left: 3.5em; margin-right: 2em;">


	<table>
	   <thead >
	   		<tr>
	   			<td style="width:595pt; height: 10em; margin-top:0; padding-top:0;">
	   						
							<div style="float: right; width: 70%; border: bottom; height: 5em;">
								<div style="text-align:left; margin: 0em 0em 0em 0em; padding-top:0;">
									<h2 style="margin-top:0; padding-top:0; padding-bottom:0; margin-bottom:0;">Test Center Tiililä Oy</h2>
									
								</div>
								
							</div>
							
				</td>
	   		</tr>
	   	</thead>

	   <tbody>
	    <tr><td>
			<div style="margin-top: 5em; margin-left: 7em;">
			<h1>Test Center Tiililä raportti</h1>
				<br>
				<br>
				<div style="border-bottom: 1px solid #eee;">
					<h2>${report.importer.name}</h2>
					<h2>[pvm]</h2>
					<br><br>
					
				</div>
				<div class="newpage"></div>		
			</div>
			
	<!-- PRINT REPORT CONTENT -->
	<!-- Report part loop -->	
	
	<c:forEach var="reportPart" items="${report.reportParts}" varStatus="reportPartCounter">

				<!-- QuestionGroup loop -->
				
				<c:forEach var="questionGroup" items="${reportPart.questionGroups}"
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
						
						<div class="noPageBreak">
						
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
												&#9745;
												&nbsp;
												<c:set var="totalScore" value="${totalScore + option.points}" />
												</td>
											</c:when>
											<c:otherwise>
												<td>
												&#9744;
												&nbsp;
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
							
							</div> <!-- Page break ok -->
							
							<div class="noPageBreak">
								<h4 style="margin-left: 32px;">Huomioita:</h4>
								<p style="margin-left: 16px;">${question.answer.remarks}</p>
							</div>
											
							<br><br>
					</c:if>

					<!--  Text area question -->
					
					<c:if test="${question.class == 'class fi.testcenter.domain.TextareaQuestion'}">
					
						<div class="noPageBreak">
					
						<h3>${questionCounter.count}. ${question.question}</h3>
						<br>
						<p style="width:100%; margin-left: 16px;">${question.answer.answer}</p>
						
						</div>
					</c:if>
										
					<!-- Text field question -->
					<c:if test="${question.class == 'class fi.testcenter.domain.TextfieldQuestion'}">
					
						<div class="noPageBreak">
						
						<h3>${questionCounter.count}. ${question.question}</h3>
						<br>
						<p style="width:100%; margin-left: 16px;">${question.answer.answer}</p> 
						</div>
						
					</c:if>
					</div>
				</c:forEach> <!-- Question loop end -->
				
													
				<c:if test="${scoredQuestions == 'TRUE'}">
				<br>
				<h4 style="font-weight: bold; margin-left: 32px;">Pisteet: ${totalScore}/${maxTotalScore}</h4>
				</c:if>
					
		</c:forEach> <!-- Question group loop end -->
		
	</c:forEach> <!-- Report part loop end -->
	<br><br>
		 
</td></tr>
	    
<tfoot><tr><td><div style="margin-top: 7em;"></div></td></tr></tfoot>
</tbody></table>
	
	
</div>

</body>

</html>