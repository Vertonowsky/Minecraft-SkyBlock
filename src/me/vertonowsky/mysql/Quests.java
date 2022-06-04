package me.vertonowsky.mysql;

import me.vertonowsky.enums.QuestState;
import me.vertonowsky.inventory.Zadania;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Quests {

    public static void checkTableQuestsAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("create table if not exists quests_all(");
        sb.append("id int(255) not null AUTO_INCREMENT,");
        sb.append("title varchar(100) not null,");
        sb.append("description varchar(5000) not null,");
        sb.append("level_min int(10) not null,");
        sb.append("stage_max int(5) not null,");
        sb.append("primary key(id)) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;");
        try {
            MySQL.conn.createStatement().executeUpdate(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    public static void addToQuests() {
        String title = "Rzemieślnik";
        String description = "Stwórz drewniany kilof.";
        int levelMin = 1;
        int stageMax = 2;
        try {
            Statement stmt = MySQL.conn.createStatement();
            String SQL = "SELECT * FROM `quests_all` WHERE title='" + title + "'";
            ResultSet rs = stmt.executeQuery(SQL);

            if (!rs.next()) {
                PreparedStatement insert = MySQL.conn.prepareStatement("INSERT INTO `quests_all` (id, title, description, level_min, stage_max) VALUES (null,?,?,?,?)");

                insert.setString(1, title);
                insert.setString(2, description);
                insert.setInt(3, levelMin);
                insert.setLong(4, stageMax);

                insert.executeUpdate();
            } else {
                PreparedStatement insert = MySQL.conn.prepareStatement("UPDATE `quests_all` set title=?, description=?, level_min=?, stage_max=? WHERE title='" + title + "'");

                insert.setString(1, title);
                insert.setString(2, description);
                insert.setInt(3, levelMin);
                insert.setLong(4, stageMax);


                insert.executeUpdate();
            }
            Bukkit.getConsoleSender().sendMessage("§a§lSukces: §7Dodano zadanie.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void reloadQuestAmount() {
        try {
            Statement stmt = MySQL.conn.createStatement();
            String SQL = "SELECT COUNT(*) FROM `quests_all`";
            ResultSet rs = stmt.executeQuery(SQL);
            rs.first();
            Zadania.allQuests = rs.getInt("COUNT(*)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




/*





Per player quests over here
          ||
          ||
          \/






 */



    public static void checkTableQuestsPlayer(){
        StringBuilder sb = new StringBuilder();
        sb.append("create table if not exists quests_player(");
        sb.append("id int(255) not null AUTO_INCREMENT,");
        sb.append("uuid varchar(100) not null,");
        sb.append("quest_1 varchar(30) null,");
        sb.append("quest_2 varchar(30) null,");
        sb.append("quest_3 varchar(30) null,");
        sb.append("quest_4 varchar(30) null,");
        sb.append("quest_5 varchar(30) null,");
        sb.append("quest_6 varchar(30) null,");
        sb.append("quest_7 varchar(30) null,");
        sb.append("quest_8 varchar(30) null,");
        sb.append("quest_9 varchar(30) null,");
        sb.append("quest_10 varchar(30) null,");
        sb.append("quest_11 varchar(30) null,");
        sb.append("quest_12 varchar(30) null,");
        sb.append("quest_13 varchar(30) null,");
        sb.append("quest_14 varchar(30) null,");
        sb.append("quest_15 varchar(30) null,");
        sb.append("quest_16 varchar(30) null,");
        sb.append("primary key(id)) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;");
        try {
            MySQL.conn.createStatement().executeUpdate(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    public static void saveQuestsPlayer(Player p, boolean update) {
        User u = User.get(p.getName());
        try {
            Statement stmt = MySQL.conn.createStatement();
            String SQL = "SELECT * FROM quests_player WHERE uuid='" + u.getUuid().toString() + "'";
            ResultSet rs = stmt.executeQuery(SQL);

            if (!rs.next()) {
                PreparedStatement insert = MySQL.conn.prepareStatement("INSERT INTO `quests_player` (id, uuid, quest_1, quest_2, quest_3, quest_4, quest_5, quest_6, quest_7, quest_8, quest_9, quest_10," +
                        " quest_11, quest_12, quest_13, quest_14, quest_15, quest_16) VALUES (null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                insert.setString(1, u.getUuid().toString());
                for (int i = 2; i <= Zadania.allQuests +1; i++) insert.setString(i, QuestState.ZABLOKOWANE.toString());

                insert.executeUpdate();

            } else {
                if (update) {
                    PreparedStatement insert = MySQL.conn.prepareStatement("UPDATE `quests_player` set quest_1=?, quest_2=?, quest_3=?, quest_4=?, quest_5=?, quest_6=?, quest_7=?, quest_8=?, quest_9=?, quest_10=?," +
                            " quest_11=?, quest_12=?, quest_13=?, quest_14=?, quest_15=?, quest_16=? WHERE uuid='" + u.getUuid().toString() + "'");

                    for (int i = 1; i <= Zadania.allQuests; i++) insert.setString(i, u.getZadanie(i).toString());

                    insert.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void loadQuestsPlayer(Player p) {
        User u = User.get(p.getName());
        try {
            ResultSet rs = MySQL.conn.createStatement().executeQuery("SELECT * FROM `quests_player` WHERE uuid='" + u.getUuid().toString() + "'");
            if (rs.next()) {
                for (int i = 1; i <= Zadania.allQuests; i++) u.setZadanie(i, QuestState.valueOf(rs.getString("quest_" + i)));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void removeQuestsPlayer(Player p) {
        User u = User.get(p.getName());
        try {
            PreparedStatement insert = MySQL.conn.prepareStatement("DELETE FROM quests_player WHERE uuid='" + u.getUuid().toString() + "'");

            insert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            p.sendMessage("§c§lBłąd: §7Wystąpił problem, spróbuj jeszcze raz lub zgłoś się do administracji.");
        }
    }




    public static void repairPlayersQuests(Player p){
        try {
            User u = User.get(p.getName());
            ResultSet rs = MySQL.conn.createStatement().executeQuery("SELECT * FROM `quests_all`");
            while (rs.next()) {
                Integer id = rs.getInt("id");
                if (u.getTotalLevel() >= rs.getInt("level_min")) {
                    if (u.getZadanie(id).equals(QuestState.ZABLOKOWANE)) u.setZadanie(id, QuestState.W_TRAKCIE);
                }
                if (u.getTotalLevel() < rs.getInt("level_min")) {
                    if (!(u.getZadanie(id).equals(QuestState.ZABLOKOWANE))) u.setZadanie(id, QuestState.ZABLOKOWANE);
                }
            }

            saveQuestsPlayer(p, true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public static void swapQuestsPlayer(User u1, User u2) {
        try {


            PreparedStatement insert = MySQL.conn.prepareStatement("UPDATE `quests_player` set uuid='1' WHERE uuid='" + u1.getUuid().toString() + "'");
            insert.executeUpdate();

            PreparedStatement insert2 = MySQL.conn.prepareStatement("UPDATE `quests_player` set uuid='2' WHERE uuid='" + u2.getUuid().toString() + "'");
            insert2.executeUpdate();

            PreparedStatement insert3 = MySQL.conn.prepareStatement("UPDATE `quests_player` set uuid='" + u2.getUuid() +"' WHERE uuid='1'");
            insert3.executeUpdate();

            PreparedStatement insert4 = MySQL.conn.prepareStatement("UPDATE `quests_player` set uuid='" + u1.getUuid() +"' WHERE uuid='2'");
            insert4.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





}
