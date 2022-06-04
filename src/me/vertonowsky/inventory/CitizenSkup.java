package me.vertonowsky.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CitizenSkup {


    public static void openInventorySkup(Inventory inv, Player p, int strona) {
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();

        items.add(addShopItem(false, new ItemStack(Material.COBBLESTONE, 1), "§8§m------§b Bruk §8§m------", 0.10));
        items.add(addShopItem(false, new ItemStack(Material.STONE, 1), "§8§m------§b Kamień §8§m------", 0.13));

        items.add(addShopItem(false, new ItemStack(Material.LOG, 1), "§8§m------§b Dębowe drewno §8§m------", 0.75));
        items.add(addShopItem(false, new ItemStack(Material.LOG, 1, (short) 1), "§8§m------§b Świerkowe drewno §8§m------", 0.75));
        items.add(addShopItem(false, new ItemStack(Material.LOG, 1, (short) 2), "§8§m------§b Brzozowe drewno §8§m------", 0.75));
        items.add(addShopItem(false, new ItemStack(Material.LOG, 1, (short) 3), "§8§m------§b Dżunglowe drewno §8§m------", 0.75));
        items.add(addShopItem(false, new ItemStack(Material.LOG_2, 1), "§8§m------§b Akacjowe drewno §8§m------", 0.75));
        items.add(addShopItem(false, new ItemStack(Material.LOG_2, 1, (short) 1), "§8§m------§b Ciemne dębowe drewno §8§m------", 0.75));

        items.add(addShopItem(false, new ItemStack(Material.CACTUS, 1), "§8§m------§b Kaktus §8§m------", 0.15));
        items.add(addShopItem(false, new ItemStack(Material.WATER_LILY, 1), "§8§m------§b Lilia wodna §8§m------", 0.15));
        items.add(addShopItem(false, new ItemStack(Material.SUGAR_CANE, 1), "§8§m------§b Trzcina cukrowa §8§m------", 0.15));
        items.add(addShopItem(false, new ItemStack(Material.VINE, 1), "§8§m------§b Pnącza §8§m------", 0.20));

        items.add(addShopItem(false, new ItemStack(Material.ENDER_PEARL, 1), "§8§m------§b Perła kresu §8§m------", 0.32));
        items.add(addShopItem(false, new ItemStack(Material.SPIDER_EYE, 1), "§8§m------§b Oko pająka §8§m------", 0.32));
        items.add(addShopItem(false, new ItemStack(Material.BONE, 1), "§8§m------§b Kości §8§m------", 0.32));
        items.add(addShopItem(false, new ItemStack(Material.SULPHUR, 1), "§8§m------§b Proch §8§m------", 0.30));
        items.add(addShopItem(false, new ItemStack(Material.ROTTEN_FLESH, 1), "§8§m------§b Zgniłe mięso §8§m------", 0.20));
        items.add(addShopItem(false, new ItemStack(Material.STRING, 1), "§8§m------§b Nić §8§m------", 0.25));
        items.add(addShopItem(false, new ItemStack(Material.ARROW, 1), "§8§m------§b Strzały §8§m------", 0.12));
        items.add(addShopItem(false, new ItemStack(Material.NETHER_STALK, 1), "§8§m------§b Netherowa brodawka §8§m------", 0.4));

        items.add(addShopItem(false, new ItemStack(Material.SEEDS, 1), "§8§m------§b Nasiona pszenicy §8§m------", 0.20));
        items.add(addShopItem(false, new ItemStack(Material.CARROT_ITEM, 1), "§8§m------§b Marechwka §8§m------", 0.20));
        items.add(addShopItem(false, new ItemStack(Material.POTATO_ITEM, 1), "§8§m------§b Ziemniak §8§m------", 0.20));
        items.add(addShopItem(false, new ItemStack(Material.WHEAT, 1), "§8§m------§b Pszenica §8§m------", 0.32));
        items.add(addShopItem(false, new ItemStack(Material.GRILLED_PORK, 1), "§8§m------§b Pieczony schab §8§m------", 0.50));
        items.add(addShopItem(false, new ItemStack(Material.LEATHER, 1), "§8§m------§b Skóra §8§m------", 3));
        items.add(addShopItem(false, new ItemStack(Material.EGG, 1), "§8§m------§b Jajko §8§m------", 0.32));
        items.add(addShopItem(false, new ItemStack(Material.INK_SACK, 1, (short) 3), "§8§m------§b Kakao §8§m------", 0.20));
        items.add(addShopItem(false, new ItemStack(Material.MELON_BLOCK, 1), "§8§m------§b Melon §8§m------", 0.32));
        items.add(addShopItem(false, new ItemStack(Material.PUMPKIN, 1), "§8§m------§b Dynia §8§m------", 0.32));

        items.add(addShopItem(false, new ItemStack(Material.COAL, 1), "§8§m------§b Węgiel §8§m------", 0.4));
        items.add(addShopItem(false, new ItemStack(Material.REDSTONE, 1), "§8§m------§b Redstone §8§m------", 0.6));
        items.add(addShopItem(false, new ItemStack(Material.QUARTZ, 1), "§8§m------§b Kwarc §8§m------", 1));
        items.add(addShopItem(false, new ItemStack(Material.GOLD_INGOT, 1), "§8§m------§b Złoto §8§m------", 2));
        items.add(addShopItem(false, new ItemStack(Material.IRON_INGOT, 1), "§8§m------§b Żelazo §8§m------", 3));
        items.add(addShopItem(false, new ItemStack(Material.INK_SACK, 1, (short) 4), "§8§m------§b Lazuryt §8§m------", 2));
        items.add(addShopItem(false, new ItemStack(Material.DIAMOND, 1), "§8§m------§b Diament §8§m------", 50));


        int totalPages = InventoryAPI.addInventoryContent(inv, items, strona);

        Integer pageCurrent = strona + 1;
        InventoryAPI.addInventoryNavigation(inv, pageCurrent, totalPages);

    }





    public static void openInventoryFinalPurchase(Inventory inv, Player p, ItemStack item, boolean sell) {
        InventoryAPI.fillWithColouredGlass(inv, 9);
        inv.setItem(4, item);


        for (int i = 9; i < 45; i++) {
            inv.setItem(i, InventoryAPI.createItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7), "§8"));
        }


        int allAmount = 0;
        if (sell) allAmount = InventoryAPI.getItemAmountInInventory(p, item, false);
        if (!sell) allAmount = InventoryAPI.getSpaceForItemInInventory(p, item, false);

        inv.setItem(11, InventoryAPI.createItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 5), "§8§m--§a [+1] §8§m--"));
        inv.setItem(20, InventoryAPI.createItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 5), "§8§m--§a [+16] §8§m--"));
        inv.setItem(29, InventoryAPI.createItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 5), "§8§m--§a [+64] §8§m--"));
        if (allAmount > 0) inv.setItem(38, InventoryAPI.createItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 5), "§8§m--§a WSZYSTKO [+" + allAmount + "] §8§m--"));

        inv.setItem(15, InventoryAPI.createItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), "§8§m--§c [-1] §8§m--"));
        inv.setItem(24, InventoryAPI.createItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), "§8§m--§c [-16] §8§m--"));
        inv.setItem(33, InventoryAPI.createItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), "§8§m--§c [-64] §8§m--"));
        if (allAmount > 0)  inv.setItem(42, InventoryAPI.createItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), "§8§m--§c WSZYSTKO [-" + allAmount + "] §8§m--"));




        ItemStack itemSell = new ItemStack(Material.SKULL_ITEM, 1, (short) 5);
        ItemMeta metaSell = itemSell.getItemMeta();
        if (sell) metaSell.setDisplayName("§8§m----[§a§l SPRZEDAJ §8§m]----");
        if (!sell) metaSell.setDisplayName("§8§m----[§a§l KUP §8§m]----");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§8➢ §6Ilość: §70");
        lore.add("§8➢ §6Wartość: §7$0");
        lore.add("");
        metaSell.setLore(lore);
        itemSell.setItemMeta(metaSell);

        inv.setItem(22, itemSell);



        ItemStack itemCancel = new ItemStack(Material.SKULL_ITEM, 1, (short) 2);
        ItemMeta metaCancel = itemCancel.getItemMeta();
        metaCancel.setDisplayName("§8§m----[§c§l ANULUJ §8§m]----");
        itemCancel.setItemMeta(metaCancel);

        inv.setItem(31, itemCancel);
    }























    public static ItemStack addShopItem(boolean kup, ItemStack item, String name, double price) {
        InventoryAPI.createItem(item, name);

        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        double priceRounded = new BigDecimal(price + "").setScale(2, RoundingMode.HALF_UP).doubleValue();

        lore.add("");
        lore.add("§8➢  §61 szt.  §8- §7$" + priceRounded);
        lore.add("§8➢ §616 szt.  §8- §7$" + (priceRounded*16));
        lore.add("§8➢ §664 szt.  §8- §7$" + (priceRounded*64));
        lore.add("");
        if (!kup) lore.add("§8[Kliknij aby sprzedać]");
        if (kup) lore.add("§8[Kliknij aby kupić]");
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;

    }

}
