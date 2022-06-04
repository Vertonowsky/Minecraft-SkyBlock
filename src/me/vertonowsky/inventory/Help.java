package me.vertonowsky.inventory;

import me.vertonowsky.mysql.MySQL;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Help {


    public static void openInventoryCategoryHelp(Inventory inv, Player p) {
        ItemStack item1 = new ItemStack(Material.PAPER, 1);
        ItemMeta meta1 = item1.getItemMeta();
        meta1.setDisplayName("§8§l>> §cJak rozpoczac grę?");
        ArrayList<String> lore1 = new ArrayList<String>();
        lore1.add("");
        lore1.add("§8§m----------------------------");
        lore1.add("");
        lore1.add("   §7Kliknij jeżeli nie wiesz jak");
        lore1.add("   §7zacząć rozgrywkę i co robić.");
        lore1.add("");
        lore1.add("§8§m----------------------------");

        meta1.setLore(lore1);
        item1.setItemMeta(meta1);


        ItemStack item2 = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta2 = item2.getItemMeta();
        meta2.setDisplayName("§8§l>> §cMam problem..");
        ArrayList<String> lore2 = new ArrayList<String>();
        lore2.add("");
        lore2.add("§8§m----------------------------");
        lore2.add("");
        lore2.add("   §7Kliknij jeżeli masz problem");
        lore2.add("   §7i nie wiesz jak sobie poradzić.");
        lore2.add("");
        lore2.add("§8§m----------------------------");

        meta2.setLore(lore2);
        item2.setItemMeta(meta2);

        inv.setItem(11, item1);
        inv.setItem(15, item2);

    }

    public static void openInventoryHelp(boolean help, Inventory inv, Player p, int strona) {
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();

        MySQL.openConnection();
        try {
            ResultSet rs = MySQL.conn.createStatement().executeQuery("SELECT * FROM `help` WHERE category='help' ORDER BY id ASC");
            if (!help) rs = MySQL.conn.createStatement().executeQuery("SELECT * FROM `help` WHERE category='tutorial' ORDER BY id ASC");

            int id = 1;
            while (rs.next()) {
                String title = rs.getString("title");
                String description = rs.getString("description");

                ItemStack itemi = new ItemStack(Material.PAPER, 1);
                if (!help) itemi = new ItemStack(Material.BARRIER, 1);


                ItemMeta meta = itemi.getItemMeta();
                meta.setDisplayName("§8§l#" + id + " §8[§c" + title + "§8]");
                ArrayList<String> Lore = new ArrayList<String>();
                Lore.add("");
                Lore.add("§8§m--------------------------------");
                Lore.add("");

                for (String s : description.split("α")) {
                    if (s != null) {
                        Lore.add("   " + s);
                    }
                }

                Lore.add("");
                Lore.add("§8§m--------------------------------");

                meta.setLore(Lore);
                itemi.setItemMeta(meta);
                items.add(itemi);
                id++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }


        int totalPages = InventoryAPI.addInventoryContent(inv, items, strona);

        Integer pageCurrent = strona + 1;
        InventoryAPI.addInventoryNavigation(inv, pageCurrent, totalPages);




        ItemStack item1 = new ItemStack(Material.BOOK, 1);
        ItemMeta meta1 = item1.getItemMeta();
        meta1.setDisplayName("§8[§bCofnij§8]");
        item1.setItemMeta(meta1);

        inv.setItem(47, item1);
        inv.setItem(51, item1);


        MySQL.closeConnection();
    }

}
