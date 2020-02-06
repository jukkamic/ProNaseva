<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
		
		

		<c:if test ="${loginRole == '[ROLE_CLIENT]'}">
			<a class="btn btn-primary" href="/ProNaseva/userOwnReports/"><span class="glyphicon glyphicon-arrow-left" style="text-decoration: none;"></span> Palaa raporttilistaan</a>
			<a class="btn btn-primary" href="/ProNaseva/getPdf/"><span class="glyphicon glyphicon-download" style="text-decoration: none;"></span> Lataa pdf</a>
		</c:if>
	
		<c:if test ="${loginRole != '[ROLE_CLIENT]'}">
		
			<c:if test="${report.reportStatus != 'APPROVED' or loginRole == '[ROLE_ADMIN]' }">
				<a class="btn btn-primary" href="/ProNaseva/editReport"><span class="glyphicon glyphicon-pencil" style="text-decoration: none;"></span> Muokkaa</a>
			</c:if>
			<c:if test="${report.reportStatus != 'APPROVED' and report.reportStatus != 'AWAIT_APPROVAL' and loginRole != '[ROLE_ADMIN]'}">
					<a class="btn btn-primary" href="/ProNaseva/submitReportForApproval/"><span class="glyphicon glyphicon-ok" style="text-decoration: none;"></span> Lähetä vahvistettavaksi</a>
			</c:if>
				
			<c:if test="${loginRole == '[ROLE_ADMIN]' and report.reportStatus != 'APPROVED' }">
				
				<a class="btn btn-primary" href="/ProNaseva/approveReport/"><span class="glyphicon glyphicon-ok" style="text-decoration: none;"></span> Vahvista raportti</a>
				
			</c:if>
			<c:if test="${loginRole == '[ROLE_ADMIN]'}">
				<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-download-alt" style="text-decoration: none;"></span> 
					Tallenna</button>
			</c:if>
			
			<a class="btn btn-primary" href="/ProNaseva/getPdf/"><span class="glyphicon glyphicon-download" style="text-decoration: none;"></span> Lataa pdf</a>
			
			<c:if test="${loginRole == '[ROLE_ADMIN]' and report.reportStatus == 'AWAIT_APPROVAL'}">
				<a class="btn btn-primary btn-danger" href="#" onclick="rejectReport();"><span class="glyphicon glyphicon-minus-sign" style="text-decoration: none;"></span> Palauta korjattavaksi</a>
			</c:if>
			
			<input type="hidden" id="rejectReport" name="rejectReport" value="false" />
			
			<c:if test="${loginRole == '[ROLE_ADMIN]' or report.reportStatus != 'APPROVED'}">
				<a href="#" class="btn btn-large btn btn-danger deleteReport"><span class="glyphicon glyphicon-remove" style="text-decoration: none;"></span> Poista</a>
								
			</c:if>
		</c:if>
						
<script>
   function rejectReport()
   {
	   document.getElementById("rejectReport").value = true;
	   
      document.getElementById('showReportForm').submit();
   }
   </script>
  
  
  
<script>
      $(document).on("click", ".deleteReport", function(e) {
          bootbox.dialog({
        	  message: "Poista raportti?",
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
          	    	window.location.href = "/ProNaseva/deleteReport"
          	      }
          	    },
          	  
        	    
        	  }
        	});
          });
     </script>