package me.vertonowsky.mysql;

import java.sql.SQLException;

public class Help {

    public static void checkTableQuestsAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("create table if not exists help(");
        sb.append("id int(255) not null AUTO_INCREMENT,");
        sb.append("category varchar(30) not null,");
        sb.append("title varchar(100) not null,");
        sb.append("description varchar(5000) not null,");
        sb.append("primary key(id)) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;");
        try {
            MySQL.conn.createStatement().executeUpdate(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
