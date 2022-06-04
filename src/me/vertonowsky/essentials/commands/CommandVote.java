package me.vertonowsky.essentials.commands;

import me.vertonowsky.api.API;
import me.vertonowsky.api.Scoreboard;
import me.vertonowsky.user.User;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;

public class CommandVote implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("vote")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				p.sendMessage("§8§m-----------------------------------------");
				p.sendMessage("");
				API.sendJsonMessage(p, "§8§l>> §8§l[§a§lKliknij§8§l] §2§lZagłosuj na mclist.pl       §e§l+1 dusza", "§8§l[§a§lKliknij§8§l]", ClickEvent.Action.OPEN_URL, "https://www.mclist.pl/serwer/prestigemc.pl");
				API.sendJsonMessage(p, "§8§l>> §8§l[§a§lKliknij§8§l] §2§lZagłosuj na topkamc.pl", "§8§l[§a§lKliknij§8§l]", ClickEvent.Action.OPEN_URL, "https://www.topkamc.pl/server/XHgcZiMk");
				API.sendJsonMessage(p, "§8§l>> §8§l[§a§lKliknij§8§l] §2§lZagłosuj na mclista.pl", "§8§l[§a§lKliknij§8§l]", ClickEvent.Action.OPEN_URL, "https://www.mclista.pl/47715");
				p.sendMessage("");
				API.sendJsonMessage(p, "§8§l>> §8§l[§a§lKliknij§8§l] §6§lZWERYFIKUJ GLOSY", "§8§l[§a§lKliknij§8§l]", ClickEvent.Action.RUN_COMMAND, "/mclist");
				p.sendMessage("");
				p.sendMessage("§8§m-----------------------------------------");
				p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
				return true;



			} else {
				sender.sendMessage("§c§lBłąd: §7Ta komenda nie jest dostepna dla konsoli!");
				return true;
			}
		}

		if (cmd.getName().equalsIgnoreCase("verifiedmclist")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				p.sendMessage("§c§lBład: §7Ta komenda nie jest dostępna dla gracza.");
				return true;
			}
			if (args.length == 1) {
				if (User.get(args[0]) != null) {
					User pUUID = User.get(args[0]);

					boolean voted = true;

					SimpleDateFormat format = new SimpleDateFormat("dd MM");

					String status = (format.format(pUUID.getVote1()));
					String[] date1 = status.split(" ");
					int day1 = Integer.parseInt(date1[0]);
					int month1 = Integer.parseInt(date1[1]);

					String status2 = (format.format(System.currentTimeMillis()));
					String[] date2 = status2.split(" ");
					int day2 = Integer.parseInt(date2[0]);
					int month2 = Integer.parseInt(date2[1]);


					if (day2 > day1) voted = false;
					if (month2 > month1) voted = false;

					if (voted) {
						pUUID.sendMessage("§c§lBłąd: §7Zagłosuj ponownie po godzinie 24.");
						return true;
					}

					pUUID.setSouls(pUUID.getSouls() + 1);
					if (Bukkit.getPlayer(args[0]) != null) {
						Player p = Bukkit.getPlayer(args[0]);
						Scoreboard.setScoreboard(p);
						p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
					}

					pUUID.sendMessage("§8§l>> §8[§a+1 dusz§8]");
					pUUID.setVote1(System.currentTimeMillis());
				} else {
					sender.sendMessage("§c§lBłąd: §7Użytkownik §f" + args[0] + " §cnie istnieje!");
					return true;
				}

			} else {
				sender.sendMessage("§c§lBłąd: §7Sprawdź składnię polecenia!");
				return true;
			}

		}
		return false;
	}

}
