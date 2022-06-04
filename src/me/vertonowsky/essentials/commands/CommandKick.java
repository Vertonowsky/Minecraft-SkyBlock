package me.vertonowsky.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandKick implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("kick")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("vert.kick")) {
					if (args.length >= 2) {
						if (Bukkit.getPlayer(args[0]) != null) {
							Player t = Bukkit.getPlayer(args[0]);
							StringBuilder message = new StringBuilder();
							for (int i = 1; i < args.length; i++) {
								message.append(args[i] + " ");
							}
							t.kickPlayer("§7Zostałeś wyrzucony z serwera przez gracza §e" + p.getName() + "§7.\n\n §aPowód: §b" + message.toString());
							Bukkit.broadcastMessage("§a[§7§m----------------------------------§a]");
							Bukkit.broadcastMessage("§8➢ §cWyrzucono gracza: §e" + t.getName());
							Bukkit.broadcastMessage("§8➢ §cPowód: §e" + message.toString());
							Bukkit.broadcastMessage("§8➢ §cWyrzucił: §e" + p.getName());
							Bukkit.broadcastMessage("§a[§7§m----------------------------------§a]");

							return true;
						} else {
							p.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
							return true;
						}
					} else {
						p.sendMessage("§6§lInfo: §7Użyj §e/kick <gracz> <powód>§7.");
						return true;
					}
				} else {
					p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
					return true;
				}
			} 
			if (sender instanceof ConsoleCommandSender) {
				if (args.length >= 2) {
					if ((Bukkit.getPlayer(args[0]) != null)) {
						Player t = Bukkit.getPlayer(args[0]);
						StringBuilder message = new StringBuilder();
						for (int i = 1; i < args.length; i++) {
							message.append(args[i] + " ");
						}
						t.kickPlayer("§7Zostałeś wyrzucony z serwera przez §eAdministratora §7.\n\n §aPowód: §b" + message.toString());
						Bukkit.broadcastMessage("§c[§4====================================§c]");
						Bukkit.broadcastMessage("§8➢ §cWyrzucono gracza: §e" + t.getName());
						Bukkit.broadcastMessage("§8➢ §cPowód: §e" + message.toString());
						Bukkit.broadcastMessage("§8➢ §cWyrzucił: §e" + "Administrator");
						Bukkit.broadcastMessage("§c[§4====================================§c]");
						return true;
					} else {
						sender.sendMessage("§c§lBlad: §7Niewlasciwy nick lub gracz nie jest online.");
						return true;
					}
				} else {
					sender.sendMessage("§6§lInfo: §7Uzyj §e/kick <gracz> <powod>§7.");
					return true;
				}
			}
		}

		if (cmd.getName().equalsIgnoreCase("kickall")) {
			if (sender instanceof ConsoleCommandSender) {
				if (args.length >= 1) {
					StringBuilder message = new StringBuilder();
					for (int i = 0; i < args.length; i++) {
						message.append(args[i] + " ");
					}


					for (Player p : Bukkit.getOnlinePlayers()) {
						p.kickPlayer("§4" + message.toString());
					}
				} else {
					sender.sendMessage("§6§lInfo: §7Uzyj §e/kick <gracz> <powod>§7.");
					return true;
				}
			}
		}
		return false;
	}
}
