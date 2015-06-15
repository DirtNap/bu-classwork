<?php 
// connect to my sql
$host="tinman.cs.gsu.edu"; // Host name
$username="cms";  // Mysql username
$password="cms123";  // Mysql password
$db_name="conf";  // Database name
$tbl_name="contacts"; // Table name
  
// Connect to server and select databse.
mysql_connect("$host", "$username", "$password")or die("cannot connect");
mysql_select_db("$db_name")or die("cannot select DB");

// show all contact information
$sql="select * from $tbl_name order by lname";
$result = mysql_query($sql);
mysql_close();
?>

<html>
<head>
<title>List</title>
</head>

<BODY>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<blockquote>
  <p>
  <h2>All  Contact Information</h2>
   <?php
       if (mysql_num_rows($result)==0){
      echo "<h4>No data<h4>"; 
    } else {
         while($row = mysql_fetch_assoc($result)) {
        $lname = $row['lname'];
          $fname = $row['fname'];
        echo "<ul><li><h4><a href=\"detail.php?lname=$lname&fname=$fname\">$lname, $fname</a><h4></li></ul>";
      }
    }
     
  ?>
  
</blockquote>
</body>
</html>
