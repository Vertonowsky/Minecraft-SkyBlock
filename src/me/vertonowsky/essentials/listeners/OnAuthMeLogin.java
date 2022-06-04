package me.vertonowsky.essentials.listeners;

import fr.xephi.authme.events.LoginEvent;
import me.vertonowsky.api.API;
import me.vertonowsky.api.Holograms;
import me.vertonowsky.essentials.commands.CommandVanish;
import me.vertonowsky.main.Main;
import me.vertonowsky.mysql.Shop;
import me.vertonowsky.user.User;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class OnAuthMeLogin implements Listener {

    @EventHandler
    public void onAuthMeLogin(LoginEvent e) {
        Player p = e.getPlayer();
        User pUUID = User.get(p.getUniqueId());

        Shop.checkShopReceivedItems(p.getName());
        Holograms.reloadMoneyChests(p);


        for (UUID u : CommandVanish.vanish) {
            if (Bukkit.getPlayer(u) != null) {
                p.hidePlayer(Bukkit.getPlayer(u));
            }
        }



        if (Main.spawn != null) {
            p.teleport(Main.spawn);
            pUUID.setPvp(false);
            p.setGlowing(false);
        }

        if (System.currentTimeMillis() > pUUID.getNextDailyReward()) {
            p.sendMessage("§8§m-----------------------------------------");
            p.sendMessage("");
            p.sendMessage("                         §5§lNAGRODA DNIA ");
            p.sendMessage("");
            API.sendJsonMessage(p, "             §5§lKLIKNIJ §d§lABY ODEBRAĆ ZESTAW", "§8§l[§5§lKliknij§8§l]", ClickEvent.Action.RUN_COMMAND, "/zestaw");
            p.sendMessage("");
            p.sendMessage("§8§m-----------------------------------------");

        }


        if (!Main.doubleExp && Main.doubleDrop) API.sendTitleWithSubTitle(p, "§6PODWOJNY EXP §2W;ACZONY", "", 0, 2, 0);
        if (Main.doubleDrop && !Main.doubleDrop) API.sendTitleWithSubTitle(p, "§6PODWOJNY DROP §2WLACZONY", "", 0, 2, 0);
        if (Main.doubleDrop && Main.doubleExp) API.sendTitleWithSubTitle(p, "§6PODWOJNY DROP i EXP §2WLACZONY", "", 0, 2, 0);

        /*
        boolean voted = true;

        SimpleDateFormat format = new SimpleDateFormat("dd MM");

        String status = (format.format(pUUID.getVote1()));
        String[] date1 = status.split(" ");
        int day1 = Integer.parseInt(date1[0]);
        int month1 = Integer.parseInt(date1[1]);

        String status2 = (format.format(System.currentTimeMillis()));
        String[] date2 = status2.split(" ");
        int day2 = Integer.parseInt(date2[0]);
        int month2 = Integer.parseInt(date2[1]);


        if (day2 > day1) voted = false;
        if (month2 > month1) voted = false;


        if (!voted) {
            p.sendMessage("§8§m-----------------------------------------");
            p.sendMessage("");
            API.sendJsonMessage(p, "§8§l>> §8§l[§a§lKliknij§8§l] §2§lZagłosuj na nasz serwer!", "§8§l[§2§lKliknij§8§l]", ClickEvent.Action.RUN_COMMAND, "/vote");
            p.sendMessage("");
            p.sendMessage("§8§m-----------------------------------------");
        }
        */
    }
}
