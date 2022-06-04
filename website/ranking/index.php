<!DOCTYPE html>

<html>
	<head>
		<title>Prestigemc.pl | Ranking</title>
		<meta charset="utf-8" />
		<link rel="stylesheet" href="/prestigemc/assets/css/main.css" />
		<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css" rel="stylesheet">
		<!--<meta name="viewport" content="width=device-width, initial-scale=1.0">-->
		<link rel="icon" href="/prestigemc/images/logo.png">
		<meta name="language" content="PL" />
		<meta name="Copyright" content="Wszystkie prawa zastrzezone 2020 Prestigemc" />
		
		
		<script src="/prestigemc/assets/js/jquery.min.js"></script>
		<script src="/prestigemc/assets/js/skel.min.js"></script>
		<script src="/prestigemc/assets/js/util.js"></script>
		<script src="/prestigemc/assets/js/main.js"></script>
		<script src="/prestigemc/assets/jquery/jquery-1.11.3.js"></script>	
		<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		<script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
		
		
		<script type="text/javascript">
		
			var paginator;
			var totalPages;
			var resultsPerPage = 10;
			var ShowingPagesList = 10;

			function createPaginator(totalItems)
			{
				paginator = $(".pagination");

				totalPages = Math.ceil(totalItems/resultsPerPage);
				
				if (totalPages > ShowingPagesList) totalPages = ShowingPagesList;

				//$('<li><a href="#" class="first_link"><</a></li>').appendTo(paginator);
				//$('<li><a href="#" class="prev_link">«</a></li>').appendTo(paginator);

				var pag = 0;
				while(totalPages > pag)
				{
					$('<li><a href="#" class="page_link">'+(pag+1)+'</a></li>').appendTo(paginator);
					pag++;
				}


				if(ShowingPagesList > 1)
				{
					$(".page_link").hide();
					$(".page_link").slice(0,ShowingPagesList).show();
				}

				/*
				$('<li><a href="#" class="next_link">»</a></li>').appendTo(paginator);
				$('<li><a href="#" class="last_link">></a></li>').appendTo(paginator);

				paginator.find(".page_link:first").addClass("active");
				paginator.find(".page_link:first").parents("li").addClass("active");

				paginator.find(".prev_link").hide();
				*/

				paginator.find("li .page_link").click(function()
				{
					$('#statsTable').empty();
					var pageToLoad = $(this).html().valueOf()-1;
					loadPage(pageToLoad);
					return false;
				});

				/*
				paginator.find("li .first_link").click(function()
				{
					$('#statsTable').empty();
					var pageToLoad = 0;
					loadPage(pageToLoad);
					return false;
				});

				paginator.find("li .prev_link").click(function()
				{
					$('#statsTable').empty();
					var pageToLoad = parseInt(paginator.data("pag")) -1;
					loadPage(pageToLoad);
					return false;
				});

				paginator.find("li .next_link").click(function()
				{
					$('#statsTable').empty();
					var pageToLoad = parseInt(paginator.data("pag")) +1;
					loadPage(pageToLoad);
					return false;
				});

				paginator.find("li .last_link").click(function()
				{
					$('#statsTable').empty();
					var pageToLoad = totalPages -1;
					loadPage(pageToLoad);
					return false;
				});
				*/

				loadPage(0);




			}
		
		

			function loadPage(page)
			{
				var desde = page * resultsPerPage;

				$.ajax({
					data:{"param":"records","limit":resultsPerPage,"offset":desde},
					type:"GET",
					dataType:"json",
					url:"loadData.php"
				}).done(function(data,textStatus,jqXHR){

					var lista = data.lista;



					$.each(lista, function(ind, elem){

						$("<tr onclick=" + "openDocument('" + elem.name + "')" + ">"+
							"<td>"+elem.id+"</td>"+
							"<td>"+elem.name+"</td>"+
							"<td>"+elem.rank+"</td>"+
							"<td>"+elem.level_total+"</td>"+
							"<td>"+elem.total_time_online+"</td>"+
							"</a></tr>").appendTo($("#statsTable"));


					});			


				}).fail(function(jqXHR,textStatus,textError){
					alert("Wystąpił błąd podczas żądania.".textError);

				});

				/*
				if(page >= 1)
				{
					paginator.find(".prev_link").show();

				}
				else
				{
					paginator.find(".prev_link").hide();
				}


				if(page <(totalPages- ShowingPagesList))
				{
					paginator.find(".next_link").show();
				}else
				{
					paginator.find(".next_link").hide();
				}
				*/

				paginator.data("pag",page);

				if(ShowingPagesList>1)
				{
					$(".page_link").hide();
					if(page < (totalPages- ShowingPagesList))
					{
						$(".page_link").slice(page,ShowingPagesList + page).show();
					}
					else{
						if(totalPages > ShowingPagesList)
							$(".page_link").slice(totalPages- ShowingPagesList).show();
						else
							$(".page_link").slice(0).show();

					}
				}

				paginator.children().removeClass("active");
				paginator.children().eq(page).addClass("active");
				
				//                         /\  "-2"


			}
		

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
			function createPaginator2(totalItems)
			{
			
				paginator = $(".pagination");

				totalPages = Math.ceil(totalItems/resultsPerPage);

				//$('<li><a href="#" class="first_link"><</a></li>').appendTo(paginator);
				//$('<li><a href="#" class="prev_link">«</a></li>').appendTo(paginator);

				var pag = 0;
				while(totalPages > pag)
				{
					$('<li><a href="#" class="page_link">'+(pag+1)+'</a></li>').appendTo(paginator);
					pag++;
				}


				if(ShowingPagesList > 1)
				{
					$(".page_link").hide();
					$(".page_link").slice(0,ShowingPagesList).show();
				}

				
				/*$('<li><a href="#" class="next_link">»</a></li>').appendTo(paginator);
				$('<li><a href="#" class="last_link">></a></li>').appendTo(paginator);

				paginator.find(".page_link:first").addClass("active");
				paginator.find(".page_link:first").parents("li").addClass("active");

				paginator.find(".prev_link").hide();
				*/

				paginator.find("li .page_link").click(function()
				{
					$('#statsTable').empty();
					var pageToLoad =$(this).html().valueOf()-1;
					loadPage2(pageToLoad);
					return false;
				});
				
				/*
				paginator.find("li .first_link").click(function()
				{
					$('#statsTable').empty();
					var pageToLoad =0;
					loadPage2(pageToLoad);
					return false;
				});

				paginator.find("li .prev_link").click(function()
				{
					$('#statsTable').empty();
					var pageToLoad =parseInt(paginator.data("pag")) -1;
					loadPage2(pageToLoad);
					return false;
				});

				paginator.find("li .next_link").click(function()
				{
					$('#statsTable').empty();
					var pageToLoad =parseInt(paginator.data("pag")) +1;
					loadPage2(pageToLoad);
					return false;
				});

				paginator.find("li .last_link").click(function()
				{
					$('#statsTable').empty();
					var pageToLoad =totalPages -1;
					loadPage2(pageToLoad);
					return false;
				});
				*/

				loadPage2(0);




			}
			
			
			
			function loadPage2(pagina)
			{
				var desde = pagina * resultsPerPage;
				
				var fn = document.getElementById("look").value;

				$.ajax({
					data:{"param":"records","limit":resultsPerPage,"offset":desde,"cos":fn},
					type:"GET",
					dataType:"json",
					url:"findPlayer.php"
				}).done(function(data,textStatus,jqXHR){

					var lista = data.lista;

					$("#statsTable").html("");

					$.each(lista, function(ind, elem){

						$("<tr onclick=" + "openDocument('" + elem.name + "')" + ">"+
							"<td>"+elem.id+"</td>"+
							"<td>"+elem.name+"</td>"+
							"<td>"+elem.rank+"</td>"+
							"<td>"+elem.level_total+"</td>"+
							"<td>"+elem.total_time_online+"</td>"+
							"</a></tr>").appendTo($("#statsTable"));


					});			


				}).fail(function(jqXHR,textStatus,textError){
					alert("Wystąpił błąd podczas żądania.".textError);

				});

				/*
				if(pagina >= 1)
				{
					paginator.find(".prev_link").show();

				}
				else
				{
					paginator.find(".prev_link").hide();
				}


				if(pagina <(totalPages- ShowingPagesList))
				{
					paginator.find(".next_link").show();
				}else
				{
					paginator.find(".next_link").hide();
				}
				*/

				paginator.data("pag",pagina);

				if(ShowingPagesList>1)
				{
					$(".page_link").hide();
					if(pagina < (totalPages- ShowingPagesList))
					{
						$(".page_link").slice(pagina,ShowingPagesList + pagina).show();
					}
					else{
						if(totalPages > ShowingPagesList)
							$(".page_link").slice(totalPages- ShowingPagesList).show();
						else
							$(".page_link").slice(0).show();

					}
				}

				paginator.children().removeClass("active");
				paginator.children().eq(pagina).addClass("active");

				//                           /\  "+2"

			}
			

			
			function searchFunc()
			{

				var fn = document.getElementById("look").value;
				
				$('#statsTable').empty();
				$(".pagination").empty();

			
				$.ajax({

					data:{"param":"count","cos":fn},
					type:"GET",
					dataType:"json",
					url:"findPlayer.php"
				}).done(function(data,textStatus,jqXHR){
					var total = data.total;

					if (total == 0) {
						if (fn == "") {
							createPaginator2(total);
							$('#noUsers').empty();
						} else {
							createPaginator2(1);
							$('#noUsers').text("Nie znaleziono takiego gracza!");
							$("#noUsers").css("color", "#FF5252");
							$("#noUsers").css("text-align", "center"); 
							$("#noUsers").css("margin-top", "20px"); 
							$("#noUsers").css("font-size", "20px"); 
						}
					} else {
						createPaginator2(total);
						$('#noUsers').empty();
					}

					


				}).fail(function(jqXHR,textStatus,textError){
					alert("Wystąpił błąd podczas żądania.".textError);

				});



			}

			
			$(function()
			{
				
				$.ajax({

					data:{"param":"count"},
					type:"GET",
					dataType:"json",
					url:"loadData.php"
				}).done(function(data,textStatus,jqXHR){
					var total = data.total;

					createPaginator(total);


				}).fail(function(jqXHR,textStatus,textError){
					alert("Wystąpił błąd podczas żądania.".textError);

				});



			});

		</script>
	</head>
	<body>

		<!-- Header -->
			 <header id="header">
				<nav id="nav">
					<a href="/prestigemc/">Strona główna</a>
					<a href="/prestigemc/shop">Sklep</a>
					<a class="active" href="/prestigemc/ranking">Ranking</a>
				</nav>
			</header>
			<a href="#menu" class="navPanelToggle"><i class="fa fa-bars"></i></a>

		<!-- Banner -->
			<section id="banner3">
				<div class="shadow">
					
				</div>	
			</section>

		<!-- Stats -->
			<section id="stats">
				<div class="inner">

				<div id="top-menu">
				
					<label>Ranking</label>
					
					<div id="search">
						<div id="group-dark" style="margin-right: 20px; margin-bottom: 0;">      
							<input type="text" id="look" class="look" name="look" maxlength="16" required>
							<span class="highlight"></span>
							<span class="bar"></span>
							<label>Szukaj..</label>
						</div>
						
						<input style="display: inline-block; margin-top: 0px;" class="light_blue_button" type="submit" onclick="searchFunc()" value="Szukaj">
					</div>
					
				</div>
					<table>
						<thead>
							<tr>
								<th>#</th>
								<th>Nick</th>
								<th>Ranga</th>
								<th>Poziom</th>
								<th>Czas online</th>
							</tr>
						</thead>
						<tbody id="statsTable">
						
						</tbody>
					</table>
					<div id="noUsers"></div>
					<div class="all-pagination">
						<ul class="pagination" id="paginator"></ul>
					</div>
				</div>
			</section>
			
			
			
			
			
			
			
			
			
		<!--Modal user -->
			<div id="modalUser" class="modal">

				<!-- Modal content -->
				<div class="modal-content-profile">
				
					<span class="close"><i class="fas fa-times"></i></span>
					
					
					<div class="modal-content-profile-top-left">
						<div class="banner">
							<img src="https://visage.surgeplay.com/bust/180/37f7fd25632946a4a823d1cd239ac1de" />
						</div>
						<label id="profileName"></label>
						
						
						<ul class ="profile-left-nav">
							<li class="nav-link active" onclick="openNav('Profil')"> <i class="fas fa-user-circle"></i> Profil </li>
							<!--<li class="nav-link" onclick="openNav('Zdolności')"> <i class="fas fa-award"></i> Zdolności </li>
							<li class="nav-link" onclick="openNav('Aktywność')"> <i style="margin-left: -4px;" class="fas fa-star"></i> Aktywność </li>
							-->
						</ul>
						
					
						
					</div><div class="modal-content-profile-top-right">
						<div class="banner">
						</div>
						
						<div id="Profil" class="profile-right-panel nav">
						
							<div class="box small-box">
								<h1 class="text-title">Zabójstwa</h1>
								<h2 class="text-input" id="kills"></h2>
								
								<h1 class="text-title">Śmierci</h1>
								<h2 class="text-input" id="deaths"></h2>
								
								<h1 class="text-title">KD</h1>
								<div class="progress"><div id="kda" class="bar"></div></div><p style="text-align: center;" id="kda-label"></p>
							</div>
							
							
							<div class="box large-box">
								<div class="progress-pie-chart">
									<div class="ppc-progress">
										<div class="ppc-progress-fill"></div>
									</div>
									<div class="ppc-percents">
										<span class="progress-circle-text"></span>
									</div>
									
								</div><div class="progress-pie-chart">
									<div class="ppc-progress">
										<div class="ppc-progress-fill"></div>
									</div>
									<div class="ppc-percents">
										<span class="progress-circle-text"></span>
									</div>
									
								</div><div class="progress-pie-chart">
									<div class="ppc-progress">
										<div class="ppc-progress-fill"></div>
									</div>
									<div class="ppc-percents">
										<span class="progress-circle-text"></span>
									</div>
									
								</div><div class="progress-pie-chart">
									<div class="ppc-progress">
										<div class="ppc-progress-fill"></div>
									</div>
									<div class="ppc-percents">
										<span class="progress-circle-text"></span>
									</div>
									
								</div>
							</div>

						
						
						</div>
						
						<!--<div id="Zdolności" class="profile-right-panel nav" style="display:none">
						
						</div>

						<div id="Aktywność" class="profile-right-panel nav" style="display:none">
						
						</div>
						
						<!--<div class="progress">
							<div class="bar">43%</div>
						</div>
						-->

					</div>
					
				</div>

			</div>
				

		<!-- Footer -->
			<?php 
				$path = $_SERVER['DOCUMENT_ROOT'];
				$path .= "/content/footer.php";
				include_once($path);
			?>
			
			
			
			
		<script>
	
			// Get the modal
			var modal_user = document.getElementById('modalUser');

			// Get the <span> element that closes the modal
			var span = document.getElementsByClassName("close")[0];

			// When the user clicks on <span> (x), close the modal
			span.onclick = function() {
				modal_user.scrollTo(0, 0);
				modal_user.style.display = "none";
				clearCircles();
			}
			
			// When the user clicks anywhere outside of the modal, close it
			window.onclick = function(event) {
				if (event.target == modal_user) {
					modal_user.scrollTo(0, 0);
					modal_user.style.display = "none";
					clearCircles();
				}
			}
			
			
			function clearCircles() {
				var circles = document.getElementsByClassName("progress-pie-chart");
				for (var i = 0; i < circles.length; i++) {
					document.getElementsByClassName("progress-pie-chart")[i].classList.remove('gt-50');
				}
				
			}
				
			
			
			
			
			
			function openDocument(name) {
				/*
					Zmienić wygląd URL
				*/
				
				modal_user.style.display = "block";

			
				$.ajax({
					data:{"user":name},
					type:"GET",
					dataType:"json",
					url:"/ranking/user/index.php"
				}).done(function(data,textStatus,jqXHR){
					var total = data.total;
					var lista = data.lista;
					

					if (total == 0) {
						
						
					} else {
						document.getElementById("profileName").innerHTML = lista.name;
						document.getElementById("kills").innerHTML = lista.kills;
						document.getElementById("deaths").innerHTML = lista.deaths;
						
						kda = lista.kills / lista.deaths;
						kda = kda.toFixed(3);
						
						if (kda >= 1) {
							document.getElementById("kda").style.width = '100%';
						}
						if (kda < 1) {
							kda2 = kda * 100;
							document.getElementById("kda").style.width = kda2 + '%';
						}
						
						document.getElementById("kda-label").innerHTML = kda;
						
						var expJobs = lista.experience_jobs.split('~');
						var maxExpJobs = lista.max_experience_jobs.split('~');


						var circles = document.getElementsByClassName("progress-pie-chart");
						for (var i = 0; i < circles.length; i++) {
							var ranga = "Nieznana";
							var procent = 0;
							var exp = 0;
							var expJobsSplit2 = 0;
							var maxExpJobsSplit2 = 0;
							
							if (expJobs[i] != null) {
								expJobsSplit2 = expJobs[i].split('=');
								maxExpJobsSplit2 = maxExpJobs[i].split('=');
								procent = (expJobsSplit2[1] / maxExpJobsSplit2[1]) *100;
								exp = expJobsSplit2[1];
								if (expJobsSplit2[0] === ("MINER")) ranga = "Górnik";
								if (expJobsSplit2[0] === ("LUMBERJACK")) ranga = "Drwal";
								if (expJobsSplit2[0] === ("ANGLER")) ranga = "Wędkarz";
								if (expJobsSplit2[0] === ("WARRIOR")) ranga = "Wojownik";
							}
							
							
							
							
							var procent = parseInt(procent);
							var deg = 360*procent/100;
							
							if (procent > 50) {
								document.getElementsByClassName("progress-pie-chart")[i].classList.add('gt-50');
							}
							
							document.getElementsByClassName("ppc-progress-fill")[i].style.transform = 'rotate('+ deg +'deg)';
							document.getElementsByClassName("progress-circle-text")[i].innerHTML = ranga + " " + procent + '% <br>' + exp + ' exp';
						}
						
						
					}

					


				}).fail(function(jqXHR,textStatus,textError){
					alert("Wystąpił błąd podczas żądania.".textError);
				});
			}
			
			
			function openNav(param) {
				var i;
				var x = document.getElementsByClassName("nav");
				for (i = 0; i < x.length; i++) {
					x[i].style.display = "none";
				}
				
				navlinks = document.getElementsByClassName("nav-link");
				for (i = 0; i < navlinks.length; i++) {
					navlinks[i].className = navlinks[i].className.replace("active", "");
				}
				event.currentTarget.className += " active";
				
				
				document.getElementById(param).style.display = "block";
			}
			
			
		</script>
	</body>
</html>
