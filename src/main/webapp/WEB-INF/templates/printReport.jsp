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


h3 {
	
	padding-top: 1em; 
	
}

h4 {
	padding-left: 1.5em;
	padding-right: 2em; 
	padding-top: 0.7em;
}

p {

	padding-left: 1.5em;
	padding-right: 2em;
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


}

</style>



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
			<h1>Autoasi</h1>
			<h1>Korjaamotestiraportti</h1>
				<br>
				<br>
				<div style="border-bottom: 1px solid #eee;">
					<h2>${report.workshop.name}</h2>
					<h2>16.10.2014</h2>
					<br><br>
					
				</div>
				<br><br>
			
			<table>
				<tr>
					<td>
						<h2 style="display: inline; padding-right: 3em;">Yleisarvosana : </h2>
					</td>
					<td>
						<p style="display: inline; font-size: 8em; padding: 0; margin: 0"> &#128530;</p>
					</td>
				</tr>
			</table>
			</div>
				<div class="newpage"></div>	
				
			<div style="width: 100%; margin-top: 5em; margin-left: 7em;">
				
			<table class="summaryTable">
				<tr>
					<th align="left" style="padding-top: 0.5em; padding-bottom: 0.5em;">
						Tulosten yhteenveto
					</th>
					<th style="padding-top: 0.5em; padding-bottom: 0.5em;" align = "left">Pisteet</th>
					<th style="padding-top: 0.5em; padding-bottom: 0.5em; " align = "left">Arvosana</th>
				</tr>
				
				
				<tr>
				<td style="padding-top: 0.5em; padding-bottom: 0.5em;">
					Tarkastuskohteet
				</td>
				<td style="padding-top: 0.5em; padding-bottom: 0.5em" align = "left">
					${report.questionGroupScore[1].score} / ${report.questionGroupScore[1].maxScore}
				</td>
				<td>
					<p style="font-size: 2em; padding: 0; margin: 0">&#128530;</p> <!-- Frown -->
				</td>
				</tr>

				<tr>
				<td width="15em" style="padding-top: 0.5em; padding-bottom: 0.5em; padding-right: 9em" align = "left">
					TCT-Palvelupisteet
				</td>
				<td style="padding-top: 0.5em; padding-bottom: 0.5em; padding-right: 4em" align = "left">
					${report.questionGroupScore[2].score} / ${report.questionGroupScore[2].maxScore}
				</td>
				<td>
					<p style="font-size: 2em; padding: 0; margin: 0">&#128528;</p> <!-- Neutral -->
				</td>
				</tr>				
								
				<tr><td width="15em" style="padding-top: 0.5em; padding-bottom: 2em;"><b>Yhteensä</b></td>
				<c:set var="tarkastuskohteet" value="${report.questionGroupScore[1].score}" />
				<c:set var="palvelu" value="${report.questionGroupScore[2].score}" />
				<c:set var="tarkastuskohteetMax" value="${report.questionGroupScore[1].maxScore}" />
				<c:set var="palveluMax" value="${report.questionGroupScore[2].maxScore}" />
				
				<td style="padding-top: 0.5em; padding-bottom: 2em;">${tarkastuskohteet + palvelu} / ${tarkastuskohteetMax + palveluMax}</td>
				<td></td>
				</tr>
				
				
				 
				<tr>
				<td width="15em" style="padding-top: 0.5em; padding-bottom: 0.5em;">
					${report.reportTemplate.reportParts[1].title}
				</td>
				<td style="padding-top: 0.5em; padding-bottom: 0.5em; ">
					<c:if test="${report.reportPartScore[1].showScore == true}" >
					${report.reportPartScore[1].scorePercentage} %
					</c:if>
				</td>
				<td>

					<p style="font-size: 2em; padding: 0; margin: 0">&#128530;</p> <!-- Frown -->
				</td>
				</tr>
				
				<tr>
				<td width="15em" style="padding-top: 0.5em; padding-bottom: 0.5em;">
					${report.reportTemplate.reportParts[2].title}
				</td>
				<td style="padding-top: 0.5em; padding-bottom: 0.5em;">
					<c:if test='${report.reportPartScore[2].showScore == true}' >
					${report.reportPartScore[2].scorePercentage} %
					</c:if>
				</td>
				<td style="padding-top: 0.5em; padding-bottom: 0.5em;">
					<p style="font-size: 2em; padding: 0; margin: 0">&#128528;</p> <!-- Frown -->
				</td>
				</tr>
				
				<tr>
				<td width="15em" style="padding-top: 0.5em; padding-bottom: 0.5em;">
					${report.reportTemplate.reportParts[3].title}
				</td>
				<td style="padding-top: 0.5em; padding-bottom: 0.5em;">
					<c:if test='${report.reportPartScore[3].showScore == true}' >
					${report.reportPartScore[3].scorePercentage} %
					</c:if>
				</td>
				<td style="padding-top: 0.5em; padding-bottom: 0.5em;">
					<p style="font-size: 2em; padding: 0; margin: 0">&#128522;</p> <!-- Happy -->
				</td>
				</tr>
				
				<tr>
				<td width="15em" style="padding-top: 0.5em; padding-bottom: 0.5em;">
					${report.reportTemplate.reportParts[4].title}
				</td>
				<td style="padding-top: 0.5em; padding-bottom: 0.5em;">
					<c:if test='${report.reportPartScore[4].showScore == true}' >
					${report.reportPartScore[4].scorePercentage} %
					</c:if>
				</td>
				<td style="padding-top: 0.5em; padding-bottom: 0.5em;">
					<p style="font-size: 2em; padding: 0; margin: 0">&#128528;</p> <!-- Neutral -->
				</td>
				</tr>
				
				<tfoot>
				<tr>
				<td style="padding-top: 0.5em; padding-bottom: 0.5em;"><b>Yhteensä:</b></td>
				<td style="padding-top: 0.5em; padding-bottom: 0.5em;"><b>${report.totalScorePercentage} %</b></td>
				<td style="padding-top: 0.5em; padding-bottom: 0.5em;">

				</td>
				</tr>
				</table>

			</div>	
			
			
			
			
			<div class="newpage"></div>	
								
			<h1>Keskeiset  huomiot</h1>

		
			
							<!-- Osio A kysymys 3c -->
						<h2>Osa A - Ajanvaraus korjaamolle</h2>
						<h3>3. Tilattava huolto</h3>
						<h3>3.6. Saiko asiakas mielestään huoltoajan kohtuullisen ajan sisällä?</h3>
						<h3 style="display: inline; float:right;">0/2</h3>
						<table>
							<tr>
								<td	style="padding-left: 1.5em;">
									&#9744;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Kyllä
								</td>
								</tr>
								<tr>
								<td	style="padding-left: 1.5em;">
									&#9745;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Ei									
								</td>
							</tr>
						</table>
						<h4>Huomioita:</h4>
						<p>Tarjottu aika ensimmäinen vapaa aika, normaali huoltoon jono 8 päivää, yli viikko (5 arkipäivää).</p>
			
			
						<br>
						<!-- Osio B kysymys 2a -->
						<h2>Osa B - Työnvastaanotto</h2>
						<h3>2. Keskustelu työnvastaanotossa</h3>
						<h3>2.1. Kävikö palvelleen huoltoneuvojan nimi ilmi?</h3>
						<h3 style="display: inline; float:right;">0/2</h3>
						<table>
							<tr>
								<td	style="padding-left: 1.5em;">
									&#9744;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Kyllä
								</td>
								</tr>
								<tr>
								<td	style="padding-left: 1.5em;">
									&#9745;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Ei
								</td>
							</tr>
						</table>
						<h4>Huomioita:</h4>
						<p>Ei nimikylttiä.</p>

						<br>
						<!-- Osio B kysymys 2b -->
						
						<h3>2.2. Varmistettiinko ajanvarauksessa sovitut asiat? (Huolto, tilatut lisätyöt, hinta- ja aika-arvio)</h3>
						<h3 style="display: inline; float:right;">0/2</h3>
						<table>
							<tr>
								<td	style="padding-left: 1.5em;">
									&#9744;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Kyllä
								</td>
								</tr>
								<tr>
								<td	style="padding-left: 1.5em;">
									&#9745;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Ei
								</td>
							</tr>
						</table>
						<h4>Huomioita:</h4>
						<p>Huolto tiedossa. Ajanvarauksessa annettua kustannusarviota ei varmistettu.</p>

						<!-- Osio B kysymys 2c -->
						<br>
						<h3>2.3. Pyydettiinkö asiakasta ottamaan huoltokirja mukaan saapuessaan työnvastaanottoon?</h3>
						<h3 style="display: inline; float:right;">0/2</h3>
						<table>
							<tr>
								<td	style="padding-left: 1.5em;">
									&#9744;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Asiakasta pyydettiin ottamaan huoltokirja
								</td>
								</tr>
								<tr>
								<td	style="padding-left: 1.5em;">
									&#9745;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Ei
								</td>
								</tr>
								<tr>
								<td	style="padding-left: 1.5em;">
									&#9744;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Asiakas otti huoltokirjan pyytämättä
								</td>
								
								
							</tr>
						</table>
								
						<h3>2.3.1. Jos ei: kysyikö korjaamon edustaja luvan ottaa se autosta?</h3>
						<h3 style="display: inline; float:right;">0/2</h3>
						<table>
							<tr>
								<td	style="padding-left: 1.5em;">
									&#9744;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Kyllä
								</td>
								</tr>
								<tr>
								<td	style="padding-left: 1.5em;">
									&#9745;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Ei
								</td>
								</tr>
								</table>
						<h4>Huomioita:</h4>
						<p>Asiakas jätti huoltokirjan autoon. Ei puhetta huoltokirjasta. Ei muistutuskylttiä.</p>
							
												
					
						<h3 class="newpage">3. Töiden kirjaaminen</h3>
						<h3>3.14. Tarjottiinko kopiota työmääräyksestä?</h3>
						<h3 style="display: inline; float:right;">0/2</h3>
						<table>
							<tr>
								<td	style="padding-left: 1.5em;">
									&#9744;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Kyllä
								</td>
								</tr>
								<tr>
								<td	style="padding-left: 1.5em;">
									&#9745;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Ei
								</td>
								</tr>
								<tr>
								<td	style="padding-left: 1.5em;">
									&#9744;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Työnvastaanotossa ohje, jossa kerrotaan, että saa pyydettäessä.
								</td>
							</tr>
						</table>

				<!-- Osa C kysymys 3 c -->
						<h2>Osa D - Auton luovutus</h2>
						<h3>2. Lasku</h3>
						<h3>Vastasiko lasku annettua kustannusarviota huomioiden sovitut lisätyöt:</h3>
						<h3 style="display: inline; float:right;">8/8</h3>
						<table>
							<tr>
								<td	style="padding-left: 1.5em;">
									&#9745;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Tarkasti (+/- 10eur)
								</td>
								</tr>
								<tr>
								<td	style="padding-left: 1.5em;">
									&#9744;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Alitti enemmän kuin 10eur
								</td>
								</tr>
								<tr>
								<td	style="padding-left: 1.5em;">
									&#9744;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Ylitti yli 10eur, mutta alle 15 %
								</td>
								</tr>
								<tr>
								<td	style="padding-left: 1.5em;">
									&#9744;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Ylitti yli 15 %
								</td>
								</tr>
								<tr>
								
								<td	style="padding-left: 1.5em;">
									&#9744;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Ei saatu vaikka kysyttiin
								</td>
								</tr>
								<tr>
								
								<td	style="padding-left: 1.5em;">
									&#9744;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Ei kysytty
								</td>
								</tr>
								<tr>
								
								<td	style="padding-left: 1.5em;">
									&#9744;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Huolto-/leasingsopimus
								</td>
														
								
							</tr>
						</table>
						
						<h3>3. Asiakkaan neuvonta</h3>
						<h3>3.1. Kerrottiinko seuraavan huollon ajankohdasta?</h3>
						<h3 style="display: inline; float:right;">0/2</h3>
						<table>
							<tr>
								<td	style="padding-left: 1.5em;">
									&#9744;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Kyllä
								</td>
								</tr>
								<tr>
								<td	style="padding-left: 1.5em;">
									&#9745;
									&nbsp;
								</td>
								<td style="padding-right: 2em;">	
									Ei
								</td>
								</tr>
								</table>


			
			
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
					<c:if test="${question.class == 'class fi.testcenter.domain.MultipleChoiceQuestion'}">
				
						<div class="noPageBreak">
						
						
						<h3>${questionGroupCounter.count}.${questionCounter.count}. ${question.question}</h3>
						<c:if test="${report.answers[answerIndexCounter].showScore == true}">
						<h3 style="display: inline; float:right;">${report.answers[answerIndexCounter].score}/${report.answers[answerIndexCounter].maxScore}</h3>
						</c:if>
						
						<table>
							<c:forEach var="option" items="${question.options}" varStatus="optionsCounter">
								
									<tr>
															
										<c:choose>
											<c:when test="${report.answers[answerIndexCounter].chosenOptionIndex == optionsCounter.index}">
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
							<c:set var="remarks" value="${report.answers[answerIndexCounter].remarks}" />
							<c:if test="${remarks !='' and remarks != null}"> 
								<div class="noPageBreak">
									<h4>Huomioita:</h4>
									<p>${report.answers[answerIndexCounter].remarks}</p>
								</div>
							</c:if>
							
					</c:if>

										
					<!-- Text field question -->
					<c:if test="${question.class == 'class fi.testcenter.domain.TextQuestion'}">
					
						<div class="noPageBreak">
						
							<h3>${questionGroupCounter.count}.${questionCounter.count}. ${question.question}</h3>
							<p>${report.answers[answerIndexCounter].answer}</p> 
						</div>
						
					</c:if>
					
					
					<c:set var="answerIndexCounter" value="${answerIndexCounter + 1}" scope="request" />
					
					<c:if test="${not empty question.subQuestions}">
						<c:set var="mainQuestion" value="${question}" scope="request" />
						<c:set var="questionGroupNumber" value="${questionGroupCounter.count}" scope="request" />
						<c:set var="mainQuestionNumber" value="${questionCounter.count}" scope="request" />
						<div style="margin-left: 3em;">
							<jsp:include page="/WEB-INF/templates/printReportSubQuestions.jsp" />
						</div>				
					</c:if>
					
				</c:forEach> <!-- Question loop end -->
										
											
				<c:if test="${report.questionGroupScore[questionGroupScoreIndexCounter].showScore == true}">
					<h4 style="font-weight: bold; padding-right: 2em; padding-bottom: 2em; padding-top: 2em; text-align: right;">Pisteet: ${report.questionGroupScore[questionGroupScoreIndexCounter].score} / 
					${report.questionGroupScore[questionGroupScoreIndexCounter].maxScore}</h4>
					<br>
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