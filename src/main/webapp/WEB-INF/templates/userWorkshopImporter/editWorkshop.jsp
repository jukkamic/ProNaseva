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
				<h1>Korjaamo</h1>
			</div>

			<br>
			<br>

			<sf:form modelAttribute="workshop" method="post">

				<label for="name">Korjaamon nimi: </label>
				<br>
				<sf:input type="text" style="width: 15em; max-width: 100%" id="name" path="name" value="${workshop.name}" /> 
				<br><br>
								
				<label for="streetAddress">Katuosoite: </label>
				<br>
				<sf:input type="text" style="width: 15em; max-width: 100%" id="streetAddress" path="streetAddress" 
					value="${workshop.streetAddress}" /> 
				<br><br>
				
				<label for="poBox">Postilaatikko: </label>
				<br>
				<sf:input type="text" style="width: 15em; max-width: 100%" id="poBox" path="poBox" value="${workshop.poBox}" /> 
				<br><br>
				
				<label for="zipCode">Postinumero: </label>
				<br>
				<sf:input type="text" style="width: 15em; max-width: 100%" id="zipCode" path="zipCode" value="${workshop.zipCode} "/> 
				<br><br>
				
				<label for="city">Postitoimipaikka: </label>
				<br>
				<sf:input type="text" style="width: 15em; max-width: 100%" id="city" path="city" value="${workshop.city} "/> 
				<br><br>
				
				
				<label for="email">Sähköpostiosoite: </label>
				<br>
				<sf:input type="text" style="width: 15em; max-width: 100%" id="email" path="email" value="${workshop.email}" /> 
				<br><br>
				
				<label for="telNum">Puhelinnumero: </label>
				<br>
				<input type="text" style="width: 15em; max-width: 100%" id="telNum" name="telNum" value="${workshop.telNum}" /> 
				<br><br><br>
		
				<button class="btn btn-large btn-primary" action="submit"><span class="glyphicon glyphicon-ok" style="text-decoration: none;"></span> Tallenna</button>
				<a class="btn btn-large btn-primary" href="/ProNaseva/showWorkshop?id=${workshop.id}"><span class="glyphicon glyphicon-remove" style="text-decoration: none;"></span> Hylkää muutokset</a>
				
				<c:if test="${edit == 'TRUE'}">
					<a href="#" class="btn btn-large btn btn-danger deleteWorkshop"><span class="glyphicon glyphicon-remove" style="text-decoration: none;"></span> Poista</a>
				</c:if>
			</sf:form>
		</div>
		<br>

		<script>
        $(document).on("click", ".deleteWorkshop", function(e) {
            bootbox.dialog({
          	  message: "Poista korjaamo?",
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
          	    	window.location.href = "deleteWorkshop"
          	      }
          	    }
          	  }
          	});
            });
       </script>
<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />