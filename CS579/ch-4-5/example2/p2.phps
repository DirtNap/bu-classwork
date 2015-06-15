<html>
<head>
<title>Simple Database Access</title>
</head>
<body>

<?
$username="user";
$password="password";
$database="company";
$dno=$_GET['dno'];
mysql_connect(localhost,$username,$password);
@mysql_select_db($database) or die( "Unable to select database");
$query="SELECT lname,salary FROM employee where dno=$dno";
$result=mysql_query($query);
$num=mysql_numrows($result);
mysql_close();
?>

<table border="2" cellspacing="2" cellpadding="2">
<tr>
<th><font face="Arial,Helvetica,sans-serif">Last Name</font></th>
<th><font face="Arial,Helvetica,sans-serif">Salary</font></th>
</tr>

<?
echo "<h4>Employees in Department $dno</h4>";
$i=0;
while ($i < $num) {
  $lname=mysql_result($result,$i,"lname");
  $salary=mysql_result($result,$i,"salary");
?>

<tr>
<td><font face="Arial, Helvetica, sans-serif">
<? echo $lname; ?>
</font></td>
<td><font face="Arial, Helvetica, sans-serif">
<? echo $salary; ?>
</font></td>
</tr>

<?
  $i++;
}
?>

</table>
</body>
</html>

