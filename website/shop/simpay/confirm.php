<?php
require_once('SimPayDB.php');

$cfg = array(
  /*
  * Klucz API uslugi
  * Typ pola string
  */
  'apiKey' => '2c76e937',
  'apiSecret' => 'b87b2a03debfa7734c20088a1b6dcfb2'
);


$conn = new mysqli('localhost', 'Admin', 'Admin123', 'education');
if ($conn->connect_error) {
	die("Connection failed: " . $conn->connect_error);
} 


$simPay = new SimPayDB();

$simPay->setApiKey($cfg['simpay']['apiKey']);
$simPay->setApiSecret($cfg['simpay']['apiSecret']);

if (!$simPay->checkIp($simPay->getRemoteAddr())) {
    $simPay->okTransaction();
    exit();
}

//Parsowanie informacji pobranych z POST
if ($simPay->parse($_POST)) {

    //Sprawdzenie czy parsowanie przebieglo pomyslnie
    if ($simPay->isError()) {
        //Zwr�cenie ze transakcja zostala pomyslnie odebrana przez partnera
        $simPay->okTransaction();
        exit();
    }

    //Dodanie informacji o transakcji do bazy danych
    //$simPay->getStatus() - Obecny status transakcji
    //$simPay->getValuePartner() - Ile partner rzeczywiscie uzyskal prowizji
    //$simPay->getControl() - Wartosc control wyslana przy starcie transakcji

    //Sprawdzenie czy transakcja zostala oplacona
    if ($simPay->isTransactionPaid()) {
		$simPay->okTransaction();
    }
} else {
    //Sprawdzenie typu bledu
    error_log($simPay->getErrorText());
}



$date = date('Y-m-d H:i:s');
$orderId = $simPay->getTransactionId();
$valueNetto = $simPay->getValue();
$valueBrutto = $simPay->getValueGross();
$valuePartner = $simPay->getValuePartner();
$phoneNumber = $simPay->getUserNumber();
$control = $simPay->getControl();
$status = $simPay->getStatus();


$sql = "UPDATE simpay_transactions SET orderId='$orderId', date_proceed='$date', value_netto='$valueNetto', value_brutto='$valueBrutto', value_partner='$valuePartner', phone_number='$phoneNumber', status='$status' WHERE id='$control'";
$conn->query($sql);
$conn->close();

//Zwr�cenie ze transakcja zostala pomyslnie odebrana przez partnera
//Wartosc zwracana przez partnera powinna zawierac tylko "OK". System SimPay uzna wtedy, ze transakcja zostala poprawnie obsluzona i nie bedzie ponawial zapytan do serwisu partnera.

$simPay->okTransaction();
exit();





