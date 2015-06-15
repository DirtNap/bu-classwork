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

// retrieve all variables
$keyword = @$_POST["keyword"];
$searchin = @$_POST["searchin"];


// execute query
$sql="select * from $tbl_name";
$result = mysql_query($sql);
mysql_close();

?>

<html>
<head>
<title>Results</title>
</head>

<BODY>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<blockquote>
  <p>
  <h2>Result</h2>
   <?php
       $i =0;
    while($row = mysql_fetch_assoc($result)) {
      $lname = $row['lname'];
        $fname = $row['fname'];
      
      if ($searchin == "both"){
        // search in last name & first name
        if ((preg_match("/$keyword/i",$fname))||(preg_match('/$keyword/',$lname))){
          echo "<ul><li><h4><a href=\"detail.php?lname=$lname&fname=$fname\">$lname, $fname</a><h4></li></ul>";
          $i++;
        }
      } else if($searchin == "fname"){
        // seach in first name
        if (preg_match("/$keyword/i",$fname)){
            echo "<ul><li><h4><a href=\"detail.php?lname=$lname&fname=$fname\">$lname, $fname</a><h4></li></ul>";
            $i++;
        }
      } else{
        // search in last name
        if (preg_match("/$keyword/i",$lname)){
            echo "<ul><li><h4><a href=\"detail.php?lname=$lname&fname=$fname\">$lname, $fname</a><h4></li></ul>";
            $i++;
        }        
      }
    }
    
    if ($i == 0)
      echo "<ul><h4>No match result.<h4></ul>";
     
  ?>
  
</blockquote>
</body>
</html>
