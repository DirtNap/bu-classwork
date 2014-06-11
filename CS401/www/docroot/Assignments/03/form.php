<?
   $styles = [
		   'name' => 'User',
		   'halign' => 'center',
		   'hsize' => 24,
		   'addstyle' => '',
		   'tbg' => 'inherit',
	      ];
foreach ($styles as $key => $value) {
  if (isset($_GET[$key]) && $_GET[$key] != '') {
    $styles[$key] = $_GET[$key];
  }
}
?>
<html>
  <head>
    <title>:: BU MET CS401 EX : Assignment 03 : Form ::</title>
    <meta name="author" content="Michael Donnelly" />
    <meta name="description" content="Table page of Assignment 03." />
    <style>
      h3 {
      text-align: <?= $styles["halign"] ?>;
      font-size: <?= $styles["hsize"] ?>pt;
      }
      table {
      background: <?= $styles["tbg"] ?>;
      }
    </style>
    <style>
      <?= $styles['addstyle']; ?>

    </style>
  </head>
  <body>
    <div id="content" style="width: 640px">
      <p style="text-align: center">
	&laquo;&nbsp;
	<a href="index.html" id="table">Table</a>
	&nbsp;&middot;&nbsp;
	<a id="form">Form</a>
	&nbsp;&raquo;
      </p>
      <table>
	<thead>
	  <tr>
	    <th colspan="2"><h3><?= $styles["name"] ?>'s Form!</h3></th>
	  </tr>
	</thead>
	<tbody>
	  <form action="form.php" method="GET">
	    <tr>
	      <td class="label">Your name:
	      </td>
	      <td class="form_input">
		<input type="text" name="name" size="40" value="<?echo(($styles['name'] == 'User')?'':$styles['name']);?>" />
	      </td>
	    </tr>
	    <tr>
	      <td class="label">
		Heading size:
	      </td>
	      <td class="form_input">
		<select name="hsize">
		  <option value="16" <?echo(($styles['hsize'] == 16)?'selected':'');?>>small</option>
		  <option value="24" <?echo(($styles['hsize'] == 24)?'selected':'');?>>medium</option>
		  <option value="32" <?echo(($styles['hsize'] == 32)?'selected':'');?>>large</option>
	      </td>
	    </tr>
	    <tr>
	      <td class="label">
		Heading alignment:
	      </td>
	      <td class="form_input">
		<input type="radio" name="halign" value="left" <?echo(($styles['halign'] == 'left')?'checked':'');?> />&nbsp;left<br />
		<input type="radio" name="halign" value="center" <?echo(($styles['halign'] == 'center')?'checked':'');?> />&nbsp;center<br />
		<input type="radio" name="halign" value="right" <?echo(($styles['halign'] == 'right')?'checked':'');?> />&nbsp;right<br />
	      </td>
	    </tr>
	    <tr>
	      <td class="label">Table background:
	      </td>
	      <td class="form_input">
		<input type="checkbox" name="tbg" value="yellow" <?echo(($styles['tbg'] == 'yellow')?'checked':'');?>/>&nbsp;on
	      </td>
	    </tr>
	    <tr>
	      <td class="label">Additional style rules:
	      </td>
	      <td class="form_input">
		<textarea name="addstyle"><?= $styles['addstyle'] ?></textarea>
	      </td>
	    </tr>
	    <tr>
	      <td class="form_input" colspan="2">
		<input type="submit" name="save" value="Save your changes!" />
	      </td>
	    </tr>
	  </form>
	</tbody>
      </table>
    </div>
  </body>
</html>


