package me.vertonowsky.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandVanish implements CommandExecutor {

	public static List<UUID> vanish = new ArrayList<UUID>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("vanish")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("vert.vanish")) {
					if (args.length == 1) {
						if (Bukkit.getPlayer(args[0]) != null) {
							Player t = Bukkit.getPlayer(args[0]);
							if (vanish.contains(t.getUniqueId())) {
								for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
									pl.showPlayer(t);
								}
								vanish.remove(t.getUniqueId());
								t.sendMessage("§a§lSukces: §7Wyłączono vanish przez §f" + p.getName() + "§7.");
								if (p != t) {
									p.sendMessage("§a§lSukces: §7Wyłączono vanish dla §f" + t.getName() + "§7.");
								}
								return true;
							}
							if (!vanish.contains(t.getUniqueId())) {
								for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
									pl.hidePlayer(t);
								}
								vanish.add(t.getUniqueId());
								t.sendMessage("§a§lSukces: §7Włączono vanish przez §f" + p.getName() + "§7.");
								if (p != t) {
									p.sendMessage("§a§lSukces: §7Włączono vanish dla §f" + t.getName() + "§7.");
								}
								return true;
							}
						} else {
							p.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
							return true;
						}
					}
					if (args.length == 0) {
						if (vanish.contains(p.getUniqueId())) {
							for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
								pl.showPlayer(p);
							}
							vanish.remove(p.getUniqueId());
							p.sendMessage("§a§lSukces: §7Wyłączono vanish.");
							return true;
						}
						if (!vanish.contains(p.getUniqueId())) {
							for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
								pl.hidePlayer(p);
							}
							vanish.add(p.getUniqueId());
							p.sendMessage("§a§lSukces: §7Włączono vanish.");
							return true;
						}
					} else {
						p.sendMessage("§6§lInfo: §7Użyj §e/vanish <gracz>§7.");
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
