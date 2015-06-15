<html>
<head>
<title>All Projects</title>
</head>
<body>

<?php
$username="book";
$password="pass1234";
$database="company";
$host="localhost";

mysql_connect($host,$username,$password);
@mysql_select_db($database) or die( "Unable to select database");
$query="SELECT pnumber,pname FROM project order by pnumber";
$result=mysql_query($query);
$num=mysql_numrows($result);
mysql_close();
?>

<h4>Projects of Company</h4>
<table border="2" cellspacing="2" cellpadding="2">
<tr>
<th><font face="Arial, Helvetica, sans-serif">Project Number</font></th>
<th><font face="Arial, Helvetica, sans-serif">Project Name</font></th>
</tr>

<?php
$i=0;
while ($i < $num) {
  $pnum=mysql_result($result,$i,"pnumber");
  $pname=mysql_result($result,$i,"pname");
?>

<tr>
<td><font face="Arial, Helvetica, sans-serif">
  <a href="projView.php?pnum=<?php echo $pnum; ?>"><?php echo $pnum; ?></a></font></td>
<td><font face="Arial, Helvetica, sans-serif"><?php echo $pname; ?></font></td>
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
