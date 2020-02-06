<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
		

    		<div style="border-bottom: 1px solid #eee;">
			<h4>Maahantuoja: ${report.importer.name}</h4>
			
			<label for="workshopSelect"><h4>Valitse korjaamo: </h4></label>
				
				<sf:select style="width: auto; max-width: 100%; display: inline;" id="workshopSelect" path="workshopId"
					class="form-control">
					<c:forEach var="workshop" items="${report.importer.workshops}">
						<c:choose>
							<c:when test='${workshop.id == report.workshop.id}'>
								<option selected="selected" value="${workshop.id}">${workshop}, ${workshop.city}</option>
							</c:when>
							<c:otherwise>
								<option value="${workshop.id}">${workshop}, ${workshop.city}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</sf:select>
			<br>
			<label for="date"><h4>Tarkastuksen päivämäärä: </h4></label>
			<sf:input path="testDateString" name="date" class="datepicker" id="date" value="${report.testDateString}"/>
			<br>
			<h4>			
			<c:choose>
				<c:when test="${edit == 'TRUE'}">
					<span class="label label-warning">Muokkaus</span>
				</c:when>
				<c:otherwise>
					<span class="label label-warning">Luonnos</span>
				</c:otherwise>
			</c:choose>
			</h4>
						
			<br><br>
			</div>
			
			<br>

	<c:if test="${report.approvalRemarksGiven == 'true'}">
		<c:if test="${report.reportStatus != 'AWAIT_APPROVAL' or loginRole == '[ROLE_ADMIN]'}">
			<div class="alert alert-danger">
				<h4><span class="glyphicon glyphicon-warning-sign"></span><b> Raportissa korjattavaa!</b>
				<c:if test="${report.approvalRemarks != null and report.approvalRemarks != ''}">
					<br><br>${report.approvalRemarks}
				</c:if>
				</h4>
			</div>
			<br><br>
		</c:if>
	</c:if>
	
			
	<c:if test="${report.reportStatus == 'AWAIT_APPROVAL' and  loginRole == '[ROLE_ADMIN]'}">
		<h4>Kommentit tarkastajalle palautettavaan raporttiin:</h4>
		<sf:textarea rows="2" style="width:50%;" id="reportApprovalRemarks" path="approvalRemarks" />
		<br><br>
	</c:if>