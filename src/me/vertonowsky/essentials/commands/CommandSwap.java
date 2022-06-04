package me.vertonowsky.essentials.commands;

import me.vertonowsky.mysql.MySQL;
import me.vertonowsky.mysql.Quests;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class CommandSwap implements CommandExecutor {

	public static ItemStack moneyChestItem;
	public static ItemStack mobSpawnerTool;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("swap")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (!p.hasPermission("vert.*")) {
					p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
					return true;
				}
			}

			if (args.length == 2) {
				if (User.get(args[0]) != null) {
					if (User.get(args[1]) != null) {
						User u1 = User.get(args[0]);
						User u2 = User.get(args[1]);

						if (u1.isOnline()) Bukkit.getPlayer(u1.getUuid()).kickPlayer("§8§l>> §aDane dotyczące Twojego konta zostały zaktualizowane!");
						if (u2.isOnline()) Bukkit.getPlayer(u2.getUuid()).kickPlayer("§8§l>> §aDane dotyczące Twojego konta zostały zaktualizowane!");

						UUID uuid1 = u1.getUuid();
						String name1 = u1.getName();

						UUID uuid2 = u2.getUuid();
						String name2 = u2.getName();


						u1.setUuid(UUID.randomUUID());
						u1.setName("Test1");
						u2.setUuid(UUID.randomUUID());
						u2.setName("Test2");


						u1.setUuid(uuid2);
						u1.setName(name2);
						u2.setUuid(uuid1);
						u2.setName(name1);

						MySQL.openConnection();
						Quests.swapQuestsPlayer(u1, u2);
						MySQL.closeConnection();

						sender.sendMessage("§a§lSukces: §7Zamieniono dane użytkowników: §f" + args[0] + "§7, §f" + args[1] + "§7.");
						return true;
					} else {
						sender.sendMessage("§a§lSukces: §7Użytkownik §f" + args[1] + " §7nie istnieje!");
						return true;
					}
				} else {
					sender.sendMessage("§a§lSukces: §7Użytkownik §f" + args[0] + " §7nie istnieje!");
					return true;
				}
			} else {
				sender.sendMessage("§6§lSukces: §7Użyj: §e/swap <gracz_kto> <gracz_z_kim>");
				return true;
			}
		}
		return false;
	}

}
