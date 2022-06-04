package me.vertonowsky.essentials.commands;

import me.vertonowsky.inventory.Zadania;
import me.vertonowsky.mysql.MySQL;
import me.vertonowsky.mysql.Quests;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandQuests implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("addquest")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (!p.hasPermission("vert.*")) {
                    p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
                    return true;
                }
            }

            MySQL.openConnection();
            Quests.addToQuests();
            MySQL.closeConnection();
            sender.sendMessage("§a§lSukces: §7Zaktualizowano zadania!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("zadania")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                Inventory inv = Bukkit.createInventory(null, 54, "§8Zadania");
                p.openInventory(inv);
                Zadania.openInventoryZadania(inv, p, 0);
                return true;
            }
        }
        return false;
    }
}
