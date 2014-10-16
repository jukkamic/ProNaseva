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

<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css' />"/>




<style>

body {
background-image: url('../resources/images/printBackground.png');
}

</style>

<style media="print">

body {
background-image: url('../resources/images/printBackground.png');
-webkit-print-color-adjust:exact;
background-repeat:repeat-y;
background-position: left top;
background-attachment:fixed;

background-size: 100% 100%; 

background-color: white;

 }
tfoot { display: table-footer-group; 
}

thead { display: table-header-group;
}


@page {
size: A4;
margin: 0;
}
.newpage {
  page-break-before: always;


}


</style>

</head>



