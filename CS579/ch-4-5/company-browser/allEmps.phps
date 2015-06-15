<html>
<head>
<title>All Employees</title>
</head>
<body>

<?
$username="user";
$password="password";
$database="company";

mysql_connect("host.domain.edu",$username,$password);
@mysql_select_db($database) or die( "Unable to select database");
$query="SELECT ssn,lname,fname FROM employee order by lname,fname";
$result=mysql_query($query);
$num=mysql_numrows($result);
mysql_close();
?>

<h4>Employees of Company</h4>
<table border="2" cellspacing="2" cellpadding="2">
<tr>
<th><font face="Arial, Helvetica, sans-serif">SSN</font></th>
<th><font face="Arial, Helvetica, sans-serif">Last Name</font></th>
<th><font face="Arial, Helvetica, sans-serif">First Name</font></th>
</tr>

<?
$i=0;
while ($i < $num) {
  $essn=mysql_result($result,$i,"ssn");
  $elname=mysql_result($result,$i,"lname");
  $efname=mysql_result($result,$i,"fname");
?>

<tr>
<td><font face="Arial, Helvetica, sans-serif">
  <a href="empView.php?ssn=<? echo $essn; ?>"><? echo $essn; ?></a></font></td>
<td><font face="Arial, Helvetica, sans-serif"><? echo $elname; ?></font></td>
<td><font face="Arial, Helvetica, sans-serif"><? echo $efname; ?></font></td>
</tr>

<?
  $i++;
}
?>

</table>

<P>
<a href="./">Return to main page</a>

</body>
</html>
