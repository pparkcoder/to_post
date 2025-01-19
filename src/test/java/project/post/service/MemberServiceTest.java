package project.post.service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.post.domain.Address;
import project.post.domain.Member;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setEmail("test@test.com");
        member.setAddress(new Address("city","zip","street"));

        // when
        Long findMemberId = memberService.join(member);

        // then
        Assertions.assertEquals(member.getId(), findMemberId);
        Assertions.assertEquals(member.getEmail(), "test@test.com");
    }

    @Test
    void 회원중복확인() {
        // given
        Member member = new Member();
        member.setEmail("test@test.com");

        Member member2 = new Member();
        member2.setEmail("test@test.com");

        // when
        memberService.join(member);
        Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }

}