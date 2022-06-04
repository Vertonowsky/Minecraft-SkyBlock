<?php
include 'przelewy24/class_przelewy24.php';
?><!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pl">

<html>
	<head>
		<title>Prestigemc.pl | Sklep</title>
		<meta charset="utf-8" />
		<link rel="stylesheet" href="../assets/css/main.css" />
		<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css" rel="stylesheet">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="icon" href="../images/logo.png">
		<meta name="language" content="PL" />
		<meta name="Copyright" content="Wszystkie prawa zastrzezone 2020 Prestigemc" />
		
		
		<script src="../assets/js/jquery.min.js"></script>
		<script src="../assets/js/skel.min.js"></script>
		<script src="../assets/js/util.js"></script>
		<script src="../assets/js/main.js"></script>
	
	</head>
	<body>
	
			<?php

				session_start();

				//Fragment kodu odpowiedzialny za weryfikacje transakcji.
				if(isset($_GET["ok"])) {
					if($_GET["ok"]==2) {
						if(file_exists ("przelewy24/parametry.txt")){
							$result = file_get_contents("przelewy24/parametry.txt");
							
							$X = explode("&", $result);
									
							foreach($X as $val) {
								$Y = explode("=", $val);
								$FIL[trim($Y[0])] = urldecode(trim($Y[1]));
							}
							
							$P24 = new Przelewy24($_POST["p24_merchant_id"],$_POST["p24_pos_id"],$FIL['p24_crc'],$FIL['env']);
							
							foreach($_POST as $k=>$v) $P24->addValue($k,$v);  
							
							$P24->addValue('p24_currency',$FIL['p24_currency']);
							$P24->addValue('p24_amount',$FIL['p24_amount']);
							$res = $P24->trnVerify();
							if(isset($res["error"]) and $res["error"] === '0') {
								$msg = 'Transakcja zostala zweryfikowana poprawnie';
							}
							else {
								$msg = 'Bledna weryfikacja transakcji';
							}
							
						}
						else {
							   $msg = 'Brak pliku parametry.txt';
						}
						
						file_put_contents("przelewy24/weryfikacja.txt",date("H:i:s").": ".$msg." \n\n",FILE_APPEND);
						exit;
					}
				
				
					//Fragment kodu wyświetalący stronę po zakończeniu transakcji.
					if($_GET["ok"]==1) {
						
						echo '<style>#success-transaction{visibility: visible !important;}</style>';
					
					}
					
					
					//Fragment kodu wyświetalący stronę po zakończeniu transakcji.
					if($_GET["ok"]==3) {
						
						echo '<style>#failure-transaction{visibility: visible !important;}</style>';
					
					}
				}


			/*if(isset($_POST["submit_test"])) {
				echo '<h2>Wynik:</h2>';
					echo '<pre>RESPONSE:'.print_r($RET,true).'</pre>';  
				//$test = ($_POST["env"]==1?true:false);
				$test = false;
				$salt = $_POST["salt"];
				$P24 = new Przelewy24($_POST["p24_merchant_id"],
										$_POST["p24_pos_id"],
										$salt,
										$test
										);
										
				$RET = $P24->testConnection();
				echo '<pre>RESPONSE:'.print_r($RET,true).'</pre>';                            

			}else*/if(isset($_POST["submit_send"])) {
				echo '<h2>Wynik:</h2>';
				//$test = ($_POST["env"]==1?"1":"0");
				$test = false;
				$salt = '55946455200c5d5f';
				
				$P24 = new Przelewy24($_POST["p24_merchant_id"],
										$_POST["p24_pos_id"],
										$salt,
										$test);
			   
				foreach($_POST as $k=>$v) $P24->addValue($k,$v);                            
				
				file_put_contents("przelewy24/parametry.txt","p24_crc=".$salt."&p24_amount=".$_POST['p24_amount']."&p24_currency=".$_POST['p24_currency']."&env=0");
				
				
				//$bool = ($_POST["redirect"]=="on")? true:false;
				$bool = true;
				$res = $P24->trnRegister($bool);
				
				echo '<pre>RESPONSE:'.print_r($res,true).'</pre>';
				
				if(isset($res["error"]) and $res["error"]==='0') {

					echo '<br/><a href="'.$P24->getHost()."trnRequest/".$res["token"].'">'.$P24->getHost()."trnRequest/".$res["token"].'</a>';
					
					
				}
				
			}


			$protocol = ( isset($_SERVER['HTTPS'] )  && $_SERVER['HTTPS'] != 'off' )? "https://":"http://";  
			session_regenerate_id();
			?>
			
			
			<form action="/shop/index.php" method="post" class="form" name="fformn" id="fformn">
				<table>
					<tr><td><input type="hidden" style="width:250px" name="p24_merchant_id" value="63710" /></td></tr>
					<tr><td><input type="hidden" style="width:250px" name="p24_pos_id" value="63710" /></td></tr>
					<tr><td><input type="hidden" style="width:250px" name="p24_session_id" value="<?php echo md5(session_id().date("YmdHis")); ?>" /></td></tr>
					<tr><td><input type="hidden" style="width:250px" id="p24_amount" name="p24_amount" value="" /></td></tr>
					<tr><td><input type="hidden" style="width:250px" name="p24_currency" value="PLN" /></td></tr>
					<tr><td><input type="hidden" style="width:250px" id="p24_description" name="p24_description" value="" /></td></tr>
					<tr><td><input type="hidden" style="width:250px" name="p24_email" value="lukaszrozycki@grape-is.com" /></td></tr>
					<tr><td><input type="hidden" type="text" style="width:250px" name="p24_country" value="PL" /></td></tr>
					<tr><td><input type="hidden" type="text" style="width:250px" name="p24_language" value="PL" /></td></tr>
					<tr><td><input type="hidden" style="width:250px" name="p24_url_return" value="https://prestigemc.pl/shop/?ok=1" /></td></tr>
					<tr><td><input type="hidden" style="width:250px" name="p24_url_status" value="https://prestigemc.pl/shop/?ok=2" /></td></tr>
					<tr><td><input type="hidden" style="width:250px" name="p24_time_limit" value="30" /></td></tr>
					<tr><td><input type="hidden" type="text" style="width:250px" name="p24_wait_for_result" value="1" /></td></tr>
					<tr><td><input type="hidden" style="width:250px" id="p24_transfer_label" name="p24_transfer_label" value="None" /></td></tr>
					<tr><td><input type="hidden" type="text" style="width:250px" name="p24_api_version" value="<?php echo P24_VERSION; ?>" /></td></tr>
				</table>

			</form>
			
			
			<form action="/shop/simpay/generate.php" method="post" name="fformn2" id="fformn2">
				<table>
					<tr><td><input type="hidden" type="text" id="nick" name="nick" value="" /></td></tr>
					<tr><td><input type="hidden" type="text" id="simpay_amount" name="simpay_amount" value="" /></td></tr>
				</table>
			</form>
			
			
			
			
			
			
			
			
			
			<div id="success-transaction">
				<div id="success-info-panel">
					<p><i class="fas fa-check"></i></p>
					<h1>Transakcja zakończona.</h1>
					<h2>Usługa została zakupiona.</h2>
					<button class="close-dialog">Powrót do sklepu</button>
					
				</div>
			</div>
			
			<div id="failure-transaction">
				<div id="failure-info-panel">
					<p><i class="fas fa-times"></i></p>
					<h1>Wystąpił problem.</h1>
					<h2>Transakcja została odrzucona.</h2>
					<button class="close-dialog">Powrót do sklepu</button>
				</div>
			</div>
			

		<!-- Header -->
			 <header id="header">
				<nav id="nav">
					<a href="/prestigemc/">Strona główna</a>
					<a class="active" href="/prestigemc/shop">Sklep</a>
					<a href="/prestigemc/ranking">Ranking</a>
				</nav>
			</header>
			<a href="#menu" class="navPanelToggle"><i class="fa fa-bars"></i></a>

		<!-- Banner -->
			<span class="cont">
				<section id="banner2">
					<div class="shadow">
						<div class="shop-title">
							<p class="title">Serwerowy sklep Prestigemc.pl</p>
							<p class="subtitle">życzymy udanych zakupów!</p>
						</div>
						
					</div>	

				</section>
			</span>
			
			
				<div id="online-circle">
					<svg width="45" height="45" viewbox="0 0 40 40">
						<circle cx="20" cy="20" fill="none" r="10" stroke="#3CCE6A" stroke-width="2">
							<animate attributeName="r" from="8" to="20" dur="1.5s" begin="0s" repeatCount="indefinite"/>
							<animate attributeName="opacity" from="1" to="0" dur="1.5s" begin="0s" repeatCount="indefinite"/>
						</circle>
						<circle cx="20" cy="20" fill="#3CCE6A" r="10"/>
					</svg>
					
					<p>Serwer włączony</p>
				</div>
			
			
			
			
		<!-- Two -->
			<section id="shop_panel">
				<div class="inner">
					<div id="shop_panel_items">
						<div class="item_relative">
							<div class="item" id="item1">
								<div class="shadow" id="shadow1">
								</div>
								<img width="200px" src="../images/VIP_2.png" />
									
								<h4 id="price_vip" >Cena już od 9zł</h4>
								
								<a id="button_vip">Więcej!</a>
							
							</div>
							
						</div><div class="item_relative">
							<div class="item" id="item2">
								<div class="shadow" id="shadow2">
								</div>
								<img src="../images/SUPER_VIP_2.png" />
									
								<h4 id="price_super_vip" >Cena już od 16zł</h4>
								
								<a id="button_super_vip">Więcej!</a>
							
							</div>
							
						</div><div class="item_relative">
							<div class="item" id="item3">
								<div class="shadow" id="shadow3">
								</div>
								<img src="../images/KEY_2.png" />

								<h4 id="price_key" >Cena już od 4zł</h4>
								
								<a id="button_key">Więcej!</a>
							
							</div>
							
						</div><div class="item_relative">
							<div class="item" id="item4">
								<div class="shadow" id="shadow4">
								</div>
								<img src="../images/UNBAN_2.png" />
									
								<h4 id="price_unban" >Cena już od 17zł</h4>
								
								<a id="button_unban">Więcej!</a>
							
							</div>
						</div>

					</div><div id="shop_terms_of_use_panel">
					
						<div class="terms_of_use"> 
							<a href="https://simpay.pl/dokumenty/simpay_regulamin_serwisu.pdf"><img src="../images/simpay_banner.png"/></a>
						</div>
						
						<div class="terms_of_use"> 
							<a href="https://www.przelewy24.pl/regulamin"><img src="../images/logo-przelewy-24.svg"/></a>
						</div>
						
						<p>Zamówienie realizowane jest natychmiastowo po zweryfikowaniu płatności przez operatora.<br><br> Dokonując zakupu akceptujesz nasz <a href="/terms"> regulamin</a> i jednocześnie oświadczasz rezygnację z możliwości zwrotu usługi.</p>
					
					
					</div>
				</div>
			</section>
			

		<!-- Footer -->
			<?php 
				$path = $_SERVER['DOCUMENT_ROOT'];
				$path .= "/prestigemc/content/footer.php";
				include_once($path);
			?>



			<div id="myPopup" class="popup">
				<span class="close2"><i class="fas fa-times"></i></span>
				<p id="popup-info"></p>
			
			</div>
			
			<input type="hidden" id="payment_id" value="przelew"></input>
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			<div id="modalVip" class="modal">

				<!-- Modal content -->
				<div class="modal-content2">
				
					<span class="close"><i class="fas fa-times"></i></span>
					
					
					<div class="modal-content-service">
						<div class="offer-image">
							<img width="50%" height="50%" src="../images/VIP.png" />
						</div>
						
						<ul type="square" class="offer-description">
							<li>Dostęp do rangi VIP na 30 dni.</li>
							<li>Unikalny prefiks na czacie.</li>
							<li>Dostęp do wyłączonego czatu.</li>
							<li>Dostęp do komendy /fly.</li>
							<li>Unikalny schemat początkowej wyspy.</li>
							<li>Max. 4 członków wyspy.</li>
							<li>Max. 2 lokalizacje /dom.</li>
							<li>Dostęp do biomu hell i sky.</li>
							<li>Zerezerwowany slot na serwerze</li>
						</ul>
					</div>
					
					
					<div class="modal-content-payment">
					
						<p class="title">Podaj swój nick:</p>
					
						<div class="center">
							<div id="group">     
								<input class="first_name" name="first_name" type="text" maxlength="16" required>
								<span class="highlight"></span>
								<span class="bar"></span>
								<label>Nazwa w grze..</label>
							</div>
						</div>
						
						<p class="title">Wybierz metodę płatności:</p>
						
						
						<div class="center">
							<input id="toggle-przelew" class="toggle toggle-left" name="toggle" onclick="changeButtonPrice(button_buy_vip, 9, 'przelew')" value="false" type="radio" checked>
							<label for="toggle-przelew" class="btn">Przelew</label>
							<!--<input id="toggle-psc" class="toggle toggle-center" name="toggle" onclick="changeButtonPrice(button_buy_vip, 9, 'psc')" value="true" type="radio">
							<label for="toggle-psc" class="btn">PSC</label>
							<input id="toggle-sms" class="toggle toggle-right" name="toggle" onclick="changeButtonPrice(button_buy_vip, 13.20, 'smsVIP')" value="true2" type="radio">
							<label for="toggle-sms" class="btn">SMS</label>-->
						</div>
						
						<button id="button_buy_vip" class="button-purchase" onclick="userExists(0, button_buy_vip2, 'VIP30', this.value)" value="9">Kupuję | 9.00 zł</button>
						<button id="button_buy_vip2" class="invisible-button" name="submit_send" type="submit" form="fformn" value="Submit"></button>
						
					</div>

				</div>

			</div>
			
			
			
			<div id="modalSuperVip" class="modal">

				<!-- Modal content -->
				<div class="modal-content2">
				
					<span class="close"><i class="fas fa-times"></i></span>
					
					
					<div class="modal-content-service">
						<div class="offer-image">
							<img src="../images/SUPER_VIP.png" />
						</div>
						
						<ul type="square" class="offer-description">
							<li>Dostęp do rangi SuperVIP na 60 dni.</li>
							<li>Unikalny prefiks na czacie.</li>
							<li>Dostęp do wyłączonego czatu.</li>
							<li>Dostęp do komendy /fly.</li>
							<li>Unikalny schemat początkowej wyspy.</li>
							<li>Max. 10 członków wyspy.</li>
							<li>Max. 2 lokalizacji /dom.</li>
							<li>Dostęp do biomu hell i sky.</li>
							<li>Zerezerwowany slot na serwerze</li>
						</ul>
					</div>
					
					
					<div class="modal-content-payment">
					
						<p class="title">Podaj swój nick:</p>
					
						<div class="center">
							<div id="group">     
								<input class="first_name" name="first_name" type="text" maxlength="16" required>
								<span class="highlight"></span>
								<span class="bar"></span>
								<label>Nazwa w grze..</label>
							</div>
						</div>
						
						<p class="title">Wybierz metodę płatności:</p>
						
						
						<div class="center">
							<input id="toggle-przelew2" class="toggle toggle-left" name="toggle2" onclick="changeButtonPrice(button_buy_super_vip, 16, 'przelew')" value="false" type="radio" checked>
							<label for="toggle-przelew2" class="btn">Przelew</label>
							<!--<input id="toggle-psc2" class="toggle toggle-center" name="toggle2" onclick="changeButtonPrice(button_buy_super_vip, 16, 'psc')" value="true" type="radio">
							<label for="toggle-psc2" class="btn">PSC</label>
							<input id="toggle-sms2" class="toggle toggle-right" name="toggle2" onclick="changeButtonPrice(button_buy_super_vip, 21.89, 'smsSuperVIP')" value="true2" type="radio">
							<label for="toggle-sms2" class="btn">SMS</label>-->
							
						</div>
						
						<button id="button_buy_super_vip" class="button-purchase" onclick="userExists(1, button_buy_super_vip2, 'SuperVIP60', this.value)" value="16">Kupuję | 16.00 zł</button>
						<button id="button_buy_super_vip2" class="invisible-button" name="submit_send" type="submit" form="fformn" value="Submit"></button>
						
					</div>
				</div>

			</div>
			
			
			<div id="modalKey" class="modal">

				<!-- Modal content -->
				<div class="modal-content2">
				
					<span class="close"><i class="fas fa-times"></i></span>
					
					
					<div class="modal-content-service">
						<div class="offer-image">
							<img width="50%" height="50%" src="../images/KEY.png" />
						</div>
						
						<ul type="square" class="offer-description">
							<li>Klucz otwierający Złotą Skrzynię.<br><br>Skrzynia zawiera: 1-4000 monet</li>
						</ul>
					</div>
					
					
					<div class="modal-content-payment">
					
						<p class="title">Podaj swój nick:</p>
					
						<div class="center">
							<div id="group">         
								<input class="first_name" name="first_name" type="text" maxlength="16" required>
								<span class="highlight"></span>
								<span class="bar"></span>
								<label>Nazwa w grze..</label>
							</div>
						</div>
						
						<p class="title">Wybierz metodę płatności:</p>
						
						
						<div class="center">
							<input id="toggle-przelew3" class="toggle toggle-left" name="toggle3" onclick="changeButtonPrice(button_buy_key, 4, 'przelew')" value="false" type="radio" checked>
							<label for="toggle-przelew3" class="btn">Przelew</label>
							<!--<input id="toggle-psc3" class="toggle toggle-center" name="toggle3" onclick="changeButtonPrice(button_buy_key, 4, 'psc')" value="true" type="radio">
							<label for="toggle-psc3" class="btn">PSC</label>
							<input id="toggle-sms3" class="toggle toggle-right" name="toggle3" onclick="changeButtonPrice(button_buy_key, 5.90, 'smsKlucz')" value="true2" type="radio">
							<label for="toggle-sms3" class="btn">SMS</label>-->
							
						</div>
						
						<button id="button_buy_key" class="button-purchase" onclick="userExists(2, button_buy_key2, 'Klucz', this.value)" value="4">Kupuję | 4.00 zł</button>
						<button id="button_buy_key2" class="invisible-button" name="submit_send" type="submit" form="fformn" value="Submit"></button>
						
					</div>
				</div>

			</div>
			
			
			<div id="modalUnBan" class="modal">

				<!-- Modal content -->
				<div class="modal-content2">
				
					<span class="close"><i class="fas fa-times"></i></span>
					
					
					<div class="modal-content-service">
						<div class="offer-image">
							<img src="../images/UNBAN.png" />
						</div>
						
						<ul type="square" class="offer-description">
							<li>Odbanuj swój nick na serwerze!</li>
						</ul>
					</div>
					
					
					<div class="modal-content-payment">
					
						<p class="title">Podaj swój nick:</p>
					
						<div class="center">
							<div id="group">      
								<input class="first_name" name="first_name" type="text" maxlength="16" required>
								<span class="highlight"></span>
								<span class="bar"></span>
								<label>Nazwa w grze..</label>
							</div>
						</div>
						
						<p class="title">Wybierz metodę płatności:</p>
						
						
						<div class="center">
							<input id="toggle-przelew4" class="toggle toggle-left" name="toggle4" onclick="changeButtonPrice(button_buy_unban, 17, 'przelew')" value="false" type="radio" checked>
							<label for="toggle-przelew4" class="btn">Przelew</label>
							<!--<input id="toggle-psc4" class="toggle toggle-center" name="toggle4" onclick="changeButtonPrice(button_buy_unban, 17, 'psc')" value="true" type="radio">
							<label for="toggle-psc4" class="btn">PSC</label>
							<input id="toggle-sms4" class="toggle toggle-right" name="toggle4" onclick="changeButtonPrice(button_buy_unban, 24.85, 'smsUnBan')" value="true2" type="radio">
							<label for="toggle-sms4" class="btn">SMS</label>-->
							
						</div>
						
						<button id="button_buy_unban" class="button-purchase" onclick="userExists(3, button_buy_unban2, 'UnBan', this.value)" value="17">Kupuję | 17.00 zł</button>
						<button id="button_buy_unban2" class="invisible-button" name="submit_send" type="submit" form="fformn" value="Submit"></button>
						
					</div>
				</div>

			</div>
			
			
			
			
		<script>
		
			// Get the <span> element that closes the popup
			var closeDialog = document.getElementsByClassName("close-dialog")[0];
			var closeDialog2 = document.getElementsByClassName("close-dialog")[1];
			var successTransaction = document.getElementById('success-transaction');
			var failureTransaction = document.getElementById('failure-transaction');

			// When the user clicks on <span> (x), close the popup
			closeDialog.onclick = function() {
				document.location.href = 'https://prestigemc.pl/shop';
			}
			
			closeDialog2.onclick = function() {
				document.location.href = 'https://prestigemc.pl/shop';
			}
			
		
			function changeButtonPrice(button, price, payment_id) {
				button.innerHTML = "Kupuję | " + price.toFixed(2) + " zł";
				document.getElementById("payment_id").value = payment_id;
			}
			
			
			
			
			
			


			function firstItem(itemId, button, type, amount, name) {
				var name = document.getElementsByClassName("first_name")[itemId].value;
				if (document.getElementById("payment_id").value == "smsVIP") {
					document.getElementById("nick").value = "VIP30 " + name;
					document.getElementById("simpay_amount").value = 13.20;
					document.getElementById("button_buy_vip2").setAttribute('form','fformn2');
					document.getElementById("button_buy_vip2").click();
				}
				
				if (document.getElementById("payment_id").value == "smsSuperVIP") {
					document.getElementById("nick").value = "SuperVIP60 " + name;
					document.getElementById("simpay_amount").value = 21.89;
					document.getElementById("button_buy_super_vip2").setAttribute('form','fformn2');
					document.getElementById("button_buy_super_vip2").click();
				}
				
				if (document.getElementById("payment_id").value == "smsKlucz") {
					document.getElementById("nick").value = "Klucz " + name;
					document.getElementById("simpay_amount").value = 5.90;
					document.getElementById("button_buy_key2").setAttribute('form','fformn2');
					document.getElementById("button_buy_key2").click();
				}
				
				if (document.getElementById("payment_id").value == "smsUnBan") {
					document.getElementById("nick").value = "UnBan " + name;
					document.getElementById("simpay_amount").value = 24.85;
					document.getElementById("button_buy_unban2").setAttribute('form','fformn2');
					document.getElementById("button_buy_unban2").click();
				}
				
				
				
				if (document.getElementById("payment_id").value == "przelew") {
					document.getElementById("p24_amount").value = amount*100;
					document.getElementById("p24_transfer_label").value = type + " " + name;
					document.getElementById("p24_description").value = type + "|" + name;
					
					document.getElementById("button_buy_vip2").click();
				}
			}
			
			
			
			
			function userExists(itemId, button, type, amount) {
				var name = document.getElementsByClassName("first_name")[itemId].value;
				// Create our XMLHttpRequest object
				var hr = new XMLHttpRequest();
				// Create some variables we need to send to our PHP file
				var url = "isUser.php";
				var vars = "firstname="+name;
				hr.open("POST", url, true);
				// Set content type header information for sending url encoded variables in the request
				hr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				// Access the onreadystatechange event for the XMLHttpRequest object
				hr.onreadystatechange = function() {
					if(hr.readyState == 4 && hr.status == 200) {
						var return_id = hr.responseText.split("||")[1];
						var return_data = hr.responseText.split("||")[0];
						
						if (return_id == 1) showPopup(return_data, 0);
						if (return_id == 0) {
							firstItem(itemId, button, type, amount, name);
						}
					}
				}
				// Send the data to PHP now... and wait for response to update the status div
				hr.send(vars); // Actually execute the request
			}
			
			
			function calledFromAjaxSuccess(result) {
				if(result) {
					alert('TRUE');
				} else {
					alert('FALSE');
				}
			}
			
			
		
		
		
		
		
		
		

			var delay = 0;
			var text1 = "";
			var status1 = 0;
			
			var timer;
			
			
			function showPopup(text, status) {
				delay = 4;
				text1 = text;
				status1 = status;
				conf();
				//close popup after 3 seconds
			}
			
			function conf() {
				delay--;
				var popup = document.getElementById('myPopup');
				
				if (delay < 0) {
					clearTimeout(timer);
					timer = null;
					return;
				}
				
				if (delay > 0) {
					//show popup
					if (status1 == 0) popup.style.backgroundColor="#F65655";
					if (status1 == 1) popup.style.backgroundColor="#1CA3D9";
					popup.style.width = "350px";
					
					document.getElementById("popup-info").innerHTML = text1;
					clearTimeout(timer);
				}
				
				if (delay == 0) {
					popup.style.width="0px";
					document.getElementById("popup-info").innerHTML = "";
				}
				timer = setTimeout("conf()", 1000);
				console.log(delay);
			}
			
			// Get the <span> element that closes the popup
			var closeBtn = document.getElementsByClassName("close2")[0];

			// When the user clicks on <span> (x), close the popup
			closeBtn.onclick = function() {
				document.getElementById('myPopup').style.width="0px";
				document.getElementById("popup-info").innerHTML = "";
			}
			
			
		
		

		
		
		
		
		
		
			// Get the modal
			var modal_vip = document.getElementById('modalVip');

			// Get the button that opens the modal
			var button_vip = document.getElementById("button_vip");

			// Get the <span> element that closes the modal
			var span = document.getElementsByClassName("close")[0];

			// When the user clicks the button, open the modal 
			button_vip.onclick = function() {
				modal_vip.style.display = "block";
				//document.body.style.overflow = 'hidden';
			}

			// When the user clicks on <span> (x), close the modal
			span.onclick = function() {
				modal_vip.scrollTo(0, 0);
				modal_vip.style.display = "none";
			}
			
			// When the user clicks anywhere outside of the modal, close it
			window.onclick = function(event) {
				if (event.target == modal_vip) {
					modal_vip.scrollTo(0, 0);
					modal_vip.style.display = "none";
				}
				
				if (event.target == modal_super_vip) {
					modal_super_vip.scrollTo(0, 0);
					modal_super_vip.style.display = "none";
				}
				
				if (event.target == modal_key) {
					modal_key.scrollTo(0, 0);
					modal_key.style.display = "none";
				}
				
				if (event.target == modal_unban) {
					modal_unban.scrollTo(0, 0);
					modal_unban.style.display = "none";
				}
				
				if (event.target == successTransaction) {
					document.location.href = 'https://prestigemc.pl/shop';
				}
				
				if (event.target == failureTransaction) {
					document.location.href = 'https://prestigemc.pl/shop';
				}
			}
			
			
			
			// Get the modal
			var modal_super_vip = document.getElementById('modalSuperVip');

			// Get the button that opens the modal
			var button_super_vip = document.getElementById("button_super_vip");

			// Get the <span> element that closes the modal
			var span2 = document.getElementsByClassName("close")[1];

			// When the user clicks the button, open the modal 
			button_super_vip.onclick = function() {
				modal_super_vip.style.display = "block";
				//document.body.style.overflow = 'hidden';
			}

			// When the user clicks on <span> (x), close the modal
			span2.onclick = function() {
				modal_super_vip.scrollTo(0, 0);
				modal_super_vip.style.display = "none";
			}


			
			// Get the modal
			var modal_key = document.getElementById('modalKey');

			// Get the button that opens the modal
			var button_key = document.getElementById("button_key");

			// Get the <span> element that closes the modal
			var span3 = document.getElementsByClassName("close")[2];

			// When the user clicks the button, open the modal 
			button_key.onclick = function() {
				modal_key.style.display = "block";
				//document.body.style.overflow = 'hidden';
			}

			// When the user clicks on <span> (x), close the modal
			span3.onclick = function() {
				modal_key.scrollTo(0, 0);
				modal_key.style.display = "none";
			}
			
			
			
			// Get the modal
			var modal_unban = document.getElementById('modalUnBan');

			// Get the button that opens the modal
			var button_unban = document.getElementById("button_unban");

			// Get the <span> element that closes the modal
			var span4 = document.getElementsByClassName("close")[3];

			// When the user clicks the button, open the modal 
			button_unban.onclick = function() {
				modal_unban.style.display = "block";
				//document.body.style.overflow = 'hidden';
			}

			// When the user clicks on <span> (x), close the modal
			span4.onclick = function() {
				modal_unban.scrollTo(0, 0);
				modal_unban.style.display = "none";
			}

	</script>
	</body>
</html>