package me.vertonowsky.essentials.listeners;

import me.vertonowsky.main.Main;
import me.vertonowsky.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class OnChat implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		User pUUID = User.get(p.getUniqueId());
		if (!p.hasPermission("vert.chat.always")) {
			if (Main.czatStatus == false) {
				e.setCancelled(true);
				p.sendMessage("§c§lBłąd: §7Czat jest wyłączony!");
				return;
			}
		}
		String rankColor = "§a";
		String messageColor = "§f";

		if (p.hasPermission("vert.chat.admin")) {
			rankColor = "§4";
			messageColor = "§c";
		} else if (p.hasPermission("vert.chat.moderator")) {
			rankColor = "§9";
			messageColor = "§2";
		} else if (p.hasPermission("vert.chat.kidmod")) {
			rankColor = "§3";
			messageColor = "§a";
		} else if (p.hasPermission("vert.chat.supervip") || p.hasPermission("vert.chat.vip")) {
			rankColor = "§6";
			messageColor = "§e";
		}


		if (!p.hasPermission("vert.chat.spam")) {
			if (System.currentTimeMillis() - pUUID.getAntiSpam() <= 5000) {
				e.setCancelled(true);
				long cd = (System.currentTimeMillis() - pUUID.getAntiSpam()) / 1000;
				long cd2 = (5 - cd);
				String sekunda = "sekund";
				if (cd2 > 1 && cd2 < 5) sekunda = "sekundy";
				if (cd2 == 1) sekunda = "sekundę";
				p.sendMessage("§c§lBłąd: §7Na czacie możesz pisać za §c" + cd2 + " §7" + sekunda + "!");
				return;
			}
		}

		if (pUUID.getMuteExpireDate() > System.currentTimeMillis()) {
			e.setCancelled(true);
			p.sendMessage("§c§lBłąd: §7Zostałeś wyciszony!");
			return;
		}

		String message = e.getMessage();

		int spamCount = 0;
		for (int i = 0; i < message.length(); i++){
			if (spamCount >= 4) {
				message = message.toLowerCase();
				break;
			}
			char c = message.charAt(i);
			if (!Character.isSpaceChar(c)) {
				if (Character.isUpperCase(c)) {
					spamCount++;
				} else {
					spamCount = 0;
				}
			}
		}


		if (message.length() > 30) {
			String message2 = message.substring(0, message.length() - 4);
			if (pUUID.getPreviousMsg().contains(message2)) {
				e.setCancelled(true);
				p.sendMessage(getSpamInfo(p));
				return;
			}
		}
		if (message.length() <= 30) {
			if (pUUID.getPreviousMsg().equals(message)) {
				e.setCancelled(true);
				p.sendMessage(getSpamInfo(p));
				return;
			}
		}


		for (String s : getVulgarisms()) {
			if (message.contains(s)) {
				p.sendMessage(getVulgarismInfo(p));
				e.setCancelled(true);
				return;
			}
		}


		pUUID.setPreviousMsg(message);
		pUUID.setAntiSpam(System.currentTimeMillis());
		e.setFormat("§7[§6Lvl. §e" + pUUID.getTotalLevel() + "§7] §8-[" + rankColor + pUUID.getRank() + "§8] §7" + p.getName() + ": " + messageColor + message.replaceAll("%", "%%"));
		/*for (Player onlineplayer : Bukkit.getOnlinePlayers()) {
			/*if (pUUID.getCity().equals("") || pUUID.getCity() == null) {
				onlineplayer.sendMessage(" + messageColor + message);
			}

			else {
				onlineplayer.sendMessage("§7[§6Lvl. §e" + pUUID.getTotalLevel() + "§7] §8-[" + rankColor + pUUID.getRank() + "§8][" + rankColor + pUUID.getCity() + "§8] §7" + p.getName() + ": " + messageColor + message);
			}
		}

		 */

	}



	private String getSpamInfo(Player p) {
		Random rand = new Random();
		List<String> list = new ArrayList<>();
		list.add("§8§l>> §cZbyszek: §7Hej! Powtarzasz się!");
		list.add("§8§l>> §cZbyszek: §7Hmm.. sformułuj to w inny sposób!");
		list.add("§8§l>> §cZbyszek: §7Znowu to samo..");
		list.add("§8§l>> §cZbyszek: §7Ty myślisz, że mi się chce sortować ten SPAM?!");

		return list.get(rand.nextInt(list.size()));
	}



	private String getVulgarismInfo(Player p) {
		Random rand = new Random();
		List<String> list = new ArrayList<>();
		list.add("§8§l>> §cZbyszek: §7Wow! Takiego słowa jeszcze nie słyszałem!");
		list.add("§8§l>> §cZbyszek: §7Powinieneś trochę uważać na słownictwo :)");
		list.add("§8§l>> §cZbyszek: §7To, że masz gorszy dzień, nie oznacza że reszta też taki musi mieć!");
		list.add("§8§l>> §cZbyszek: §7!#HJ@1<%!#e, to sobie porozmawialiśmy :)");

		return list.get(rand.nextInt(list.size()));
	}



	private List<String> getVulgarisms() {

		List<String> list = new ArrayList<>(Arrays.asList("chuj","chuja", "chujek", "chuju", "chujem", "chujnia",
				"chujowy", "chujowa", "chujowe", "cipa", "cipę", "cipe", "cipą",
				"cipie", "dojebać","dojebac", "dojebie", "dojebał", "dojebal",
				"dojebała", "dojebala", "dojebałem", "dojebalem", "dojebałam",
				"dojebalam", "dojebię", "dojebie", "dopieprzać", "dopieprzac",
				"dopierdalać", "dopierdalac", "dopierdala", "dopierdalał",
				"dopierdalal", "dopierdalała", "dopierdalala", "dopierdoli",
				"dopierdolił", "dopierdolil", "dopierdolę", "dopierdole", "dopierdoli",
				"dopierdalający", "dopierdalajacy", "dopierdolić", "dopierdolic",
				"dupa", "dupie", "dupą", "dupcia", "dupeczka", "dupy", "dupe", "huj",
				"hujek", "hujnia", "huja", "huje", "hujem", "huju", "jebać", "jebac",
				"jebał", "jebal", "jebie", "jebią", "jebia", "jebak", "jebaka", "jebal",
				"jebał", "jebany", "jebane", "jebanka", "jebanko", "jebankiem",
				"jebanymi", "jebana", "jebanym", "jebanej", "jebaną", "jebana",
				"jebani", "jebanych", "jebanymi", "jebcie", "jebiący", "jebiacy",
				"jebiąca", "jebiaca", "jebiącego", "jebiacego", "jebiącej", "jebiacej",
				"jebia", "jebią", "jebie", "jebię", "jebliwy", "jebnąć", "jebnac",
				"jebnąc", "jebnać", "jebnął", "jebnal", "jebną", "jebna", "jebnęła",
				"jebnela", "jebnie", "jebnij", "jebut", "koorwa", "kórwa", "kurestwo",
				"kurew", "kurewski", "kurewska", "kurewskiej", "kurewską", "kurewska",
				"kurewsko", "kurewstwo", "kurwa", "kurwaa", "kurwami", "kurwą", "kurwe",
				"kurwę", "kurwie", "kurwiska", "kurwo", "kurwy", "kurwach", "kurwami",
				"kurewski", "kurwiarz", "kurwiący", "kurwica", "kurwić", "kurwic",
				"kurwidołek", "kurwik", "kurwiki", "kurwiszcze", "kurwiszon",
				"kurwiszona", "kurwiszonem", "kurwiszony", "kutas", "kutasa", "kutasie",
				"kutasem", "kutasy", "kutasów", "kutasow", "kutasach", "kutasami",
				"matkojebca", "matkojebcy", "matkojebcą", "matkojebca", "matkojebcami",
				"matkojebcach", "nabarłożyć", "najebać", "najebac", "najebał",
				"najebal", "najebała", "najebala", "najebane", "najebany", "najebaną",
				"najebana", "najebie", "najebią", "najebia", "naopierdalać",
				"naopierdalac", "naopierdalał", "naopierdalal", "naopierdalała",
				"naopierdalala", "naopierdalała", "napierdalać", "napierdalac",
				"napierdalający", "napierdalajacy", "napierdolić", "napierdolic",
				"nawpierdalać", "nawpierdalac", "nawpierdalał", "nawpierdalal",
				"nawpierdalała", "nawpierdalala", "obsrywać", "obsrywac", "obsrywający",
				"obsrywajacy", "odpieprzać", "odpieprzac", "odpieprzy", "odpieprzył",
				"odpieprzyl", "odpieprzyła", "odpieprzyla", "odpierdalać",
				"odpierdalac", "odpierdol", "odpierdolił", "odpierdolil",
				"odpierdoliła", "odpierdolila", "odpierdoli", "odpierdalający",
				"odpierdalajacy", "odpierdalająca", "odpierdalajaca", "odpierdolić",
				"odpierdolic", "odpierdoli", "odpierdolił", "opieprzający",
				"opierdalać", "opierdalac", "opierdala", "opierdalający",
				"opierdalajacy", "opierdol", "opierdolić", "opierdolic", "opierdoli",
				"opierdolą", "opierdola", "piczka", "pieprznięty", "pieprzniety",
				"pieprzony", "pierdel", "pierdlu", "pierdolą", "pierdola", "pierdolący",
				"pierdolacy", "pierdoląca", "pierdolaca", "pierdol", "pierdole",
				"pierdolenie", "pierdoleniem", "pierdoleniu", "pierdolę", "pierdolec",
				"pierdola", "pierdolą", "pierdolić", "pierdolicie", "pierdolic",
				"pierdolił", "pierdolil", "pierdoliła", "pierdolila", "pierdoli",
				"pierdolnięty", "pierdolniety", "pierdolisz", "pierdolnąć",
				"pierdolnac", "pierdolnął", "pierdolnal", "pierdolnęła", "pierdolnela",
				"pierdolnie", "pierdolnięty", "pierdolnij", "pierdolnik", "pierdolona",
				"pierdolone", "pierdolony", "pierdołki", "pierdzący", "pierdzieć",
				"pierdziec", "pizda", "pizdy", "pizdo", "pizdą", "pizde", "pizdę", "piździe", "pizdzie",
				"pizdnąć", "pizdnac", "pizdu", "podpierdalać", "podpierdalac",
				"podpierdala", "podpierdalający", "podpierdalajacy", "podpierdolić",
				"podpierdolic", "podpierdoli", "pojeb", "pojeba", "pojebami",
				"pojebani", "pojebanego", "pojebanemu", "pojebani", "pojebany",
				"pojebanych", "pojebanym", "pojebanymi", "pojebem", "pojebać",
				"pojebac", "pojebalo", "popierdala", "popierdalac", "popierdalać",
				"popierdolić", "popierdolic", "popierdoli", "popierdolonego",
				"popierdolonemu", "popierdolonym", "popierdolone", "popierdoleni",
				"popierdolony", "porozpierdalać", "porozpierdala", "porozpierdalac",
				"poruchac", "poruchać", "przejebać", "przejebane", "przejebac",
				"przyjebali", "przepierdalać", "przepierdalac", "przepierdala",
				"przepierdalający", "przepierdalajacy", "przepierdalająca",
				"przepierdalajaca", "przepierdolić", "przepierdolic", "przyjebać",
				"przyjebac", "przyjebie", "przyjebała", "przyjebala", "przyjebał",
				"przyjebal", "przypieprzać", "przypieprzac", "przypieprzający",
				"przypieprzajacy", "przypieprzająca", "przypieprzajaca",
				"przypierdalać", "przypierdalac", "przypierdala", "przypierdoli",
				"przypierdalający", "przypierdalajacy", "przypierdolić",
				"przypierdolic", "qrwa", "rozjebać", "rozjebac", "rozjebie",
				"rozjebała", "rozjebią", "rozpierdalać", "rozpierdalac", "rozpierdala",
				"rozpierdolić", "rozpierdolic", "rozpierdole", "rozpierdoli",
				"rozpierducha", "skurwić", "skurwiel", "skurwiela", "skurwielem",
				"skurwielu", "skurwysyn", "skurwysynów", "skurwysynow", "skurwysyna",
				"skurwysynem", "skurwysynu", "skurwysyny", "skurwysyński",
				"skurwysynski", "skurwysyństwo", "skurwysynstwo", "spieprzać",
				"spieprzac", "spieprza", "spieprzaj", "spieprzajcie", "spieprzają",
				"spieprzaja", "spieprzający", "spieprzajacy", "spieprzająca",
				"spieprzajaca", "spierdalać", "spierdalac", "spierdala", "spierdalał",
				"spierdalała", "spierdalal", "spierdalalcie", "spierdalala",
				"spierdalający", "spierdalajacy", "spierdolić", "spierdolic",
				"spierdoli", "spierdoliła", "spierdoliło", "spierdolą", "spierdola",
				"srać", "srac", "srający", "srajacy", "srając", "srajac", "sraj",
				"sukinsyn", "sukinsyny", "sukinsynom", "sukinsynowi", "sukinsynów",
				"sukinsynow", "śmierdziel", "udupić", "ujebać", "ujebac", "ujebał",
				"ujebal", "ujebana", "ujebany", "ujebie", "ujebała", "ujebala",
				"upierdalać", "upierdalac", "upierdala", "upierdoli", "upierdolić",
				"upierdolic", "upierdoli", "upierdolą", "upierdola", "upierdoleni",
				"wjebać", "wjebac", "wjebie", "wjebią", "wjebia", "wjebiemy",
				"wjebiecie", "wkurwiać", "wkurwiac", "wkurwi", "wkurwia", "wkurwiał",
				"wkurwial", "wkurwiający", "wkurwiajacy", "wkurwiająca", "wkurwiajaca",
				"wkurwić", "wkurwic", "wkurwi", "wkurwiacie", "wkurwiają", "wkurwiali",
				"wkurwią", "wkurwia", "wkurwimy", "wkurwicie", "wkurwiacie", "wkurwić",
				"wkurwic", "wkurwia", "wpierdalać", "wpierdalac", "wpierdalający",
				"wpierdalajacy", "wpierdol", "wpierdolić", "wpierdolic", "wpizdu",
				"wyjebać", "wyjebac", "wyjebali", "wyjebał", "wyjebac", "wyjebała",
				"wyjebały", "wyjebie", "wyjebią", "wyjebia", "wyjebiesz", "wyjebie",
				"wyjebiecie", "wyjebiemy", "wypieprzać", "wypieprzac", "wypieprza",
				"wypieprzał", "wypieprzal", "wypieprzała", "wypieprzala", "wypieprzy",
				"wypieprzyła", "wypieprzyla", "wypieprzył", "wypieprzyl", "wypierdal",
				"wypierdalać", "wypierdalac", "wypierdala", "wypierdalaj",
				"wypierdalał", "wypierdalal", "wypierdalała", "wypierdalala",
				"wypierdalać", "wypierdolić", "wypierdolic", "wypierdoli",
				"wypierdolimy", "wypierdolicie", "wypierdolą", "wypierdola",
				"wypierdolili", "wypierdolił", "wypierdolil", "wypierdoliła",
				"wypierdolila", "zajebać", "zajebac", "zajebie", "zajebią", "zajebia",
				"zajebiał", "zajebial", "zajebała", "zajebiala", "zajebali", "zajebana",
				"zajebani", "zajebane", "zajebany", "zajebanych", "zajebanym",
				"zajebanymi", "zajebiste", "zajebisty", "zajebistych", "zajebista",
				"zajebistym", "zajebistymi", "zajebiście", "zajebiscie", "zapieprzyć",
				"zapieprzyc", "zapieprzy", "zapieprzył", "zapieprzyl", "zapieprzyła",
				"zapieprzyla", "zapieprzą", "zapieprza", "zapieprzy", "zapieprzymy",
				"zapieprzycie", "zapieprzysz", "zapierdala", "zapierdalać",
				"zapierdalac", "zapierdalaja", "zapierdalał", "zapierdalaj",
				"zapierdalajcie", "zapierdalała", "zapierdalala", "zapierdalali",
				"zapierdalający", "zapierdalajacy", "zapierdolić", "zapierdolic",
				"zapierdoli", "zapierdolił", "zapierdolil", "zapierdoliła",
				"zapierdolila", "zapierdolą", "zapierdola", "zapierniczać",
				"zapierniczający", "zasrać", "zasranym", "zasrywać", "zasrywający",
				"zesrywać", "zesrywający", "zjebać", "zjebac", "zjebał", "zjebal",
				"zjebała", "zjebala", "zjebana", "zjebią", "zjebali", "zjeby", "ciota",
				"cioto", "cioty", "gówno", "gównie", "guwno", "guwnie", "cwel", "fuck",
				"dupka", "dupce", "piczko", "shit", "motherfucker"));


		return list;

	}

}
