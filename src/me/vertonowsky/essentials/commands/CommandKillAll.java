package me.vertonowsky.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;

public class CommandKillAll implements CommandExecutor {
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("killall")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("vert.*")) {
					Integer i = 0;
					for (Entity entity : Bukkit.getWorld(p.getWorld().getName()).getEntities()) {
						if (!(entity instanceof Player || entity instanceof Item || entity instanceof ItemFrame)) {
							entity.remove();
						} 
						i++;
	
					}
					p.sendMessage("§a§lSukces: §7Zabito §f" + i + " entity.");
					return true;
				} else {
					p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
					return true;
				}
			} else {
				sender.sendMessage("§c§lBlad: §7Ta komenda nie jest dostepna dla konsoli!");
				return true;
			}
		}
		return false;
	}

}
