package me.vertonowsky.essentials.commands;

import me.vertonowsky.inventory.Roles;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandRole implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("zdolnosci")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (args.length == 1) {
					if (p.hasPermission("vert.*")) {
						if (User.get(args[0]) != null) {
							Inventory inv = Bukkit.createInventory(null, 27, "§8Zdolnosci " + args[0]);
							p.openInventory(inv);
							Roles.openInventoryRolesInventory(inv, User.get(args[0]));
						} else {
							p.sendMessage("§c§lBład: §7Niewłaściwy nick lub gracz nie jest online!");
							return true;
						}
					} else {
						p.sendMessage("§c§lBład: §7Nieznana komenda lub nie masz do niej uprawnień!");
						return true;
					}
				}
				if (args.length == 0) {
					Inventory inv = Bukkit.createInventory(null, 27, "§8Zdolnosci " + p.getName());
					p.openInventory(inv);
					Roles.openInventoryRolesInventory(inv, User.get(p.getName()));
					return true;
				}
			} else {
				sender.sendMessage("§c§lBlad: §7Ta komenda nie jest dostepna dla konsoli!");
			}
		}
		return false;
	}
}