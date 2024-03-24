package org.example.springdb.jdbc.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.springdb.jdbc.domain.Member;
import org.example.springdb.jdbc.repository.exception.MyDbException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * V4: 예외 처리 개선한 최종 버전
 */
@Slf4j
@Repository
public class MemberJDBCImprovementRepository implements MemberImprovementRepository {
    private final DataSource dataSource;

    public MemberJDBCImprovementRepository(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Member save(final Member member) {
        log.info("save");
        final String sql = "INSERT INTO member (member_id, money) VALUES (?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, member.getMemberId());
            preparedStatement.setInt(2, member.getMoney());
            preparedStatement.executeUpdate();
            return member;
        } catch (SQLException e) {
            throw new MyDbException(e);
        } finally {
            close(connection, preparedStatement, null);
        }
    }

    @Override
    public Member findById(final String memberId) {
        log.info("findById");
        final String sql = "SELECT * FROM member WHERE member_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, memberId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Member(resultSet.getString("member_id"), resultSet.getInt("money"));
            }

            throw new NoSuchElementException("No such member with memberId: " + memberId);
        } catch (SQLException e) {
            throw new MyDbException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public int update(final String memberId, final int money) {
        log.info("update");
        final String sql = "UPDATE member SET money = ? WHERE member_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, money);
            preparedStatement.setString(2, memberId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new MyDbException(e);
        } finally {
            close(connection, preparedStatement, null);
        }
    }

    @Override
    public int delete(final String memberId) {
        final String sql = "DELETE FROM member WHERE member_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, memberId);
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new MyDbException(e);
        } finally {
            close(connection, preparedStatement, null);
        }
    }

    private void close(final Connection connection, final PreparedStatement preparedStatement, final ResultSet resultSet) {
        JdbcUtils.closeResultSet(resultSet);
        JdbcUtils.closeStatement(preparedStatement);
        DataSourceUtils.releaseConnection(connection, dataSource);
    }

    private Connection getConnection() throws SQLException {
        final Connection connection = DataSourceUtils.getConnection(dataSource);
        log.info("Connection: {}", connection);
        return connection;
    }
}
