package me.vertonowsky.essentials.listeners;

import me.vertonowsky.api.Scoreboard;
import me.vertonowsky.enums.QuestState;
import me.vertonowsky.events.PlayerQuestCompleteEvent;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class OnItemPickUp implements Listener {

    @EventHandler
    public void onItemPickUp(PlayerPickupItemEvent e) {
        if (e.getItem().getItemStack().getType().equals(Material.PAPER)) {
            if (e.getItem().getItemStack().getItemMeta() != null && e.getItem().getItemStack().getItemMeta().getDisplayName() != null) {
                String itemName = e.getItem().getItemStack().getItemMeta().getDisplayName();
                if (itemName.contains("§aKasa")) {
                    itemName = ChatColor.stripColor(itemName.replaceFirst("Kasa: ", ""));
                    Player p = e.getPlayer();
                    User pUUID = User.get(p.getUniqueId());
                    pUUID.setMoney(pUUID.getMoney() + Integer.parseInt(itemName));
                    e.getItem().remove();
                    e.setCancelled(true);
                    p.sendMessage("§8§l>> §8[§a+$" + Integer.parseInt(itemName) + "§8]");
                    Scoreboard.setScoreboard(p);
                    return;
                }
            }
        }
        if (e.getItem().getItemStack().getType().equals(Material.GHAST_TEAR)) {
            if (e.getItem().getItemStack().getItemMeta() != null && e.getItem().getItemStack().getItemMeta().getDisplayName() != null) {
                String itemName = e.getItem().getItemStack().getItemMeta().getDisplayName();
                if (itemName.contains("§aDusze")) {
                    itemName = ChatColor.stripColor(itemName.replaceFirst("Dusze: ", ""));
                    Player p = e.getPlayer();
                    User pUUID = User.get(p.getUniqueId());
                    pUUID.setSouls(pUUID.getSouls() + Integer.parseInt(itemName));
                    e.getItem().remove();
                    e.setCancelled(true);
                    p.sendMessage("§8§l>> §8[§a+" + Integer.parseInt(itemName) + " dusz§8]");
                    Scoreboard.setScoreboard(p);
                    return;
                }
            }
        }
        if (e.getItem().getItemStack().getType().equals(Material.DIAMOND)) {
            Player p = e.getPlayer();
            User pUUID = User.get(p.getUniqueId());
            if (pUUID.getTotalLevel() >= 21) {
                if (pUUID.getZadanie(11).equals(QuestState.W_TRAKCIE)) Bukkit.getServer().getPluginManager().callEvent(new PlayerQuestCompleteEvent(pUUID, 11, QuestState.WYKONANE));
            }
        }
    }
}
