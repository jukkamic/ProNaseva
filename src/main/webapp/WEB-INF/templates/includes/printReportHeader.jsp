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


h3 {
	
	padding-top: 1em; 
	
}

h4 {
	padding-left: 1.5em;
	padding-right: 2em; 
	padding-top: 0.7em;
}

p {

	padding-left: 1.5em;
	padding-right: 2em;
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



