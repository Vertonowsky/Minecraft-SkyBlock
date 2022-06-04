package me.vertonowsky.essentials.commands;

import me.vertonowsky.api.API;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandPrize implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("prize")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                User pUUID = User.get(p.getUniqueId());
                if (p.hasPermission("vert.*")) {
                    if (args.length == 1) {
                        if (API.isInt(args[0])) {
                            ItemStack itemInHand = new ItemStack(Material.PAPER, 1);
                            ItemMeta meta = itemInHand.getItemMeta();
                            meta.setDisplayName("§a$" + args[0] + " §7[Kliknij prawy]");
                            itemInHand.setItemMeta(meta);
                            pUUID.giveItem(itemInHand, false);

                            p.sendMessage("§a§lSukces: §7Dodano bon o wartości: §f" + args[0] + "§7. Użyj §f/forward §7aby przekazać go wszystkim graczom.");
                            return true;
                        } else {
                            p.sendMessage("§6§lInfo: §7Użyj §f/prize <kwota>§7.");
                            return true;
                        }
                    } else {
                        p.sendMessage("§6§lInfo: §7Użyj §f/prize <kwota>§7.");
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

        if (cmd.getName().equalsIgnoreCase("forward")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("vert.*")) {
                    ItemStack itemInHand = p.getInventory().getItemInMainHand();
                    for (Player t : Bukkit.getOnlinePlayers()) {
                        User tUUID = User.get(t.getUniqueId());
                        tUUID.giveItem(itemInHand, false);
                        tUUID.sendMessage("§6§lInfo: §7Otrzymano nagrodę od §fAdministracji§7!");
                    }

                    p.sendMessage("§a§lSukces: §7Wysłano przedmiot wszystkim graczom!");
                    return true;
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
