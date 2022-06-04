package me.vertonowsky.essentials.commands;

import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWhoIs implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("whois")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (!p.hasPermission("vert.*")) {
                    p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
                    return true;
                }
            }

            if (args.length == 1) {
                if (User.get(args[0]) != null) {
                    String address = "Offline";
                    int port = 0;
                    if (Bukkit.getPlayer(args[0]) != null) {
                        Player t = Bukkit.getPlayer(args[0]);
                        address = t.getAddress().getHostName();
                        port = t.getAddress().getPort();
                    }

                    sender.sendMessage("   §8§m---------------------------------  §7  §7  §7");
                    sender.sendMessage("");
                    sender.sendMessage("       §8§l>> §bAdres IP: §7" + address);
                    sender.sendMessage("       §8§l>> §bPort: §7" + port);
                    sender.sendMessage("");
                    sender.sendMessage("   §8§m---------------------------------  §7  §7  §7");
                    return true;

                } else {
                    sender.sendMessage("§c§lBłąd: §7Gracz o takiej nazwie nie istnieje.");
                    return true;
                }

            } else {
                sender.sendMessage("§6§lInfo: §7Użyj §e/whois <gracz>§7.");
                return true;
            }
        }
        return false;
    }


}
