package com.study.applicationtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


//@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class StudyTest2 {

    int value = 0;
    int sharedValue = 0;

//    @BeforeAll
//    static void beforeAll() {
//        System.out.println("beforeAll");
//        value = 1;
//        sharedValue = 1;
//    }

//    @org.junit.Test
//    public void junit4_test() {
//
//    }

    @Test
    @DisplayName("Value 테스트")
    @Order(1)
    void value_test() {
        System.out.println(this);
        System.out.println(value++);
        System.out.println(sharedValue++);
    }

    @Test
    @DisplayName("Value 테스트2")
    @Order(2)
    void value_test2() throws InterruptedException {
        Thread.sleep(1500L);
        System.out.println(this);
        System.out.println(value++);
        System.out.println(sharedValue++);
    }

}
