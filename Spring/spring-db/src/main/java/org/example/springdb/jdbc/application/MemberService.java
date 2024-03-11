package org.example.springdb.jdbc.application;

import java.sql.SQLException;

public interface MemberService {
    void accountTransfer(String fromId, String toId, int amount) throws SQLException;
}
