package me.vertonowsky.inventory;

import me.vertonowsky.user.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CitizenSklep {


    public static void openInventorySklep(Inventory inv, User u, String name) {

        if (name.equalsIgnoreCase("§6§lBloki")) {
            inv.setItem(0, CitizenSkup.addShopItem(true, new ItemStack(Material.DIRT, 1), "§8§m------§b Ziemia §8§m------", 5));
            inv.setItem(1, CitizenSkup.addShopItem(true, new ItemStack(Material.DIRT, 1, (short) 2), "§8§m------§b Podzol §8§m------", 6));
            inv.setItem(2, CitizenSkup.addShopItem(true, new ItemStack(Material.GRASS, 1), "§8§m------§b Ziemia z trawą §8§m------", 7));
            inv.setItem(3, CitizenSkup.addShopItem(true, new ItemStack(Material.SAND, 1), "§8§m------§b Piasek §8§m------", 5));
            inv.setItem(4, CitizenSkup.addShopItem(true, new ItemStack(Material.SAND, 1, (short) 1), "§8§m------§b Czerwony piasek §8§m------", 7));
            inv.setItem(5, CitizenSkup.addShopItem(true, new ItemStack(Material.GLASS, 1), "§8§m------§b Szkło §8§m------", 7));
            inv.setItem(6, CitizenSkup.addShopItem(true, new ItemStack(Material.OBSIDIAN, 1), "§8§m------§b Obsydian §8§m------", 35));
            inv.setItem(7, CitizenSkup.addShopItem(true, new ItemStack(Material.GLOWSTONE, 1), "§8§m------§b Jasnogłaz §8§m------", 35));

            inv.setItem(18, CitizenSkup.addShopItem(true, new ItemStack(Material.LOG, 1), "§8§m------§b Dębowe drewno §8§m------", 1.5));
            inv.setItem(19, CitizenSkup.addShopItem(true, new ItemStack(Material.LOG, 1, (short) 1), "§8§m------§b Świerkowe drewno §8§m------", 1.5));
            inv.setItem(20, CitizenSkup.addShopItem(true, new ItemStack(Material.LOG, 1, (short) 2), "§8§m------§b Brzozowe drewno §8§m------", 1.5));
            inv.setItem(21, CitizenSkup.addShopItem(true, new ItemStack(Material.LOG, 1, (short) 3), "§8§m------§b Dżunglowe drewno §8§m------", 1.5));
            inv.setItem(22, CitizenSkup.addShopItem(true, new ItemStack(Material.LOG_2, 1), "§8§m------§b Akacjowe drewno §8§m------", 1.5));
            inv.setItem(23, CitizenSkup.addShopItem(true, new ItemStack(Material.LOG_2, 1, (short) 1), "§8§m------§b Ciemne dębowe drewno §8§m------", 1.5));

            inv.setItem(36, CitizenSkup.addShopItem(true, new ItemStack(Material.COBBLESTONE, 1), "§8§m------§b Bruk §8§m------", 0.14));
            inv.setItem(37, CitizenSkup.addShopItem(true, new ItemStack(Material.STONE, 1), "§8§m------§b Kamień §8§m------", 0.16));
            inv.setItem(38, CitizenSkup.addShopItem(true, new ItemStack(Material.STONE, 1, (short) 1), "§8§m------§b Granit §8§m------", 0.16));
            inv.setItem(39, CitizenSkup.addShopItem(true, new ItemStack(Material.STONE, 1, (short) 3), "§8§m------§b Dioryt §8§m------", 0.16));
            inv.setItem(40, CitizenSkup.addShopItem(true, new ItemStack(Material.STONE, 1, (short) 5), "§8§m------§b Andezyt §8§m------", 0.16));

        }

        if (name.equalsIgnoreCase("§6§lSpawnery")) {
            inv.setItem(10, CitizenSkup.addShopItem(true, new ItemStack(Material.MONSTER_EGG, 1, (short) 93), "§8§m------§b Spawn kura §8§m------", 400));
            inv.setItem(28, CitizenSkup.addShopItem(true, new ItemStack(Material.MONSTER_EGG, 1, (short) 91), "§8§m------§b Spawn owca §8§m------", 400));
            inv.setItem(12, CitizenSkup.addShopItem(true, new ItemStack(Material.MONSTER_EGG, 1, (short) 90), "§8§m------§b Spawn świnia §8§m------", 400));
            inv.setItem(30, CitizenSkup.addShopItem(true, new ItemStack(Material.MONSTER_EGG, 1, (short) 92), "§8§m------§b Spawn krowa §8§m------", 400));
            inv.setItem(14, CitizenSkup.addShopItem(true, new ItemStack(Material.MONSTER_EGG, 1, (short) 96), "§8§m------§b Spawn krowa grzybowa §8§m------", 500));
            inv.setItem(32, CitizenSkup.addShopItem(true, new ItemStack(Material.MONSTER_EGG, 1, (short) 100), "§8§m------§b Spawn koń §8§m------", 1000));
            inv.setItem(16, CitizenSkup.addShopItem(true, new ItemStack(Material.MONSTER_EGG, 1, (short) 95), "§8§m------§b Spawn wilk §8§m------", 1000));
        }


        if (name.equalsIgnoreCase("§6§lInne")) {
            inv.setItem(0, CitizenSkup.addShopItem(true, new ItemStack(Material.SAPLING, 1), "§8§m------§b Sadzonka dębu §8§m------", 5));
            inv.setItem(1, CitizenSkup.addShopItem(true, new ItemStack(Material.SAPLING, 1, (short) 1), "§8§m------§b Sadzonka świerku §8§m------", 5));
            inv.setItem(2, CitizenSkup.addShopItem(true, new ItemStack(Material.SAPLING, 1, (short) 2), "§8§m------§b Sadzonka brzozy §8§m------", 5));
            inv.setItem(3, CitizenSkup.addShopItem(true, new ItemStack(Material.SAPLING, 1, (short) 3), "§8§m------§b Sadzonka dżunglowa §8§m------", 30));
            inv.setItem(4, CitizenSkup.addShopItem(true, new ItemStack(Material.SAPLING, 1, (short) 4), "§8§m------§b Sadzonka akacjowa §8§m------", 5));
            inv.setItem(5, CitizenSkup.addShopItem(true, new ItemStack(Material.SAPLING, 1, (short) 5), "§8§m------§b Sadzonka ciemnego dębu §8§m------", 5));

            inv.setItem(18, CitizenSkup.addShopItem(true, new ItemStack(Material.BUCKET, 1), "§8§m------§b Wiadro §8§m------", 25));
            inv.setItem(19, CitizenSkup.addShopItem(true, new ItemStack(Material.WATER_BUCKET, 1), "§8§m------§b Wiadro z wodą §8§m------", 35));
            inv.setItem(20, CitizenSkup.addShopItem(true, new ItemStack(Material.LAVA_BUCKET, 1), "§8§m------§b Wiadro z lawą §8§m------", 35));
            inv.setItem(24, CitizenSkup.addShopItem(true, new ItemStack(Material.REDSTONE, 1), "§8§m------§b Czerwony proszek §8§m------", 5));
            inv.setItem(25, CitizenSkup.addShopItem(true, new ItemStack(Material.IRON_INGOT, 1), "§8§m------§b żelazo §8§m------", 10));
            inv.setItem(26, CitizenSkup.addShopItem(true, new ItemStack(Material.DIAMOND, 1), "§8§m------§b Diament §8§m------", 100));
            inv.setItem(28, CitizenSkup.addShopItem(true, new ItemStack(Material.BLAZE_ROD, 1), "§8§m------§b Płomienna różdżka §8§m------", 200));
            inv.setItem(34, CitizenSkup.addShopItem(true, new ItemStack(Material.SLIME_BALL, 1), "§8§m------§b Kula szlamu §8§m------", 30));

            inv.setItem(40, CitizenSkup.addShopItem(true, new ItemStack(Material.NETHER_STAR, 1), "§8§m------§b Netherowa gwiazda §8§m------", 3000));
        }

    }
}
