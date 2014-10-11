<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<!-- Raportin tulostus selaimella pdf-tiedostoksi. Toimii ainakin FireFox 32.0.3 jossa
lisäosana Printpdf 0.76 tai Print Edit 12.10. Print Edit näyttää print preview näkymässä
tulostavan virheellisesti mutta pdf tulostuu oikein -->


<html>
<head>


<title>Test-Center</title>

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

<body>

<p></p>
<table>
   <thead><tr><td><div style="margin-bottom: 10em;"></div></td></tr></thead>
   <tbody>
    <tr><td>
		
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx1</p>
	<p>xx2</p>
	<p>xx3</p>
	<p>xx4</p>
	<p>xx5</p>
	<p>xx6/p>
	<p>xx7</p>
	<p>xx8</p>
	<p>xx9</p>
	<p>xx1</p>
	<p>xx2</p>
	<p>xx3</p>
	<p>xx4</p>
	<p>xx5</p>
	<p>xx6</p>
	<p>xx7</p>
	<p>xx8</p>
	<p>xx9</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
		<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
		<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
		<p>xx</p>

	<p>xx</p>
		<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
		<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
	<p>xx</p>
		
	   
    </td></tr>
    
      <tfoot><tr><td><div style="margin-top: 7em;"></div></td></tr></tfoot>
    </tbody></table>
    
    
    
    <div class="newpage" style="height:100%;"></div> 
	
	


</body>

</html>