package me.vertonowsky.essentials.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRepair implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("repair")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("vert.*")) {
					if (p.getItemInHand() != null && p.getItemInHand().getType().equals(Material.AIR)) {
						p.sendMessage("§c§lBłąd: §7Nie można naprawić powietrza.");
						return true;
					} else {
						p.getItemInHand().setDurability((short) 0);
						p.sendMessage("§a§lSukces: §7Naprawiłeś swój przedmiot.");
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
