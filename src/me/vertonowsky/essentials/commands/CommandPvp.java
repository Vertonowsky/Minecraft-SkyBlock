package me.vertonowsky.essentials.commands;

import me.vertonowsky.user.User;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class CommandPvp implements CommandExecutor {

    public static BossBar bar = Bukkit.createBossBar("§c§lPVP §7§ljest wlaczone! §c§l/pvp §7§laby wylaczyc!".toUpperCase(), BarColor.PINK, BarStyle.SEGMENTED_20);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pvp")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                User pUUID = User.get(p.getName());
                if (p.hasPermission("vert.pvp")) {
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                    Location loc = p.getLocation().add(0, 1, 0);
                    PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.CRIT, true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0.5F, 0.5F, 0.5F, 0, 20, null);
                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

                    if (pUUID.isPvp()) {
                        pUUID.setPvp(false);
                        p.setGlowing(false);
                        p.sendMessage("§8>> §c§lUWAGA! §7PVP zostało §7wyłączone!");
                        bar.removePlayer(p);
                        return true;
                    }
                    if (!pUUID.isPvp()) {
                        pUUID.setPvp(true);
                        p.setGlowing(true);
                        p.setAllowFlight(false);
                        p.sendMessage("§8>> §c§lUWAGA! §7PVP zostało §7włączone!");
                        bar.addPlayer(p);
                        return true;
                    }
                    return true;
                } else {
                    p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
                    return true;

                }
            }
        }
        return false;
    }
}
