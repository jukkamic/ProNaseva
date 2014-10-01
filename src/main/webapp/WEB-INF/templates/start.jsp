<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/templates/includes/header.jsp" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<body>
	<div id="wrap">
		<div class="container">
			<div class="page-header">
				<security:authentication property="principal.username" var="loginId" scope="request" />
				<div style="float: right; font-size: 1.3em">
				<p style="color: #8C8C8C; display: inline;">${loginId}</p>
				&nbsp;
				<a class="btn btn-default" style="text-decoration: none" href="j_spring_security_logout">Kirjaudu ulos</a>
				 
				</div>
				<br><br><br>
				<h1>Test-Center</h1>
			</div>

			<br><br><br><br>
			<div style="width: 70%;">
			<a class="btn btn-large btn-block btn-primary" style="font-size:2em; text-decoration: none" href="addNewReport">Uusi raportti</a>
			<a class="btn btn-large btn-block btn-primary" style="font-size:2em; text-decoration: none" href="searchReport">Hae raportti</a>
			<a class="btn btn-large btn-block btn-primary" style="font-size:2em; text-decoration: none" href="importers">Maahantuojat</a>
			<a class="btn btn-large btn-block btn-primary" style="font-size:2em; text-decoration: none" href="workshops">Korjaamot</a>
			<security:authorize access="hasRole('ROLE_ADMIN')">
			<a class="btn btn-large btn-block btn-primary" style="font-size:2em; text-decoration: none" href="admin">Käyttäjätilit</a>
			</security:authorize>
			</div>

		
		</div>

		<br>

	<jsp:include page="/WEB-INF/templates/includes/footer.jsp" />
	
	

	<!-- Le javascript
    ================================================== 
    <!-- Placed at the end of the document so the pages load faster 
    <script src="../assets/js/jquery.js"></script>
    <script src="../assets/js/bootstrap-transition.js"></script>
    <script src="../assets/js/bootstrap-alert.js"></script>
    <script src="../assets/js/bootstrap-modal.js"></script>
    <script src="../assets/js/bootstrap-dropdown.js"></script>
    <script src="../assets/js/bootstrap-scrollspy.js"></script>
    <script src="../assets/js/bootstrap-tab.js"></script>
    <script src="../assets/js/bootstrap-tooltip.js"></script>
    <script src="../assets/js/bootstrap-popover.js"></script>
    <script src="../assets/js/bootstrap-button.js"></script>
    <script src="../assets/js/bootstrap-collapse.js"></script>
    <script src="../assets/js/bootstrap-carousel.js"></script>
    <script src="../assets/js/bootstrap-typeahead.js"></script> -->
