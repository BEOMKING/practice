package org.example.springdb.jdbc.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.springdb.jdbc.connection.ConnectionConst.*;

class SpringExceptionTranslatorTest {
    DataSource dataSource;

    @BeforeEach
    void init() {
        dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
    }

    @Test
    void relationDoesNotExist() {
        String sql = "SELECT * FROM non_existent_table";

        try {
            dataSource.getConnection().createStatement().execute(sql);
        } catch (SQLException e) {
            System.out.print("error " + e.getErrorCode());
            e.printStackTrace();
        }
    }

    @Test
    void badGrammar() {
        String sql = "SELECT bad grammar";

        try {
            dataSource.getConnection().createStatement().execute(sql);
        } catch (SQLException e) {
            SQLErrorCodeSQLExceptionTranslator translator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
            final DataAccessException result = translator.translate("select", sql, e);
            System.out.println(result);

            assertThat(result.getClass()).isEqualTo(BadSqlGrammarException.class);
        }
    }
}
