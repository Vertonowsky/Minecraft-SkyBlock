package me.vertonowsky.essentials.commands;

import me.vertonowsky.api.API;
import me.vertonowsky.main.Main;
import me.vertonowsky.user.User;
import me.vertonowsky.user.UserUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandLevel implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("doubleexp")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (!p.hasPermission("vert.*")) {
                    p.sendMessage("Ëc§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień.");
                    return true;
                }
            }
            if (Main.doubleExp == false) {
                Main.doubleExp = true;
                sendChatInfo("EXP", "§aWłączony!");
                return true;
            }
            if (Main.doubleExp == true) {
                Main.doubleExp = false;
                sendChatInfo("EXP", "§cWyłączony!");
                return true;
            }
        }

        if (cmd.getName().equalsIgnoreCase("doubledrop")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (!p.hasPermission("vert.*")) {
                    p.sendMessage("Ëc§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień.");
                    return true;
                }
            }
            if (Main.doubleDrop == false) {
                Main.doubleDrop = true;
                sendChatInfo("DROP", "§aWłączony!");
                return true;
            }
            if (Main.doubleDrop == true) {
                Main.doubleDrop = false;
                sendChatInfo("DROP", "§cWyłączony!");
                return true;
            }
        }

        if (cmd.getName().equalsIgnoreCase("ustawpoziom")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (!p.hasPermission("vert.*")) {
                    p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
                    return true;
                }
            }
            if (args.length == 2) {
                if (UserUtils.getUsers().contains(User.get(args[0]))) {
                    if (API.isInt(args[1])) {
                        Integer level = Integer.parseInt(args[1]);
                        if (level > 0 && level <= 200) {
                            User tUUID = User.get(args[0]);
                            tUUID.setTotalLevel(level);
                            sender.sendMessage("§a§lSukces: §7Ustawiono §f" + level + " §7poziom dla §f" + tUUID.getName() + "§7.");
                            return true;
                        } else {
                            sender.sendMessage("§c§lBłąd: §7Możliwy zakres poziomów §f[1-200]§7.");
                            return true;
                        }
                    } else {
                        sender.sendMessage("§6§lInfo: §7Użyj §e/ustawpoziom <gracz> <poziom>§7.");
                        return true;
                    }

                } else {
                    sender.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online!");
                    return true;
                }
            } else {
                sender.sendMessage("§6§lInfo: §7Użyj §e/ustawpoziom <gracz> <poziom>§7.");
                return true;
            }
        }
        return false;
    }








    private void sendChatInfo(String what, String type) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("§8§m--------------------------------------------------");
        list.add("                             §fInformacje");
        list.add("");
        list.add("");
        list.add("               §aDOUBLE " + what + " §7został " + type);
        list.add("");
        list.add("");
        list.add("§8§m--------------------------------------------------");
        for (String s : list) {
            API.sendBroadcastMessage(s);
        }

    }


}
