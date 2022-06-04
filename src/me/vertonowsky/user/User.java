package me.vertonowsky.user;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import me.vertonowsky.enums.JobType;
import me.vertonowsky.enums.QuestState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.UUID;

public class User {

    private UUID uuid;
    private String name;
    private String rank = "Gracz";
    private String city = "";
    private long rankExpireDate = -1;
    private long lastQuit;
    private long totalTimeOnline;


    private double money = 100;
    private int totalLevel = JobType.values().length;
    private HashMap<JobType, Integer> level = new HashMap<>();
    private HashMap<JobType, Long> experience = new HashMap<>();
    private HashMap<JobType, Long> maxExperience = new HashMap<>();
    private long antiSpam = 0;
    private boolean endTutorial;
    private long nextDailyReward;
    private long islandCreatedDate;


    private String banReason = "Brak";
    private boolean banStatus = false;
    private long banStartDate;
    private long banExpireDate;
    private String banBanner = "Brak";
    private long muteExpireDate;


    private String previousMsg = " ";
    private Player lastMsg;
    private boolean pvp;
    private long kills;
    private long deaths;
    private boolean answeredPlayerSource;

    private long waitALittle;
    private long moneyChests = 0;
    private Hologram moneyChestsHologram;

    private int souls;
    private UUID onlineUUID;
    private long vote1;


    private long lastInteract;
    private Location lastPosition1;
    private Location lastPosition2;




    private HashMap<Integer, QuestState> zadanie = new HashMap<>();


    private long cobblestoneDestroyed;
    private long stoneDestroyed;
    private long coalOreDestroyed;
    private long ironOreDestroyed;
    private long goldOreDestroyed;
    private long diamondOreDestroyed;
    private long emeraldOreDestroyed;
    private long redstoneOreDestroyed;
    private long quartzOreDestroyed;
    private long lapisOreDestroyed;


    private long oakDestroyed;
    private long darkOakDestroyed;
    private long spruceDestroyed;
    private long birchDestroyed;
    private long acaciaDestroyed;
    private long jungleDestroyed;


    private long zombieKilled;
    private long skeletonKilled;
    private long spiderKilled;
    private long creeperKilled;
    private long endermanKilled;
    private long pigmanKilled;
    private long witchKilled;
    private long slimeKilled;
    private long magmaKilled;


    private long rawFishCatched;
    private long salmonCatched;
    private long clownFishCatched;
    private long pufferFishCatched;








    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        UserUtils.addUser(this);
    }

    private User(String name) {
        this.name = name;
        UserUtils.addUser(this);
    }


    public UUID getOnlineUUID() {
        return onlineUUID;
    }

    public void setOnlineUUID(UUID onlineUUID) {
        this.onlineUUID = onlineUUID;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getMuteExpireDate() {
        return muteExpireDate;
    }

    public void setMuteExpireDate(long muteExpireDate) {
        this.muteExpireDate = muteExpireDate;
    }

    public String getBanReason() {
        return banReason;
    }

    public void setBanReason(String banReason) {
        this.banReason = banReason;
    }

    public boolean getBanStatus() {
        return banStatus;
    }

    public void setBanStatus(boolean banStatus) {
        this.banStatus = banStatus;
    }

    public long getBanStartDate() {
        return banStartDate;
    }

    public void setBanStartDate(long banStartDate) {
        this.banStartDate = banStartDate;
    }

    public long getBanExpireDate() {
        return banExpireDate;
    }

    public void setBanExpireDate(long banExpireDate) {
        this.banExpireDate = banExpireDate;
    }

    public String getBanBanner() {
        return banBanner;
    }

    public void setBanBanner(String banBanner) {
        this.banBanner = banBanner;
    }

    public long getRankExpireDate() {
        return rankExpireDate;
    }

    public void setRankExpireDate(long rankExpireDate) {
        this.rankExpireDate = rankExpireDate;
    }

    public int getTotalLevel() {
        return totalLevel;
    }

    public void setTotalLevel(int totalLevel) {
        this.totalLevel = totalLevel;
    }

    public long getMoneyChests() {
        return moneyChests;
    }

    public void setMoneyChests(long moneyChests) {
        this.moneyChests = moneyChests;
    }

    public Hologram getMoneyChestsHologram() {
        return moneyChestsHologram;
    }

    public void setMoneyChestsHologram(Hologram moneyChestsHologram) {
        this.moneyChestsHologram = moneyChestsHologram;
    }

    public int getLevel(JobType jobType) {
        if (this.level.get(jobType) == null) {
            level.put(jobType, 1);
        }
        return level.get(jobType);
    }

    public void setLevel(JobType jobType, int level) {
        this.level.put(jobType, level);
    }

    public long getExperience(JobType jobType) {
        if (this.experience.get(jobType) == null) {
            experience.put(jobType, Long.valueOf(0));
        }
        return experience.get(jobType);
    }

    public void setExperience(JobType jobType, long experience) {
        this.experience.put(jobType, experience);
    }

    public long getMaxExperience(JobType jobType) {
        if (this.maxExperience.get(jobType) == null) {
            maxExperience.put(jobType, Long.valueOf(100));
        }
        return maxExperience.get(jobType);
    }

    public void setMaxExperience(JobType jobType, long maxExperience) {
        this.maxExperience.put(jobType, maxExperience);
    }

    public long getAntiSpam() {
        return antiSpam;
    }

    public void setAntiSpam(long antiSpam) {
        this.antiSpam = antiSpam;
    }

    public String getPreviousMsg() {
        return previousMsg;
    }

    public void setPreviousMsg(String previousMsg) {
        this.previousMsg = previousMsg;
    }

    public long getLastQuit() {
        return lastQuit;
    }

    public void setLastQuit(long lastQuit) {
        this.lastQuit = lastQuit;
    }

    public boolean getEndTutorial() {
        return endTutorial;
    }

    public void setEndTutorial(boolean endTutorial) {
        this.endTutorial = endTutorial;
    }

    public long getNextDailyReward() {
        return nextDailyReward;
    }

    public void setNextDailyReward(long nextDailyReward) {
        this.nextDailyReward = nextDailyReward;
    }

    public long getTotalTimeOnline() {
        return totalTimeOnline;
    }

    public void setTotalTimeOnline(long totalTimeOnline) {
        this.totalTimeOnline = totalTimeOnline;
    }

    public Player getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(Player lastMsg) {
        this.lastMsg = lastMsg;
    }


    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        double priceRounded = new BigDecimal(money).setScale(2, RoundingMode.HALF_UP).doubleValue();
        this.money = priceRounded;
    }


    public boolean isPvp() {
        return pvp;
    }

    public void setPvp(boolean pvp) {
        this.pvp = pvp;
    }

    public long getKills() {
        return kills;
    }

    public void setKills(long kills) {
        this.kills = kills;
    }

    public long getDeaths() {
        return deaths;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public boolean isAnsweredPlayerSource() {
        return answeredPlayerSource;
    }

    public void setAnsweredPlayerSource(boolean answeredPlayerSource) {
        this.answeredPlayerSource = answeredPlayerSource;
    }

    public int getSouls() {
        return souls;
    }

    public void setSouls(int souls) {
        this.souls = souls;
    }

    public long getLastInteract() {
        return lastInteract;
    }

    public void setLastInteract(long lastInteract) {
        this.lastInteract = lastInteract;
    }

    public Location getLastPosition1() {
        return lastPosition1;
    }

    public void setLastPosition1(Location lastPosition1) {
        this.lastPosition1 = lastPosition1;
    }

    public Location getLastPosition2() {
        return lastPosition2;
    }

    public void setLastPosition2(Location lastPosition2) {
        this.lastPosition2 = lastPosition2;
    }

    public long getVote1() {
        return vote1;
    }

    public void setVote1(long vote1) {
        this.vote1 = vote1;
    }

    /*

                    Miner

                 */
    public long getCobblestoneDestroyed() {
        return cobblestoneDestroyed;
    }

    public void setCobblestoneDestroyed(long cobblestoneDestroyed) {
        this.cobblestoneDestroyed = cobblestoneDestroyed;
    }

    public long getStoneDestroyed() {
        return stoneDestroyed;
    }

    public void setStoneDestroyed(long stoneDestroyed) {
        this.stoneDestroyed = stoneDestroyed;
    }

    public long getCoalOreDestroyed() {
        return coalOreDestroyed;
    }

    public void setCoalOreDestroyed(long coalOreDestroyed) {
        this.coalOreDestroyed = coalOreDestroyed;
    }

    public long getIronOreDestroyed() {
        return ironOreDestroyed;
    }

    public void setIronOreDestroyed(long ironOreDestroyed) {
        this.ironOreDestroyed = ironOreDestroyed;
    }

    public long getGoldOreDestroyed() {
        return goldOreDestroyed;
    }

    public void setGoldOreDestroyed(long goldOreDestroyed) {
        this.goldOreDestroyed = goldOreDestroyed;
    }

    public long getDiamondOreDestroyed() {
        return diamondOreDestroyed;
    }

    public void setDiamondOreDestroyed(long diamondOreDestroyed) {
        this.diamondOreDestroyed = diamondOreDestroyed;
    }

    public long getEmeraldOreDestroyed() {
        return emeraldOreDestroyed;
    }

    public void setEmeraldOreDestroyed(long emeraldOreDestroyed) {
        this.emeraldOreDestroyed = emeraldOreDestroyed;
    }

    public long getRedstoneOreDestroyed() {
        return redstoneOreDestroyed;
    }

    public void setRedstoneOreDestroyed(long redstoneOreDestroyed) {
        this.redstoneOreDestroyed = redstoneOreDestroyed;
    }

    public long getQuartzOreDestroyed() {
        return quartzOreDestroyed;
    }

    public void setQuartzOreDestroyed(long quartzOreDestroyed) {
        this.quartzOreDestroyed = quartzOreDestroyed;
    }

    public long getLapisOreDestroyed() {
        return lapisOreDestroyed;
    }

    public void setLapisOreDestroyed(long lapisOreDestroyed) {
        this.lapisOreDestroyed = lapisOreDestroyed;
    }


    /*

        LumberJack

     */

    public long getOakDestroyed() {
        return oakDestroyed;
    }

    public void setOakDestroyed(long oakDestroyed) {
        this.oakDestroyed = oakDestroyed;
    }

    public long getDarkOakDestroyed() {
        return darkOakDestroyed;
    }

    public void setDarkOakDestroyed(long darkOakDestroyed) {
        this.darkOakDestroyed = darkOakDestroyed;
    }

    public long getSpruceDestroyed() {
        return spruceDestroyed;
    }

    public void setSpruceDestroyed(long spruceDestroyed) {
        this.spruceDestroyed = spruceDestroyed;
    }

    public long getBirchDestroyed() {
        return birchDestroyed;
    }

    public void setBirchDestroyed(long birchDestroyed) {
        this.birchDestroyed = birchDestroyed;
    }

    public long getAcaciaDestroyed() {
        return acaciaDestroyed;
    }

    public void setAcaciaDestroyed(long acaciaDestroyed) {
        this.acaciaDestroyed = acaciaDestroyed;
    }

    public long getJungleDestroyed() {
        return jungleDestroyed;
    }

    public void setJungleDestroyed(long jungleDestroyed) {
        this.jungleDestroyed = jungleDestroyed;
    }




    /*

        Warrior

     */


    public long getZombieKilled() {
        return zombieKilled;
    }

    public void setZombieKilled(long zombieKilled) {
        this.zombieKilled = zombieKilled;
    }

    public long getSkeletonKilled() {
        return skeletonKilled;
    }

    public void setSkeletonKilled(long skeletonKilled) {
        this.skeletonKilled = skeletonKilled;
    }

    public long getSpiderKilled() {
        return spiderKilled;
    }

    public void setSpiderKilled(long spiderKilled) {
        this.spiderKilled = spiderKilled;
    }

    public long getCreeperKilled() {
        return creeperKilled;
    }

    public void setCreeperKilled(long creeperKilled) {
        this.creeperKilled = creeperKilled;
    }

    public long getEndermanKilled() {
        return endermanKilled;
    }

    public void setEndermanKilled(long endermanKilled) {
        this.endermanKilled = endermanKilled;
    }

    public long getPigmanKilled() {
        return pigmanKilled;
    }

    public void setPigmanKilled(long pigmanKilled) {
        this.pigmanKilled = pigmanKilled;
    }

    public long getWitchKilled() {
        return witchKilled;
    }

    public void setWitchKilled(long witchKilled) {
        this.witchKilled = witchKilled;
    }

    public long getSlimeKilled() {
        return slimeKilled;
    }

    public void setSlimeKilled(long slimeKilled) {
        this.slimeKilled = slimeKilled;
    }

    public long getMagmaKilled() {
        return magmaKilled;
    }

    public void setMagmaKilled(long magmaKilled) {
        this.magmaKilled = magmaKilled;
    }

    /*

        Angler

     */

    public long getRawFishCatched() {
        return rawFishCatched;
    }

    public void setRawFishCatched(long rawFishCatched) {
        this.rawFishCatched = rawFishCatched;
    }

    public long getSalmonCatched() {
        return salmonCatched;
    }

    public void setSalmonCatched(long salmonCatched) {
        this.salmonCatched = salmonCatched;
    }

    public long getClownFishCatched() {
        return clownFishCatched;
    }

    public void setClownFishCatched(long clownFishCatched) {
        this.clownFishCatched = clownFishCatched;
    }

    public long getPufferFishCatched() {
        return pufferFishCatched;
    }

    public void setPufferFishCatched(long pufferFishCatched) {
        this.pufferFishCatched = pufferFishCatched;
    }


    public long getIslandCreatedDate() {
        return islandCreatedDate;
    }

    public void setIslandCreatedDate(long islandCreatedDate) {
        this.islandCreatedDate = islandCreatedDate;
    }

    public long getWaitALittle() {
        return waitALittle;
    }

    public void setWaitALittle(long waitALittle) {
        this.waitALittle = waitALittle;
    }


    public QuestState getZadanie(Integer i) {
        return zadanie.get(i);
    }

    public void setZadanie(Integer i, QuestState zadanie) {
        this.zadanie.put(i, zadanie);
    }

    public void giveItem(ItemStack itemToAdd, boolean giveIfFullEq) {
        Player p = (Bukkit.getPlayer(this.name));

        boolean czysteQ = false;
        for (int i = 0; i < 36; i++) {
            ItemStack checkItem = p.getInventory().getItem(i);
            if (checkItem == null) {
                czysteQ = true;
                break;
            } else if (checkItem != null) {
                if (checkItem.isSimilar(itemToAdd)) {
                    if (checkItem.getAmount() + itemToAdd.getAmount() <= checkItem.getMaxStackSize()) {
                        czysteQ = true;
                        break;
                    } else {
                        czysteQ = false;
                        continue;
                    }
                } else {
                    czysteQ = false;
                    continue;
                }
            }
        }
        if (czysteQ == true) {
            p.getInventory().addItem(itemToAdd);
            return;
        }
        if (czysteQ == false) {
            if (giveIfFullEq) {
                p.getWorld().dropItem(p.getLocation(), itemToAdd);
                return;
            }
            else if (!giveIfFullEq) {
                p.sendMessage("§cNie masz wolnego miejsca w ekwipunku!");
                return;
            }
        }
    }



    public void sendMessage(String message) {
        Player p = (Bukkit.getPlayer(this.name));
        p.sendMessage(message);
    }

    public boolean isOnline() {
        return (Bukkit.getPlayer(this.name) != null);
    }

    public static User get(String name) {
        for (User u : UserUtils.getUsers()) {
            if (u.getName().equalsIgnoreCase(name))
                return u;
        }
        return null;
    }

    public static User get(UUID uuid) {
        for (User u : UserUtils.getUsers()) {
            if (u.getUuid().equals(uuid)) {
                return u;
            }
        }

        return null;
    }

    public static User get(String name, boolean ignoreCase) {
        for (User u : UserUtils.getUsers()) {
            if (ignoreCase ? u.getName().equalsIgnoreCase(name) : u.getName().equals(name)) {
                return u;
            }
        }

        return null;
    }
}
