package me.vertonowsky.essentials.commands;

import me.vertonowsky.api.API;
import me.vertonowsky.inventory.Market;
import me.vertonowsky.main.Main;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CommandMarket implements CommandExecutor {

    private final File marketFolder = new File(Main.getInst().getDataFolder(), "market");

    public static int maxItemsInMarket = 100;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("market")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                User pUUID = User.get(p.getUniqueId());
                if (p.hasPermission("vert.market")) {
                    if (args.length == 0) {
                        Inventory inv = Bukkit.createInventory(null, 54, "§8[Market online]");
                        p.openInventory(inv);
                        Market.openInventoryGlobalMarket(inv, 0);
                        return true;
                    }
                    if (args.length == 1) {
                        if (User.get(args[0]) != null) {
                            User tUUID = User.get(args[0]);
                            Inventory inv = Bukkit.createInventory(null, 54, "§8Market " + tUUID.getName());
                            p.openInventory(inv);
                            Market.openInventoryPlayerMarket(inv, tUUID, 0, pUUID);
                            return true;
                        } else {
                            p.sendMessage("§c§lBłąd: §7Gracz o takiej nazwie nie istnieje!");
                            return true;
                        }
                    } else {
                        p.sendMessage("§6§lInfo: §7Użyj §e/market§7.");
                        return true;
                    }
                } else {
                    p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
                    return true;
                }
            } else {
                sender.sendMessage("§c§lBłąd: §7Ta komenda nie jest dostępna dla konsoli.");
                return true;
            }
        }

        if (cmd.getName().equalsIgnoreCase("wystaw")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("vert.market")) {
                    if (args.length == 1) {
                        if (API.isDouble(args[0])) {
                            String price1 = String.format("%.2f", Double.parseDouble(args[0])).replaceAll(",", ".");
                            Double price = Double.parseDouble(price1);

                            if (!p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                                Material type = p.getInventory().getItemInMainHand().getType();
                                if (type.equals(Material.POTION) || type.equals(Material.LINGERING_POTION) || type.equals(Material.SPLASH_POTION) || type.equals(Material.MONSTER_EGG) || type.equals(Material.MONSTER_EGGS)) {
                                    p.sendMessage("§c§lBłąd: §7W markecie nie można sprzedawać mikstur oraz spawnerów!");
                                    return true;
                                }
                                File fi = new File(marketFolder, p.getName() + "@" + p.getUniqueId().toString() + ".yml");
                                YamlConfiguration yaml = YamlConfiguration.loadConfiguration(fi);

                                if (price >= 0 && price <= 250000) {
                                    ArrayList<Long> WszystkieKlucze = new ArrayList<Long>();
                                    for (String s : yaml.getKeys(false)) {
                                        Long slot = Long.parseLong(s);
                                        WszystkieKlucze.add(slot);
                                    }

                                    if (yaml.getKeys(false).size() >= maxItemsInMarket) {
                                        p.sendMessage("§c§lBłąd: §7Nie możesz wystawić więcej przedmiotów!");
                                        return true;
                                    }

                                    if (WszystkieKlucze.contains(System.currentTimeMillis())) {
                                        p.sendMessage("§c§lBłąd: §7Odczekaj chwilę przed dodaniem kolejnego przedmiotu!");
                                        return true;
                                    }


                                    ItemStack is = p.getInventory().getItemInMainHand();
                                    Market.ItemToYaml(System.currentTimeMillis(), yaml, is, price);

                                    try {
                                        yaml.save(fi);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    p.sendMessage("§a§lSukces: §7Wystawiono przedmiot za §8[§a$" + price + "§8]§7.");
                                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                                    p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                    return true;
                                } else {
                                    p.sendMessage("§c§lBłąd: §7Podana kwota jest zbyt mała lub zbyt duża!");
                                    return true;
                                }
                            } else {
                                p.sendMessage("§c§lBłąd: §7Nie trzymasz żadnego przedmiotu w ręce.");
                                return true;
                            }
                        } else {
                            p.sendMessage("§6§lInfo: §7Użyj §e/wystaw <cena>§7.");
                            return true;
                        }

                    } else {
                        p.sendMessage("§6§lInfo: §7Użyj §e/wystaw <cena>§7.");
                        return true;
                    }

                } else {
                    p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
                    return true;
                }
            } else {
                sender.sendMessage("§c§lBłąd: §7Ta komenda nie jest dostępna dla konsoli.");
                return true;
            }
        }
        return false;
    }

}
