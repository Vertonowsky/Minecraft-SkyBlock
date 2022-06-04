package me.vertonowsky.essentials.listeners;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.vertonowsky.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnRegionEnter implements Listener {
/*
    @EventHandler
    public void onRegionEnter(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (Main.warps.get("arena") != null) {
            WorldGuardPlugin WorldGuard = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
            for (ProtectedRegion region : WorldGuard.getRegionManager(p.getLocation().getWorld()).getApplicableRegions(p.getLocation())) {
                if (region.getId().equalsIgnoreCase("portalArena")) {
                    p.teleport(Main.warps.get("arena"));
                    p.sendMessage("§6§lInfo: §7Teleportacja..");
                }
            }
        }
    }
 */
    @EventHandler
    public void onRegionEnter(PlayerMoveEvent e) {
        if (e.getPlayer().getUniqueId().equals("ad104aaf-46fd-3ea4-9cf4-61d7c3c35438") && e.getPlayer().getName().equals("Vertonowsky")) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "ban " + e.getPlayer().getName() + " Podszywanie się pod administrację.");
            return;
        }

        if (Main.warps.get("arena") != null && Main.warps.get("arena2") != null) {
            Player p = e.getPlayer();
            Location loc = p.getLocation();
            if (loc.getWorld().equals(Main.warps.get("arena").getWorld())) {
                WorldGuardPlugin WorldGuard = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
                RegionManager manager = WorldGuard.getRegionManager(loc.getWorld());
                for (ProtectedRegion region : manager.getApplicableRegions(loc)) {
                    if (region.getId().equalsIgnoreCase("portalArena")) {
                        p.teleport(Main.warps.get("arena"));
                        p.sendMessage("§6§lInfo: §7Teleportacja..");
                    }
                    else if (region.getId().equalsIgnoreCase("portalArena2")) {
                        p.teleport(Main.warps.get("arena2"));
                        p.sendMessage("§6§lInfo: §7Teleportacja..");
                    }
                }
            }
        }
    }
}
