<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
		

		
	<br>
	<br>
	<div style="border-bottom: 1px solid #eee;">
	<h4>Maahantuoja: ${report.importer.name}</h4>
	<h4>Tarkastettu korjaamo: ${report.workshop.name}, ${report.workshop.city}</h4>
	<h4>Tarkastuksen päivämäärä: ${report.testDateString}</h4>
	
	<h4>
	<c:choose>
		
		<c:when test="${report.reportStatus == 'DRAFT'}">
			<span class="label label-warning">Luonnos</span>
		</c:when>
		<c:when test="${report.reportStatus == 'AWAIT_APPROVAL'}">
			<span class="label label-info">Vahvistettavana</span>
		</c:when>
		<c:when test="${report.reportStatus == 'APPROVED'}">
			<span class="label label-success">Valmis</span>
		</c:when>
		<c:when test="${report.reportStatus == 'REJECTED'}">
			<span class="label label-danger">Korjattavana</span>
		</c:when>
		
	</c:choose></h4>
					
		<br><br>
		</div>
		<br>

<div class="panel-group" id="accordion"> 
		<c:set var="bootstrapPanelCounter" value="1" scope="request" />
				
	
	<c:if test="${report.approvalRemarksGiven == 'true'}">
		<c:if test="${report.reportStatus != 'AWAIT_APPROVAL' or loginRole == '[ROLE_ADMIN]'}">
			<div class="alert alert-danger">
				<h4> <span class="glyphicon glyphicon-warning-sign"></span><b> Raportissa korjattavaa!</b>
				<c:if test="${report.approvalRemarks != null and report.approvalRemarks != ''}">
					<br><br>${report.approvalRemarks}
				</c:if>
				</h4>
			</div>
			<br>
		</c:if>
	</c:if>
	
			
	<c:if test="${report.reportStatus == 'AWAIT_APPROVAL' and  loginRole == '[ROLE_ADMIN]'}">
		<h4>Kommentit tarkastajalle palautettavaan raporttiin:</h4>
		<sf:textarea rows="2" style="width:50%;" id="reportApprovalRemarks" path="approvalRemarks" />
		<br><br><br>
	</c:if>