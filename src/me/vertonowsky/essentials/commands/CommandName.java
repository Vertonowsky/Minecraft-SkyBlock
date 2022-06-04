package me.vertonowsky.essentials.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandName implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("name")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("vert.*")) {
                    if (args.length >= 1) {
                        ItemStack itemInHand = p.getInventory().getItemInMainHand();
                        ItemMeta meta = itemInHand.getItemMeta();

                        StringBuilder message = new StringBuilder();
                        for (int i = 0; i < args.length; i++) {
                            message.append(args[i] + " ");
                        }

                        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', message.toString()));
                        itemInHand.setItemMeta(meta);
                        p.getInventory().setItemInMainHand(itemInHand);

                        p.sendMessage("§a§lSukces: §7Zmieniono nazwę na §f" + ChatColor.translateAlternateColorCodes('&', message.toString()));
                        return true;
                    } else {
                        p.sendMessage("§6§lInfo: §7Użyj §f/name <nazwa>§7.");
                        return true;
                    }
                } else {
                    p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
                    return true;
                }

            } else {
                sender.sendMessage("§c§lBłąd: §7Ta komenda nie jest dostepna dla konsoli!");
                return true;
            }
        }
        return false;
    }
}
