package me.vertonowsky.essentials.commands;

import me.vertonowsky.api.API;
import me.vertonowsky.main.Main;
import me.vertonowsky.mysql.Users;
import me.vertonowsky.permissions.PermissionVert;
import me.vertonowsky.user.User;
import me.vertonowsky.user.UserUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class CommandRank implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setrank")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (!p.hasPermission("vert.*")) {
                    p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
                    return true;
                }
            }

            if (args.length == 2 || args.length == 3) {
                User tUUID = User.get(args[0]);
                if (UserUtils.getUsers().contains(tUUID)) {

                    File f = new File(Main.getInst().getDataFolder(), "permissions.yml");
                    YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
                    List<String> ranksLower = new ArrayList<>();
                    List<String> ranksNormal = new ArrayList<>();
                    for (String rank : yml.getConfigurationSection("ranks").getKeys(false)) {
                        ranksNormal.add(rank);
                        ranksLower.add(rank.toLowerCase());
                    }

                    if (ranksLower.contains(args[1].toLowerCase())) {
                        long days = -1;
                        String time = "";
                        if (args.length == 3) {
                            days = Long.parseLong(args[2]);
                            time = " §bna §f" + args[2] + " §bdni!";
                        }
                        PermissionVert.setRank(tUUID, args[1], days, false);


                        if (!(args[1].equals("KidMod") || args[1].equals("Moderator") || args[1].equals("Admin"))) {
                            Bukkit.broadcastMessage("§8§m--------------------------------------------------");
                            Bukkit.broadcastMessage("");
                            Bukkit.broadcastMessage("§8§l➢ §bGracz §f" + tUUID.getName() + " §bzakupił rangę §f" + args[1] + time);
                            Bukkit.broadcastMessage("");
                            Bukkit.broadcastMessage("§8§l➢   §3Serdecznie dziękujemy za wsparcie");
                            Bukkit.broadcastMessage("§8§l➢       §3oraz życzymy miłej gry!");
                            Bukkit.broadcastMessage("");
                            Bukkit.broadcastMessage("§8§m--------------------------------------------------");
                        } else {
                            Bukkit.broadcastMessage("§8§m--------------------------------------------------");
                            Bukkit.broadcastMessage("");
                            Bukkit.broadcastMessage("§8§l➢ §bGracz §f" + tUUID.getName() + " §bawansował na rangę §f" + args[1]);
                            Bukkit.broadcastMessage("");
                            Bukkit.broadcastMessage("§8§l➢   §3Dziękujemy za dbanie o bezpieczne");
                            Bukkit.broadcastMessage("§8§l➢        §3korzystanie z serwera!");
                            Bukkit.broadcastMessage("");
                            Bukkit.broadcastMessage("§8§m--------------------------------------------------");
                        }
                        Users.saveDataUserGeneralOne(tUUID);
                        API.playBroadcastSound(Sound.ENTITY_PLAYER_LEVELUP);
                        return true;
                    } else {
                        sender.sendMessage("§c§lBlad: §7Dostępne rangi: §f" + ranksNormal.toString().replaceAll("\\[", "").replaceAll("]", ""));
                        return true;
                    }
                } else {
                    sender.sendMessage("§c§lBlad: §7Niewlasciwy nick lub gracz nie jest online.");
                    return true;
                }
            } else {
                sender.sendMessage("§6§lInfo: §7Uzyj §e/setrank <gracz> <ranga> <dni>§7.");
                return true;
            }
        }

        return false;

    }
}
