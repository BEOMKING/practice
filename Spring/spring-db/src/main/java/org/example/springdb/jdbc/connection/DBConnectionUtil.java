package org.example.springdb.jdbc.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class DBConnectionUtil {
    public static Connection getConnection() {
        try {
            final Connection connection = DriverManager.getConnection(ConnectionConst.URL, ConnectionConst.USERNAME, ConnectionConst.PASSWORD);
            log.info("get connection: {}", connection);
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
