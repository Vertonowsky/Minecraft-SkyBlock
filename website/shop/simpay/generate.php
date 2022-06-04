<?php

$cfg = array(
	'simpay' => array(
		/*
			Tryb debugowania
			Typ pola bolean true/false
			Opis Ustawienie pola na TRUE, wlaczna tryb debugowania, kt�ry wyswietla bledy np. w konfiguracji.
		*/
		'debugMode' => TRUE,
		/*
			ID uslugi
			Typ pola int, np. 60
			Opis ID Uslugi DirectCarrierBilling z panelu simpay.pl, 
		*/
		'serviceId' => 115,
		/*
			Klucz API uslugi
			Typ pola string
		*/
		'apiKey' => 'MaHBkVe6Ft4qps5K',
		/*
			Control
			Typ pola string
		*/
		'control' => hash('sha256', time()),
		/*
			Control
			Typ pola string
		*/
		'nick' => $_POST["nick"],
		/*
			Adres URL do powrotu po prawidlowej transakcji
			Typ pola string
			Opis Uzytkownik jest przekierowywany na ten adres po prawidlowo zakonczonej transakcji.
		*/
		'completeUrl' => 'https://www.prestigemc.pl/shop/?ok=1',
		/*
			Adres URL do powrotu po nieudanej transakcji
			Typ pola string
			Opis Uzytkownik jest przekierowywany na ten adres po nieprawidlowo zakonczonej transakcji.
		*/
		'failureUrl' => 'https://www.prestigemc.pl/shop/?ok=3',
		/*
			Kwota transakcji
			Typ pola float
		*/
		'amount' => $_POST["simpay_amount"],
		/*
			Typ ustalania prowizji
			Typ pola enum?
			Opis
				-> Ustawienie opcji amount (netto)
				-> Ustawienie opcji amount_gross (brutto)
				-> Ustawienie opcji amount_required (kwota jaka otrzyma klient niezaleznie od wybranego operatora)
		*/
		'amountType' => 'amount_gross'
	)
);




$array = array(
	'serviceId' => $cfg['simpay']['serviceId'],
	'control' => $cfg['simpay']['control'],
	'complete' => $cfg['simpay']['completeUrl'],
	'failure' => $cfg['simpay']['failureUrl'],
	'sign' => hash('sha256', $cfg['simpay']['serviceId'] . $cfg['simpay']['amount'] . $cfg['simpay']['control'] . $cfg['simpay']['apiKey'])
);

if ($cfg['simpay']['amountType'] == "amount") {
	$array['amount'] = $cfg['simpay']['amount'];
} elseif ($cfg['simpay']['amountType'] == "amount_gross") {
	$array['amount_gross'] = $cfg['simpay']['amount'];
} else {
	$array['amount_required'] = $cfg['simpay']['amount'];
}


$ch = curl_init('https://simpay.pl/db/api');
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLINFO_HEADER_OUT, true);
curl_setopt($ch, CURLOPT_POST, true);
curl_setopt($ch, CURLOPT_POSTFIELDS, $array);
$result = curl_exec($ch);
curl_close($ch);
$result = json_decode($result);

if ($result->status == "success") {
	
	
	$conn = new mysqli('localhost', 'Admin', 'Admin123', 'education');
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	} 

	$sessionId = $result->name;
	$serviceId = $cfg['simpay']['serviceId'];
	$id = $cfg['simpay']['control'];
	$title = $cfg['simpay']['nick'];
	$value = $cfg['simpay']['amount'];

	$date = date('Y-m-d H:i:s');
	
	$nameArray = explode(" ", $title);
	$name = $nameArray[1];

	$sql = "INSERT INTO simpay_transactions (id, date_initialize, serviceId, title, name, value_brutto, status) VALUES ('$id', '$date', '$serviceId', '$title', '$name', '$value', 'new')";
	$conn->query($sql);
	$conn->close();
	
	
	
	header('Location: ' . $result->link);
	exit();
} else {
	echo 'Wystapil blad podczas generowania transakcji.';
	if ($cfg['simpay']['debugMode']) {
		echo '<br/>Debug --> ';
		print_r($result);
	}
}

?>