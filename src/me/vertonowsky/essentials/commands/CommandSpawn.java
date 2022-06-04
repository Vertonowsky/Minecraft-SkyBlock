package me.vertonowsky.essentials.commands;

import me.vertonowsky.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandSpawn implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("setspawn")) {
			Player p = (Player) sender;
			if (sender instanceof Player) {
				if (p.hasPermission("vert.*")) {
					Main.spawn = p.getLocation();
					p.sendMessage("§a§lSukces: §7Ustawiono punkt spawnu.");
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
		if (cmd.getName().equalsIgnoreCase("spawn")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (Main.spawn == null) {
					p.sendMessage("§c§lBłąd: §7Spawn nie został ustawiony. Zgłoś się do administracji.");
					return true;
				} else {
					p.teleport(Main.spawn);
					p.sendMessage("§6§lInfo: §7Teleportacja..");
					return true;
				}
			} else {
				sender.sendMessage("§c§lBlad: §7Ta komenda nie jest dostepna dla konsoli.");
				return true;
			}
		}

		if (cmd.getName().equalsIgnoreCase("skup")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (Main.warps.get("skup") != null) {
					p.sendMessage("§6§lInfo: §7Teleportacja..");
					p.teleport(Main.warps.get("skup"));
					return true;
				} else {
					p.sendMessage("§c§lBlad: §7Wystąpił problem! Zgłoś się do administracji!");
					return true;
				}
			} else {
				sender.sendMessage("§c§lBlad: §7Ta komenda nie jest dostepna dla konsoli.");
				return true;
			}
		}

		if (cmd.getName().equalsIgnoreCase("sklep")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (Main.warps.get("sklep") != null) {
					p.sendMessage("§6§lInfo: §7Teleportacja..");
					p.teleport(Main.warps.get("sklep"));
					return true;
				} else {
					p.sendMessage("§c§lBlad: §7Wystąpił problem! Zgłoś się do administracji!");
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
