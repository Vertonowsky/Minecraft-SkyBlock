package me.vertonowsky.essentials.listeners;

import me.vertonowsky.enums.JobType;
import me.vertonowsky.events.PlayerExpGainEvent;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.Random;

public class OnFish implements Listener {

    @EventHandler
    public void onFishing(PlayerFishEvent e) {
        Player p = e.getPlayer();
        User pUUID = User.get(p.getName());


        if (e.getCaught() != null && e.getCaught() instanceof Item) {
            if (e.getExpToDrop() > 0) {
                Item item = (Item) e.getCaught();
                if (item.getItemStack().getType().equals(Material.RAW_FISH)) {
                    int data = item.getItemStack().getDurability();
                    if (data == 0) pUUID.setRawFishCatched(pUUID.getRawFishCatched() + 1);
                    if (data == 1) pUUID.setSalmonCatched(pUUID.getSalmonCatched() + 1);
                    if (data == 2) pUUID.setClownFishCatched(pUUID.getClownFishCatched() + 1);
                    if (data == 3) pUUID.setPufferFishCatched(pUUID.getPufferFishCatched() + 1);

                    Random r = new Random();
                    double bonusChance = (double) (pUUID.getLevel(JobType.ANGLER) / 50) + 5;
                    if (bonusChance > r.nextDouble() * 100) {
                        pUUID.giveItem(item.getItemStack(), true);
                    }
                }

                Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(pUUID, JobType.ANGLER, 25));
                return;
            }

        }

    }
}
