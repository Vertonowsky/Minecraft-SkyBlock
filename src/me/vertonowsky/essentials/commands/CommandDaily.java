package me.vertonowsky.essentials.commands;

import me.vertonowsky.user.User;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;

public class CommandDaily implements CommandExecutor {

	public static ItemStack dailyChestItem;


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("zestaw")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				User pUUID = User.get(p.getUniqueId());
				if (System.currentTimeMillis() < pUUID.getNextDailyReward()) {
					SimpleDateFormat format = new SimpleDateFormat("dd-MM HH:mm");
					String status = (format.format(pUUID.getNextDailyReward()));
					p.sendMessage("§c§lBłąd: §7Dzienny zestaw będzie dostępny §f" + status + "§7.");
					return true;
				}

				pUUID.setNextDailyReward(System.currentTimeMillis() + 60*60*24*1000);
				p.sendMessage("§a§lSukces: §7Odebrano §aDzienny zestaw§7.");
				pUUID.giveItem(dailyChestItem, true);
				p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.15F, 0.15F);
				return true;

			} else {
				sender.sendMessage("§c§lBłąd: §7Ta komenda nie jest dostepna dla konsoli!");
				return true;
			}
		}
		return false;
	}

}
