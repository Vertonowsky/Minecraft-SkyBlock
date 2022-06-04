package me.vertonowsky.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandThor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("thor")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("vert.*")) {
					if (args.length == 1) {
						if (Bukkit.getPlayer(args[0]) != null) {
							Player t = Bukkit.getPlayer(args[0]);
							World world = t.getWorld();
							Location loc = t.getLocation();
							world.strikeLightning(loc);
							p.sendMessage("§a§lSukces: §7Uderzono piorunem gracza §f" + t.getName() + "§7.");
							return true;
						} else {
							p.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
							return true;
						}
					} else {
						p.sendMessage("§6§lInfo: §7Użyj §e/thor <gracz>§7.");
						return true;
					}
				} else {
					p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
					return true;
				}
			
			} if (sender instanceof ConsoleCommandSender) {
				if (args.length == 1) {
					if ((Bukkit.getPlayer(args[0]) != null)) {
						Player t = Bukkit.getPlayer(args[0]);
						World world = t.getWorld();
						Location loc = t.getLocation();
						world.strikeLightning(loc);
						sender.sendMessage("§a§lSukces: §7Uderzono piorunem gracza §f" + t.getName() + "§7.");
						return true;
					} else {
						sender.sendMessage("§c§lBlad: §7Niewlasciwy nick lub gracz nie jest online.");
						return true;
					}
				} else {
					sender.sendMessage("§6§lInfo: §7Uzyj §e/thor <gracz>§7. ");
					return true;
				}
			}
		}
		return false;
	}

}
