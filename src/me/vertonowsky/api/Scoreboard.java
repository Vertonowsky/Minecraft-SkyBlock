package me.vertonowsky.api;

import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;

public class Scoreboard {

    public static org.bukkit.scoreboard.Scoreboard board;
    public static Objective o;

    public static void setScoreboard(Player p) {
        User pUUID = User.get(p.getUniqueId());
        board = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
        o = board.registerNewObjective("test", "dummy");

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String status = (format.format(System.currentTimeMillis()));
        double priceRounded = new BigDecimal(pUUID.getMoney() + "").setScale(2, RoundingMode.HALF_UP).doubleValue();

        o.setDisplayName("§8-§m[§b Prestigemc §8§m]-");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        p.setScoreboard(board);
        o.getScore("§0").setScore(15);
        o.getScore("§6Godzina:").setScore(14);
        o.getScore("§f" + status).setScore(13);
        o.getScore("§1").setScore(12);
        o.getScore("§6Saldo:").setScore(11);
        o.getScore("§7$" + priceRounded).setScore(10);
        o.getScore("§2").setScore(9);
        o.getScore("§6Poziom:").setScore(8);
        o.getScore("§7" + pUUID.getTotalLevel()).setScore(7);
        o.getScore("§3").setScore(6);
        o.getScore("§eDusze:").setScore(5);
        if (pUUID.getSouls() < 0) o.getScore("§e0").setScore(4);
        if (pUUID.getSouls() >= 0)o.getScore("§e" + pUUID.getSouls()).setScore(4);
        o.getScore("§4").setScore(3);
        o.getScore(" §bwww.prestigemc.pl").setScore(2);
        o.getScore("§8§m----------------").setScore(1);
    }


}
