package me.vertonowsky.essentials.listeners;

import me.vertonowsky.api.Scoreboard;
import me.vertonowsky.enums.JobType;
import me.vertonowsky.events.PlayerLevelUpEvent;
import me.vertonowsky.main.Main;
import me.vertonowsky.mysql.MySQL;
import me.vertonowsky.mysql.Quests;
import me.vertonowsky.user.User;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;

public class OnPlayerLevelUp implements Listener {


    @EventHandler
    public void onLevelUp(PlayerLevelUpEvent e) {
        User u = e.getUser();

        u.setLevel(e.getJobType(), e.getLevel());
        u.setExperience(e.getJobType(), 0);
        u.setMaxExperience(e.getJobType(), Main.maxExpTable.get(e.getLevel()));

        int totalLevel = 0;
        for (JobType jobType : JobType.values()) {
            totalLevel += u.getLevel(jobType);
        }
        u.setTotalLevel(totalLevel);

        if (u.getSouls() == -1) u.setSouls(0);
        u.setSouls(u.getSouls() + 1);


        if (Bukkit.getPlayer(u.getName()) != null) {
            Player p = Bukkit.getPlayer(u.getName());
            Location loc = p.getLocation().add(0, 2.5, 0);


            Firework firework = (Firework) p.getWorld().spawn(loc, Firework.class);
            FireworkMeta fm = firework.getFireworkMeta();
            fm.addEffect(FireworkEffect.builder()
                    .flicker(false)
                    .trail(true)
                    .with(FireworkEffect.Type.BURST)
                    .withColor(Color.FUCHSIA)
                    .withFade(Color.GREEN)
                    .build());
            fm.setPower(1);
            firework.setFireworkMeta(fm);


            Firework firework2 = (Firework) p.getWorld().spawn(loc, Firework.class);
            FireworkMeta fm2 = firework2.getFireworkMeta();
            fm2.addEffect(FireworkEffect.builder()
                    .flicker(true)
                    .trail(true)
                    .with(FireworkEffect.Type.BALL)
                    .withColor(Color.BLUE)
                    .withFade(Color.PURPLE)
                    .build());
            fm2.setPower(1);
            firework2.setFireworkMeta(fm2);

            Firework firework3 = (Firework) p.getWorld().spawn(loc, Firework.class);
            FireworkMeta fm3 = firework3.getFireworkMeta();
            fm3.addEffect(FireworkEffect.builder()
                    .flicker(true)
                    .trail(true)
                    .with(FireworkEffect.Type.STAR)
                    .withColor(Color.GREEN)
                    .withFade(Color.ORANGE)
                    .build());
            fm3.setPower(1);
            firework3.setFireworkMeta(fm3);



            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

            p.sendMessage("§8§m--------------------------------------");
            String rola = "";
            if (e.getJobType().equals(JobType.MINER)) rola = "Górnik";
            if (e.getJobType().equals(JobType.LUMBERJACK)) rola = "Drwal";
            if (e.getJobType().equals(JobType.WARRIOR)) rola = "Wojownik";
            if (e.getJobType().equals(JobType.ANGLER)) rola = "Wędkarz";

            p.sendMessage("§8§l>> §bZdolność §7" + rola + " §bwzrosła do §7§l" + e.getLevel() + " poziomu§b!");
            p.sendMessage("");
            p.sendMessage("§8§l>> §bDoświadczenie:");
            p.sendMessage("§8§l>> §7" + u.getExperience(e.getJobType()) + "§8/§7" + u.getMaxExperience(e.getJobType()));
            p.sendMessage("");
            p.sendMessage("                             §8§l[§bPoziom: §7" + u.getTotalLevel() + "§8§l]");
            p.sendMessage("§8§m--------------------------------------");

            Scoreboard.setScoreboard(p);

            MySQL.openConnection();
            Quests.repairPlayersQuests(p);
            MySQL.closeConnection();

        }

    }
}
