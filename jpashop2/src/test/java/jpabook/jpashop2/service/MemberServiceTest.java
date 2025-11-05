package jpabook.jpashop2.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    //@Autowired EntityManager em; // 데이터 보존X 쿼리는 보고싶을 때


    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("dalbong");

        //when
        Long saveId = memberService.join(member);

        //then
        //em.flush(); // 데이터 보존X 쿼리는 보고싶을 때
        assertEquals(member, memberRepository.findOne(saveId));

    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("bongdal");

        Member member2 = new Member();
        member2.setName("bongdal");

        //when
        /*memberService.join(member1);
        try{
            memberService.join(member2); // 예외 발생해야 함.
        }catch (IllegalStateException e){
            return;
        }*/
        memberService.join(member1);

        // assertThrows(예외 클래스, 람다식);
        assertThrows(IllegalStateException.class,
                    () -> memberService.join(member2));

        //then
        //fail("예외 발생해야 함");
    }

}