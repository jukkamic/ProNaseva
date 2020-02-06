<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div style="width: 100%; margin-top: 5em; margin-left: 7em;">

<br>
<table class="summaryTable" width="100%">
	<tr>
		<th width="50%" align="left" style="padding-top: 0.5em; padding-bottom: 0.5em;">
			Tulosten yhteenveto
		</th>
		<th  style="padding-top: 0.5em; padding-bottom: 0.5em;" align = "left">Pisteet</th>
		<th style="padding-top: 0.5em; padding-bottom: 0.5em;" align = "left">Arvosana</th>
	</tr>
	
	<!-- Jos raportissa on yhteenvedossa näytettäviä kysymysryhmiä, näytetään niiden pisteet erikseen ensiksi -->

<c:set var="highlightGroupsScore" value="0" />
<c:set var="highlightGroupsMaxScore" value="0" />
<c:set var="hasQuestionGroupHighlights" value="false" />


		<c:forEach var="questionGroup" items="${report.reportQuestionGroup}">
			
			<c:if test="${questionGroup.questionGroup.showInReportSummary == true}">
				<c:set var="hasQuestionGroupHighlights" value="true" />
					<tr>
						<td style="padding-top: 0.5em; padding-bottom: 0.5em;">
							${questionGroupScore.questionGroup.title}
						</td>
						<c:if test="${questionGroupScore.maxScore > 0}">
							<td style="padding-top: 0.5em; padding-bottom: 0.5em" align = "left">
								${questionGroupScore.score} / ${questionGroupScore.maxScore}
							</td>
						</c:if>
						<c:if test="${questionGroupScore.maxScore == 0}">
							<td style="padding-top: 0.5em; padding-bottom: 0.5em" align = "left">
								--
							</td>
						</c:if>
						<c:set var="highlightGroupsScore" value="${highlightGroupsScore + questionGroupScore.score}" />
						<c:set var="highlightGroupsMaxScore" value="${highlightGroupsMaxScore + questionGroupScore.maxScore}" />
						
						<td>
							<c:choose>
								<c:when test="${questionGroupScore.scoreSmiley == 'SMILE' }">
									<i class="fa fa-smile-o fa-3x"></i>
								</c:when>
								<c:when test="${questionGroupScore.scoreSmiley == 'NEUTRAL' }">
									<i class="fa fa-meh-o fa-3x"></i>
								</c:when>
								<c:when test="${questionGroupScore.scoreSmiley == 'FROWN' }">
									<i class="fa fa-frown-o fa-3x"></i>
								</c:when>
								<c:otherwise>
									--
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		
	
<c:if test="${hasQuestionGroupHighlights == 'true'}">
	<tr><td width="15em" style="padding-top: 0.5em; padding-bottom: 2em;"><b>Yhteensä</b></td>
		
	<td style="padding-top: 0.5em; padding-bottom: 2em;">${highlightGroupsScore} / ${highlightGroupsMaxScore}</td>
	<td></td>
	</tr>
</c:if>
	
<c:forEach var="reportPartScore" items="${readyReport.reportPartScore}" varStatus="reportPartScoreCounter">
	
	<!-- POISTA E-OSAA KOSKEVA IF-LAUSEKE, TILAPÄINEN !!! -->
	
	<c:if test="${reportPartScore.reportPart.title != 'Osa E - Korjaamon yhteistyö' }">
	<c:if test="${reportPartScore.reportPart.showScoreInReportHighlights == 'true' }">
	 
		<tr>
			<td width="15em" style="padding-top: 0.5em; padding-bottom: 0.5em;">
				${reportPartScore.reportPart.title}
			</td>
			<td style="padding-top: 0.5em; padding-bottom: 0.5em; ">
				<c:if test="${reportPartScore.maxScore > 0}">
					${reportPartScore.scorePercentage} %
				</c:if>
				<c:if test="${reportPartScore.maxScore == 0}">
					--
				</c:if>
			</td>
			<td>
				<c:choose>
					<c:when test="${reportPartScore.scoreSmiley == 'SMILE' }">
						<i class="fa fa-smile-o fa-3x"></i>
					</c:when>
					<c:when test="${reportPartScore.scoreSmiley == 'NEUTRAL' }">
						<i class="fa fa-meh-o fa-3x"></i>
					</c:when>
					<c:when test="${reportPartScore.scoreSmiley == 'FROWN' }">
						<i class="fa fa-frown-o fa-3x"></i>
					</c:when>
					<c:otherwise>
						--
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</c:if>
</c:if>
</c:forEach>
		

	
	<tfoot>
	<tr>
	<td style="padding-top: 0.5em; padding-bottom: 0.5em;"><b>Yhteensä:</b></td>
	<td style="padding-top: 0.5em; padding-bottom: 0.5em;"><b>${report.totalScorePercentage} %</b></td>
	<td style="padding-top: 0.5em; padding-bottom: 0.5em;">

	</td>
	</tr>
</table>

</div>	


