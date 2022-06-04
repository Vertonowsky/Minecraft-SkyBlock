package me.vertonowsky.api;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import me.vertonowsky.main.Main;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;


public class Holograms {



    public static void reloadHolograms() {
        for (Hologram holo : HologramsAPI.getHolograms(Main.getInst())) {
            holo.delete();
        }
        File f3 = new File(Main.getInst().getDataFolder(), "holograms.yml");
        YamlConfiguration yaml3 = YamlConfiguration.loadConfiguration(f3);

        if (yaml3.get("holograms") != null) {
            for (String id : yaml3.getConfigurationSection("holograms").getKeys(false)) {
                Location holoLoc = Main.StringToLoc(yaml3.getString("holograms." + id + ".location"));
                List<String> lines = yaml3.getStringList("holograms." + id + ".text");

                final Hologram hol = HologramsAPI.createHologram(Main.getInst(), holoLoc);
                if (!yaml3.getBoolean("holograms." + id + ".visible")) hol.getVisibilityManager().setVisibleByDefault(false);

                for (String line : lines) {
                    hol.appendTextLine(ChatColor.translateAlternateColorCodes('&', line));
                }

            }
        }

        reloadMoneyChests();
    }

    public static void reloadMoneyChests(Player p) {
        if (Main.warps.get("moneyChest") != null) {
            User pUUID = User.get(p.getUniqueId());
            if (pUUID.getMoneyChestsHologram() != null) pUUID.getMoneyChestsHologram().delete();
            Location loc = Main.warps.get("moneyChest").clone();
            loc.add(0.5, 2.5, 0.5);
            Hologram price = HologramsAPI.createHologram(Main.getInst(), loc);
            VisibilityManager vm = price.getVisibilityManager();
            vm.setVisibleByDefault(false);
            vm.showTo(p);
            price.insertTextLine(0, "§eKlucze §7[§e" + pUUID.getMoneyChests() + "§7]");
            price.insertTextLine(1, "§7✧ §6Złota skrzynia §7✧");
            price.insertTextLine(2, "§8[§c!§8]§c Zamknięta §8[§c!§8]");
            pUUID.setMoneyChestsHologram(price);
        }
    }


    public static void reloadMoneyChests() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            reloadMoneyChests(p);
        }
    }



    private Hologram getHologram(String id) {
        File f3 = new File(Main.getInst().getDataFolder(), "holograms.yml");
        YamlConfiguration yaml3 = YamlConfiguration.loadConfiguration(f3);
        Hologram holo = HologramsAPI.createHologram(Main.getInst(), Main.StringToLoc(yaml3.getString("holograms." + id + ".location")).add(1000, 1000, 1000));

        if (yaml3.get("holograms") != null) {
            Location holoLoc = Main.StringToLoc(yaml3.getString("holograms." + id + ".location"));
            for (Hologram hologram : HologramsAPI.getHolograms(Main.getInst())) {
                if (hologram.getLocation().equals(holoLoc)) {
                    holo = hologram;
                    break;
                }
            }
        }
        return holo;
    }



}
