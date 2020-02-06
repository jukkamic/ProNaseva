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
				<h1>Korjaamo</h1>
			</div>


			<br>
			<br>
				<label for="name">Korjaamon nimi: </label>
				<p id="name">${workshop.name}</p> 
				<br>
				
				<label for="address">Katuosoite: </label>
				<p id="address">${workshop.streetAddress}</p> 
				<br>
				
				<label for="poBox">Postilaatikko: </label>
				<p id="poBox">${workshop.poBox}</p> 
				<br>
								
				<label for="zipCode">Postinumero ja postitoimipaikka: </label>
				<p id="zipCode">${workshop.zipCode} </p> 
				<br>
												
				<label for="city">Postitoimipaikka: </label>
				<p id="city">${workshop.city} </p> 
				<br>
				
				<label for="email">Sähköpostiosoite: </label>
				<p id="email">${workshop.email} </p> 
				<br>
				
				<label for="telNum">Puhelinnumero: </label>
				<p id="telNum">${workshop.telNum} </p> 
				<br><br>
	
				<a class="btn btn-large btn-primary" href="/ProNaseva/showWorkshopList?page=1"><span class="glyphicon glyphicon-arrow-left" style="text-decoration: none;"></span> Palaa korjaamolistaan</a>
				<a class="btn btn-large btn-primary" style="text-decoration: none" href="/ProNaseva/admin/editWorkshop?id=${workshop.id}"><span class="glyphicon glyphicon-edit" style="text-decoration: none;"></span> Muokkaa</a>
				<a href="#" class="btn btn-large btn-danger deleteAlert" style="text-decoration: none"><span class="glyphicon glyphicon-remove" style="text-decoration: none;"></span> Poista</a>
	
		</div>
		<br>
		
				
		<script>
        $(document).on("click", ".deleteAlert", function(e) {
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
          	    	window.location.href = "/ProNaseva/admin/deleteWorkshop"
          	      }
          	    }
          	  }
          	});
            });
       </script>
			
				
<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />