package me.vertonowsky.essentials.commands;

import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFly implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("fly")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				User pUUID = User.get(p.getName());
				if (args.length == 1) {
					if (p.hasPermission("vert.*")) {
						if (Bukkit.getPlayer(args[0]) != null) {
							Player t = (Player) Bukkit.getPlayer(args[0]);
							if (pUUID.isPvp()) {
								p.sendMessage("§c§lBłąd: §7Komenda niedostępna podczas PVP.");
								return true;
							}
							if (t.getAllowFlight()) {
								t.setAllowFlight(false);
								p.sendMessage("§a§lSukces: §7Wyłączono latanie dla gracza §f" + t.getName() + "§7.");
								return true;
							}
							else if (!t.getAllowFlight()) {
								t.setAllowFlight(true);
								p.sendMessage("§a§lSukces: §7Włączono latanie dla gracza §f" + t.getName() + "§7.");
								return true;
							}
						} else {
							p.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
							return true;
						}
					} else {
						p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
						return true;
					}
				}
				if (args.length == 0) {
					if (p.hasPermission("vert.fly")) {
						if (pUUID.isPvp()) {
							p.sendMessage("§c§lBłąd: §7Komenda niedostępna podczas PVP.");
							return true;
						}
						if (p.getAllowFlight()) {
							p.setAllowFlight(false);
							p.sendMessage("§a§lSukces: §7Wyłączono latanie.");
							return true;
						}
						else if (!p.getAllowFlight()) {
							p.setAllowFlight(true);
							p.sendMessage("§a§lSukces: §7Włączono latanie.");
							return true;
						}
					} else {
						p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
						return true;
					}
				}
				p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
				return true;
			} else {
				sender.sendMessage("§c§lBlad: §7Ta komenda nie jest dostepna dla konsoli.");
				return true;
			}
		}
		return false;
	}
}