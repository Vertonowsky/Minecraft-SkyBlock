package me.vertonowsky.permissions;

import me.vertonowsky.main.Main;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class PermissionVert {


    public static HashMap<UUID, PermissionAttachment> playerPermissions = new HashMap<>();

    public static String defaultRank;



    public static void setRank(User u, String newRank, long days, boolean onlyUpdatePermissions) {
        playerPermissions.remove(u.getUuid());
        if (newRank.equalsIgnoreCase("Brak")) newRank = defaultRank;

        if (Bukkit.getPlayer(u.getName()) != null) setupPermissions(Bukkit.getPlayer(u.getName()), newRank);

        if (!onlyUpdatePermissions) {
            long currentExpireDate = System.currentTimeMillis();
            if (u.getRank().equals(newRank)) if (currentExpireDate < u.getRankExpireDate()) currentExpireDate = u.getRankExpireDate();


            long expireDate;
            if (days > 0) {
                long time = (days * 60 * 60 * 24 * 1000);
                expireDate = currentExpireDate + time;
            } else {
                expireDate = -1;
            }

            u.setRank(newRank);
            u.setRankExpireDate(expireDate);
        }
    }




    public static void getDefaultRank() {
        File f = new File(Main.getInst().getDataFolder(), "permissions.yml");
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
        for (String rank : yml.getConfigurationSection("ranks").getKeys(false)) {
            String path = "ranks." + rank + ".options.default";
            if (yml.contains(path) && yml.getBoolean(path) == true) {
                PermissionVert.defaultRank = rank;
                return;
            }
        }
    }




    public static void setupPermissions(Player p, String newRank) {
        File f = new File(Main.getInst().getDataFolder(), "permissions.yml");


        YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);

        PermissionAttachment attachment = p.addAttachment(Main.getInst());
        User u = User.get(p.getUniqueId());
        if (!(playerPermissions.containsKey(u.getUuid()))) {
            playerPermissions.put(u.getUuid(), attachment);
        }

        if (!(u.getRank().equals("Brak"))) {
            for (String permissions : yml.getStringList("ranks." + u.getRank() + ".permissions")) {
                attachment.setPermission(permissions, false);
            }
            for (String permissions : yml.getStringList("ranks." + newRank + ".blockedPermissions")) {
                attachment.setPermission(permissions, false);
            }
        }

        for (String permissions : yml.getStringList("ranks." + newRank + ".permissions")) {
            attachment.setPermission(permissions, true);
        }


    }
}
