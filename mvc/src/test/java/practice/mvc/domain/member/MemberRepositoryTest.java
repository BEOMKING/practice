package practice.mvc.domain.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void afterEach() {
        memberRepository.clear();
    }

    @Test
    void save() {
        Member member = new Member("hello", 20);

        Member savedMember = memberRepository.save(member);
        assertEquals(member, savedMember);
//        Member testMember = new Member("hello", 20);
//        testMember.setId(1L);

        Member findMember = memberRepository.findById(savedMember.getId());
        findMember.setAge(22);
        assertEquals(findMember.getAge(), savedMember.getAge());
        assertEquals(findMember, savedMember);
//        assertEquals(testMember, savedMember);
    }

    @Test
    void findAll() {

    }

}