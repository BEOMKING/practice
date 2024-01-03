package helloboot.tobyspringboot;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HelloJdbcRepository implements HelloRepository {
    private final JdbcTemplate jdbcTemplate;

    public HelloJdbcRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Hello findHello(final String name) {
        try {
            return jdbcTemplate.queryForObject("select * from member where name = '" + name + "'",
                    (rs, rowNum) -> new Hello(rs.getString("name"), rs.getInt("count")));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void increaseHello(final String name) {
        Hello hello = findHello(name);
        if (hello == null) {
            jdbcTemplate.update("insert into member values(?, ?)", name, 1);
        } else {
            jdbcTemplate.update("update member set count = ? where name = ?", hello.getCount() + 1, name);
        }
    }
}
