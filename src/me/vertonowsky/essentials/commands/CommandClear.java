package me.vertonowsky.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandClear implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("clear")) {
			if (sender instanceof BlockCommandSender) {
				if (args[0] != null) {
					if (Bukkit.getPlayer(args[0]) != null) {
						Player t = (Player) Bukkit.getPlayer(args[0]);
						t.getInventory().clear();
						t.getInventory().setArmorContents(null);
						Bukkit.broadcastMessage("§a§lSukces: §7Wyczyszczono ekwipunek gracza §f" + t.getName() + "§7.");
						return true;
					}
				}
			}
			
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("vert.*")) {
					if (args.length == 0) {
						p.getInventory().clear();
						p.sendMessage("§a§lSukces: §7Twój ekwipunek został wyczyszczony!");
						return true;
					}
					if (args.length == 1) {
						if (Bukkit.getPlayer(args[0]) != null) {
							Player t = (Player) Bukkit.getPlayer(args[0]);
							t.getInventory().clear();
							t.getInventory().setArmorContents(null);
							p.sendMessage("§a§lSukces: §7Wyczyszczono ekwipunek gracza §f" + t.getName() + "§7.");
							if (t != p) {
								t.sendMessage("§6§lInfo: §7Twój ekwipunek został wyczyszczony!");
							}
							return true;
						}
					} else {
						p.sendMessage("§6§lInfo: §7Użyj §e/clear <gracz>§7.");
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