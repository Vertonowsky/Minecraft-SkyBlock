package me.vertonowsky.essentials.listeners;

import me.vertonowsky.enums.JobType;
import me.vertonowsky.enums.QuestState;
import me.vertonowsky.events.PlayerExpGainEvent;
import me.vertonowsky.events.PlayerQuestCompleteEvent;
import me.vertonowsky.main.Main;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OnEntityDeath implements Listener {

    private static HashMap<ItemStack, Double> skeletonDrops = new HashMap<ItemStack, Double>();
    private static HashMap<ItemStack, Double> creeperDrops = new HashMap<ItemStack, Double>();
    private static HashMap<ItemStack, Double> zombieDrops = new HashMap<ItemStack, Double>();
    private static HashMap<ItemStack, Double> spiderDrops = new HashMap<ItemStack, Double>();
    private static HashMap<ItemStack, Double> pigmanDrops = new HashMap<ItemStack, Double>();
    private static HashMap<ItemStack, Double> endermanDrops = new HashMap<ItemStack, Double>();
    private static HashMap<ItemStack, Double> magmaDrops = new HashMap<ItemStack, Double>();


    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().getKiller() == null) {
            dropOnKill(e.getEntity().getLocation(), null, e.getEntityType());
        }

        if (e.getEntity().getKiller() != null) {
            Player p = e.getEntity().getKiller();
            User pUUID = User.get(p.getName());

            dropOnKill(e.getEntity().getLocation(), p, e.getEntityType());


            if (e.getEntity() instanceof Skeleton) {
                pUUID.setSkeletonKilled(pUUID.getSkeletonKilled() + 1);
                Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(pUUID, JobType.WARRIOR, 3));
                return;
            }

            if (e.getEntity() instanceof Zombie || e.getEntity() instanceof ZombieVillager) {
                if (e.getEntity() instanceof PigZombie) {
                    pUUID.setPigmanKilled(pUUID.getPigmanKilled() + 1);
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(pUUID, JobType.WARRIOR, 4));


                    if (pUUID.getTotalLevel() >= 52) {
                        if (p.getInventory().getItemInMainHand().getType().equals(Material.GOLD_SWORD)) {
                            if (pUUID.getZadanie(7).equals(QuestState.W_TRAKCIE)) {
                                Bukkit.getServer().getPluginManager().callEvent(new PlayerQuestCompleteEvent(pUUID, 7, QuestState.WYKONANE));
                                return;
                            }
                        }
                    }
                } else {
                    pUUID.setZombieKilled(pUUID.getZombieKilled() + 1);
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(pUUID, JobType.WARRIOR, 3));
                }
                return;
            }

            if (e.getEntity() instanceof Spider) {
                pUUID.setSpiderKilled(pUUID.getSpiderKilled() + 1);
                Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(pUUID, JobType.WARRIOR, 3));
                return;
            }

            if (e.getEntity() instanceof Creeper) {
                pUUID.setCreeperKilled(pUUID.getCreeperKilled() + 1);
                Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(pUUID, JobType.WARRIOR, 4));
                return;
            }

            if (e.getEntity() instanceof Witch) {
                pUUID.setWitchKilled(pUUID.getWitchKilled() + 1);
                Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(pUUID, JobType.WARRIOR, 4));
                return;
            }

            if (e.getEntity() instanceof Enderman) {
                pUUID.setEndermanKilled(pUUID.getEndermanKilled() + 1);
                Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(pUUID, JobType.WARRIOR, 4));
                return;
            }

            if (e.getEntity() instanceof Slime) {
                pUUID.setSlimeKilled(pUUID.getSlimeKilled() + 1);
                Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(pUUID, JobType.WARRIOR, 5));
                return;
            }

            if (e.getEntity() instanceof MagmaCube) {
                pUUID.setMagmaKilled(pUUID.getMagmaKilled() + 1);
                Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(pUUID, JobType.WARRIOR, 5));
                return;
            }
        }

    }


    private void dropOnKill(Location loc, Player p, EntityType entityType) {
        HashMap<ItemStack, Double> hashMap = new HashMap<>();

        if (entityType.equals(EntityType.SKELETON)) hashMap.putAll(skeletonDrops);
        if (entityType.equals(EntityType.CREEPER)) hashMap.putAll(creeperDrops);
        if (entityType.equals(EntityType.ZOMBIE)) hashMap.putAll(zombieDrops);
        if (entityType.equals(EntityType.SPIDER)) hashMap.putAll(spiderDrops);
        if (entityType.equals(EntityType.PIG_ZOMBIE)) hashMap.putAll(pigmanDrops);
        if (entityType.equals(EntityType.ENDERMAN)) hashMap.putAll(endermanDrops);
        if (entityType.equals(EntityType.MAGMA_CUBE)) hashMap.putAll(magmaDrops);

        if (hashMap.size() == 0) {
            return;
        }


        Random r = new Random();
        double bonusChance = 0;

        if (p != null) {
            ItemStack itemInHand = p.getInventory().getItemInMainHand();
            if (itemInHand.containsEnchantment(Enchantment.LOOT_BONUS_MOBS)) {
                int level = itemInHand.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);

                if (level == 1) bonusChance += 0.1;
                if (level == 2) bonusChance += 0.2;
                if (level == 3) bonusChance += 0.3;

                bonusChance += (double) User.get(p.getName()).getLevel(JobType.WARRIOR) /80;
            }
        }


        for (Map.Entry<ItemStack, Double> entry : hashMap.entrySet()) {
            double chance = entry.getValue();
            if (Main.doubleDrop) chance = chance*2;
            if (entry.getKey().getType().equals(Material.LAPIS_ORE)) {
                if (p != null) {
                    if (User.get(p.getUniqueId()) != null) {
                        if (User.get(p.getUniqueId()).getZadanie(14) != null) {
                            if (User.get(p.getUniqueId()).getZadanie(14).equals(QuestState.W_TRAKCIE)) {
                                Bukkit.getServer().getPluginManager().callEvent(new PlayerQuestCompleteEvent(User.get(p.getUniqueId()), 14, QuestState.WYKONANE));
                                return;
                            }
                        }
                    }
                }

                if (bonusChance >= 20) {
                    bonusChance = 0.20;
                    if (Main.doubleDrop) chance = chance/2;
                }
            }
            if (chance + bonusChance > r.nextDouble() * 100) {
                loc.getWorld().dropItemNaturally(loc.add(0.5, 0, 0.5), entry.getKey());
            }
        }
    }


    private static HashMap<ItemStack, Double> addOresToDrop() {
        HashMap<ItemStack, Double> toReturn = new HashMap();
        toReturn.put(new ItemStack(Material.LAPIS_ORE, 1), 0.08);
        toReturn.put(new ItemStack(Material.DIAMOND_ORE, 1), (double)1);
        toReturn.put(new ItemStack(Material.IRON_ORE, 1), (double)2);
        toReturn.put(new ItemStack(Material.GOLD_ORE, 1), (double)2);
        toReturn.put(new ItemStack(Material.COAL_ORE, 1), (double)3);
        toReturn.put(new ItemStack(Material.REDSTONE_ORE, 1), (double)4);
        toReturn.put(new ItemStack(Material.QUARTZ_ORE, 1), (double)4);

        return toReturn;
    }





    public static void setSkeletonDrops() {
        skeletonDrops.putAll(addOresToDrop());
        skeletonDrops.put(new ItemStack(Material.CACTUS, 1), (double)5);
        skeletonDrops.put(new ItemStack(Material.SUGAR_CANE, 1), (double)5);
        skeletonDrops.put(new ItemStack(Material.VINE, 1), (double)5);
        skeletonDrops.put(new ItemStack(Material.INK_SACK, 1, (short) 3), (double)5);
        skeletonDrops.put(new ItemStack(Material.GRAVEL, 1), (double)5);
        skeletonDrops.put(new ItemStack(Material.CLAY_BALL, 1), (double)5);
        skeletonDrops.put(new ItemStack(Material.CLAY_BRICK, 1), (double)5);
        skeletonDrops.put(new ItemStack(Material.PACKED_ICE, 1), (double)5);
        skeletonDrops.put(new ItemStack(Material.SAND, 1), (double)6);
    }

    public static HashMap<ItemStack, Double> getSkeletonDrops() {
        return skeletonDrops;
    }

    public static void setCreeperDrops() {
        creeperDrops.putAll(addOresToDrop());
        creeperDrops.put(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 1), (double)5);
        creeperDrops.put(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 2), (double)5);
        creeperDrops.put(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 3), (double)5);
        creeperDrops.put(new ItemStack(Material.MOSSY_COBBLESTONE, 1), (double)5);
        creeperDrops.put(new ItemStack(Material.IRON_INGOT, 1), (double)4);
        creeperDrops.put(new ItemStack(Material.QUARTZ, 1), (double)4);
        creeperDrops.put(new ItemStack(Material.SAND, 1), (double)6);
    }

    public static void setZombieDrops() {
        zombieDrops.putAll(addOresToDrop());
        zombieDrops.put(new ItemStack(Material.MYCEL, 1), (double)8);
        zombieDrops.put(new ItemStack(Material.SAND, 1), (double)6);
        zombieDrops.put(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 1), (double)5);
        zombieDrops.put(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 2), (double)5);
        zombieDrops.put(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 3), (double)5);
        zombieDrops.put(new ItemStack(Material.BEETROOT, 1), (double)5);
        zombieDrops.put(new ItemStack(Material.POTATO, 1), (double)5);
        zombieDrops.put(new ItemStack(Material.CARROT, 1), (double)5);
        zombieDrops.put(new ItemStack(Material.CLAY_BALL, 1), (double)5);
        zombieDrops.put(new ItemStack(Material.CLAY_BRICK, 1), (double)5);
        zombieDrops.put(new ItemStack(Material.IRON_INGOT, 1), (double)4);
        zombieDrops.put(new ItemStack(Material.QUARTZ, 1), (double)4);
    }

    public static void setSpiderDrops() {
        spiderDrops.putAll(addOresToDrop());
        spiderDrops.put(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 1), (double)5);
        spiderDrops.put(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 2), (double)5);
        spiderDrops.put(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 3), (double)5);
        spiderDrops.put(new ItemStack(Material.MOSSY_COBBLESTONE, 1), (double)5);
        spiderDrops.put(new ItemStack(Material.MYCEL, 1), (double)10);
        spiderDrops.put(new ItemStack(Material.WEB, 1), (double)5);
        spiderDrops.put(new ItemStack(Material.MELON_SEEDS, 1), (double)5);
        spiderDrops.put(new ItemStack(Material.PUMPKIN_SEEDS, 1), (double)5);
    }

    public static void setPigmanDrops() {
        pigmanDrops.put(new ItemStack(Material.DIAMOND, 1), (double)1);
        pigmanDrops.put(new ItemStack(Material.GOLD_ORE, 1), (double)4);
        pigmanDrops.put(new ItemStack(Material.GOLD_INGOT, 1), (double)4);
        pigmanDrops.put(new ItemStack(Material.NETHER_BRICK_ITEM, 1), (double)4);
        pigmanDrops.put(new ItemStack(Material.NETHER_BRICK, 1), (double)4);
        pigmanDrops.put(new ItemStack(Material.RED_NETHER_BRICK, 1), (double)4);
        pigmanDrops.put(new ItemStack(Material.NETHER_BRICK_STAIRS, 1), (double)4);
        pigmanDrops.put(new ItemStack(Material.NETHERRACK, 1), (double)4);
        pigmanDrops.put(new ItemStack(Material.SOUL_SAND, 1), (double)4);
        pigmanDrops.put(new ItemStack(Material.PRISMARINE_CRYSTALS, 1), (double)4);
        pigmanDrops.put(new ItemStack(Material.PRISMARINE_SHARD, 1), (double)4);
        pigmanDrops.put(new ItemStack(Material.GLOWSTONE_DUST, 1), (double)6);
    }

    public static void setEndermanDrops() {
        endermanDrops.put(new ItemStack(Material.DIAMOND, 1), (double)1);
        endermanDrops.put(new ItemStack(Material.DIAMOND_ORE, 1), (double)1);
        endermanDrops.put(new ItemStack(Material.ENDER_CHEST, 1), (double)5);
        endermanDrops.put(new ItemStack(Material.BLAZE_ROD, 1), (double)5);
    }

    public static void setMagmaDrops() {
        magmaDrops.put(new ItemStack(Material.EMERALD, 1), (double)7);
    }

    public static HashMap<ItemStack, Double> getCreeperDrops() {
        return creeperDrops;
    }

    public static HashMap<ItemStack, Double> getZombieDrops() {
        return zombieDrops;
    }

    public static HashMap<ItemStack, Double> getSpiderDrops() {
        return spiderDrops;
    }

    public static HashMap<ItemStack, Double> getPigmanDrops() {
        return pigmanDrops;
    }

    public static HashMap<ItemStack, Double> getEndermanDrops() {
        return endermanDrops;
    }

    public static HashMap<ItemStack, Double> getMagmaDrops() {
        return magmaDrops;
    }

    public static void setMobDrops() {
        setSkeletonDrops();
        setCreeperDrops();
        setZombieDrops();
        setSpiderDrops();
        setPigmanDrops();
        setEndermanDrops();
        setMagmaDrops();
    }
}
