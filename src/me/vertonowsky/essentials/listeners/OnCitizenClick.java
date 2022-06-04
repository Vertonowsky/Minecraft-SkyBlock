package me.vertonowsky.essentials.listeners;

import me.vertonowsky.api.API;
import me.vertonowsky.inventory.*;
import me.vertonowsky.user.User;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.text.SimpleDateFormat;

public class OnCitizenClick implements Listener {


    @EventHandler
    public void onCitizenClick(NPCRightClickEvent e) {
        Player p = e.getClicker();
        NPC npc = e.getNPC();

        if (npc.getName().equalsIgnoreCase("§6§lSkup")) {
            Inventory inv = Bukkit.createInventory(null, 54, "§8Skup");
            p.openInventory(inv);
            CitizenSkup.openInventorySkup(inv, p, 0);
            return;
        }

        if (npc.getName().equalsIgnoreCase("§6§lInne") || npc.getName().equalsIgnoreCase("§6§lBloki") || npc.getName().equalsIgnoreCase("§6§lSpawnery")) {
            /*
            if (npc.getName().equalsIgnoreCase("§6§lSpawnery")) {
                if (!p.hasPermission("vert.spawn.shop")) {
                    p.getWorld().playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
                    p.sendMessage("§8§l>> §cDostęp do tego sklepu ma tylko ranga §fVIP §club §fSuperVIP§c!");
                    return;
                }
            }

             */
            Inventory inv = Bukkit.createInventory(null, 45, "§8" + ChatColor.stripColor(npc.getName()));
            p.openInventory(inv);
            CitizenSklep.openInventorySklep(inv, User.get(p.getName()), npc.getName());
            return;
        }

        if (npc.getName().equalsIgnoreCase("§a§lZadania")) {
            Inventory inv = Bukkit.createInventory(null, 54, "§8" + ChatColor.stripColor(npc.getName()));
            p.openInventory(inv);
            Zadania.openInventoryZadania(inv, p, 0);
            return;
        }

        if (npc.getName().equalsIgnoreCase("§a§lTOP 10")) {
            Inventory inv = Bukkit.createInventory(null, 54, "§8TOP graczy");
            p.openInventory(inv);
            TopPlayers.openInventoryTopPlayers(inv, p, 0);
            return;
        }

        if (npc.getName().equalsIgnoreCase("§a§lZdolnosci")) {
            Inventory inv = Bukkit.createInventory(null, 27, "§8Zdolnosci " + p.getName());
            p.openInventory(inv);
            Roles.openInventoryRolesInventory(inv, User.get(p.getName()));
            return;
        }

        if (npc.getName().equalsIgnoreCase("§cZbyszek")) {
            p.sendMessage("§8§l>> §cZbyszek: §7Siemaneczko w czym mogę ci po.. Ughh znowu ten green screen!! Idź sobie!!! Nie mam teraz czasu na pogaduszki!");
            return;
        }

        if (npc.getName().equalsIgnoreCase("§a§lStatystyki")) {
            User pUUID = User.get(p.getName());

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String status = (format.format(p.getFirstPlayed()));
            long hoursOnline = (long) pUUID.getTotalTimeOnline()/60;
            String minutesOnline = "";

            if (hoursOnline < 10) {
                minutesOnline = ", " + (int)(pUUID.getTotalTimeOnline()%60) + " minut";
            }



            p.sendMessage("§8§m-----------------------------------------");
            p.sendMessage("");
            p.sendMessage("   §8§l>> §bRanga: " + API.getRankColor(pUUID.getRank()) + pUUID.getRank() + "                        §8§l>> §bPoziom: §7" + pUUID.getTotalLevel());
            p.sendMessage("");
            if (pUUID.getMoney() > 0) p.sendMessage("   §8§l>> §bSaldo: §a$" + pUUID.getMoney());
            if (pUUID.getMoney() < 0) p.sendMessage("   §8§l>> §bSaldo: §c$" + pUUID.getMoney());
            p.sendMessage("   §8§l>> §bCzas online §8[§7h§8]: §7" + hoursOnline + " godzin" + minutesOnline);
            p.sendMessage("   §8§l>> §bData dołączenia: §7" + status);
            p.sendMessage("   §8§l>> §bZabójstwa : Zgony: §7" + pUUID.getKills() + " : " + pUUID.getDeaths());
            p.sendMessage("");
            p.sendMessage("§8§m-----------------------------------------");
            return;
        }
    }
}
