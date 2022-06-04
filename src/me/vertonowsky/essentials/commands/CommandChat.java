package me.vertonowsky.essentials.commands;

import me.vertonowsky.api.API;
import me.vertonowsky.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandChat implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("chat")) {
			if (args.length == 1) {
				String who = "Administrator";
				if (sender instanceof Player) {
					if (!sender.hasPermission("vert.chat")) {
						sender.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
						return true;
					}
					who = sender.getName();
				}

				if (args[0].equalsIgnoreCase("on")) {
					if (!Main.czatStatus) {
						Main.czatStatus = true;
						sendChatInfo("włączony", who);
						return true;
					} else {
						sender.sendMessage("§c§lBłąd: §7Czat jest już §fwłączony§7.");
						return true;
					}
				}
				if (args[0].equalsIgnoreCase("off")) {
					if (Main.czatStatus) {
						Main.czatStatus = false;
						sendChatInfo("wyłączony", who);
						return true;
					} else {
						sender.sendMessage("§c§lBłąd: §7Czat jest już §fwyłączony§7.");
						return true;
					}
				}
				if (args[0].equalsIgnoreCase("clear")) {
					for (int x = 0; x < 100; x++) {
						API.sendBroadcastMessage("");
					}
					sendChatInfo("wyczyszczony", who);
					return true;
				}
				sender.sendMessage("§6§lInfo: §7Użyj §e/czat <on/off/clear>§7.");
				return true;
			}
		}
		return false;
	}




	private void sendChatInfo(String type, String who) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("§8§m--------------------------------------------------");
		list.add("                             §fInformacje");
		list.add("");
		list.add("");
		list.add("");
		list.add("    §bCzat został §f" + type + " §bprzez gracza: §f" + who);
		list.add("");
		list.add("");
		list.add("");
		list.add("§8§m--------------------------------------------------");
		for (String s : list) {
			API.sendBroadcastMessage(s);
		}

	}

}
