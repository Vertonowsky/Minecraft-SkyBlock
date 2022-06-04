<?php
$servername = "localhost";
$username = "Admin";
$password = "Admin123";
$dbname = "education";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

// sql to create table
$sql = "CREATE TABLE p24_transactions (id INT AUTO_INCREMENT PRIMARY KEY, sessionId VARCHAR(100) NOT NULL, date_initialize DATETIME NOT NULL, amount INT NOT NULL, title VARCHAR(100) NOT NULL) CHARACTER SET UTF8";
$sql2 = "CREATE TABLE p24_success (id INT AUTO_INCREMENT PRIMARY KEY, sessionId VARCHAR(100) NOT NULL, orderId INT NOT NULL, date_proceed DATETIME NOT NULL, amount INT NOT NULL, currency VARCHAR(5) NOT NULL, title VARCHAR(100) NOT NULL, name VARCHAR(20) NOT NULL, received BOOLEAN NOT NULL, error INT NOT NULL) CHARACTER SET UTF8";
$sql3 = "CREATE TABLE simpay_transactions (id VARCHAR(64) PRIMARY KEY, orderId INT NOT NULL, date_initialize DATETIME NOT NULL, date_proceed DATETIME, serviceId int NOT NULL, title VARCHAR(100) NOT NULL, name VARCHAR(20) NOT NULL, value_brutto VARCHAR(10) NOT NULL, value_netto VARCHAR(10) NOT NULL, value_partner VARCHAR(10) NOT NULL, phone_number VARCHAR(15) NOT NULL, received BOOLEAN NOT NULL, status VARCHAR(20) NOT NULL) CHARACTER SET UTF8";


if ($conn->query($sql) === TRUE) {
    echo "Table p24_transactions created successfully";
} else {
    echo "Error creating table 'p24_transactions': " . $conn->error;
}

echo "<br>";


if ($conn->query($sql2) === TRUE) {
    echo "Table p24_success created successfully";
} else {
    echo "Error creating table 'p24_success': " . $conn->error;
}

echo "<br>";


if ($conn->query($sql3) === TRUE) {
    echo "Table simpay_transactions created successfully";
} else {
    echo "Error creating table 'simpay_transactions': " . $conn->error;
}

echo "<br>";

if ($conn->connect_error) {
	die("Connection failed: " . $conn->connect_error);
} 


//$date = date('Y-m-d H:i:s');

//$sql = "INSERT INTO simpay_transactions (id, date, serviceId, title, value_brutto, status) VALUES (null, '$date', 'aha', '20', '20', 'new')";
//$conn->query($sql);



	/*$date = date('Y-m-d H:i:s');

	$sql = "INSERT INTO simpay_transactions (id, sessionId, date, serviceId, title, value_brutto, status) VALUES (null, '$sessionId', '$date', '$serviceId', '$title', '$value', 'new')";
	$conn->query($sql);
	$conn->close();*/


$conn->close();
?>