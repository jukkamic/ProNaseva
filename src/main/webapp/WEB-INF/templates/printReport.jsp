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
				
				
				<h2 class="newpage" style="border-bottom: 1px solid #eee;">${questionGroup.title}</h2>
				
								
									<!-- Questions loop -->
									
									<c:forEach var="question" items="${questionGroup.questions}" varStatus="questionCounter">
		

										<!--  Text area question -->
										<c:if
											test="${question.class == 'class fi.testcenter.domain.TextareaQuestion'}">
											<h3>${questionCounter.count}. ${question.question}</h3>
											<br>
											<p style="width:100%;">${question.answer.answer}</p>
										</c:if>
										
										<!-- Text field question -->
										<c:if
											test="${question.class == 'class fi.testcenter.domain.TextfieldQuestion'}">
											<h3>${questionCounter.count}. ${question.question}</h3>
											<br>
											<p style="width:100%;">${question.answer.answer}</p> 
										</c:if>
							
					</c:forEach>
					
					</c:forEach>

				<br>
				

				<br>
</body>
				
		<script>
		window.print();
		window.location = "/ProNaseva/printDone"
		</script>  