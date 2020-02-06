<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
				
<a style="text-decoration: none;" href="<c:url value='/'/>"><span class="glyphicon glyphicon-home" style="font-size: 1.7em; text-decoration: none; color: black;"></span></a>
				
<security:authentication property="principal.username" var="loginId" scope="request" />
<div style="float: right; font-size: 1.3em">
		<p style="color: #8C8C8C; display: inline;">${loginId}</p>
		&nbsp;
		<a class="btn btn-default logout" style="text-decoration: none" href="#">Kirjaudu ulos</a>
</div>
<br><br>

<c:if test="${not empty successMessages}">
	<div class="alert alert-success">
	<c:forEach var="message" items="${successMessages}">
		<h4><span class="glyphicon glyphicon-ok"></span> ${message}</h4>
		
	</c:forEach>
	</div>
</c:if>

<c:if test="${not empty alertMessages}">
	<div class="alert alert-warning">
	<c:forEach var="message" items="${alertMessages}">
		<h4><span class="glyphicon glyphicon-warning-sign"></span> ${message}</h4>
		
	</c:forEach>
	</div>
</c:if>

<c:if test="${not empty errorMessages}">
	<div class="alert alert-danger">
	<c:forEach var="message" items="${errorMessages}">
		<h4><span class="glyphicon glyphicon-remove"></span> ${message}</h4>
		
	</c:forEach>
	</div>
</c:if>


		<script>
        $(document).on("click", ".logout", function(e) {
            bootbox.dialog({
          	  message: "Kirjaudu ulos?",
          	  title: "Vahvista",
          	  buttons: {
          	   confirm: {
          	      label: "Vahvista",
          	      className: "btn-primary",
          	      callback: function() {
          	    	window.location.href = "<c:url value='/j_spring_security_logout' />"
          	      }
          	    },
          	  cancel: {
          	      label: "Peruuta",
          	      className: "btn-danger",
          	      callback: function() {
          	        
          	      }
          	    }
          	    
          	  }
          	});
            });
       </script>