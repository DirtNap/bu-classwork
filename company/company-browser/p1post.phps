<html>
<head>
<title>Simple Database Access</title>
</head>
<body>

<?
$username="user";
$password="password";
$database="company";
mysql_connect(localhost,$username,$password);
@mysql_select_db($database) or die( "Unable to select database");
$query="SELECT ssn FROM employee";
$result=mysql_query($query);
$num=mysql_numrows($result);
mysql_close();
?>

<h4>Employee Details for:</h4>
<form method="post" action="p1.php">
<select name="ssn">

<?
$i=0;
while ($i < $num) {
  $ssn=mysql_result($result,$i,"ssn");
  echo "<option>",$ssn,"\n";
  $i++;
}
?>

</select>
<input type="submit" value="Get Employee Details">
</form>
</body>
</html>

