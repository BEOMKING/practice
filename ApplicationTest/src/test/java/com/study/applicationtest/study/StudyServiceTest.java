package com.study.applicationtest.study;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.study.applicationtest.domain.Member;
import com.study.applicationtest.domain.Study;
import com.study.applicationtest.member.MemberService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @Test
    @DisplayName("스터디 서비스 객체가 생성되는지 확인")
    void createStudyService() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }

    @Test
    @DisplayName("Mock Stubbing")
    void mockStubbing() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        Member member = new Member();
        member.setId(1L);
        member.setEmail("qjawlsqjacks@naver.com");

        when(memberService.findById(any())).thenReturn(Optional.of(member));
        when(memberService.findById(1L)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> memberService.findById(1L));

        doThrow(new IllegalArgumentException()).when(memberService).validate(2L);
        assertThrows(IllegalArgumentException.class, () -> memberService.validate(2L));

        Study study = new Study(10, "java");
        studyService.createNewStudy(3L, study);
    }

    @Test
    void member() {
        Member member = new Member();
        member.setId(1L);
        member.setEmail("qjawlsqjacks@naver.com");

        when(memberService.findById(any()))
            .thenReturn(Optional.of(member))
            .thenThrow(new RuntimeException())
            .thenReturn(Optional.empty());

        Optional<Member> any = memberService.findById(2L);
        assertThrows(RuntimeException.class, () -> memberService.findById(3L));

        assertEquals(Optional.empty(), memberService.findById(2L));
    }
}