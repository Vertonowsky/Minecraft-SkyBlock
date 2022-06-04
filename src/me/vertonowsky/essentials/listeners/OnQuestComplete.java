package me.vertonowsky.essentials.listeners;

import me.vertonowsky.api.Scoreboard;
import me.vertonowsky.enums.JobType;
import me.vertonowsky.enums.QuestState;
import me.vertonowsky.events.PlayerExpGainEvent;
import me.vertonowsky.events.PlayerQuestCompleteEvent;
import me.vertonowsky.inventory.Zadania;
import me.vertonowsky.main.Main;
import me.vertonowsky.user.User;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

public class OnQuestComplete implements Listener {


    @EventHandler
    public void onQuestComplete(PlayerQuestCompleteEvent e) {
        User u = e.getUser();

        u.setZadanie(e.getQuestId(), e.getQuestState());

        if (Bukkit.getPlayer(u.getUuid()) != null) {
            Player p = Bukkit.getPlayer(u.getUuid());
            if (e.getQuestState() == QuestState.WYKONANE) {
                Zadania.bossBar.addPlayer(p);
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                Location loc = p.getLocation().clone().add(0, 2.5, 0);

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

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInst(), new Runnable() {
                    public void run() {
                        if (Zadania.bossBar != null) {
                            Zadania.bossBar.removePlayer(p);
                        }
                    }
                }, 5 * 20);
            }


            if (e.getQuestState() == QuestState.ZAKONCZONE) {
                Integer money = 0;
                Integer exp = 0;
                ItemStack item = null;
                if (e.getQuestId() == 1) {
                    money = 25;
                    exp = 25;
                    item = new ItemStack(Material.BROWN_MUSHROOM, 1);
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(u, JobType.MINER, exp));
                }

                if (e.getQuestId() == 2) {
                    money = 25;
                    exp = 25;
                    item = new ItemStack(Material.RED_MUSHROOM, 1);
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(u, JobType.MINER, exp));
                }

                if (e.getQuestId() == 3) {
                    money = 40;
                    exp = 25;
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(u, JobType.LUMBERJACK, exp));
                }

                if (e.getQuestId() == 4) {
                    money = 60;
                    exp = 30;
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(u, JobType.WARRIOR, exp));
                }

                if (e.getQuestId() == 5) {
                    money = 20;
                    exp = 15;
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(u, JobType.LUMBERJACK, exp));
                }

                if (e.getQuestId() == 6) {
                    money = 2000;
                    exp = 2500;
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(u, JobType.WARRIOR, exp));
                }

                if (e.getQuestId() == 7) {
                    money = 1500;
                    exp = 220;
                    item = new ItemStack(Material.NETHER_STALK, 1);
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(u, JobType.WARRIOR, exp));
                }

                if (e.getQuestId() == 8) {
                    money = 20;
                    exp = 10;
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(u, JobType.MINER, exp));
                }

                if (e.getQuestId() == 9) {
                    money = 250;
                    exp = 400;
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(u, JobType.MINER, exp));
                }

                if (e.getQuestId() == 10) {
                    money = 420;
                    exp = 800;
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(u, JobType.ANGLER, exp));
                }

                if (e.getQuestId() == 11) {
                    money = 150;
                    exp = 250;
                    item = new ItemStack(Material.DIAMOND, 1);
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(u, JobType.MINER, exp));
                }

                if (e.getQuestId() == 12) {
                    money = 200;
                    exp = 350;
                    item = new ItemStack(Material.DIAMOND, 1);
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(u, JobType.MINER, exp));
                }

                if (e.getQuestId() == 13) {
                    money = 300;
                    exp = 450;
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(u, JobType.WARRIOR, exp));
                }

                if (e.getQuestId() == 14) {
                    money = 120;
                    exp = 100;
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerExpGainEvent(u, JobType.WARRIOR, exp));
                }



                u.setMoney(u.getMoney() + money);
                Scoreboard.setScoreboard(p);
                p.sendMessage("§8§m-----------------------------");
                p.sendMessage("");
                p.sendMessage("§8§l>> §b§lWykonano zadanie.");
                p.sendMessage("");
                p.sendMessage("§8§l>> §8[§7$§8] §a+" + money + "§7");
                p.sendMessage("§8§l>> §8[§7exp§8] §a+" + exp);
                if (item != null) {
                    p.sendMessage("§8§l>> §8[§7item§8] §a+" + item.getAmount() + "x" + OnInventoryClick.getItemName(item));
                    u.giveItem(item, true);
                }
                p.sendMessage("");
                p.sendMessage("§8§m-----------------------------");
            }
        }
    }
}
