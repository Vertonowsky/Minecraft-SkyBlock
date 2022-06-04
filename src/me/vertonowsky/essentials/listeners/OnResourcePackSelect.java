package me.vertonowsky.essentials.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class OnResourcePackSelect implements Listener {

    @EventHandler
    public void onResourcePackStatusEvent(PlayerResourcePackStatusEvent e) {
        Player p = e.getPlayer();
        if (e.getStatus() == PlayerResourcePackStatusEvent.Status.ACCEPTED) {
            p.sendMessage("§8§l>> §a§lPomyslnie zaladowano paczke zasobów!");
            return;
        } else if (e.getStatus() == PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD) {
            p.sendMessage("§8§l>> §c§lWystąpił blad podczas pobierania paczki zasobów!");
            return;
        }
        /*else if (e.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED) {
            p.sendMessage("§4§m------------------------------------------");
            p.sendMessage("");
            p.sendMessage("§8§l>> §cZalecamy korzystanie z serwerowej paczki");
            p.sendMessage("§8§l>> §czasobów. Jej brak może utrudniać rozgrywkę.");
            p.sendMessage("");
            p.sendMessage("§8§l>> §cTryb wieloosobowy -> dodaj serwer ->");
            p.sendMessage("§8§l>> §cprestigemc.pl -> edytuj -> Włącz paczki zasobów");
            p.sendMessage("");
            p.sendMessage("§4§m------------------------------------------");
            p.playSound(p.getLocation(), Sound.BLOCK_CHORUS_FLOWER_DEATH, 1, 1);
            return;
        }
        
         */
    }
}
