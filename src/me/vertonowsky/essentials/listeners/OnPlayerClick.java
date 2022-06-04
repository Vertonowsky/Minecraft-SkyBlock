package me.vertonowsky.essentials.listeners;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.vertonowsky.api.API;
import me.vertonowsky.api.Holograms;
import me.vertonowsky.api.Scoreboard;
import me.vertonowsky.essentials.commands.CommandAdminItems;
import me.vertonowsky.inventory.InventoryAPI;
import me.vertonowsky.main.Main;
import me.vertonowsky.user.User;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockAction;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class OnPlayerClick implements Listener {

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        User pUUID = User.get(p.getName());
        ItemStack itemInHand = p.getInventory().getItemInMainHand();

        EquipmentSlot hand = e.getHand();

        pUUID.setLastInteract(System.currentTimeMillis());


        if (itemInHand.getType().equals(Material.PAPER)) {
            if (hand.equals(EquipmentSlot.HAND)) {
                if (itemInHand.getItemMeta() != null && itemInHand.getItemMeta().getDisplayName() != null) {
                    if (itemInHand.getItemMeta().getDisplayName().contains("§7[Kliknij prawy]")) {
                        String name = ChatColor.stripColor(itemInHand.getItemMeta().getDisplayName());
                        name = name.replaceAll("\\$", "");
                        name = name.replaceAll("\\[Kliknij prawy\\]", "");
                        name = name.replaceAll(" ", "");

                        InventoryAPI.removeItemFromInventory(p, itemInHand, 1);
                        int kwota = Integer.parseInt(name);
                        pUUID.setMoney(pUUID.getMoney() + kwota);
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                        API.sendBroadcastMessage("§8§l>> §2Gracz §a" + p.getName() + " §2aktywował bon o wartości §a$" + kwota + "§2!");
                        Scoreboard.setScoreboard(p);
                        e.setUseItemInHand(Event.Result.DENY);
                        e.setUseInteractedBlock(Event.Result.DENY);
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }


        if (itemInHand.getType().equals(Material.MONSTER_EGG)) {
            if (hand.equals(EquipmentSlot.HAND)) {
                Location loc = p.getLocation();
                WorldGuardPlugin WorldGuard = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
                RegionManager manager = WorldGuard.getRegionManager(loc.getWorld());
                for (ProtectedRegion region : manager.getApplicableRegions(loc)) {
                    if (region.getId().equalsIgnoreCase("spawn") || region.getId().equalsIgnoreCase("arenka")) {
                        e.setUseItemInHand(Event.Result.DENY);
                        e.setUseInteractedBlock(Event.Result.DENY);
                        e.setCancelled(true);
                        p.sendMessage("§c§lHey! §7Sorry, but you can't use that here.");
                        return;
                    }
                }
            }
        }

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (hand.equals(EquipmentSlot.HAND)) {
                if (e.getClickedBlock().getType() == Material.ANVIL) {
                    Location loc = p.getLocation();
                    WorldGuardPlugin WorldGuard = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
                    RegionManager manager = WorldGuard.getRegionManager(loc.getWorld());
                    for (ProtectedRegion region : manager.getApplicableRegions(loc)) {
                        if (region.getId().equalsIgnoreCase("spawn") || region.getId().equalsIgnoreCase("arenka")) {
                            e.setUseItemInHand(Event.Result.DENY);
                            e.setUseInteractedBlock(Event.Result.DENY);
                            e.setCancelled(true);
                            p.sendMessage("§c§lHey! §7Sorry, but you can't use that here.");
                            return;
                        }
                    }
                }
            }

            if (e.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE) {
                if (hand.equals(EquipmentSlot.HAND)) {
                    if (!p.hasPermission("vert.*")) {
                        if (pUUID.getTotalLevel() < 20) {
                            p.sendMessage("§c§lBłąd: §7Stół do zaklęć dostępny jest od §f20 §7poziomu!");
                            e.setUseItemInHand(Event.Result.DENY);
                            e.setUseInteractedBlock(Event.Result.DENY);
                            e.setCancelled(true);
                            return;
                        }
                    }
                }
            }

            if (e.getClickedBlock().getType() == Material.CHEST) {
                if (hand.equals(EquipmentSlot.HAND)) {
                    Chest chest = (Chest) e.getClickedBlock().getState();
                    Inventory inv = chest.getInventory();
                    if (inv.getTitle().equalsIgnoreCase("§7>> §aDzienny zestaw §7<<")) {
                        e.setCancelled(true);
                    }


                    if (e.getClickedBlock().getLocation().equals(Main.warps.get("moneyChest"))) {
                        e.setUseItemInHand(Event.Result.DENY);
                        e.setCancelled(true);
                        if (System.currentTimeMillis() - pUUID.getWaitALittle() <= 12 * 1000) {
                            p.sendMessage("§c§lBłąd: §7Odczekaj chwilę..");
                            return;
                        }

                        if (pUUID.getMoneyChests() < 1) {
                            p.sendMessage("§c§lBłąd: §7Skrzynka jest zamknięta.");
                            p.getLocation().getWorld().playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
                            return;
                        }

                        pUUID.setMoneyChests(pUUID.getMoneyChests() - 1);
                        pUUID.setWaitALittle(System.currentTimeMillis());
                        Location loc = e.getClickedBlock().getLocation();
                        PacketPlayOutBlockAction packet = new PacketPlayOutBlockAction(new BlockPosition(loc.getX(), loc.getY(), loc.getZ()), CraftMagicNumbers.getBlock(e.getClickedBlock()), 1, 1);
                        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                        loc.add(0.5, 1, 0.5);
                        p.getLocation().getWorld().playSound(loc, Sound.BLOCK_CHEST_OPEN, 1, 1);


                        Random r = new Random();
                        int amount = (r.nextInt(3999) + 1);
                        String amount2 = String.valueOf(amount);
                        pUUID.setMoney(pUUID.getMoney() + amount);

                        if (amount2.length() < 4) {
                            for (int in = 0; in <= 4 - amount2.length(); in++) {
                                amount2 = "⋯" + amount2;
                            }
                        }


                        String[] characters = new String[amount2.length()];

                        for (int in = 0; in < amount2.length(); in++) {
                            characters[in] = "§e§k" + amount2.charAt(in);
                        }


                        final Hologram hologram = HologramsAPI.createHologram(Main.getInst(), loc);
                        hologram.getVisibilityManager().setVisibleByDefault(false);
                        hologram.getVisibilityManager().showTo(p);
                        hologram.appendItemLine(new ItemStack(Material.GOLD_BLOCK, 1));

                        final Hologram price = pUUID.getMoneyChestsHologram();
                        price.teleport(price.getLocation().clone().subtract(0, 0.5, 0));
                        price.clearLines();
                        price.appendTextLine("       §6$" + characters[0] + characters[1] + characters[2] + characters[3] + "§6       ");

                        new BukkitRunnable() {
                            int ticks = 0;
                            int timeout = new Random().nextInt((20 - 15) + 1) + 15;
                            int currentCharacter = 0;

                            String[] characters2 = characters;

                            public void run() {
                                if (ticks == 100 || ticks == (timeout * 4) + 20) {
                                    if (!hologram.isDeleted()) hologram.delete();
                                    if (!price.isDeleted()) price.delete();

                                    final Location loc2 = e.getClickedBlock().getLocation();
                                    PacketPlayOutBlockAction packet = new PacketPlayOutBlockAction(new BlockPosition(loc2.getX(), loc2.getY(), loc2.getZ()), CraftMagicNumbers.getBlock(e.getClickedBlock()), 1, 0);
                                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                                    p.getLocation().getWorld().playSound(loc, Sound.BLOCK_CHEST_CLOSE, 1, 1);
                                    PacketPlayOutWorldParticles packet2 = new PacketPlayOutWorldParticles(EnumParticle.SMOKE_NORMAL, true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0.5F, 0.5F, 0.5F, 0, 20, null);
                                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet2);

                                    Scoreboard.setScoreboard(p);
                                    API.sendTitleWithSubTitle(p, "§6Dodano $" + amount + " do konta.", "§7Saldo: §a$" + pUUID.getMoney(), 0, 3, 0);
                                    Holograms.reloadMoneyChests(p);
                                    cancel();
                                    //return;
                                }
                                ticks++;
                                if (ticks < 20) hologram.teleport(hologram.getLocation().add(0, 0.02, 0));
                                if (ticks > 20 && ticks < 30)
                                    hologram.teleport(hologram.getLocation().add(0, 0.01, 0));

                                for (int i = 0; i < characters.length; i++) {
                                    if (characters[i].equals("§e§k⋯")) characters2[i] = "§e§kh";
                                }


                                if (currentCharacter == 0) {
                                    if (timeout < ticks) {
                                        characters2[0] = "§6" + ChatColor.stripColor(characters2[0]).replaceAll("h", "⋯");
                                        p.getLocation().getWorld().playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                                        currentCharacter++;
                                        if (!price.isDeleted()) {
                                            price.clearLines();
                                            price.appendTextLine("       §6$" + characters2[0] + characters2[1] + characters2[2] + characters2[3] + "§6       ");
                                        }
                                    }
                                }
                                if (currentCharacter == 1) {
                                    if (timeout * 2 < ticks) {
                                        characters2[1] = "§6" + ChatColor.stripColor(characters2[1]).replaceAll("h", "⋯");
                                        p.getLocation().getWorld().playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                                        currentCharacter++;
                                        if (!price.isDeleted()) {
                                            price.clearLines();
                                            price.appendTextLine("       §6$" + characters2[0] + characters2[1] + characters2[2] + characters2[3] + "§6       ");
                                        }
                                    }
                                }
                                if (currentCharacter == 2) {
                                    if (timeout * 3 < ticks) {
                                        characters2[2] = "§6" + ChatColor.stripColor(characters2[2]).replaceAll("h", "⋯");
                                        p.getLocation().getWorld().playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                                        currentCharacter++;
                                        if (!price.isDeleted()) {
                                            price.clearLines();
                                            price.appendTextLine("       §6$" + characters2[0] + characters2[1] + characters2[2] + characters2[3] + "§6       ");
                                        }
                                    }
                                }
                                if (currentCharacter == 3) {
                                    if (timeout * 4 < ticks) {
                                        characters2[3] = "§6" + ChatColor.stripColor(characters2[3]).replaceAll("h", "⋯");
                                        timeout = 10000;

                                        Firework firework = p.getWorld().spawn(loc, Firework.class);
                                        FireworkMeta fm = firework.getFireworkMeta();
                                        fm.addEffect(FireworkEffect.builder()
                                                .flicker(true)
                                                .trail(true)
                                                .with(FireworkEffect.Type.BALL)
                                                .withColor(Color.GREEN)
                                                .withFade(Color.RED)
                                                .build());
                                        fm.setPower(0);
                                        firework.setFireworkMeta(fm);
                                        if (!price.isDeleted()) {
                                            price.clearLines();
                                            price.appendTextLine("       §6$" + characters2[0] + characters2[1] + characters2[2] + characters2[3] + "§6       ");
                                        }
                                    }
                                }


                            }
                        }.runTaskTimer(Main.getInst(), 0, 1);
                        return;
                    }
                }
            }


            if (!e.getClickedBlock().getType().equals(Material.AIR)) {
                if (hand.equals(EquipmentSlot.HAND)) {
                    if (itemInHand != null & itemInHand.getItemMeta() != null) {
                        if (itemInHand.getItemMeta().getDisplayName() != null && itemInHand.getItemMeta().getDisplayName().equals(CommandAdminItems.mobSpawnerTool.getItemMeta().getDisplayName())) {
                            File f = new File(Main.getInst().getDataFolder(), "mob_spawners.yml");
                            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
                            if (yaml.get("Spawners") != null) {
                                for (String s : yaml.getConfigurationSection("Spawners").getKeys(false)) {
                                    if (e.getClickedBlock() != null) {
                                        Location loc = e.getClickedBlock().getLocation();
                                        if (Main.StringToLoc(yaml.getString("Spawners." + s)).equals(loc)) {
                                            yaml.set("Spawners." + s, null);
                                            OnBlockPlace.playParticles(p, loc.clone().add(0.5, 1, 0.5));

                                            try {
                                                yaml.save(f);
                                            } catch (IOException exe) {
                                                exe.printStackTrace();
                                                return;
                                            }
                                            p.sendMessage("§a§lSukces: §7Zniszczono spawner.");
                                            return;
                                        }
                                    }
                                }
                            }

                            ArrayList<Integer> WszystkieKlucze = new ArrayList<>();
                            if (yaml.get("Spawners") != null) {
                                for (String s : yaml.getConfigurationSection("Spawners").getKeys(false)) {
                                    int slot = Integer.parseInt(s);
                                    WszystkieKlucze.add(slot);
                                }
                            }

                            for (int startSlot = 1; startSlot <= 10000; ) {
                                if (startSlot <= 10000) {
                                    if (WszystkieKlucze.contains(startSlot)) {
                                        startSlot++;
                                        continue;
                                    } else {
                                        Location loc = e.getClickedBlock().getLocation();
                                        yaml.set("Spawners." + startSlot, Main.LocToString(loc));
                                        OnBlockPlace.playParticles(p, loc.clone().add(0.5, 1, 0.5));

                                        try {
                                            yaml.save(f);
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }
                                        p.sendMessage("§a§lSukces: §7Postawiono spawner.");
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        /*
        if (e.getAction() == Action.RIGHT_CLICK_AIR) {
            ItemStack itemInHand = p.getInventory().getItemInMainHand();
            if (itemInHand.getType() == Material.COMPASS && itemInHand.getItemMeta().getDisplayName().equalsIgnoreCase("§8§m--------§b Konsola §8§m--------")) {
                Inventory inv = Bukkit.createInventory(null, 54, "§8Konsola");
                p.openInventory(inv);
                Methods.openInventoryProgram(p, inv);
            }
        }

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() == Material.CHEST) {
                e.setCancelled(true);
                Inventory inv = Bukkit.createInventory(null, 54, "§8Konsola");
                p.openInventory(inv);
                Methods.openInventoryProgram(p, inv);
                OnBlockPlace.robotLoc = e.getClickedBlock().getLocation();
            }
        }
         */


    }


    private Hologram getMainHologram(Location loc) {
        Hologram holos = HologramsAPI.createHologram(Main.getInst(), loc);
        for (Hologram holo : HologramsAPI.getHolograms(Main.getInst())) {
            if (holo.getLocation().equals(loc.clone().add(0.5, 2, 0.5))) {
                holos = holo;
                break;
            }
        }
        return holos;
    }
}
