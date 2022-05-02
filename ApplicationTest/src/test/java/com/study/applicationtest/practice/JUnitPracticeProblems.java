package com.study.applicationtest.practice;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JUnitPracticeProblems {

    // 1 @DisplayName()
    // 2 3
    // 3 @Tag()
    // 4
    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    @DisplayName("4번 여러 Assertions를 실행하는 코드")
    @Order(1)
    void assertionAll(int a) {
        System.out.println(a);
        Practice practice = new Practice(1, "P1");
        assertAll(
            () -> assertEquals(1, practice.getNum()),
            () -> assertEquals("P1", practice.getName())
        );
    }
    // 5
    // 6 2
    // 7 PER_CLASS, OrderAnnotation
    // 8

    class Practice {

        private int num;

        private String name;

        public Practice(int num, String name) {
            this.num = num;
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public String getName() {
            return name;
        }
    }
}
