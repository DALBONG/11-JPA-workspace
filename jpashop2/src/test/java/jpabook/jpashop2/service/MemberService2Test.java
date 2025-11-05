package jpabook.jpashop2.service;

import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.repository.MemberRepository2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberService2Test {

    @Autowired MemberRepository2 memberRepository2;
    @Autowired MemberService2 memberService2;

    // 가입
    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("bong");

        //when
        Long saveId = memberService2.join(member);

        //then
        assertEquals(member, memberRepository2.findOne(saveId), "멤버 저장 확인");
    }

    @Test
    public void 중복회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("dalbong");

        Member member2 = new Member();
        member2.setName("dalbong");

        //when
        memberService2.join(member1);
        /*try{
            memberService2.join(member2); // 예외 발생
        }catch (IllegalStateException e){
            return;
        }*/
        assertThrows(IllegalStateException.class, () -> memberService2.join(member2));

        //then
        //fail("예외 발생하지 않았음");
    }



}