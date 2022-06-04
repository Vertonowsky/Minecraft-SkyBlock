package me.vertonowsky.essentials.listeners;

import me.vertonowsky.enums.QuestState;
import me.vertonowsky.events.PlayerQuestCompleteEvent;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class OnItemEnchant implements Listener {

    @EventHandler
    public void onItemEnchant(EnchantItemEvent e) {
        Player p = (Player) e.getEnchanter();
        User pUUID = User.get(p.getUniqueId());
        if (pUUID.getTotalLevel() >= 30) {
            if (pUUID.getZadanie(13).equals(QuestState.W_TRAKCIE))
                Bukkit.getServer().getPluginManager().callEvent(new PlayerQuestCompleteEvent(pUUID, 13, QuestState.WYKONANE));
        }
    }
}
