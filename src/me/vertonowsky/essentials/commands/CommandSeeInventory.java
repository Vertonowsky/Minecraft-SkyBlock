package me.vertonowsky.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSeeInventory implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("invsee")) {
			Player p = (Player) sender;
			if (p instanceof Player) {
				if (p.hasPermission("vert.*")) {
					if (args.length == 1) {
						if (Bukkit.getPlayer(args[0]) != null) {
							Player t = Bukkit.getPlayer(args[0]);
							p.openInventory(t.getInventory());
							p.sendMessage("§a§lSukces: §7Otwarto ekwipunek gracza §f" + t.getName() + "§7.");
							return true;
						} else {
							p.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
							return true;
						}
					} else {
						p.sendMessage("§6§lInfo: §7Użyj §e/invsee <gracz>§7.");
						return true;
					}
				} else {
					p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
					return true;
				}
			} else {
				sender.sendMessage("§c§lBlad: §7Ta komenda nie jest dostepna dla konsoli.");
				return true;
			}
		}
		if (cmd.getName().equalsIgnoreCase("endersee")) {
			if ((sender instanceof Player)) {
				Player p = (Player) sender;
				if (p.hasPermission("vert.*")) {
					if (args.length == 1) {
						if ((Bukkit.getPlayer(args[0]) != null)) {
							Player t = Bukkit.getPlayer(args[0]);
							p.openInventory(t.getEnderChest());
							p.sendMessage("§a§lSukces: §7Otwarto enderchest gracza §f" + t.getName() + "§7.");
							return true;
						} else {
							p.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
							return true;
						}
					} else {
						p.sendMessage("§6§lInfo: §7Użyj §e/endersee <gracz>§7.");
						return true;
					}
				} else {
					p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
					return true;
				}
			} else {
				sender.sendMessage("§c§lBlad: §7Ta komenda nie jest dostepna dla konsoli.");
				return true;
			}
		}
		return false;
	}

}
