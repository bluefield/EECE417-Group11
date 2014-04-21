<html>
<script>
	function getParam( name )
	{
		 name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
		 var regexS = "[\\?&]"+name+"=([^&#]*)";
		 var regex = new RegExp( regexS );
		 var results = regex.exec( window.location.href );
		 if( results == null )
		  	return "";
		 else
	 		return results[1];
	}
	
	function autoFill(){
		var parkingLotNumberElem  = document.getElementById("lotnum_id");
		var parkingLotProviderElem = document.getElementById("providername_id");
		
		parkingLotNumberElem.value = getParam("selectedLot");
		parkingLotProviderElem.value = "Provider";
	}
	
	
	</script>
<body onload="autoFill()">
<form action="/newReservation" method="POST" oninput="reserveLength_output.value = reserveLength_id.value">
<h1>Parking Lot Reservation</h1>

<p>Please enter your information below:</p>
	
	Parking Lot Number: <input type="text" id="lotnum_id" name="lotnum" readonly="readonly"/><br /><br />
	Provider: <input type="text" id="providername_id" name="providername" readonly="readonly"/><br /><br />
	Reserve Start Time: <input type="datetime-local" id="start_time" name= "start_time"  required/><br /><br />
	Reservation Length: <input type="range" id="reserveLength_id" name="reserveLength" min="1" max="24" value="1" />                                                     
    <output name="reserveLength_output">1</output> hours<br/><br/>
	<input type="hidden" id="customer_username_id" name="customer_username_name" value="hai2you"> 
	<button id="submitButton" type="submit">Reserve</button>
	<button id="cancelButton" type="button" onclick="window.location.href='customer.jsp'">Cancel</button>
</form>

</script>
</body>
</html>