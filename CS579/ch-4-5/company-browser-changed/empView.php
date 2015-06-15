<html>
<head>
<title>Employee View</title>
</head>
<body>

<?php
$username="book";
$password="pass1234";
$database="company";
$host="localhost";

$essn=$_GET['ssn'];

mysql_connect($host,$username,$password);
@mysql_select_db($database) or die( "Unable to select database");

$query="SELECT lname,fname,bdate,address,salary,dno,dname FROM employee,department where ssn=$essn and dno=dnumber";
$result=mysql_query($query);
$num=mysql_numrows($result);

$elname=mysql_result($result,0,"lname");
$efname=mysql_result($result,0,"fname");
$ebdate=mysql_result($result,0,"bdate");
$eaddress=mysql_result($result,0,"address");
$esalary=mysql_result($result,0,"salary");
$edno=mysql_result($result,0,"dno");
$edname=mysql_result($result,0,"dname");

echo "<b>Employee: </b>", $elname, ", ", $efname, "<br>";
echo "Birth Date: ", $ebdate, "<br>";
echo "Address: ", $eaddress, "<br>";
echo "Salary: ", $esalary, "<br>";
echo "Department: <a href=\"deptView.php?dno=", $edno, "\">", $edname, "</a>";

echo "<h4>Projects Involved In:</h4>";
$query="SELECT pnumber,pname,hours FROM project,works_on where pno=pnumber and essn=$essn";
$result=mysql_query($query);
$num=mysql_numrows($result);
if ($num > 0) {
?>
  <table border="2" cellspacing="2" cellpadding="2">
  <tr>
  <th><font face="Arial, Helvetica, sans-serif">Project Number</font></th>
  <th><font face="Arial, Helvetica, sans-serif">Project Name</font></th>
  <th><font face="Arial, Helvetica, sans-serif">Hours</font></th>
  </tr>
<?php
  $i=0;
  while ($i < $num) {
    $pno=mysql_result($result,$i,"pnumber");
    $pname=mysql_result($result,$i,"pname");
    $hours=mysql_result($result,$i,"hours");
?>  
  <tr>
  <td><font face="Arial, Helvetica, sans-serif">
  <a href="projView.php?pnum=<?php echo $pno; ?>"><?php echo $pno; ?></a></font></td>
  <td><font face="Arial, Helvetica, sans-serif"><?php echo $pname; ?></font></td>
  <td><font face="Arial, Helvetica, sans-serif"><?php echo $hours; ?></font></td>
  </tr>
<?php
    $i++;
  }
} else
    echo "Employee works for no project<br>";
echo "</table>";

echo "<h4>Dependents:</h4>";
$query="SELECT dependent_name,sex,bdate,relationship FROM dependent where essn=$essn";
$result=mysql_query($query);
$num=mysql_numrows($result);
if ($num > 0) {
?>
  <table border="2" cellspacing="2" cellpadding="2">
  <tr>
  <th><font face="Arial, Helvetica, sans-serif">Dependent Name</font></th>
  <th><font face="Arial, Helvetica, sans-serif">Sex</font></th>
  <th><font face="Arial, Helvetica, sans-serif">Birth Date</font></th>
  <th><font face="Arial, Helvetica, sans-serif">Relationship</font></th>
  </tr>
  <?php
  $i=0;
  while ($i < $num) {
    $depname=mysql_result($result,$i,"dependent_name");
    $depsex=mysql_result($result,$i,"sex");
    $depbdate=mysql_result($result,$i,"bdate");
    $deprelationship=mysql_result($result,$i,"relationship");
  ?>  
  <tr>
  <td><font face="Arial, Helvetica, sans-serif"><?php echo $depname; ?></font></td>
  <td><font face="Arial, Helvetica, sans-serif"><?php echo $depsex; ?></font></td>
  <td><font face="Arial, Helvetica, sans-serif"><?php echo $depbdate; ?></font></td>
  <td><font face="Arial, Helvetica, sans-serif"><?php echo $deprelationship; ?></font></td>
  </tr>
  <?php
    $i++;
  }
} else
    echo "Employee has no dependents<br>";
echo "</table>";
mysql_close();
?>

<P>
<a href="./">Return to main page</a>

</body>
</html>
