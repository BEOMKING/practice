package org.example.springdb.jdbc.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.springdb.jdbc.connection.DBConnectionUtil;
import org.example.springdb.jdbc.domain.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

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

    public Member findById(final String memberId) throws SQLException {
        final String sql = "SELECT * FROM member WHERE member_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, memberId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Member(resultSet.getString("member_id"), resultSet.getInt("money"));
            }

            throw new NoSuchElementException("No such member with memberId: " + memberId);
        } catch (Exception e) {
            log.error("findById error", e);
            throw e;
        } finally {
            close(connection, preparedStatement, resultSet);
        }
    }

    public int update(final String memberId, final int money) throws SQLException {
        final String sql = "UPDATE member SET money = ? WHERE member_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, money);
            preparedStatement.setString(2, memberId);
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            log.error("update error", e);
            throw e;
        } finally {
            close(connection, preparedStatement, null);
        }
    }

    public int delete(final String memberId) throws SQLException {
        final String sql = "DELETE FROM member WHERE member_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, memberId);
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            log.error("delete error", e);
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
