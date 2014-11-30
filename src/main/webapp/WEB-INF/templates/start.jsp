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
				<jsp:include page="/WEB-INF/templates/includes/pageHeaderRow.jsp" />
				<h1>Test-Center</h1>
				</div>
			<br><br>
			<div style="width: 70%;">
			<a class="btn btn-large btn-block btn-primary" style="font-size:2em; text-decoration: none" href="addNewReport">Uusi raportti</a>
			<a class="btn btn-large btn-block btn-primary" style="font-size:2em; text-decoration: none" href="userOwnReports">Omat raportit</a>
			<a class="btn btn-large btn-block btn-primary" style="font-size:2em; text-decoration: none" href="searchReport">Hae raportti</a>
			<br><br><br>
			<a class="btn btn-large btn-block btn-default" style="font-size:2em; text-decoration: none" href="showImporterList?page=1">Maahantuojat</a>
			<a class="btn btn-large btn-block btn-default" style="font-size:2em; text-decoration: none" href="showWorkshopList?page=1">Korjaamot</a>
			<security:authorize access="hasRole('ROLE_ADMIN')">
			<a class="btn btn-large btn-block btn-default" style="font-size:2em; text-decoration: none" href="admin/showUserList?page=1">Käyttäjätilit</a>
			<a class="btn btn-large btn-block btn-default" style="font-size:2em; text-decoration: none" href="admin/reportTemplates">Raporttipohjat</a>
			</security:authorize>
			</div>
			<br>
					
		</div>

	

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
