package me.vertonowsky.mysql;

import me.vertonowsky.enums.JobType;
import me.vertonowsky.main.Main;
import me.vertonowsky.user.User;
import me.vertonowsky.user.UserUtils;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class Users {




    public static void checkTableUsersSkyBlock() {
        StringBuilder sb = new StringBuilder();
        sb.append("create table if not exists users_skyblock(");
        sb.append("id int(255) not null AUTO_INCREMENT,");
        sb.append("uuid varchar(100) not null,");
        sb.append("name varchar(50) not null,");
        sb.append("last_online long not null,");
        sb.append("total_time_online varchar(30) not null,");
        sb.append("tutorial_ended BOOLEAN not null default 0,");
        sb.append("daily_reward long not null,");
        sb.append("answered_source boolean not null,");
        sb.append("money double not null,");
        sb.append("level_total int not null,");
        sb.append("level_jobs varchar(200) not null,");
        sb.append("experience_jobs varchar(200) not null,");
        sb.append("max_experience_jobs varchar(200) not null,");
        sb.append("kills long not null,");
        sb.append("deaths long not null,");
        sb.append("island_created_date long not null,");
        sb.append("money_chests long not null,");
        sb.append("souls int not null,");
        sb.append("vote_1 long not null,");
        sb.append("primary key(id, uuid));");
        try {
            MySQL.conn.createStatement().executeUpdate(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void loadDataUsersSkyBlock() {
        int i = 0;
        try {
            ResultSet rs;
            rs = MySQL.conn.createStatement().executeQuery("SELECT * FROM `users_skyblock`");
            while (rs.next()) {
                if (rs.getString("uuid") != null || !rs.getString("uuid").isEmpty()) {
                    User u = new User(UUID.fromString(rs.getString("uuid")), rs.getString("name"));
                    u.setLastQuit(rs.getLong("last_online"));
                    u.setTotalTimeOnline(rs.getLong("total_time_online"));
                    u.setEndTutorial(rs.getBoolean("tutorial_ended"));


                    u.setNextDailyReward(rs.getLong("daily_reward"));
                    if (u.getNextDailyReward() == 0) {
                        u.setNextDailyReward(System.currentTimeMillis());
                    }

                    u.setAnsweredPlayerSource(rs.getBoolean("answered_source"));
                    u.setMoney(rs.getDouble("money"));
                    u.setTotalLevel(rs.getInt("level_total"));
                    stringToJobs(u, rs.getString("level_jobs"), rs.getString("experience_jobs"), rs.getString("max_experience_jobs"));

                    u.setKills(rs.getLong("kills"));
                    u.setDeaths(rs.getLong("deaths"));
                    u.setAnsweredPlayerSource(rs.getBoolean("answered_source"));
                    u.setIslandCreatedDate(rs.getLong("island_created_date"));
                    u.setMoneyChests(rs.getLong("money_chests"));
                    u.setSouls(rs.getInt("souls"));
                    u.setVote1(rs.getLong("vote_1"));

                    i++;
                }

            }
            Bukkit.getConsoleSender().sendMessage(Main.logo + "§bLoaded §7" + i + " §busers.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void saveDataUsersSkyBlock() {
        int i = 0;
        try {
            for (User u : UserUtils.getUsers()) {
                Statement stmt = MySQL.conn.createStatement();
                String SQL = "SELECT * FROM users_skyblock WHERE uuid='" + u.getUuid().toString() + "'";
                ResultSet rs = stmt.executeQuery(SQL);

                PreparedStatement insert;
                if (!rs.next()) {
                    insert = MySQL.conn.prepareStatement("INSERT INTO users_skyblock (id, uuid, name, last_online, total_time_online, tutorial_ended, daily_reward, answered_source, money, level_total, level_jobs, experience_jobs, max_experience_jobs, kills, deaths, island_created_date, money_chests, souls, vote_1) VALUES (null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                    insert.setString(1, u.getUuid().toString());
                    insert.setString(2, u.getName());
                    insert.setLong(3, u.getLastQuit());
                    insert.setLong(4, u.getTotalTimeOnline());
                    insert.setBoolean(5, u.getEndTutorial());
                    insert.setLong(6, u.getNextDailyReward());
                    insert.setBoolean(7, u.isAnsweredPlayerSource());
                    insert.setDouble(8, u.getMoney());

                    insert.setInt(9, u.getTotalLevel());
                    String[] returnedData = jobsToString(u);
                    insert.setString(10, returnedData[0]);
                    insert.setString(11, returnedData[1]);
                    insert.setString(12, returnedData[2]);

                    insert.setLong(13, u.getKills());
                    insert.setLong(14, u.getDeaths());
                    insert.setLong(15, u.getIslandCreatedDate());
                    insert.setLong(16, u.getMoneyChests());
                    insert.setInt(17, u.getSouls());
                    insert.setLong(18, u.getVote1());


                } else {
                    insert = MySQL.conn.prepareStatement("UPDATE users_skyblock set last_online=?, total_time_online=?, tutorial_ended=?, daily_reward=?, answered_source=?, money=?, level_total=?, level_jobs=?, experience_jobs=?, max_experience_jobs=?, kills=?, deaths=?, island_created_date=?, money_chests=?, souls=?, vote_1=? WHERE uuid='" + u.getUuid().toString() + "'");

                    insert.setLong(1, u.getLastQuit());
                    insert.setLong(2, u.getTotalTimeOnline());
                    insert.setBoolean(3, u.getEndTutorial());
                    insert.setLong(4, u.getNextDailyReward());
                    insert.setBoolean(5, u.isAnsweredPlayerSource());
                    insert.setDouble(6, u.getMoney());

                    insert.setInt(7, u.getTotalLevel());
                    String[] returnedData = jobsToString(u);
                    insert.setString(8, returnedData[0]);
                    insert.setString(9, returnedData[1]);
                    insert.setString(10, returnedData[2]);

                    insert.setLong(11, u.getKills());
                    insert.setLong(12, u.getDeaths());
                    insert.setLong(13, u.getIslandCreatedDate());
                    insert.setLong(14, u.getMoneyChests());
                    insert.setInt(15, u.getSouls());
                    insert.setLong(16, u.getVote1());

                }
                insert.executeUpdate();
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Bukkit.getConsoleSender().sendMessage(Main.logo + "§bSaved §7" + i + " §busers.");
    }


    public static void saveDataUserSkyBlock(User u) {
        try {
            Statement stmt = MySQL.conn.createStatement();
            String SQL = "SELECT * FROM users_skyblock WHERE uuid='" + u.getUuid().toString() + "'";
            ResultSet rs = stmt.executeQuery(SQL);

            PreparedStatement insert;
            if (!rs.next()) {
                insert = MySQL.conn.prepareStatement("INSERT INTO users_skyblock (id, uuid, name, last_online, total_time_online, tutorial_ended, daily_reward, answered_source, money, level_total, level_jobs, experience_jobs, max_experience_jobs, kills, deaths, island_created_date, money_chests, souls, vote_1) VALUES (null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                insert.setString(1, u.getUuid().toString());
                insert.setString(2, u.getName());
                insert.setLong(3, u.getLastQuit());
                insert.setLong(4, u.getTotalTimeOnline());
                insert.setBoolean(5, u.getEndTutorial());
                insert.setLong(6, u.getNextDailyReward());
                insert.setBoolean(7, u.isAnsweredPlayerSource());
                insert.setDouble(8, u.getMoney());

                insert.setInt(9, u.getTotalLevel());
                String[] returnedData = jobsToString(u);
                insert.setString(10, returnedData[0]);
                insert.setString(11, returnedData[1]);
                insert.setString(12, returnedData[2]);

                insert.setLong(13, u.getKills());
                insert.setLong(14, u.getDeaths());
                insert.setLong(15, u.getIslandCreatedDate());
                insert.setLong(16, u.getMoneyChests());
                insert.setInt(17, u.getSouls());
                insert.setLong(18, u.getVote1());


            } else {
                insert = MySQL.conn.prepareStatement("UPDATE users_skyblock set last_online=?, total_time_online=?, tutorial_ended=?, daily_reward=?, answered_source=?, money=?, level_total=?, level_jobs=?, experience_jobs=?, max_experience_jobs=?, kills=?, deaths=?, island_created_date=?, money_chests=?, souls=?, vote_1=? WHERE uuid='" + u.getUuid().toString() + "'");

                insert.setLong(1, u.getLastQuit());
                insert.setLong(2, u.getTotalTimeOnline());
                insert.setBoolean(3, u.getEndTutorial());
                insert.setLong(4, u.getNextDailyReward());
                insert.setBoolean(5, u.isAnsweredPlayerSource());
                insert.setDouble(6, u.getMoney());

                insert.setInt(7, u.getTotalLevel());
                String[] returnedData = jobsToString(u);
                insert.setString(8, returnedData[0]);
                insert.setString(9, returnedData[1]);
                insert.setString(10, returnedData[2]);

                insert.setLong(11, u.getKills());
                insert.setLong(12, u.getDeaths());
                insert.setLong(13, u.getIslandCreatedDate());
                insert.setLong(14, u.getMoneyChests());
                insert.setInt(15, u.getSouls());
                insert.setLong(16, u.getVote1());

            }
            insert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






    /*



        users generally




     */








    public static void loadDataUsersGeneral() {
        int i = 0;
        try {
            ResultSet rs;
            rs = MySQL.conn.createStatement().executeQuery("SELECT * FROM `users_general`");
            while (rs.next()) {
                if (rs.getString("uuid") != null || !rs.getString("uuid").isEmpty()) {
                    if (User.get(UUID.fromString(rs.getString("uuid"))) != null) {
                        User u = User.get(UUID.fromString(rs.getString("uuid")));
                        u.setRank(rs.getString("rank"));
                        u.setRankExpireDate(rs.getLong("rank_expire_date"));

                        u.setBanStatus(rs.getBoolean("banned"));
                        u.setBanStartDate(rs.getLong("ban_start_date"));
                        u.setBanExpireDate(rs.getLong("ban_expire_date"));
                        u.setBanReason(rs.getString("ban_reason"));
                        u.setBanBanner(rs.getString("ban_banner"));
                        u.setCity(rs.getString("city"));
                        i++;
                    }
                }
            }
            Bukkit.getConsoleSender().sendMessage(Main.logo + "§bLoaded §7" + i + " §busers - general.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void saveDataUserGeneralOne(User u) {
        MySQL.openConnection();
        try {
            Statement stmt = MySQL.conn.createStatement();
            String SQL = "SELECT * FROM users_general WHERE uuid='" + u.getUuid().toString() + "'";
            ResultSet rs = stmt.executeQuery(SQL);

            PreparedStatement insert;
            if (!rs.next()) {
                insert = MySQL.conn.prepareStatement("INSERT INTO users_general (id, uuid, name, rank, rank_expire_date, banned, ban_start_date, ban_expire_date, ban_reason, ban_banner, city) VALUES (null,?,?,?,?,?,?,?,?,?,?)");

                insert.setString(1, u.getUuid().toString());
                insert.setString(2, u.getName());
                insert.setString(3, u.getRank());
                insert.setLong(4, u.getRankExpireDate());

                insert.setBoolean(5, u.getBanStatus());
                insert.setLong(6, u.getBanStartDate());
                insert.setLong(7, u.getBanExpireDate());
                insert.setString(8, u.getBanReason());
                insert.setString(9, u.getBanBanner());
                insert.setString(10, u.getCity());

            } else {
                insert = MySQL.conn.prepareStatement("UPDATE users_general set rank=?, rank_expire_date=?, banned=?, ban_start_date=?, ban_expire_date=?, ban_reason=?, ban_banner=?, city=? WHERE uuid='" + u.getUuid().toString() + "'");

                insert.setString(1, u.getRank());
                insert.setLong(2, u.getRankExpireDate());

                insert.setBoolean(3, u.getBanStatus());
                insert.setLong(4, u.getBanStartDate());
                insert.setLong(5, u.getBanExpireDate());
                insert.setString(6, u.getBanReason());
                insert.setString(7, u.getBanBanner());
                insert.setString(8, u.getCity());

            }
            insert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            MySQL.closeConnection();
        }
        MySQL.closeConnection();
    }



    public static void loadDataUserGeneralOne(User u) {
        MySQL.openConnection();
        try {
            ResultSet rs;
            rs = MySQL.conn.createStatement().executeQuery("SELECT * FROM `users_general` WHERE uuid='" + u.getUuid().toString() + "'");
            while (rs.next()) {
                u.setRank(rs.getString("rank"));
                u.setRankExpireDate(rs.getLong("rank_expire_date"));

                u.setBanStatus(rs.getBoolean("banned"));
                u.setBanStartDate(rs.getLong("ban_start_date"));
                u.setBanExpireDate(rs.getLong("ban_expire_date"));
                u.setBanReason(rs.getString("ban_reason"));
                u.setBanBanner(rs.getString("ban_banner"));
                u.setCity(rs.getString("city"));
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            MySQL.closeConnection();
        }
        MySQL.closeConnection();
    }





    public static boolean isNameRegistered(String name) {
        MySQL.openConnection();
        try {
            ResultSet rs;
            rs = MySQL.conn.createStatement().executeQuery("SELECT name FROM `registered_names`");
            while(rs.next()) {
                if (rs.getString("name").equals(name)) {
                    MySQL.closeConnection();
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MySQL.closeConnection();
        return false;
    }




    private static String[] jobsToString(User u) {
        /*
            Level - Experience - MaxExperience
         */
        String[] toReturn = new String[3];


        StringBuilder builder = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();
        StringBuilder builder3 = new StringBuilder();
        for (JobType jobType : JobType.values()) {
            builder.append(jobType + "=" + u.getLevel(jobType) + "~");
            builder2.append(jobType + "=" + u.getExperience(jobType) + "~");
            builder3.append(jobType + "=" + u.getMaxExperience(jobType) + "~");
        }
        builder.deleteCharAt(builder.length() -1);
        builder2.deleteCharAt(builder2.length() -1);
        builder3.deleteCharAt(builder3.length() -1);
        toReturn[0] = builder.toString();
        toReturn[1] = builder2.toString();
        toReturn[2] = builder3.toString();


        return toReturn;
    }



    private static void stringToJobs(User u, String level, String experience, String maxExperience) {
        /*
            Level - Experience - MaxExperience
         */
        String[] toReturn = new String[3];


        for (String s : level.split("~")) {
            String[] line = s.split("=");
            u.setLevel(JobType.valueOf(line[0]), Integer.parseInt(line[1]));
        }

        for (String s : experience.split("~")) {
            String[] line = s.split("=");
            u.setExperience(JobType.valueOf(line[0]), Integer.parseInt(line[1]));
        }

        for (String s : maxExperience.split("~")) {
            String[] line = s.split("=");
            u.setMaxExperience(JobType.valueOf(line[0]), Integer.parseInt(line[1]));
        }
    }

}
