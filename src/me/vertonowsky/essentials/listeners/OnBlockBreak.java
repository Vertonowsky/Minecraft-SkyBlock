package me.vertonowsky.essentials.listeners;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import me.vertonowsky.api.Holograms;
import me.vertonowsky.enums.JobType;
import me.vertonowsky.enums.QuestState;
import me.vertonowsky.events.PlayerExpGainEvent;
import me.vertonowsky.events.PlayerQuestCompleteEvent;
import me.vertonowsky.main.Main;
import me.vertonowsky.recipes.StoneGenerator;
import me.vertonowsky.user.User;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OnBlockBreak implements Listener {


    private static HashMap<ItemStack, Double> stoneDrops = new HashMap<>();


    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        User pUUID = User.get(p.getUniqueId());
        ItemStack itemInHand = p.getInventory().getItemInMainHand();

        if (e.getBlock().getType().equals(Material.LOG) || e.getBlock().getType().equals(Material.LOG_2)) {
            if (pUUID.getZadanie(5).equals(QuestState.W_TRAKCIE)) {
                Bukkit.getServer().getPluginManager().callEvent(new PlayerQuestCompleteEvent(pUUID, 5, QuestState.WYKONANE));
            }
        }

        if (e.getBlock().getType().equals(Material.CHEST)) {
            if (Main.warps.containsKey("moneyChest")) {
                if (e.getBlock().getLocation().equals(Main.warps.get("moneyChest"))) {
                    Main.warps.remove("moneyChest");
                    p.sendMessage("§a§lSukces: §7Zniszczono §6skrzynię§7.");
                    Holograms.reloadHolograms();
                    return;
                }
            }
        }

        if (e.getBlock().getType() == Material.ENDER_STONE) {
            if (!p.hasPermission("vert.*")) {
                if (!ASkyBlockAPI.getInstance().playerIsOnIsland(p)) {
                    e.setCancelled(true);
                    return;
                }
            }
            Location loc = e.getBlock().getLocation().add(0.5, 1, 0.5);
            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.CLOUD, true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0.5F, 0.5F, 0.5F, 0, 20, null);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

            e.setDropItems(false);

            ItemStack item = StoneGenerator.stoneGeneratorItem;
            p.getWorld().dropItemNaturally(e.getBlock().getLocation().add(0.5, 0, 0.5), item);

            p.sendMessage("§a§lSukces: §7Zniszczono §6stoniarkę§7.");
            return;
        }

        if (e.getBlock().getType() == Material.STONE) {
            if (p.getGameMode().equals(GameMode.SURVIVAL) || p.getGameMode().equals(GameMode.ADVENTURE)) {
                Material itemMaterial = p.getInventory().getItemInMainHand().getType();
                if (itemMaterial.equals(Material.WOOD_PICKAXE) || itemMaterial.equals(Material.STONE_PICKAXE) || itemMaterial.equals(Material.IRON_PICKAXE) || itemMaterial.equals(Material.GOLD_PICKAXE) || itemMaterial.equals(Material.DIAMOND_PICKAXE)) {
                    if (!p.hasPermission("vert.*")) {
                        if (!ASkyBlockAPI.getInstance().playerIsOnIsland(p)) {
                            return;
                        }
                    }
                    Random r = new Random();
                    boolean dropped = false;
                    double bonusChance = 0;

                    if (itemInHand.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                        int level = itemInHand.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                        if (level == 1) bonusChance += 0.1;
                        if (level == 2) bonusChance += 0.2;
                        if (level == 3) bonusChance += 0.3;

                        bonusChance += (double) (pUUID.getLevel(JobType.MINER)-1)/100;

                    }

                    for (Map.Entry<ItemStack, Double> entry : stoneDrops.entrySet()) {
                        if (!dropped) {
                            double chance = entry.getValue();
                            if (Main.doubleDrop) chance = chance*2;
                            if (entry.getKey().getType().equals(Material.LAPIS_ORE)) {
                                if (bonusChance >= 20) {
                                    bonusChance = 0.20;
                                    if (Main.doubleDrop) chance = chance/2;
                                }
                            }
                            if (chance + bonusChance > r.nextDouble() * 100) {
                                e.setDropItems(false);
                                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation().add(0.5, 0, 0.5), entry.getKey());
                                dropped = true;
                                break;
                            }
                        }
                    }
                }
            }












            if (!p.hasPermission("vert.*")) {
                if (!ASkyBlockAPI.getInstance().playerIsOnIsland(p)) {
                    return;
                }
            }
            if (e.getBlock().getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.ENDER_STONE)) {

                Location loc = e.getBlock().getLocation();
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        loc.getBlock().setType(Material.STONE);
                        cancel();
                        return;
                    }

                }.runTaskLater(Main.getInst(), 30);
            }
            pUUID.setStoneDestroyed(pUUID.getStoneDestroyed() + 1);
            Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(pUUID, JobType.MINER, 1));
            return;
        }

        if (e.getBlock().getType() == Material.COBBLESTONE) {
            if (ASkyBlockAPI.getInstance().playerIsOnIsland(p)) {
                pUUID.setCobblestoneDestroyed(pUUID.getCobblestoneDestroyed() + 1);
                Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(pUUID, JobType.MINER, 1));
                return;
            }
        }

        if (e.getBlock().getType() == Material.COAL_ORE) {
            if (ASkyBlockAPI.getInstance().playerIsOnIsland(p)) {
                pUUID.setCoalOreDestroyed(pUUID.getCoalOreDestroyed() + 1);
                Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(pUUID, JobType.MINER, 2));
                return;
            }
        }

        if (e.getBlock().getType() == Material.IRON_ORE) {
            if (ASkyBlockAPI.getInstance().playerIsOnIsland(p)) {
                pUUID.setIronOreDestroyed(pUUID.getIronOreDestroyed() + 1);
                Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(pUUID, JobType.MINER, 2));
                return;
            }
        }

        if (e.getBlock().getType() == Material.GOLD_ORE) {
            if (ASkyBlockAPI.getInstance().playerIsOnIsland(p)) {
                pUUID.setGoldOreDestroyed(pUUID.getGoldOreDestroyed() + 1);
                Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(pUUID, JobType.MINER, 2));
                return;
            }
        }

        if (e.getBlock().getType() == Material.DIAMOND_ORE) {
            if (ASkyBlockAPI.getInstance().playerIsOnIsland(p)) {
                pUUID.setDiamondOreDestroyed(pUUID.getDiamondOreDestroyed() + 1);
                Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(pUUID, JobType.MINER, 3));
                return;
            }
        }

        if (e.getBlock().getType() == Material.EMERALD_ORE) {
            if (ASkyBlockAPI.getInstance().playerIsOnIsland(p)) {
                pUUID.setEmeraldOreDestroyed(pUUID.getEmeraldOreDestroyed() + 1);
                Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(pUUID, JobType.MINER, 2));
                return;
            }
        }

        if (e.getBlock().getType() == Material.REDSTONE_ORE) {
            if (ASkyBlockAPI.getInstance().playerIsOnIsland(p)) {
                pUUID.setRedstoneOreDestroyed(pUUID.getRedstoneOreDestroyed() + 1);
                Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(pUUID, JobType.MINER, 2));
                return;
            }
        }

        if (e.getBlock().getType() == Material.QUARTZ_ORE) {
            if (ASkyBlockAPI.getInstance().playerIsOnIsland(p)) {
                pUUID.setQuartzOreDestroyed(pUUID.getQuartzOreDestroyed() + 1);
                Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(pUUID, JobType.MINER, 2));
                return;
            }
        }

        if (e.getBlock().getType() == Material.LAPIS_ORE) {
            if (ASkyBlockAPI.getInstance().playerIsOnIsland(p)) {
                pUUID.setLapisOreDestroyed(pUUID.getLapisOreDestroyed() + 1);
                Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(pUUID, JobType.MINER, 2));
                return;
            }
        }






        if (e.getBlock().getType() == Material.LOG) {
            if (ASkyBlockAPI.getInstance().playerIsOnIsland(p)) {
                Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(pUUID, JobType.LUMBERJACK, 2));

                short data = e.getBlock().getData();
                if (data == 0) pUUID.setOakDestroyed(pUUID.getOakDestroyed() + 1);
                if (data == 1) pUUID.setSpruceDestroyed(pUUID.getSpruceDestroyed() + 1);
                if (data == 2) pUUID.setBirchDestroyed(pUUID.getBirchDestroyed() + 1);
                if (data == 3) pUUID.setJungleDestroyed(pUUID.getJungleDestroyed() + 1);

                if (data > 3) data = 3;

                dropDoubleWood(e.getBlock().getLocation(), Material.LOG, data, pUUID);
            }
        }
        if (e.getBlock().getType() == Material.LOG_2) {
            if (ASkyBlockAPI.getInstance().playerIsOnIsland(p)) {
                Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(pUUID, JobType.LUMBERJACK, 2));

                short data = e.getBlock().getData();
                if (data == 0) pUUID.setAcaciaDestroyed(pUUID.getAcaciaDestroyed() + 1);
                if (data == 1) pUUID.setDarkOakDestroyed(pUUID.getDarkOakDestroyed() + 1);

                if (data > 1) data = 1;

                dropDoubleWood(e.getBlock().getLocation(), Material.LOG_2, data, pUUID);
                return;
            }
        }

        /*if (e.getBlock().getType() == Material.SIGN_POST || e.getBlock().getType() == Material.WALL_SIGN) {
            if (e.getBlock().getState() instanceof Sign) {
                Sign sign = (Sign) e.getBlock().getState();
                if (sign.getLine(0).equals("§2[SKLEP]")) {
                    if (!p.hasPermission("vert.shop.create")) {
                        e.setCancelled(true);
                        p.sendMessage("§c§lBłąd: §7Nie masz uprawnień do niszczenia sklepu.");
                        return;
                    }
                    if (!p.hasPermission("vert.shop.admin")) {
                        if (!sign.getLine(3).equals("§8" + p.getName())) {
                            e.setCancelled(true);
                            return;
                        }
                    }

                    Double price;
                    int amount;
                    int id ;
                    short data ;
                    int shopSize;

                    /*
                       Line2





                    String[] line2 = sign.getLine(1).split(":");
                    if (API.isDouble(line2[1])) {
                        price = Double.parseDouble(line2[1]);
                    }


                    /*
                        Line 3

                        Amount - ID - Data - ShopSize





                    int[] returnedData = OnSignChange.getItemDetailsFromString(sign.getLine(2));
                    amount = returnedData[0];
                    id = returnedData[1];
                    data = Short.parseShort(returnedData[2] + "");
                    shopSize = returnedData[3];


                    ItemStack itemStack = new ItemStack(Material.getMaterial(id), 1, data);
                    int itemsLeft = InventoryAPI.addItemToInventory(p, itemStack, (int) (shopSize % amount));
                    p.sendMessage(itemsLeft + "");
                    if (itemsLeft > 0) {
                        e.setCancelled(true);
                        if (itemsLeft == shopSize) {
                            p.sendMessage("§c§lBłąd: §7Nie posiadasz wolnego miejsca w ekwipunku.");
                            return;
                        }

                        p.sendMessage("§a§lSukces: §7Wyjęto część przedmiotów ze sklepu.");
                    }

                    String[] s = sign.getLine(2).split(":");
                    sign.setLine(2, s[0] + ":" + itemsLeft);
                    sign.update();

                    if (itemsLeft <= 0) {
                        p.sendMessage("§a§lSukces: §7Zniszczono sklep.");
                    }
                    return;
                }
            }
        }
        */


    }

    private void dropDoubleWood(Location loc, Material material, short durability, User pUUID) {
        Random r = new Random();
        double bonusChance = (double) (pUUID.getLevel(JobType.LUMBERJACK) / 25) + 2;
        if (bonusChance > r.nextDouble() * 100) {
            loc.getWorld().dropItemNaturally(loc.add(0.5, 0, 0.5), new ItemStack(material, 1, (short) durability));
        }
        return;
    }


    public static void setStoneDrops() {
        stoneDrops.put(new ItemStack(Material.IRON_ORE, 1), (double)1);
        stoneDrops.put(new ItemStack(Material.GOLD_ORE, 1), (double)1);
        stoneDrops.put(new ItemStack(Material.COAL_ORE, 1), (double)2);
        stoneDrops.put(new ItemStack(Material.QUARTZ_ORE, 1), (double)2);
        stoneDrops.put(new ItemStack(Material.REDSTONE_ORE, 1), (double)2);
        stoneDrops.put(new ItemStack(Material.LAPIS_ORE, 1), 0.03);
        stoneDrops.put(new ItemStack(Material.STONE, 1), (double)4);
    }

    public static HashMap<ItemStack, Double> getStoneDrops() {
        return stoneDrops;
    }
}
