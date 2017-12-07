package org.ramer.demo.jdbc.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Properties;

@Slf4j
public class JdbcUtil{
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/dev";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, new Properties() {
                {
                    this.put("user", "root");
                    this.put("password", "ramer");
                }
            });
        } catch (SQLException e) {
            log.error("getConnection: {}", e);
        }
        return connection;
    }

    public static Statement getStatement() {
        try {
            return getConnection().createStatement();
        } catch (SQLException e) {
            log.error(" getStatement : [{}]", e);
        }
        return null;
    }
}
