package me.vertonowsky.essentials.listeners;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.vertonowsky.essentials.commands.CommandPvp;
import me.vertonowsky.main.Main;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class OnRespawn implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInst(), new Runnable() {
            public void run() {
                boolean inArena = false;
                Location loc = p.getLocation();
                WorldGuardPlugin WorldGuard = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
                RegionManager manager = WorldGuard.getRegionManager(loc.getWorld());
                for (ProtectedRegion region : manager.getApplicableRegions(loc)) {
                    if (region.getId().equalsIgnoreCase("arenka")) {
                        if (Main.warps.containsKey("arena")) {
                            p.teleport(Main.warps.get("arena"));
                            inArena = true;
                            break;
                        }
                    }
                }

                if (!inArena) {
                    if (Main.spawn != null) {
                        p.teleport(Main.spawn);
                    }
                }

                User pUUID = User.get(p.getUniqueId());
                if (CommandPvp.bar.getPlayers().contains(p)) CommandPvp.bar.removePlayer(p);
                pUUID.setPvp(false);
                p.setGlowing(false);
            }
        }, 2);
    }
}
