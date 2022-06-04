package me.vertonowsky.inventory;

import me.vertonowsky.enums.JobType;
import me.vertonowsky.user.User;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Roles {


    //Bukkit.getServer().getPluginManager().callEvent(new PlayerLevelUpEvent(tUUID, level));
    public static void openInventoryRolesInventory(Inventory inv, User u) {
        InventoryAPI.fillWithColouredGlass(inv, 27);


        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§8§l>>  §bGórnik");
        List<String> lore = new ArrayList<>();
        lore.add("");
        for (String s : getLevelMessage(u, JobType.MINER)) {
            lore.add(s);
        }
        lore.add("   §8[Kliknij aby zobaczyć szczegóły§8]");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        inv.setItem(10, item);


        ItemStack item2 = new ItemStack(Material.DIAMOND_AXE, 1);
        ItemMeta meta2 = item2.getItemMeta();
        meta2.setDisplayName("§8§l>>  §bDrwal");
        List<String> lore2 = new ArrayList<>();
        lore2.add("");
        for (String s : getLevelMessage(u, JobType.LUMBERJACK)) {
            lore2.add(s);
        }
        lore2.add("   §8[Kliknij aby zobaczyć szczegóły§8]");
        meta2.setLore(lore2);
        meta2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item2.setItemMeta(meta2);
        inv.setItem(12, item2);


        ItemStack item3 = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta meta3 = item3.getItemMeta();
        meta3.setDisplayName("§8§l>>  §bWojownik");
        List<String> lore3 = new ArrayList<>();
        lore3.add("");
        for (String s : getLevelMessage(u, JobType.WARRIOR)) {
            lore3.add(s);
        }
        lore3.add("   §8[Kliknij aby zobaczyć szczegóły§8]");
        meta3.setLore(lore3);
        meta3.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item3.setItemMeta(meta3);
        inv.setItem(14, item3);


        ItemStack item4 = new ItemStack(Material.FISHING_ROD, 1);
        ItemMeta meta4 = item4.getItemMeta();
        meta4.setDisplayName("§8§l>>  §bWędkarz");
        List<String> lore4 = new ArrayList<>();
        lore4.add("");
        for (String s : getLevelMessage(u, JobType.ANGLER)) {
            lore4.add(s);
        }
        lore4.add("   §8[Kliknij aby zobaczyć szczegóły§8]");
        meta4.setLore(lore4);
        meta4.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item4.setItemMeta(meta4);

        inv.setItem(16, item4);
    }








    private static List<String> getLevelMessage(User u, JobType jobType) {
        List<String> message = new ArrayList<>();
        String[] returnedData = calculatePlayerExperience(u, u.getExperience(jobType), u.getMaxExperience(jobType));



        message.add("   §8§m-------------------------------§7 §7 §7");
        message.add("");
        message.add("         §bPoziom: §7" + u.getLevel(jobType) + "        §bXP: §7" + u.getExperience(jobType) + "§8/§7" + u.getMaxExperience(jobType));
        message.add("");
        message.add("           " + returnedData[0] + "");
        message.add("                        §8(§7" + returnedData[1] + "%§8)");
        message.add("");
        message.add("   §8§m-------------------------------§7 §7 §7");

        return message;
    }



    public static String[] calculatePlayerExperience(User u, long expierence, long maxExperience) {
        String[] toReturn = new String[2];

        double poczatek1 = expierence;
        double max1 = maxExperience;
        double roznica1 = (poczatek1 / max1);
        double procent1 = (roznica1 * 100);
        roznica1 = (roznica1 *100) /7;
        String lvlWyglad1 = "";
        String lvlWyglad2 = "";
        for (long n = 1; n < 14;) {
            if (roznica1 >= 1) {
                if (lvlWyglad1.equals("")) {
                    lvlWyglad1 = "█";
                    roznica1--;
                } else {
                    lvlWyglad1 = lvlWyglad1 + "█";
                    roznica1--;
                }
            }

            if (roznica1 < 1) {
                if (lvlWyglad2.equals("")) {
                    lvlWyglad2 = "█";
                } else {
                    lvlWyglad2 = lvlWyglad2 + "█";
                }
            }
            n++;
        }

        toReturn[0] = "§a" + lvlWyglad1 + "§8" + lvlWyglad2;
        toReturn[1] = new BigDecimal(procent1 + "").setScale(2, RoundingMode.HALF_UP).doubleValue() + "";

        return toReturn;
    }

}
