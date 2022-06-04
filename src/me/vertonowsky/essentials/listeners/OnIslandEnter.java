package me.vertonowsky.essentials.listeners;

import com.wasteofplastic.askyblock.events.IslandEnterEvent;
import me.vertonowsky.api.API;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnIslandEnter implements Listener {

    @EventHandler
    public void onIslandEnter(IslandEnterEvent e) {
        Player p = Bukkit.getPlayer(e.getPlayer());

        if (e.getPlayer().equals(e.getIslandOwner())) {
            API.sendActionBar(p, "§6Jesteś na własnej wyspie.");
            return;
        }
        else if (!e.getPlayer().equals(e.getIslandOwner())) {
            if (e.getIslandOwner() != null) {
                if (User.get(e.getIslandOwner()) != null) {
                    API.sendActionBar(p, "§6Jesteś na wyspie §e" + User.get(e.getIslandOwner()).getName());
                    return;
                }
            }
        }
    }
}
