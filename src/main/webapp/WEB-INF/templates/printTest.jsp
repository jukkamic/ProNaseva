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

<div style="margin-left: 3.5em; margin-right: 2em;">


	<table>
	   <thead >
	   		<tr>
	   			<td style="width:595pt; height: 10em; margin-top:0; padding-top:0;">
	   						
							<div style="float: right; width: 70%; border: bottom; height: 5em;">
								<div style="text-align:left; margin: 0em 0em 0em 0em; padding-top:0;">
									<h2 style="margin-top:0; padding-top:0; padding-bottom:0; margin-bottom:0;">Test Center Tiililä Oy</h2>
									<h3 style="margin-top:0; padding-top:0.5em; margin-top:0;">[Raportin osa]</h3>
								</div>
								<p style="text-align:right; margin-top:0; padding-top:0;">Sivu X</p>
							</div>
							
				</td>
	   		</tr>
	   	</thead>

	   <tbody>
	    <tr><td>
			
			<div style="margin-top: 5em; margin-left: 7em;">
			<h1>Test Center Tiililä raportti</h1>
				<br>
				<br>
				<div style="border-bottom: 1px solid #eee;">
					<h2>${report.importer.name}</h2>
					<h2>[pvm]</h2>
					<br><br>
					
				</div>
				<div class="newpage"></div>		
			</div>
		
		
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
		<p class="newpage">xx3</p>
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
	      
	    
	    
	
	
</div>

</body>

</html>