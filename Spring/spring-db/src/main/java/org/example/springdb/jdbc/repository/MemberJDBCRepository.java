package org.example.springdb.jdbc.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.springdb.jdbc.connection.DBConnectionUtil;
import org.example.springdb.jdbc.domain.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class MemberJDBCRepository {
    public Member save(final Member member) throws SQLException {
        final String sql = "INSERT INTO member (member_id, money) VALUES (?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, member.getMemberId());
            preparedStatement.setInt(2, member.getMoney());
            preparedStatement.executeUpdate();
            return member;
        } catch (Exception e) {
            log.error("save error", e);
            throw e;
        } finally {
            close(connection, preparedStatement, null);
        }
    }

    private void close(final Connection connection, final PreparedStatement preparedStatement, final ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            log.error("resultSet close error", e);
        }

        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            log.error("preparedStatement close error", e);
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            log.error("connection close error", e);
        }
    }
}
