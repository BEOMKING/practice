package com.study.applicationtest.solution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.study.applicationtest.domain.Study;
import com.study.applicationtest.domain.StudyStatus;
import com.study.applicationtest.member.MemberService;
import com.study.applicationtest.study.StudyRepository;
import com.study.applicationtest.study.StudyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MockitoPracticeSolution {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @DisplayName("다른 사용자가 볼 수 있도록 스터디를 공개한다.")
    @Test
    void openStudy() {
        //Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        Study study = new Study(10, "더 자바, 테스트");
        assertEquals(StudyStatus.DRAFT, study.getStatus());
        given(studyRepository.save(study)).willReturn(study);
        // When
        studyService.openStudy(study);
        // Then
        assertEquals(StudyStatus.OPENED, study.getStatus());
        assertNotNull(study.getStatus());
        then(memberService).should(times(1)).notify(study);
    }

}
