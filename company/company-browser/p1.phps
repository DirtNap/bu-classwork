<html>
<head>
<title>Simple Database Access</title>
</head>
<body>
<h4>Employee Information</h4>

<?
$username="user";
$password="password";
$database="company";
$ssn=$_POST['ssn'];
mysql_connect(localhost,$username,$password);
@mysql_select_db($database) or die( "Unable to select database");
$query="SELECT * FROM employee where ssn=$ssn";
$result=mysql_query($query);
$num=mysql_numrows($result);
mysql_close();
if ($num == 1) {
  $fname=mysql_result($result,$i,"fname");
  $minit=mysql_result($result,$i,"minit");
  $lname=mysql_result($result,$i,"lname");
  echo "<b>$fname $minit, $lname</b>";
}?>

</body>
</html>

