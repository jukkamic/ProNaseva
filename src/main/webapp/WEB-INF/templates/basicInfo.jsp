<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/templates/includes/header.jsp" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<body>

<div id="wrap">
	<div class="container">
        <div class="page-header">
			<h1>Ajoneuvon tiedot</h1>
		</div>

	<br><br>
	<sf:form modelAttribute="report" action="submitBasicInfo" method="post">
		<label for="model">Ajoneuvon malli:</label>  
		<sf:input type="text" path="vehicleModel" id="model" class="form-control" style="width: 25em"></sf:input>
			<br>
		<label for="registerNumber">Ajoneuvon rekisterinumero:</label> 			
		<sf:input type="text" id="registerNumber" path="vehicleRegistrationNumber" class="form-control" 
		 	style="width: 25em"></sf:input>
		 	<br>
		<label for="registrationDate">Ajoneuvon rekisteröintipäivämäärä:</label> 
		<sf:input type="text" id="registrationDate" path="vehicleRegistrationDate" class="form-control" 
		 	style="width: 25em"></sf:input>
		 	<br>
		<label for="mileage">Ajoneuvon mittarilukema:</label> 
		<sf:input type="text" id="mileage" path="vehicleMileage" class="form-control" 
		 	style="width: 25em"></sf:input>
		 	<br>
		
		<br><br>
		<sf:button type="submit" class="btn btn-large btn-primary">Seuraava</sf:button>
	</sf:form>

</div>

<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />
