package me.vertonowsky.essentials.listeners;

import fr.xephi.authme.api.v3.AuthMeApi;
import me.vertonowsky.main.Main;
import me.vertonowsky.mysql.MySQL;
import me.vertonowsky.mysql.Shop;
import me.vertonowsky.user.User;
import me.vertonowsky.user.UserUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class OnPlayerLogin implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();

        if (!AuthMeApi.getInstance().isRegistered(p.getName())) {
            Main.antiBot.put(p.getName(), System.currentTimeMillis());

            if (System.currentTimeMillis() > Main.antiBotDelay) {

                int countAllPlayers = 0;
                for (long delay : Main.antiBot.values()) {
                    if (System.currentTimeMillis() - delay <= 5000) {
                        countAllPlayers++;
                    }
                }

                if (countAllPlayers >= 10) {
                    Main.antiBotDelay = System.currentTimeMillis() + 60 * 10 * 1000;
                }

            } else {
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§8§l[§2§lAntyBot§8§l]\n\n §8§l>> §7Nastąpił atak botów! Odczekaj chwilę przed ponownym wejściem do gry!");
                return;
            }
        }



        Shop.checkShopReceivedUnBan(p.getName());


        if (UserUtils.getUsers().contains(User.get(p.getUniqueId()))) {
            MySQL.openConnection();
            try {
                ResultSet rs;
                rs = MySQL.conn.createStatement().executeQuery("SELECT * FROM `users_general` WHERE uuid='" + p.getUniqueId().toString() + "'");
                while(rs.next()) {
                    boolean banned = rs.getBoolean("banned");
                    if (banned) {
                        String banner = rs.getString("ban_banner");
                        String reason = "null";
                        String banReason = rs.getString("ban_reason");
                        long expireDate = rs.getLong("ban_expire_date");

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String status = (format.format(expireDate));
                        if (expireDate > System.currentTimeMillis()) {
                            reason = "§4Zostałeś zbanowany na tym serwerze! \n\n §8➢ §cZbanował: §e" + banner + "\n§cPowód: §e" + banReason + "\n§cDługość bana: §e" + status + "\n\n §4Odbanuj się na stronie §cwww.prestigemc.pl";
                        }

                        if (expireDate == -1) {
                            reason = "§4Zostałeś zbanowany na tym serwerze! \n\n §8➢ §cZbanował: §e" + banner + "\n§8➢ §cPowód: §e" + banReason + "\n§8➢ §cDługość bana: §eNa zawsze \n\n §4Odbanuj się na stronie §cwww.prestigemc.pl";
                        }

                        if (!reason.equals("null")) {
                            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, reason);
                        }
                        MySQL.closeConnection();
                        return;
                    }
                }
            } catch (SQLException exe) {
                exe.printStackTrace();
                MySQL.closeConnection();
            }
            MySQL.closeConnection();
        }



        if (Main.connectionWait.containsKey(p.getName())) {
            long wait = Main.connectionWait.get(p.getName());

            if (System.currentTimeMillis() - wait <= 10000) {
                long cd = (System.currentTimeMillis() - wait) / 1000;
                long cd2 = (10 - cd);
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ("§8§l>> §cOdczekaj §e" + cd2 + " §csekund zanim znowu dołączysz do gry!"));
                return;
            }
        }

        Main.connectionWait.put(p.getName(), System.currentTimeMillis());


    }

}
