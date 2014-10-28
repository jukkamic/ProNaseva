<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <head>
        <title>bootstrap datepicker examples</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Bootstrap CSS and bootstrap datepicker CSS used for styling the demo pages-->
        
        <link rel="stylesheet" href="<c:url value='/resources/css/datepicker.css' />"/>
        <link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.css' />"/>
        

       
    </head>
    <body>
        <div class="container">
            <div class="hero-unit">
                <input  type="text" placeholder="click to show datepicker"  id="example1">
            </div>
        </div>
        <!-- Load jQuery and bootstrap datepicker scripts -->

		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="<c:url value='/resources/js/bootstrap-datepicker.js' />" /></script>
        
        <script type="text/javascript">
            // When the document is ready
            $(document).ready(function () {
                
                $('#example1').datepicker({
                    format: "dd/mm/yyyy"
                });  
            
            });
        </script>
    </body>
</html>