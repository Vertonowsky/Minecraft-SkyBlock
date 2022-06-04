package me.vertonowsky.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandBroadcast implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("broadcast")) {
			if ((sender instanceof ConsoleCommandSender)) {
				if (args.length == 0) {
					sender.sendMessage("§c§lBlad: §7Uzyj §e/broadcast <wiadomosc>§7.");
					return true;
				}
				if (args.length >= 1) {
					StringBuilder message = new StringBuilder();
					for (int i = 0; i < args.length; i++) {
						message.append(args[i] + " ");
					}
					Bukkit.broadcastMessage("§4" + message.toString());
					return true;
				} else {
					sender.sendMessage("§c§lBlad: §7Uzyj §e/broadcast <wiadomosc>§7.");
					return true;
				}
			}
			if ((sender instanceof Player)) {
				Player p = (Player) sender;
				if (p.hasPermission("vert.broadcast")) {
					if (args.length == 0) {
						p.sendMessage("§c§lBłąd: §7Użyj §e/broadcast <wiadomość>§7.");
						return true;
					}
					if (args.length >= 1) {
						StringBuilder message = new StringBuilder();
						for (int i = 0; i < args.length; i++) {
							message.append(args[i] + " ");
						}
						Bukkit.broadcastMessage("§4[Serwer] " + message.toString());
						return true;
					} else {
						p.sendMessage("§c§lBłąd: §7Użyj §e/broadcast <wiadomość>§7.");
						return true;
					}
				} else {
					p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
					return true;
				}
			}
		}
		return false;
	}
}
