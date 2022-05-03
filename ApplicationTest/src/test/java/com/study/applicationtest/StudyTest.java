package com.study.applicationtest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import com.study.applicationtest.domain.Study;
import com.study.applicationtest.domain.StudyStatus;
import java.time.Duration;
import java.util.function.Supplier;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class StudyTest {

    Study study;
    int value = 0;

    @BeforeEach
    void before_each() {
        System.out.println("beforeEach");
    }

    @DisplayName("반복 테스트")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeat_test(RepetitionInfo repetitionInfo) {
        System.out.println("test" + repetitionInfo.getCurrentRepetition() + " of " + repetitionInfo.getTotalRepetitions());
    }

    @DisplayName("파라미터 테스트")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(strings = {"정기점검", "프로젝트"})
    @EmptySource
    @NullSource
    void parameterized_test(String message) {
        System.out.println(message);


    }

//    @DisplayName("파라미터 테스트")
//    @ParameterizedTest(name = "{index} {displayName} message={0}")
//    @ValueSource(ints = {1, 2})
//    void csv_test(@ConvertWith(StudyConverter.class) Study study) {
//        System.out.println(study.getLimit());
//    }

    static class StudyConverter extends SimpleArgumentConverter {

        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "can only convert to study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    @Test
    @DisplayName("시스템 테스트 fast")
    @Tag("fast")
    void system_test() {
        String test_env = System.getenv("TEST_ENV");
        System.out.println(test_env);
        assumeTrue("LOCAL".equalsIgnoreCase(test_env));
    }

    @Test
    @DisplayName("스터디 만들기 slow")
    @Tag("slow")
    void create_new_study() {
        Study study = new Study(1);
        System.out.println(study);
        assertNotNull(study);
        Assertions.assertThat(study).isNotEqualTo(null);
    }

    @Test
    @DisplayName("스터디를 생성하면 상태 확인")
    void created_study_status_check() {
        Study study = new Study(1);
        assertEquals(StudyStatus.DRAFT, study.getStatus(), new Supplier<String>() {
            @Override
            public String get() {
                return "스터디를 처음 만들면 상태값이 " +  StudyStatus.DRAFT + "여야 한다.";
            }
        });
//        assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 상태값이 DRAFT여야 한다.");
//        assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다.");
//        assertTrue(study.getLimit() > 0, "스터디 제한 인원은 0보다 커야 한다.");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new Study(-10));
        assertEquals("limit은 0보다 커야 한다.", exception.getMessage());
    }

    @Test
    @DisplayName("스터디를 생성하면 상태 확인 (assertAll)")
    @DisabledOnJre(JRE.JAVA_11)
    void created_study_status_check_assertAll() {
        Study study = new Study(-10);
        assertAll(
            () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 " +  StudyStatus.DRAFT + "여야 한다.")
//            () -> assertTrue(study.getLimit() > 0, "스터디 제한 인원은 0보다 커야 한다.")
        );
    }

    @Test
    @DisplayName("실행시간 테스트")
    @DisabledOnJre(JRE.JAVA_11)
    void created_study_status_check_time_out() {
//        assertTimeout(Duration.ofSeconds(1), () -> {
//            new Study(10);
//            Thread.sleep(998);
//        });
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            new Study(10);
            Thread.sleep(10000);
        });
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
