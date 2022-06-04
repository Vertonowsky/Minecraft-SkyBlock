package me.vertonowsky.essentials.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTime implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("day")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				World world = p.getWorld();
				if (p.hasPermission("vert.*")) {
					world.setTime(1000);
					p.sendMessage("§a§lSukces: §7Ustawiono §fdzień§7.");
					return true;
				} else {
					p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
					return true;
				}
			} else {
				sender.sendMessage("§c§lBlad: §7Ta komenda nie jest dostepna dla konsoli.");
				return true;
			}
		}
		if (cmd.getName().equalsIgnoreCase("night")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				World world = p.getWorld();
				if (p.hasPermission("vert.*")) {
					world.setTime(13000);
					p.sendMessage("§a§lSukces: §7Ustawiono §fnoc§7.");
					return true;
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
