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
				<h1>Uusi käyttäjä</h1>
			</div>

			<br>
			<br>

			<sf:form modelAttribute="user" method="post">

				<label for="firstName">Etunimi: </label>
				<br>
				<sf:input type="text" style="width: 15em; max-width: 100%" id="firstName" path="firstName" /> 
				<br><br>
								
				<label for="lastName">Sukunimi: </label>
				<br>
				<sf:input type="text" style="width: 15em; max-width: 100%" id="lastName" path="lastName" /> 
				<br><br>
				
				<label for="email">Sähköposti: </label>
				<br>
				<sf:input type="text" style="width: 15em; max-width: 100%" id="email" path="email" /> 
				<br><br>
				
				<label for="userName">Käyttäjätunnus: </label>
				<br>
				<sf:input type="text" style="width: 15em; max-width: 100%" id="userName" path="userName" /> 
				<br><br>
				
				<label for="password">Salasana: </label>
				<br>
				<sf:input type="password" style="width: 15em; max-width: 100%" id="password" path="password" /> 
				<br><br>
				
				<label for="confirmPassword">Vahvista salasana: </label>
				<br>
				<input type="password" style="width: 15em; max-width: 100%" id="confirmPassword" name="confirmPassword" /> 
				<br><br>
				
				<label for="role">Käyttäjäryhmä: </label>
				<br>				
				<sf:select style="width: auto; max-width: 100%" id="role" path="role"
					class="form-control">
					<c:forEach var="role" items="${roles}">
						<option value="${role.value}">${role.key}</option>
					</c:forEach>
				</sf:select>
				<br><br>
		
				<br>
				<button class="btn btn-large btn-primary" action="submit">Tallenna</button>
				<a class="btn btn-large btn-primary" href="/ProNaseva">Hylkää</a>
			</sf:form>
		</div>
		<br>
			
<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />