package com.study.applicationtest.solution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.study.applicationtest.domain.Member;
import com.study.applicationtest.domain.Study;
import com.study.applicationtest.member.MemberService;
import com.study.applicationtest.study.StudyRepository;
import com.study.applicationtest.study.StudyService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MockPracticeSolution {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @Test
    void mockMember() {
        Member member = new Member();
        member.setEmail("BJP");
        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        assertEquals("BJP", memberService.findById(1L).get().getEmail());
    }

    @Test
    void mockStudy() {
        Study study = new Study(10, "BJP");
        when(studyRepository.save(study)).thenReturn(study);
        assertEquals("BJP", studyRepository.save(study).getName());
    }

    @Test
    void solution() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        Member member = new Member();
        member.setId(1L);
        member.setEmail("BJP");
        Study study = new Study(10, "BJP");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        assertEquals(member.getId(), study.getOwnerId());
        assertEquals(member.getId(), study.getOwnerId());

        verify(memberService, times(1)).notify(study);
        verifyNoMoreInteractions(memberService);
//        verify(memberService, times(1)).notify(member);
        verify(memberService, never()).validate(any());

//        InOrder inOrder = inOrder(memberService);
//        inOrder.verify(memberService).notify(study);
//        inOrder.verify(memberService).notify(member);
    }

    @Test
    void bddTest() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        Member member = new Member();
        member.setId(1L);
        Study study = new Study(10, "테스트");

        given(memberService.findById(1L)).willReturn(Optional.of(member));
        given(studyRepository.save(study)).willReturn(study);

        studyService.createNewStudy(1L, study);

        assertEquals(member.getId(), study.getOwnerId());
        then(memberService).should(times(1)).notify(study);
        then(memberService).shouldHaveNoMoreInteractions();
    }

}
