<!DOCTYPE HTML>
<html lang="pl">

<html>
	<head>
		<title>Prestigemc.pl</title>
		<meta name="description" content="Prestigemc.pl to najszybciej rozwijający się serwer Minecraft w polsce! Mile widziani sa także gracze non-premium!" />
		<meta charset="utf-8" />
		<link rel="stylesheet" href="assets/css/main.css" />
		<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css" rel="stylesheet">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="google-site-verification" content="PZqjDQuzLNOueaa4Rrnt4hZwNsEbU-oOWfrSipkJ-8o" />
		<link rel="icon" href="images/logo.png">
		
		<!-- Scripts -->
			<script src="assets/js/jquery.min.js"></script>
			<script src="assets/js/skel.min.js"></script>
			<script src="assets/js/util.js"></script>
			<script src="assets/js/main.js"></script>

		
		<script src="https://www.google.com/recaptcha/api.js" async defer></script>
		
		
	
	</head>
	<body>

		<!-- Header -->
			 <header id="header">
				<img class="aha" src="images/PRESTIGEMC_1.png"/>
				<nav id="nav">
					<a class="active" href="/prestigemc/">Strona główna</a>
					<!--<a href="forum">Forum</a>-->
					<a href="shop">Sklep</a>
					<a href="ranking">Ranking</a>
				</nav>
			</header>
			<a href="#menu" class="navPanelToggle"><i class="fa fa-bars"></i></a>
	
		<!-- Banner -->
			<section id="banner">
				<div class="shadow">

					<p class="title" style="margin-top: 200px;">Najszybciej rozwijający się serwer minecraft</p>
					<p class="subtitle" >otwarty na wasze propozycje!</p>
					
					<!--<input class="normal-button" name="myBtn" id="offer-vip" type="submit" value="Dołącz do gry!">-->
					
				</div>	

					
			</section>
			
		<!-- One -->
			<section id="one">
				<div class="square">
					<a class="copier" onclick="copyServerIp('prestigemc.pl')"><b style="color: black; font-weight: bold;">Adres IP:</b> prestigemc.pl</a>
					<p class="copier" onclick="copyServerIp('prestigemc.pl')"><i class="fas fa-mouse-pointer"></i> Kliknij aby skopiować</p>
				</div>
			</section>
			
			
		<!-- Two -->
			<section id="two">
				<div class="left">
					<img src="./images/BODY.png"/>
				</div><div class="right">
					<div class="desc">
						<h3>Czym jest prestigemc</h3><br>
						
						<h4>Prestigemc to nowoczesny serwer łączący graczy gry Minecraft. Stale zapewniamy wsparcie wszystkim użytkownikom tak aby mile spędzić z nami każdą chwilę.<br><br><font style="color: #27ADE4; font-weight: bold;">Zapraszamy wszystkich na tryb SkyBlock!</font></h4>
					</div>
				</div>
			</section>
		
		
		<!--Three -->
			<section id="three">
				<div id="youtube-trailer">
					<h3>Zobacz nas na YouTube</h3><br>
					<iframe width="90%" height="90%" src="https://www.youtube.com/embed/QOUKC2g9hjg" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
				</div>
			</div>
			
			
			
		<!-- Footer -->
			<?php 
				//$path = $_SERVER['DOCUMENT_ROOT'];
				$path = "content/footer.php";
				include_once($path);
			?>



			<div id="myPopup" class="popup">
				<span class="close2"><i class="fas fa-times"></i></span>
				<p id="popup-info"></p>
			
			</div>
			
			
			
			
			
			
			<div id="myModal" class="modal">

				<!-- Modal content -->
				<div class="modal-content">
				
					<span class="close2"><i class="fas fa-times"></i></span>
					
					<div id="top-banner">
						<div class="layer">

							<p class="title"> Zapisz się na liście graczy</p>
							<p class="subtitle"> I dołącz do nas już dziś!</p>
							
							
							<div id="group">      
								<input id="first_name" name="first_name" type="text" maxlength="16" required>
								<span class="highlight"></span>
								<span class="bar"></span>
								<label>Nazwa w grze..</label>
							</div>
								
							
							<input style="display: inline-block;" class="normal-button" onclick="validate()" name="myBtn" id="Btn" type="submit" value="Potwierdź">
							
							<div class="text-xs-center">
								<div class="g-recaptcha" data-sitekey="6LeCDMgUAAAAAG8M-vyC7cAjyPPJv7NjI15rwkm_"></div>
							</div>
								
						</div>
						
					</div>
					
				</div>

			</div>
			
			
			
		<script>

			function validate() {
				if (!document.getElementById('first_name').value) {
					showPopup("<i class=\"fas fa-exclamation-circle\"></i> Najpierw wpisz wybraną nazwę w polu tekstowym!", 0);
					return false;
				} 
				
				if (grecaptcha && grecaptcha.getResponse().length <= 0) {
					showPopup("<i class=\"fas fa-exclamation-circle\"></i> Udowodnij, że nie jesteś robotem!", 0);
					return false;
				}
				
				
				if (grecaptcha && grecaptcha.getResponse().length > 0) {
					ajax_post();
					return true;
				}
				
				//grecaptcha.execute();
			}
		
		
		
		
			function copyServerIp(text) {
				var customText = document.createElement("textarea");
				
				
				//create fake text to copy
				document.body.appendChild(customText);
				customText.value = text;
				customText.select();
				
				//check if coppied successfully
				try {
					var successful = document.execCommand('copy');
					var msg = successful ? '<i class="fas fa-check-circle"></i> Pomyślnie skopiowano do schowka.' : '<i class="fas fa-exclamation-circle"></i> Nie udało się skopiować do schowka.';
					
				} catch (err) {
					var msg = '<i class="fas fa-exclamation-circle"></i> Przeglądarka nie obsługuje tej opcji.';
				}
				document.body.removeChild(customText);
				
				showPopup(msg, 1);
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
			var modal = document.getElementById('myModal');

			// Get the button that opens the modal
			var btn = document.getElementById("offer-vip");

			// Get the <span> element that closes the modal
			var span = document.getElementsByClassName("close2")[1];

			// When the user clicks the button, open the modal 
			//btn.onclick = function() {
				//modal.style.display = "block";
				//document.body.style.overflow = 'hidden';
			//}

			// When the user clicks on <span> (x), close the modal
			span.onclick = function() {
				modal.scrollTo(0, 0);
				modal.style.display = "none";
				//document.body.style.overflow = 'visible';
				document.getElementById("first_name").value = "";
				grecaptcha.reset();
			}
			
			// When the user clicks anywhere outside of the modal, close it
			window.onclick = function(event) {
				if (event.target == modal) {
					modal.scrollTo(0, 0);
					modal.style.display = "none";
					//document.body.style.overflow = 'visible';
					document.getElementById("first_name").value = "";
					grecaptcha.reset();
				}
			}
			
			

			
			
			
			
			

			function ajax_post(){
				// Create our XMLHttpRequest object
				var hr = new XMLHttpRequest();
				// Create some variables we need to send to our PHP file
				var url = "sendUser.php";
				var fn = document.getElementById("first_name").value;
				var vars = "firstname="+fn;
				hr.open("POST", url, true);
				// Set content type header information for sending url encoded variables in the request
				hr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				// Access the onreadystatechange event for the XMLHttpRequest object
				hr.onreadystatechange = function() {
					if(hr.readyState == 4 && hr.status == 200) {
						var return_id = hr.responseText.split("||")[1];
						var return_data = hr.responseText.split("||")[0];
						
						if (return_id == 0) showPopup(return_data, 0);
						if (return_id == 1) showPopup(return_data, 1);
						
					}
				}
				// Send the data to PHP now... and wait for response to update the status div
				hr.send(vars); // Actually execute the request
				grecaptcha.reset();
			}
			
			  

	</script>
		
		
		

	</body>
</html>