package me.vertonowsky.essentials.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWeather implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("sun")) {
			if ((sender instanceof Player)) {
				Player p = (Player) sender;
				World world = p.getWorld();
				if (p.hasPermission("vert.*")) {
					world.setStorm(false);
					world.setThundering(false);
					p.sendMessage("§a§lSukces: §7Ustawiono §fsłoneczną §7pogodę.");
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

		if (cmd.getName().equalsIgnoreCase("rain")) {
			if ((sender instanceof Player)) {
				Player p = (Player) sender;
				World world = p.getWorld();
				if (p.hasPermission("vert.*")) {
					world.setStorm(true);
					world.setThundering(true);
					p.sendMessage("§a§lSukces: §7Ustawiono §fdeszczową §7pogodę.");
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
