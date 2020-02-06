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
				<h1>Maahantuoja</h1>
			</div>

			<br>
			<br>

			<sf:form id="importerForm" modelAttribute="importer" method="post">

				<label for="name">Maahantuojan nimi: </label>
				<br>
				<sf:input type="text" style="width: 15em; max-width: 100%" id="name" path="name" value="${importer.name}" /> 
				<br><br>
								
				<label for="streetAddress">Katuosoite: </label>
				<br>
				<sf:input type="text" style="width: 15em; max-width: 100%" id="streetAddress" path="streetAddress" value="${importer.streetAddress}"/> 
				<br><br>
				
				<label for="poBox">Postilaatikko: </label>
				<br>
				<sf:input type="text" style="width: 15em; max-width: 100%" id="poBox" path="poBox" value="${importer.poBox}"/> 
				<br><br>
				
				<label for="zipCode">Postinumero: </label>
				<br>
				<sf:input type="text" style="width: 15em; max-width: 100%" id="zipCode" path="zipCode" value="${importer.zipCode}" /> 
				<br><br>
				
				<label for="city">Postitoimipaikka: </label>
				<br>
				<sf:input type="text" style="width: 15em; max-width: 100%" id="city" path="city" value="${importer.city}" /> 
				<br><br>
				
				<label for="email">Sähköpostiosoite: </label>
				<br>
				<sf:input type="text" style="width: 15em; max-width: 100%" id="email" path="email" value="${importer.email}"/> 
				<br><br>
				
				<label for="telNum">Puhelinnumero: </label>
				<br>
				<input type="text" style="width: 15em; max-width: 100%" id="telNum" name="telNum" value="${importer.telNum}"/>
				<br><br>
				
				<label for="workshops">Maahantuojan korjaamolista:</label>
				<br>
				<a href="#" class="btn btn-large btn-default" onclick="editWorkshopList();"><span class="glyphicon glyphicon-list" style="text-decoration: none;"></span> Korjaamot</a>
				<input type="hidden" name="editWorkshopList" value="false" id="editWorkshopList" />
				<br><br>
				
				<label for="template">Raporttipohja: </label>
				<sf:select multiple="true" style="width: auto; max-width: 100%" id="role" path="chosenTemplates"
					class="form-control">
					<option value=''>-- Valitse --</option>
					<c:forEach var="template" items="${reportTemplateList}">
					
						<c:set var="selectedTemplate" value="false" />
						<c:forEach items="${importer.reportTemplates}" var="importerTemplate">
							<c:if test="${importerTemplate.templateName == template.templateName }">
								<c:set var="selectedTemplate" value="true" />
							</c:if>
						</c:forEach>
						<c:choose>
							<c:when test="${selectedTemplate == 'true'}">
								<option value="${template.templateName}" selected="selected">${template.templateName}</option>
							</c:when>
							<c:otherwise>
								<option value="${template.templateName}">${template.templateName}</option>
							</c:otherwise>
						</c:choose>
						
					</c:forEach>
				</sf:select>
				<br>
				
				<br><br>
		
				<button class="btn btn-large btn-primary" type="submit"><span class="glyphicon glyphicon-ok" style="text-decoration: none;"></span> Tallenna</button>
				<a class="btn btn-large btn-primary" href="/ProNaseva/showImporter?id=${importer.id}"><span class="glyphicon glyphicon-remove" style="text-decoration: none;"></span> Hylkää muutokset</a>
				
				<c:if test="${edit == 'TRUE'}">
					<a href="#" class="btn btn-large btn btn-danger deleteImporter"><span class="glyphicon glyphicon-remove" style="text-decoration: none;"></span> Poista</a>
				</c:if>
				
				<br>				
			</sf:form>
		</div>
		<br>
		
<script>
   function editWorkshopList()
   {
	   
	   document.getElementById("editWorkshopList").value = true;
	  
      document.getElementById('importerForm').submit();
   }

  
</script>
		
		<script>
        $(document).on("click", ".deleteImporter", function(e) {
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
          	    	window.location.href = "deleteImporter"
          	      }
          	    }
          	  }
          	});
            });
       </script>
<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />