package me.vertonowsky.essentials.listeners;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OnEntityDamageByEntity implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof ItemFrame) {
            Location loc = e.getEntity().getLocation();
            WorldGuardPlugin WorldGuard = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
            RegionManager manager = WorldGuard.getRegionManager(loc.getWorld());
            for (ProtectedRegion region : manager.getApplicableRegions(loc)) {
                if (region.getId().equalsIgnoreCase("spawn") || region.getId().equalsIgnoreCase("arenka")) {
                    if (e.getDamager() instanceof Player) {
                        Player p = (Player) e.getDamager();
                        if (p.hasPermission("vert.*")) return;

                        p.sendMessage("§c§lHey! §7Sorry, but you can't break that block here.");
                    }
                    e.setCancelled(true);
                    return;
                }
            }
        }
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            Player t = null;
            User pUUID = User.get(p.getName());
            User tUUID = null;

            if (e.getDamager() instanceof Player) {
                t = (Player) e.getDamager();
                tUUID = User.get(t.getName());
            }

            if (e.getDamager() instanceof Projectile) {
                if (((Projectile) e.getDamager()).getShooter() instanceof Player) {
                    t = ((Player) ((Projectile) e.getDamager()).getShooter());
                    tUUID = User.get(t.getName());
                }
            }

            if (tUUID != null) {
                if (pUUID == tUUID) return;

                if (!tUUID.isPvp()) {
                    e.setCancelled(true);
                    p.setFireTicks(0);
                    t.sendMessage("§c§lBłąd: §7Włącz pvp przy użyciu komendy §c/pvp§7.");
                    return;
                }

                if (pUUID != null) {
                    if (!pUUID.isPvp()) {
                        e.setCancelled(true);
                        p.setFireTicks(0);
                        t.sendMessage("§c§lBłąd: §7Gracz §f" + p.getName() + " §7ma wyłączone §cpvp§7.");
                        return;
                    }
                }
            }
        }
    }
}
