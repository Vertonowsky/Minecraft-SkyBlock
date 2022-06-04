package me.vertonowsky.essentials.commands;

import me.vertonowsky.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandWarp implements CommandExecutor {
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("setwarp")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("vert.*")) {
					if (args.length == 1) {
						if (Main.warps.keySet().contains(args[0])) {
							p.sendMessage("§c§lBłąd: §7Warp o nazwie §f" + args[0] + " §7już istnieje.");
							return true;
						}
						
						Main.warps.put(args[0], p.getLocation());
						p.sendMessage("§a§lSukces: §7Ustawiono warp §f" + args[0] + "§7.");
						return true;
					} else {
						p.sendMessage("§6§lInfo: §7Użyj §e/setwarp <nazwa>§7.");
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
		
		if (cmd.getName().equalsIgnoreCase("warp")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("vert.*")) {
					if (args.length == 0) {
						p.sendMessage("§6§lInfo: §7Dostępne warpy: §f" + Main.warps.keySet().toString().replaceAll("\\[", "").replaceAll("]", "") + "§7.");
						return true;
					}
					if (args.length == 1) {
						if (Main.warps.keySet().contains(args[0])) {
							p.teleport(Main.warps.get(args[0]));
							p.sendMessage("§a§lSukces: §7Przeteleportowano do §f" + args[0] + "§7.");
							return true;
						} else {
							p.sendMessage("§6§lInfo: §7Dostępne warpy: §f" + Main.warps.keySet().toString().replaceAll("\\[", "").replaceAll("]", "") + "§7.");
							return true;
						}
					} else {
						p.sendMessage("§6§lInfo: §7Dostępne warpy: §f" + Main.warps.keySet().toString().replaceAll("\\[", "").replaceAll("]", "") + "§7.");
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
		
		if (cmd.getName().equalsIgnoreCase("delwarp")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("vert.*")) {
					if (args.length == 1) {
						if (Main.warps.keySet().contains(args[0])) {
							Main.warps.remove(args[0]);
							p.sendMessage("§a§lSukces: §7Usunięto warp §f" + args[0] + "§7.");
							return true;
						} else {
							p.sendMessage("§c§lBłąd: §7Nie istnieje warp o nazwie §f" + args[0] + "§7.");
							return true;
						}
					} else {
						p.sendMessage("§6§lInfo: §7Użyj §e/delwarp <nazwa>§7.");
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
