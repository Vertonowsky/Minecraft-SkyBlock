package me.vertonowsky.essentials.listeners;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import me.vertonowsky.api.Holograms;
import me.vertonowsky.enums.QuestState;
import me.vertonowsky.essentials.commands.CommandAdminItems;
import me.vertonowsky.essentials.commands.CommandDaily;
import me.vertonowsky.events.PlayerQuestCompleteEvent;
import me.vertonowsky.main.Main;
import me.vertonowsky.recipes.StoneGenerator;
import me.vertonowsky.user.User;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockAction;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OnBlockPlace implements Listener {


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        User pUUID = User.get(p.getUniqueId());
        ItemStack itemInHand = p.getInventory().getItemInMainHand();
        if (e.getBlock().getType().equals(Material.CROPS)) {
            if (pUUID.getZadanie(2).equals(QuestState.W_TRAKCIE)) {
                Bukkit.getServer().getPluginManager().callEvent(new PlayerQuestCompleteEvent(pUUID, 2, QuestState.WYKONANE));
            }
        }

        if (itemInHand.getItemMeta() == null && itemInHand.getItemMeta().getDisplayName() == null) return;

        if (itemInHand.getItemMeta().getDisplayName().equals(StoneGenerator.stoneGeneratorItem.getItemMeta().getDisplayName())) {
            if (itemInHand.getItemMeta().getLore() != null && itemInHand.getItemMeta().getLore().equals(StoneGenerator.stoneGeneratorItem.getItemMeta().getLore())) {
                if (e.getBlock().getType().equals(Material.ENDER_STONE)) {
                    if (!p.hasPermission("vert.*")) {
                        if (!ASkyBlockAPI.getInstance().playerIsOnIsland(p)) {
                            e.setCancelled(true);
                            return;
                        }
                    }

                    Location loc = e.getBlock().getLocation().clone().add(0, 1, 0);
                    if (!loc.getBlock().getType().equals(Material.AIR)) {
                        p.sendMessage("§c§lBłąd: §7Nie możesz postawić stoniarki w tym miejscu.");
                        e.setCancelled(true);
                        e.setBuild(false);
                        return;
                    }
                    loc.getBlock().setType(Material.STONE);
                    Location loc2 = e.getBlock().getLocation().add(0.5, 1, 0.5);
                    playParticles(p, loc2);
                    p.sendMessage("§a§lSukces: §7Postawiono §6stoniarkę§7.");
                    return;
                }
            }
        }

        if (itemInHand.getItemMeta().getDisplayName().equals(CommandAdminItems.moneyChestItem.getItemMeta().getDisplayName())) {
            if (e.getBlock().getType().equals(Material.CHEST)) {
                if (!p.hasPermission("vert.*")) {
                    if (!ASkyBlockAPI.getInstance().playerIsOnIsland(p)) {
                        e.setCancelled(true);
                        return;
                    }
                }

                Location loc = e.getBlock().getLocation().add(0.5, 1, 0.5);
                playParticles(p, loc);
                if (Main.warps.containsKey("moneyChest")) {
                    Main.warps.remove("moneyChest");
                }

                Main.warps.put("moneyChest", e.getBlock().getLocation());
                p.sendMessage("§a§lSukces: §7Postawiono §6skrzynię§7.");
                Holograms.reloadHolograms();
                return;
            }
        }

        if (itemInHand.getItemMeta().getDisplayName().equals(CommandDaily.dailyChestItem.getItemMeta().getDisplayName())) {
            if (e.getBlock().getType().equals(Material.CHEST)) {
                if (!p.hasPermission("vert.*")) {
                    if (!ASkyBlockAPI.getInstance().playerIsOnIsland(p)) {
                        e.setCancelled(true);
                        p.sendMessage("§c§lBłąd: §7Nie możesz użyć tego przedmiotu w tym miejscu!");
                        return;
                    }
                }

                Location loc = e.getBlockPlaced().getLocation();
                PacketPlayOutBlockAction packet = new PacketPlayOutBlockAction(new BlockPosition(loc.getX(), loc.getY(), loc.getZ()), CraftMagicNumbers.getBlock(e.getBlockPlaced()), 1, 1);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                loc.add(0.5, 1, 0.5);
                p.getLocation().getWorld().playSound(loc, Sound.BLOCK_CHEST_OPEN, 1, 1);


                final Hologram hologram = HologramsAPI.createHologram(Main.getInst(), loc);
                hologram.getVisibilityManager().setVisibleByDefault(false);
                hologram.getVisibilityManager().showTo(p);
                hologram.appendItemLine(new ItemStack(Material.PAPER, 1));


                new BukkitRunnable() {
                    int ticks = 0;

                    public void run() {
                        if (ticks == 30) {
                            if (!hologram.isDeleted()) hologram.delete();

                            e.getBlock().setType(Material.AIR);
                            p.getLocation().getWorld().playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                            PacketPlayOutWorldParticles packet2 = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_NORMAL, true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0.5F, 0.5F, 0.5F, 0, 70, null);
                            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet2);

                            dropReward(loc);

                            cancel();
                        }
                        ticks++;
                        if (ticks < 20) hologram.teleport(hologram.getLocation().add(0, 0.02, 0));
                        if (ticks > 20 && ticks < 30)
                            hologram.teleport(hologram.getLocation().add(0, 0.01, 0));

                    }
                }.runTaskTimer(Main.getInst(), 0, 1);
                return;
            }
        }
    }

    public static void playParticles(Player p, Location loc) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.VILLAGER_HAPPY, true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0.5F, 0.5F, 0.5F, 0, 20, null);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        p.getLocation().getWorld().playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
    }


    private void dropReward(Location loc) {
        Random r = new Random();
        Random r2 = new Random();
        List<ItemStack> rewards = new ArrayList<>();

        ItemStack itemStack = new ItemStack(Material.GHAST_TEAR, 1);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("§aDusze: " + r.nextInt(4));
        itemStack.setItemMeta(meta);
        rewards.add(itemStack);

        ItemStack itemStack2 = new ItemStack(Material.PAPER, 1);
        ItemMeta meta2 = itemStack2.getItemMeta();
        meta2.setDisplayName("§aKasa: " + r2.nextInt(96));
        itemStack2.setItemMeta(meta2);
        rewards.add(itemStack2);

        rewards.add(new ItemStack(Material.RED_ROSE, r.nextInt(10) + 1, (short)2 ));
        rewards.add(new ItemStack(Material.NETHER_WARTS, r.nextInt(2) + 1));
        rewards.add(new ItemStack(Material.CARROT_ITEM, r.nextInt(6) + 1));
        rewards.add(new ItemStack(Material.BREAD, r.nextInt(3) + 1));
        rewards.add(new ItemStack(Material.APPLE, r.nextInt(3) + 1));
        rewards.add(new ItemStack(Material.BAKED_POTATO, r.nextInt(3) + 1));
        rewards.add(new ItemStack(Material.DIAMOND, 1));
        rewards.add(new ItemStack(Material.BONE, r.nextInt(5) + 1));
        rewards.add(new ItemStack(Material.IRON_INGOT, r.nextInt(5) + 1));
        rewards.add(new ItemStack(Material.REDSTONE, r.nextInt(12) + 1));
        rewards.add(new ItemStack(Material.LAPIS_ORE, 1));
        rewards.add(new ItemStack(Material.QUARTZ, r.nextInt(7) + 1));
        rewards.add(new ItemStack(Material.LEATHER, r.nextInt(5) + 1));
        rewards.add(new ItemStack(Material.SLIME_BALL, r.nextInt(2) + 1));
        rewards.add(new ItemStack(Material.GLOWSTONE, r.nextInt(3) + 1));
        rewards.add(new ItemStack(Material.SUGAR, r.nextInt(15) + 1));
        rewards.add(new ItemStack(Material.GOLD_NUGGET, r.nextInt(9) + 1));
        rewards.add(new ItemStack(Material.GOLD_NUGGET, r.nextInt(9) + 1));


        List<Integer> finalRewards = new ArrayList<>();


        for (int i = 0; i < 100; i++) {
            if (finalRewards.size() == 3) {
                break;
            }

            int randomInt = r.nextInt(rewards.size());
            if (!finalRewards.contains(randomInt)) {
                finalRewards.add(randomInt);
            }
        }


        for (int item : finalRewards) {
            loc.getWorld().dropItemNaturally(loc, rewards.get(item));
        }

    }

}
