package me.vertonowsky.mysql;

import me.vertonowsky.api.Holograms;
import me.vertonowsky.user.User;
import me.vertonowsky.user.UserUtils;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Shop {



    public static boolean checkShopReceivedItems(String name) {
        MySQL.openConnection();

        boolean received = checkShopReceivedItemsPrzelewy(name);
        if (!received) received = checkShopReceivedItemsSms(name);

        MySQL.closeConnection();
        return received;
    }


    public static boolean checkShopReceivedUnBan(String name) {
        MySQL.openConnection();

        boolean received = checkShopReceivedUnBanPrzelewy(name);
        if (!received) received = checkShopReceivedUnBanSms(name);

        MySQL.closeConnection();
        return received;
    }



    private static boolean checkShopReceivedUnBanPrzelewy(String name) {
        MySQL.openConnection();
        try {
            ResultSet rs;
            rs = MySQL.conn.createStatement().executeQuery("SELECT title, amount, currency FROM `p24_success` WHERE title='UnBan " + name + "' AND received='0'");
            while (rs.next()) {
                if (UserUtils.getUsers().contains(User.get(name))) {
                    ResultSet rs2 = MySQL.conn.createStatement().executeQuery("SELECT * FROM `users_general` WHERE name='" + name + "'");
                    if (rs2.next()) {
                        if (rs2.getBoolean("banned") == true) {

                            if (rs.getInt("amount") >= 1700 && rs.getString("currency").equals("PLN")) {
                                MySQL.openConnection();
                                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "unban " + name);
                                PreparedStatement insert = MySQL.conn.prepareStatement("UPDATE `p24_success` set received=? WHERE title='UnBan " + name + "' AND received='0'");

                                insert.setBoolean(1, true);
                                insert.executeUpdate();
                                MySQL.closeConnection();
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MySQL.closeConnection();
        return false;
    }



    private static boolean checkShopReceivedUnBanSms(String name) {
        MySQL.openConnection();
        try {
            ResultSet rs;
            rs = MySQL.conn.createStatement().executeQuery("SELECT title, value_brutto FROM `simpay_transactions` WHERE title='UnBan " + name + "' AND received='0' AND status='ORDER_PAYED'");
            while (rs.next()) {
                ResultSet rs2 = MySQL.conn.createStatement().executeQuery("SELECT * FROM `users_general` WHERE name='" + name + "'");
                if (rs2.next()) {
                    if (rs2.getBoolean("banned") == true) {

                        if (rs.getDouble("value_brutto") >= 24.85) {
                            MySQL.openConnection();
                            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "unban " + name);
                            PreparedStatement insert = MySQL.conn.prepareStatement("UPDATE `simpay_transactions` set received=? WHERE title='UnBan " + name + "' AND received='0' AND status='ORDER_PAYED'");

                            insert.setBoolean(1, true);
                            insert.executeUpdate();
                            MySQL.closeConnection();
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MySQL.closeConnection();
        return false;
    }








    public static boolean checkShopReceivedItemsPrzelewy(String name) {
        try {
            ResultSet rs;
            rs = MySQL.conn.createStatement().executeQuery("SELECT title, amount, currency FROM `p24_success` WHERE name='" + name + "' AND received='0'");
            while(rs.next()) {
                String titleFull = rs.getString("title");
                String title = titleFull.split(" ")[0];
                boolean correctValue = false;
                if (title.equals("VIP30")) {
                    if (rs.getInt("amount") >= 900 && rs.getString("currency").equals("PLN")) {
                        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "setrank " + name + " VIP 30");
                        correctValue = true;
                    }
                }

                if (title.equals("SuperVIP60")) {
                    if (rs.getInt("amount") >= 1600 && rs.getString("currency").equals("PLN")) {
                        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "setrank " + name + " SuperVIP 60");
                        correctValue = true;
                    }
                }
                if (title.equals("Klucz")) {
                    if (rs.getInt("amount") >= 400 && rs.getString("currency").equals("PLN")) {
                        User pUUID = User.get(name);
                        pUUID.setMoneyChests(pUUID.getMoneyChests() + 1);
                        Bukkit.broadcastMessage("§8§m--------------------------------------------------");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("§8§l➢ §bGracz §f" + name + " §bzakupił §e§lZłoty Klucz");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("§8§l➢   §3Serdecznie dziękujemy za wsparcie");
                        Bukkit.broadcastMessage("§8§l➢       §3oraz życzymy miłej gry!");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("§8§m--------------------------------------------------");

                        if (Bukkit.getPlayer(name) != null) {
                            Holograms.reloadMoneyChests(Bukkit.getPlayer(name));
                        }
                        correctValue = true;
                    }
                }

                if (correctValue) {
                    MySQL.openConnection();
                    PreparedStatement insert = MySQL.conn.prepareStatement("UPDATE `p24_success` set received=? WHERE title='" + titleFull + "' AND received='0' LIMIT 1");
                    insert.setBoolean(1, true);
                    insert.executeUpdate();
                    MySQL.closeConnection();
                    return true;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    public static boolean checkShopReceivedItemsSms(String name) {
        try {
            ResultSet rs;
            rs = MySQL.conn.createStatement().executeQuery("SELECT title, value_brutto FROM `simpay_transactions` WHERE name='" + name + "' AND received='0' AND status='ORDER_PAYED'");
            while(rs.next()) {
                String titleFull = rs.getString("title");
                String title = titleFull.split(" ")[0];
                boolean correctValue = false;
                if (title.equals("VIP30")) {
                    if (rs.getDouble("value_brutto") >= 13.2) {
                        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "setrank " + name + " VIP 30");
                        correctValue = true;
                    }
                }

                if (title.equals("SuperVIP60")) {
                    if (rs.getDouble("value_brutto") >= 21.89) {
                        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "setrank " + name + " SuperVIP 60");
                        correctValue = true;
                    }
                }

                if (title.equals("Klucz")) {
                    if (rs.getDouble("value_brutto") >= 5.9) {
                        User pUUID = User.get(name);
                        pUUID.setMoneyChests(pUUID.getMoneyChests() + 1);
                        Bukkit.broadcastMessage("§8§m--------------------------------------------------");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("§8§l➢ §bGracz §f" + name + " §bzakupił §e§lZłoty Klucz");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("§8§l➢   §3Serdecznie dziękujemy za wsparcie");
                        Bukkit.broadcastMessage("§8§l➢       §3oraz życzymy miłej gry!");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("§8§m--------------------------------------------------");

                        if (Bukkit.getPlayer(name) != null) {
                            Holograms.reloadMoneyChests(Bukkit.getPlayer(name));
                        }
                        correctValue = true;
                    }
                }

                if (correctValue) {
                    MySQL.openConnection();
                    PreparedStatement insert = MySQL.conn.prepareStatement("UPDATE `simpay_transactions` set received=? WHERE title='" + titleFull + "' AND received='0' AND status='ORDER_PAYED' LIMIT 1");
                    insert.setBoolean(1, true);
                    insert.executeUpdate();
                    MySQL.closeConnection();
                    return true;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
