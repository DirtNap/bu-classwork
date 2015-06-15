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
$fname = @$_GET["fname"];
$lname = @$_GET["lname"];

// show all contact information
$sql="select * from $tbl_name where fname='$fname' and lname='$lname'";
$result = mysql_query($sql);
$row = mysql_fetch_assoc($result); 
mysql_close();
?>

<html>
<head>
<title>Detail</title>
</head>

<BODY>

<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<blockquote> 
    <table width="60%" border="0" cellpadding="5" cellspacing="15">
	<tr>
		<td colspan="2"><p><h2><?php echo "$lname, $fname"; ?></h2></td>
	  </tr>
    <tr>
      <td width="130">First Name :</td>
      <td><?php echo $row['fname']; ?></td>
    </tr>
    <tr>
      <td width="130">Last Name :</td>
      <td><?php echo $row['lname']; ?></td>
    </tr>
    <tr>
      <td width="130">E-mail Address :</td>
      <td><?php echo $row['email']; ?></td>
    </tr>
    <tr>
      <td width="130"><p>Home Phone :<br />
      </p>        </td>
      <td><?php echo $row['homePhone']; ?></td>
    </tr>
    <tr>
      <td width="130">Cell Phone :</td>
      <td><?php echo $row['cellPhone']; ?></td>
    </tr>
    <tr>
      <td width="130">Office Phone :</td>
      <td><?php echo $row['officePhone']; ?></td>
    </tr>
    <tr valign="top">
      <td width="130">Address :</td>
      <td><?php echo $row['address']; ?></td>
    </tr>
    <tr valign="top">
      <td width="130">Comment :</td>
      <td><?php echo $row['comment']; ?></td>
    </tr>
  </table>
   </p>
</blockquote>
</body>
</html>
