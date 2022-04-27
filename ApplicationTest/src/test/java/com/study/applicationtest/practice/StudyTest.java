package com.study.applicationtest.practice;

import java.util.function.Supplier;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class StudyTest {

    Study study;

    @BeforeEach
    void before_each() {
        System.out.println("beforeEach");
    }

    @Test
    @DisplayName("스터디 만들기")
    void create_new_study() {
        Study study = new Study();
        assertNotNull(study);
    }

    @Test
    @DisplayName("스터디를 생성하면 상태 확인")
    void created_study_status_check() {
        Study study = new Study();
        assertEquals(StudyStatus.DRAFT, study.getStatus(), new Supplier<String>() {
            @Override
            public String get() {
                return "스터디를 처음 만들면 상태값이 " +  StudyStatus.DRAFT + "여야 한다.";
            }
        });
//        assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 상태값이 DRAFT여야 한다.");
//        assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다.");
    }

    @Test
    @DisplayName("스터디 또 만들기")
    void create_new_study_again() {
        System.out.println("create1");
    }

    @BeforeAll
    static void before_all() {
        System.out.println("beforeAll");
    }



    @AfterEach
    void after_each() {
        System.out.println("afterEach");
    }

    @AfterAll
    static void after_all() {
        System.out.println("afterAll");
    }
}
