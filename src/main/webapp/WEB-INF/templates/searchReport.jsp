<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/templates/includes/header.jsp" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<body>
	<div id="wrap">
		<div class="container">
			<div class="page-header">
				<jsp:include page="/WEB-INF/templates/includes/pageHeaderRow.jsp" />
				<h1>Hae raportti</h1>
			</div>

			<br>
			<br>

			<sf:form modelAttribute="searchReportCriteria" action="searchReport" method="post">

				<label for="importerSelect">Maahantuoja: </label>
				<br>
				<sf:select style="width: auto; max-width: 100%" id="importerSelect" path="importerId"
					class="form-control">
						<option value="">-- Valitse --</option>
					<c:forEach var="importer" items="${importers}">
						<c:if test="${searchReportCriteria.importerId == importer.id}">
							<option value="${importer.id}" selected>${importer}</option>
						</c:if>
						<c:if test="${searchReportCriteria.importerId != importer.id}">
							<option value="${importer.id}">${importer}</option>
						</c:if>
					</c:forEach>
				</sf:select>
				<br>
				
				<label for="wrokshopSelect">Korjaamo: </label>
				<br>
				<sf:select style="width: auto; max-width: 100%" id="workshopSelect" path="workshopId"
					class="form-control">
					<option value="">-- Valitse --</option>
					<c:forEach var="workshop" items="${workshops}">
						<c:if test="${searchReportCriteria.workshopId == workshop.id}">
							<option value="${workshop.id}" selected>${workshop}</option>
						</c:if>
						<c:if test="${searchReportCriteria.workshopId != workshop.id}">
							<option value="${workshop.id}">${workshop}</option>
						</c:if>

					</c:forEach>
				</sf:select>
				<br>
				
				<label for="date"><h4>Raportit alkaen: </h4></label>
				<br>
				<sf:input path="startDate" name="date" class="datepicker" id="date" />
				<br><br>
				
				<label for="date"><h4>Raportit saakka: </h4></label>
				<br>
				<sf:input path="endDate" name="date" class="datepicker" id="endDate"/>	
				
				<br><br>
				
				<label for="userSelect">Tarkastaja: </label>
				<br>
				<sf:select style="width: auto; max-width: 100%" id="userSelect" path="userId"
					class="form-control">
					<option value="">-- Valitse --</option>
					<c:forEach var="user" items="${users}">
						<c:if test="${searchReportCriteria.userId == user.id}">
							<option value="${user.id}" selected>${user.firstName} ${user.lastName}</option>
						</c:if>
						<c:if test="${searchReportCriteria.userId != user.id}">
							<option value="${user.id}">${user.firstName} ${user.lastName}</option>
						</c:if>
						
					</c:forEach>
				</sf:select>
				<br><br>
				

				
				<button class="btn btn-large btn-primary" action="submit"><span class="glyphicon glyphicon-search" style="text-decoration: none;"></span> Hae</button>
				
				</sf:form>
		</div>

		<br>
		
		
      <script type="text/javascript">
            // When the document is ready
            $(document).ready(function () {
                
                $('#date').datepicker({
                   
                    language: "fi",
        			autoclose: true
                    
                });  
            
            });

            $(document).ready(function () {
                
                $('#endDate').datepicker({
                   
                    language: "fi",
        			autoclose: true
                    
                });  
            
            });
        </script>

			<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />