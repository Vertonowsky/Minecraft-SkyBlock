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
$sql = "CREATE TABLE registered_names (id INT(50) UNSIGNED AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20) NOT NULL)";

if ($conn->query($sql) === TRUE) {
    echo "Table allusers created successfully";
} else {
    echo "Error creating table: " . $conn->error;
}

$conn->close();
?>