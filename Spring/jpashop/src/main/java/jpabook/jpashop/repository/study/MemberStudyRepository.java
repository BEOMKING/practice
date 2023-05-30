package jpabook.jpashop.repository.study;

import java.util.List;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.entity.study.CompositeMemberStudy;
import jpabook.jpashop.entity.study.MemberStudy;
import jpabook.jpashop.entity.study.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberStudyRepository extends JpaRepository<MemberStudy, CompositeMemberStudy> {
    // 특정 스터디의 속한 멤버의 관계 정보
    @Query(value = "select ms from matching.member_study ms "
        + "where ms.compositeMemberStudy.study = :study and ms.isActive = true")
    List<MemberStudy> findMemberRelationInStudy(@Param("study") Study study);

    // 특정 프로젝트의 속한 멤버의 정보
    @Query(value = "select ms.compositeMemberStudy.member from matching.member_study ms "
        + "where ms.compositeMemberStudy.study = :study and ms.isActive = true")
    List<Member> findMemberInStudy(@Param("study") Study study);

    // 특정 멤버가 가지고 있는 활성화 스터디
    @Query(value = "select ms.compositeMemberStudy.member from matching.member_study ms "
        + "where ms.compositeMemberStudy.member = :member and ms.isActive = true")
    List<Study> studyInMember(@Param("member") Member member);
}