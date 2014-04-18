
<html>
<script>
	function checkRepassword(thisForm){
		if(thisForm.pass.value != thisForm.repass.value){
			alert("The re-entered password is not same as your password");
			thisForm.repass.focus();
			return false;
		}
		return true;
	}
</script>
<body>
<form action = "/register" method="POST" onsubmit='return checkRepassword(this)'>
<h1>New User Registration</h1>

<p>Please enter your information below:</p>
	
	First Name: <input type="text" name= "fname" required/><br /><br />
	Last Name: <input type="text" name= "lname" required/><br /><br />
	Username: <input type="text" name= "uname" required/><br /><br />
	Password: <input type="password" name= "pass" required/><br /><br />
	Re-Password: <input type="password" name= "repass" required/><br /><br />
	<input type="radio" name="userType" value="customer" checked>Customer
	<input type="radio" name="userType" value="host">Host<br>
	
	<button id="submitButton" type="submit">Register</button>
	<button id="cancelButton" type="button" onclick="window.location.href='home.jsp'">Cancel</button>
</form>
</body>
</html>
