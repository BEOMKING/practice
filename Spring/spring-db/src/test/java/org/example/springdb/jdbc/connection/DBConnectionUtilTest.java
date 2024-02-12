package org.example.springdb.jdbc.connection;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DBConnectionUtilTest {
    @Test
    void getConnection() {
        assertThat(DBConnectionUtil.getConnection()).isNotNull();
    }
}