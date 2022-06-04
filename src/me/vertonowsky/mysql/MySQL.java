package me.vertonowsky.mysql;

import me.vertonowsky.main.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MySQL {

    public static Connection conn;


    public static synchronized void openConnection(){
        if(!isConnected()){

            try {
                File file = new File(Main.getInst().getDataFolder(), "config.yml");
                YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
                String user = yaml.getString("database-user");
                String password = yaml.getString("database-password");
                String database = yaml.getString("database-name");

                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database + "?useUnicode=yes&characterEncoding=UTF-8", user, password);

            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static synchronized void closeConnection(){
        if(isConnected()){
            try{
                conn.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static boolean isConnected() {
        try{
            if(conn == null) return false;
            if(conn.isClosed()) return false;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return true;
    }



    public static String listLocationToString(List<Location> list) {
        StringBuilder stoneGenerators = new StringBuilder();
        int in = 0;
        for (Location loc : list) {
            stoneGenerators.append(Main.LocToString(loc));
            if (in < list.size() -1) stoneGenerators.append("~");
            in++;
        }
        return stoneGenerators.toString();
    }


}
