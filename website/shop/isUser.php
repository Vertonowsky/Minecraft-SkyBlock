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

$sql = "SELECT name FROM users_general WHERE name='$nick'";
$result = $conn->query($sql);



//1 - "Najpierw wpisz wybraną nazwę w polu tekstowym!"
//2 - "Dozwolone znaki (a-z, A-Z, 0-9, -, _). Zwróć uwagę na spacje!!"
//3 - "Ta nazwa jest już zajęta!"
//4 - "Pomyślnie dodałeś nazwę $nick!"


$msg = "";

if (empty($nick)) {
	$msg = "<i class=\"fas fa-exclamation-circle\"></i> Najpierw wpisz nazwę gracza w polu tekstowym!"."||1";
	echo $msg;
	exit;
}

if (!preg_match("/^[a-zA-Z0-9_]+$/", $nick)) {
	$msg = "<i class=\"fas fa-exclamation-circle\"></i> Dozwolone znaki (a-z, A-Z, 0-9, _). Zwróć uwagę na spacje!!"."||1";
	echo $msg;
	exit;
}

if($result->num_rows <= 0) {
	$msg =  "<i class=\"fas fa-exclamation-circle\"></i> Nie ma gracza o takiej nazwie."."||1";
} else { 
	$msg = "<i class=\"fas fa-check-circle\"></i> Pomyślnie dodałeś nazwę $nick!"."||0";
}

echo $msg;

$conn->close();

?>