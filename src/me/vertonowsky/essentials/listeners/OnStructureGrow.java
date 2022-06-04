package me.vertonowsky.essentials.listeners;

import me.vertonowsky.enums.QuestState;
import me.vertonowsky.events.PlayerQuestCompleteEvent;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;

public class OnStructureGrow implements Listener {

    @EventHandler
    public void onStructureGrow(StructureGrowEvent e) {
        if (e.isFromBonemeal()) {
            Player p = e.getPlayer();
            User pUUID = User.get(p.getUniqueId());
            if (pUUID.getTotalLevel() >= 5) {
                if (pUUID.getZadanie(3).equals(QuestState.W_TRAKCIE))
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerQuestCompleteEvent(pUUID, 3, QuestState.WYKONANE));
            }
        }
    }
}
