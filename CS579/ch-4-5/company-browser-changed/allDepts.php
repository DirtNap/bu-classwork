<html>
<head>
<title>All Departments</title>
</head>
<body>

<?php
$username="book";
$password="pass1234";
$database="company";
$host="localhost";

mysql_connect($host,$username,$password);
@mysql_select_db($database) or die( "Unable to select database");
$query="SELECT dnumber,dname FROM department order by dnumber";
$result=mysql_query($query);
$num=mysql_numrows($result);
mysql_close();
?>

<h4>Departments of Company</h4>
<table border="2" cellspacing="2" cellpadding="2">
<tr>
<th><font face="Arial, Helvetica, sans-serif">Department Number</font></th>
<th><font face="Arial, Helvetica, sans-serif">Department Name</font></th>
</tr>

<?php
$i=0;
while ($i < $num) {
  $dno=mysql_result($result,$i,"dnumber");
  $dname=mysql_result($result,$i,"dname");
?>

<tr>
<td><font face="Arial, Helvetica, sans-serif">
  <a href="deptView.php?dno=<?php echo $dno; ?>"><?php echo $dno; ?></a></font></td>
<td><font face="Arial, Helvetica, sans-serif"><?php echo $dname; ?></font></td>
</tr>

<?php
  $i++;
}
?>

</table>

<P>
<a href="./">Return to main page</a>

</body>
</html>

