package me.vertonowsky.essentials.listeners;

import me.vertonowsky.enums.QuestState;
import me.vertonowsky.events.PlayerQuestCompleteEvent;
import me.vertonowsky.recipes.StoneGenerator;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class OnItemCraft implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        Player p = (Player) e.getWhoClicked();
        User pUUID = User.get(p.getUniqueId());
        if (e.getRecipe().getResult().getType() == Material.WOOD_PICKAXE) {
            if (pUUID.getZadanie(1).equals(QuestState.W_TRAKCIE)) Bukkit.getServer().getPluginManager().callEvent(new PlayerQuestCompleteEvent(pUUID, 1, QuestState.WYKONANE));
        }

        if (e.getRecipe().getResult().getType() == Material.ENDER_STONE) {
            if (e.getRecipe().getResult().getItemMeta() != null && e.getRecipe().getResult().getItemMeta().getDisplayName() != null) {
                if (e.getRecipe().getResult().getItemMeta().getDisplayName().equals(StoneGenerator.stoneGeneratorItem.getItemMeta().getDisplayName())) {
                    if (pUUID.getZadanie(9).equals(QuestState.W_TRAKCIE)) Bukkit.getServer().getPluginManager().callEvent(new PlayerQuestCompleteEvent(pUUID, 9, QuestState.WYKONANE));
                }
            }
        }
    }
}
