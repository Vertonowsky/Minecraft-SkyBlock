package me.vertonowsky.essentials.commands;

import me.vertonowsky.api.API;
import me.vertonowsky.api.Holograms;
import me.vertonowsky.main.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandHologram implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("vholo")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("vert.*")) {
                    if (args.length == 0) {
                        p.sendMessage("§8§m--------------------------------------------------");
                        p.sendMessage("§8- §b/vholo create <nazwa> <text> §8- §7Tworzy hologram.");
                        p.sendMessage("§8- §b/vholo delete <nazwa> §8- §7Usuwa hologram.");
                        p.sendMessage("§8- §b/vholo teleport <nazwa> §8- §7Teleportuje hologram.");
                        p.sendMessage("§8- §b/vholo setline <nazwa> <linia> <text> §8- §7Zmienia tekst linijki.");
                        p.sendMessage("§8- §b/vholo delline <nazwa> <linia>  §8- §7Usuwa linijke.");
                        p.sendMessage("§8- §b/vholo list §8- §7Wyświetla listę hologramów.");
                        p.sendMessage("§8- §b/vholo reload §8- §7Odświerza wszystkie hologramy.");
                        p.sendMessage("§8§m--------------------------------------------------");
                        return true;
                    }
                    if (args.length >= 1) {

                        if (args[0].equalsIgnoreCase("reload")) {
                            Holograms.reloadHolograms();
                            p.sendMessage("§a§lSukces: §7Zaktualizowano wszystkie hologramy.");
                            return true;
                        }

                        if (args[0].equalsIgnoreCase("create")) {
                            if (args.length < 3) {
                                p.sendMessage("§6§lInfo: §7Użyj §e/vholo create <nazwa> <text>§7.");
                                return true;
                            }
                        }

                        else if (args[0].equalsIgnoreCase("delete")) {
                            if (args.length < 2) {
                                p.sendMessage("§6§lInfo: §7Użyj §e/vholo delete <nazwa>§7.");
                                return true;
                            }
                        }

                        else if (args[0].equalsIgnoreCase("teleport")) {
                            if (args.length < 2) {
                                p.sendMessage("§6§lInfo: §7Użyj §e/vholo teleport <nazwa>§7.");
                                return true;
                            }
                        }

                        else if (args[0].equalsIgnoreCase("setline")) {
                            if (args.length < 4) {
                                p.sendMessage("§6§lInfo: §7Użyj §e/vholo setline <nazwa> <linia> <text>§7.");
                                return true;
                            }
                        }

                        else if (args[0].equalsIgnoreCase("delline")) {
                            if (args.length < 3) {
                                p.sendMessage("§6§lInfo: §7Użyj §e/vholo delline <nazwa> <linia>§7.");
                                return true;
                            }
                        }

                        else if (args[0].equalsIgnoreCase("list")) {
                            File f = new File(Main.getInst().getDataFolder(), "holograms.yml");
                            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
                            //List<String> list = new ArrayList<>();
                            String list = "§f";
                            int i = 0;
                            for (String id : yaml.getConfigurationSection("holograms").getKeys(false)) {
                                list = list + id;
                                if (i < yaml.getConfigurationSection("holograms").getKeys(false).size() -1) list = list + "§7, §f";
                                i++;
                            }


                            p.sendMessage("§6§lInfo: §7Dostępne hologramy: " + list + "§7.");
                            return true;
                        }
                    }

                    if (args.length == 2) {
                        File f = new File(Main.getInst().getDataFolder(), "holograms.yml");
                        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
                        String id = args[1];


                        if (args[0].equalsIgnoreCase("delete")) {
                            if (!yaml.contains("holograms." + id)) {
                                p.sendMessage("§c§lBłąd: §7Hologram o ID §f" + id + " §7nie istnieje!");
                                return true;
                            }

                            yaml.set("holograms." + id, null);
                            try {
                                yaml.save(f);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            p.sendMessage("§a§lSukces: §7Usunięto hologram o ID §f" + id + "§7.");
                            Holograms.reloadHolograms();
                            return true;
                        }


                        if (args[0].equalsIgnoreCase("teleport")) {
                            if (!yaml.contains("holograms." + id)) {
                                p.sendMessage("§c§lBłąd: §7Hologram o ID §f" + id + " §7nie istnieje!");
                                return true;
                            }

                            Location loc = p.getLocation();
                            loc.setX(loc.getX());
                            loc.setY(loc.getY() + 2);
                            loc.setZ(loc.getZ());
                            loc.setYaw(0);
                            loc.setPitch(0);

                            yaml.set("holograms." + id + ".location", Main.LocToString(loc));
                            try {
                                yaml.save(f);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            p.sendMessage("§a§lSukces: §7Przeteleportowano hologram o ID §f" + id + "§7.");
                            Holograms.reloadHolograms();
                            return true;
                        }

                    }

                    if (args.length == 3) {
                        File f = new File(Main.getInst().getDataFolder(), "holograms.yml");
                        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
                        String id = args[1];

                        if (args[0].equalsIgnoreCase("delline")) {
                            int line = Integer.parseInt(args[2]);
                            if (!yaml.contains("holograms." + id)) {
                                p.sendMessage("§c§lBłąd: §7Hologram o ID §f" + id + " §7nie istnieje!");
                                return true;
                            }

                            List<String> oldLines = new ArrayList<>();
                            oldLines.addAll(yaml.getStringList("holograms." + id + ".text"));

                            if (line > 0 && line <= oldLines.size()) {
                                oldLines.remove(line -1);
                            } else {
                                p.sendMessage("§c§lBłąd: §7Hologram o ID §f" + id + " §7nie posiada §f" + line + " §7linijek.");
                                return true;
                            }


                            yaml.set("holograms." + id + ".text", oldLines);
                            try {
                                yaml.save(f);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            p.sendMessage("§a§lSukces: §7Usunięto §f" + line + " §7linijkę hologramu o ID §f" + id + "§7.");
                            Holograms.reloadHolograms();
                            return true;
                        }

                    }


                    if (args.length > 2) {
                        if (args[0].equalsIgnoreCase("create")) {
                            File f = new File(Main.getInst().getDataFolder(), "holograms.yml");
                            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
                            String id = args[1];

                            if (yaml.contains("holograms." + id)) {
                                p.sendMessage("§c§lBłąd: §7Hologram o ID §f" + id + " §7już istnieje!");
                                return true;
                            }

                            Location loc = p.getLocation();
                            loc.setX(loc.getX());
                            loc.setY(loc.getY() + 2);
                            loc.setZ(loc.getZ());
                            loc.setYaw(0);
                            loc.setPitch(0);
                            boolean visible = true;
                            List<String> lines = new ArrayList<>();


                            StringBuilder message = new StringBuilder();
                            for (int i = 2; i < args.length; i++) {
                                String s = args[i];
                                if (i < args.length - 1) s = s + " ";
                                message.append(s);
                            }

                            lines.add(message.toString());


                            yaml.set("holograms." + id + ".location", Main.LocToString(loc));
                            yaml.set("holograms." + id + ".visible", visible);
                            yaml.set("holograms." + id + ".text", lines);
                            try {
                                yaml.save(f);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Holograms.reloadHolograms();

                            p.sendMessage("§a§lSukces: §7Utworzono hologram o ID §f" + id + "§7.");
                            return true;
                        }
                    }

                    if (args.length > 3) {
                        if (args[0].equalsIgnoreCase("setline")) {
                            File f = new File(Main.getInst().getDataFolder(), "holograms.yml");
                            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
                            String id = args[1];
                            if (!API.isInt(args[2])) {
                                p.sendMessage("§6§lInfo: §7Użyj §e/vholo setline <nazwa> <linia> <text>§7.");
                                return true;
                            }
                            int line = Integer.parseInt(args[2]);

                            if (!yaml.contains("holograms." + id)) {
                                p.sendMessage("§c§lBłąd: §7Hologram o ID §f" + id + " §7nie istnieje!");
                                return true;
                            }

                            List<String> oldLines = new ArrayList<>();
                            List<String> newLines = new ArrayList<>();


                            StringBuilder message = new StringBuilder();
                            for (int i = 3; i < args.length; i++) {
                                String s = args[i];
                                if (i < args.length - 1) s = s + " ";
                                message.append(s);
                            }

                            oldLines.addAll(yaml.getStringList("holograms." + id + ".text"));


                            for (int i = 0; i < line -1; i++) {
                                if (i < oldLines.size()) {
                                    newLines.add(oldLines.get(i));
                                } else {
                                    newLines.add(" ");
                                }
                            }

                            newLines.add(message.toString());

                            if (oldLines.size() > line) {
                                for (int i = line; i < oldLines.size(); i++) {
                                    newLines.add(oldLines.get(i));
                                }
                            }



                            yaml.set("holograms." + id + ".text", newLines);
                            try {
                                yaml.save(f);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Holograms.reloadHolograms();

                            p.sendMessage("§a§lSukces: §7Zmodyfikowano §f" + line + " §7linijkę hologramu o ID §f" + id + "§7.");
                            return true;
                        }
                    }
                    p.sendMessage("§6§lInfo: §7Użyj §e/vholo aby uzyskać listę dostępnych komend.");
                    return true;
                } else {
                    p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
                    return true;
                }
            }
        }
        return false;
    }

}
