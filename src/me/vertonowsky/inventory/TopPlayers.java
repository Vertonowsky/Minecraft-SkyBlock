package me.vertonowsky.inventory;

import me.vertonowsky.api.API;
import me.vertonowsky.user.User;
import me.vertonowsky.user.UserUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TopPlayers {


    public static void openInventoryTopPlayers(Inventory inv, Player p, int strona) {
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        ArrayList<User> users = new ArrayList<>();
        users.addAll(UserUtils.getUsers());
        //sort users by highest money
        users.sort(Comparator.comparing(User::getTotalLevel).reversed());

        int totalPlayers = 1;
        for (User u : users) {
            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            if (totalPlayers <= 10) {
                item = new ItemStack(Material.SKULL_ITEM, 1, (short) 4);
            }

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§e#" + totalPlayers + " §c" + u.getName());
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("   §8§m---------------------------------  §7  §7  §7");
            lore.add("");
            lore.add("       §8§l>> §bRanga: " + API.getRankColor(u.getRank()) + u.getRank() + "              §8§l>> §bPoziom: §7" + u.getTotalLevel());
            lore.add("");
            double money = new BigDecimal(u.getMoney()).setScale(2, RoundingMode.HALF_UP).doubleValue();
            if (totalPlayers <= 10) {
                if (u.getMoney() > 0) lore.add("       §8§l>> §bSaldo: §a$" + u.getMoney());
                if (u.getMoney() < 0) lore.add("       §8§l>> §bSaldo: §c$" + u.getMoney());
            }
            lore.add("");
            lore.add("   §8§m---------------------------------  §7  §7  §7");
            lore.add("§8[Kliknij aby odwiedzić§8]");
            meta.setLore(lore);
            item.setItemMeta(meta);

            items.add(item);
            totalPlayers++;
        }



        int totalPages = InventoryAPI.addInventoryContent(inv, items, strona);

        Integer pageCurrent = strona + 1;
        InventoryAPI.addInventoryNavigation(inv, pageCurrent, totalPages);

    }
}
