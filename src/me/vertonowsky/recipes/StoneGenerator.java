package me.vertonowsky.recipes;

import me.vertonowsky.main.Main;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class StoneGenerator {

    public static ItemStack stoneGeneratorItem;


    public void recipeStoneGenerator() {
        stoneGeneratorItem = new ItemStack(Material.ENDER_STONE, 1);
        ItemMeta meta = stoneGeneratorItem.getItemMeta();

        meta.setDisplayName("§8§m----------§6 Stoniarka §8§m----------");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("§8➢ §7Nieskończony generator stone.");
        lore.add("§8➢ §7Stone pojawia sią nad stoniarką.");
        meta.setLore(lore);
        stoneGeneratorItem.setItemMeta(meta);
        ShapedRecipe r = new ShapedRecipe(stoneGeneratorItem);

        r.shape("#%#", "#$#", "###");
        r.setIngredient('#', Material.STONE);
        r.setIngredient('%', Material.DIAMOND);
        r.setIngredient('$', Material.PISTON_BASE);

        Main.getInst().getServer().addRecipe(r);

    }


}
