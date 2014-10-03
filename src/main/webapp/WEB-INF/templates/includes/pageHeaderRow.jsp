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
		<a class="btn btn-default" style="text-decoration: none" href="<c:url value='/j_spring_security_logout' />">Kirjaudu ulos</a>
</div>
<br><br><br>