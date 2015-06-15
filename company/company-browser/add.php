<?php 
// connect to my sql
$host="tinman.cs.gsu.edu"; // Host name
$username="cms";	// Mysql username
$password="cms123";	// Mysql password
$db_name="conf";	// Database name
$tbl_name="contacts"; // Table name
	
// Connect to server and select databse.
mysql_connect("$host", "$username", "$password")or die("cannot connect");
mysql_select_db("$db_name")or die("cannot select DB");

// retrieve all variables
$fname = @$_POST["fname"];
$lname = @$_POST["lname"];
$email = @$_POST["email"];
$homePhone = @$_POST["homePhone"];
$cellPhone = @$_POST["cellPhone"];
$officePhone = @$_POST["officePhone"];
$address = @$_POST["address"];
$comment = @$_POST["comment"];


// insert information to database
$sql="insert into $tbl_name values('$lname','$fname','$email','$homePhone','$cellPhone','$officePhone','$address','$comment')";
$result = mysql_query($sql);
mysql_close();
?>



<html>
<head>

<title>Add processed</title>
<BODY>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<blockquote>
  <p>
  <h3>Your information is added to database. </h3>
  <body>


<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
</body>
</html>
