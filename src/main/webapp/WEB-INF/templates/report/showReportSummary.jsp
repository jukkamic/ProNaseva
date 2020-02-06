<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
    
    
    		<h3>Raportin yhteenveto</h3>
			<br>
			<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a style="font-size: 1.5em; text-decoration: none; display: block;"
										data-toggle="collapse" data-parent="#accordion"
										href="#${bootstrapPanelCounter}">Tulokset
									</a>
								</h4>
							</div> 
							
		<div id="${bootstrapPanelCounter}" class="panel-collapse collapse start"> 
		<div class="panel-body">
			<br>
			
			<c:if test="${loginRole == '[ROLE_ADMIN]' }">
				<a class="btn btn-default" style="text-decoration: none;" href="/ProNaseva/editSmileys"><span class="glyphicon glyphicon-edit" style="text-decoration: none;"></span> Muokkaa arvostelua</a>
				
			</c:if>
			<br><br><br>
			
			<div style="border-bottom: 3px solid #eee;">
			<h3 style="display: inline"><b>Yleisarvosana: </b></h3>


			<div class="Demo-boot" style="padding-top: 15px;">
				<div class="btn-group" data-toggle="buttons">
					
						<c:if test="${readyReport.overallResultSmiley == 'SMILE' }"> 
							<button class="btn btn-selectedOption disabled" type="button"><i class="fa fa-smile-o fa-2x smileys"></i></button>
						</c:if>
						
						<c:if test="${readyReport.overallResultSmiley != 'SMILE' }"> 
							<button class="btn btn-showSelections" type="button" disabled><i class="fa fa-smile-o fa-2x smileys"></i></button>
 						</c:if>

						<c:if test="${readyReport.overallResultSmiley == 'NEUTRAL' }"> 
							<button class="btn btn-selectedOption disabled" type="button"><i class="fa fa-meh-o fa-2x smileys"></i></button>
						</c:if>
						
						<c:if test="${readyReport.overallResultSmiley != 'NEUTRAL' }"> 
							<button class="btn btn-showSelections" type="button" disabled><i class="fa fa-meh-o fa-2x smileys"></i></button>
 						</c:if>
 						
 						<c:if test="${readyReport.overallResultSmiley == 'FROWN' }"> 
							<button class="btn btn-selectedOption disabled" type="button"><i class="fa fa-frown-o fa-2x smileys"></i></button>
						</c:if>
						
						<c:if test="${readyReport.overallResultSmiley != 'FROWN' }"> 
							<button class="btn btn-showSelections" type="button" disabled><i class="fa fa-frown-o fa-2x smileys"></i></button>
 						</c:if>
 				
					</div>
				</div>
				<br><br>
				</div>
		
				<c:set var="highlightGroupsScore" value="0" />
				<c:set var="highlightGroupsMaxScore" value="0" />
				<c:set var="showQuestionGroupHighlightsTitle" value="false" />
				<div style="border-bottom: 3px solid #eee;">
				
				<c:forEach var="reportPart" items="${readyReport.reportParts}" varStatus="reportPartCounter">
				<c:forEach var="questionGroup" items="${reportPart.reportQuestionGroups}" varStatus="questionGroupCounter">
					
						<c:if test="${questionGroup.reportTemplateQuestionGroup.showInReportSummary == true}">
								<c:if test="${showQuestionGroupHighlightsTitle == 'false'}">
									<h3><b>Yhteenveto: </b></h3>
									
									<c:set var="showQuestionGroupHighlightsTitle" value="true" />
								</c:if>
								<h3>${questionGroup.reportTemplateQuestionGroup.title}</h3>
																
								<c:if test="${questionGroup.maxScore > 0}">
									<h4>Pisteet: ${questionGroup.score}/${questionGroup.maxScore}</h4>
								</c:if>
						
								<c:if test="${questionGroup.maxScore == 0}">
									<h4>Pisteet: --</h4>
								</c:if>
								<c:if test="${questionGroup.score != -1 }">
									<c:set var="highlightGroupsScore" value="${highlightGroupsScore + questionGroup.score}" />
									<c:set var="highlightGroupsMaxScore" value="${highlightGroupsMaxScore + questionGroup.maxScore}" />
								</c:if>
								
								<c:if test="${questionGroup.maxScore > 0 }">
									<div class="Demo-boot" style="padding-top: 15px;">
									<div class="btn-group" data-toggle="buttons">
									
										<c:if test="${questionGroup.scoreSmiley == 'SMILE' }"> 
											<button class="btn btn-selectedOption disabled" type="button"><i class="fa fa-smile-o fa-2x"></i></button>
				 						</c:if>
										<c:if test="${questionGroup.scoreSmiley != 'SMILE' }"> 
											<button class="btn btn-showSelections" type="button" disabled><i class="fa fa-smile-o fa-2x"></i></button>
				 						</c:if>
									
									
										<c:if test="${questionGroup.scoreSmiley == 'NEUTRAL' }"> 
											<button class="btn btn-selectedOption disabled" type="button"><i class="fa fa-meh-o fa-2x"></i></button>
				 						</c:if>
										<c:if test="${questionGroup.scoreSmiley != 'NEUTRAL' }"> 
											<button class="btn btn-showSelections" type="button" disabled><i class="fa fa-meh-o fa-2x"></i></button>
				 						</c:if>
				 						
				 															
										<c:if test="${questionGroup.scoreSmiley == 'FROWN' }"> 
											<button class="btn btn-selectedOption disabled" type="button"><i class="fa fa-frown-o fa-2x"></i></button>
				 						</c:if>
										<c:if test="${questionGroup.scoreSmiley != 'FROWN' }"> 
											<button class="btn btn-showSelections" type="button" disabled><i class="fa fa-frown-o fa-2x"></i></button>
				 						</c:if>

									</div>
								</div>
								</c:if>
							<br>	
						</c:if>
						
					</c:forEach>
				</c:forEach>
								
				<br>
				<c:if test="${showQuestionGroupHighlightsTitle == 'true'}">

				<h4><b>Yhteensä: ${highlightGroupsScore}/${highlightGroupsMaxScore}</b></h4>
			
				<br>
				</c:if>			
			</div>
			<br>
			<div style="border-bottom: 3px solid #eee;">
			<h3><b>Raportin osien pisteet: </b></h3>
					<c:forEach var="reportPart" items="${readyReport.reportParts}" varStatus="reportPartCounter">
					
					<c:if test="${reportPart.reportTemplatePart.showScoreInReportHighlights == 'true' }">
						<h3>${reportPart.reportTemplatePart.title}</h3>
						<c:if test="${reportPart.maxScore > 0}">
							<h4>Pisteet: ${reportPart.scorePercentage} %</h4>
						</c:if>
						
						<c:if test="${reportPart.maxScore == 0}">
							<h4>Pisteet: --</h4>
						</c:if>
						
						<c:if test="${reportPart.maxScore > 0 }">
							<div class="Demo-boot" style="padding-top: 15px;">
								<div class="btn-group" data-toggle="buttons">
							
							
							
												
						<c:if test="${reportPart.scoreSmiley == 'SMILE' }">  
							<button class="btn btn-selectedOption disabled" type="button"><i class="fa fa-smile-o fa-2x"></i></button>
 						</c:if>
						<c:if test="${reportPart.scoreSmiley  != 'SMILE' }">  
							<button class="btn btn-showSelections" type="button" disabled><i class="fa fa-smile-o fa-2x"></i></button>
 						</c:if>
			
			
						<c:if test="${reportPart.scoreSmiley == 'NEUTRAL' }"> 
							<button class="btn btn-selectedOption disabled" type="button"><i class="fa fa-meh-o fa-2x"></i></button>
 						</c:if>
						<c:if test="${reportPart.scoreSmiley != 'NEUTRAL' }"> 
							<button class="btn btn-showSelections" type="button" disabled><i class="fa fa-meh-o fa-2x"></i></button>
 						</c:if>
 						
 															
						<c:if test="${reportPart.scoreSmiley == 'FROWN' }"> 
							<button class="btn btn-selectedOption disabled" type="button"><i class="fa fa-frown-o fa-2x"></i></button>
 						</c:if>
						<c:if test="${reportPart.scoreSmiley != 'FROWN' }"> 
							<button class="btn btn-showSelections" type="button" disabled><i class="fa fa-frown-o fa-2x"></i></button>
 						</c:if>
			

							</div>
							</div>
							</c:if>
							<br>	
						
						</c:if>
					</c:forEach>
								
				<br>
				</div>
				<br>
				
				<h3><b>Raportin kokonaispisteet: ${report.totalScorePercentage} %</b></h3>
								
			</div>
			<br>
			
			<c:set var="bootstrapPanelCounter" value="2" scope="request" />
			</div> 
			
			</div>