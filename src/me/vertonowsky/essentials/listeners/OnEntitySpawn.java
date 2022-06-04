package me.vertonowsky.essentials.listeners;

import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class OnEntitySpawn implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        if (e.getEntity() instanceof Ghast) {
            e.setCancelled(true);
        }

        if (e.getEntity() instanceof WitherSkeleton) {
            e.setCancelled(true);
        }

        if (e.getEntity() instanceof Wither) {
            e.setCancelled(true);
        }

        if (e.getEntity() instanceof Snowman) {
            e.setCancelled(true);
        }

        if (e.getEntity() instanceof IronGolem) {
            
            e.setCancelled(true);
        }

        if (e.getEntity() instanceof Enderman) {
            Location loc = e.getLocation();
            if (!loc.getWorld().getBiome(loc.getBlockX(), loc.getBlockZ()).equals(Biome.SKY)) e.setCancelled(true);
        }
    }
}
