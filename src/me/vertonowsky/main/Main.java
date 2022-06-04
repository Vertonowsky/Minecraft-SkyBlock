package me.vertonowsky.main;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.vertonowsky.api.API;
import me.vertonowsky.api.Holograms;
import me.vertonowsky.api.Scoreboard;
import me.vertonowsky.essentials.commands.*;
import me.vertonowsky.essentials.listeners.*;
import me.vertonowsky.mysql.Help;
import me.vertonowsky.mysql.MySQL;
import me.vertonowsky.mysql.Quests;
import me.vertonowsky.mysql.Users;
import me.vertonowsky.permissions.PermissionVert;
import me.vertonowsky.recipes.StoneGenerator;
import me.vertonowsky.user.User;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class Main extends JavaPlugin {
    private static Main instance;


    public static String logo = "§8[SkyBlock] ";
    public static Location spawn;
    public static HashMap<String, Location> warps = new HashMap<String, Location>();
    public static HashMap<Integer, Long> maxExpTable = new HashMap<Integer, Long>();

    public static List<String> playersToCheck = new ArrayList<>();
    public static HashMap<String, Long> connectionWait = new HashMap<String, Long>();
    public static HashMap<String, Long> antiBot = new HashMap<String, Long>();
    public static long antiBotDelay;


    public static boolean administrate = false;
    public static boolean czatStatus = true;
    public static boolean doubleExp = false;
    public static boolean doubleDrop = false;
    public static boolean spawnersWork = false;

    private int tabList = 0;




    public static Main getInst() {
        return instance;
    }



    @Override
    public void onEnable() {
        instance = this;

        //player_knockback

        //lever turn on

        //brewing_stand_brew

        //end_gateaway_spawn

        //entity_endereye_death

        //entity_horse_armor

        //entity_illusion_illager_mirror_move

        //BLOCK_NOTE_CHIME


        /*
            Load data from MySQL
         */
        MySQL.openConnection();

        if (!MySQL.isConnected()) {
            Bukkit.getConsoleSender().sendMessage(logo + "§bCan't connect to the database. Shutting down..");
            Bukkit.shutdown();
            return;
        }


        Users.checkTableUsersSkyBlock();
        Users.loadDataUsersSkyBlock();
        Users.loadDataUsersGeneral();
        Quests.checkTableQuestsAll();
        Quests.checkTableQuestsPlayer();
        Help.checkTableQuestsAll();

        MySQL.closeConnection();





        /*
            Load data from files
         */
        if (!getDataFolder().exists()) getDataFolder().mkdir();

        File f = new File(getDataFolder(), "config.yml");
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);

        if (yaml.getString("administrate") != null) {
            administrate = yaml.getBoolean("administrate");
        }

        if (yaml.getString("doubleExp") != null) {
            doubleExp = yaml.getBoolean("doubleExp");
        }

        if (yaml.getString("doubleDrop") != null) {
            doubleDrop = yaml.getBoolean("doubleDrop");
        }

        if (yaml.getString("czatStatus") != null) {
            czatStatus = yaml.getBoolean("czatStatus");
        }

        if (yaml.getString("spawn") != null) {
            spawn = StringToLoc(yaml.getString("spawn"));
        }

        if (yaml.getString("spawnersWork") != null) {
            spawnersWork = yaml.getBoolean("spawnersWork");
        }




        File f2 = new File(getDataFolder(), "warps.yml");
        YamlConfiguration yaml2 = YamlConfiguration.loadConfiguration(f2);

        if (yaml2.get("warps") != null) {
            for (String name : yaml2.getConfigurationSection("warps").getKeys(false)) {
                warps.put(name, StringToLoc(yaml2.getString("warps." + name)));
            }
        }

        File f3 = new File(getDataFolder(), "max_exp.yml");
        YamlConfiguration yaml3 = YamlConfiguration.loadConfiguration(f3);
        if (yaml3.get("MaxExp") != null) {
            for (String name : yaml3.getConfigurationSection("MaxExp").getKeys(false)) {
                maxExpTable.put(Integer.parseInt(name), yaml3.getLong("MaxExp." + name));
            }
        }




        /*
            Register permissions
         */
        PermissionVert.getDefaultRank();


        /*
            Register custom recipes
         */
        StoneGenerator items = new StoneGenerator();
        items.recipeStoneGenerator();
        CommandAdminItems.loadChests();

        /*
            Server list decorations
         */
        ServerListPingEvent.changePlayerCount();
        Bukkit.getPluginManager().registerEvents(new ServerListPingEvent(), this);


        /*
            Register Listeners
         */
        Bukkit.getPluginManager().registerEvents(new OnInventoryClick(), this);
        Bukkit.getPluginManager().registerEvents(new OnInventoryClose(), this);
        Bukkit.getPluginManager().registerEvents(new OnEntityClick(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerClick(), this);
        Bukkit.getPluginManager().registerEvents(new OnChat(), this);
        Bukkit.getPluginManager().registerEvents(new OnItemCraft(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerTeleport(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerQuit(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerLogin(), this);
        Bukkit.getPluginManager().registerEvents(new OnAuthMeLogin(), this);
        Bukkit.getPluginManager().registerEvents(new OnCitizenClick(), this);

        Bukkit.getPluginManager().registerEvents(new OnPlayerDeath(), this);
        Bukkit.getPluginManager().registerEvents(new OnRespawn(), this);
        Bukkit.getPluginManager().registerEvents(new OnEntityDeath(), this);
        Bukkit.getPluginManager().registerEvents(new OnEntityDamageByEntity(), this);
        Bukkit.getPluginManager().registerEvents(new OnEntitySpawn(), this);
        Bukkit.getPluginManager().registerEvents(new OnFish(), this);

        Bukkit.getPluginManager().registerEvents(new OnBucketEmptyEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnBlockBreak(), this);
        Bukkit.getPluginManager().registerEvents(new OnBlockPlace(), this);
        Bukkit.getPluginManager().registerEvents(new OnSignChange(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerLevelUp(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerExpGain(), this);
        Bukkit.getPluginManager().registerEvents(new OnQuestComplete(), this);

        Bukkit.getPluginManager().registerEvents(new OnIslandEnter(), this);
        Bukkit.getPluginManager().registerEvents(new OnRegionEnter(), this);

        Bukkit.getPluginManager().registerEvents(new OnResourcePackSelect(), this);
        Bukkit.getPluginManager().registerEvents(new OnStructureGrow(), this);
        Bukkit.getPluginManager().registerEvents(new OnItemConsume(), this);
        Bukkit.getPluginManager().registerEvents(new OnEntityBreed(), this);
        Bukkit.getPluginManager().registerEvents(new OnItemPickUp(), this);
        Bukkit.getPluginManager().registerEvents(new OnItemEnchant(), this);

        /*
            Register commands
         */
        getCommand("gamemode").setExecutor(new CommandGamemode());
        getCommand("vanish").setExecutor(new CommandVanish());
        getCommand("heal").setExecutor(new CommandHeal());
        getCommand("sun").setExecutor(new CommandWeather());
        getCommand("rain").setExecutor(new CommandWeather());
        getCommand("day").setExecutor(new CommandTime());
        getCommand("night").setExecutor(new CommandTime());
        getCommand("teleport").setExecutor(new CommandTeleport());
        getCommand("tpall").setExecutor(new CommandTeleport());
        getCommand("clear").setExecutor(new CommandClear());
        getCommand("msg").setExecutor(new CommandMsg());
        getCommand("r").setExecutor(new CommandMsg());
        getCommand("invsee").setExecutor(new CommandSeeInventory());
        getCommand("endersee").setExecutor(new CommandSeeInventory());
        getCommand("broadcast").setExecutor(new CommandBroadcast());
        getCommand("setspawn").setExecutor(new CommandSpawn());
        getCommand("spawn").setExecutor(new CommandSpawn());
        getCommand("repair").setExecutor(new CommandRepair());
        getCommand("thor").setExecutor(new CommandThor());
        getCommand("daj").setExecutor(new CommandGive());
        getCommand("chat").setExecutor(new CommandChat());
        getCommand("setwarp").setExecutor(new CommandWarp());
        getCommand("warp").setExecutor(new CommandWarp());
        getCommand("delwarp").setExecutor(new CommandWarp());
        getCommand("killall").setExecutor(new CommandKillAll());
        getCommand("fly").setExecutor(new CommandFly());
        getCommand("skup").setExecutor(new CommandSpawn());
        getCommand("sklep").setExecutor(new CommandSpawn());


		getCommand("bany").setExecutor(new CommandBan());
		getCommand("ban").setExecutor(new CommandBan());
        getCommand("tempban").setExecutor(new CommandBan());
        getCommand("mute").setExecutor(new CommandBan());
        getCommand("unmute").setExecutor(new CommandBan());
		getCommand("unban").setExecutor(new CommandBan());
		getCommand("banlist").setExecutor(new CommandBan());
		getCommand("kick").setExecutor(new CommandKick());
        getCommand("kickall").setExecutor(new CommandKick());
		getCommand("checkban").setExecutor(new CommandBan());

        getCommand("pvp").setExecutor(new CommandPvp());
        getCommand("money").setExecutor(new CommandMoney());
        getCommand("pay").setExecutor(new CommandMoney());
        getCommand("addmoney").setExecutor(new CommandMoney());
        getCommand("wyspa").setExecutor(new CommandIsland());
        getCommand("oijziojsjdfisjdfisdf").setExecutor(new CommandIsland());
        getCommand("dom").setExecutor(new CommandIsland());
        getCommand("odwiedz").setExecutor(new CommandIsland());


        getCommand("doubleexp").setExecutor(new CommandLevel());
        getCommand("doubledrop").setExecutor(new CommandLevel());
        getCommand("ustawpoziom").setExecutor(new CommandLevel());
        getCommand("adminitems").setExecutor(new CommandAdminItems());
        getCommand("spawners").setExecutor(new CommandAdminItems());
        getCommand("moneychest").setExecutor(new CommandAdminItems());
        getCommand("setrank").setExecutor(new CommandRank());
        getCommand("vholo").setExecutor(new CommandHologram());
        getCommand("zdolnosci").setExecutor(new CommandRole());
        getCommand("addquest").setExecutor(new CommandQuests());
        getCommand("zadania").setExecutor(new CommandQuests());
        getCommand("pomoc").setExecutor(new CommandHelp());
        getCommand("setcity").setExecutor(new CommandCity());

        getCommand("name").setExecutor(new CommandName());
        getCommand("prize").setExecutor(new CommandPrize());
        getCommand("forward").setExecutor(new CommandPrize());
        getCommand("dusza").setExecutor(new CommandSouls());
        getCommand("zestaw").setExecutor(new CommandDaily());
        getCommand("vote").setExecutor(new CommandVote());
        getCommand("verifiedmclist").setExecutor(new CommandVote());
        getCommand("swap").setExecutor(new CommandSwap());
        getCommand("whois").setExecutor(new CommandWhoIs());

        getCommand("market").setExecutor(new CommandMarket());
        getCommand("wystaw").setExecutor(new CommandMarket());

        if (Bukkit.getServer().getWorld("void") == null) {
            getServer().createWorld(WorldCreator.name("void").type(WorldType.FLAT).generatorSettings("3;minecraft:air;2").generateStructures(false));
        }


        updateScoreboard();
        updateTimeOnline();
        spawnMonsters();
        updateTabList();

        OnBlockBreak.setStoneDrops();
        OnEntityDeath.setMobDrops();


        Holograms.reloadHolograms();

        Bukkit.getConsoleSender().sendMessage(logo + "§bEnabled successfully.");

    }

    @Override
    public void onDisable() {

        try {

            File f = new File(getDataFolder(), "config.yml");
            if (!f.exists()) saveResource("config.yml", false);
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);

            File f2 = new File(getDataFolder(), "warps.yml");
            if (!f2.exists()) f2.createNewFile();
            YamlConfiguration yaml2 = YamlConfiguration.loadConfiguration(f2);

            File f3 = new File(getDataFolder(), "permissions.yml");
            if (!f3.exists()) saveResource("permissions.yml", false);

            File f4 = new File(getDataFolder(), "holograms.yml");
            if (!f4.exists()) f4.createNewFile();

            File f5 = new File(getDataFolder(), "max_exp.yml");
            if (!f5.exists()) saveResource("max_exp.yml", false);

            File f6 = new File(getDataFolder(), "mob_spawners.yml");
            if (!f6.exists()) f6.createNewFile();


            yaml.set("administrate", administrate);
            yaml.set("doubleExp", doubleExp);
            yaml.set("doubleDrop", doubleDrop);
            yaml.set("czatStatus", czatStatus);
            yaml.set("spawnersWork", spawnersWork);
            if (spawn != null) {
                yaml.set("spawn", LocToString(spawn));
            }



            if (warps != null) {
                for (String name : warps.keySet()) {
                    yaml2.set("warps." + name, LocToString(warps.get(name)));
                }
            }

            yaml.save(f);
            yaml2.save(f2);

        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(logo + "§cThere was a problem during saving data.");
        }

        Bukkit.getConsoleSender().sendMessage(logo + "§bDisabled successfully.");

    }




    public static List<Player> getNearbyPlayers(Location loc, double range) {
        ArrayList<Player> players = new ArrayList<Player>();
        for (Player pl :Bukkit.getOnlinePlayers()) {
            if (loc.distance(pl.getLocation()) <= range) {
                players.add(pl);
            }
        }

        return players;
    }




    public static List<Entity> getNearbyEntities(Location loc, double range) {
        ArrayList<Entity> entities = new ArrayList<Entity>();
        for (Entity entity : loc.getWorld().getEntities()) {
            if (entity.getType().isAlive()) {
                if (entity.getLocation().clone().add(0, 1, 0).distance(loc) <= range) {
                    entities.add(entity);
                }
            }
        }

        return entities;
    }






    public static ItemStack getSkull(String uuid, String data) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta headMeta = head.getItemMeta();

        GameProfile profile = new GameProfile(UUID.fromString(uuid), null);
        profile.getProperties().put("textures", new Property("textures", data));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        profileField.setAccessible(true);
        try {
            profileField.set(headMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }









    private void updateScoreboard() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    Scoreboard.setScoreboard(p);
                }
            }
        }, 0, 15 * 20);
    }



    private void updateTimeOnline() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    User u = User.get(p.getName());

                    u.setTotalTimeOnline(u.getTotalTimeOnline() + 1);

                    /*
                    if (u.getLastPosition2() == null && u.getLastPosition1() != null) u.setLastPosition2(p.getLocation());
                    if (u.getLastPosition1() == null) u.setLastPosition1(p.getLocation());
                    if (u.getLastPosition2() != null) {
                        u.setLastPosition1(u.getLastPosition1());
                        u.setLastPosition2(p.getLocation());
                    }

                    long afk = 60*1000;
                    if (System.currentTimeMillis() >= u.getLastInteract() + afk && System.currentTimeMillis() >= u.getAntiSpam() + afk && u.getLastPosition1().equals(u.getLastPosition2())) {
                        p.kickPlayer("§8§l>> §cWyrzucono z serwera! §7Powód: §cAFK§7.");
                    }

                     */

                }
            }
        }, 0, 60 * 20);
    }


    private void updateTabList() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                int id = tabList;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (id == 0) API.sendHeaderFooter(p, "§7Prestigemc\n\n §aOnline: §7" + Bukkit.getOnlinePlayers().size() + "\n", "\n§b--------------");
                    if (id == 20) API.sendHeaderFooter(p, "§7P§br§7estigemc\n\n §aOnline: §7" + Bukkit.getOnlinePlayers().size() + "\n", "\n§b--------------");
                    if (id == 21) API.sendHeaderFooter(p, "§7Pr§be§7stigemc\n\n §aOnline: §7" + Bukkit.getOnlinePlayers().size() + "\n", "\n§b--------------");
                    if (id == 22) API.sendHeaderFooter(p, "§7Pre§bs§7tigemc\n\n §aOnline: §7" + Bukkit.getOnlinePlayers().size() + "\n", "\n§b--------------");
                    if (id == 23) API.sendHeaderFooter(p, "§7Pres§bt§7igemc\n\n §aOnline: §7" + Bukkit.getOnlinePlayers().size() + "\n", "\n§b--------------");
                    if (id == 24) API.sendHeaderFooter(p, "§7Prest§bi§7gemc\n\n §aOnline: §7" + Bukkit.getOnlinePlayers().size() + "\n", "\n§b--------------");
                    if (id == 25) API.sendHeaderFooter(p, "§7Presti§bg§7emc\n\n §aOnline: §7" + Bukkit.getOnlinePlayers().size() + "\n", "\n§b--------------");
                    if (id == 26) API.sendHeaderFooter(p, "§7Prestig§be§7mc\n\n §aOnline: §7" + Bukkit.getOnlinePlayers().size() + "\n", "\n§b--------------");
                    if (id == 27) API.sendHeaderFooter(p, "§7Prestige§bm§7c\n\n §aOnline: §7" + Bukkit.getOnlinePlayers().size() + "\n", "\n§b--------------");
                    if (id == 28) API.sendHeaderFooter(p, "§7Prestigem§bc\n\n §aOnline: §7" + Bukkit.getOnlinePlayers().size() + "\n", "\n§b--------------");
                }
                tabList++;
                if (tabList > 28) tabList = 0;
            }
        }, 0, 2);
    }



    private void spawnMonsters() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

            public void run() {
                if (!spawnersWork) {
                    return;
                }
                File f = new File(Main.getInst().getDataFolder(), "mob_spawners.yml");
                YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
                int mobAmount = 0;
                World world = spawn.getWorld();
                Random r = new Random();
                List<Integer> spawners = new ArrayList<>();
                List<Integer> usedSpawners = new ArrayList<>();

                if (Main.warps.get("arena") == null) return;
                if (Bukkit.getOnlinePlayers().size() == 0) return;


                if (yaml.get("Spawners") != null) {
                    for (String s : yaml.getConfigurationSection("Spawners").getKeys(false)) {
                        world = Main.StringToLoc(yaml.getString("Spawners." + s)).getWorld();
                        break;
                    }
                }

                if (Bukkit.getWorld(world.getName()) != null) {
                    for (Entity ent : Bukkit.getWorld(world.getName()).getEntities()) {
                        if (ent instanceof Zombie || ent instanceof ZombieVillager || ent instanceof Skeleton || ent instanceof Creeper || ent instanceof Spider) {
                            mobAmount++;
                        }
                    }
                }

                if (yaml.get("Spawners") != null) {
                    for (String s : yaml.getConfigurationSection("Spawners").getKeys(false)) {
                        spawners.add(Integer.parseInt(s));
                    }
                }


                if (yaml.get("Spawners") != null) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getLocation().getWorld().equals(world) && p.getLocation().distance(Main.warps.get("arena")) <= 200) {
                            for (int i = 20 - mobAmount; i > 0; i--) {
                                if (usedSpawners.size() == spawners.size()) {
                                    break;
                                }
                                int rand = 1;
                                aj:for (int is = 0; is < 10000; is++) {
                                    int rand2 = r.nextInt(spawners.size())+1;
                                    if (!usedSpawners.contains(rand2)) {
                                        usedSpawners.add(rand2);
                                        rand = rand2;
                                        break aj;
                                    }
                                }
                                Location loc = Main.StringToLoc(yaml.getString("Spawners." + rand));
                                if (loc.distance(p.getLocation()) <= 60) {
                                    loc = loc.clone().add(0.5, 1, 0.5);

                                    int randomInt = r.nextInt(4) + 1;

                                    if (randomInt == 1) {
                                        world.spawnEntity(loc, EntityType.ZOMBIE);
                                        continue;
                                    }
                                    if (randomInt == 2) {
                                        world.spawnEntity(loc, EntityType.SKELETON);
                                        continue;
                                    }
                                    if (randomInt == 3) {
                                        world.spawnEntity(loc, EntityType.CREEPER);
                                        continue;
                                    }
                                    if (randomInt == 4) {
                                        world.spawnEntity(loc, EntityType.SPIDER);
                                        continue;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, 0, 180 * 20);
    }


    public static String LocToString(Location loc) {
        String s = "Wystapil problem.";
        if (loc == null) return s;

        if (loc != null) {
            s = loc.getWorld().getName() + " " + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " " + loc.getYaw() + " " + loc.getPitch();
        }

        return s;

    }



    public static Location StringToLoc(String s) {
        String[] ss= s.split(" ");

        Location loc = new Location(Bukkit.getServer().getWorld(ss[0]), Double.parseDouble(ss[1]), Double.parseDouble(ss[2]), Double.parseDouble(ss[3]), (float)Double.parseDouble(ss[4]), (float)Double.parseDouble(ss[5]));

        return loc;

    }

}