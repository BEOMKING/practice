package com.bjp.objects.ticket_sell;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeamTest {

    @Autowired
    private Team team;

    @Test
    public void 테스트() {
        assertAll(
                () -> assertEquals("Fasoo", team.getName()),
                () -> assertEquals(1, team.getMembers().size()),
                () -> assertEquals("BJP", team.getMembers().get(0).getName()),
                () -> assertEquals(28, team.getMembers().get(0).getAge())
        );
    }

}