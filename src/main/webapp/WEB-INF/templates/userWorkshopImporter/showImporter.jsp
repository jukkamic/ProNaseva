<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/templates/includes/header.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
				<h1>Maahantuoja</h1>
			</div>

			<br>
			<br>
				<label for="name">Maahantuojan nimi: </label>
				<p id="name">${importer.name}</p> 
				<br>
				
				<label for="address">Katuosoite: </label>
				<p id="address">${importer.streetAddress}</p> 
				<br>
				
				<label for="poBox">Postilaatikko: </label>
				<p id="poBox">${importer.poBox}</p> 
				<br>
								
				<label for="zipCode">Postinumero: </label>
				<p id="zipCode">${importer.zipCode} </p> 
				<br>
				
				<label for="city">Postitoimipaikka: </label>
				<p id="city">${importer.city} </p> 
				<br>
				
				<label for="email">Sähköpostiosoite: </label>
				<p id="email">${importer.email} </p> 
				<br>
				
				<label for="telNum">Puhelinnumero: </label>
				<p id="telNum">${importer.telNum} </p> 
				<br>
				<label for="reportTemplate">Raporttipohjat: </label>
				<c:if test="${importer.reportTemplates != 'null'}">
					<c:forEach var="template" items="${importer.reportTemplates }" varStatus="count">
						
						<c:if test="${count.count < fn:length(importer.reportTemplates)}">
							<p>${template.templateName}, </p>
						</c:if>
						<c:if test="${count.count == fn:length(importer.reportTemplates)}">
							<p>${template.templateName}</p>
						</c:if>
					
					</c:forEach>
				</c:if> 
				<br><br>
	
				<a class="btn btn-large btn-primary" href="/ProNaseva/showImporterList?page=1"><span class="glyphicon glyphicon-arrow-left" style="text-decoration: none;"></span> Palaa maahantuojalistaan</a>
				<a class="btn btn-large btn-primary" style="text-decoration: none" href="/ProNaseva/admin/editImporter?id=${importer.id}"><span class="glyphicon glyphicon-edit" style="text-decoration: none;"></span> Muokkaa</a>
				<a href="#" class="btn btn-large btn-danger deleteAlert" style="text-decoration: none"><span class="glyphicon glyphicon-remove" style="text-decoration: none;"></span> Poista</a>
	
		</div>
		<br>
		
				
		<script>
        $(document).on("click", ".deleteAlert", function(e) {
            bootbox.dialog({
          	  message: "Poista maahantuoja?",
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
          	    	window.location.href = "/ProNaseva/admin/deleteImporter"
          	      }
          	    }
          	  }
          	});
            });
       </script>
			