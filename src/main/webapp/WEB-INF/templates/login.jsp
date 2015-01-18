<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/templates/includes/header.jsp" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<body>
	<div id="wrap">
		<div class="container">
			<div class="page-header">
				<h1>Test-Center</h1>
			</div>

			<br>
			<br>
			
			<h2>Kirjaudu</h2>
			
			<br><br>
			<form method="post" action="/ProNaseva/j_spring_security_check">

				<label for="userName">Käyttäjätunnus: </label>
				<br>
				<input name="j_username" type="text" style="width: 15em; max-width: 100%" />
				<br><br>
				<label for="password">Salasana: </label>
				<br>
				<input name="j_password" type="password" style="width: 15em; max-width: 100%" />
				<br><br><br>
				<input class="btn btn-large btn-primary" type="submit" value="Kirjaudu" />
			</form>

		</div>
		
		
		<br>

	<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />