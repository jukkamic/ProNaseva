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
							
								
			<c:if test="${openSummaryPanel == 'true'}">
				<div id="${bootstrapPanelCounter}" class="panel-collapse start">
			</c:if>
			<c:if test="${openSummaryPanel != 'true'}">
				<div id="${bootstrapPanelCounter}" class="panel-collapse collapse start">
			</c:if>
								
								
			<div class="panel-body">
			<br>
			<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-save" style="text-decoration: none;"></span> Tallenna arvostelu</button>
			
			<br><br><br>
			<div style="border-bottom: 3px solid #eee;">
			<h3 style="display: inline"><b>Yleisarvosana: </b></h3>


			<div class="Demo-boot" style="padding-top: 15px;">
				<div class="btn-group" data-toggle="buttons">
					
						<c:if test="${readyReport.overallResultSmiley == 'SMILE' }"> 
							<label class="btn btn-primary active">
 						</c:if>
						<c:if test="${readyReport.overallResultSmiley != 'SMILE' }"> 
							<label class="btn btn-primary">
 						</c:if>
					
						<sf:radiobutton id="button" path="overallResultSmiley" value="SMILE" /> <i class="fa fa-smile-o fa-2x"></i>
			
						</label>
						
						<c:if test="${readyReport.overallResultSmiley == 'NEUTRAL' }"> 
							<label class="btn btn-primary active">
 						</c:if>
						<c:if test="${readyReport.overallResultSmiley != 'NEUTRAL' }"> 
							<label class="btn btn-primary">
 						</c:if>
					
						<sf:radiobutton id="button" path="overallResultSmiley" value="NEUTRAL"/> <i class="fa fa-meh-o fa-2x"></i>
			
						</label>
						
						<c:if test="${readyReport.overallResultSmiley == 'FROWN' }"> 
							<label class="btn btn-primary active">
 						</c:if>
						<c:if test="${readyReport.overallResultSmiley != 'FROWN' }"> 
							<label class="btn btn-primary">
 						</c:if>
					
						<sf:radiobutton id="button" path="overallResultSmiley" value="FROWN"/> <i class="fa fa-frown-o fa-2x"></i>
			
						</label>
					</div>
				</div>
				<br><br>
				</div>
		
				<c:set var="highlightGroupsScore" value="0" />
				<c:set var="highlightGroupsMaxScore" value="0" />
				<c:set var="showQuestionGroupHighlightsTitle" value="false" />
				<div style="border-bottom: 3px solid #eee;">
				
				
				<c:forEach var="questionGroupScore" items="${readyReport.questionGroupScore}" varStatus="questionGroupScoreCounter">
					
						<c:if test="${questionGroupScore.questionGroup.showInReportSummary == true}">
								<c:if test="${showQuestionGroupHighlightsTitle == 'false'}">
									<h3><b>Yhteenveto: </b></h3>
									
									<c:set var="showQuestionGroupHighlightsTitle" value="true" />
								</c:if>
								<h3>${questionGroupScore.questionGroup.title}</h3>
																
								<c:if test="${questionGroupScore.maxScore > 0}">
									<h4>Pisteet: ${questionGroupScore.score}/${questionGroupScore.maxScore}</h4>
								</c:if>
						
								<c:if test="${questionGroupScore.maxScore == 0}">
									<h4>Pisteet: --</h4>
								</c:if>
								
								
								
								<c:set var="highlightGroupsScore" value="${highlightGroupsScore + questionGroupScore.score}" />
								<c:set var="highlightGroupsMaxScore" value="${highlightGroupsMaxScore + questionGroupScore.maxScore}" />
								
								<c:if test="${questionGroupScore.maxScore > 0 }">
									<div class="Demo-boot" style="padding-top: 15px;">
									<div class="btn-group" data-toggle="buttons">
									
										<c:if test="${questionGroupScore.scoreSmiley == 'SMILE' }"> 
											<label class="btn btn-primary active">
				 						</c:if>
										<c:if test="${questionGroupScore.scoreSmiley != 'SMILE' }"> 
											<label class="btn btn-primary inline">
				 						</c:if>
									
										<sf:radiobutton id="button" path="questionGroupScore[${questionGroupScoreCounter.index}].scoreSmiley" value="SMILE" /> <i class="fa fa-smile-o fa-2x"></i>
							
										</label>
										
										<c:if test="${questionGroupScore.scoreSmiley == 'NEUTRAL' }"> 
											<label class="btn btn-primary active">
				 						</c:if>
										<c:if test="${questionGroupScore.scoreSmiley != 'NEUTRAL' }"> 
											<label class="btn btn-primary">
				 						</c:if>
									
										<sf:radiobutton id="button" path="questionGroupScore[${questionGroupScoreCounter.index}].scoreSmiley" value="NEUTRAL"/> <i class="fa fa-meh-o fa-2x"></i>
							
										</label>
										
										<c:if test="${questionGroupScore.scoreSmiley == 'FROWN' }"> 
											<label class="btn btn-primary active">
				 						</c:if>
										<c:if test="${questionGroupScore.scoreSmiley != 'FROWN' }"> 
											<label class="btn btn-primary">
				 						</c:if>
									
										<sf:radiobutton id="button" path="questionGroupScore[${questionGroupScoreCounter.index}].scoreSmiley" value="FROWN"/> <span class="fa fa-frown-o fa-2x"></span>
							
										</label>
									</div>
								</div>
								</c:if>
							<br>	
						</c:if>
						
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
					<c:forEach var="reportPartScore" items="${readyReport.reportPartScore}" varStatus="reportPartScoreCounter">
					<c:if test="${reportPartScore.reportPart.showScoreInReportHighlights == 'true' }">
						<h3>${reportPartScore.reportPart.title}</h3>
						<c:if test="${reportPartScore.maxScore > 0}">
							<h4>Pisteet: ${reportPartScore.scorePercentage} %</h4>
						</c:if>
						
						<c:if test="${reportPartScore.maxScore == 0}">
							<h4>Pisteet: --</h4>
						</c:if>
						
						<c:if test="${reportPartScore.maxScore > 0 }">
							<div class="Demo-boot" style="padding-top: 15px;">
								<div class="btn-group" data-toggle="buttons">
							
								<c:if test="${reportPartScore.scoreSmiley == 'SMILE' }"> 
									<label class="btn btn-primary active">
		 						</c:if>
								<c:if test="${reportPartScore.scoreSmiley  != 'SMILE' }"> 
									<label class="btn btn-primary">
		 						</c:if>
							
								<sf:radiobutton id="button" path="reportPartScore[${reportPartScoreCounter.index}].scoreSmiley" value="SMILE" /> <i class="fa fa-smile-o fa-2x"></i>
					
								</label>
								
								<c:if test="${reportPartScore.scoreSmiley  == 'NEUTRAL' }"> 
									<label class="btn btn-primary active">
		 						</c:if>
								<c:if test="${reportPartScore.scoreSmiley  != 'NEUTRAL' }"> 
									<label class="btn btn-primary">
		 						</c:if>
							
								<sf:radiobutton id="button" path="reportPartScore[${reportPartScoreCounter.index}].scoreSmiley" value="NEUTRAL"/> <i class="fa fa-meh-o fa-2x"></i>
					
								</label>
								
								<c:if test="${reportPartScore.scoreSmiley == 'FROWN' }"> 
									<label class="btn btn-primary active">
		 						</c:if>
								<c:if test="${reportPartScore.scoreSmiley != 'FROWN' }"> 
									<label class="btn btn-primary">
		 						</c:if>
							
								<sf:radiobutton id="button" path="reportPartScore[${reportPartScoreCounter.index}].scoreSmiley" value="FROWN"/> <i class="fa fa-frown-o fa-2x"></i>
					
								</label>
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
