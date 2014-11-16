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
				<h1>Käyttäjätiedot</h1>
			</div>

			<br>
			<br>

			<sf:form id="editUserForm" modelAttribute="user" method="post">

				<label for="firstName">Etunimi: </label>
				<br>
				<sf:input type="text" style="width: 15em; max-width: 100%" id="firstName" path="firstName" value="${user.firstName}"/> 
				<br><br>
								
				<label for="lastName">Sukunimi: </label>
				<br>
				<sf:input type="text" style="width: 15em; max-width: 100%" id="lastName" path="lastName" value="${user.lastName}"/> 
				<br><br>
				
				<label for="email">Sähköposti: </label>
				<br>
				<sf:input type="text" style="width: 15em; max-width: 100%" id="email" path="email" value="${user.email}"/> 
				<br><br>
				
				<label for="userName">Käyttäjätunnus: </label>
				<br>
				<sf:input type="text" style="width: 15em; max-width: 100%" id="userName" path="userName" value="${user.userName}" /> 
				<br><br>
				
				<label for="password">Salasana: </label>
				<br>
				<input type="hidden" id="editPassword" name="editPassword" value="false" />
				<c:if test="${editPassword == 'true' }">
					<sf:input type="password" style="width: 15em; max-width: 100%" id="password" path="password" value=" "/> 
					<br><br>
					
					<label for="confirmPassword">Vahvista salasana: </label>
					<br>
					<input type="password" style="width: 15em; max-width: 100%" id="confirmPassword" name="confirmPassword" value=""/> 
					<br><br>
				</c:if>
				<c:if test="${editPassword != 'true' }">
					<a class="btn btn-large btn-default" href="#" onclick="editPassword();">Vaihda salasana</a>
					<br><br>
					
					<input type="hidden" name="confirmPassword" value="" />
					
				</c:if>
				<label for="role">Käyttöoikeudet: </label>
				<br>				

				<sf:select style="width: auto; max-width: 100%" id="role" path="role"
					class="form-control">
					<c:forEach var="role" items="${roles}">
						<c:choose>
							<c:when test="${user.role == role.value}">
								<option value="${role.value}" selected="selected">${role.key}</option>
							</c:when>
							<c:otherwise>
								<option value="${role.value}">${role.key}</option>
							</c:otherwise>
						</c:choose>
						
					</c:forEach>
				</sf:select>
				<br><br>
	
				<button class="btn btn-large btn-primary" action="submit"><span class="glyphicon glyphicon-ok" style="text-decoration: none;"></span> Tallenna</button>
				<a class="btn btn-large btn-primary" style="text-decoration: none" href="/ProNaseva/admin/showUser?id=${user.id}"><span class="glyphicon glyphicon-remove" style="text-decoration: none;"></span> Hylkää muutokset</a>
				
				<c:if test="${edit == 'TRUE'}">
					<a href="#" class="btn btn-large btn-danger deleteAlert" style="text-decoration: none"><span class="glyphicon glyphicon-remove" style="text-decoration: none;"></span> Poista</a>

				</c:if>
					
			</sf:form>
		</div>
		<br>

<script>
   function editPassword()
   {
           
      document.getElementById("editPassword").value = true;
      document.getElementById('editUserForm').submit();
   }
</script>


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