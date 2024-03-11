package org.example.springdb.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.example.springdb.jdbc.connection.ConnectionConst.*;

public class ConnectionPoolTest {
    @Test
    void getDataSourceFromPool() throws SQLException, InterruptedException {
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("springHikariCP");

        useDataSource(dataSource, 10);

        Thread.sleep(5000);
    }

    private void useDataSource(final HikariDataSource dataSource, final int count) throws SQLException {
        for (int i = 0; i < count; i++) {
            final Connection connection = dataSource.getConnection();
            System.out.println("connection = " + connection);
        }
    }
}
