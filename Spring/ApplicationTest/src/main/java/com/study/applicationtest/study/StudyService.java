package com.study.applicationtest.study;

import com.study.applicationtest.domain.Member;
import com.study.applicationtest.domain.Study;
import com.study.applicationtest.member.MemberService;
import java.util.Optional;

public class StudyService {

    private final MemberService memberService;

    private final StudyRepository repository;

    public StudyService(MemberService memberService, StudyRepository repository) {
        assert memberService != null;
        assert repository != null;
        this.memberService = memberService;
        this.repository = repository;
    }

    public Study createNewStudy(Long memberId, Study study) {
        Optional<Member> member = memberService.findById(memberId);
        study.setOwnerId(member.orElseThrow(
            () -> new IllegalArgumentException("Member doesn't exist for id: '" + memberId + "'"))
            .getId());

        Study newstudy = repository.save(study);
        memberService.notify(newstudy);
//        memberService.notify(member.get());
        return newstudy;
    }

    public Study openStudy(Study study) {
        study.open();
        Study openedStudy = repository.save(study);
        memberService.notify(openedStudy);
        return openedStudy;
    }

    public void hi() {

    }
}
