package me.vertonowsky.essentials.commands;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.Island;
import me.vertonowsky.api.API;
import me.vertonowsky.main.Main;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandIsland implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("oijziojsjdfisjdfisdf")) {
            if (args.length == 1) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    Player p = Bukkit.getPlayer(args[0]);
                    if (ASkyBlockAPI.getInstance().hasIsland(p.getUniqueId())) {
                        User pUUID = User.get(p.getName());
                        pUUID.setIslandCreatedDate(System.currentTimeMillis());
                        return true;
                    }
                }
            }
        }

        if (cmd.getName().equalsIgnoreCase("wyspa")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (args.length == 0) {
                    p.sendMessage("§8§m---------------------§8[ §bWyspa §8]§m---------------------");
                    p.sendMessage("§b/wyspa stworz §f- §7tworzy nową wyspę.");
                    p.sendMessage("§b/dom §f- §7teleportuje na wyspę.");
                    p.sendMessage("§b/wyspa ustawdom §f- §7ustawia dom wyspy.");
                    p.sendMessage("§b/wyspa odwiedz <gracz> §f- §7teleportuje na wyspę gracza.");
                    p.sendMessage("§b/wyspa gracze §f- §7pokazuje listę komend do zarządzania graczami.");
                    p.sendMessage("§b/wyspa info §f- §7pokazuje informacje o wyspie.");
                    p.sendMessage("§b/wyspa wlasciciel <gracz> §8- §7Zmienia właściciela wyspy.");
                    p.sendMessage("§b/wyspa inne §f- §7pozostała lista komend.");
                    return true;
                }

                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("gracze")) {
                        p.sendMessage("§8§m-----------------§8[ §bWyspa | Gracze§8]§m-----------------");
                        p.sendMessage("§b/wyspa dodaj <gracz> §8- §7dodaje gracza do wyspy.");
                        p.sendMessage("§b/wyspa usuń <gracz> §8- §7usuwa gracza z wyspy.");
                        p.sendMessage("§b/wyspa opusc §8- §7opuszcza wyspę innego gracza.");
                        p.sendMessage("§b/wyspa wyrzuc <gracz> §8- §7wyrzuca gracza, który jest na wyspie.");
                        p.sendMessage("§b/wyspa czlonkowie §8- §7pokazuje listę uprawnionych graczy.");
                        p.sendMessage("§b/wyspa coop <gracz> §8- §7daje komuś pełny dostęp do wyspy.");
                        p.sendMessage("§b/wyspa uncoop <gracz> §8- §7zabiera komuś pełny dostęp do wyspy.");
                        p.sendMessage("§b/wyspa cooplist §8- §7pokazuje listę uprawnionych graczy.");
                        p.sendMessage("§b/wyspa ban <gracz> §8- §7blokuje gracza.");
                        p.sendMessage("§b/wyspa unban <gracz> §8- §7odblokowywuje gracza.");
                        p.sendMessage("§b/wyspa banlist §8- §7pokazuje listę zbanowanych graczy.");
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("inne")) {
                        p.sendMessage("§8§m------------------§8[ §bWyspa | Inne§8]§m------------------");
                        p.sendMessage("§b/wyspa cp §8- §7otwiera panel zarządzania wyspą.");
                        p.sendMessage("§b/wyspa restart §8- §7usuwa wyspę i tworzy nową.");
                        p.sendMessage("§b/wyspa biom §8- §7otwiera menu z biomami.");
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("info")) {
                        Island island = null;
                        if (ASkyBlockAPI.getInstance().hasIsland(p.getUniqueId()))
                            island = ASkyBlockAPI.getInstance().getIslandOwnedBy(p.getUniqueId());

                        if (ASkyBlockAPI.getInstance().inTeam(p.getUniqueId()))
                            island = ASkyBlockAPI.getInstance().getIslandOwnedBy(ASkyBlockAPI.getInstance().getTeamLeader(p.getUniqueId()));


                        if (island == null) {
                            p.sendMessage("§c§lBłąd: §7Nie posiadasz własnej wyspy.");
                            return true;
                        }


                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        String status = (format.format(User.get(island.getOwner()).getIslandCreatedDate()));
                        StringBuilder builder = new StringBuilder();

                        for (UUID uuid : island.getMembers()) {
                            if (!uuid.equals(island.getOwner())) {
                                builder.append(" §7" + User.get(uuid).getName() + "§8,");
                            }
                        }
                        if (builder.length() > 0) builder.deleteCharAt(builder.length() - 1);
                        if (builder.length() <= 0) builder.append(" §7Brak");
                        Location islandCenter = island.getCenter();


                        p.sendMessage("§8§m---------[§b Wyspa | Info §8]§m---------------");
                        p.sendMessage("");
                        p.sendMessage("§8§l>> §bWłaściciel: §7" + User.get(island.getOwner()).getName());
                        p.sendMessage("§8§l>> §bWspółrzędne: §8[§7" + (int) islandCenter.getX() + "§7," + (int) islandCenter.getY() + "§7," + (int) islandCenter.getZ() + "§8]");
                        p.sendMessage("§8§l>> §bRozmiar wyspy: §7" + island.getProtectionSize());
                        p.sendMessage("§8§l>> §bData utworzenia: §7" + status);
                        p.sendMessage("§8§l>> §bBiom: §7" + island.getBiome());
                        p.sendMessage("§8§l>> §bCzłonkowie:" + builder.toString());
                        p.sendMessage("");
                        p.sendMessage("§8§m----------------------------------");
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("stworz")) {
                        Bukkit.getServer().dispatchCommand(p, "is");
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("dom")) {
                        int number = 1;
                        if (args.length == 1) {
                            if (API.isInt(args[0])) number = Integer.parseInt(args[0]);
                        }
                        Bukkit.getServer().dispatchCommand(p, "is go " + number);
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("czlonkowie")) {
                        Bukkit.getServer().dispatchCommand(p, "is team");
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("cooplist")) {
                        Bukkit.getServer().dispatchCommand(p, "is listcoops");
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("ustawdom")) {
                        Bukkit.getServer().dispatchCommand(p, "is sethome");
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("opusc")) {
                        Bukkit.getServer().dispatchCommand(p, "is leave");
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("banlist")) {
                        Bukkit.getServer().dispatchCommand(p, "is banlist");
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("wyrzuc")) {
                        UUID owner = null;
                        if (ASkyBlockAPI.getInstance().hasIsland(p.getUniqueId()))
                            owner = p.getUniqueId();

                        if (ASkyBlockAPI.getInstance().inTeam(p.getUniqueId()))
                            owner = ASkyBlockAPI.getInstance().getTeamLeader(p.getUniqueId());

                        Island island = ASkyBlockAPI.getInstance().getIslandOwnedBy(owner);
                        int count = 0;
                        List<UUID> members = new ArrayList<>();
                        for (UUID uuid : island.getMembers()) {
                            members.add(uuid);
                        }

                        for (Player player : Bukkit.getOnlinePlayers()) {
                            if (!player.hasPermission("vert.*")) {
                                if (!members.contains(player.getUniqueId())) {
                                    if (island.onIsland(player.getLocation())) {
                                        if (Main.spawn != null) {
                                            player.teleport(Main.spawn);
                                            player.sendMessage("§6§lInfo: §7Zostałeś wyrzucony z wyspy.");
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                        if (count == 0) {
                            p.sendMessage("§c§lBłąd: §7Nie ma żadnych graczy na Twojej wyspie.");
                            return true;
                        }
                        p.sendMessage("§a§lSukces: §7Wyrzucono §f" + count + " §7graczy z wyspy.");
                        return true;
                    }


                    if (args[0].equalsIgnoreCase("cp")) {
                        Bukkit.getServer().dispatchCommand(p, "is cp");
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("restart")) {
                        Bukkit.getServer().dispatchCommand(p, "is reset");
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("potwierdz")) {
                        Bukkit.getServer().dispatchCommand(p, "is confirm");
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("akceptuj")) {
                        Bukkit.getServer().dispatchCommand(p, "is accept");
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("odrzuc")) {
                        Bukkit.getServer().dispatchCommand(p, "is deny");
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("biom")) {
                        Bukkit.getServer().dispatchCommand(p, "is biomes");
                        return true;
                    }

                }


                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("ustawdom")) {
                        if (API.isInt(args[1])) {
                            Bukkit.getServer().dispatchCommand(p, "is sethome " + args[1]);
                            return true;
                        }
                        p.sendMessage("§6§lInfo: §7Użyj §e/wyspa ustawdom <1-2>§7.");
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("odwiedz")) {
                        Bukkit.getServer().dispatchCommand(p, "is warp " + args[1]);
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("wlasciciel")) {
                        Bukkit.getServer().dispatchCommand(p, "is makeleader " + args[1]);
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("dodaj")) {
                        Bukkit.getServer().dispatchCommand(p, "is invite " + args[1]);
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("usun")) {
                        Bukkit.getServer().dispatchCommand(p, "is kick " + args[1]);
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("wyrzuc")) {
                        Bukkit.getServer().dispatchCommand(p, "is expel " + args[1]);
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("coop")) {
                        Bukkit.getServer().dispatchCommand(p, "is coop " + args[1]);
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("uncoop")) {
                        Bukkit.getServer().dispatchCommand(p, "is expel " + args[1]);
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("ban")) {
                        Bukkit.getServer().dispatchCommand(p, "is ban " + args[1]);
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("unban")) {
                        Bukkit.getServer().dispatchCommand(p, "is unban " + args[1]);
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("cp")) {
                        if (args[1].equalsIgnoreCase("on") || args[1].equalsIgnoreCase("off")) {
                            Bukkit.getServer().dispatchCommand(p, "is cp " + args[1]);
                            return true;
                        }
                        p.sendMessage("§6§lInfo: §7Użyj §e/wyspa cp <on/off>§7.");
                        return true;
                    }
                }

                /*if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("setowner")) {
                        if (!p.hasPermission("vert.island.setowner")) {
                            p.sendMessage("§6§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
                            return true;
                        }
                        if (User.get(args[1]) != null) {
                            if (User.get(args[2]) != null) {
                                User oldOwnerUser = User.get(args[1]);
                                User newOwnerUser = User.get(args[2]);
                                Island island = ASkyBlockAPI.getInstance().getIslandOwnedBy(oldOwnerUser.getUuid());
                                if (island == null) {
                                    p.sendMessage("§c§lBłąd: §7Gracz §f" + args[1] + " §7nie posiada własnej wyspy!");
                                    return true;
                                }

                                Island island2 = ASkyBlockAPI.getInstance().getIslandOwnedBy(newOwnerUser.getUuid());
                                if (island2 != null) {
                                    p.sendMessage("§c§lBłąd: §7Gracz §f" + args[2] + " §7jest już właścicielem innej wyspy!");
                                    return true;
                                }

                                island.setOwner(newOwnerUser.getUuid());

                                if (oldOwnerUser.isOnline())
                                    oldOwnerUser.sendMessage("§a§lSukces: §7Nie jesteś już dłużej wlaścicielem wyspy!");
                                if (newOwnerUser.isOnline())
                                    newOwnerUser.sendMessage("§a§lSukces: §7Zostałeś nowym właścicielem wyspy!");

                                p.sendMessage("§a§lSukces: §7Gracz §f" + args[2] + " §7został nowym właścicielem wyspy §f" + args[1] + "§7!");
                                return true;
                            } else {
                                p.sendMessage("§c§lBłąd: §7Gracz §f" + args[2] + " §7nie istnieje!");
                                return true;
                            }
                        } else {
                            p.sendMessage("§c§lBłąd: §7Gracz §f" + args[1] + " §7nie istnieje!");
                            return true;
                        }
                    } else {
                        p.sendMessage("§c§lBłąd: §7Użyj §f/wyspa setowner <stary> <nowy>§7!");
                        return true;
                    }

                 */

                 else {
                    p.sendMessage("§c§lBłąd: §7Sprawdź składnię polecenia!");
                    return true;
                }

            }
        }
        if (cmd.getName().equalsIgnoreCase("dom")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                int number = 1;
                if (args.length == 1) {
                    if (API.isInt(args[0])) number = Integer.parseInt(args[0]);
                }
                Bukkit.getServer().dispatchCommand(p, "is go " + number);
                return true;
            }
        }
        if (cmd.getName().equalsIgnoreCase("odwiedz")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (args.length == 1) {
                    Bukkit.getServer().dispatchCommand(p, "is warp " + args[0]);
                    return true;
                }
                p.sendMessage("§6§lInfo: §7Użyj §e/odwiedz <gracz>§7.");
                return true;
            }
        }
        return false;
    }


}
