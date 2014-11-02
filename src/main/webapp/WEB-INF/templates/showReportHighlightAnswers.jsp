<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    
    
    			<h3>Tiivistelmä</h3>
			<br>
			<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a style="font-size: 1.5em; text-decoration: none; display: block;"
										data-toggle="collapse" data-parent="#accordion"
										href="#${bootstrapPanelCounter}">Huomionarvoiset vastaukset
									</a>
								</h4>
							</div> 
							
 							<div id="${bootstrapPanelCounter}" class="panel-collapse collapse start"> 
 							<div class="panel-body">

 							
							<c:forEach var="reportHighlight" items="${report.reportHighlights}">
								<div style="border-bottom: 3px solid #eee;">
								<c:if test="${reportHighlight.reportPart.title != reportPartTitle}">
									<h3>${reportHighlight.reportPart.title}</h3>
									<c:set var="reportPartTitle" value="${reportHighlight.reportPart.title}" />
								</c:if>
								
								<c:if test="${reportHighlight.questionGroup.title != questionGroupTitle}">
									<h3>${reportHighlight.questionGroupOrderNumber}. ${reportHighlight.questionGroup.title}</h3>
									<c:set var="questionGroupTitle" value="${reportHighlight.questionGroup.title}" />
								</c:if>
								
								<!-- Multiple choice answer -->
								
								<c:if test="${reportHighlight.answer['class'] == 'class fi.testcenter.domain.MultipleChoiceAnswer'}">
									<h3>${reportHighlight.questionGroupOrderNumber}.${reportHighlight.questionOrderNumber}.
									<c:if test="${reportHighlight.subQuestionOrderNumber != null and reportHighlight.subQuestionOrderNumber != 0}">
										${reportHighlight.subQuestionOrderNumber}.
									</c:if>									
									
									${reportHighlight.answer.question.question}</h3>
									
									
											<div class="Demo-boot" style="padding-top: 15px;">
												<div class="btn-group" data-toggle="buttons">
													<c:forEach var="option" items="${reportHighlight.answer.question.options}" varStatus="optionsCounter">
																												
														<!-- Jos MultipleChoiceOption-oliolle on asetettu pitkää valintanapin tekstiä
																varten erillinen radiobuttonText, jossa napin teksti on jaettu kahdelle 
																riville <br> tägillä, näytetään radiobuttonText, muuten option teksti jossa 
																ei ole tägejä -->
														<c:choose>
															<c:when test="${option.radiobuttonText != null }">
																<c:choose>
																	<c:when test="${reportHighlight.answer.chosenOptionIndex == optionsCounter.index}">
																		<button class="btn btn-large btn-primary disabled" type="button">
																			${option.radiobuttonText}
																																																			
																		</button>
																	</c:when>
																	<c:otherwise>
																		<button class="btn btn-large btn-default" type="button" disabled>
																			${option.radiobuttonText}
																		</button>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${reportHighlight.answer.chosenOptionIndex == optionsCounter.index}">
																		<button class="btn btn-large btn-primary disabled" type="button">
																			${option.option}
																			
																		</button>
																	</c:when>
																	<c:otherwise>
																		<button class="btn btn-large btn-default" type="button" disabled>
																			${option.option}
																		</button>
																	</c:otherwise>
																</c:choose>	
															</c:otherwise>
														</c:choose>														
													</c:forEach> 
													
																								
												</div>
											</div>
											
											<c:if test="${reportHighlight.answer.remarks != '' and reportHighlight.answer.remarks != null}">
												<br>
												<h4>Huomioita:</h4>
												<p style="font-size: 1.2em;">${reportHighlight.answer.remarks}</p>
											</c:if>
											<br><br>
										</c:if>
								
								
								
								
								<!-- Text answer -->
								
								<c:if test="${reportHighlight.answer['class'] == 'class fi.testcenter.domain.TextAnswer'}">
									<h3>${reportHighlight.questionGroupOrderNumber}.${reportHighlight.questionOrderNumber}.
									<c:if test="${reportHighlight.subQuestionOrderNumber != null and reportHighlight.subQuestionOrderNumber != 0}">
										${reportHighlight.subQuestionOrderNumber}.
									</c:if>									
									${reportHighlight.answer.question.question}</h3>
									<p style="font-size: 1.2em;">${reportHighlight.answer.answer}</p>
									<br>
								</c:if>
								
							</div>
							</c:forEach>
	
							</div>
							</div>
			
			</div> 
			<br>