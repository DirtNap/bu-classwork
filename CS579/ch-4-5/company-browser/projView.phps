<html>
<head>
<title>Project View</title>
</head>
<body>

<?
$username="user";
$password="password";
$database="company";

$pnum=$_GET['pnum'];
mysql_connect("host.domain.edu",$username,$password);
@mysql_select_db($database) or die( "Unable to select database");

$query="SELECT pname,plocation,dnum,dname FROM project,department where pnumber=$pnum and dnum=dnumber";
$result=mysql_query($query);
$num=mysql_numrows($result);

$pname=mysql_result($result,0,"pname");
$ploc=mysql_result($result,0,"plocation");
$pdnum=mysql_result($result,0,"dnum");
$pdname=mysql_result($result,0,"dname");

echo "<b>Project: </b>", $pname, "<br>";
echo "Project Location: ", $ploc, "<br>";
echo "Controlling Department: <a href=\"deptView.php?dno=", $pdnum, "\">", $pdname, "</a>";

echo "<h4>Employees working in project:</h4>";
$query="SELECT ssn,lname,fname,hours FROM employee,works_on where pno=$pnum and essn=ssn";
$result=mysql_query($query);
$num=mysql_numrows($result);
if ($num > 0) {
?>
  <table border="2" cellspacing="2" cellpadding="2">
  <tr>
  <th><font face="Arial, Helvetica, sans-serif">Employee SSN</font></th>
  <th><font face="Arial, Helvetica, sans-serif">Employee Last Name</font></th>
  <th><font face="Arial, Helvetica, sans-serif">Employee First Name</font></th>
  <th><font face="Arial, Helvetica, sans-serif">Hours</font></th>
  </tr>
<?
  $i=0;
  while ($i < $num) {
    $essn=mysql_result($result,$i,"ssn");
    $elname=mysql_result($result,$i,"lname");
    $efname=mysql_result($result,$i,"fname");
    $hours=mysql_result($result,$i,"hours");
?>  
  <tr>
  <td><font face="Arial, Helvetica, sans-serif">
  <a href="empView.php?ssn=<? echo $essn; ?>"><? echo $essn; ?></a></font></td>
  <td><font face="Arial, Helvetica, sans-serif"><? echo $elname; ?></font></td>
  <td><font face="Arial, Helvetica, sans-serif"><? echo $efname; ?></font></td>
  <td><font face="Arial, Helvetica, sans-serif"><? echo $hours; ?></font></td>
  </tr>
<?
    $i++;
  }
} else
    echo "Project has no employees<br>";
echo "</table>";

mysql_close();
?>

<P>
<a href="./">Return to main page</a>

</body>
</html>
