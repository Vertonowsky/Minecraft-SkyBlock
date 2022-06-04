package me.vertonowsky.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InventoryAPI {





    public static ItemStack createItem(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }



    public static int getSpaceForItemInInventory(Player p, ItemStack itemStack, boolean exactlySame) {
        int amount = 0;
        for (int i = 0; i < 36; i++) {
            if (p.getInventory().getItem(i) == null) {
                amount += itemStack.getMaxStackSize();
                continue;
            }

            if (p.getInventory().getItem(i) != null) {
                if (exactlySame) {
                    if (p.getInventory().getItem(i).isSimilar(itemStack)) {
                        amount += itemStack.getMaxStackSize() - p.getInventory().getItem(i).getAmount();
                    }
                }

                if (!exactlySame) {
                    if (p.getInventory().getItem(i).getType().equals(itemStack.getType()) && p.getInventory().getItem(i).getData().equals(itemStack.getData())) {
                        amount += itemStack.getMaxStackSize() - p.getInventory().getItem(i).getAmount();
                    }
                }
            }
        }
        return amount;
    }


    public static int getItemAmountInInventory(Player p, ItemStack itemStack, boolean exactlySame) {
        int amount = 0;
        for (int i = 0; i < 36; i++) {
            if (p.getInventory().getItem(i) != null) {
                if (exactlySame) {
                    if (p.getInventory().getItem(i).isSimilar(itemStack)) {
                        amount += p.getInventory().getItem(i).getAmount();
                    }
                }

                if (!exactlySame) {
                    if (p.getInventory().getItem(i).getType().equals(itemStack.getType()) && p.getInventory().getItem(i).getData().equals(itemStack.getData())) {
                        amount += p.getInventory().getItem(i).getAmount();
                    }
                }
            }
        }

        return amount;
    }


    public static void removeItemFromInventory(Player p, ItemStack itemStack, int amount) {
        for (int i = 0; i < 36; i++) {
            if (amount > 0) {
                if (p.getInventory().getItem(i) != null) {
                    if (p.getInventory().getItem(i).isSimilar(itemStack)) {
                        int itemAmount = p.getInventory().getItem(i).getAmount();

                        if (amount < itemAmount) {
                            p.getInventory().getItem(i).setAmount(itemAmount-amount);
                            break;
                        }
                        amount -= itemAmount;
                        p.getInventory().setItem(i, null);
                    }
                }
            }
        }
    }


    public static int addItemToInventory(Player p, ItemStack itemStack, int amount) {
        int emptySpace = getSpaceForItemInInventory(p, itemStack, false);

        int itemsToAdd = 0;

        if (emptySpace >= amount) itemsToAdd = amount;
        if (emptySpace < amount) itemsToAdd = emptySpace;

        for (int i = 0; i < 36; i++) {
            if (itemsToAdd >= itemStack.getMaxStackSize()) {
                itemStack.setAmount(itemStack.getMaxStackSize());
                itemsToAdd -= itemStack.getMaxStackSize();
                p.getInventory().addItem(itemStack);
                continue;
            } else {
                itemStack.setAmount(itemsToAdd);
                p.getInventory().addItem(itemStack);
                break;
            }
        }

        //How much items still left to give
        return amount - emptySpace;
    }





    public static int addInventoryContent(Inventory inv, List<ItemStack> items, int strona) {
        int slot = 0;
        int pageContentCount = inv.getSize() - 9;
        int liczba = strona * pageContentCount;
        int totalPages = 1;

        if (items.size() >= 1) {
            for (int fullSize = items.size(); fullSize > pageContentCount;) {
                totalPages++;
                fullSize = fullSize - pageContentCount;

            }
            if (items.size() > liczba) {
                for (ItemStack item : items) {
                    if (liczba >= 1) {
                        liczba--;
                    } else if (liczba == 0) {

                        if (slot < pageContentCount) {

                            inv.setItem(slot, item);

                            slot++;
                        }
                    }

                }

            }
        }

        if (items.size() <= liczba || items.size() == 0) {
            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 2);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§8§m----[§c§l BLAD §8§m]----");
            ArrayList<String> Lore = new ArrayList<String>();
            Lore.add("§8➢ §7Brak wyników.");
            meta.setLore(Lore);
            item.setItemMeta(meta);


            int center = 4;
            if (inv.getSize() == 27 || inv.getSize() == 36) center = 13;
            if (inv.getSize() == 45 || inv.getSize() == 54) center = 22;

            inv.setItem(center, item);

        }

        return totalPages;
    }





    public static void addInventoryNavigation(Inventory inv, int currentPage, int totalPages) {
        ItemStack itemPreviousPage = new ItemStack(Material.SKULL_ITEM, 1, (short) 1);
        ItemMeta metaPreviousPage = itemPreviousPage.getItemMeta();
        metaPreviousPage.setDisplayName("§8§m---§b Poprzednia strona §7[" + (currentPage-1) + "] §8§m---");
        itemPreviousPage.setItemMeta(metaPreviousPage);

        ItemStack itemNextPage = new ItemStack(Material.SKULL_ITEM, 1, (short) 0);
        ItemMeta metaNextPage = itemNextPage.getItemMeta();
        metaNextPage.setDisplayName("§8§m---§b Następna strona §7[" + (currentPage+1) + "] §8§m---");
        itemNextPage.setItemMeta(metaNextPage);

        ItemStack item4 = new ItemStack(Material.STAINED_CLAY, 1, (short) 5);
        ItemMeta meta4 = item4.getItemMeta();
        meta4.setDisplayName("§8§m---§6 Strona §7[" + currentPage + "/" + totalPages + "] §8§m---");
        item4.setItemMeta(meta4);

        ItemStack item5 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        ItemMeta meta5 = item5.getItemMeta();
        meta5.setDisplayName("§a");
        item5.setItemMeta(meta5);


        int inventorySize = inv.getSize();


        for (int i = inventorySize -9; i < inventorySize; i++) {
            inv.setItem(i, item5);
        }

        inv.setItem(inventorySize -5, item4);

        if (currentPage < totalPages) {
            inv.setItem(inventorySize-1, itemNextPage);
        }

        if (currentPage > 1) {
            inv.setItem(inventorySize-9, itemPreviousPage);
        }


    }



    public static void fillWithColouredGlass(Inventory inv, int invSize) {
        Random random = new Random();
        int randomInt = 1;
        for (int i = 0; i < invSize; i++) {
            randomInt = random.nextInt(14);
            for (int n = 0; n <= 50; n++) {
                if (!(randomInt == 0 || randomInt == 7 || randomInt == 8 || randomInt == 15)) {
                    break;
                }
                randomInt = random.nextInt(14);
            }

            inv.setItem(i, InventoryAPI.createItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) randomInt), "§7"));
        }
    }



    public static ItemStack getRandomGlassColor() {
        Random random = new Random();
        int randomInt = random.nextInt(14);

        return new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) randomInt);
    }



}
