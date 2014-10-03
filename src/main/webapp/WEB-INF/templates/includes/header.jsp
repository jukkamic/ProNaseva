<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />

<title>Test-Center</title>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.css' />"/>

<!-- Optional theme -->
<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap-theme.css' />"/>

<!--  Stricky footer-template -->
<link rel="stylesheet" href="<c:url value='/resources/css/stickyfooter-template.css' />"/>


<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>


<!-- Accordion Menun siirto avattuun paneeliin -->

<script type="text/javascript">

	$(function() {
		$('#accordion').on(
				'shown.bs.collapse',
				function(e) {
					var offset = $('.panel.panel-default > .panel-collapse.start')
							.offset();
					if (offset) {
						$('html,body').animate({
							scrollTop : $('.panel-title a').offset().top - 20
						}, 500);
					}
				});
	});
</script>


<link rel="stylesheet" href="resources/css/jquery-ui.css" />
<link rel="stylesheet" href="resources/css/jquery-ui.theme.css" />
</head>




