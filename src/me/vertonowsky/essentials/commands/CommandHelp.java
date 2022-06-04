package me.vertonowsky.essentials.commands;

import me.vertonowsky.inventory.Help;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandHelp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pomoc")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                Inventory inv = Bukkit.createInventory(null, 27, "§8Pomoc - Problemy");
                p.openInventory(inv);
                Help.openInventoryCategoryHelp(inv, p);
                return true;
            }
        }
        return false;
    }


}
