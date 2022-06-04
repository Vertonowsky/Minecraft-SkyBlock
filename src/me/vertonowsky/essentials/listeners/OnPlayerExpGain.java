package me.vertonowsky.essentials.listeners;

import me.vertonowsky.api.API;
import me.vertonowsky.enums.JobType;
import me.vertonowsky.events.PlayerExpGainEvent;
import me.vertonowsky.events.PlayerLevelUpEvent;
import me.vertonowsky.main.Main;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnPlayerExpGain implements Listener {


    @EventHandler
    public void onExpGain(PlayerExpGainEvent e) {
        User u = e.getUser();
        long exp = e.getExp();

        long currentExperience = u.getExperience(e.getJobType());
        long maxExperience = u.getMaxExperience(e.getJobType());

        String zdolnosc = "Górnik";
        if (e.getJobType().equals(JobType.ANGLER)) zdolnosc = "Wędkarz";
        if (e.getJobType().equals(JobType.WARRIOR)) zdolnosc = "Wojownik";
        if (e.getJobType().equals(JobType.LUMBERJACK)) zdolnosc = "Drwal";


        if (Main.doubleExp) exp = exp*2;

        if (Bukkit.getPlayer(u.getUuid()) != null) {
            Player p = Bukkit.getPlayer(u.getUuid());
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.15F, 0.15F);
            if (!Main.doubleExp) API.sendActionBar(p, "§8§l>> §b" + zdolnosc + " §7+" + exp + " exp");
            if (Main.doubleExp) API.sendActionBar(p, "§8§l>> §b" + zdolnosc + " §d+" + exp + " exp [DOUBLE EXP]");
        }

        if (currentExperience + exp >= maxExperience) {
            Bukkit.getServer().getPluginManager().callEvent(new PlayerLevelUpEvent(u, e.getJobType(), u.getLevel(e.getJobType()) + 1));
            return;
        }

        u.setExperience(e.getJobType(), currentExperience + exp);
    }
}
