package org.example.springdb.jdbc.connection;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.example.springdb.jdbc.connection.ConnectionConst.*;

class ConnectionTest {
    @Test
    void getDriverManager() throws SQLException {
        final Connection connection1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        final Connection connection2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        System.out.println("connection1 = " + connection1);
        System.out.println("connection1 = " + connection1.getClass());
        System.out.println("connection2 = " + connection2);
        System.out.println("connection2 = " + connection2.getClass());
    }

    @Test
    void getDataSourceDriverManagerMax() throws SQLException {
        final DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        for (int i = 0; i < 100; i++) {
            final Connection connection = dataSource.getConnection();
            System.out.println("connection = " + connection);
            System.out.println("connection = " + connection.getClass());
        }
    }
}
