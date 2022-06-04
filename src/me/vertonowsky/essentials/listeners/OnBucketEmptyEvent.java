package me.vertonowsky.essentials.listeners;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.Island;
import me.vertonowsky.enums.QuestState;
import me.vertonowsky.events.PlayerQuestCompleteEvent;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OnBucketEmptyEvent implements Listener {


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBucketEmpty(PlayerBucketEmptyEvent e) {
        Player p = e.getPlayer();
        if (e.getBucket() == null) {
            return;
        }
        /*if (e.getBucket().equals(Material.WATER_BUCKET)) {
            Location loc = p.getLocation();
            if (!loc.getWorld().getBiome(loc.getBlockX(), loc.getBlockZ()).equals(Biome.SKY))
                e.setCancelled(false);
        }
         */
        if (e.getBucket().equals(Material.LAVA_BUCKET) || e.getBucket().equals(Material.WATER_BUCKET)) {
            UUID owner = null;
            if (ASkyBlockAPI.getInstance().hasIsland(p.getUniqueId()))
                owner = p.getUniqueId();

            if (ASkyBlockAPI.getInstance().inTeam(p.getUniqueId()))
                owner = ASkyBlockAPI.getInstance().getTeamLeader(p.getUniqueId());

            Island island = ASkyBlockAPI.getInstance().getIslandOwnedBy(owner);
            int count = 0;
            List<UUID> members = new ArrayList<>();
            if (island.getMembers().size() > 0) {
                for (UUID uuid : island.getMembers()) {
                    members.add(uuid);
                }
            }

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!members.contains(player.getUniqueId())) {
                    if (island.onIsland(player.getLocation())) {
                        count++;
                    }
                }
            }

            if (count > 0) {
                p.sendMessage("§c§lBłąd: §7Na Twojej wyspie są inni gracze! Wpisz §f/wyspa wyrzuc §7aby postawić ten blok.");
                e.setCancelled(true);
                return;
            }

            if (e.getBucket().equals(Material.LAVA_BUCKET)) {
                User pUUID = User.get(p.getUniqueId());
                if (pUUID.getZadanie(8).equals(QuestState.W_TRAKCIE))
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerQuestCompleteEvent(pUUID, 8, QuestState.WYKONANE));
            }
        }
    }
}
