<?php
$servername = "localhost";
$username = "Admin";
$password = "Admin123";
$dbname = "education";
$nick = $_POST['firstname'];

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "SELECT * FROM `users_general` WHERE BINARY `name` ='$nick'";
$result = $conn->query($sql);



//1 - "Najpierw wpisz wybraną nazwę w polu tekstowym!"
//2 - "Dozwolone znaki (a-z, A-Z, 0-9, -, _). Zwróć uwagę na spacje!!"
//3 - "Ta nazwa jest już zajęta!"
//4 - "Pomyślnie dodałeś nazwę $nick!"


$msg = "";

if (empty($nick)) {
	$msg = "<i class=\"fas fa-exclamation-circle\"></i> Najpierw wpisz wybraną nazwę w polu tekstowym!"."||0";
	echo $msg;
	exit;
}

if (!preg_match("/^[a-zA-Z0-9_]+$/", $nick)) {
	$msg = "<i class=\"fas fa-exclamation-circle\"></i> Dozwolone znaki (a-z, A-Z, 0-9, _). Zwróć uwagę na spacje!!"."||0";
	echo $msg;
	exit;
}

if($result->num_rows > 0) {
	$msg =  "<i class=\"fas fa-exclamation-circle\"></i> Ta nazwa jest już zajęta!"."||0";
} else { 
	$sql = "INSERT INTO users_general (id, name) VALUES (NULL, '$nick')";
	if ($conn->query($sql) === TRUE) {
		$msg = "<i class=\"fas fa-check-circle\"></i> Pomyślnie dodałeś nazwę $nick!"."||1";
	} else {
		$msg = "<i class=\"fas fa-exclamation-circle\"></i> Błąd: " . $sql . "<br>" . $conn->error ."||0";
	}
}

echo $msg;

$conn->close();

?>