```
@SpringBootTest
public class TestDataCreator {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void createTestData() {
        final String sql = "INSERT INTO member (id, user_name, salary, create_date, is_man, event_type, platform)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        final List<String> alpa = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                                                "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                                                "u", "v", "w", "x", "y", "z");

        final List<String> result = new ArrayList<>();
        recusion(0, "", alpa, result);

        final Random random = new Random();

        for (int z = 1; z < 2000; z++) {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, String.valueOf(UUID.randomUUID()));
                    ps.setString(2, result.get(random.nextInt(result.size())));
                    ps.setInt(3, random.nextInt(10000000));
                    ps.setTimestamp(4, new Timestamp(random.nextLong(1000000000000L, 2000000000000L)));
                    ps.setBoolean(5, random.nextBoolean());
                    ps.setInt(6, random.nextInt(30));
                    ps.setInt(7, random.nextInt(50));
                }

                @Override
                public int getBatchSize() {
                    return 10000;
                }
            });

            System.out.println(z);
        }
    }

    static void recusion(int index, String name, List<String> alpa, List<String> result) {
        if (index == 4) {
            result.add(name);
            return;
        }

        for (int i = 0; i < alpa.size(); i++) {
            recusion(index + 1, name + alpa.get(i), alpa, result);
        }
    }
}
```

