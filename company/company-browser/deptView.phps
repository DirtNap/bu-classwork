<html>
<head>
<title>Department View</title>
</head>
<body>

<?
$username="user";
$password="password";
$database="company";

$dno=$_GET['dno'];
mysql_connect("host.domain.edu",$username,$password);
@mysql_select_db($database) or die( "Unable to select database");

$query="SELECT dname,mgrssn,mgrstartdate,lname,fname FROM department,employee where dnumber=$dno and mgrssn=ssn";
$result=mysql_query($query);
$num=mysql_numrows($result);

$dname=mysql_result($result,0,"dname");
$mssn=mysql_result($result,0,"mgrssn");
$mstart=mysql_result($result,0,"mgrstartdate");
$mlname=mysql_result($result,0,"lname");
$mfname=mysql_result($result,0,"fname");

echo "<b>Department: </b>", $dname;
echo "<P>Manager: <a href=\"empView.php?ssn=", $mssn, "\">", $mlname, ", ", $mfname, "</a></BR>";
echo "Manager Start Date: ", $mstart;

echo "<h4>Department Locations:</h4>";
$query="SELECT dlocation FROM dept_locations where dnumber=$dno";
$result=mysql_query($query);
$num=mysql_numrows($result);
$i=0;
while ($i < $num) {
  $dloc=mysql_result($result,$i,"dlocation");
  echo $dloc, "<BR>\n";
  $i++;
}

echo "<h4>Employees:</h4>";
$query="SELECT ssn,lname,fname FROM employee where dno=$dno";
$result=mysql_query($query);
$num=mysql_numrows($result);
?>

<table border="2" cellspacing="2" cellpadding="2">
<tr>
<th><font face="Arial, Helvetica, sans-serif">Employee SSN</font></th>
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

<?
echo "<h4>Projects:</h4>";
$query="SELECT pnumber,pname,plocation FROM project where dnum=$dno";
$result=mysql_query($query);
$num=mysql_numrows($result);
?>

<table border="2" cellspacing="2" cellpadding="2">
<tr>
<th><font face="Arial, Helvetica, sans-serif">Project Number</font></th>
<th><font face="Arial, Helvetica, sans-serif">Project Name</font></th>
<th><font face="Arial, Helvetica, sans-serif">Location</font></th>
</tr>

<?
$i=0;
while ($i < $num) {
  $pnum=mysql_result($result,$i,"pnumber");
  $pname=mysql_result($result,$i,"pname");
  $ploc=mysql_result($result,$i,"plocation");
?>  
<tr>
  <td><font face="Arial, Helvetica, sans-serif">
  <a href="projView.php?pnum=<? echo $pnum; ?>"><? echo $pnum; ?></a></font></td>
<td><font face="Arial, Helvetica, sans-serif"><? echo $pname; ?></font></td>
<td><font face="Arial, Helvetica, sans-serif"><? echo $ploc; ?></font></td>
</tr>
<?
  $i++;
}

mysql_close();
?>

<P>
<a href="./">Return to main page</a>

</body>
</html>
