package me.vertonowsky.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSouls implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("dusza")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				p.sendMessage("§8§m-----------------------------------------");
				p.sendMessage("");
				p.sendMessage("   §8§l>> §bAwansując na wyższy poziom, §7liczba");
				p.sendMessage("   §8§l>> §7dusz §bzwiększa się o §7jeden§b..");
				p.sendMessage("");
				p.sendMessage("   §8§l>> §bJeżeli masz co najmniej jedną duszę,");
				p.sendMessage("   §8§l>> §bto po śmierci nie stracisz żadnych");
				p.sendMessage("   §8§l>> §bprzedmiotów, ale stracisz §71 duszę§b.");
				p.sendMessage("");
				p.sendMessage("§8§m-----------------------------------------");

			} else {
				sender.sendMessage("§c§lBłąd: §7Ta komenda nie jest dostepna dla konsoli!");
				return true;
			}
		}
		return false;
	}
}
