<?php
$mysqli= new mysqli("localhost","notatest","Karaoke321.","education");

if($mysqli ->connect_error)
{
	echo "Wystąpił błąd podczas połączenia.".$mysqli->connect_error;

}
else
{

	$mysqli->set_charset("utf8");

	$jsondata = array();
	$jsondataList = array();

	if($_GET["user"] != null) {
		$user = $_GET["user"];
		$sql = "SELECT * FROM users_skyblock WHERE name='$user'";

		$result = $mysqli->query($sql);
		
		if($result->num_rows <= 0) {
			$jsondata["total"] = 0;
			echo $jsondata["total"];
			exit();
		} else {
			
			if ($row = $result ->fetch_assoc()) {
				/*$username = "asdasd";
				$json = file_get_contents("https://api.mojang.com/users/profiles/minecraft/$username");
				$obj = json_decode($json, true);
				$id = $obj['id'];
				
				echo ('<img src="https://crafatar.com/avatars/'.$id.'?size=48">');
				*/
				
				

				$jsondataperson = array();
				$jsondataperson["name"] = $row["name"];
				$jsondataperson["kills"] = $row["kills"];
				$jsondataperson["deaths"] = $row["deaths"];
				$jsondataperson["rank"] = $row["rank"];
				$jsondataperson["level_total"] = $row["level_total"];
				$jsondataperson["experience_jobs"] = $row["experience_jobs"];
				$jsondataperson["max_experience_jobs"] = $row["max_experience_jobs"];
				$jsondataperson["total_time_online"] = intval($row["total_time_online"]/60).' godź.';

				$jsondata["lista"] = $jsondataperson;


			}

		}

		
	}

header("Content-type:application/json; charset = utf-8");
echo json_encode($jsondata);
exit();
}

?>