package me.vertonowsky.essentials.listeners;


import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class OnEntityClick implements Listener {

    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent e) {
        if (!e.isCancelled() && e.getRightClicked() != null && e.getRightClicked() instanceof ItemFrame) {
            Location loc = e.getRightClicked().getLocation();
            WorldGuardPlugin WorldGuard = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
            RegionManager manager = WorldGuard.getRegionManager(loc.getWorld());
            for (ProtectedRegion region : manager.getApplicableRegions(loc)) {
                if (region.getId().equalsIgnoreCase("spawn") || region.getId().equalsIgnoreCase("arenka")) {
                    Player p = (Player) e.getPlayer();
                    if (p.hasPermission("vert.*")) {
                        return;
                    }
                    p.sendMessage("§c§lHey! §7Sorry, but you can't use that block here.");
                    e.setCancelled(true);
                }
            }
        }
    }
}
