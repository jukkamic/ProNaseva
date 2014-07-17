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
	<sf:form modelAttribute="basicInfo" action="submitBasicInfo" method="post">
		Ajoneuvon malli:  <sf:input type="text" path="model" class="form-control" 
			style="width: 25em"></sf:input>
		<br>
		Kilometrimäärä: <sf:input type="text" path="mileage" class="form-control" 
		 	style="width: 25em"></sf:input>
		<br><br>
		<sf:button type="submit" class="btn btn-large btn-primary">Seuraava</sf:button>
	</sf:form>

</div>

<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />
