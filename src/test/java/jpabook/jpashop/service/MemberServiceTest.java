package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest // 스프링부트 띄운상태로 테스트하려면 작성해야함
@Transactional // 롤백
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given ~ 코드가 주어졌을때
        Member member = new Member();
        member.setName("kim");

        //when 실행
        Long saveId = memberService.join(member);
        //then 결과
        em.flush();
        assertEquals(member, memberRepository.findOne(saveId));
    }
    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        try {
            memberService.join(member2); //예외
        } catch (IllegalStateException e) {
            return;
        }

        //then
        fail("예외가 발생해야 한다."); // 이쪽으로 들어오면 안됨
    }
}