<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<title>Test-Center</title>

<link rel="stylesheet" href="<c:url value='/resources/css/font-awesome.css' />"/>
<style>

body {
background-image: url('../resources/images/printReportBackground.jpg');
font-family: 'Arial';
 
}

</style>

<style media="print">

body {
font-family: 'Arial';
background-image: url('../resources/images/printReportBackground.jpg');
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


h3, h4, p {
	padding: 0;
	margin: .5 0 .5 0;
		
}

table.multipleChoice {
	
	padding: 0;
	margin: 0;
	
}

.multipleChoice p {
	padding: 0;
	margin: 0;
	
	
}

.multipleChoice h3, h4 {
	padding: 0;
	margin: .5em 2em .5em 0;
	
}


.multipleChoice td {
	padding: 0;
	margin: 0;
	
}

.multipleChoice th {
	padding: 0;
	margin: 0;
	
}
iv

table.costListing {
	
	padding: 0;
	margin: 0;
	vertical-align: top;
	text-align: top;
	border-spacing: 0;
	border-collapse: collapse;
	
}

.costListing p {
	border-collapse: collapse;
	padding: .5em 0 .5em 0;
	margin: 0;
	vertical-align: top;
	text-align: top;
}

.costListing h3, h4 {
	padding: 0;
	margin: .5em 2em .5em 0;
	vertical-align: top;
	text-align: top;
	
}


.costListing td {
	border-top: 2px solid black;
	
	border-spacing: 0;
	border-collapse: collapse;
	padding: 0;
	margin: 0;
	vertical-align: top;
	text-align: top;
	
}

.costListing tr {
	
	padding: 0;
	margin: 0;
	vertical-align: top;
	text-align: top;
	
}



table {
  border-collapse: collapse;
  border-spacing: 0;
}

table.importantPoints {
	
	border-collapse: collapse;
	
	padding: 0;
	margin: 0;
	
}

.importantPoints p {
	border-collapse: collapse;
	padding: .5em 0 .5em 0;
	margin: 0;
	border-spacing: 0;
	margin-left: 1.5em;
	vertical-align: top;
	text-align: center;
	
}

.importantPoints h3, h4 {
	border-collapse: collapse;
	padding: 0;
	border-spacing: 0;
	margin: .5em 2em .5em 0;
	vertical-align: top;
	text-align: top;
	
}


.importantPoints td {
	border-top: 2px solid black;
	
	border-spacing: 0;
	border-collapse: collapse;
	padding: 0;
	margin: 0;
	vertical-align: top;
	text-align: top;
	
}

.importantPoints th {
	
	border-spacing: 0;
	border-collapse: collapse;
	padding: 0;
	margin: 0;
	vertical-align: top;
	text-align: top;
	
}

.indentAnswer h3, h4, p, td, th {
	margin-left: 2.2em;
}

}

@page {
size: A4;
margin: 0;

}

.newpage {
  page-break-before: always;

}

.noPageBreak {
	page-break-inside: avoid;
}


}

</style>



</head>



