<?php
$mysqli= new mysqli("localhost","Admin","Admin123.","education");

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

		$sql = "SELECT COUNT(*) total FROM users_skyblock";

		$result = $mysqli->query($sql);

		$row = $result ->fetch_assoc();

		$jsondata['total'] = $row['total'];
	}
	elseif($_GET["param"]=="records")
	{
		$offset = $_GET["offset"]+1;
		$sql = "SELECT * FROM users_skyblock ORDER BY level_total DESC LIMIT ".$mysqli->real_escape_string($_GET['limit'])." OFFSET ".$mysqli->real_escape_string($_GET["offset"]);

		$result = $mysqli->query($sql);
		while($row = $result ->fetch_assoc())
		{
			
			$sql2 = "SELECT rank FROM users_general WHERE name='".$row["name"]."' LIMIT 1";
			$result2 = $mysqli->query($sql2);
			
			/*$username = "asdasd";
			$json = file_get_contents("https://api.mojang.com/users/profiles/minecraft/$username");
			$obj = json_decode($json, true);
			$id = $obj['id'];
			
			echo ('<img src="https://crafatar.com/avatars/'.$id.'?size=48">');
			*/
			

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