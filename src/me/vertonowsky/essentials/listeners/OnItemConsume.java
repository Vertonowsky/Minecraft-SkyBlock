package me.vertonowsky.essentials.listeners;

import me.vertonowsky.enums.QuestState;
import me.vertonowsky.events.PlayerQuestCompleteEvent;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class OnItemConsume implements Listener {

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent e) {
        if (e.getItem().getType().equals(Material.COOKED_BEEF)) {
            User pUUID = User.get(e.getPlayer().getUniqueId());
            if (pUUID.getTotalLevel() >= 10) {
                if (pUUID.getZadanie(4).equals(QuestState.W_TRAKCIE)) Bukkit.getServer().getPluginManager().callEvent(new PlayerQuestCompleteEvent(pUUID, 4, QuestState.WYKONANE));
            }
        }
    }
}
