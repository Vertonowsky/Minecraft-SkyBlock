<?php
$mysqli= new mysqli("localhost","Admin","Admin123.","education");

$look = $_GET["cos"];

if($mysqli ->connect_error)
{
	echo "Wystąpił błąd podczas połączenia.".$mysqli->connect_error;

}
else
{

	$mysqli->set_charset("utf8");

	$jsondata = array();
	$jsondataList = array();

	if($_GET['param']=="count")
	{

		if ($look == "") {
			$sql = "SELECT COUNT(*) total FROM users_skyblock";

			$result = $mysqli->query($sql);
				
			$row = $result ->fetch_assoc();

			$jsondata['total'] = $row['total'];
		} else {
			$sql = "SELECT COUNT(*) total FROM users_skyblock WHERE name LIKE '%$look%'";

			$result = $mysqli->query($sql);
				
			$row = $result ->fetch_assoc();

			$jsondata['total'] = $row['total'];
		}
	}
	elseif($_GET["param"]=="records")
	{
		if ($look == "") {
			$sql = "SELECT * FROM users_skyblock ORDER BY level_total DESC LIMIT ".$mysqli->real_escape_string($_GET['limit'])." OFFSET ".$mysqli->real_escape_string($_GET["offset"]);
		} else {
			$sql = "SELECT * FROM users_skyblock WHERE name LIKE '%$look%' ORDER BY level_total DESC LIMIT ".$mysqli->real_escape_string($_GET['limit'])." OFFSET ".$mysqli->real_escape_string($_GET["offset"]);
		}

		$offset = $_GET["offset"]+1;
		$result = $mysqli->query($sql);
		while($row = $result ->fetch_assoc())
		{
			
			$sql2 = "SELECT rank FROM users_general WHERE name='".$row["name"]."' LIMIT 1";
			$result2 = $mysqli->query($sql2);
			
			
			
			$jsondataperson = array();
			$jsondataperson["id"] = $offset;
			$jsondataperson["name"] = $row["name"];
			$r = "Gracz";
			if ($row2 = $result2 ->fetch_assoc()) {
				$r = $row2["rank"];
			}
			$jsondataperson["rank"] = $r;
			$jsondataperson["level_total"] = $row["level_total"];
			$jsondataperson["total_time_online"] = intval($row["total_time_online"]/60).' godź.';

			$jsondataList[]=$jsondataperson;
			
			$offset++;

		}

		$jsondata["lista"] = array_values($jsondataList);
	}

header("Content-type:application/json; charset = utf-8");
echo json_encode($jsondata);
exit();
}

?>