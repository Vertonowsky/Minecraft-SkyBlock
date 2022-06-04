package me.vertonowsky.essentials.listeners;

import me.vertonowsky.enums.QuestState;
import me.vertonowsky.events.PlayerQuestCompleteEvent;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;

public class OnEntityBreed implements Listener {

    @EventHandler
    public void onEntityBreed(EntityBreedEvent e) {
        if (e.getBreeder() instanceof Player) {
            Player p = (Player) e.getBreeder();
            User pUUID = User.get(p.getUniqueId());
            if (pUUID.getTotalLevel() >= 37) {
                if (pUUID.getZadanie(10).equals(QuestState.W_TRAKCIE))
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerQuestCompleteEvent(pUUID, 10, QuestState.WYKONANE));
            }
        }
    }
}
