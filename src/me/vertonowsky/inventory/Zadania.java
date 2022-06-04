package me.vertonowsky.inventory;

import me.vertonowsky.enums.QuestState;
import me.vertonowsky.mysql.MySQL;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Zadania {

    public static BossBar bossBar = Bukkit.createBossBar("§8§l>> §b§lWykonano zadanie! §8§l<<".toUpperCase(), BarColor.GREEN, BarStyle.SEGMENTED_20);
    public static int allQuests = 16;


    public static void openInventoryZadania(Inventory inv, Player p, int strona) {
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        User pUUID = User.get(p.getName());

        MySQL.openConnection();

        try {
            ResultSet rs;
            rs = MySQL.conn.createStatement().executeQuery("SELECT * FROM `quests_all` ORDER BY level_min ASC");
            while (rs.next()){
                Integer levelMin = rs.getInt("level_min");
                String title = rs.getString("title");
                String description = rs.getString("description");
                Integer id = rs.getInt("id");

                String status = "§cWystąpił błąd";
                ItemStack itemi = new ItemStack(Material.BARRIER, 1);

                if (pUUID.getZadanie(id).equals(QuestState.ZABLOKOWANE)) {
                    itemi = new ItemStack(Material.BARRIER, 1);
                    status = "§cZablokowane..";
                }
                if (pUUID.getZadanie(id).equals(QuestState.W_TRAKCIE)) {
                    itemi = new ItemStack(Material.BOOK, 1);
                    status = "§eW trakcie..";
                }
                if (pUUID.getZadanie(id).equals(QuestState.WYKONANE)) {
                    itemi = new ItemStack(Material.BOOK_AND_QUILL, 1);
                    status = "§aWykonane..";
                }
                if (pUUID.getZadanie(id).equals(QuestState.ZAKONCZONE)) {
                    itemi = new ItemStack(Material.SKULL_ITEM, 1, (short) 5);
                    status = "§aZakończone..";
                }

                ItemMeta meta = itemi.getItemMeta();
                meta.setDisplayName("§8§l#" + id);
                ArrayList<String> Lore = new ArrayList<String>();
                Lore.add("§8§l>> §c" + title + " §8[" + status + "§8]");
                Lore.add("");
                if (pUUID.getTotalLevel() >= levelMin) {
                    Lore.add("§a✔ §7Lv. Min: §f" + levelMin);
                } else if (pUUID.getTotalLevel() < levelMin) {
                    Lore.add("§c✘ §7Lv. Min: §f" + levelMin);
                }
                Lore.add("");
                Lore.add("§8§m----------------------------");
                Lore.add("");

                for (String s : description.split("α")) {
                    if (s != null) {
                        Lore.add("   " + s);
                    }
                }

                Lore.add("");
                Lore.add("§8§m----------------------------");

                meta.setLore(Lore);
                itemi.setItemMeta(meta);
                items.add(itemi);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }


        int totalPages = InventoryAPI.addInventoryContent(inv, items, strona);

        Integer pageCurrent = strona + 1;
        InventoryAPI.addInventoryNavigation(inv, pageCurrent, totalPages);





        int questsDone = 0;


        for (int i = 1; i <= allQuests-3; i++) {
            if (pUUID.getZadanie(i).equals(QuestState.ZAKONCZONE)) questsDone ++;
        }


        String[] returnedData = Roles.calculatePlayerExperience(pUUID, questsDone, allQuests-3);
        ItemStack item6 = new ItemStack(Material.STAINED_CLAY, 1, (short) 5);
        ItemMeta meta6 = item6.getItemMeta();
        meta6.setDisplayName("§8§l>> §bWykonane zadania: §8[§7" + questsDone + "§8/§7" + (allQuests-3) + "§8]");
        List<String> message = new ArrayList<>();
        message.add("");
        message.add("   §8§m-------------------------------§7 §7 §7");
        message.add("");
        message.add("           " + returnedData[0] + "");
        message.add("                        §8(§7" + returnedData[1] + "%§8)");
        message.add("");
        message.add("   §8§m-------------------------------§7 §7 §7");
        meta6.setLore(message);
        item6.setItemMeta(meta6);

        inv.setItem(inv.getSize()-5, item6);



        MySQL.closeConnection();
    }

}
