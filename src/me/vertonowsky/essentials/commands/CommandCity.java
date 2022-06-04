package me.vertonowsky.essentials.commands;

import me.vertonowsky.mysql.Users;
import me.vertonowsky.user.User;
import me.vertonowsky.user.UserUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandCity implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setcity")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (!p.hasPermission("vert.*")) {
                    p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
                    return true;
                }
            }

            if (args.length == 2) {
                User tUUID = User.get(args[0]);
                if (UserUtils.getUsers().contains(tUUID)) {
                    if (args[1].equals("null")) {
                        tUUID.setCity("");
                    } else {
                        tUUID.setCity(args[1]);
                    }
                    sender.sendMessage("§a§lSuckes: §7Przyznano miasto §f" + args[1] + " §7 dla gracza §f" + args[0] + "§7.");
                    tUUID.sendMessage("§a§lSukces: §7Otrzymano rangę - Miasto: §f" + args[1] + "§7.");
                    Users.saveDataUserGeneralOne(tUUID);
                    return true;
                } else {
                    sender.sendMessage("§c§lBlad: §7Niewlasciwy nick lub gracz nie jest online.");
                    return true;
                }
            } else {
                sender.sendMessage("§6§lInfo: §7Uzyj §e/setcity <gracz> <miasto>§7.");
                return true;
            }
        }

        return false;

    }
}
