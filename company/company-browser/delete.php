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

// delete record
$delete = @$_POST["delete"];
if ($delete == "Delete"){
  $dfname = @$_POST["dfname"];
  $dlname = @$_POST["dlname"];
  $checkbox = @$_POST["checkbox"];
  // delete record
  foreach ($checkbox as $index){
    $sql = "delete from $tbl_name where fname='$dfname[$index]' and lname='$dlname[$index]'";
    $result = mysql_query($sql);
  }

}
// show all contact information
$sql="select * from $tbl_name order by lname";
$result = mysql_query($sql);
mysql_close();
?>

<html
<head>
<title>Delete</title>
</head>

<BODY>
<SCRIPT language=JavaScript type=text/javascript>

  <!-- function to check whether checkbox is selected -->
  function checkboxchecked(){
    element = document.getElementsByName("checkbox[]")
    
  for (var index=0; index< element.length; index++){
      if (element[index].checked == true)
        return true;
    }
  alert("Please select name to be deleted");
    return false;
  }
  
</SCRIPT>

<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<blockquote>
  <p>
  <h2>Delete Contact Information</h2>
  
  
   <form name="myform" method="post" action="delete.php" onSubmit="return checkboxchecked()">
     <table width="40%" border="0">
     <?php
      $index=0;
      while($row = mysql_fetch_assoc($result)) {
        $lname = $row['lname'];
          $fname = $row['fname'];
        echo "<tr><td width=\"25\" valign=\"top\"><input type=\"checkbox\" name=\"checkbox[]\" value=\"$index\" />";
        echo "</td><td valign=\"bottom\"><h4><a href=\"detail.php?lname=$lname&fname=$fname\">$lname, $fname</a><h4></td></tr>";
        echo "<input type=\"hidden\" name=\"dfname[]\" value=\"$fname\" />";
        echo "<input type=\"hidden\" name=\"dlname[]\" value=\"$lname\" />";
        $index++;
      }
      
      echo "<tr><td width=\"25\" valign=\"top\">&nbsp;</td><td valign=\"bottom\"><input type=\"submit\" name=\"delete\"";
      echo "value=\"Delete\" />&nbsp;&nbsp;&nbsp;<input type=\"reset\" name=\"Submit2\" value=\"Clear\" /></td></tr>";
      
    ?>
  </table>  
   </form>
  
  <?php
    if (mysql_num_rows($result)==0)
        echo "<h4>No data<h4>";
  ?>
  </p>
 
</blockquote>
</body>
</html>
