<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/templates/includes/header.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<style>
p {
	display: inline;
	
}

label {
	padding-bottom: 1.5em;
}
</style>

<body>
	<div id="wrap">
		<div class="container">
			<div class="page-header">
				<jsp:include page="/WEB-INF/templates/includes/pageHeaderRow.jsp" />
				<h1>Käyttäjätiedot</h1>
			</div>

			<br>
			<br>
				<label for="firstName">Etunimi: </label>
				<p id="firstName">${user.firstName}</p> 
				<br>
												
				<label for="lastName">Sukunimi: </label>
				<p>${user.lastName}</p> 
				<br>
				
				
				<label for="email">Sähköposti: </label>
				<p id="email">${user.email}</p> 
				<br>
				
				<label for="userName">Käyttäjätunnus: </label>
				<p id="userName">${user.userName}</p> 
				<br>
								
				<label for="authorities">Käyttöoikeudet: </label>
				<c:choose>
					<c:when test="${user.role == 'ROLE_ADMIN'}">
						<p id="authorities">Admin </p>
					</c:when>
					<c:when test="${user.role == 'ROLE_TESTER'}">
						<p id="authorities">Testaaja</p>
					</c:when>
					<c:when test="${user.role == 'ROLE_CLIENT'}">
						<p id="authorities">Asiakas</p>
					</c:when>
					<c:when test="${user.role == 'ROLE_SUPPORT'}">
						<p id="authorities">IT-tuki</p>
					</c:when>
				</c:choose>
				<br>
				<c:if test="${user.role == 'ROLE_CLIENT'}">
				<label for="importer">Edustaa maahantuojaa: </label>
				<p id="importer">${user.importer}</p> 
				<br>
				<label for="workshopList">Käyttäjän korjaamot: </label>
				
				<a id="workshopList" class="btn btn-large btn-default" href="/ProNaseva/showWorkshopListForUser"><span class="glyphicon glyphicon-list" style="text-decoration: none;"></span> Näytä korjaamolista</a>
				</c:if>
				
					
		
				<br><br>
	
				<a class="btn btn-large btn-primary" href="/ProNaseva/admin/showUserList?page=1"><span class="glyphicon glyphicon-arrow-left" style="text-decoration: none;"></span> Palaa käyttäjälistaan</a>
				<a class="btn btn-large btn-primary" style="text-decoration: none" href="/ProNaseva/admin/editUser?id=${user.id}"><span class="glyphicon glyphicon-edit" style="text-decoration: none;"></span> Muokkaa</a>
				<a href="#" class="btn btn-large btn-danger deleteAlert" style="text-decoration: none"><span class="glyphicon glyphicon-remove" style="text-decoration: none;"></span> Poista</a>
	
		</div>
		<br>
		
				
		<script>
        $(document).on("click", ".deleteAlert", function(e) {
            bootbox.dialog({
          	  message: "Poista käyttäjä?",
          	  title: "Vahvista",
          	  buttons: {
          	    cancel: {
          	      label: "Peruuta",
          	      className: "btn-primary",
          	      callback: function() {
          	        
          	      }
          	    },
          	    confirm: {
          	      label: "Poista",
          	      className: "btn-danger",
          	      callback: function() {
          	    	window.location.href = "deleteUser"
          	      }
          	    }
          	  }
          	});
            });
       </script>
			
				
<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />